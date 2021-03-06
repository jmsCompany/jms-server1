package com.jms.controller.production;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.PCheckPlan;
import com.jms.domain.db.PCheckTime;
import com.jms.domain.db.PLine;
import com.jms.domain.db.SMaterial;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.p.WSPBom;
import com.jms.domain.ws.p.WSPCheckPlan;
import com.jms.domain.ws.p.WSPCheckTime;
import com.jms.domain.ws.p.WSPLine;
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
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getCheckPlanList", method=RequestMethod.POST)
	public WSTableData  getCheckPlanList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		List<PCheckPlan> pCheckPlans =pCheckPlanRepository.getCheckPlans(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(pCheckPlans.size()<start + length)
			end =pCheckPlans.size();
		else
			end =start + length;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		for (int i = start; i < end; i++) {
			PCheckPlan w = pCheckPlans.get(i);
			
//			工单号woNo、工艺routeNo、产品pno-rev-des、班次、机器machineCode-machineName、
//			操作者op、计划检查时间planSt、检查数量planQty、实际检查时间checkTime、
//			实际检查数量finQty、需完成数量toBeQty,偏差

			SMaterial s = w.getPCPp().getPWo().getSSo().getSMaterial();
			String material = s.getPno()+"-"+s.getRev()+"-"+s.getDes();
			String  plancheckTime =(w.getPlanCheckTime()==null)?"":w.getPlanCheckTime().toString();
			String woNo =(w.getPCPp().getPWo().getWoNo()==null)?"":w.getPCPp().getPWo().getWoNo();
			String routinNo="";
			if(w.getPCPp().getPRoutineD()!=null)
			{
				routinNo=(w.getPCPp().getPRoutineD().getRouteNo()==null)?"":w.getPCPp().getPRoutineD().getRouteNo();
			}
			
			
			String checkTime = "";
			if(w.getCheckTime()!=null)
			{
				checkTime= formatter.format(w.getCheckTime());
			}
			
			
			String shift =(w.getPCPp().getPShiftPlanD().getShift()==null)?"":w.getPCPp().getPShiftPlanD().getShift();
			String code =(w.getPCPp().getMMachine().getCode()==null)?"":w.getPCPp().getMMachine().getCode();
			String name = (w.getPCPp().getUsers().getName()==null)?"":w.getPCPp().getUsers().getName();
			
			String[] d = {woNo,routinNo,material,shift,code,name,plancheckTime
					,""+w.getToBeQty(),checkTime,""+w.getFinQty(),""+w.getToBeQty()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(pCheckPlans.size());
		t.setRecordsFiltered(pCheckPlans.size());
	    t.setData(lst);
	    return t;
	}
	


}