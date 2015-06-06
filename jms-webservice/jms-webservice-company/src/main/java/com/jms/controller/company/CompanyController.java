package com.jms.controller.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.Company;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.WSCompany;
import com.jms.domain.ws.WSSector;
import com.jms.domainadapter.CompanyAdapter;
import com.jms.service.company.CompanyService;



@RestController
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CompanyAdapter companyAdapter;
	
	@Transactional(readOnly = true)
	@RequestMapping(value="company/view/{idCompany}", method=RequestMethod.GET)
	public WSCompany getCompany(@PathVariable("idCompany") int idCompany) throws Exception {
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
}