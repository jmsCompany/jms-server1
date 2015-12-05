package com.jms.controller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.jms.acl.SecuredObjectService;
import com.jms.domain.db.Apps;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSMenu;
import com.jms.domain.ws.WSUser;
import com.jms.domain.ws.WSUserProfile;
import com.jms.domainadapter.UserAdapter;
import com.jms.repositories.system.AppsRepository;
import com.jms.repositories.user.UsersRepository;
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
	private UsersRepository usersRepository;
	@Autowired
	private WSUsersValidator wsUsersValidator;
	@Autowired
	private AppsRepository appsRepository;
	@Autowired
	private SecuredObjectService securedObjectService;
	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		binder.addValidators(wsUsersValidator);
	}
	@Transactional(readOnly=false)
	@RequestMapping(value="/login", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSUserProfile login(@RequestBody WSUser wsUser) throws Exception {
		WSUserProfile userProfile = new WSUserProfile();
		System.out.println("user: " +wsUser.getLogin() +", pass: "  +wsUser.getPassword());
		String token = userService.login(wsUser.getLogin(), wsUser.getPassword());
		if(token==null)
			return userProfile;
		Users u =usersRepository.findByUsernameOrEmailOrMobile(wsUser.getLogin());
		userProfile.setLogin(wsUser.getLogin());
		userProfile.setToken(token);
		userProfile.setIdUser(u.getIdUser());
		userProfile.setLogoURL("www.logo.com");
		userProfile.setIdCompany(u.getCompany().getIdCompany());
		userProfile.setName(u.getName());
		List<Apps> appList =appsRepository.findAll();
		Map<Apps, String> smap= securedObjectService.getSecuredObjectsWithPermissions(u, appList);
		List<WSMenu> WSMenuList = new ArrayList<WSMenu>();
		for(Apps a : smap.keySet())
		{
			WSMenu item = new WSMenu();
			item.setGroup(a.getGroups());
			item.setName(a.getAppName());
			item.setId(a.getIdApp());
			item.setUrl(a.getUrl());
			item.setPermission(smap.get(a));
			WSMenuList.add(item);
		}
		userProfile.setWSMenuList(WSMenuList);
		return userProfile;
	}
	
	
	@RequestMapping(value="/check/username", method=RequestMethod.GET)
	public Valid checkLogin(@RequestParam("login") String login,@RequestParam(required=false, value="idUser") Long idUser) throws Exception {
		Boolean returnVal = userService.checkLogin(login,idUser);
		Valid valid = new Valid();
		valid.setValid(returnVal);
		return valid;
	}
	
	@RequestMapping(value="/check/jmstoken", method=RequestMethod.GET)
	public Valid checkToken(@RequestParam("jmstoken") String jmstoken) throws Exception {
		System.out.println("token: " + jmstoken);
		Boolean returnVal = userService.checkToken(jmstoken);
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