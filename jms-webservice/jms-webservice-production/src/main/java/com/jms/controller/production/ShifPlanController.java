package com.jms.controller.production;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.PBomLabel;
import com.jms.domain.db.PShiftPlan;
import com.jms.domain.db.PWo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.production.WSPBom;
import com.jms.domain.ws.production.WSPWo;
import com.jms.domain.ws.production.WSShiftPlan;
import com.jms.repositories.p.PBomLabelRepository;
import com.jms.repositories.p.PShiftPlanDRepository;
import com.jms.repositories.p.PShiftPlanRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.service.production.BomLabelService;
import com.jms.service.production.BomService;
import com.jms.service.production.ShiftPlanDService;
import com.jms.service.production.ShiftPlanService;
import com.jms.service.production.WoService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class ShifPlanController {
	
	@Autowired private ShiftPlanService shiftPlanService;
	@Autowired private ShiftPlanDService shiftPlanDService;
	@Autowired
	private PShiftPlanDRepository pShiftPlanDRepository;
	@Autowired
	private PShiftPlanRepository pShiftPlanRepository;
	
	@Autowired private SecurityUtils securityUtils;

	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/saveShiftPlan", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSShiftPlan saveWSShiftPlan(@RequestBody WSShiftPlan wsShiftPlan) throws Exception {
		return shiftPlanService.saveWSPShiftPlan(wsShiftPlan);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/deleteShiftPlan", method=RequestMethod.GET)
	public Valid deleteShiftPlan(@RequestParam("idShiftPlan") Long idShiftPlan) {
		return shiftPlanService.deletePShiftPlan(idShiftPlan);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findShiftPlan", method=RequestMethod.GET)
	public WSShiftPlan findShiftPlan(@RequestParam("idShiftPlan") Long idShiftPlan) throws Exception {
		return shiftPlanService.findWSShiftPlan(idShiftPlan);
		
	}
	


	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findWSShiftPlanDObjs", method=RequestMethod.GET)
	public List<WSSelectObj> findWSShiftPlanDObjs(){
		return shiftPlanDService.findWSShiftPlanDObjs();
		
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getShiftPlanList", method=RequestMethod.GET)
	public WSTableData  getShiftPlanList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		List<PShiftPlan> shifPlans =pShiftPlanRepository.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(shifPlans.size()<start + length)
			end =shifPlans.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			PShiftPlan w = shifPlans.get(i);
			String[] d = {w.getName(),w.getSt().toString(),w.getFt().toString(),w.getPStatusDic().getName(),""+w.getIdShiftPlan()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(shifPlans.size());
		t.setRecordsFiltered(shifPlans.size());
	    t.setData(lst);
	    return t;
	}
	

}