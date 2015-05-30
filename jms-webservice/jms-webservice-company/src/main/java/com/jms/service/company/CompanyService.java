package com.jms.service.company;


import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.EnabledEnum;
import com.jms.domain.FineTaskEnum;
import com.jms.domain.SpecialCompanyEnum;
import com.jms.domain.SystemUser;
import com.jms.domain.db.Company;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.MessageTypeEnum;
import com.jms.domain.ws.WSCompany;
import com.jms.email.LocalUtil;
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

	private static final Logger logger = LogManager.getLogger(CompanyService.class.getCanonicalName());
	
	@Transactional(readOnly=true)
	public Company findCompanyById(int idCompany)
	{
		Company company = companyRepository.findOne(idCompany);
		if(company!=null)
		logger.debug("find company: " + company.getCompanyName());
		else
			company = new Company();
		return company;
	}
	public Message registCompany(WSCompany wsCompany)
	{
		return null;
		/*
		Message msg = new Message();
		
		
	
		Users user1 = usersRepository.findByEmail(wsCompany.getEmail());
		Users user2 = usersRepository.findByMobile(wsCompany.getMobile());
	
		if(companyRepository.findByCompanyName(wsCompany.getCompanyName()) != null ){
			String message = source.getMessage("company.alreadyexist", null, LocalUtil.getLocal());
			msg.setMessage(message);
			msg.setMessageTypeEnum(MessageTypeEnum.ERROR);
			return msg;
		}
		if( usersRepository.findByEmail(wsCompany.getEmail()) != null   ){
			String message = source.getMessage("company.alreadyexist", null, LocalUtil.getLocal());
			msg.setMessage(message);
			msg.setMessageTypeEnum(MessageTypeEnum.ERROR);
			return msg;
		}
		
		
		return msg;
		*/
	}
	
	
	
	
	
				
				
			
	
	
	public void createTemplateCompany( ){
		
		Company company = companyRepository.findByCompanyName(SpecialCompanyEnum.Template_company.toString() );
		if( company == null ){
			Company templateCompany = new Company();
			templateCompany.setCompanyName(SpecialCompanyEnum.Template_company.toString() );
			templateCompany.setCreationTime(new Date());
			templateCompany.setUsers(usersRepository.findByUsername(SystemUser.System.toString()));
			templateCompany.setEnabled(EnabledEnum.ROBOT.ordinal());
			templateCompany.setFineTask(FineTaskEnum.NORMALTASK.ordinal());
			companyRepository.save(templateCompany);
		}	
	}
	

}
