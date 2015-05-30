package com.jms;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.service.company.CompanyService;
import com.jms.service.user.ModuleService;
import com.jms.service.user.RolePrivService;
import com.jms.service.user.RoleService;
import com.jms.service.user.SectorService;
import com.jms.service.user.UserService;


@Service
@Transactional
public class InitDataBase {
	
	@Autowired private UserService userService;
	@Autowired private RoleService roleService;
	@Autowired private SectorService sectorService;
	@Autowired private ModuleService moduleService;
	@Autowired private CompanyService companyService;
	@Autowired private RolePrivService rolePrivService;
	
	public void init(ConfigurableApplicationContext ctx) throws IOException
	{
   	     Resource userRes = ctx.getResource("classpath:data/users.csv");
   	     Resource roleRes=  ctx.getResource("classpath:data/roles.csv");
   	     Resource sectorRes=  ctx.getResource("classpath:data/sectors.csv");
   	     Resource moudleRes=  ctx.getResource("classpath:data/modules.csv");
   	     Resource rolePrivRes=  ctx.getResource("classpath:data/role_priv.csv");
     
		 userService.loadUsersFromCSV(userRes.getFile().getAbsolutePath());
   	     companyService.createTemplateCompany();
		 roleService.loadRolesFromCSV(roleRes.getFile().getAbsolutePath());
		 sectorService.loadSectorsFromCSV(sectorRes.getFile().getAbsolutePath());
		 moduleService.loadModulesFromCSV(moudleRes.getFile().getAbsolutePath());
		 rolePrivService.loadRolesPrivFromCSV(rolePrivRes.getFile().getAbsolutePath());
		 
		 //todo: add a general project for the template company!
	}

}
