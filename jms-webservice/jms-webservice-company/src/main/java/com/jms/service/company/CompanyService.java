package com.jms.service.company;


import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.domain.EnabledEnum;
import com.jms.domain.FineTaskEnum;
import com.jms.domain.SystemUser;
import com.jms.domain.db.Company;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.MessageTypeEnum;
import com.jms.domain.ws.WSCompany;
import com.jms.messages.MessagesUitl;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.user.UsersRepository;

@Service
public class CompanyService {
	
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private ResourceBundleMessageSource source;
	@Autowired
	private MessagesUitl messagesUitl;

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
	
	public Message registCompany(WSCompany wsCompany)
	{
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
	

	public Message  verifyCompanyName(WSCompany wscompany) 
	{
		if (companyRepository.findByCompanyName(wscompany.getCompanyName())== null) 
		{
			return messagesUitl.getMessage("user.register.success",null,MessageTypeEnum.INFOMATION);
		}
		else
		{
			return messagesUitl.getMessage("user.register.error",null,MessageTypeEnum.ERROR);
		}
		
	}
	
	
	public Message verifyCompanyNameAndUser(WSCompany wscompany) 
	{
		//todo
		return null;
	}
}
