package com.jms.service.company;


import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.domain.EnabledEnum;
import com.jms.domain.FineTaskEnum;
import com.jms.domain.SystemUser;
import com.jms.domain.db.Company;
import com.jms.domain.db.Project;
import com.jms.domain.db.RolePriv;
import com.jms.domain.db.RolePrivId;
import com.jms.domain.db.Roles;
import com.jms.domain.db.Sector;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.MessageTypeEnum;
import com.jms.domain.ws.WSCompany;
import com.jms.domain.ws.WSSector;
import com.jms.domainadapter.CompanyAdapter;
import com.jms.domainadapter.SectorAdapter;
import com.jms.domainadapter.UserAdapter;
import com.jms.messages.MessagesUitl;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.company.SectorsRepository;
import com.jms.repositories.user.RolePrivRepository;
import com.jms.repositories.user.RoleRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.repositories.workmanagement.ProjectRepository;
import com.jms.user.IUserService;
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
	@Autowired @Qualifier("iUserServiceImpl")
	private IUserService iUserServiceImpl;
	@Autowired
	private RolePrivRepository rolePrivReposity;
	
	@Autowired
	private SectorAdapter sectorAdapter;
	private static final Logger logger = LogManager.getLogger(CompanyService.class.getCanonicalName());
	
	@Transactional(readOnly=true)
	public Company findCompanyById(int idCompany)
	{
		Company company= companyRepository.findOne(idCompany);
	    if(company.getEnabled()==EnabledEnum.DISENABLED.getStatusCode())
        	return null;
        return company;
	}
	@Transactional(readOnly=true)
	public Company findCompanyByIdUser(String idUser)
	{
		Users u = usersRepository.findByUsernameOrEmailOrMobile(idUser);
		if(u==null)
			return null;
        Company company = u.getCompany();
        if(company.getEnabled()==EnabledEnum.DISENABLED.getStatusCode())
        	return null;
        return company;
		
	}
	@Transactional(readOnly=false)
	public Message registCompany(WSCompany wsCompany) throws Exception
	{
		Message message = checkCompanyName(wsCompany.getCompanyName());
		if(message.getMessageTypeEnum().equals(MessageTypeEnum.ERROR))
			return message;
		message = iUserServiceImpl.checkLogin(wsCompany.getWsUsers().getUsername(),wsCompany.getWsUsers().getEmail(),wsCompany.getWsUsers().getMobile());
		 if(message.getMessageTypeEnum().equals(MessageTypeEnum.ERROR))
				return message;
		 Users dbUser= userAdapter.toDBUser(wsCompany.getWsUsers());
		 logger.debug("user id before :  " + dbUser.getIdUser());
		 iUserServiceImpl.register(dbUser);
		 logger.debug("user id after:  " + dbUser.getIdUser());
		 Company company = companyAdapter.toDBCompany(wsCompany);
		 company.setUsers(dbUser);
		 company.setCreationTime(new Date());
		 companyRepository.save(company);
		 dbUser.setCompany(company);
		 usersRepository.save(dbUser);
		 //todo: find template company by some rules!!
		 Company templateCompany= companyRepository.findByCompanyName("零售业企业模版");
		 copyDataBetweenCompanies(templateCompany,company);
		 return  messagesUitl.getMessage("company.success",null,MessageTypeEnum.INFOMATION);
		
	}		


	public Message checkCompanyName(String companyName) 
	{
		logger.debug("company name: " + companyName);
		if(companyName==null)
			return messagesUitl.getMessage("company.name.required",null,MessageTypeEnum.ERROR);
		if (companyRepository.findByCompanyName(companyName)== null) 
		{
			return messagesUitl.getMessage("company.name.available",null,MessageTypeEnum.INFOMATION);
		}
		else
		{
			return messagesUitl.getMessage("company.alreadyexist",null,MessageTypeEnum.ERROR);
		}
		
	}
	@Transactional(readOnly=false)
	public Message cancelCompany(int idCompany)
	{
		logger.debug("user name: " + SecurityUtils.getUsername());
		String login =  SecurityUtils.getUsername();
		Users u = usersRepository.findByUsernameOrEmailOrMobile(login);
		if(u==null)
			 return  messagesUitl.getMessage("nosuchuser",null,MessageTypeEnum.ERROR);
		Company company = u.getCompany();
		if(u!=null && company!=null && company.getIdCompany()==idCompany)
		{
			company.setEnabled(EnabledEnum.DISENABLED.getStatusCode());
			companyRepository.save(company);
			return messagesUitl.getMessage("company.cancel.success",null,MessageTypeEnum.INFOMATION);
					
		}
			
		return  messagesUitl.getMessage("company.cancel.failure",null,MessageTypeEnum.ERROR);
	}
	@Transactional(readOnly=false)
	private void copyDataBetweenCompanies(Company from,Company to)
	{
		//copy projects
		for(Project p: from.getProjects())
		{
			Project p1 = new Project();
			p1.setCompany(to);
			p1.setProjectName(p.getProjectName());
			p1.setDescription(p.getDescription());
	        p1.setEnabled(p.getEnabled());
			projectRepository.save(p1);
		}
		//copy sectors
		for(Sector s: from.getSectors())
		{
		  Sector s1 = new Sector();
		  s1.setCompany(to);
		  s1.setDescription(s.getDescription());
		  s1.setSector(s.getSector());
		  sectorsRepository.save(s1);
		}
	   //copy roles and privileges
        for(Roles r: from.getRoleses())
        {
        	Set<RolePriv> rpSet =r.getRolePrivs();
        	Roles r1 = new Roles();
        	r1.setRole(r.getRole());
        	r1.setDescription(r.getDescription());
        	r1.setCompany(to);
        	r1.setLevel(r.getLevel());
        	roleRepository.save(r1);
        	for(RolePriv rp: rpSet)
        	{
        		logger.debug("module id: " + rp.getModules().getIdModule()+" role id: " +r1.getIdRole());
        		RolePrivId id = new RolePrivId(rp.getModules().getIdModule(),r1.getIdRole()); 
        		RolePriv rp1 = new RolePriv();
        		rp1.setId(id);
        		rp1.setRoles(r1); 
        		rp1.setPriv(rp.getPriv());
        		rp1.setModules(rp.getModules());
        		rolePrivReposity.save(rp1);
        	}
        	
        }
   }
	@Transactional(readOnly=false)
	public void loadCompaniesFromCSV(String fileName) throws IOException
	{
		CsvReader reader = new CsvReader(fileName,',', Charset.forName("UTF-8"));
        reader.readHeaders();  //CompanyCatergory (NORMAL_COMPANY(0), SYSTEM_COMPANY(1), TEMPLATE_COMPANY(2)),CompanyName,Description
		while(reader.readRecord())
		{
			Company templateCompany = new Company();
			templateCompany.setCompanyName(reader.get("CompanyName"));
			templateCompany.setDescription(reader.get("Description"));
			templateCompany.setCreationTime(new Date());
			templateCompany.setUsers(usersRepository.findByUsername(SystemUser.System.toString()));
			templateCompany.setEnabled(EnabledEnum.ROBOT.getStatusCode());
			templateCompany.setFineTask(FineTaskEnum.NORMALTASK.getStatusCode());
			templateCompany.setCompanyCatergory(Integer.parseInt(reader.get("CompanyCatergory")));
			companyRepository.save(templateCompany);
		
		}
	}
	@Transactional(readOnly=false)
	public Message addSector(WSSector wsSector) throws Exception
	{
		 Message msg = checkSector(wsSector);
		 if(msg.getMessageTypeEnum().equals(MessageTypeEnum.ERROR))
			 return msg;
		
		Sector sector = sectorAdapter.toDBSector(wsSector);
		sectorsRepository.save(sector);
		return messagesUitl.getMessage("company.sector.success",null,MessageTypeEnum.INFOMATION);
	}
	
	public Message checkSector(WSSector wsSector)
	{
		if( companyRepository.findByCompanyName(wsSector.getCompanyName())==null)
			return messagesUitl.getMessage("company.doesnotexist",null,MessageTypeEnum.ERROR);
		
		if(sectorsRepository.findBySectorAndCompanyName(wsSector.getSector(), wsSector.getCompanyName())==null)
			return messagesUitl.getMessage("company.sector.avaible",null,MessageTypeEnum.INFOMATION);
		 return messagesUitl.getMessage("company.sector.alreadyexist",null,MessageTypeEnum.ERROR);
	}
} 