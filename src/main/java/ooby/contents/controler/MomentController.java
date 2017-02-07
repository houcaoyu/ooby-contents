package ooby.contents.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import ooby.contents.dao.MomentDao;
import ooby.contents.entity.Moment;
import ooby.contents.entity.User;

@Controller
@RequestMapping("/moment")
public class MomentController {
	@Autowired
	private MomentDao dao;
	
	@RequestMapping("/user")
	@ResponseBody
	public List<Moment> user(@SessionAttribute User user){
		return dao.findByFromUserFollowersId(user.getId());
	}
	
	
}
