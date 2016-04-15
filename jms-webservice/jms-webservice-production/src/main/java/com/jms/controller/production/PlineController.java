package com.jms.controller.production;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.PLine;
import com.jms.domain.db.PWorkCenter;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.production.WSPLine;
import com.jms.domain.ws.production.WSPWorkCenter;
import com.jms.repositories.p.PLineRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.service.production.PlineService;
import com.jms.service.production.WorkCenterService;
import com.jms.web.security.SecurityUtils;




@RestController
@Transactional(readOnly=true)
public class PlineController {
	
	@Autowired private PlineService plineService;
	@Autowired private PLineRepository pLineRepository;
	@Autowired private SecurityUtils securityUtils;

	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/saveWSPLine", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSPLine savePLine(@RequestBody WSPLine wsPLine) throws Exception {
		return plineService.saveWSPLine(wsPLine);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/deleteWSPLine", method=RequestMethod.GET)
	public Valid deleteWSPLine(@RequestParam("lineId") Long lineId) {
		return plineService.deleteWSPLine(lineId);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findWSPLine", method=RequestMethod.GET)
	public WSPLine findWSPLine(@RequestParam("lineId") Long lineId) throws Exception {
		return plineService.findWSPLine(lineId);
		
	}
	

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/plineList", method=RequestMethod.GET)
	public WSTableData  getPLineList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		List<PLine> pLines =pLineRepository.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(pLines.size()<start + length)
			end =pLines.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			PLine w = pLines.get(i);
			String[] d = {""+w.getIdPline(),w.getPline(),""+w.getIdPline()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(pLines.size());
		t.setRecordsFiltered(pLines.size());
	    t.setData(lst);
	    return t;
	}
	
	


}