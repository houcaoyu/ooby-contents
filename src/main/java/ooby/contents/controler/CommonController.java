package ooby.contents.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ooby.contents.entity.User;
import ooby.contents.utils.App;

@Controller
@RequestMapping("/common")
public class CommonController {
	
	@Autowired
	private App app;
	
	@RequestMapping("/needlogin")
	@ResponseBody
	@ResponseStatus(code=HttpStatus.UNAUTHORIZED)
	public void needLogin(){
		
	}
	@RequestMapping("/loginsuccess")
	@ResponseBody
	public User loginSuccess(){
		return app.getCurrentUser(); 
	}
	@RequestMapping("/loginfailure")
	@ResponseBody
	@ResponseStatus(code=HttpStatus.PAYMENT_REQUIRED)
	public void loginFailure(){
	}
	
	@RequestMapping("/logoutsuccess")
	@ResponseBody
	public void logouSuccess(){
	}
	
	@PostMapping("/logininfo")
	@ResponseBody 
	public User getLoginInfo(){
		return app.getCurrentUser();
	}
}
