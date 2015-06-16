package com.jms.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.Config;
import com.jms.domain.ws.WSCity;
import com.jms.domain.ws.WSDistrict;
import com.jms.domain.ws.WSProvince;
import com.jms.domain.ws.WSSysDicD;
import com.jms.service.system.DistrictService;
import com.jms.system.IDicDService;

@RestController
public class DicController {
	
	@Autowired  @Qualifier("dicDService")
	private IDicDService dicDService;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="dic/companynatures", method=RequestMethod.GET)
	public List<WSSysDicD> getNatures() throws Exception
	{
		return dicDService.getDicsByType(Config.companyNature);
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value="dic/companysizes", method=RequestMethod.GET)
	public List<WSSysDicD> getSizes() throws Exception
	{
		return dicDService.getDicsByType(Config.companySize);
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value="dic/companytypes", method=RequestMethod.GET)
	public List<WSSysDicD> getTypes() throws Exception
	{
		return dicDService.getDicsByType(Config.companyType);
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value="dic/languages", method=RequestMethod.GET)
	public List<WSSysDicD> getLanguages() throws Exception
	{
		return dicDService.getDicsByType(Config.lang);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="dic/taskTypes", method=RequestMethod.GET)
	public List<WSSysDicD> getTaskTypes()throws Exception
	{
		 return dicDService.getDicsByType(Config.taskType);
	}
	
}