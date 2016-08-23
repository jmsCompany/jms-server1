package com.jms.controller.maintenance;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.ws.WSSelectObj;
import com.jms.service.maintenance.MMainCycleService;
import com.jms.service.maintenance.MStatusDicService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class MMainCycleController {
	

	@Autowired private MStatusDicService mStatusDicService;
	

	@Autowired private MMainCycleService mMainCycleService;

	@Autowired private SecurityUtils securityUtils;

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/m/getMainCycles", method=RequestMethod.GET)
	public List<WSSelectObj> getMainCycles() {
		return mMainCycleService.getMainCycles();
	}

}