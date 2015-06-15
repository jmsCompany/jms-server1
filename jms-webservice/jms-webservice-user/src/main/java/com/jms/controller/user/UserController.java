package com.jms.controller.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.ws.Message;
import com.jms.domain.ws.MessageTypeEnum;
import com.jms.domain.ws.WSUser;
import com.jms.service.user.UserService;
import com.jms.service.user.WSUsersValidator;

@RestController
public class UserController {
	
	@Autowired @Qualifier("userService")
	private UserService userService;
	
	@Autowired
	private WSUsersValidator wsUsersValidator;
	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		binder.addValidators(wsUsersValidator);
	}

	@RequestMapping(value="user/checklogin", method=RequestMethod.GET)
	public Message checkLogin(@RequestParam("login") String login) throws Exception {
		return userService.checkLogin(login);
	}
	
	@RequestMapping(value="user/registor", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSUser regist(@RequestBody @Valid WSUser wsUser, BindingResult bindingResult) throws Exception {
		if(bindingResult.hasErrors())
		{
			wsUser.setCode(bindingResult.getAllErrors().get(0).getCode());
			wsUser.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
			wsUser.setMessageTypeEnum(MessageTypeEnum.ERROR);
	
		}
		return wsUser;
	
	}

}