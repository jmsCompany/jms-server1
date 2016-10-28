package com.jms.controller.production;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.MMachine;
import com.jms.domain.db.OOvertime;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.p.WSOOvertime;
import com.jms.repositories.m.MMachineRepository;
import com.jms.repositories.o.OOvertimeRepository;
import com.jms.service.production.OOvertimeService;
import com.jms.web.security.SecurityUtils;



@RestController
@Transactional(readOnly=true)
public class OOvertimeController {
	
	@Autowired private OOvertimeService oOvertimeService;
	@Autowired private OOvertimeRepository oOvertimeRepository;
	@Autowired private SecurityUtils securityUtils;
	@Autowired private MMachineRepository mMachineRepository;
	@Transactional(readOnly = false)
	@RequestMapping(value="/o/saveWSOOvertime", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSOOvertime saveWSOOvertime(@RequestBody WSOOvertime wsOOvertime) throws Exception {
		return oOvertimeService.saveWSOOvertime(wsOOvertime);
	}


	@Transactional(readOnly = true)
	@RequestMapping(value="/o/findWSOOvertime", method=RequestMethod.GET)
	public WSOOvertime findWSOOvertime(@RequestParam("idOvertime") Long idOvertime) throws Exception{
		return oOvertimeService.findWSOOvertime(idOvertime);
		
	}
	@Transactional(readOnly = false)
	@RequestMapping(value="/o/deleteWSOOvertime", method=RequestMethod.GET)
	public Valid deleteWSOCalendar(@RequestParam("idOvertime") Long idOvertime) {
		return oOvertimeService.deleteWSOOvertime(idOvertime);
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/o/getWSOOvertimeList", method=RequestMethod.POST)
	public WSTableData  getWSOOvertimeList(
			@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<OOvertime> oOvertimeList =oOvertimeRepository.findByCompanyId(companyId);
		 SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		    
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(oOvertimeList.size()<start + length)
			end =oOvertimeList.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			OOvertime w = oOvertimeList.get(i);
			MMachine m = mMachineRepository.findOne(w.getIdMachine());
			
			String os =(w.getOvertimeStart()==null)?"":myFmt.format(w.getOvertimeStart());
			String oe =(w.getOvertimeEnd()==null)?"":myFmt.format(w.getOvertimeEnd());
			String ct= (w.getCreationTime()==null)?"":myFmt.format(w.getCreationTime());
			
			String[] d = {m.getCode()+"_" + m.getSpec(), os,oe,ct,""+w.getIdOvertime()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(oOvertimeList.size());
		t.setRecordsFiltered(oOvertimeList.size());
	    t.setData(lst);
	    return t;
	}
	
}