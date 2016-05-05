package com.jms.controller.production;


import java.util.ArrayList;
import java.util.List;
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
import com.jms.repositories.p.PStopsCodeRepository;
import com.jms.repositories.p.PStopsPlanRepository;
import com.jms.repositories.p.PSubCodeRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.service.production.PStopsCodeService;
import com.jms.service.production.PStopsPlanService;
import com.jms.service.production.WorkCenterService;
import com.jms.web.security.SecurityUtils;




@RestController
@Transactional(readOnly=true)
public class StopsPlanController {
	
	@Autowired private PStopsPlanService pStopsPlanService;
	//@Autowired private PStopsCodeRepository pStopsCodeRepository;
	@Autowired
	private  PStopsPlanRepository pStopPlanRepository;
	
	@Autowired private SecurityUtils securityUtils;

	
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
	@RequestMapping(value="/p/findPStopsPlanList", method=RequestMethod.GET)
	public WSTableData  findPStopsPlanList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		List<PStopsPlan> pStopsPlans =pStopPlanRepository.findAll();
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(pStopsPlans.size()<start + length)
			end =pStopsPlans.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			PStopsPlan w = pStopsPlans.get(i);
			String[] d = {""+w.getStopsPlanNo(),w.getMMachine().getName(),""+w.getIdStopsPlan()};
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