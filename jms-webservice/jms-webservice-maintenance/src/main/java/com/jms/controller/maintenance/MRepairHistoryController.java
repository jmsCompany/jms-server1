package com.jms.controller.maintenance;


import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.MRepairHistory;
import com.jms.domain.db.PUnplannedStops;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.m.WSMRepairHistory;
import com.jms.repositories.m.MMachineGroupRepository;
import com.jms.repositories.m.MRepairHistoryRepository;
import com.jms.service.maintenance.MMachineGroupService;
import com.jms.service.maintenance.MRepairHistoryService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class MRepairHistoryController {
	
	@Autowired private MRepairHistoryService mRepairHistoryService;
	@Autowired private MRepairHistoryRepository mRepairHistoryRepository;
	@Autowired private MMachineGroupService mMachineGroupService;
	@Autowired private MMachineGroupRepository mMachineGroupRepository;
	@Autowired private SecurityUtils securityUtils;
	private static final Logger logger = LogManager.getLogger(MRepairHistoryController.class
			.getCanonicalName());

	
	@Transactional(readOnly = false)
	@RequestMapping(value="/m/saveRepairHistory", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid saveRepairHistory(@RequestBody WSMRepairHistory wsMRepairHistory) throws Exception  {
		return mRepairHistoryService.saveRepairHistory(wsMRepairHistory);
	}
	

	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/m/deleteRepairHistory", method=RequestMethod.GET)
	public Valid deleteWSRepairHistory(@RequestParam("idRepairHistory") Long idRepairHistory) {
		return mRepairHistoryService.deleteWSMRepairHistory(idRepairHistory);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/m/findRepairHistory", method=RequestMethod.GET)
	public WSMRepairHistory findRepairHistory(@RequestParam("idRepairHistory") Long idRepairHistory) throws Exception {
		return mRepairHistoryService.findWSMRepairHistory(idRepairHistory);
		
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/m/findRepairHistoryByIdUnplannedStop", method=RequestMethod.GET)
	public WSMRepairHistory findRepairHistoryByIdUnplannedStop(@RequestParam("idUnplannedStop") Long idUnplannedStop) throws Exception {
		return mRepairHistoryService.findRepairHistoryByIdUnplannedStop(idUnplannedStop);
		
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/m/repairHistoryList", method=RequestMethod.POST)
	public WSTableData  repairHistoryList(@RequestParam(value="idMachine",required=false) Long idMachine, @RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		List<MRepairHistory> ws;
		if(idMachine!=null)
		{
			ws=mRepairHistoryRepository.findByIdMachine(idMachine);
		}
		else
		{
			ws = mRepairHistoryRepository.findByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		}
		

		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(ws.size()<start + length)
			end =ws.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			MRepairHistory w = ws.get(i);
			String machineCode="",machineName="";
			if(w.getMMachine()!=null)
			{
				machineCode =(w.getMMachine().getCode()==null)?"":w.getMMachine().getCode();
				machineName=(w.getMMachine().getName()==null)?"":w.getMMachine().getName();
			}
			String op="",opReportTime="";
			if(w.getUsersByOp()!=null)
			{
				op = (w.getUsersByOp().getName()==null)?"":w.getUsersByOp().getName();
			}
			opReportTime =(w.getRepairTime()==null)?"":w.getRepairTime().toString();
			
			String des =(w.getIssueDes()==null)?"":w.getIssueDes();
			String repair ="";
			if(w.getUsersByRepair()!=null)
			{
				repair = (w.getUsersByRepair().getName()==null)?"":w.getUsersByRepair().getName();
			}
			String finishTime =(w.getRecoverTime()==null)?"":w.getRecoverTime().toString();
			
			String status =(w.getMStatusDic()==null)?"":w.getMStatusDic().getName();
			String[] d = {machineCode,machineName,op,opReportTime,des,repair,finishTime,status,""+w.getIdRepairHistory()};
			lst.add(d);
			
			
//			if(mRepairHistory.getIdUnplannedStop()!=null)
//			{
//				PUnplannedStops pUnplannedStops = pUnplannedStopsRepository.findOne(mRepairHistory.getIdUnplannedStop());
//				
//				pc.setRecoverTime(pUnplannedStops.getOpFt());
//				pc.setRepairingTime(pUnplannedStops.getEqFt());
//				pc.setResponseTime(pUnplannedStops.getEqSt());
//			}
			

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(ws.size());
		t.setRecordsFiltered(ws.size());
	    t.setData(lst);
	    return t;
	}
	
	
	
	
}