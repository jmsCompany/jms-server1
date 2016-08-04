package com.jms.controller.maintenance;


import java.util.ArrayList;
import java.util.List;

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
import com.jms.domain.ws.m.WSMSparePart;
import com.jms.domain.ws.m.WSMachineGroup;
import com.jms.domain.ws.p.WSPCheckPlan;
import com.jms.domain.ws.p.WSPLine;
import com.jms.repositories.m.MMachineGroupRepository;
import com.jms.repositories.m.MSparePartRepository;
import com.jms.service.maintenance.MMachineGroupService;
import com.jms.service.maintenance.MMachineService;
import com.jms.service.maintenance.MSparePartService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class MSparePartController {
	
	@Autowired private MSparePartService mSparePartService;
	@Autowired private MSparePartRepository mSparePartRepository;
	@Autowired private MMachineGroupService mMachineGroupService;
	@Autowired private MMachineGroupRepository mMachineGroupRepository;
	@Autowired private SecurityUtils securityUtils;
	private static final Logger logger = LogManager.getLogger(MSparePartController.class
			.getCanonicalName());
	
//	@Transactional(readOnly = true)
//	@RequestMapping(value="/m/getMachineGroupsObjs", method=RequestMethod.GET)
//	public List<WSSelectObj> getMachineGroupsObjs() {	
//		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
//		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
//		for(MMachineGroup g:mMachineGroupRepository.findByIdCompany( companyId)){
//			WSSelectObj w = new WSSelectObj(g.getIdGroup(),g.getGroupName());
//			ws.add(w);
//		}
//	   return ws;
//	}

	
	@Transactional(readOnly = false)
	@RequestMapping(value="/m/saveSparePart", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid saveSparePart(@RequestBody WSMSparePart wsMSparePart) throws Exception  {
		return mSparePartService.saveWSMSparePart(wsMSparePart);
	}
	

	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/m/deleteSparePart", method=RequestMethod.GET)
	public Valid deleteWMachineGroup(@RequestParam("idPart") Long idPart) {
		return mSparePartService.deleteMSparePart(idPart);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/m/findSparePart", method=RequestMethod.GET)
	public WSMSparePart findSparePart(@RequestParam("idPart") Long idPart) throws Exception {
		return mSparePartService.findWSMSparePart(idPart);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/m/getSparePartsListByIdMachine", method=RequestMethod.GET)
	public List<WSMSparePart> getSparePartsListByIdMachine(@RequestParam Long idMachine) throws Exception {
		List<WSMSparePart> ws = new ArrayList<WSMSparePart>();
		for(MSparePart mSparePart: mSparePartRepository.findMachineId(idMachine))
		{
			ws.add(mSparePartService.toWSMSparePart(mSparePart));
		}
		return ws;
		
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/m/sparePartsList", method=RequestMethod.POST)
	public WSTableData  sparePartsList(@RequestParam Long idMachine, @RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		List<WSMSparePart> ws = new ArrayList<WSMSparePart>();
		for(MSparePart mSparePart: mSparePartRepository.findMachineId(idMachine))
		{
			ws.add(mSparePartService.toWSMSparePart(mSparePart));
		}
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(ws.size()<start + length)
			end =ws.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			WSMSparePart w = ws.get(i);
			String saftyQty = (w.getSafetyQty()==null)?"":""+w.getSafetyQty();
			String qty = (w.getQty()==null)?"":""+w.getQty();
			String incoming = (w.getIncoming()==null)?"":""+w.getIncoming();
			String outgoing = (w.getOutgoing()==null)?"":""+w.getOutgoing();
			String remark =(w.getRemark()==null)?"":w.getRemark();
			String status = (w.getStatus()==null)?"":w.getStatus();
			String[] d = {w.getMaterial(),saftyQty,qty,incoming,outgoing,remark,status,""+w.getIdPart()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(ws.size());
		t.setRecordsFiltered(ws.size());
	    t.setData(lst);
	    return t;
	}
	
	
	
	
}