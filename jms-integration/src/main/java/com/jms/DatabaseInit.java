package com.jms;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.Company;
import com.jms.domain.db.Users;
import com.jms.repositories.user.UsersRepository;
import com.jms.service.company.CompanyService;
import com.jms.service.system.AppsService;
import com.jms.service.system.DicService;
import com.jms.service.system.DistrictService;
import com.jms.service.user.GroupService;
import com.jms.service.user.GroupTypeService;
import com.jms.service.user.RoleService;
import com.jms.service.user.UserService;
import com.jms.service.workmanagement.ProjectService;
import com.jms.web.security.JMSUserDetails;

@Service
@Transactional
public class DatabaseInit {

	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private DistrictService districtService;
	@Autowired
	private DicService dicService;
	@Autowired
	private GroupTypeService groupTypeService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private AppsService appsService;
	@Autowired
	private UserDetailsService userDetailService;
	@Autowired
	private AuthenticationManager authenticationManager;

	// 在系统初装的执行切只能执行一次，读取csv文件的数据到数据库中。
	// todo:详细说明系统预设的所有信息这些信息的用途
	public void init(ConfigurableApplicationContext ctx) throws IOException {
		if (usersRepository.findByUsername("admin") != null)
			return;


		dicService.loadDics();
		groupTypeService.loadGroupTypes();
		userService.createDefaultUsers();
	
		
		Resource provinceRes = ctx.getResource("classpath:data/province.csv");
		Resource cityRes = ctx.getResource("classpath:data/city.csv");
		Resource districtRes = ctx.getResource("classpath:data/district.csv");
		districtService.loadProvicesFromCSV(provinceRes.getInputStream());
		districtService.loadCitiesFromCSV(cityRes.getInputStream());
		districtService.loadDistrictsFromCSV(districtRes.getInputStream());

		UserDetails userDetails = userDetailService.loadUserByUsername(""+usersRepository.findByUsername("admin").getIdUser());
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				userDetails.getUsername(), userDetails.getPassword());
		Authentication authenticated = authenticationManager
				.authenticate(authentication);
		SecurityContextHolder.getContext().setAuthentication(authenticated);
		
		Company templateCompany = companyService.createTemplateCompany();
		roleService.createDefaultRoles(templateCompany);
		
		groupService.createDefaultGroups(templateCompany);
		projectService.createGenerialProject(templateCompany);
	
		
		Resource appsRes = ctx.getResource("classpath:data/apps.csv");
		appsService.createInitalApps(appsRes.getInputStream(), templateCompany);
	

	}

}
