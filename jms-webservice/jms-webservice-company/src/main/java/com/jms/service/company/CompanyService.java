package com.jms.service.company;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.acl.SecurityACLDAO;
import com.jms.domain.Config;
import com.jms.domain.EnabledEnum;
import com.jms.domain.GroupTypeEnum;
import com.jms.domain.db.Company;
import com.jms.domain.db.GroupMembers;
import com.jms.domain.db.GroupMembersId;
import com.jms.domain.db.Groups;
import com.jms.domain.db.Project;
import com.jms.domain.db.Roles;
import com.jms.domain.db.Sector;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.MessageTypeEnum;
import com.jms.domain.ws.WSCompany;
import com.jms.domain.ws.WSUser;
import com.jms.domainadapter.CompanyAdapter;
import com.jms.domainadapter.SectorAdapter;
import com.jms.domainadapter.UserAdapter;
import com.jms.messages.MessagesUitl;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.company.SectorsRepository;
import com.jms.repositories.system.SysDicDRepository;
import com.jms.repositories.user.GroupMemberRepository;
import com.jms.repositories.user.GroupRepository;
import com.jms.repositories.user.GroupTypeRepository;
import com.jms.repositories.user.RoleRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.repositories.workmanagement.ProjectRepository;
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
	private SectorsRepository sectorsRepository;
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
	private SectorAdapter sectorAdapter;

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
	private static final Logger logger = LogManager
			.getLogger(CompanyService.class.getCanonicalName());

	@Transactional(readOnly = true)
	public Company findCompanyById(Long idCompany) {
		Company company = companyRepository.findOne(idCompany);
		if (company.getEnabled() == EnabledEnum.DISENABLED.getStatusCode())
			return null;
		return company;
	}

	@Transactional(readOnly = true)
	public Company findCompanyByLogin(String login) {
		Users u = usersRepository.findByUsernameOrEmailOrMobile(login);
		if (u == null)
			return null;
		Company company = u.getCompany();
		if (company.getEnabled() == EnabledEnum.DISENABLED.getStatusCode())
			return null;
		return company;

	}

	@Transactional(readOnly = false)
	public Message registCompany(WSCompany wsCompany) throws Exception {
		Message message = checkCompanyName(wsCompany.getCompanyName(), null);
		if (message.getMessageTypeEnum().equals(MessageTypeEnum.ERROR))
			return message;
		message = iUserServiceImpl.checkLogin(wsCompany.getWsUsers()
				.getUsername(), wsCompany.getWsUsers().getEmail(), wsCompany
				.getWsUsers().getMobile(), null);
		if (message.getMessageTypeEnum().equals(MessageTypeEnum.ERROR))
			return message;
		Users dbUser = userAdapter.toDBUser(wsCompany.getWsUsers(), null);
		iUserServiceImpl.register(dbUser);
		wsCompany.setVerified(0l);
		Company company = companyAdapter.toDBCompany(wsCompany, null);
		company.setUser(dbUser);
		company.setUsersByCreator(dbUser);
		company.setCreationTime(new Date());
		companyRepository.save(company);
		dbUser.setCompany(company);
		usersRepository.save(dbUser);
	    
		WSUser wsUser = new WSUser();
		wsUser.setIdUser(dbUser.getIdUser());
		wsUser.setUsername(""+dbUser.getIdUser());
		JMSUserDetails userDetails = new JMSUserDetails(wsUser);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				userDetails, "***********",
				null);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		securityACLDAO.addPermission(company, Company.class, BasePermission.ADMINISTRATION);
		// todo: find template company by some rules!!
		Company templateCompany = companyRepository
				.findByCompanyName("企业模版");
		copyDataBetweenCompanies(templateCompany, company);
		return messagesUitl.getMessage("company.success", null,
				MessageTypeEnum.INFOMATION);

	}

	@Transactional(readOnly = false)
	public Message updateCompany(WSCompany wsCompany) throws Exception {
		Long idCompamplany = wsCompany.getIdCompany();
		Company company = companyRepository.findOne(idCompamplany);
		Message message = checkCompanyName(wsCompany.getCompanyName(),
				idCompamplany);
		if (message.getMessageTypeEnum().equals(MessageTypeEnum.ERROR))
			return message;
		company = companyAdapter.toDBCompany(wsCompany, company);
		companyRepository.save(company);
		return messagesUitl.getMessage("company.update.success", null,
				MessageTypeEnum.INFOMATION);

	}

	public Message checkCompanyName(String companyName, Long idCompany) {

		String dbCompanyName = "";
		//已有公司修改
		if (idCompany != null) {
			Company company = companyRepository.findOne(idCompany);
			dbCompanyName = company.getCompanyName();
			if(companyName != null &&!companyName.isEmpty())
			{
				if(companyRepository.findByCompanyName(companyName) == null||companyName.equals(dbCompanyName))
				{
					return messagesUitl.getMessage("company.name.available", null,
							MessageTypeEnum.INFOMATION);
				}
				else
				{
					return messagesUitl.getMessage("company.alreadyexist", null,
							MessageTypeEnum.ERROR);
				}
			}
			else
				return messagesUitl.getMessage("company.name.available", null,
						MessageTypeEnum.INFOMATION);
		}
		else
		{
			if (companyName == null || companyName.isEmpty())
				return messagesUitl.getMessage("company.name.required", null,
						MessageTypeEnum.ERROR);
			else
			{
				if (companyRepository.findByCompanyName(companyName) == null) {
					return messagesUitl.getMessage("company.name.available", null,
							MessageTypeEnum.INFOMATION);
				} 
				else
				{
					return messagesUitl.getMessage("company.alreadyexist", null,
							MessageTypeEnum.ERROR);
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

		// copy sectors
		for (Sector s : from.getSectors()) {
			Sector s1 = new Sector();
			s1.setCompany(to);
			s1.setDescription(s.getDescription());
			s1.setSector(s.getSector());
			s1.setSeq(s.getSeq());
			s1.setEnabled(s.getEnabled());
			sectorsRepository.save(s1);
		}
		// copy roles
		for (Roles r : from.getRoleses()) {
			Roles r1 = new Roles();
			r1.setRole(r.getRole());
			r1.setDescription(r.getDescription());
			r1.setCompany(to);
			r1.setLevel(r.getLevel());
			roleRepository.save(r1);
		}
		//todo: copy groups
		
		for(Groups g:from.getGroupses())
		{
			if(g.getGroupType().getGroupType().equals(GroupTypeEnum.Sector.name()))
			{
				Groups g1 = new Groups();
				g1.setCompany(to);
				g1.setCreationTime(new Date());
				g1.setGroupName(g.getGroupName());
				g1.setGroupType(g.getGroupType());
				g1.setDescription(g.getDescription());
				g1.setUsers(securityUtils.getCurrentDBUser());
				groupRepository.save(g1);
			}
			else if(g.getGroupType().getGroupType().equals(GroupTypeEnum.Company.name()))
			{
				Groups g1 = new Groups();
				g1.setCompany(to);
				g1.setCreationTime(new Date());
				g1.setGroupName(g.getGroupName());
				g1.setGroupType(g.getGroupType());
				g1.setDescription(g.getDescription());
				g1.setUsers(securityUtils.getCurrentDBUser());
				groupRepository.save(g1);
				  GroupMembers gm1 = new GroupMembers(); 
				    GroupMembersId id1 = new GroupMembersId();
				    id1.setIdGroup(g.getIdGroup());
				    id1.setIdUser(securityUtils.getCurrentDBUser().getIdUser());
				    gm1.setId(id1);
				    gm1.setRoles(roleRepository.findByRoleAndCompanyName("user", to.getCompanyName()));
				    groupMemberRepository.save(gm1);
			}
			else
			{
				   Users dbUser=securityUtils.getCurrentDBUser();
					Groups g1 = new Groups();
					g1.setCompany(to);
					g1.setUsers(dbUser);
					g1.setCreationTime(new Date());
					g1.setGroupName(""+dbUser.getIdUser());
					g1.setDescription(dbUser.getName());
				    g1.setGroupType(groupTypeRepository.findByGroupType(GroupTypeEnum.User.name()));
				    groupRepository.save(g1);
				    
				    GroupMembers gm1 = new GroupMembers(); 
				    GroupMembersId id1 = new GroupMembersId();
				    id1.setIdGroup(g1.getIdGroup());
				    id1.setIdUser(dbUser.getIdUser());
				    gm1.setId(id1);
				    gm1.setRoles(roleRepository.findByRoleAndCompanyName("admin", to.getCompanyName()));
				    groupMemberRepository.save(gm1);
			}
		
		}
		
		//System.out.println("projects: "+from.getProjects().size());
		// copy projects
		for (Project p : projectRepository.findByCompany(from)) {
			Project p1 = new Project();
			p1.setCompany(to);
			p1.setProjectName(p.getProjectName());
			p1.setDescription(p.getDescription());
			p1.setSysDicDByStatus(p.getSysDicDByStatus());
			p1.setUsers(securityUtils.getCurrentDBUser());
			projectRepository.save(p1);
			securityACLDAO.addPermission(p1, Project.class, BasePermission.ADMINISTRATION);
			Groups  group= groupRepository.findGroupByGroupNameAndCompany("全公司", to.getIdCompany(), GroupTypeEnum.Company.name());
			GrantedAuthoritySid sid = new GrantedAuthoritySid(""+group.getIdGroup());
			securityACLDAO.addPermission(p1, sid, BasePermission.READ);
		}
	}
/*
	@Transactional(readOnly = false)
	public void loadCompaniesFromCSV(InputStream inputStream) throws IOException {
		CsvReader reader = new CsvReader(inputStream, ',',
				Charset.forName("UTF-8"));
		reader.readHeaders(); // CompanyCatergory (NORMAL_COMPANY(0),
								// SYSTEM_COMPANY(1),
								// TEMPLATE_COMPANY(2)),CompanyName,Description
		while (reader.readRecord()) {
			Company templateCompany = new Company();
			templateCompany.setCompanyName(reader.get("CompanyName"));
			templateCompany.setDescription(reader.get("Description"));
			templateCompany.setCreationTime(new Date());
			templateCompany.setUser(usersRepository.findByUsername("system"));
			templateCompany.setEnabled(EnabledEnum.ROBOT.getStatusCode());

			templateCompany.setSysDicDByTaskType(sysDicDRepository
					.findDicsByType(Config.taskType).get(0));
			templateCompany.setSysDicDByCompanyCatorgory(sysDicDRepository
					.findDicsByType(Config.companyCatergory).get(0));
			templateCompany.setUsersByCreator(usersRepository.findByUsername("system"));
			companyRepository.save(templateCompany);

		}
	}
	
	*/
	@Transactional(readOnly = false)
	public Company createTemplateCompany()
	{
		Company templateCompany = new Company();
		templateCompany.setCompanyName("企业模版");
		templateCompany.setDescription("企业模版");
		templateCompany.setCreationTime(new Date());
		templateCompany.setEnabled(EnabledEnum.ROBOT.getStatusCode());

		templateCompany.setSysDicDByTaskType(sysDicDRepository
				.findDicsByType(Config.taskType).get(0));
		templateCompany.setSysDicDByCompanyCatorgory(sysDicDRepository
				.findDicsByType(Config.companyCatergory).get(0));
		templateCompany.setUsersByCreator(usersRepository.findByUsername("admin"));
		companyRepository.save(templateCompany);
		return templateCompany;
	}
	
}