package ooby.contents.controler;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;

import ooby.contents.controler.form.ContentForm;
import ooby.contents.dao.ContentDao;
import ooby.contents.dao.ContentGroupDao;
import ooby.contents.dao.MomentDao;
import ooby.contents.dao.ResourceRepositoryDao;
import ooby.contents.dao.UserDao;
import ooby.contents.entity.Content;
import ooby.contents.entity.ContentGroup;
import ooby.contents.entity.Moment;
import ooby.contents.entity.ResourceRepository;
import ooby.contents.entity.User;
import ooby.contents.utils.App;
import ooby.contents.view.ContentView;

@Controller
@RequestMapping("/content")
public class ContentController {
	
	@Autowired
	private ContentDao dao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private MomentDao momentDao;
	@Autowired
	private App app;
	@Autowired
	private ResourceRepositoryDao resourceRepositoryDao;
	@Autowired
	private ContentGroupDao contentGroupDao;
	
	@JsonView(ContentView.List.class)
	@GetMapping("/hotest")
	@ResponseBody
	public List<Content> hotest(int number){
		Pageable page=new PageRequest(0, number);
		return dao.findByOrderByPvDesc(page);
	}
	@JsonView(ContentView.Detail.class)
	@GetMapping("/{id}")
	@ResponseBody
	public Content detail(@PathVariable Long id){
		Content content= dao.findOne(id);
		if(app.getCurrentUser()==null||!app.getCurrentUser().getId().equals(content.getContentGroup().getUser().getId())){
			content.setPv(content.getPv()+1);
			dao.save(content);
		}
		app.setLiked(content);
		app.setCollected(content);
		return content;
	}
	@JsonView(ContentView.List.class)
	@GetMapping("/ofauthor/{authorId}")
	@ResponseBody
	public List<Content> getByAuthor(@PathVariable Long authorId){
		return dao.findByContentGroupUserId(authorId);
	}
	@JsonView(ContentView.List.class)
	@GetMapping("/ofcollector/{collectorId}")
	@ResponseBody
	public List<Content> getByCollector(@PathVariable Long collectorId){
		return dao.findByCollectedUsersId(collectorId);
	}
	@JsonView(ContentView.List.class)
	@GetMapping("/offan/{fanId}")
	@ResponseBody
	public List<Content> getByFan(@PathVariable Long fanId){
		return dao.findByFansId(fanId);
	}
	@JsonView(ContentView.List.class)
	@GetMapping("/ofreader/{readerId}")
	@ResponseBody
	public List<Content> getByReader(@PathVariable Long readerId){
		return dao.findByReadersId(readerId);
	}
	@JsonView(ContentView.Base.class)
	@PreAuthorize("authenticated")
	@PostMapping()
	@ResponseBody
	@Transactional
	public Content create(ContentForm form){
		User user=app.getCurrentUser();
		Content content=new Content();
		BeanUtils.copyProperties(form, content);
		resourceRepositoryDao.copyTemporaryFile(form.getCoverId());
		ResourceRepository resource=resourceRepositoryDao.findOne(form.getCoverId());
		content.setImage(resource);
		
		ContentGroup contentGroup=contentGroupDao.findOne(form.getContentGroupId());
		content.setContentGroup(contentGroup);
		
		dao.save(content);
		
		Moment moment=new Moment();
		moment.setType("create_content");
		moment.setFromUser(user);
		moment.setContent(content);
		momentDao.save(moment);
		return content;
	}
	
	@JsonView(ContentView.Base.class)
	@PreAuthorize("authenticated")
	@PutMapping()
	@ResponseBody
	@Transactional
	public Content update(ContentForm form) {
		User user=app.getCurrentUser();
		Content content=dao.findOne(form.getId());
		BeanUtils.copyProperties(form, content);
		resourceRepositoryDao.copyTemporaryFile(form.getCoverId());
		ResourceRepository resource=resourceRepositoryDao.findOne(form.getCoverId());
		content.setImage(resource);
		dao.save(content);

		Moment moment = new Moment();
		moment.setType("update_content");
		moment.setFromUser(user);
		moment.setContent(content);
		momentDao.save(moment);
		return content;
	}
	
	@PreAuthorize("authenticated")
	@PostMapping("/like")
	@ResponseBody
	@Transactional
	public void like(Long id){
		User user=app.getCurrentUser();
		Content c=dao.findOne(id);
		User u=userDao.findOne(user.getId());
		c.getFans().add(u);
		u.getLikes().add(c);
		dao.save(c);
		
		Moment moment=new Moment();
		moment.setType("like");
		moment.setFromUser(u);
		moment.setToUser(c.getContentGroup().getUser());
		moment.setContent(c);
		momentDao.save(moment);
	}
	@PreAuthorize("authenticated")
	@PostMapping("/unlike")
	@ResponseBody
	@Transactional
	public void unlike(Long id){
		User loginInfo=app.getCurrentUser();
		User user=userDao.findOne(loginInfo.getId());
		Content content=dao.findOne(id);
		for(Content c:user.getLikes()){
			if(c.getId().equals(content.getId())){
				content=c;
				break;
			}
		}
		content.getFans().remove(user);
		user.getLikes().remove(content);
		dao.save(content);
		
		Moment moment=new Moment();
		moment.setType("unlike");
		moment.setFromUser(user);
		moment.setToUser(content.getContentGroup().getUser());
		moment.setContent(content);
		momentDao.save(moment);
	}
	@PreAuthorize("authenticated")
	@PostMapping("/uncollect")
	@ResponseBody
	@Transactional
	public void uncollect(Long id){
		User loginInfo=app.getCurrentUser();
		User user=userDao.findOne(loginInfo.getId());
		Content content=dao.findOne(id);
		for(Content c:user.getCollections()){
			if(content.getId().equals(c.getId())){
				content=c;
				break;
			}
		}
		content.getCollectedUsers().remove(user);
		user.getCollections().remove(content);
		dao.save(content);
		
		Moment moment=new Moment();
		moment.setType("uncollect");
		moment.setFromUser(user);
		moment.setToUser(content.getContentGroup().getUser());
		moment.setContent(content);
		momentDao.save(moment);
	}
	@PreAuthorize("authenticated")
	@PostMapping("/collect")
	@ResponseBody
	@Transactional
	public void collect(Long id){
		User user=app.getCurrentUser();
		Content c=dao.findOne(id);
		User u=userDao.findOne(user.getId());
		c.getCollectedUsers().add(user);
		u.getCollections().add(c);
		dao.save(c);
		
		Moment moment=new Moment();
		moment.setType("collect");
		moment.setFromUser(u);
		moment.setToUser(c.getContentGroup().getUser());
		moment.setContent(c);
		momentDao.save(moment);
	}
}
