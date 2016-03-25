package com.jms.service.user;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.audit.AudiReaderService;
import com.jms.domain.Config;
import com.jms.domain.GroupTypeEnum;
import com.jms.domain.SandVikRoleEnum;
import com.jms.domain.SystemRoleEnum;
import com.jms.domain.db.Company;
import com.jms.domain.db.Groups;
import com.jms.domain.db.Roles;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.MessageTypeEnum;
import com.jms.domain.ws.WSUser;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.system.SysDicDRepository;
import com.jms.repositories.user.RoleRepository;

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
			usersRepository.save(dbUser);
			wsUser.setIdUser(dbUser.getIdUser());
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
		
		
		Groups g_op = groupService.createGroup(c, GroupTypeEnum.Group, SandVikRoleEnum.OP.name(), "操作员", null);
		Groups g_quality =  groupService.createGroup(c, GroupTypeEnum.Group, SandVikRoleEnum.quality.name(), "质检员", null);
		Groups g_supervisor =  groupService.createGroup(c, GroupTypeEnum.Group, SandVikRoleEnum.supervisor.name(), "主管", null);
		Groups g_warehouse =  groupService.createGroup(c, GroupTypeEnum.Group, SandVikRoleEnum.warehouse.name(), "主管", null);
		Groups g_equipment = groupService.createGroup(c, GroupTypeEnum.Group, SandVikRoleEnum.equipment.name(), "仪器维护员", null);
		
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
	
	
	
	
	
}
