package com.jms.controller.production;


import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jms.domain.db.PWo;
import com.jms.domain.db.SMaterial;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.production.WSPMr;
import com.jms.domain.ws.production.WSPWo;
import com.jms.domain.ws.store.WSMaterialQty;
import com.jms.repositories.p.PWoRepository;
import com.jms.service.production.PMrService;
import com.jms.web.security.SecurityUtils;
import com.fasterxml.jackson.databind.ObjectMapper; 


@RestController
@Transactional(readOnly=true)
public class PMrController {
	
	@Autowired private PMrService pMrService;
	@Autowired private PWoRepository pWoRepository;
	@Autowired private SecurityUtils securityUtils;
	private static final Logger logger = LogManager.getLogger(PMrController.class
			.getCanonicalName());
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/saveWSPMrs", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid saveWSPMrs(@RequestBody List<WSPMr> wsPMrs) throws Exception {
		ObjectMapper m = new ObjectMapper();
		logger.debug(m.writeValueAsString(wsPMrs));
		return pMrService.saveWSPMrs(wsPMrs);
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