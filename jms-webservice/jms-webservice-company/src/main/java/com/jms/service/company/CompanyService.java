package com.jms.service.company;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.acl.SecuredObjectService;
import com.jms.acl.SecurityACLDAO;
import com.jms.domain.Config;
import com.jms.domain.EnabledEnum;
import com.jms.domain.EventTypeEnum;
import com.jms.domain.GroupTypeEnum;
import com.jms.domain.NotificationMethodEnum;
import com.jms.domain.SystemRoleEnum;
import com.jms.domain.db.Apps;
import com.jms.domain.db.Company;
import com.jms.domain.db.GroupMembers;
import com.jms.domain.db.GroupMembersId;
import com.jms.domain.db.Groups;
import com.jms.domain.db.WProject;
import com.jms.domain.db.Roles;
import com.jms.domain.db.SStk;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.MessageTypeEnum;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSCompany;
import com.jms.domain.ws.WSUser;
import com.jms.domainadapter.CompanyAdapter;
import com.jms.domainadapter.UserAdapter;
import com.jms.messages.MessagesUitl;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.system.AppsRepository;
import com.jms.repositories.system.SysDicDRepository;
import com.jms.repositories.user.GroupMemberRepository;
import com.jms.repositories.user.GroupRepository;
import com.jms.repositories.user.GroupTypeRepository;
import com.jms.repositories.user.RoleRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.repositories.workmanagement.ProjectRepository;
import com.jms.s.ISBinService;
import com.jms.s.ISmtfNoService;
import com.jms.repositories.s.SStkRepository;
import com.jms.system.INotificationService;
import com.jms.user.IUserService;
import com.jms.web.security.JMSUserDetails;
import com.jms.web.security.SecurityUtils;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private ResourceBundleMessageSource source;
	@Autowired
	private MessagesUitl messagesUitl;
	@Autowired
	private UserAdapter userAdapter;
	@Autowired
	private CompanyAdapter companyAdapter;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	@Qualifier("iUserServiceImpl")
	private IUserService iUserServiceImpl;
	@Autowired
	private SecurityUtils securityUtils;
	@Autowired
	private SysDicDRepository sysDicDRepository;
	@Autowired
	private SecurityACLDAO securityACLDAO;
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private GroupMemberRepository groupMemberRepository;
	@Autowired
	protected GroupTypeRepository groupTypeRepository;
	@Autowired
	private SecuredObjectService securedObjectService;
	@Autowired
	private AppsRepository appsRepository;
	@Autowired
	private SStkRepository sStkRepository;
	
	@Autowired
	private ISmtfNoService sMtfNoService;
	
	@Autowired
	private ISBinService sBinService;
	
	
	@Autowired
	private INotificationService notificationService;

	private static final Logger logger = LogManager
			.getLogger(CompanyService.class.getCanonicalName());

	@Transactional(readOnly = true)
	public Company findCompany() {
	
		//System.out.println("find company: " + );
		Company company = securityUtils.getCurrentDBUser().getCompany();
	//	System.out.println("find company: " +company.getCompanyName() );
		if (company.getEnabled() == EnabledEnum.DISENABLED.getStatusCode())
			return null;
		return company;
	}


	@Transactional(readOnly = false)
	public Boolean registCompany(WSCompany wsCompany) throws Exception {
		
		Boolean valid = checkCompanyName(wsCompany.getCompanyName(), null);
		if (!valid)
			return false;
		Boolean userValid = iUserServiceImpl.checkLogin(wsCompany.getWsUsers()
				.getUsername(), wsCompany.getWsUsers().getEmail(), wsCompany
				.getWsUsers().getMobile(), null);
		if (!userValid)
			return false;
		//System.out.println("注册公司： " + wsCompany.getCompanyName() +", username: " + wsCompany.getWsUsers().getUsername());
		Users dbUser = userAdapter.toDBUser(wsCompany.getWsUsers(), null);
		iUserServiceImpl.register(dbUser);
		wsCompany.setVerified(0l);
		Company company = companyAdapter.toDBCompany(wsCompany, null);
		company.setUser(dbUser);
		company.setUsersByCreator(dbUser);
		company.setCreationTime(new Date());
		company = companyRepository.save(company);
		dbUser.setCompany(company);
		usersRepository.save(dbUser);

		WSUser wsUser = new WSUser();
		wsUser.setIdUser(dbUser.getIdUser());
		wsUser.setUsername("" + dbUser.getIdUser());
		JMSUserDetails userDetails = new JMSUserDetails(wsUser);
		List<GrantedAuthority> l = new ArrayList<GrantedAuthority>();
		l.add(new GrantedAuthority() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getAuthority() {
				return "admin";
			}
		});
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				userDetails, "***********", l);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		securityACLDAO.addPermission(company, Company.class,
				BasePermission.ADMINISTRATION);
		
		// todo: find template company by some rules!!
		Company templateCompany = companyRepository.findByCompanyName("企业模版");
		copyDataBetweenCompanies(templateCompany, company);
		
		
		sMtfNoService.loadSmtfNosByCompanyId(company.getIdCompany());
		sBinService.loadSystemBins(company.getIdCompany());
		return true;

	}

	@Transactional(readOnly = false)
	public Boolean updateCompany(WSCompany wsCompany) throws Exception {
		Long idCompamplany = wsCompany.getIdCompany();
		Company company = companyRepository.findOne(idCompamplany);
		Boolean valid = checkCompanyName(wsCompany.getCompanyName(),
				idCompamplany);
		if (!valid)
			return false;
		company = companyAdapter.toDBCompany(wsCompany, company);
		companyRepository.save(company);
		return true;

	}

	public Boolean checkCompanyName(String companyName, Long idCompany) {

		
		String dbCompanyName = "";
		// 已有公司修改
		if (idCompany != null) {
			Company company = companyRepository.findOne(idCompany);
			dbCompanyName = company.getCompanyName();
			if (companyName != null && !companyName.isEmpty()) {
				if (companyRepository.findByCompanyName(companyName) == null
						|| companyName.equals(dbCompanyName)) {
					return true;
				} else {
					return false;
				}
			} else
				return true;
		} else {
			if (companyName == null || companyName.isEmpty())
				return false;
			else {
				if (companyRepository.findByCompanyName(companyName) == null) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	@Transactional(readOnly = false)
	public Message cancelCompany(int idCompany) {
		logger.debug("user name: " + securityUtils.getUsername());
		String login = securityUtils.getUsername();
		Users u = usersRepository.findByUsernameOrEmailOrMobile(login);
		if (u == null)
			return messagesUitl.getMessage("nosuchuser", null,
					MessageTypeEnum.ERROR);
		Company company = u.getCompany();
		if (u != null && company != null && company.getIdCompany() == idCompany) {
			company.setEnabled(EnabledEnum.DISENABLED.getStatusCode());
			companyRepository.save(company);
			return messagesUitl.getMessage("company.cancel.success", null,
					MessageTypeEnum.INFOMATION);

		}

		return messagesUitl.getMessage("company.cancel.failure", null,
				MessageTypeEnum.ERROR);
	}

	@Transactional(readOnly = false)
	private void copyDataBetweenCompanies(Company from, Company to) {

	
		// copy roles
		for (Roles r : from.getRoleses()) {
			Roles r1 = new Roles();
			r1.setRole(r.getRole());
			r1.setDescription(r.getDescription());
			r1.setCompany(to);
			r1.setLevel(r.getLevel());
			roleRepository.save(r1);
			
			Groups g = new Groups();
			g.setCompany(to);
			g.setGroupName(""+r.getRole());
			g.setDescription(r.getDescription());
			g.setGroupType(groupTypeRepository.findOne(5l));
			groupRepository.save(g);
			
			GroupMembers gm1 = new GroupMembers();
			GroupMembersId id1 = new GroupMembersId();
			System.out.println("admin group: " +g.getIdGroup() );
			id1.setIdGroup(g.getIdGroup());
			id1.setIdUser(securityUtils.getCurrentDBUser().getIdUser());
			gm1.setId(id1);
			groupMemberRepository.save(gm1);
		}

		
		Users creator = securityUtils.getCurrentDBUser();
		Long companyGroupId = -1l;

		for (Groups g : from.getGroupses()) {
			if (g.getGroupType().getGroupType().equals("User"))
				continue;
			Groups g1 = new Groups();
			g1.setCompany(to);
			g1.setCreationTime(new Date());
			g1.setGroupName(g.getGroupName());
			g1.setGroupType(g.getGroupType());
			g1.setDescription(g.getDescription());
			g1.setUsers(creator);
			//System.out.println("create group: " + g.getGroupName());
			groupRepository.save(g1);
			List<Long> idGroups = new ArrayList<Long>();
			idGroups.add(g1.getIdGroup());
			notificationService.createNotification(to, g1, EventTypeEnum.create, NotificationMethodEnum.sys, idGroups);
			if (g.getGroupType().getGroupType()
					.equals(GroupTypeEnum.Company.name())) {
				companyGroupId = g1.getIdGroup();
				GroupMembers gm1 = new GroupMembers();
				GroupMembersId id1 = new GroupMembersId();
				id1.setIdGroup(g1.getIdGroup());
				id1.setIdUser(securityUtils.getCurrentDBUser().getIdUser());
				gm1.setId(id1);
				gm1.setRoles(roleRepository.findByRoleAndCompanyName(
						SystemRoleEnum.admin.name(), from.getCompanyName()));
				groupMemberRepository.save(gm1);
			}
		}

		Groups himself = new Groups();
		himself.setCompany(to);
		himself.setCreationTime(new Date());
		himself.setGroupName("" + creator.getIdUser());
		himself.setGroupType(groupTypeRepository
				.findByGroupType(GroupTypeEnum.User.name()));
		himself.setDescription("" + creator.getIdUser());
		himself.setUsers(creator);
		groupRepository.save(himself);
		GroupMembers gm = new GroupMembers();
		GroupMembersId id = new GroupMembersId();
		id.setIdGroup(himself.getIdGroup());
		id.setIdUser(securityUtils.getCurrentDBUser().getIdUser());
		gm.setId(id);
		gm.setRoles(roleRepository.findByRoleAndCompanyName(
				SystemRoleEnum.user.name(), from.getCompanyName()));
		groupMemberRepository.save(gm);
		
		Groups group = groupRepository.findGroupByGroupNameAndCompany(
				"全公司", to.getIdCompany(), GroupTypeEnum.Company.name());
		List<Long> idGroups = new ArrayList<Long>();
		idGroups.add(group.getIdGroup());
		// copy projects
		for (WProject p : projectRepository.findByCompany(from)) {
			WProject p1 = new WProject();
			p1.setCompany(to);
			p1.setProjectName(p.getProjectName());
			p1.setDescription(p.getDescription());
			p1.setSysDicDByStatus(p.getSysDicDByStatus());
			p1.setUsers(securityUtils.getCurrentDBUser());
			projectRepository.save(p1);
			securityACLDAO.addPermission(p1, WProject.class,
					BasePermission.ADMINISTRATION);
			GrantedAuthoritySid sid = new GrantedAuthoritySid(""
					+ group.getIdGroup());
			securityACLDAO.addPermission(p1, sid, BasePermission.READ);
			notificationService.createNotification(to, p, EventTypeEnum.create, NotificationMethodEnum.sys, idGroups);

		}
		List<Apps> appList = appsRepository.findInvs();
		//List<Apps> appList = appsRepository.findAll();
		Users admin = from.getUsersByCreator();
		// copy apps
//	Map<Apps, String> smap = securedObjectService
//				.getSecuredObjectsWithPermissions(admin, appList);
//
    	PrincipalSid pid = new PrincipalSid("" + creator.getIdUser());
//		// logger.debug("creator id: " + creator.getIdUser());
		for (Apps a : appList) {
			securityACLDAO.addPermission(a, pid, BasePermission.ADMINISTRATION);
		}

		Users normalUser = usersRepository.findByUsername("user");
		GrantedAuthoritySid sid = new GrantedAuthoritySid("" + companyGroupId);
//		Map<Apps, String> smap1 = securedObjectService
//				.getSecuredObjectsWithPermissions(normalUser, appList);

		for (Apps a : appList) {
			securityACLDAO.addPermission(a, sid, BasePermission.READ);
		}
		
	
		notificationService.createNotification(to, to, EventTypeEnum.create, NotificationMethodEnum.sys, idGroups);
	}

	@Transactional(readOnly = false)
	public Company createTemplateCompany() {
		Company templateCompany = new Company();
		templateCompany.setCompanyName("企业模版");
		templateCompany.setDescription("企业模版");
		templateCompany.setCreationTime(new Date());
		templateCompany.setEnabled(EnabledEnum.ROBOT.getStatusCode());
		templateCompany.setSysDicDByTaskType(sysDicDRepository.findDicsByType(
				Config.taskType).get(0));
		templateCompany.setSysDicDByCompanyCategory(sysDicDRepository
				.findDicsByType(Config.companyCatergory).get(0));
		templateCompany.setUsersByCreator(usersRepository
				.findByUsername("admin"));
		companyRepository.save(templateCompany);
		Users user = usersRepository.findByUsername("user");
		user.setCompany(templateCompany);
		usersRepository.save(user);
		templateCompany.setUsersByCreator(user);
		return templateCompany;
	}
	
	@Transactional(readOnly = false)
	public Boolean createSTK(SStk sStk) {
		sStkRepository.save(sStk);
		return true;
	}
	
	public void addAppsPerms()
	{
		List<Apps> appList = appsRepository.findAll();
		Users admin = securityUtils.getCurrentDBUser();
		PrincipalSid pid = new PrincipalSid("" + admin.getIdUser());
		// logger.debug("creator id: " + creator.getIdUser());
		for (Apps a : appList) {
			securityACLDAO.addPermission(a, pid, BasePermission.ADMINISTRATION);
		}
	}

}