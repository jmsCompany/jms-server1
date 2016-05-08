package com.jms.controller.store;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jms.domain.db.SCountryDic;
import com.jms.domain.db.SLevelDic;
import com.jms.domain.db.SStatusDic;
import com.jms.domain.db.STermDic;
import com.jms.domain.db.STypeDic;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.store.WSCompanyCo;
import com.jms.repositories.s.SCountryDicRepository;
import com.jms.repositories.s.SLevelDicRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.s.SStermDicRepository;
import com.jms.repositories.s.STypeDicRepository;
import com.jms.service.store.CompanyCoService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class CompanyCoController {
	
	@Autowired private CompanyCoService companyCoService;
	@Autowired private SecurityUtils securityUtils;
	@Autowired private SCountryDicRepository sCountryDicRepository;
	@Autowired private STypeDicRepository sTypeDicRepository;
	@Autowired private SStermDicRepository sStermDicRepository;
	@Autowired private SLevelDicRepository sLevelDicRepository;
	@Autowired private SStatusDicRepository	sStatusDicRepository;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/saveCompanyCo", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSCompanyCo saveCompanyCo(@RequestBody WSCompanyCo wSCompanyCo) throws Exception {
	
		return companyCoService.saveWSCompanyCo(wSCompanyCo);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/deleteCompanyCo", method=RequestMethod.GET)
	public Valid deletecCompanyCo(@RequestParam("companyCoId") Long companyCoId) {
		return companyCoService.deleteCompanyCo(companyCoId);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findCompanyCo", method=RequestMethod.GET)
	public WSCompanyCo findCompanyCo(@RequestParam("companyCoId") Long companyCoId) {
		return companyCoService.findCompanyCo(companyCoId);
		
	}
	
	//国家
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/countries", method=RequestMethod.GET)
	public List<WSSelectObj> findCountries() {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		for(SCountryDic c : sCountryDicRepository.findAll())
		{
			wso.add(new WSSelectObj(c.getId(),c.getName()));
		}
		return wso;
		
	}

	
	//类型
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/companyCoTypes", method=RequestMethod.GET)
	public List<WSSelectObj> findCompanyCoTypes() {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		for(STypeDic s : sTypeDicRepository.findAll())
		{
			wso.add(new WSSelectObj(s.getId(),s.getName()));
		}
		return wso;
	}
	
	//条款 source=freight or =payment
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/terms", method=RequestMethod.GET)
	public List<WSSelectObj> findFreightTerms(@RequestParam("source") String source) {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		for(STermDic s : sStermDicRepository.getBySource(source))
		{
			wso.add(new WSSelectObj(s.getId(),s.getName()));
		}
		return wso;
	}
	
	//级别
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/companyCoLevels", method=RequestMethod.GET)
	public List<WSSelectObj> findCompanyCoLevels() {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		for(SLevelDic s : sLevelDicRepository.findAll())
		{
			wso.add(new WSSelectObj(s.getId(),s.getName()));
		}
		return wso;
	}
	
	//状态
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/status", method=RequestMethod.GET)
	public List<WSSelectObj> findSStatusBySource(@RequestParam("source") String source) {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		for(SStatusDic s : sStatusDicRepository.getBySource(source))
		{
			wso.add(new WSSelectObj(s.getId(),s.getName()));
		}
		return wso;
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/companyCoList", method=RequestMethod.GET)
	public WSTableData  getCompanyCoList(@RequestParam(required=false, value="companyCoTypeId") Long companyCoTypeId, @RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		System.out.println("co Type: " + companyCoTypeId);
		List<WSCompanyCo> wsCompanyCos = companyCoService.getCoCompanies(companyCoTypeId,securityUtils.getCurrentDBUser().getCompany().getId());
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(wsCompanyCos.size()<start + length)
			end =wsCompanyCos.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			WSCompanyCo w = wsCompanyCos.get(i);
			String[] d = {w.getCode(),w.getShortName(),w.getType(),w.getTel(),w.getFax(),w.getAddressAct(),"",""+w.getId()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(wsCompanyCos.size());
		t.setRecordsFiltered(wsCompanyCos.size());
	    t.setData(lst);
	    return t;
	}
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getCompanyCoList", method=RequestMethod.GET)
	public List<WSSelectObj> companyCoList(@RequestParam(value="coCompanyType", required=false) Long coCompanyType) {
		return companyCoService.getCoCompaniesByType(securityUtils.getCurrentDBUser().getCompany().getId(), coCompanyType);
	}

}