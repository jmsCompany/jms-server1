package com.jms.controller.production;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jms.domain.db.PWip;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.production.WSPWip;
import com.jms.repositories.p.PWipRepository;
import com.jms.service.production.PWipService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class PWipController {
	
	@Autowired private PWipService pWipService;
	@Autowired private PWipRepository pWipRepository;
	@Autowired private SecurityUtils securityUtils;

	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/saveWip", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSPWip saveWip(@RequestBody WSPWip wsPWip) throws Exception {
		return pWipService.saveWSPWip(wsPWip);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/deleteWip", method=RequestMethod.GET)
	public Valid deleteWip(@RequestParam("wipId") Long wipId) {
		return pWipService.deletePWip(wipId);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findWip", method=RequestMethod.GET)
	public WSPWip findWip(@RequestParam("wipId") Long wipId) throws Exception {
		return pWipService.findWSPWip(wipId);
		
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/wipList", method=RequestMethod.GET)
	public WSTableData  getWipList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		List<PWip> pWips =pWipRepository.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(pWips.size()<start + length)
			end =pWips.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			PWip w = pWips.get(i);
			String[] d = {w.getWip(),w.getPStatusDic().getName(),""+w.getIdWip()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(pWips.size());
		t.setRecordsFiltered(pWips.size());
	    t.setData(lst);
	    return t;
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getWipObjsList", method=RequestMethod.GET)
	public List<WSSelectObj>  getWipObjsList() throws Exception {	   
	    return pWipService.findWipObjs();
	}


}