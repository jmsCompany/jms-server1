package com.jms.controller.production;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.PCheckTime;
import com.jms.domain.db.PLine;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.p.WSPCheckTime;
import com.jms.domain.ws.p.WSPLine;
import com.jms.repositories.p.PCheckTimeRepository;
import com.jms.repositories.p.PLineRepository;
import com.jms.service.production.PCheckTimeService;
import com.jms.service.production.PlineService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class CheckTimeController {
	
	@Autowired private PCheckTimeService pCheckTimeService;
	@Autowired private PCheckTimeRepository pCheckTimeRepository;
	@Autowired private SecurityUtils securityUtils;

	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/saveCheckTime", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid saveCheckTime(@RequestBody WSPCheckTime wsPCheckTime) throws Exception {
		return pCheckTimeService.saveWSPCheckTime(wsPCheckTime);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/deleteCheckTime", method=RequestMethod.GET)
	public Valid deleteCheckTime(@RequestParam("checkTimeId") Long checkTimeId) {
		return pCheckTimeService.deletePCheckTime(checkTimeId);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findCheckTime", method=RequestMethod.GET)
	public WSPCheckTime findCheckTime(@RequestParam("checkTimeId") Long checkTimeId) throws Exception {
		return pCheckTimeService.findWSPCheckTime(checkTimeId);
		
	}
	

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getCheckTimeList", method=RequestMethod.POST)
	public WSTableData  getCheckTimeList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		List<PCheckTime> pChenTimes =pCheckTimeRepository.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(pChenTimes.size()<start + length)
			end =pChenTimes.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			PCheckTime w = pChenTimes.get(i);
			String[] d = {w.getMMachine().getCode(),w.getMMachine().getSpec(),""+w.getInterval1(),w.getPUTime().getUTime(),""+w.getIdCheckTime()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(pChenTimes.size());
		t.setRecordsFiltered(pChenTimes.size());
	    t.setData(lst);
	    return t;
	}
	
	


}