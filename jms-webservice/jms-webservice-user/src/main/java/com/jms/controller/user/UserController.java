package com.jms.controller.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.ws.Message;
import com.jms.domain.ws.WSRoles;
import com.jms.domain.ws.WSUser;
import com.jms.service.user.UserService;

@RestController
public class UserController {
	
	@Autowired @Qualifier("userService")
	private UserService userService;
	
	@RequestMapping(value="user/checklogin", method=RequestMethod.GET)
	public Message checkLogin(@RequestParam("login") String login) throws Exception {
		return userService.checkLogin(login);
	}
	
	@RequestMapping(value="user/registor", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public void regist(@RequestBody @Valid WSUser wsUser, BindingResult bindingResult) throws Exception {
		//return userService.checkLogin(login);
		if(bindingResult.hasErrors())
			System.out.println("error!!!!!!!!!!!!!!!!!!");
	}

}