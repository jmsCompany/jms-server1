package com.jms.controller.production;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.OCalendar;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.p.WSOCalendar;
import com.jms.domain.ws.p.WSPActualSetup;
import com.jms.domain.ws.p.WSPBom;
import com.jms.repositories.o.OCalendarRepository;
import com.jms.service.production.OCalendarService;
import com.jms.web.security.SecurityUtils;



@RestController
@Transactional(readOnly=true)
public class OCalendarController {
	
	@Autowired private OCalendarService oCalendarService;
	@Autowired private OCalendarRepository oCalendarRepository;
	@Autowired private SecurityUtils securityUtils;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/o/saveWSOCalendar", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSOCalendar saveWSOCalendar(@RequestBody WSOCalendar wsOCalendar) throws Exception {
		return oCalendarService.saveWSOCalendar(wsOCalendar);
	}


	@Transactional(readOnly = true)
	@RequestMapping(value="/o/findWSOCalendar", method=RequestMethod.GET)
	public WSOCalendar findWSOCalendar(@RequestParam("idCalendar") Long idCalendar) throws Exception{
		return oCalendarService.findWSOCalendar(idCalendar);
		
	}
	@Transactional(readOnly = false)
	@RequestMapping(value="/o/deleteWSOCalendar", method=RequestMethod.GET)
	public Valid deleteWSOCalendar(@RequestParam("idCalendar") Long idCalendar) {
		return oCalendarService.deleteWSOCalendar(idCalendar);
		
	}
	@Transactional(readOnly = true)
	@RequestMapping(value="/o/getWSOCalendarList", method=RequestMethod.POST)
	public WSTableData  getWSOCalendarList(
			@RequestParam Long year,
			@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<OCalendar> oCalendarList =oCalendarRepository.findByCompanyIdAndYear(companyId,year);
		 SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		    
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(oCalendarList.size()<start + length)
			end =oCalendarList.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			OCalendar w = oCalendarList.get(i);
			
			
			String[] d = {""+w.getYear(),myFmt.format(w.getDate()),w.getOCalendarType().getType(),w.getName(),""+w.getIdCalendar()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(oCalendarList.size());
		t.setRecordsFiltered(oCalendarList.size());
	    t.setData(lst);
	    return t;
	}
	
}