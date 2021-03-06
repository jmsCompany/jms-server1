package com.jms.controller.production;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.MMachine;
import com.jms.domain.db.PStopsPlan;
import com.jms.domain.db.PUnplannedStops;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.p.WSPStopsPlan;
import com.jms.domain.ws.p.WSPUnplannedStops;
import com.jms.repositories.m.MMachineRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PStopsPlanRepository;
import com.jms.repositories.p.PUnplannedStopsRepository;
import com.jms.service.production.PStopsPlanService;
import com.jms.service.production.PUnplannedStopsService;
import com.jms.web.security.SecurityUtils;




@RestController
@Transactional(readOnly=true)
public class UnplannedStopsController {

	private static final Logger logger = LogManager.getLogger(UnplannedStopsController.class
			.getCanonicalName());
	@Autowired private PStopsPlanService pStopsPlanService;
	//@Autowired private PStopsCodeRepository pStopsCodeRepository;
	@Autowired
	private  PUnplannedStopsRepository pUnplannedStopsRepository;
	@Autowired
	private  PUnplannedStopsService pUnplannedStopsService;
	
	@Autowired private SecurityUtils securityUtils;

	@Autowired private PStatusDicRepository pStatusDicRepository;
	@Autowired private MMachineRepository mMachineRepository;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/saveWSPUnplannedStops", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSPUnplannedStops saveWSPUnplannedStops(@RequestBody WSPUnplannedStops wsPUnplannedStops) throws Exception {
		return pUnplannedStopsService.saveWSPStopsPlan(wsPUnplannedStops);
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findWSUnPlannedStop", method=RequestMethod.GET)
	public WSPUnplannedStops findWSUnPlannedStop(@RequestParam("idUnplannedStops") Long idUnplannedStops) throws Exception {
		return pUnplannedStopsService.findWSPUnplannedStopsById(idUnplannedStops);
		
	}
	
	
	/**type: 0 处理开始，1 处理结束， 2 结束确认*/
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/updateWSPUnplannedStops", method=RequestMethod.GET)
	public Valid updateWSPUnplannedStops(@RequestParam("machineId") Long machineId,@RequestParam("type") Long type) throws Exception {
		return pUnplannedStopsService.updateWSPStopsPlan(machineId,type);
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findWSPUnplannedStopsByCppId", method=RequestMethod.GET)
	public List<WSPUnplannedStops> findWSPUnplannedStopsByCppId(@RequestParam("cppId") Long cppId) throws Exception {
		return pUnplannedStopsService.findWSPUnplannedStopsByCppId(cppId);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findWSPUnplannedStopsBySubCodeId", method=RequestMethod.GET)
	public List<WSPUnplannedStops> findWSPUnplannedStopsBySubCodeId(@RequestParam("subCodeId") Long subCodeId) throws Exception {

		return pUnplannedStopsService.findWSPUnplannedStopsBySubCodeId(subCodeId,securityUtils.getCurrentDBUser().getCompany().getIdCompany());
	
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/etUnplannedStopsList", method=RequestMethod.POST)
	public WSTableData  etUnplannedStopsList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		List<PUnplannedStops> pUnplannedStops=pUnplannedStopsRepository.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(pUnplannedStops.size()<start + length)
			end =pUnplannedStops.size();
		else
			end =start + length;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		for (int i = start; i < end; i++) {
			PUnplannedStops w = pUnplannedStops.get(i);
			
			MMachine m = mMachineRepository.findOne(w.getIdMachine());
			
			String opSt = "";
			if(w.getOpSt()!=null)
			{
				
				opSt= formatter.format(w.getOpSt());
			}
			
			String eqSt = "";
			if(w.getEqSt()!=null)
			{
				
				eqSt= formatter.format(w.getEqSt());
			}
			
			
			String eqFt = "";
			if(w.getEqFt()!=null)
			{
				
				eqFt= formatter.format(w.getEqFt());
			}
			
			String opFt = "";
			if(w.getOpFt()!=null)
			{
				
				opFt= formatter.format(w.getOpFt());
			}
			
			
			
			String subCode="";
			if(w.getPSubCode()!=null)
			{
				subCode =w.getPSubCode().getSubCode();
			}
			String[] d = {""+m.getCode(),subCode,opSt,eqSt,eqFt,opFt,""+w.getIdUnplannedStops()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(pUnplannedStops.size());
		t.setRecordsFiltered(pUnplannedStops.size());
	    t.setData(lst);
	    return t;
	}

}