package com.jms;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.Company;
import com.jms.email.EmailSenderTest;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.service.company.CompanyService;
import com.jms.service.maintenance.MMachineGroupService;
import com.jms.service.maintenance.MMachineService;
import com.jms.service.maintenance.MStatusDicService;
import com.jms.service.production.EWorkCategoryDicService;
import com.jms.service.production.PPUTimeService;
import com.jms.service.production.PStatusDicService;
import com.jms.service.production.PStopsCodeService;
import com.jms.service.quality.ItemTypeService;
import com.jms.service.quality.TesterService;
import com.jms.service.store.CurrencyTypeService;
import com.jms.service.store.MaterialService;
import com.jms.service.store.MaterialTypeService;
import com.jms.service.store.SCountryDicService;
import com.jms.service.store.SGenderDicService;
import com.jms.service.store.SInventoryService;
import com.jms.service.store.SLevelDicService;
import com.jms.service.store.SMtfNoService;
import com.jms.service.store.SMtfTypeDicService;
import com.jms.service.store.SStatusDicService;
import com.jms.service.store.SStkService;
import com.jms.service.store.SStkTypeDicService;
import com.jms.service.store.STypeDicService;
import com.jms.service.store.SUnitDicService;
import com.jms.service.store.StermDicService;
import com.jms.service.store.YesOrNoService;
import com.jms.service.system.AppsService;
import com.jms.service.system.DicService;
import com.jms.service.system.DistrictService;
import com.jms.service.system.EventReceiverService;
import com.jms.service.system.JmsEventService;
import com.jms.service.system.NotiMethodService;
import com.jms.service.user.GroupService;
import com.jms.service.user.GroupTypeService;
import com.jms.service.user.RoleService;
import com.jms.service.user.UserService;
import com.jms.service.workmanagement.ProjectService;
import com.jms.system.INotificationService;

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
	
	@Autowired
	private NotiMethodService notiMethodService;
	@Autowired
	private JmsEventService jmsEventService;
	@Autowired
	private MaterialTypeService materialTypeService;
	@Autowired
	private SStatusDicService sStatusDicService;
	@Autowired
	private  PStatusDicService pStatusDicService;

	@Autowired
	private SCountryDicService sCountryDicService;
	
	@Autowired
	private SStkTypeDicService sStkTypeDicService;
	
	@Autowired
	private SStkService sStkService;
	
	@Autowired
	private YesOrNoService yesOrNoService;
	
	@Autowired
	private STypeDicService sTypeDicService;
	
	@Autowired
	private StermDicService stermDicService;
	
	@Autowired
	private  SLevelDicService sLevelDicService;
	
	@Autowired
	private SGenderDicService sGenderDicService;
	
	@Autowired
	private  CompanyRepository companyRepository;
	@Autowired
	private SUnitDicService sUnitDicService;
	@Autowired
	private CurrencyTypeService currencyTypeService;
	
	@Autowired
	private  SMtfTypeDicService sMtfTypeDicService;
	@Autowired
	private MMachineGroupService mMachineGroupService;
	@Autowired
	private MStatusDicService mStatusDicService;
	@Autowired
	private  MMachineService mMachineService;
	@Autowired private EWorkCategoryDicService eWorkCategoryDicService;
	
	@Autowired
	private  PPUTimeService pPUTimeService;
	@Autowired
	private  PStopsCodeService pStopsCodeService;
	@Autowired
	private  TesterService testerService;

	@Autowired
	private  ItemTypeService itemTypeService;
	//step 1 load materials
	@Autowired
	private   MaterialService materialService;
	
	
	@Autowired
	private SMtfNoService sMtfNoService;
	
	
	@Autowired
	private EventReceiverService eventReceiverService;
	
	@Autowired
	private EmailSenderTest emailSenderTest;
	@Autowired
	private SInventoryService sInventoryService;
	@Autowired
	private INotificationService notificationService;
	
	// 在系统初装的执行切只能执行一次，读取csv文件的数据到数据库中。
	// todo:详细说明系统预设的所有信息这些信息的用途
	public void init(ConfigurableApplicationContext ctx) throws IOException {
	/*	
		if (usersRepository.findByUsername("admin") != null)
			return;


		dicService.loadDics();
		groupTypeService.loadGroupTypes();
		userService.createDefaultUsers();
		notiMethodService.createNotiMethods();
		jmsEventService.createJmsEvents();
		
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
		
		
		materialTypeService.loadMaterilaTypies();
		Resource s_statuRes = ctx.getResource("classpath:data/s_status_dic.csv");
		sStatusDicService.loadStatus(s_statuRes.getInputStream());

	    Resource p_statuRes = ctx.getResource("classpath:data/p_status_dic.csv");
		pStatusDicService.loadStatus(p_statuRes.getInputStream());
		
		Resource countriesRes = ctx.getResource("classpath:data/s_country_dic.csv");
		sCountryDicService.loadCountries(countriesRes.getInputStream());
		sStkTypeDicService.loadStkTypes();
		yesOrNoService.loadSYesOrNoDics();
		
	
		sTypeDicService.loadSTypes();
		stermDicService.loadStermDics();
		sLevelDicService.loadLevels();
		
		sGenderDicService.loadGenders();
		sUnitDicService.loadUnits();
		  currencyTypeService.loadCurrencyTypies();
		*/
		
		/*
		UserDetails userDetails = userDetailService.loadUserByUsername(""+usersRepository.findByUsername("admin").getIdUser());
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				userDetails.getUsername(), userDetails.getPassword());
		Authentication authenticated = authenticationManager
				.authenticate(authentication);
		SecurityContextHolder.getContext().setAuthentication(authenticated);
		userService.createTestUsersforSandVik();
	    sMtfTypeDicService.loadMtfTypes();
		*/
		/*
		mMachineGroupService.loadMMachineGroupForSandVik();
	     */
		
        
//	    Resource m_statuRes = ctx.getResource("classpath:data/m_status_dic.csv");
//		mStatusDicService.loadStatus(m_statuRes.getInputStream());

//		Resource machines = ctx.getResource("classpath:data/san_machine.csv");
//		mMachineService.loadMachinesForSandVik(machines.getInputStream(),8l);
		
		
		
		/*
		pPUTimeService.loadPuTimes();
	     */
		
		/*
		 * eWorkCategoryDicService.loadEWorkCategoryDicForSandVik();*/
		/*
		 
		UserDetails userDetails = userDetailService.loadUserByUsername(""+usersRepository.findByUsername("admin").getIdUser());
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				userDetails.getUsername(), userDetails.getPassword());
		Authentication authenticated = authenticationManager
				.authenticate(authentication);
		SecurityContextHolder.getContext().setAuthentication(authenticated);
		userService.createTestUsersforWWW();
		
		*/
		
		//pStopsCodeService.loadSubCodes();
		//testerService.loadTesters();
		
		//itemTypeService.loadItemTypes();
		
		/****导入公司数据***/
//	   Resource materialsFile = ctx.getResource("classpath:data/san_material.csv");
//	   materialService.loadMaterialsByCompanyId(materialsFile.getInputStream(), 8l);
		
		UserDetails userDetails = userDetailService.loadUserByUsername(""+usersRepository.findByUsername("18515510311").getIdUser());
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				userDetails.getUsername(), userDetails.getPassword());
		Authentication authenticated = authenticationManager
				.authenticate(authentication);
		SecurityContextHolder.getContext().setAuthentication(authenticated);
//		 Resource departmentsFile = ctx.getResource("classpath:data/san_dept.csv");
//		 groupService.loadDepartmentsByCompanyId(departmentsFile.getInputStream(), 8l);
//		 Resource usersFile = ctx.getResource("classpath:data/san_users.csv");
//		 userService.loadUserssByCompanyId(usersFile.getInputStream(), 8l);
		 
//		 Resource usersDeptFile = ctx.getResource("classpath:data/san_usersDept.csv");
		// groupService.loadDepartmentMembersByCompanyId(usersDeptFile.getInputStream(), 8l);
		
	    // Resource usersFile = ctx.getResource("classpath:data/san_stk.csv");
		 //sStkService.loadStksByCompanyId(usersFile.getInputStream(), 8l);

	
		//sMtfNoService.loadSmtfNosByCompanyId(8l);
//		 Resource receiversFile = ctx.getResource("classpath:data/san_receivers.csv");
//		 eventReceiverService.loadReceivers(receiversFile.getInputStream(), 8l);
//	try {
//		emailSenderTest.testSendEmail();
//	} catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
	
	
//	sInventoryService.test();
		
	//	companyService.addAppsPerms();
	}
	
	

	@Scheduled(fixedRate=1000*60*3)
	public void sendEmails() {
		notificationService.sendEmails();
		
	}

}
