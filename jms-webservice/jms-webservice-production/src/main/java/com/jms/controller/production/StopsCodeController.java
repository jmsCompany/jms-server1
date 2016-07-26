package com.jms.controller.production;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.PStopsCode;
import com.jms.domain.db.PSubCode;
import com.jms.domain.db.PWorkCenter;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.p.WSPStopsCode;
import com.jms.domain.ws.p.WSPWorkCenter;
import com.jms.repositories.p.PStopsCodeRepository;
import com.jms.repositories.p.PSubCodeRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.service.production.PStopsCodeService;
import com.jms.service.production.WorkCenterService;
import com.jms.web.security.SecurityUtils;




@RestController
@Transactional(readOnly=true)
public class StopsCodeController {
	
	@Autowired private PStopsCodeService pStopsCodeService;
	//@Autowired private PStopsCodeRepository pStopsCodeRepository;
	@Autowired
	private  PSubCodeRepository pSubCodeRepository;
	
	@Autowired private SecurityUtils securityUtils;

	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/savePStopsCode", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSPStopsCode savePStopsCode(@RequestBody WSPStopsCode wsPStopsCode) throws Exception {
		return pStopsCodeService.saveWSPStopsCode(wsPStopsCode);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/deletePStopsCode", method=RequestMethod.GET)
	public Valid deletePStopsCode(@RequestParam("idStopsCode") Long idStopsCode) {
		return pStopsCodeService.deletePStopsCode(idStopsCode);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findPStopsCode", method=RequestMethod.GET)
	public WSPStopsCode findPStopsCode(@RequestParam("idStopsCode") Long idStopsCode) throws Exception {
		return pStopsCodeService.findWSPStopsCode(idStopsCode);
		
	}
	

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findPStopsCodeList", method=RequestMethod.POST)
	public WSTableData  findPStopsCodeList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		List<PSubCode> pStopscodes =pSubCodeRepository.findAll();
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(pStopscodes.size()<start + length)
			end =pStopscodes.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			PSubCode w = pStopscodes.get(i);
			String[] d = {w.getSubCode(),w.getSubDes(),""+w.getIdSubCode()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(pStopscodes.size());
		t.setRecordsFiltered(pStopscodes.size());
	    t.setData(lst);
	    return t;
	}
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getPStopsCodeObjsList", method=RequestMethod.GET)
	public List<WSSelectObj>  getPStopsCodeObjsList() throws Exception {	   
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		for(PSubCode pSubCode: pSubCodeRepository.findAll())
		{
			WSSelectObj w = new WSSelectObj(pSubCode.getIdSubCode(), pSubCode.getSubCode());
			ws.add(w);		
		}
		
	    return ws;
	}
	

}