package com.jms.controller.maintenance;


import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.MMachine;
import com.jms.domain.db.MMachineGroup;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.m.WSMachine;
import com.jms.domain.ws.m.WSMachineGroup;
import com.jms.repositories.m.MMachineRepository;
import com.jms.service.maintenance.MMachineService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class MMachineController {
	
	
	@Autowired private MMachineService mMachineService;
	@Autowired private MMachineRepository mMachineRepository;
	@Autowired private SecurityUtils securityUtils;
	private static final Logger logger = LogManager.getLogger(MMachineController.class
			.getCanonicalName());
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/m/getMachinesObjs", method=RequestMethod.GET)
	public List<WSSelectObj> getMachinesObjs() throws Exception {	
	   return mMachineService.getMachinesObj();
	}

	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/m/getMachinesObjsByLineId", method=RequestMethod.GET)
	public List<WSSelectObj> getMachinesObjsByLineId(@RequestParam("lineId") Long lineId) {	
	   return mMachineService.getMachinesObjByLineId(lineId);
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/m/saveMachine", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSMachine saveMachine(@RequestBody WSMachine wsMachine) throws Exception  {
		return mMachineService.saveMachine(wsMachine);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/m/findMachine", method=RequestMethod.GET)
	public WSMachine findMachine(@RequestParam("machineId") Long machineId) throws Exception{	
	   return mMachineService.findMachine(machineId);
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/m/deleteMachine", method=RequestMethod.GET)
	public Valid deleteachine(@RequestParam("idMachine") Long idMachine) {
		return mMachineService.deleteMachine(idMachine);
		
	}
	@Transactional(readOnly = true)
	@RequestMapping(value="/m/getMachineList", method=RequestMethod.POST)
	public WSTableData  getMachineList(@RequestParam(value="q",required=false) String q, @RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		List<MMachine> ms = new ArrayList<MMachine>();
		if(q==null||q.trim().isEmpty())
		{
			ms=mMachineRepository.getMachinesByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		}
		else
		{
			ms = mMachineRepository.getMachinesByCompanyIdAndQ(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), q);
		}
		
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(ms.size()<start + length)
			end =ms.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			MMachine w = ms.get(i);
			String name =(w.getName()==null)?"":w.getName();
			String groupName =(w.getMMachineGroup()==null)?"":w.getMMachineGroup().getGroupName();
			String kwa =(w.getTotalKwa()==null)?"":""+w.getTotalKwa();
			String spec =(w.getSpec()==null)?"":w.getSpec();
			String date =(w.getSatCompletedDate()==null)?"":w.getSatCompletedDate().toString();
			String location = (w.getSBin()==null)?"":w.getSBin().getBin();
			String status = (w.getMStatusDic()==null)?"":w.getMStatusDic().getName();
			String[] d = {w.getCode(),name,groupName,kwa,spec,date,location,status,""+w.getIdMachine()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(ms.size());
		t.setRecordsFiltered(ms.size());
	    t.setData(lst);
	    return t;
	}
	
	
}