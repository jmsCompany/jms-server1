package com.jms.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.ws.Message;
import com.jms.domain.ws.WSRolePrivs;
import com.jms.domain.ws.WSRoles;
import com.jms.service.user.RoleService;


@RestController
public class RoleController {
	
	@Autowired 
	private RoleService roleService;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/company/addRole", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Message addRole(@RequestBody WSRoles wsRoles) throws Exception
	{
		return roleService.addRole(wsRoles);
	}
	@Transactional(readOnly = false)
	@RequestMapping(value="/company/addRolePrivs", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Message addRolePrivs(@RequestBody WSRolePrivs wsRolePrivs)
	{
		return roleService.addRolePrivs(wsRolePrivs);
	}
	@Transactional(readOnly = true)
	@RequestMapping(value="/company/roleprivs", method=RequestMethod.GET)
	public WSRolePrivs getRolePrivs(@RequestParam("companyName") String companyName,@RequestParam("role") String role)
	{
		return roleService.getRolePrivs(companyName, role);
	}
}