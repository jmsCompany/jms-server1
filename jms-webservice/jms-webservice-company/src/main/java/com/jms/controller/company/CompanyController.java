package com.jms.controller.company;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jms.acl.SecuredObjectService;
import com.jms.domain.db.SStatusDic;
import com.jms.domain.db.SStk;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSCompany;
import com.jms.domain.ws.WSTest;
import com.jms.domainadapter.CompanyAdapter;
import com.jms.repositories.s.SStkRepository;
import com.jms.repositories.system.AppsRepository;
import com.jms.service.company.CompanyService;
import com.jms.system.INotificationService;
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
	@Autowired 
	private SecuredObjectService securedObjectService;
	@Autowired 
	private AppsRepository appsRepository;
	@Autowired
	private INotificationService notificationService;
	@Autowired
	private SStkRepository sStkRepository;
	
	private static final Logger logger = LogManager.getLogger(CompanyController.class.getCanonicalName());
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/company/create", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Boolean createCompany(@RequestBody WSCompany wsCompany) throws Exception {
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
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="company/view", method=RequestMethod.GET)
	public WSCompany getCompany() throws Exception {
		return companyAdapter.toWSCompany(companyService.findCompany());
	}


	@Transactional(readOnly = false)
	@RequestMapping(value="/company/cancel", method=RequestMethod.DELETE)
	public Message cancelCompany(@RequestParam("idCompany") int idCompany)
	{
		return companyService.cancelCompany(idCompany);
	}

	@Transactional(readOnly = true)
	@RequestMapping(value="/check/companyname", method=RequestMethod.GET)
	public Valid checkCompanyName(@RequestParam("companyname") String companyname,@RequestParam(required=false,value="idCompany") Long idCompany) throws Exception {
		Boolean returnVal= companyService.checkCompanyName(companyname,idCompany);
		Valid valid = new Valid();
		valid.setValid(returnVal);
		return valid;
	}

	/*just for example, need to move another module */
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/createSTK", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid createSTK(@RequestBody SStk sstk) throws Exception {
		sstk.setUsers(securityUtils.getCurrentDBUser());
		sstk.setCompany(securityUtils.getCurrentDBUser().getCompany());
		Boolean returnVal= companyService.createSTK(sstk);
		Valid valid = new Valid();
		valid.setValid(returnVal);
		return valid;
	}
	
	/*just for example, need to move another module */
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/stklist", method=RequestMethod.GET)
	public WSTest  stkList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {
	    List<SStk> stkList =	sStkRepository.findAll();	
		List<String[]> lst = new ArrayList<String[]>();
		//logger.warn("````````````````````````````````` srart=" + start);
		//logger.warn("````````````````````````````````` draw=" + draw);
		//logger.warn("````````````````````````````````` length=" + length);
		int end=0;
		if(stkList.size()<start + length)
			end =stkList.size();
		else
			end =start + length;
			
		for (int i = start; i < end; i++) {
			SStk stk =stkList.get(i);
			SStatusDic status =stk.getSStatusDic();
			String sStatus="";
			if(status!=null)
				sStatus = status.getName();
			String[] d = { stk.getStkName(), stk.getDes(),stk.getAddress(),stk.getSStatusDic().getName(),sStatus,"<a href='xxxx/?id="+stk.getId()+"'>编辑</a>"};
			lst.add(d);

		}
		WSTest t = new WSTest();
		t.setDraw(draw);
		t.setRecordsTotal(stkList.size());
		t.setRecordsFiltered(stkList.size());
	    t.setData(lst);
	    return t;
	}
}