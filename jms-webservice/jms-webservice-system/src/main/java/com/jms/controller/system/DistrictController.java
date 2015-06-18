package com.jms.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.ws.WSCity;
import com.jms.domain.ws.WSDistrict;
import com.jms.domain.ws.WSProvince;
import com.jms.service.system.DistrictService;

@RestController
public class DistrictController {
	
	@Autowired  
	private DistrictService districtService;
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/provinces", method=RequestMethod.GET)
	public List<WSProvince> getProvinces() throws Exception{
		
		return districtService.getProvinces();
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/provinces/{idProvince}/cities", method=RequestMethod.GET)
	public List<WSCity> getCites(@PathVariable("idProvince") Integer idProvince) throws Exception {
		
		return districtService.getCites(idProvince);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/cities/{idCity}/districts", method=RequestMethod.GET)
	public List<WSDistrict> getDistricts(@PathVariable("idCity")  Integer idCity) throws Exception
	{
		return districtService.getDistricts(idCity);
	}
	
}