package com.jms.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.ws.Message;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSUser;
import com.jms.domain.ws.WSUserProfile;
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
	@Transactional(readOnly=false)
	@RequestMapping(value="/login", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSUserProfile login(@RequestBody WSUser wsUser) throws Exception {
		WSUserProfile userProfile = new WSUserProfile();
		String token = userService.login(wsUser.getLogin(), wsUser.getPassword());
		System.out.println("token: " + token);
		userProfile.setLogin(wsUser.getLogin());
		userProfile.setToken(token);
		return userProfile;
	}
	
	
	@RequestMapping(value="/check/username", method=RequestMethod.GET)
	public Valid checkLogin(@RequestParam("login") String login,@RequestParam(required=false, value="idUser") Long idUser) throws Exception {
		System.out.println("check login: " + login);
		Boolean returnVal = userService.checkLogin(login,idUser);
		Valid valid = new Valid();
		valid.setValid(returnVal);
		return valid;
	}
	@Transactional(readOnly=false)
	@RequestMapping(value="/user/save", method=RequestMethod.POST)
	public WSUser save(@RequestBody WSUser wsUser) throws Exception {
		return userService.save(wsUser);
	}

}