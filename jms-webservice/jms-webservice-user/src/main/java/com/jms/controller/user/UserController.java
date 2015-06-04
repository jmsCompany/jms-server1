package com.jms.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import com.jms.domain.ws.Message;
import com.jms.service.user.UserService;

@RestController
public class UserController {
	
	@Autowired @Qualifier("userService")
	private UserService userService;
	
	@RequestMapping(value="user/checklogin", method=RequestMethod.GET)
	public Message checkLogin(@RequestParam("login") String login) throws Exception {
		return userService.checkLogin(login);
	}
	


}