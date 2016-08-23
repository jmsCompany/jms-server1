package com.jms.controller.maintenance;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.MMachineGroup;
import com.jms.domain.db.MSparePart;
import com.jms.domain.db.PLine;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.m.WSBreakdownRate;
import com.jms.domain.ws.m.WSMSparePart;
import com.jms.domain.ws.m.WSMachineGroup;
import com.jms.domain.ws.m.WSMainRecord;
import com.jms.domain.ws.m.WSMtbf;
import com.jms.domain.ws.p.WSPActualSetup;
import com.jms.domain.ws.p.WSPCheckPlan;
import com.jms.domain.ws.p.WSPLine;
import com.jms.repositories.m.MMachineGroupRepository;
import com.jms.repositories.m.MSparePartRepository;
import com.jms.service.maintenance.MMachineGroupService;
import com.jms.service.maintenance.MMachineService;
import com.jms.service.maintenance.MMainRecordService;
import com.jms.service.maintenance.MSparePartService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class MMainRecordController {
	
	@Autowired private MMainRecordService mMainRecordService;
	@Autowired private MSparePartRepository mSparePartRepository;
	@Autowired private MMachineGroupService mMachineGroupService;
	@Autowired private MMachineGroupRepository mMachineGroupRepository;
	@Autowired private SecurityUtils securityUtils;
	private static final Logger logger = LogManager.getLogger(MMainRecordController.class
			.getCanonicalName());
	
	

	@Transactional(readOnly = false)
	@RequestMapping(value="/m/saveWSMainRecords", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid saveWSMainRecords(@RequestBody List<WSMainRecord> mainRecords) {
		return mMainRecordService.saveWSMainRecords(mainRecords);
	}
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/m/mainRecordList", method=RequestMethod.POST)
	public WSTableData  mainRecordList(@RequestParam Long date, @RequestParam Long idMachine, @RequestParam Long idDept, @RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length)  {	   
		return mMainRecordService.mainRecordList(date, idMachine, idDept, draw, start, length);
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/m/mainRecordListForPad", method=RequestMethod.GET)
	public List<WSMainRecord>  mainRecordListForPad(@RequestParam Long idMachine, @RequestParam Long idDept)  {	   
		return mMainRecordService.mainRecordListForPad(idMachine, idDept);
	}
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/m/getMainRecordById", method=RequestMethod.GET)
	public WSMainRecord  getMainRecordById(@RequestParam Long idMainRecord)  {	   
		return mMainRecordService.getMainRecordById(idMainRecord);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/m/getMainRecordByItemId", method=RequestMethod.GET)
	public WSMainRecord  getMainRecordByItemId(@RequestParam Long idMainItem)  {	   
		return mMainRecordService.getMainRecordByItemId(idMainItem);
	}
	
	
	
	
//	
//	@Transactional(readOnly = true)
//	@RequestMapping(value="/m/getWSMtbf", method=RequestMethod.GET)
//	public List<WSMtbf>  getWSMtbf(@RequestParam Long year)  {	
//		
//		List<WSMtbf> ws = new ArrayList<WSMtbf>();
//		
//		
//		for(int x =1 ; x<13; x++)
//		{
//			WSMtbf w = new WSMtbf();
//			w.setX(x);
//			w.setMttrTarget(40);
//			w.setMtbfTarget(10);
//			w.setMtbfActual((float)Math.random()*(20));
//			w.setMttrActual((float)Math.random()*(60));
//			ws.add(w);
//		}
//
//		
//		return ws;
//	}
//	
//	
//	@Transactional(readOnly = true)
//	@RequestMapping(value="/m/getWSBreakdownRate", method=RequestMethod.GET)
//	public List<WSBreakdownRate>  getWSBreakdownRate(@RequestParam Long year)  {	
//		
//		List<WSBreakdownRate> ws = new ArrayList<WSBreakdownRate>();
//		
//		
//		for(int x =1 ; x<27; x++)
//		{
//			WSBreakdownRate w = new WSBreakdownRate();
//			w.setX(x);
//			w.setTarget(5);
//			w.setY((float)Math.random()*(10));
//			ws.add(w);
//		}
//
//		
//		return ws;
//	}
	
	
	
}