package ooby.contents.controler;

import java.util.List;

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

import ooby.contents.dao.UserDao;
import ooby.contents.entity.User;
import ooby.contents.utils.App;
import ooby.contents.view.UserView;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserDao dao;

	@Autowired
	private App app;
	
	@GetMapping("/hotest")
	@ResponseBody
	@JsonView(UserView.List.class)
	public List<User> hotest(int number){
		Pageable page=new PageRequest(0,number);
		return dao.findHotestUsers(page);
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	@JsonView(UserView.Detail.class)
	public User detail(@PathVariable Long id){
		User user= dao.findOne(id);
		app.setFollowed(user);
		return user;
	}
	
	@PreAuthorize("authenticated")
	@GetMapping("/self")
	@ResponseBody
	@JsonView(UserView.Detail.class)
	public User detailSelf(){
		User user= dao.findOne(app.getCurrentUser().getId());
		app.setFollowed(user);
		return user;
	}
	
	@GetMapping("/offollower/{followerId}")
	@ResponseBody
	@JsonView(UserView.List.class)
	public List<User> byFollower(@PathVariable Long followerId){
		return dao.findByFollowersId(followerId);
	}
	
	
	@PostMapping("")
	@ResponseBody
	@JsonView(UserView.List.class)
	@Transactional
	public User register(User user){
		dao.save(user);
		return user;
	}
	
	@PreAuthorize("authenticated")
	@PutMapping
	@ResponseBody
	@JsonView(UserView.List.class)
	@Transactional
	public User update(User user){
		dao.save(user);
		return user;
	}
	
	@PreAuthorize("authenticated")
	@PostMapping("/follow")
	@ResponseBody
	@Transactional
	public void follow(Long id){
		User user=app.getCurrentUser();
		User followee=dao.findOne(id);
		User follower=dao.findOne(user.getId());
		follower.getFollowees().add(followee);
		followee.getFollowers().add(follower);
		dao.save(followee);
		
	}
	
	@PreAuthorize("authenticated")
	@PostMapping("/unfollow")
	@ResponseBody
	@Transactional
	public void unfollow(Long id){
		User user=app.getCurrentUser();
		User followee=dao.findOne(id);
		for(User u:followee.getFollowers()){
			if(u.getId().equals(user.getId())){
				user=u;
				break;
			}
		}
		followee.getFollowers().remove(user);
		user.getFollowees().remove(followee);
		dao.save(followee);
	}
	
//	@PostMapping("/login")
//	@ResponseBody
//	public void login(String loginName,String password,HttpSession session){
//		User user=dao.findByLoginNameAndPassword(loginName, password);
//		if(user==null){
//			//throw authentication exception
//		}else{
//			User u=new User();
//			u.setId(user.getId());
//			u.setNickname(user.getNickname());
//			u.setLoginName(user.getLoginName());
//			session.setAttribute("user", u);
//		}
//	}
	
}
