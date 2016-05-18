package com.jms.controller.production;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.ws.production.WSPActualSetup;
import com.jms.service.production.ActualSetupService;



@RestController
@Transactional(readOnly=true)
public class ActualSetupController {
	
	@Autowired private ActualSetupService actualSetupService;


	@Transactional(readOnly = false)
	@RequestMapping(value="/p/saveWSPActualSetup", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSPActualSetup saveWSPActualSetup(@RequestBody WSPActualSetup wsPActualSetup) {
		return actualSetupService.saveWSPActualSetup(wsPActualSetup);
	}



	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findWSPActualSetups", method=RequestMethod.GET)
	public List<WSPActualSetup> findWSPActualSetups(@RequestParam("cppId") Long cppId){
		return actualSetupService.findWSPActualSetups(cppId);
		
	}
	
	

}