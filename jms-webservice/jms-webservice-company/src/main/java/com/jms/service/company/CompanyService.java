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
import com.jms.domainadapter.CompanyAdapter;
import com.jms.domainadapter.UserAdapter;
import com.jms.messages.MessagesUitl;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.company.SectorsRepository;
import com.jms.repositories.user.RolePrivRepository;
import com.jms.repositories.user.RoleRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.repositories.workmanagement.ProjectReposity;
import com.jms.user.IUserService;

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
	private ProjectReposity projectRepository; 
	@Autowired @Qualifier("iUserServiceImpl")
	private IUserService iUserServiceImpl;
	@Autowired
	private RolePrivRepository rolePrivReposity;
	
	private static final Logger logger = LogManager.getLogger(CompanyService.class.getCanonicalName());
	
	@Transactional(readOnly=true)
	public Company findCompanyById(int idCompany)
	{
		return companyRepository.findOne(idCompany);
		

	}
	@Transactional(readOnly=true)
	public Company findCompanyByIdUser(String idUser)
	{
		Users u = usersRepository.findByUsernameOrEmailOrMobile(idUser);
		if(u==null)
			return null;
        return u.getCompany();
		
	}
	@Transactional(readOnly=false)
	public Message registCompany(WSCompany wsCompany) throws Exception
	{
		Message message = checkCompanyName(wsCompany.getCompanyName());
		if(message.getMessageTypeEnum().equals(MessageTypeEnum.ERROR))
			return message;
		logger.debug("user name: " + wsCompany.getWsUsers().getName());
		message = iUserServiceImpl.checkLogin(wsCompany.getWsUsers().getUsername(),wsCompany.getWsUsers().getEmail(),wsCompany.getWsUsers().getMobile());
		
		 if(message.getMessageTypeEnum().equals(MessageTypeEnum.ERROR))
				return message;
		 
		 //
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
		 return null;
		
	}		
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
	

	public Message checkCompanyName(String companyName) 
	{
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
	
	public void copyDataBetweenCompanies(Company from,Company to)
	{
		//copy projects
		for(Project p: from.getProjects())
		{
			p.setIdProject(null);
			p.setCompany(to);
			projectRepository.save(p);
		}
		//copy sectors
		for(Sector s: from.getSectors())
		{
		  s.setIdSector(null);
		  s.setCompany(to);
		  sectorsRepository.save(s);
		}
	   //copy roles and privileges
        for(Roles r: from.getRoleses())
        {
        	Set<RolePriv> rpSet =r.getRolePrivs();
        	r.setIdRole(null);
        	r.setCompany(to);
        	roleRepository.save(r);
        	for(RolePriv rp: rpSet)
        	{
        		RolePrivId id = new RolePrivId(rp.getModules().getIdModule(),r.getIdRole()); 
        		rp.setId(id);
        		//rp.setRoles(roles); data inconsist
        		rolePrivReposity.save(rp);
        	}
        	
        }
   }

}