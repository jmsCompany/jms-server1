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
import com.jms.service.system.DicService;
import com.jms.service.system.DistrictService;
import com.jms.service.system.ModuleService;
import com.jms.service.user.GroupTypeService;
import com.jms.service.user.RolePrivService;
import com.jms.service.user.RoleService;
import com.jms.service.user.UserService;
import com.jms.service.workmanagement.ProjectService;

@Service
@Transactional
public class DatabaseInit {

	@Autowired private UsersRepository usersRepository;
	@Autowired @Qualifier("userService") private UserService userService;
	@Autowired private RoleService roleService;
	@Autowired private SectorService sectorService;
	@Autowired private ModuleService moduleService;
	@Autowired private CompanyService companyService;
	@Autowired private RolePrivService rolePrivService;
	@Autowired private ProjectService  projectService;
	@Autowired private DistrictService districtService;
	@Autowired private DicService dicService;
	@Autowired private GroupTypeService groupTypeService;

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
   	     Resource provinceRes = ctx.getResource("classpath:data/province.csv");
   	     Resource cityRes = ctx.getResource("classpath:data/city.csv");
         Resource districtRes = ctx.getResource("classpath:data/district.csv");
         
         dicService.loadDics();
         groupTypeService.loadGroupTypes();
         
		 userService.loadUsersFromCSV(userRes.getInputStream());
		 companyService.loadCompaniesFromCSV(companyRes.getInputStream());
		 roleService.loadRolesFromCSV(roleRes.getInputStream());
		 sectorService.loadSectorsFromCSV(sectorRes.getInputStream());
		 moduleService.loadModulesFromCSV(moudleRes.getInputStream());
		 rolePrivService.loadRolesPrivFromCSV(rolePrivRes.getInputStream());

		 projectService.createGenerialProject();
		  
	     districtService.loadProvicesFromCSV(provinceRes.getInputStream());
	     districtService.loadCitiesFromCSV(cityRes.getInputStream());
         districtService.loadDistrictsFromCSV(districtRes.getInputStream());
		 
	}

}
