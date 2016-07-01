package com.jms.controller.production;


import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jms.domain.db.PMr;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.production.WSPMr;
import com.jms.repositories.p.PMrRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.service.production.PMrService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class PMrController {
	
	@Autowired private PMrService pMrService;
	@Autowired private PWoRepository pWoRepository;
	@Autowired private SecurityUtils securityUtils;

	@Autowired private PMrRepository pMrRepository;
	@Autowired private PStatusDicRepository pStatusDicRepository;
	
	private static final Logger logger = LogManager.getLogger(PMrController.class
			.getCanonicalName());
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/saveWSPMrs", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid saveWSPMrs(@RequestBody List<WSPMr> wsPMrs) throws Exception {

		return pMrService.saveWSPMrs(wsPMrs);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/updateWSPMrStatus", method=RequestMethod.GET)
	public Valid updateWSPMrStatus(@RequestParam("idPmr") Long idPmr,@RequestParam("statusId") Long statusId) throws Exception {
		Valid v = new Valid();
		PMr pmr = pMrRepository.findOne(idPmr);
		if(pmr==null)
		{
			v.setValid(false);
			v.setMsg("没有此需料");
			return v;
		}
	    
		pmr.setFt(new Date());
		pmr.setPStatusDic(pStatusDicRepository.findOne(statusId));
		pMrRepository.save(pmr);
		v.setValid(true);
		return v;
	}
	
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/updateWSPMrStatusByCppId", method=RequestMethod.GET)
	public Valid updateWSPMrStatusByCppId(@RequestParam("cppId") Long cppId,@RequestParam("type") Long type, @RequestParam("statusId") Long statusId) throws Exception {
		Valid v = new Valid();
		List<PMr> pmrs = pMrRepository.getByTypeAndCppId(type, cppId);
	 
		//logger.debug("cppId: " + cppId +", type: " + type + ", statusId: " + statusId);
		for(PMr pmr:pmrs)
		{
			pmr.setFt(new Date());
			pmr.setPStatusDic(pStatusDicRepository.findOne(statusId));
			pMrRepository.save(pmr);
		}
				
		v.setValid(true);
		return v;
	}
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getWSPMrsByType", method=RequestMethod.GET)
	public List<WSPMr> getWSPMrsByType(@RequestParam Long type) throws Exception {
		return pMrService.getWSPMrsByType(type);
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getWSPMrById", method=RequestMethod.GET)
	public WSPMr getWSPMrById(@RequestParam Long idMr) throws Exception {
		return pMrService.getWSPMrById(idMr);
	}
	
	
}