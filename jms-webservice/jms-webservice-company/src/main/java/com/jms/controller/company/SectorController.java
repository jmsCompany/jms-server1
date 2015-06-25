package com.jms.controller.company;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jms.domain.ws.WSSector;
import com.jms.domain.ws.WSUser;
import com.jms.service.company.SectorService;
import com.jms.user.IUserService;
import com.jms.web.security.SecurityUtils;

@RestController
public class SectorController {
	
	@Autowired
	private SectorService sectorService;
	@Autowired @Qualifier("iUserServiceImpl")
	private IUserService iUserServiceImpl;
	@Autowired 
	private  SecurityUtils securityUtils;
	private static final Logger logger = LogManager.getLogger(SectorController.class.getCanonicalName());
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/sector/save", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public WSSector addSector(@RequestBody WSSector wsSector) throws Exception
	{
		return sectorService.save(wsSector);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/sectors", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<WSSector> getSectors(@RequestParam("idCompany") Long idCompany) throws Exception
	{
		return sectorService.getSectorsByIdCompany(idCompany);
	}
	@Transactional(readOnly = true)
	@RequestMapping(value="/sector/view/{idSector}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public WSSector getSector(@PathVariable("idSector") Long idSector) throws Exception
	{
		return sectorService.getSector(idSector);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/sector/{idSector}/members", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<WSUser> getUsersByIdSector(@PathVariable("idSector") Long idSector) throws Exception
	{
		return iUserServiceImpl.getUsersByIdSector(idSector);
	}

	
}