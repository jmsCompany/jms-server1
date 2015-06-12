package com.jms.controller.company;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.Config;
import com.jms.domain.FineTaskEnum;
import com.jms.domain.db.Company;
import com.jms.domain.db.Roles;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.WSCompany;
import com.jms.domain.ws.WSDics;
import com.jms.domain.ws.WSSector;
import com.jms.domain.ws.WSTaskType;
import com.jms.domain.ws.WSTaskTypes;
import com.jms.domain.ws.WSUser;
import com.jms.domainadapter.CompanyAdapter;
import com.jms.service.company.CompanyService;
import com.jms.system.IDicDService;
import com.jms.web.security.JMSUserDetails;
import com.jms.web.security.SecurityUtils;



@RestController
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CompanyAdapter companyAdapter;
	
	@Autowired  @Qualifier("dicDService")
	private IDicDService dicDService;
	private static final Logger logger = LogManager.getLogger(CompanyController.class.getCanonicalName());
	
	@Autowired 
	private  SecurityUtils securityUtils;
	@Transactional(readOnly = true)
	@RequestMapping(value="company/view/{idCompany}", method=RequestMethod.GET)
	public WSCompany getCompany(@PathVariable("idCompany") int idCompany) throws Exception {
		JMSUserDetails u =securityUtils.getCurrentUser();
		logger.debug("user: " + u.getLogin());
		
		for(GrantedAuthority a : u.getAuthorities())
		{
			logger.debug("auth: " + a.getAuthority());
		}
		Company company= companyService.findCompanyById(idCompany);
		return companyAdapter.toWSCompany(company);
	}
	@Transactional(readOnly = true)
	@RequestMapping(value="company/view", method=RequestMethod.GET)
	public WSCompany getCompany(@RequestParam("idUser") String idUser) throws Exception {
		Company company= companyService.findCompanyByIdUser(idUser);
		return companyAdapter.toWSCompany(company);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="company/checkCompanyName", method=RequestMethod.GET)
	public Message checkCompanyName(@RequestParam("companyName") String companyName) throws Exception {
		return companyService.checkCompanyName(companyName);
	}

	@Transactional(readOnly = false)
	@RequestMapping(value="/company/create", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Message createCompany(@RequestBody WSCompany wsCompany) throws Exception {
		return companyService.registCompany(wsCompany);
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/company/cancel", method=RequestMethod.GET)
	public Message cancelCompany(@RequestParam("idCompany") int idCompany)
	{
		return companyService.cancelCompany(idCompany);
	}
	@Transactional(readOnly = false)
	@RequestMapping(value="/company/addSector", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Message addSector(@RequestBody WSSector wsSector) throws Exception
	{
		return companyService.addSector(wsSector);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/company/natures", method=RequestMethod.GET)
	public WSDics getNatures() throws Exception
	{
		return dicDService.getDicsByType(Config.companyNature);
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/company/sizes", method=RequestMethod.GET)
	public WSDics getSizes() throws Exception
	{
		return dicDService.getDicsByType(Config.companySize);
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/company/types", method=RequestMethod.GET)
	public WSDics getTypes() throws Exception
	{
		return dicDService.getDicsByType(Config.companyType);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/company/taskTypes", method=RequestMethod.GET)
	public WSTaskTypes getTaskTypes()
	{
		WSTaskTypes wsTaskTypes = new WSTaskTypes();
		wsTaskTypes.getTaskTypes().add(new WSTaskType(FineTaskEnum.NORMALTASK.getStatusCode(), FineTaskEnum.NORMALTASK.toString()));
		wsTaskTypes.getTaskTypes().add(new WSTaskType(FineTaskEnum.FINETASK.getStatusCode(), FineTaskEnum.FINETASK.toString()));
		return wsTaskTypes;
	}
}