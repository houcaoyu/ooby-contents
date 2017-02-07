package ooby.contents.controler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;

import ooby.contents.dao.CommentDao;
import ooby.contents.dao.ContentDao;
import ooby.contents.dao.UserDao;
import ooby.contents.entity.Comment;
import ooby.contents.entity.Content;
import ooby.contents.entity.User;
import ooby.contents.utils.App;
import ooby.contents.view.CommentView;

@Controller
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private CommentDao dao;
	@Autowired
	private ContentDao contentDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private App app;
	
	@PreAuthorize("authenticated")
	@PostMapping()
	@ResponseBody()
	@JsonView(CommentView.Base.class)
	@Transactional
	public Comment create(String text,Long contentId){
		Comment comment=new Comment();
		Content content=contentDao.findOne(contentId);
		User loginInfo=app.getCurrentUser();
		User user=userDao.findOne(loginInfo.getId());
		comment.setContent(content);
		comment.setUser(user);
		comment.setText(text);
		comment.setDateTime(new Date());
		return dao.save(comment);
	}

}
