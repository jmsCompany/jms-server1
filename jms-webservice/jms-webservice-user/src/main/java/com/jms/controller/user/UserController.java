package com.jms.controller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.jms.acl.SecuredObjectService;
import com.jms.domain.SandVikRoleEnum;
import com.jms.domain.db.Apps;
import com.jms.domain.db.GroupMembers;
import com.jms.domain.db.Groups;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSAndriodMenuItem;
import com.jms.domain.ws.WSMenu;
import com.jms.domain.ws.WSRoles;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSUser;
import com.jms.domain.ws.WSUserProfile;
import com.jms.domainadapter.UserAdapter;
import com.jms.repositories.system.AppsRepository;
import com.jms.repositories.user.GroupMemberRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.service.user.UserService;
import com.jms.service.user.WSUsersValidator;
import com.jms.web.security.JMSUserDetails;
import com.jms.web.security.SecurityUtils;

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
	@Autowired
	private GroupMemberRepository groupMemberRepository;
	@Autowired
	private SecurityUtils securityUtils;
	
	/*
	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		binder.addValidators(wsUsersValidator);
	}
	*/
	@Transactional(readOnly=false)
	@RequestMapping(value="/login", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSUserProfile login(@RequestBody WSUser wsUser) throws Exception {
		WSUserProfile userProfile = new WSUserProfile();
		//System.out.println("user: " +wsUser.getLogin() +", pass: "  +wsUser.getPassword());
		String token = userService.login(wsUser.getLogin(), wsUser.getPassword());
		if(token==null)
		{
			//System.out.println("token is null????");
			return userProfile;
		}
		Users u =usersRepository.findByUsernameOrEmailOrMobile(wsUser.getLogin());
		userProfile.setLogin(wsUser.getLogin());
		userProfile.setToken(token);
		userProfile.setIdUser(u.getIdUser());
		userProfile.setLogoURL("www.xxxxxx.com");
		userProfile.setIdCompany(u.getCompany().getIdCompany());
		userProfile.setName(u.getName());
		Boolean isOP= false;
		for(GroupMembers g:u.getGroupMemberses())
		{
			if(g.getRoles().getRole().equals("OP"))
			{
				isOP=true;
				break;
			}
		}
		
		userProfile.setIsOP(isOP);
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
	//	System.out.println("check token: " + jmstoken);
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
	
	
	
	@Transactional(readOnly=true)
	@RequestMapping(value="/getAndroidMenu", method=RequestMethod.GET)
	public List<WSAndriodMenuItem> getAndroidMenu() throws Exception {

	
		List<WSAndriodMenuItem>  items = new ArrayList<WSAndriodMenuItem>();
		JMSUserDetails ud = securityUtils.getCurrentUser();
		//System.out.println(" get android menu: the token is:  " + ud.getToken());
		for(GrantedAuthority g: ud.getAuthorities())
		{
			if(g.getAuthority().equals(SandVikRoleEnum.OP.name()))
			{
				System.out.println(" get OP: ");
				WSAndriodMenuItem i1 = new WSAndriodMenuItem("OP","操作员","MRstopforOP","MRstopforOP","需料与停机（操作工人的主界面");
				WSAndriodMenuItem i2 = new WSAndriodMenuItem("OP","操作员","Qtycheck","Qtycheck","到点检查已经生产的数量");
				WSAndriodMenuItem i3 = new WSAndriodMenuItem("OP","操作员","Stopbyplan","Stopbyplan","按计划停机");
				WSAndriodMenuItem i4 = new WSAndriodMenuItem("OP","操作员","Actualsetup","Actualsetup","实际装载时间记录");
				WSAndriodMenuItem i5 = new WSAndriodMenuItem("OP","操作员","Checklist","Checklist","检查清单");
				WSAndriodMenuItem i6 = new WSAndriodMenuItem("OP","操作员","Eqstopforop","Eqstopforop","由操作工人填写设备故障原因");
				//WSAndriodMenuItem i7 = new WSAndriodMenuItem("OP","操作员","Ehslogin","Ehslogin","EHS登录");
				WSAndriodMenuItem i8 = new WSAndriodMenuItem("OP","操作员","Thinkthroughthetask","Thinkthroughthetask","进行安全检查");
				items.add(i1);
				items.add(i2);
				items.add(i3);
				items.add(i4);
				items.add(i5);
				items.add(i6);
				//items.add(i7);
				items.add(i8);
				break;
			}
			if(g.getAuthority().equals(SandVikRoleEnum.equipment.name()))
			{
				System.out.println(" get equipment: ");
				WSAndriodMenuItem i1 = new WSAndriodMenuItem("equipment","设备","Unplanned_equipment","Unplanned_equipment","计划外的设备原因导致停机");
				WSAndriodMenuItem i2 = new WSAndriodMenuItem("equipment","设备","Eqstopforeq","Eqstopforeq","由设备部员工填写设备故障原因");
				WSAndriodMenuItem i3 = new WSAndriodMenuItem("equipment","设备","Maintainance","Maintainance","设备保养计划");
				
				items.add(i1);
				items.add(i2);
				items.add(i3);
				
				break;
			}
			if(g.getAuthority().equals(SandVikRoleEnum.quality.name()))
			{
				System.out.println(" get quality: ");
				WSAndriodMenuItem i1 = new WSAndriodMenuItem("quality","质量","Unplanned_quality","Unplanned_quality","计划外的质量原因导致停机");
				WSAndriodMenuItem i2 = new WSAndriodMenuItem("quality","质量","Problem_des","Problem_des","质量问题描述");
				items.add(i1);
				items.add(i2);
				break;
			}
			if(g.getAuthority().equals(SandVikRoleEnum.warehouse.name()))
			{
				System.out.println(" get warehouse: ");
				WSAndriodMenuItem i1 = new WSAndriodMenuItem("warehouse","仓库","Mrforwh","Mrforwh","仓库发料满足工人的需料要求");
				WSAndriodMenuItem i2 = new WSAndriodMenuItem("warehouse","仓库","Unplanned_ms","Unplanned_ms","计划外的缺料导致停机");
				WSAndriodMenuItem i3 = new WSAndriodMenuItem("warehouse","仓库","Msstopforwh","Msstopforwh","仓库发料满足工人的需料要求");
				WSAndriodMenuItem i4 = new WSAndriodMenuItem("warehouse","仓库","Returnrmlist","Returnrmlist","退料列表");
				WSAndriodMenuItem i5 = new WSAndriodMenuItem("warehouse","仓库","Returnrm","Returnrm","新建退料");
				WSAndriodMenuItem i6 = new WSAndriodMenuItem("warehouse","仓库","Receiving","Receiving","从供应商处收货");
				WSAndriodMenuItem i7 = new WSAndriodMenuItem("warehouse","仓库","Delivery","Delivery","发货给客户");
				WSAndriodMenuItem i8 = new WSAndriodMenuItem("warehouse","仓库","RTV","RTV","退货给供应商");
				WSAndriodMenuItem i9 = new WSAndriodMenuItem("warehouse","仓库","Inventorysummary","Inventorysummary","库存汇总");
				WSAndriodMenuItem i10 = new WSAndriodMenuItem("warehouse","仓库","Inventorydetail","Inventorydetail","库存明细");
				items.add(i1);
				items.add(i2);
				items.add(i3);
				items.add(i4);
				items.add(i5);
				items.add(i6);
				items.add(i7);
				items.add(i8);
				items.add(i9);
				items.add(i10);
				break;
			}
			if(g.getAuthority().equals(SandVikRoleEnum.supervisor.name()))
			{
				System.out.println(" get supervisor: ");
				WSAndriodMenuItem i1 = new WSAndriodMenuItem("supervisor","主管","MRstopforOP","MRstopforOP","需料与停机（操作工人的主界面");
				WSAndriodMenuItem i2 = new WSAndriodMenuItem("supervisor","主管","Qtycheck","Qtycheck","到点检查已经生产的数量");
				WSAndriodMenuItem i3 = new WSAndriodMenuItem("supervisor","主管","Stopbyplan","Stopbyplan","按计划停机");
				WSAndriodMenuItem i4 = new WSAndriodMenuItem("supervisor","主管","Actualsetup","Actualsetup","实际装载时间记录");
				WSAndriodMenuItem i5 = new WSAndriodMenuItem("supervisor","主管","Checklist","Checklist","检查清单");
				WSAndriodMenuItem i6 = new WSAndriodMenuItem("supervisor","主管","Eqstopforop","Eqstopforop","由操作工人填写设备故障原因");
				WSAndriodMenuItem i7 = new WSAndriodMenuItem("supervisor","主管","Thinkthroughthetaskfordirector","Thinkthroughthetaskfordirector","检查操作工人的安全检查情况");
				
			    items.add(i1);
				items.add(i2);
				items.add(i3);
				items.add(i4);
				items.add(i5);
				items.add(i6);
				items.add(i7);
			
				break;
			}
		}
		return items;
	}
	

	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/group/op/members", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<WSSelectObj> findOPByCompanyId()
	{
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		List<Users> users = groupMemberRepository.findOPByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		for(Users u : users)
		{
			WSSelectObj w = new WSSelectObj(u.getIdUser(),u.getName());
			ws.add(w);
		}
		return ws;
	}
	

}