package com.jms.controller.store;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
import com.jms.domain.ws.s.WSSComCom;
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
	@Autowired
	private SComComRepository sComComRepository;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/saveComCom", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSSComCom saveComCom(@RequestBody WSSComCom wSComCom) throws Exception {
		return companyCompanyService.saveWSComCom(wSComCom);
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

}