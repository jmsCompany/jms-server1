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
import com.jms.domain.db.SComCom;
import com.jms.domain.db.SStatusDic;
import com.jms.domain.db.SStk;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSCompany;
import com.jms.domain.ws.WSTableData;
import com.jms.domainadapter.CompanyAdapter;
import com.jms.repositories.s.SComComRepository;
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
	@Autowired
	private SComComRepository sComComRepository;
	
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

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/check1/existcompanyname", method=RequestMethod.GET)
	public Valid existcompanyname(@RequestParam("companyname") String companyname,@RequestParam(required=false,value="idCompany") Long idCompany) throws Exception {
		Boolean returnVal = companyService.checkCompanyName(companyname,idCompany);
		
		//存在
		if(!returnVal)
		{
			//System.out.println("id:" + securityUtils.getCurrentDBUser().getCompany().getIdCompany());
			for(SComCom sc: sComComRepository.findMyCompanies(securityUtils.getCurrentDBUser().getCompany().getIdCompany()))
			{
				if(sc.getCompany1().equals(companyname)||sc.getCompany2().equals(companyname))
				{
					Valid valid = new Valid();
					valid.setValid(false);
					valid.setMsg("该公司已经是往来公司！");
					return valid;
				}
			}
			Valid valid = new Valid();
			valid.setValid(true);
			return valid;
		}
		else
		{
			Valid valid = new Valid();
			valid.setValid(false);
			valid.setMsg("不存在该公司！");
			return valid;
		}
	
	}

	
}