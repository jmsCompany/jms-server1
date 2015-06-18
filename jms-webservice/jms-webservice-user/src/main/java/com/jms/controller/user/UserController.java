package com.jms.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.WSUser;
import com.jms.domainadapter.UserAdapter;
import com.jms.service.user.UserService;
import com.jms.service.user.WSUsersValidator;

@RestController
@Transactional(readOnly=true)
public class UserController {
	
	@Autowired @Qualifier("userService")
	private UserService userService;
	
	@Autowired 
	private UserAdapter userAdapter;
	
	@Autowired
	private WSUsersValidator wsUsersValidator;
	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		binder.addValidators(wsUsersValidator);
	}

	@RequestMapping(value="/login", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public String login(@RequestBody WSUser wsUser) throws Exception {
		return userService.login(wsUser.getLogin(), wsUser.getPassword());
	}
	
	
	@RequestMapping(value="/user/checklogin", method=RequestMethod.GET)
	public Message checkLogin(@RequestParam("login") String login,@RequestParam(required=false, value="idUser") Integer idUser) throws Exception {
		return userService.checkLogin(login,idUser);
	}
	

}