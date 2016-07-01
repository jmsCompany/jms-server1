package com.jms.controller.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.acl.SecuredObjectService;
import com.jms.domain.GroupTypeEnum;
import com.jms.domain.db.Apps;
import com.jms.domain.db.Groups;
import com.jms.domain.db.Roles;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSMenu;
import com.jms.domain.ws.WSRolePermissions;
import com.jms.domain.ws.WSRoles;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.production.WSPBom;
import com.jms.repositories.system.AppsRepository;
import com.jms.repositories.user.GroupRepository;
import com.jms.repositories.user.GroupTypeRepository;
import com.jms.repositories.user.RoleRepository;
import com.jms.service.user.RoleService;
import com.jms.service.user.UserService;
import com.jms.web.security.SecurityUtils;


@RestController
public class RoleController {
	
	@Autowired 
	private RoleService roleService;
	
	@Autowired 
	private RoleRepository roleRepository;
	@Autowired 
	private GroupRepository groupRepository;
	@Autowired 
	private GroupTypeRepository groupTypeRepository;
	
	@Autowired
	private SecurityUtils securityUtils;
	@Autowired
	private  AppsRepository appsRepository;
	
	@Autowired @Qualifier("userService")
	private UserService userService;
	@Autowired
	private  SecuredObjectService securedObjectService;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/u/roles/create", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Message addRole(@RequestBody WSRoles wsRoles) throws Exception
	{
		wsRoles.setCompanyName(securityUtils.getCurrentDBUser().getCompany().getCompanyName());
		return roleService.addRole(wsRoles);
	}


	@Transactional(readOnly = false)
	@RequestMapping(value="/u/rolePermissions/create", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid addRolePermission(@RequestBody WSRolePermissions wsRolePermissions) throws Exception
	{
		
		Valid v = new Valid();
		v.setValid(true);
		Groups g = groupRepository.findGroupByGroupNameAndCompany(wsRolePermissions.getRole(), securityUtils.getCurrentDBUser().getCompany().getIdCompany(), GroupTypeEnum.Role.name());
		if(g==null)
		{
			g = new Groups();
			g.setCompany(securityUtils.getCurrentDBUser().getCompany());
			g.setCreationTime(new Date());
			g.setGroupName(wsRolePermissions.getRole());
			g.setGroupType(groupTypeRepository.findOne(5l));
			g=groupRepository.save(g);
		}
		
		List<Apps> appList =appsRepository.findAll();
		securedObjectService.removePermissions(g, appList);
			
        	 for(WSMenu m:wsRolePermissions.getMenuList())
        	 {
        		 System.out.println("save perm: gid: " + g.getIdGroup() +", appId: " + m.getId());
        		 userService.addAppPermissionToGroup(g.getIdGroup(), m.getId());
        	 }
       
		
		return v;
	}
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/u/roles/list", method=RequestMethod.GET)
	public WSTableData  roles(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		List<Roles> rs =roleRepository.findByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());

		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(rs.size()<start + length)
			end =rs.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			Roles w = rs.get(i);
			String[] d = {w.getRole(),w.getDescription(),""+w.getIdRole()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(rs.size());
		t.setRecordsFiltered(rs.size());
	    t.setData(lst);
	    return t;
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/u/wsRoles", method=RequestMethod.GET)
	public List<WSRoles>  wsRoles() throws Exception {	   
		
		List<Roles> rs =roleRepository.findByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		//List<Groups> gs = groupsRepository.
		List<WSRoles> wsRoleList = new ArrayList<WSRoles>();
		for(Roles r: rs)
		{
			WSRoles w = new WSRoles();
			Groups g =groupRepository.findGroupByGroupNameAndCompany(r.getRole(), securityUtils.getCurrentDBUser().getCompany().getIdCompany(), "Role");
			if(g==null)
			{
				  g= new Groups();
				  g.setGroupName(r.getRole());
				  g.setDescription(r.getDescription());
				  g.setCompany(securityUtils.getCurrentDBUser().getCompany());
				  g.setGroupType(groupTypeRepository.findOne(5l));
				  g=groupRepository.save(g);
			}		
			w.setIdRole(g.getIdGroup());
			w.setDescription(r.getDescription());
			if(r.getCompany()!=null)
			{
				w.setCompanyName(r.getCompany().getCompanyName());
			}
			
			w.setRole(r.getRole());
			wsRoleList.add(w);
		}
		
	    return wsRoleList;
	}
	
	
}