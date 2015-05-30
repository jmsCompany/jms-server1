package com.jms.controller.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.*;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.Company;
import com.jms.domain.ws.Message;
import com.jms.service.company.CompanyService;



@RestController
@EnableAutoConfiguration
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	//test
	@RequestMapping(value="company/{idCompany}/view", method=RequestMethod.GET)
	public Company getCompany(@PathVariable("idCompany") int idCompany) {
		return companyService.findCompanyById(idCompany);
	}
	
	
	/*
	@Transactional(readOnly = false)
	@RequestMapping(value="/company/create", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Message createCompany(@RequestBody Company company) {
		
		return null;
		//return companyService.createCompany(company);
	}
*/


}