package com.jms.controller.production;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jms.domain.ws.WSSelectObj;
import com.jms.service.production.PPUTimeService;



@RestController
@Transactional(readOnly=true)
public class PutimeController {
	
	@Autowired private PPUTimeService pPUTimeService;

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getPutimesObj", method=RequestMethod.GET)
	public List<WSSelectObj> getPutimesObj() {
		return pPUTimeService.findPutimess();
	}

}