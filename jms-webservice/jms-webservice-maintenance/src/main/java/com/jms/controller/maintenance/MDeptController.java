package com.jms.controller.maintenance;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.ws.WSSelectObj;
import com.jms.service.maintenance.MDeptService;
import com.jms.service.maintenance.MMainCycleService;
import com.jms.service.maintenance.MStatusDicService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class MDeptController {
	

	@Autowired private MStatusDicService mStatusDicService;
	

	@Autowired private MDeptService mDeptService;

	@Autowired private SecurityUtils securityUtils;

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/m/getDepts", method=RequestMethod.GET)
	public List<WSSelectObj> getDepts() {
		return mDeptService.getDepts();
	}

}