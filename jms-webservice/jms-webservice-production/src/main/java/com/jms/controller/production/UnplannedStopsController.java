package com.jms.controller.production;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jms.domain.db.PStopsPlan;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.production.WSPStopsPlan;
import com.jms.domain.ws.production.WSPUnplannedStops;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PStopsPlanRepository;
import com.jms.repositories.p.PUnplannedStopsRepository;
import com.jms.service.production.PStopsPlanService;
import com.jms.service.production.PUnplannedStopsService;
import com.jms.web.security.SecurityUtils;




@RestController
@Transactional(readOnly=true)
public class UnplannedStopsController {

	private static final Logger logger = LogManager.getLogger(UnplannedStopsController.class
			.getCanonicalName());
	@Autowired private PStopsPlanService pStopsPlanService;
	//@Autowired private PStopsCodeRepository pStopsCodeRepository;
	@Autowired
	private  PUnplannedStopsRepository pUnplannedStopsRepository;
	@Autowired
	private  PUnplannedStopsService pUnplannedStopsService;
	
	@Autowired private SecurityUtils securityUtils;

	@Autowired private PStatusDicRepository pStatusDicRepository;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/saveWSPUnplannedStops", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSPUnplannedStops saveWSPUnplannedStops(@RequestBody WSPUnplannedStops wsPUnplannedStops) throws Exception {
		return pUnplannedStopsService.saveWSPStopsPlan(wsPUnplannedStops);
	}
	
	
	
	/**type: 0 处理开始，1 处理结束， 2 结束确认*/
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/updateWSPUnplannedStops", method=RequestMethod.GET)
	public Valid updateWSPUnplannedStops(@RequestParam("machineId") Long machineId,@RequestParam("type") Long type) throws Exception {
		return pUnplannedStopsService.updateWSPStopsPlan(machineId,type);
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findWSPUnplannedStopsByCppId", method=RequestMethod.GET)
	public List<WSPUnplannedStops> findWSPUnplannedStopsByCppId(@RequestParam("cppId") Long cppId) throws Exception {

		return pUnplannedStopsService.findWSPUnplannedStopsByCppId(cppId);
	
	}
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findWSPUnplannedStopsBySubCodeId", method=RequestMethod.GET)
	public List<WSPUnplannedStops> findWSPUnplannedStopsBySubCodeId(@RequestParam("subCodeId") Long subCodeId) throws Exception {

		return pUnplannedStopsService.findWSPUnplannedStopsBySubCodeId(subCodeId);
	
	}
	

}