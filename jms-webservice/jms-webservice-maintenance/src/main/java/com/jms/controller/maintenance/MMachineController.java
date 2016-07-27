package com.jms.controller.maintenance;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jms.domain.ws.WSSelectObj;
import com.jms.service.maintenance.MMachineService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class MMachineController {
	
	
	@Autowired private MMachineService mMachineService;
	@Autowired private SecurityUtils securityUtils;
	private static final Logger logger = LogManager.getLogger(MMachineController.class
			.getCanonicalName());
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/m/getMachinesObjs", method=RequestMethod.GET)
	public List<WSSelectObj> getMachinesObjs() throws Exception {	
	   return mMachineService.getMachinesObj();
	}

	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/m/m/getMachinesObjsByLineId", method=RequestMethod.GET)
	public List<WSSelectObj> getMachinesObjsByLineId(@RequestParam("lineId") Long lineId) {	
	   return mMachineService.getMachinesObjByLineId(lineId);
	}

	
}