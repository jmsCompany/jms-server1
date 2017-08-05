package com.jms.controller.production;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.PWorkCenter;
import com.jms.domain.db.SBin;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.p.WSPWorkCenter;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.repositories.s.SBinRepository;
import com.jms.service.production.WorkCenterService;
import com.jms.web.security.SecurityUtils;




@RestController
@Transactional(readOnly=true)
public class WorkCenterController {
	
	@Autowired private WorkCenterService workCenterService;
	@Autowired private PWorkCenterRepository pWorkCenterRepository;
	@Autowired private SecurityUtils securityUtils;

	@Autowired private SBinRepository sBinRepository;
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/saveWorkCenter", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSPWorkCenter saveWSFCostCenter(@RequestBody WSPWorkCenter wsPWorkCenter) throws Exception {
		return workCenterService.saveWSPWorkCenter(wsPWorkCenter);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/deleteWorkCenter", method=RequestMethod.GET)
	public Valid deleteWorkCenter(@RequestParam("workCenterId") Long workCenterId) {
		return workCenterService.deletePWorkCenter(workCenterId);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findWorkCenter", method=RequestMethod.GET)
	public WSPWorkCenter findCostCenter(@RequestParam("workCenterId") Long workCenterId) throws Exception {
		return workCenterService.findWSPworkCenter(workCenterId);
		
	}
	

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/workCenterList", method=RequestMethod.POST)
	public WSTableData  getWorkCenterList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		List<PWorkCenter> pWorkCenters =pWorkCenterRepository.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(pWorkCenters.size()<start + length)
			end =pWorkCenters.size();
		else
			end =start + length;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		for (int i = start; i < end; i++) {
			PWorkCenter w = pWorkCenters.get(i);
			
			String ctime = "";
			if(w.getCreationTime()!=null)
			{
				
				ctime= formatter.format(w.getCreationTime());
			}
			String[] d = {w.getWorkCenter(),ctime,""+w.getUsers().getName(),""+w.getIdWc()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(pWorkCenters.size());
		t.setRecordsFiltered(pWorkCenters.size());
	    t.setData(lst);
	    return t;
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getWorkCenterList", method=RequestMethod.GET)
	public List<WSSelectObj>  getWorkCenterList() throws Exception {	   
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		
//		for(PWorkCenter p: pWorkCenterRepository.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany()))
//		 {
//			WSSelectObj w = new WSSelectObj(p.getIdWc(),p.getWorkCenter());
//			ws.add(w);
//		 }
		
		for(SBin bin:sBinRepository.getByCompanyIdAndStkType(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), 2l))
		
		{
			WSSelectObj w = new WSSelectObj(bin.getIdBin(),bin.getBin());
			ws.add(w);
		}
	    return ws;
	}


}