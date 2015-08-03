package com.jms.controller.company;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.ws.Message;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSCompany;
import com.jms.domain.ws.WSTest;
import com.jms.domainadapter.CompanyAdapter;
import com.jms.service.company.CompanyService;
import com.jms.user.IUserService;
import com.jms.web.security.SecurityUtils;

@RestController
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CompanyAdapter companyAdapter;
	@Autowired @Qualifier("iUserServiceImpl")
	private IUserService iUserServiceImpl;
	@Autowired 
	private  SecurityUtils securityUtils;

	private static final Logger logger = LogManager.getLogger(CompanyController.class.getCanonicalName());
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/company/create", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Boolean createCompany(@RequestBody WSCompany wsCompany) throws Exception {
		System.out.println("call company registed: " +wsCompany.getCompanyName());
		return companyService.registCompany(wsCompany);
	}
	@Transactional(readOnly = false)
	@RequestMapping(value="/company/update", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Boolean updateCompany(@RequestBody WSCompany wsCompany) throws Exception {
		return companyService.updateCompany(wsCompany);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/dic/test", method=RequestMethod.GET)
	public WSTest  test(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {
		List<String[]> lst = new ArrayList<String[]>();
		for (int i = start; i < start + length; i++) {
			String[] d = { "co1_data" + i, "col2_data" + i ,"col3_data" + i,"col4_data" + i,"col5_data" + i};
			lst.add(d);

		}
		WSTest t = new WSTest();
		t.setDraw(draw);
		t.setRecordsTotal(1000000);
		t.setRecordsFiltered(1000000);
	    t.setData(lst);
	    return t;
		
		//String jsonp = "("+getObj.toString()+");";
		 //MappingJacksonValue value = new MappingJacksonValue(t);
	     //   value.setJsonpFunction(callback);
	    //    return value;

	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="company/view/{idCompany}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public WSCompany getCompany(@PathVariable("idCompany") Long idCompany) throws Exception {
		return companyAdapter.toWSCompany(companyService.findCompanyById(idCompany));
	}
	@Transactional(readOnly = true)
	@RequestMapping(value="company/view", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public WSCompany getCompany(@RequestParam("login") String login) throws Exception {
		return companyAdapter.toWSCompany(companyService.findCompanyByLogin(login));
	}

	@Transactional(readOnly = false)
	@RequestMapping(value="/company/cancel", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	public Message cancelCompany(@RequestParam("idCompany") int idCompany)
	{
		return companyService.cancelCompany(idCompany);
	}

	@Transactional(readOnly = true)
	@RequestMapping(value="/check/companyname", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Valid checkCompanyName(@RequestParam("companyname") String companyname,@RequestParam(required=false,value="idCompany") Long idCompany) throws Exception {
		
		//System.out.print("company name: " + companyname);
		Boolean returnVal= companyService.checkCompanyName(companyname,idCompany);
		Valid valid = new Valid();
		valid.setValid(returnVal);
		return valid;
	}


}