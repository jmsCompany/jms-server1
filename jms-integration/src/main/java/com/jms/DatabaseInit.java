package com.jms;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.repositories.user.UsersRepository;
import com.jms.service.company.CompanyService;
import com.jms.service.company.SectorService;
import com.jms.service.user.ModuleService;
import com.jms.service.user.RolePrivService;
import com.jms.service.user.RoleService;
import com.jms.service.user.UserService;
import com.jms.service.workmanagement.ProjectService;


@Service
@Transactional
public class DatabaseInit {

	@Autowired private UsersRepository usersRepository;
	@Qualifier private UserService userService;
	@Autowired private RoleService roleService;
	@Autowired private SectorService sectorService;
	@Autowired private ModuleService moduleService;
	@Autowired private CompanyService companyService;
	@Autowired private RolePrivService rolePrivService;
	@Autowired private ProjectService  projectService;

	//在系统初装的执行切只能执行一次，读取csv文件的数据到数据库中。
	//todo:详细说明系统预设的所有信息，已经这些信息的用途
	public void init(ConfigurableApplicationContext ctx) throws IOException
	{
		if(usersRepository.findByUsername("system")!=null)
			return;
   	     Resource userRes = ctx.getResource("classpath:data/users.csv");
   	     Resource companyRes = ctx.getResource("classpath:data/company.csv");
   	     Resource roleRes=  ctx.getResource("classpath:data/roles.csv");
   	     Resource sectorRes=  ctx.getResource("classpath:data/sectors.csv");
   	     Resource moudleRes=  ctx.getResource("classpath:data/modules.csv");
   	     Resource rolePrivRes=  ctx.getResource("classpath:data/role_priv.csv");
  
		 userService.loadUsersFromCSV(userRes.getFile().getAbsolutePath());
		 companyService.loadCompaniesFromCSV(companyRes.getFile().getAbsolutePath());
		 roleService.loadRolesFromCSV(roleRes.getFile().getAbsolutePath());
		 sectorService.loadSectorsFromCSV(sectorRes.getFile().getAbsolutePath());
		 moduleService.loadModulesFromCSV(moudleRes.getFile().getAbsolutePath());
		 rolePrivService.loadRolesPrivFromCSV(rolePrivRes.getFile().getAbsolutePath());
		 projectService.createGenerialProject();
	
	}

}
