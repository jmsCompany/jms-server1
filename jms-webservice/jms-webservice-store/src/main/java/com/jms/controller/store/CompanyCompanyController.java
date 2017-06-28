package com.jms.controller.store;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.Company;
import com.jms.domain.db.SComCom;
import com.jms.domain.db.SCompanyCo;
import com.jms.domain.db.SCountryDic;
import com.jms.domain.db.SLevelDic;
import com.jms.domain.db.SStatusDic;
import com.jms.domain.db.STermDic;
import com.jms.domain.db.STypeDic;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.s.WSCompanyCo;
import com.jms.domain.ws.s.WSNumSet;
import com.jms.domain.ws.s.WSOverfulfill;
import com.jms.domain.ws.s.WSSComCom;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.s.SComComRepository;
import com.jms.repositories.s.SCompanyCoRepository;
import com.jms.repositories.s.SCountryDicRepository;
import com.jms.repositories.s.SLevelDicRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.s.SStermDicRepository;
import com.jms.repositories.s.STypeDicRepository;
import com.jms.service.store.CompanyCoService;
import com.jms.service.store.CompanyCompanyService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class CompanyCompanyController {
	
	@Autowired private CompanyCompanyService companyCompanyService;
	@Autowired private SecurityUtils securityUtils;
	@Autowired private SCountryDicRepository sCountryDicRepository;
	@Autowired private STypeDicRepository sTypeDicRepository;
	@Autowired private SStermDicRepository sStermDicRepository;
	@Autowired private SLevelDicRepository sLevelDicRepository;
	@Autowired private SStatusDicRepository	sStatusDicRepository;
	@Autowired private SCompanyCoRepository sCompanyCoRepository;
	@Autowired private CompanyRepository companyRepository;
	@Autowired
	private SComComRepository sComComRepository;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/saveComCom", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSSComCom saveComCom(@RequestBody WSSComCom wSComCom) throws Exception {
		return companyCompanyService.saveWSComCom(wSComCom);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/updateComCom", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSSComCom updateComCom(@RequestBody WSSComCom wSComCom) throws Exception {
		return companyCompanyService.updateWSComCom(wSComCom);
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findComCom", method=RequestMethod.GET)
	public WSSComCom findCompanyCompany(@RequestParam("id") Long id) throws Exception {
		return companyCompanyService.findCompanyCompany(id);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/updateWSComComStatus", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid updateWSComComStatus(@RequestBody WSSComCom wSComCom) throws Exception {
		return companyCompanyService.updateWSComComStatus(wSComCom);
	}
	
	
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/myAuditList", method=RequestMethod.POST)
	public WSTableData  getMyAuditList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   

		List<WSSComCom> wsCompanyCos = companyCompanyService.findMyAudits();
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(wsCompanyCos.size()<start + length)
			end =wsCompanyCos.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			WSSComCom w = wsCompanyCos.get(i);
			Long t = w.getType();
			String type = (t.equals(0l))?"流转":"库存";
			Long s = w.getStatus();
			String status="";
			if(s.equals(1l))
			{
				status ="提交";
			}
			else if(s.equals(2l))
			{
				status ="同意";
			}
			else if(s.equals(3l))
			{
				status ="拒绝";
			}
			else if(s.equals(4l))
			{
				status ="再提交";
			}
			else
			{
				status ="取消";
			}
			String[] d = {w.getCompany1(),w.getLinkman1(),w.getEmail1(),w.getCompany2(),w.getLinkman2(),w.getEmail2(),type,status,""+w.getId()};
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
	@RequestMapping(value="/s/myApplyList", method=RequestMethod.POST)
	public WSTableData  getMyApplyList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   

		List<WSSComCom> wsCompanyCos = companyCompanyService.findMyApplied();
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(wsCompanyCos.size()<start + length)
			end =wsCompanyCos.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			WSSComCom w = wsCompanyCos.get(i);
			Long t = w.getType();
			String type = (t.equals(0l))?"流转":"库存";
			Long s = w.getStatus();
			String status="";
			if(s.equals(1l))
			{
				status ="提交";
			}
			else if(s.equals(2l))
			{
				status ="同意";
			}
			else if(s.equals(3l))
			{
				status ="拒绝";
			}
			else if(s.equals(4l))
			{
				status ="再提交";
			}
			else
			{
				status ="取消";
			}
			String[] d = {w.getCompany1(),w.getLinkman1(),w.getEmail1(),w.getCompany2(),w.getLinkman2(),w.getEmail2(),type,status,""+w.getId()};
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
	@RequestMapping(value="/s/myAgreeList", method=RequestMethod.POST)
	public WSTableData  getMyAgreeList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   

		List<WSSComCom> wsCompanyCos = companyCompanyService.findMyAgrees();
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(wsCompanyCos.size()<start + length)
			end =wsCompanyCos.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			WSSComCom w = wsCompanyCos.get(i);
			Long t = w.getType();
			String type = (t.equals(0l))?"流转":"库存";
			Long s = w.getStatus();
			String status="";
			if(s.equals(1l))
			{
				status ="提交";
			}
			else if(s.equals(2l))
			{
				status ="同意";
			}
			else if(s.equals(3l))
			{
				status ="拒绝";
			}
			else if(s.equals(4l))
			{
				status ="再提交";
			}
			else
			{
				status ="取消";
			}
			String[] d = {w.getCompany1(),w.getLinkman1(),w.getEmail1(),w.getCompany2(),w.getLinkman2(),w.getEmail2(),type,status,""+w.getId()};
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
	@RequestMapping(value="/s/myDegreeList", method=RequestMethod.POST)
	public WSTableData  myDegreeList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   

		List<WSSComCom> wsCompanyCos = companyCompanyService.findDegrees();
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(wsCompanyCos.size()<start + length)
			end =wsCompanyCos.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			WSSComCom w = wsCompanyCos.get(i);
			Long t = w.getType();
			String type = (t.equals(0l))?"流转":"库存";
			Long s = w.getStatus();
			String status="";
			if(s.equals(1l))
			{
				status ="提交";
			}
			else if(s.equals(2l))
			{
				status ="同意";
			}
			else if(s.equals(3l))
			{
				status ="拒绝";
			}
			else if(s.equals(4l))
			{
				status ="再提交";
			}
			else
			{
				status ="取消";
			}
			String[] d = {w.getCompany1(),w.getLinkman1(),w.getEmail1(),w.getCompany2(),w.getLinkman2(),w.getEmail2(),type,status,""+w.getId()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(wsCompanyCos.size());
		t.setRecordsFiltered(wsCompanyCos.size());
	    t.setData(lst);
	    return t;
	}
	
	
	//往来公司
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/myCompanies", method=RequestMethod.GET)
	public List<WSSelectObj> findMyCompanies() {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		Long idCompany =securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		//添加本身 
		wso.add(new WSSelectObj(0l,securityUtils.getCurrentDBUser().getCompany().getCompanyName()));
		for(SComCom c : sComComRepository.findMyCompanies(idCompany))
		{
			if(idCompany.equals(c.getIdCompany1()))
			{
				wso.add(new WSSelectObj(c.getId(),c.getCompany2()));
			}
			else
			{
				wso.add(new WSSelectObj(c.getId(),c.getCompany1()));
			}
			
		}
		return wso;
		
	}

	
	//往来公司
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/myCoCompanies", method=RequestMethod.GET)
	public List<WSSelectObj> findMyCoCompanies() {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		Long idCompany =securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		for(SComCom c : sComComRepository.findMyCompanies(idCompany))
		{
			if(idCompany.equals(c.getIdCompany1()))
			{
				wso.add(new WSSelectObj(c.getIdCompany2(),c.getCompany2()));
			}
			else
			{
				wso.add(new WSSelectObj(c.getIdCompany1(),c.getCompany1()));
			}
			
		}
		return wso;
		
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/saveOverfulfill", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSOverfulfill saveOverfulfill(@RequestBody WSOverfulfill wsOverfulfill) {
		Company company = securityUtils.getCurrentDBUser().getCompany();
		company.setOverfulfilRecieve(wsOverfulfill.getReceive());
		company.setOverfulfilSend(wsOverfulfill.getSend());
		companyRepository.save(company);
		return wsOverfulfill;
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/getOverfulfill", method=RequestMethod.GET)
	public WSOverfulfill getOverfulfill() {
		Company company = securityUtils.getCurrentDBUser().getCompany();
		WSOverfulfill ws = new WSOverfulfill();
		Long receive = (company.getOverfulfilRecieve()==null)?0l:company.getOverfulfilRecieve();
		Long send = (company.getOverfulfilSend()==null)?0l:company.getOverfulfilSend();
		ws.setReceive(receive);
		ws.setSend(send);
		return ws;
	}
	
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/saveWSNumSet", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSNumSet saveWSNumSet(@RequestBody WSNumSet wsNumSet) {
		Company company = securityUtils.getCurrentDBUser().getCompany();
		company.setAutoDo(wsNumSet.getAutoDo());
		company.setAutoPo(wsNumSet.getAutoPo());
		company.setAutoSo(wsNumSet.getAutoSo());
		company.setAutoWo(wsNumSet.getAutoWo());
		companyRepository.save(company);
		return wsNumSet;
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/getWSNumSet", method=RequestMethod.GET)
	public WSNumSet getWSNumSet() {
		Company company = securityUtils.getCurrentDBUser().getCompany();
		WSNumSet ws = new WSNumSet();
		Long autoDo = (company.getAutoDo()==null)?1l:company.getAutoDo();
		Long autoSo = (company.getAutoSo()==null)?1l:company.getAutoSo();
		Long autoWo = (company.getAutoWo()==null)?1l:company.getAutoWo();
		Long autoPo = (company.getAutoPo()==null)?1l:company.getAutoPo();
		ws.setAutoDo(autoDo);
		ws.setAutoPo(autoPo);
		ws.setAutoSo(autoSo);
		ws.setAutoWo(autoWo);
		return ws;
	}
	
	
	
}