package com.jms.controller.production;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.PCPp;
import com.jms.domain.db.PCheckTime;
import com.jms.domain.db.PLine;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.production.WSPCpp;
import com.jms.domain.ws.production.WSPLine;
import com.jms.domain.ws.production.WSPMr;
import com.jms.domain.ws.store.WSMaterialQty;
import com.jms.repositories.p.PCPpRepository;
import com.jms.repositories.p.PCheckTimeRepository;
import com.jms.repositories.p.PLineRepository;
import com.jms.service.production.PCppService;
import com.jms.service.production.PlineService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class CppController {
	
	@Autowired private PCppService pCppService;
	@Autowired private PCPpRepository pCPpRepository;
	@Autowired private SecurityUtils securityUtils;

	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/saveWSPCpp", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSPCpp saveCheckTime(@RequestBody WSPCpp wsPCpp) throws Exception {
		return pCppService.saveWSPCPp(wsPCpp);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/deleteWSCpp", method=RequestMethod.GET)
	public Valid deleteCpp(@RequestParam("cppId") Long cppId) {
		return pCppService.deletePCpp(cppId);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findCpp", method=RequestMethod.GET)
	public WSPCpp findCpp(@RequestParam("cppId") Long cppId) throws Exception {
		return pCppService.findWSCPp(cppId);
		
	}
	

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getCppList", method=RequestMethod.GET)
	public WSTableData  getCheckTimeList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		List<PCPp> pCpps =pCPpRepository.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(pCpps.size()<start + length)
			end =pCpps.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			PCPp w = pCpps.get(i);
			String[] d = {""+w.getCPpCode(),w.getPWo().getWoNo(),w.getPRoutineD().getRouteNo(),w.getPWo().getSSo().getSMaterial().getPno(),w.getPWo().getSSo().getSMaterial().getRev(),""+w.getPWo().getQty(),w.getPShiftPlanD().getShift(),w.getPlanSt().toString(),w.getPlanFt().toString(),w.getMMachine().getCode()+""
					+ "-"+w.getMMachine().getSpec(),w.getUsers().getName(),""+w.getQty(),""+w.getIdCPp()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(pCpps.size());
		t.setRecordsFiltered(pCpps.size());
	    t.setData(lst);
	    return t;
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findWSPMrsByCppId", method=RequestMethod.GET)
	public List<WSPMr> findWSPMrsByCppId(@RequestParam("cppId") Long cppId) throws Exception {
		return pCppService.findWSPMrsByCppId(cppId);
		
	}
	


}