package com.jms.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.WSRoles;
import com.jms.service.user.RoleService;


@RestController
public class RoleController {
	
	@Autowired 
	private RoleService roleService;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/roles/create", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Message addRole(@RequestBody WSRoles wsRoles) throws Exception
	{
		return roleService.addRole(wsRoles);
	}

	
}