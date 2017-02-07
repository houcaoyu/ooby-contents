package ooby.contents.controler;

import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;

import ooby.contents.controler.form.ContentGroupForm;
import ooby.contents.dao.ContentGroupCategoryDao;
import ooby.contents.dao.ContentGroupDao;
import ooby.contents.dao.MomentDao;
import ooby.contents.dao.ResourceRepositoryDao;
import ooby.contents.dao.TemporaryFileDao;
import ooby.contents.dao.UserDao;
import ooby.contents.entity.ContentGroup;
import ooby.contents.entity.ContentGroupCategory;
import ooby.contents.entity.Moment;
import ooby.contents.entity.ResourceRepository;
import ooby.contents.entity.User;
import ooby.contents.utils.App;
import ooby.contents.view.ContentGroupView;

@Controller
@RequestMapping("/contentgroup")
public class ContentGroupController {

	@Autowired
	private ContentGroupDao dao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private MomentDao momentDao;
	@Autowired
	private App app;
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private TemporaryFileDao temporaryFileDao;
	@Autowired
	private ResourceRepositoryDao resourceRepositoryDao;
	
	@Autowired
	private ContentGroupCategoryDao contentGroupCategoryDao;

	@GetMapping("/hotest/ofcategory")
	@ResponseBody
	@JsonView({ContentGroupView.List.class})
	public List<ContentGroup> hostest(int number, Long categoryId) {
		Pageable page = new PageRequest(0, number);
		return dao.findHotestByCategoryId(categoryId, page);
	}

	@GetMapping("/hotest")
	@ResponseBody
	@JsonView(ContentGroupView.List.class)
	public List<ContentGroup> hotest(int number) {
		Pageable page = new PageRequest(0, number);
		return dao.findHotest(page);
	}

	@GetMapping("/{id}")
	@ResponseBody
	@JsonView({ContentGroupView.Detail.class})
	public ContentGroup detail(@PathVariable Long id) {
		ContentGroup c = dao.findOne(id);
		app.setFollowed(c.getUser());
		app.setSubscribed(c);
		return c;
	}

	@GetMapping("/ofauthor/{authorId}")
	@ResponseBody
	@JsonView(ContentGroupView.List.class)
	public List<ContentGroup> getByAuthorId(@PathVariable Long authorId) {
		return dao.findByUserId(authorId);
	}

	@GetMapping("/oftag/{tagId}")
	@ResponseBody
	@JsonView(ContentGroupView.List.class)
	public List<ContentGroup> getByTagId(@PathVariable Long tagId) {
		return dao.findByTagsId(tagId);
	}

	@GetMapping("/ofsubscriber/{subscriberId}")
	@ResponseBody
	@JsonView(ContentGroupView.List.class)
	public List<ContentGroup> getBySubscriberId(@PathVariable Long subscriberId) {
		return dao.findBySubscribersId(subscriberId);
	}
	
	@PreAuthorize("authenticated")
	@GetMapping("/ofsubscriber/currentuser")
	@ResponseBody
	@JsonView(ContentGroupView.List.class)
	public List<ContentGroup> getSubscriptionsOfCurrentUser() {
		User loginInfo=app.getCurrentUser();
		return dao.findBySubscribersId(loginInfo.getId());
	}

	
	
	@GetMapping("/ofcategory/{categoryId}")
	@ResponseBody
	@JsonView(ContentGroupView.List.class)
	public List<ContentGroup> getByCategoryId(@PathVariable Long categoryId){
		return dao.findByCategoryId(categoryId);
	}

	@PreAuthorize("authenticated")
	@PostMapping("")
	@ResponseBody
	@Transactional
	@JsonView(ContentGroupView.List.class)
	public ContentGroup create(ContentGroupForm form) throws HibernateException, IOException {
		User user=app.getCurrentUser();
		ContentGroup contentGroup=new ContentGroup();
		BeanUtils.copyProperties(form, contentGroup);
		contentGroup.setUser(user);
		resourceRepositoryDao.copyTemporaryFile(form.getCoverId());
		ResourceRepository resource=resourceRepositoryDao.findOne(form.getCoverId());
		contentGroup.setImage(resource);
		
		ContentGroupCategory category=contentGroupCategoryDao.findOne(form.getCategoryId());
		contentGroup.setCategory(category);
		
		dao.save(contentGroup);

		Moment moment = new Moment();
		moment.setType("create_content_group");
		moment.setFromUser(user);
		moment.setContentGroup(contentGroup);
		momentDao.save(moment);
		return contentGroup;
	}

	@PreAuthorize("authenticated")
	@PutMapping("")
	@ResponseBody
	@Transactional
	@JsonView(ContentGroupView.List.class)
	public ContentGroup update(ContentGroupForm form) {
		User user=app.getCurrentUser();
		ContentGroup contentGroup=dao.findOne(form.getId());
		BeanUtils.copyProperties(form, contentGroup);
		if(form.getCoverId()!=null){
			resourceRepositoryDao.copyTemporaryFile(form.getCoverId());
			ResourceRepository resource=resourceRepositoryDao.findOne(form.getCoverId());
			resourceRepositoryDao.delete(contentGroup.getImage());
			contentGroup.setImage(resource);
		}
		ContentGroupCategory category=contentGroupCategoryDao.findOne(form.getCategoryId());
		contentGroup.setCategory(category);
		
		dao.save(contentGroup);

		Moment moment = new Moment();
		moment.setType("update_content_group");
		moment.setFromUser(user);
		moment.setContentGroup(contentGroup);
		momentDao.save(moment);
		return contentGroup;
	}

	@PreAuthorize("authenticated")
	@DeleteMapping("/{id}")
	@ResponseBody
	@Transactional
	public void delete(@PathVariable Long id) {
		User user=app.getCurrentUser();
		dao.delete(id);

		ContentGroup contentGroup = new ContentGroup();
		contentGroup.setId(id);
		Moment moment = new Moment();
		moment.setType("delete_content_group");
		moment.setFromUser(user);
		moment.setContentGroup(contentGroup);
		momentDao.save(moment);
	}

	@PreAuthorize("authenticated") 
	@RequestMapping("/subscribe")
	@ResponseBody
	@Transactional
	public void subscribe(Long id) {
		User user=app.getCurrentUser();
		ContentGroup c= dao.findOne(id);
		User u = userDao.findOne(user.getId());
		u.getSubscriptions().add(c);
		c.getSubscribers().add(u);
		dao.save(c);

		Moment moment = new Moment();
		moment.setType("subscribe");
		moment.setFromUser(u);
		moment.setToUser(c.getUser());
		moment.setContentGroup(c);
		momentDao.save(moment);
	}

	@PreAuthorize("authenticated")
	@PostMapping("/unsubscribe")
	@ResponseBody
	@Transactional
	public void unsubscribe(Long id) {
		User user=app.getCurrentUser();
		ContentGroup c = dao.findOne(id);
		for (User u : c.getSubscribers()) {
			if (u.getId().equals(user.getId())) {
				user = u;
				break;
			}
		}
		c.getSubscribers().remove(user);
		user.getSubscriptions().remove(c);
		dao.save(c);

		Moment moment = new Moment();
		moment.setType("unsubscribe");
		moment.setFromUser(user);
		moment.setToUser(c.getUser());
		moment.setContentGroup(c);
		momentDao.save(moment);
	}
}
