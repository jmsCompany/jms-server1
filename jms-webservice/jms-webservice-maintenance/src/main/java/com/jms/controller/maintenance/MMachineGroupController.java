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
import com.jms.domain.db.PLine;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.m.WSMachineGroup;
import com.jms.domain.ws.p.WSPCheckPlan;
import com.jms.domain.ws.p.WSPLine;
import com.jms.repositories.m.MMachineGroupRepository;
import com.jms.service.maintenance.MMachineGroupService;
import com.jms.service.maintenance.MMachineService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class MMachineGroupController {
	
	
	@Autowired private MMachineGroupService mMachineGroupService;
	@Autowired private MMachineGroupRepository mMachineGroupRepository;
	@Autowired private SecurityUtils securityUtils;
	private static final Logger logger = LogManager.getLogger(MMachineGroupController.class
			.getCanonicalName());
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/m/getMachineGroupsObjs", method=RequestMethod.GET)
	public List<WSSelectObj> getMachineGroupsObjs() {	
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		for(MMachineGroup g:mMachineGroupRepository.findByIdCompany( companyId)){
			WSSelectObj w = new WSSelectObj(g.getIdGroup(),g.getGroupName());
			ws.add(w);
		}
	   return ws;
	}

	
	@Transactional(readOnly = false)
	@RequestMapping(value="/m/saveMachineGroup", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSMachineGroup saveMachineGroup(@RequestBody WSMachineGroup wsMachineGroup) throws Exception  {
		return mMachineGroupService.saveMachineGroup(wsMachineGroup);
	}
	

	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/m/deleteMachineGroup", method=RequestMethod.GET)
	public Valid deleteWMachineGroup(@RequestParam("idMachineGroup") Long idMachineGroup) {
		return mMachineGroupService.deleteWSMachineGroup(idMachineGroup);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/m/findWSMachineGroup", method=RequestMethod.GET)
	public WSMachineGroup findWSMachineGroup(@RequestParam("idMachineGroup") Long idMachineGroup) throws Exception {
		return mMachineGroupService.getWSMachineGroup(idMachineGroup);
		
	}
	

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/m/MachineGroup", method=RequestMethod.POST)
	public WSTableData  getMachineGroup(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		List<MMachineGroup> mgs =mMachineGroupRepository.findByIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(mgs.size()<start + length)
			end =mgs.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			MMachineGroup w = mgs.get(i);
			String[] d = {w.getGroupName(),""+w.getIdGroup()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(mgs.size());
		t.setRecordsFiltered(mgs.size());
	    t.setData(lst);
	    return t;
	}
	
	
	
	
}