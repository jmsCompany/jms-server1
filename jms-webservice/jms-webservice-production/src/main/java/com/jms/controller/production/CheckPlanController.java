package com.jms.controller.production;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.PCheckTime;
import com.jms.domain.db.PLine;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.production.WSPCheckPlan;
import com.jms.domain.ws.production.WSPCheckTime;
import com.jms.domain.ws.production.WSPLine;
import com.jms.repositories.p.PCheckPlanRepository;
import com.jms.repositories.p.PCheckTimeRepository;
import com.jms.repositories.p.PLineRepository;
import com.jms.service.production.PCheckPlanService;
import com.jms.service.production.PCheckTimeService;
import com.jms.service.production.PlineService;
import com.jms.web.security.SecurityUtils;



@RestController
@Transactional(readOnly=true)
public class CheckPlanController {
	
	@Autowired private PCheckPlanService pCheckPlanService;
	@Autowired private PCheckPlanRepository pCheckPlanRepository;
	@Autowired private SecurityUtils securityUtils;

	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/saveCheckPlan", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSPCheckPlan saveWSPCheckPlan(@RequestBody WSPCheckPlan wsPCheckPlan) throws Exception {
		return pCheckPlanService.saveWSPCheckPlan(wsPCheckPlan);
	}
	

	

	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findCheckPlans", method=RequestMethod.GET)
	public List<WSPCheckPlan> findCheckPlans(@RequestParam("cppId") Long cppId) throws Exception {
		return pCheckPlanService.findWSPCheckPlans(cppId);
		
	}
	


	
	


}