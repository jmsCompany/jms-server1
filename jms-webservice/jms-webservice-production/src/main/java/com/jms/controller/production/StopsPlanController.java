package com.jms.controller.production;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.PStopsPlan;
import com.jms.domain.db.PWorkCenter;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.production.WSPStopsCode;
import com.jms.domain.ws.production.WSPStopsPlan;
import com.jms.domain.ws.production.WSPWorkCenter;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PStopsCodeRepository;
import com.jms.repositories.p.PStopsPlanRepository;
import com.jms.repositories.p.PSubCodeRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.service.production.PCheckPlanService;
import com.jms.service.production.PStopsCodeService;
import com.jms.service.production.PStopsPlanService;
import com.jms.service.production.WorkCenterService;
import com.jms.web.security.SecurityUtils;




@RestController
@Transactional(readOnly=true)
public class StopsPlanController {

	private static final Logger logger = LogManager.getLogger(StopsPlanController.class
			.getCanonicalName());
	@Autowired private PStopsPlanService pStopsPlanService;
	//@Autowired private PStopsCodeRepository pStopsCodeRepository;
	@Autowired
	private  PStopsPlanRepository pStopPlanRepository;
	//pStopsPlanRepository
	
	@Autowired private SecurityUtils securityUtils;

	@Autowired private PStatusDicRepository pStatusDicRepository;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/savePStopsPlan", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSPStopsPlan savePStopsPlan(@RequestBody WSPStopsPlan wsPStopsPlan) throws Exception {
		return pStopsPlanService.saveWSPStopsPlan(wsPStopsPlan);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/deletePStopsPlan", method=RequestMethod.GET)
	public Valid deletePStopsPlan(@RequestParam("idStopsPlan") Long idStopsPlan) {
		return pStopsPlanService.deletePStopsPlan(idStopsPlan);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findPStopsPlan", method=RequestMethod.GET)
	public WSPStopsPlan findPStopsPlan(@RequestParam("idStopsPlan") Long idStopsPlan) throws Exception {
		return pStopsPlanService.findWSPStopsPlan(idStopsPlan);
		
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findPStopsPlans", method=RequestMethod.GET)
	public List<WSPStopsPlan> findPStopsPlans() throws Exception {
		//logger.debug("/p/findPStopsPlans");
		return pStopsPlanService.findWSPStopsPlans();
	
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findPStopsPlansByMachineId", method=RequestMethod.GET)
	public List<WSPStopsPlan> findPStopsPlansByMachineId(@RequestParam Long machineId) throws Exception {
		//logger.debug("/p/findPStopsPlans");
		return pStopsPlanService.findWSPStopsPlansByMachineId(machineId);
	
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/pStopsActStart", method=RequestMethod.GET)
	public Valid pStopsActStart(@RequestParam Long idStopsPlan) throws Exception {
		PStopsPlan pStopPlan = pStopPlanRepository.findOne(idStopsPlan);
		pStopPlan.setActSt(new Date());
		pStopPlanRepository.save(pStopPlan);
		Valid valid = new Valid();
		valid.setValid(true);
		return valid;
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/pStopsActFinish", method=RequestMethod.GET)
	public Valid pStopsActFinish(@RequestParam Long idStopsPlan) throws Exception {
		PStopsPlan pStopPlan = pStopPlanRepository.findOne(idStopsPlan);
		pStopPlan.setActFt(new Date());
		pStopPlan.setPStatusDic(pStatusDicRepository.findOne(17l)); //结束
		pStopPlanRepository.save(pStopPlan);
		Valid valid = new Valid();
		valid.setValid(true);
		return valid;
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findPStopsPlanList", method=RequestMethod.GET)
	public WSTableData  findPStopsPlanList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		List<PStopsPlan> pStopsPlans =pStopPlanRepository.getPStopsPlansByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(pStopsPlans.size()<start + length)
			end =pStopsPlans.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			PStopsPlan w = pStopsPlans.get(i);
			String[] d = {""+w.getMMachine().getCode(),w.getPSubCode().getSubCode()+"_"+w.getPSubCode().getSubDes(),w.getPlanFt().toString(),w.getPlanSt().toString(),w.getPStatusDic().getName(),""+w.getIdStopsPlan()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(pStopsPlans.size());
		t.setRecordsFiltered(pStopsPlans.size());
	    t.setData(lst);
	    return t;
	}
	
	
	
	
//	显示顺序：机器编号-机器名字、停机编码-停机原因、计划开始时间、计划停止时间、实际开始时间、实际停止时间
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findPStopsPlanReportList", method=RequestMethod.GET)
	public WSTableData  findPStopsPlanReportList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		List<PStopsPlan> pStopsPlans = pStopPlanRepository.getPStopsPlansByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(pStopsPlans.size()<start + length)
			end =pStopsPlans.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			PStopsPlan w = pStopsPlans.get(i);
			String ps=(w.getPlanSt()==null)?"":w.getPlanSt().toString();
			String pf=(w.getPlanFt()==null)?"":w.getPlanFt().toString();
			String as=(w.getActSt()==null)?"":w.getActSt().toString();
			String af=(w.getActFt()==null)?"":w.getActFt().toString();
			String[] d = {""+w.getMMachine().getCode(),w.getPSubCode().getSubCode(),w.getPSubCode().getSubDes(),ps,pf,as,af};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(pStopsPlans.size());
		t.setRecordsFiltered(pStopsPlans.size());
	    t.setData(lst);
	    return t;
	}
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getPStopsPlanObjsList", method=RequestMethod.GET)
	public List<WSSelectObj>  getPStopsPlanObjsList() throws Exception {	   
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		for(PStopsPlan plan: pStopPlanRepository.findAll())
		{
			WSSelectObj w = new WSSelectObj(plan.getIdStopsPlan(),""+plan.getStopsPlanNo());
			ws.add(w);		
		}
		
	    return ws;
	}
	

}