package com.jms.service.user;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.csvreader.CsvReader;
import com.jms.acl.SecurityACLDAO;
import com.jms.audit.AudiReaderService;
import com.jms.domain.Config;
import com.jms.domain.GroupTypeEnum;
import com.jms.domain.SandVikRoleEnum;
import com.jms.domain.SystemRoleEnum;
import com.jms.domain.db.Apps;
import com.jms.domain.db.Company;
import com.jms.domain.db.GroupMembers;
import com.jms.domain.db.GroupMembersId;
import com.jms.domain.db.Groups;
import com.jms.domain.db.PCPp;
import com.jms.domain.db.PCppOpId;
import com.jms.domain.db.Roles;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.MessageTypeEnum;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSRoles;
import com.jms.domain.ws.WSUser;
import com.jms.domain.ws.WSUserPassword;
import com.jms.domain.ws.WSUserRoles;
import com.jms.domain.ws.p.WSPCppOP;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.p.PCPpRepository;
import com.jms.repositories.p.PCppOpRepository;
import com.jms.repositories.system.AppsRepository;
import com.jms.repositories.system.SysDicDRepository;
import com.jms.repositories.user.GroupMemberRepository;
import com.jms.repositories.user.GroupRepository;
import com.jms.repositories.user.GroupTypeRepository;
import com.jms.repositories.user.RoleRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Qualifier("userService")
public class UserService extends IUserServiceImpl{
	@Autowired
	private SysDicDRepository sysDicDRepository;
	@Autowired
	private AudiReaderService audiReaderService;
	private static final Logger logger = LogManager.getLogger(UserService.class.getCanonicalName());
	@Autowired
	private RoleService roleService;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private  GroupService groupService;
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private SecurityACLDAO securityACLDAO;
	@Autowired
	private SecurityUtils securityUtils;
	
	@Autowired
	private AppsRepository appsRepository;
	
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private  GroupTypeRepository groupTypeRepository;
	
	@Autowired
	private  PCPpRepository pCPpRepository;
	
	@Autowired
	private  PCppOpRepository pCppOpRepository;
	
	
	@Autowired
	private GroupMemberRepository groupMemberRepository;
	
	@Transactional(readOnly=false)
	public WSUser save(WSUser wsUser) throws Exception
	{
			Boolean userValid = checkLogin(wsUser.getUsername(), wsUser.getEmail(),
					wsUser.getMobile(),wsUser.getIdUser());
	        String anyLogin ="";
	        if(wsUser.getUsername()!=null&&!wsUser.getUsername().isEmpty())
	        	anyLogin =wsUser.getUsername();
	        if(anyLogin.isEmpty())
	        {
	        	 if(wsUser.getEmail()!=null&&!wsUser.getEmail().isEmpty())
	             	anyLogin =wsUser.getEmail();
	        }
	        if(anyLogin.isEmpty())
	        {
	        	 if(wsUser.getMobile()!=null&&!wsUser.getMobile().isEmpty())
	             	anyLogin =wsUser.getMobile();
	        }
			if (!userValid)
				throw new java.lang.Exception("用户已经被注册了");
			Users dbUser = usersRepository.findOne(wsUser.getIdUser());
			dbUser = userAdapter.toDBUser(wsUser, dbUser);
			if(securityUtils.getCurrentDBUser()!=null)
			{
				dbUser.setCompany(securityUtils.getCurrentDBUser().getCompany());
			}
			usersRepository.save(dbUser);
			wsUser.setIdUser(dbUser.getIdUser());
			return wsUser;
	}
	
	
	@Transactional(readOnly=false)
	public Valid updateUserPasswordByAdmin(WSUserPassword wsUserPassword)
	{
		Valid v = new Valid();
		Long userId = wsUserPassword.getIdUser();
		Users u = usersRepository.findOne(userId);
		u.setPassword(new BCryptPasswordEncoder().encode(wsUserPassword.getNewPassword()));
		usersRepository.save(u);
		v.setValid(true);
		return v;
	}
	
	
	@Transactional(readOnly=false)
	public Valid updateUserPassword( WSUserPassword wsUserPassword) throws Exception
	{
		Valid v = new Valid();
		Long userId = wsUserPassword.getIdUser();
		Users u = usersRepository.findOne(userId);
		if(new BCryptPasswordEncoder().matches(wsUserPassword.getPassword(), u.getPassword()))
    	{
			u.setPassword(new BCryptPasswordEncoder().encode(wsUserPassword.getNewPassword()));
			usersRepository.save(u);
			v.setValid(true);
			return v;
    	}
		else
		{
			v.setValid(false);
			v.setMsg("您输入的原密码不对！");
			return v;
		}
	
	}

	
	@Transactional(readOnly=false)
	public List<WSPCppOP> getWSCppOPs(Long machineId) throws Exception
	{
		List<WSPCppOP> ws = new ArrayList<WSPCppOP>();
		for(PCPp cpp:pCPpRepository.getNotFinishedCppsByMachineId(machineId))
		{
			WSPCppOP w  = new WSPCppOP();
			w.setIdCpp(cpp.getIdCPp());
			Date d = cpp.getPlanSt();
			String dd = "";
			if(d!=null)
			{
				SimpleDateFormat formatter = new SimpleDateFormat ("MMdd"); 
				
				dd= formatter.format(d);
			}
			String shift = "";
			if(cpp.getPShiftPlanD()!=null)
			{
				shift = cpp.getPShiftPlanD().getShift();
			}
			w.setCpp(dd +" "+shift+"_"+cpp.getPWo().getWoNo()+"_"+cpp.getMMachine().getCode());
			PCppOpId id =new PCppOpId();
			id.setIdCpp(cpp.getIdCPp());
			id.setIdUser(securityUtils.getCurrentDBUser().getIdUser());
			if(pCppOpRepository.findOne(id)==null)
			{
				w.setChecked(false);
			}
			else
			{
				w.setChecked(true);
			}
			
			ws.add(w);
		}
		
		return ws;
	}
	
	
	@Transactional(readOnly=false)
	public WSUser updateInfo(WSUser wsUser) throws Exception
	{
			Boolean userValid = checkLogin(wsUser.getUsername(), wsUser.getEmail(),
					wsUser.getMobile(),wsUser.getIdUser());
	        String anyLogin ="";
	        if(wsUser.getUsername()!=null&&!wsUser.getUsername().isEmpty())
	        	anyLogin =wsUser.getUsername();
	        if(anyLogin.isEmpty())
	        {
	        	 if(wsUser.getEmail()!=null&&!wsUser.getEmail().isEmpty())
	             	anyLogin =wsUser.getEmail();
	        }
	        if(anyLogin.isEmpty())
	        {
	        	 if(wsUser.getMobile()!=null&&!wsUser.getMobile().isEmpty())
	             	anyLogin =wsUser.getMobile();
	        }
			if (!userValid)
				throw new java.lang.Exception("用户已经被注册了");
			Users dbUser = usersRepository.findOne(wsUser.getIdUser());
			String p = dbUser.getPassword();
			String t = dbUser.getToken();
			Users dbUser1 = userAdapter.toDBUser(wsUser, dbUser);
			dbUser1.setPassword(p);
			dbUser1.setToken(t);
			if(securityUtils.getCurrentDBUser()!=null)
			{
				dbUser1.setCompany(securityUtils.getCurrentDBUser().getCompany());
			}
			usersRepository.save(dbUser1);
			wsUser.setIdUser(dbUser1.getIdUser());
			return wsUser;
	}
	
	/*
	@Transactional(readOnly=false)
	public void loadUsersFromCSV(InputStream inputStream) throws IOException
	{
		
		CsvReader reader = new CsvReader(inputStream,Charset.forName("UTF-8"));
        reader.readHeaders();  //username	password	name	email	mobile	address	id_card	enabled
		while(reader.readRecord())
		{
			Users u = new Users();
			u.setUsername(reader.get("username"));
			u.setPassword(reader.get("password"));
			u.setName(reader.get("name"));
			u.setEmail(reader.get("email"));
			u.setMobile(reader.get("mobile"));
			u.setAddress(reader.get("address"));
			u.setIdcard(reader.get("id_card"));
			u.setEnabled(Long.parseLong(reader.get("enabled")));
			u.setCreationTime(new Date());
			u.setSysDicDByLocale(sysDicDRepository.findDicsByType(Config.lang).get(0));
			Message msg = register(u);
			logger.debug(msg.getMessageTypeEnum().toString()  +", "+ msg.getMessage());
		}
	}
	*/
	@Transactional(readOnly=false)
	public void createDefaultUsers()
	{
		Users system = new Users();
		system.setUsername("system");
		system.setPassword("system");
		
		register(system);
		Users admin = new Users();
		admin.setUsername("admin");
		admin.setPassword("admin");
		register(admin);
		
		
		Users user = new Users();
		user.setUsername("user");
		user.setPassword("user");
		register(user);

	}
	@Transactional(readOnly=false)
	public String login(String login, String password)
	{
		String defaultMsg=null;
	//	System.out.println("user login :" +login);
	    Users user =  usersRepository.findByUsernameOrEmailOrMobile(login);
	    System.out.println("find user:" +user.getIdUser());
	    if(user!=null&&user.getEnabled().longValue()==1l)
	    {
	    //	System.out.println("user is not null");
	    	if(new BCryptPasswordEncoder().matches(password, user.getPassword()))
	    	{
	    		//System.out.println("wrong password!");
	    		user.setLastLogin(new Date());
	    		String token = user.getIdUser()+"__"+new BCryptPasswordEncoder().encode(new Date().toString());
				user.setToken(token);
				usersRepository.save(user);
	    		defaultMsg = user.getToken();
	    	}
	    		
	    }
		return defaultMsg;
		
	}

	@Transactional(readOnly=true)
     public List<Users> findRevisions(Long idUser)
     {
    	return audiReaderService.getRevisions(Users.class, idUser);

     }
	
	@Transactional(readOnly=false)
	public void createTestUsersforSandVik()
	{
		
		Company  c = companyRepository.findByCompanyName("SandVik");
		Roles r_op = roleService.save(SandVikRoleEnum.OP.name(),"操作员",c);
		Roles r_quality = roleService.save(SandVikRoleEnum.quality.name(),"质检员",c);
		Roles r_supervisor = roleService.save(SandVikRoleEnum.supervisor.name(),"主管",c);
		Roles r_warehouse = roleService.save(SandVikRoleEnum.warehouse.name(),"主管",c);
		Roles r_equipment = roleService.save(SandVikRoleEnum.equipment.name(),"仪器维护员",c);
		Roles userRole = roleRepository.findByRoleAndCompanyName(
	    SystemRoleEnum.user.name(), c.getCompanyName());
		
		
		Groups g_op = groupService.createGroup(c, GroupTypeEnum.Role, SandVikRoleEnum.OP.name(), "操作员", null);
		Groups g_quality =  groupService.createGroup(c, GroupTypeEnum.Role, SandVikRoleEnum.quality.name(), "质检员", null);
		Groups g_supervisor =  groupService.createGroup(c, GroupTypeEnum.Role, SandVikRoleEnum.supervisor.name(), "主管", null);
		Groups g_warehouse =  groupService.createGroup(c, GroupTypeEnum.Role, SandVikRoleEnum.warehouse.name(), "主管", null);
		Groups g_equipment = groupService.createGroup(c, GroupTypeEnum.Role, SandVikRoleEnum.equipment.name(), "仪器维护员", null);
		
		Users op = new Users();
		op.setUsername(SandVikRoleEnum.OP.name());
		op.setPassword(SandVikRoleEnum.OP.name());
		op.setCompany(c);
		register(op);
		
		
		Users quality = new Users();
		quality.setUsername(SandVikRoleEnum.quality.name());
		quality.setPassword(SandVikRoleEnum.quality.name());
		quality.setCompany(c);
		register(quality);
		
		
		
		Users supervisor = new Users();
		supervisor.setUsername(SandVikRoleEnum.supervisor.name());
		supervisor.setPassword(SandVikRoleEnum.supervisor.name());
		supervisor.setCompany(c);
		register(supervisor);
		
		
		Users warehouse = new Users();
		warehouse.setUsername(SandVikRoleEnum.warehouse.name());
		warehouse.setPassword(SandVikRoleEnum.warehouse.name());
		warehouse.setCompany(c);
		register(warehouse);
		
		
		Users equipment = new Users();
		equipment.setUsername(SandVikRoleEnum.equipment.name());
		equipment.setPassword(SandVikRoleEnum.equipment.name());
		equipment.setCompany(c);
		register(equipment);
		
		

		Groups opSelf = groupService.createGroup(c, GroupTypeEnum.User, ""+op.getIdUser(), ""+op.getIdUser(), null);
		Groups qualitySelf = groupService.createGroup(c, GroupTypeEnum.User, ""+quality.getIdUser(), ""+quality.getIdUser(), null);
		Groups supervisorSelf = groupService.createGroup(c, GroupTypeEnum.User, ""+supervisor.getIdUser(), ""+supervisor.getIdUser(), null);
		Groups warehouseSelf = groupService.createGroup(c, GroupTypeEnum.User, ""+warehouse.getIdUser(), ""+warehouse.getIdUser(), null);
		Groups equipmentSelf = groupService.createGroup(c, GroupTypeEnum.User, ""+equipment.getIdUser(), ""+equipment.getIdUser(), null);
		
		groupService.addUserToDefaultGroup(op, opSelf, userRole);
		groupService.addUserToDefaultGroup(quality, qualitySelf, userRole);
		groupService.addUserToDefaultGroup(supervisor, supervisorSelf, userRole);
		groupService.addUserToDefaultGroup(warehouse, warehouseSelf, userRole);
		groupService.addUserToDefaultGroup(equipment, equipmentSelf, userRole);
		
		
		groupService.addUserToDefaultGroup(op, g_op, r_op);
		groupService.addUserToDefaultGroup(quality, g_quality, r_quality);
		groupService.addUserToDefaultGroup(supervisor, g_supervisor, r_supervisor);
		groupService.addUserToDefaultGroup(warehouse, g_warehouse, r_warehouse);
		groupService.addUserToDefaultGroup(equipment, g_equipment, r_equipment);

	}
	
	public void loadUsersByCompanyId(InputStream inputStream, Long companyId) throws IOException {

		Company company = companyRepository.findOne(companyId);
		Roles userRole = roleRepository.findByRoleAndCompanyName(
			    SystemRoleEnum.user.name(), company.getCompanyName());
		CsvReader reader = new CsvReader(inputStream, ',', Charset.forName("UTF-8"));
		reader.readHeaders();
		while (reader.readRecord()) {
			Users u = new Users();
			u.setUsername(reader.get(0)+"@"+company.getIdCompany());
			u.setPassword(reader.get(0));
			u.setName(reader.get(1));
			u.setDescription(reader.get(2)); //description as English name
			u.setCompany(company);
			register(u);
			Groups himSelf = groupService.createGroup(company, GroupTypeEnum.User, ""+u.getIdUser(), ""+u.getIdUser(), null);
			groupService.addUserToDefaultGroup(u, himSelf, userRole);
		}
	}
	
	
	@Transactional(readOnly=false)
	public void createTestUsersforWWW()
	{
		
		Company  c = companyRepository.findOne(3l);
		Roles r_op = roleService.save(SandVikRoleEnum.OP.name(),"操作员",c);
		Roles r_quality = roleService.save(SandVikRoleEnum.quality.name(),"质检员",c);
		Roles r_supervisor = roleService.save(SandVikRoleEnum.supervisor.name(),"主管",c);
		Roles r_warehouse = roleService.save(SandVikRoleEnum.warehouse.name(),"主管",c);
		Roles r_equipment = roleService.save(SandVikRoleEnum.equipment.name(),"仪器维护员",c);
		Roles userRole = roleRepository.findByRoleAndCompanyName(
	    SystemRoleEnum.user.name(), c.getCompanyName());
		
		
		Groups g_op = groupService.createGroup(c, GroupTypeEnum.Group, SandVikRoleEnum.OP.name(), "操作员", null);
		Groups g_quality =  groupService.createGroup(c, GroupTypeEnum.Group, SandVikRoleEnum.quality.name(), "质检员", null);
		Groups g_supervisor =  groupService.createGroup(c, GroupTypeEnum.Group, SandVikRoleEnum.supervisor.name(), "主管", null);
		Groups g_warehouse =  groupService.createGroup(c, GroupTypeEnum.Group, SandVikRoleEnum.warehouse.name(), "主管", null);
		Groups g_equipment = groupService.createGroup(c, GroupTypeEnum.Group, SandVikRoleEnum.equipment.name(), "仪器维护员", null);
		
		Users op = new Users();
		op.setUsername(SandVikRoleEnum.OP.name()+c.getIdCompany());
		op.setPassword(SandVikRoleEnum.OP.name());
		op.setCompany(c);
		register(op);
		
		
		Users quality = new Users();
		quality.setUsername(SandVikRoleEnum.quality.name()+c.getIdCompany());
		quality.setPassword(SandVikRoleEnum.quality.name());
		quality.setCompany(c);
		register(quality);
		
		
		
		Users supervisor = new Users();
		supervisor.setUsername(SandVikRoleEnum.supervisor.name()+c.getIdCompany());
		supervisor.setPassword(SandVikRoleEnum.supervisor.name());
		supervisor.setCompany(c);
		register(supervisor);
		
		
		Users warehouse = new Users();
		warehouse.setUsername(SandVikRoleEnum.warehouse.name()+c.getIdCompany());
		warehouse.setPassword(SandVikRoleEnum.warehouse.name());
		warehouse.setCompany(c);
		register(warehouse);
		
		
		Users equipment = new Users();
		equipment.setUsername(SandVikRoleEnum.equipment.name()+c.getIdCompany());
		equipment.setPassword(SandVikRoleEnum.equipment.name());
		equipment.setCompany(c);
		register(equipment);
		
		

		Groups opSelf = groupService.createGroup(c, GroupTypeEnum.User, ""+op.getIdUser(), ""+op.getIdUser(), null);
		Groups qualitySelf = groupService.createGroup(c, GroupTypeEnum.User, ""+quality.getIdUser(), ""+quality.getIdUser(), null);
		Groups supervisorSelf = groupService.createGroup(c, GroupTypeEnum.User, ""+supervisor.getIdUser(), ""+supervisor.getIdUser(), null);
		Groups warehouseSelf = groupService.createGroup(c, GroupTypeEnum.User, ""+warehouse.getIdUser(), ""+warehouse.getIdUser(), null);
		Groups equipmentSelf = groupService.createGroup(c, GroupTypeEnum.User, ""+equipment.getIdUser(), ""+equipment.getIdUser(), null);
		
		groupService.addUserToDefaultGroup(op, opSelf, userRole);
		groupService.addUserToDefaultGroup(quality, qualitySelf, userRole);
		groupService.addUserToDefaultGroup(supervisor, supervisorSelf, userRole);
		groupService.addUserToDefaultGroup(warehouse, warehouseSelf, userRole);
		groupService.addUserToDefaultGroup(equipment, equipmentSelf, userRole);
		
		
		groupService.addUserToDefaultGroup(op, g_op, r_op);
		groupService.addUserToDefaultGroup(quality, g_quality, r_quality);
		groupService.addUserToDefaultGroup(supervisor, g_supervisor, r_supervisor);
		groupService.addUserToDefaultGroup(warehouse, g_warehouse, r_warehouse);
		groupService.addUserToDefaultGroup(equipment, g_equipment, r_equipment);

	}
	
	@Transactional(readOnly=false)
	public void addAppPermissionsToGroup(Long id,  List<Apps> apps)
	{
		//for group
		GrantedAuthoritySid sid = new GrantedAuthoritySid("" +id);
		for (Apps a : apps) {
			securityACLDAO.addPermission(a, sid, BasePermission.READ);
		}
		
	}
	
	@Transactional(readOnly=false)
	public void addAppPermissionToGroup(Long id,  Long appId)
	{
		//for group
		GrantedAuthoritySid sid = new GrantedAuthoritySid("" +id);
		Apps a = appsRepository.findOne(appId);
		securityACLDAO.addPermission(a, sid, BasePermission.READ);

	}
	@Transactional(readOnly=false)
	public void deleteAppPermissionToGroup(Long id,  Long appId)
	{
		//for group
		GrantedAuthoritySid sid = new GrantedAuthoritySid("" +id);
		Apps a = appsRepository.findOne(appId);
		securityACLDAO.deletePermission(a, Apps.class, sid, BasePermission.READ);

	}
	@Transactional(readOnly=false)
	public void deleteAppPermissionsToGroup(Long id,  List<Apps> apps)
	{
		//for group
		GrantedAuthoritySid sid = new GrantedAuthoritySid("" +id);
		for (Apps a : apps) {
			securityACLDAO.deletePermission(a, Apps.class, sid, BasePermission.READ);
		}
		
	}
	
	public List<WSRoles> getRolesByUserId(long userId)
	{
		List<WSRoles> wsrs = new ArrayList<WSRoles>();
		
		Users u = usersRepository.findOne(userId);
		
		Map<Long,String> groups = new HashMap<Long,String>();
	    
		for(final GroupMembers gm : u.getGroupMemberses()) {
		   
		   if(groups.containsKey(gm.getId().getIdGroup()))
			   continue;
		   Groups g = gm.getGroups();
		   if(g.getGroupType().getIdGroupType().equals(5l))
		   {
			   WSRoles wsr = new WSRoles();
			   wsr.setIdRole(g.getIdGroup());
			   wsr.setRole(g.getGroupName());
			   wsr.setDescription(g.getDescription());
			   wsrs.add(wsr);
		   }
		  
			
		
		}
		
		return wsrs;
	}

	@Transactional(readOnly=true)
	public WSUser getUsersByIdUser(Long idUser) throws Exception
	{


		Users u = usersRepository.findOne(idUser);

		WSUser  wsu= userAdapter.toWSUser(u);
		
		wsu.setRoleList(getRolesByUserId(idUser));
		
		
		return wsu;
		
	}
	
	
	
	@Transactional(readOnly=false)
	public Valid addRolesforUser(WSUserRoles wsUserRoles) throws Exception
	{
		//System.out.println("add rols......");
		Valid v = new Valid();
		v.setValid(true);
		Users u = usersRepository.findOne(wsUserRoles.getIdUser());
		//System.out.println("user id: " + wsUserRoles.getIdUser());
		
		Map<Long,GroupMembers> gmsMap = new HashMap<Long,GroupMembers>();
	
		for(GroupMembers gm : u.getGroupMemberses()) {
			   Groups g = gm.getGroups();
			   if(g.getGroupType().getIdGroupType().equals(5l))
			   {
				 //  System.out.println("delete role: " + gm.getGroups().getGroupName());
				 //  groupMemberRepository.deleteByIdUserAndIdGroup(wsUserRoles.getIdUser(), g.getIdGroup());
				   gmsMap.put(g.getIdGroup(), gm);
			   }

			}
		for(WSRoles r: wsUserRoles.getRoleList())
		{
			//Roles role = roleRepository.findOne(r.getIdRole());
			//System.out.println("add role......" +role.getRole());
			//Groups g =groupRepository.findGroupByGroupNameAndCompany(role.getRole(), securityUtils.getCurrentDBUser().getCompany().getIdCompany(), GroupTypeEnum.Role.name());
			Groups g =groupRepository.findOne(r.getIdRole());
		
				//System.out.println("find group id ......" +g.getIdGroup());
				//保留该小组成员
				if(gmsMap.containsKey(g.getIdGroup()))
				{
				  //  System.out.println("keep role......" +gmsMap.get(g.getIdGroup()).getGroups().getGroupName());
					gmsMap.remove(g.getIdGroup());
					continue;
				}
				groupService.addUserToDefaultGroup(u, g, null);
			
			
		}
		for(GroupMembers m: gmsMap.values())
		{
			//System.out.println("delete role......" +m.getGroups().getIdGroup());
			groupMemberRepository.deleteByIdUserAndIdGroup(wsUserRoles.getIdUser(), m.getGroups().getIdGroup());
		}
		return v;
	}
	


	
}
