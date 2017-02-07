package ooby.contents.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import ooby.contents.dao.UserDao;
import ooby.contents.entity.Content;
import ooby.contents.entity.ContentGroup;
import ooby.contents.entity.User;

@Component
public class App {
	@Autowired
	private UserDao userDao;
	
	public User getCurrentUser(){
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof User)
			return (User)principal;
		else
			return null;
	}
	
	public boolean setFollowed(User user){
		User loginInfo=getCurrentUser();
		if(loginInfo==null)
			return false;
		else{
			User currentUser=userDao.findOne(loginInfo.getId());
			for(User u:currentUser.getFollowees()){
				if(u.getId().equals(user.getId())){
					user.setFollowed(true);
					return true;
				}
			}
		}
		return false;
	}
	public boolean setSubscribed(ContentGroup contentGroup){
		User user=getCurrentUser();
		if(user==null)
			return false;
		if (user != null) {
			User u = userDao.findOne(user.getId());
			for (ContentGroup group : u.getSubscriptions()) {
				if (contentGroup.getId().equals(group.getId())) {
					contentGroup.setSubscribed(true);
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean setLiked(Content content){
		User user=getCurrentUser();
		if(user==null)
			return false;
		if (user != null) {
			User u = userDao.findOne(user.getId());
			for (Content c : u.getLikes()) {
				if (content.getId().equals(c.getId())) {
					content.setLiked(true);
					return true;
				}
			}
		}
		return false;
	}
	public boolean setCollected(Content content){
		User user=getCurrentUser();
		if(user==null)
			return false;
		if (user != null) {
			User u = userDao.findOne(user.getId());
			for (Content c : u.getCollections()) {
				if (content.getId().equals(c.getId())) {
					content.setCollected(true);
					return true;
				}
			}
		}
		return false;
	}
	
	public String getTempPath(HttpSession session){
		String path = session.getServletContext().getRealPath(getCurrentUser().getId()+"");
		File file=new File(path);
		if(!file.exists())
			file.mkdirs();
		return file.getAbsolutePath();
	}
	
	public InputStream getInputStream(HttpSession session,String fileName) throws FileNotFoundException{
		File file=new File(getTempPath(session)+"\\"+fileName);
		return new FileInputStream(file);
	}
}
