package com.jms.controller.user;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.ws.WSGroup;
import com.jms.domain.ws.WSUser;
import com.jms.service.user.GroupService;
import com.jms.user.IUserService;
import com.jms.web.security.SecurityUtils;

@RestController
public class GroupController {
	
	@Autowired
	private GroupService groupService;
	@Autowired @Qualifier("iUserServiceImpl")
	private IUserService iUserServiceImpl;
	@Autowired 
	private  SecurityUtils securityUtils;
	private static final Logger logger = LogManager.getLogger(GroupController.class.getCanonicalName());
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/group/save", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public WSGroup createGroup(@RequestBody WSGroup wsGroup) throws Exception
	{
		return groupService.save(wsGroup);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/groups", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<WSGroup> getGroups(@RequestParam("idCompany") Long idCompany) throws Exception
	{
		return groupService.getGroupsByIdCompany(idCompany);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/sectors", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<WSGroup> getSectors(@RequestParam("idCompany") Long idCompany) throws Exception
	{
		return groupService.getSectorsByIdCompany(idCompany);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/group/view/{idGroup}", method=RequestMethod.GET
	, produces=MediaType.APPLICATION_JSON_VALUE)
	public WSGroup getGroup(@PathVariable("idGroup") Long idGroup) throws Exception
	{
		return groupService.getWSGroup(idGroup);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/group/{idGroup}/members", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<WSUser> getUsersByIdGroup(@PathVariable("idGroup") Long idGroup) throws Exception
	{
		return iUserServiceImpl.getUsersByIdGroup(idGroup);
	}

	
}