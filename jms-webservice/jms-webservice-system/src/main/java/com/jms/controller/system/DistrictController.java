package com.jms.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jms.domain.ws.WSSelectObj;
import com.jms.service.system.DistrictService;

@RestController
public class DistrictController {
	
	@Autowired  
	private DistrictService districtService;
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/dic/provinces", method=RequestMethod.GET)
	public List<WSSelectObj> getProvinces() throws Exception{
		
		return districtService.getProvinces();
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/dic/provinces/{idProvince}/cities", method=RequestMethod.GET)
	public List<WSSelectObj> getCites(@PathVariable("idProvince") Long idProvince) throws Exception {
		
		return districtService.getCites(idProvince);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/dic/cities/{idCity}/districts", method=RequestMethod.GET)
	public List<WSSelectObj> getDistricts(@PathVariable("idCity")  Long idCity) throws Exception
	{
		return districtService.getDistricts(idCity);
	}
	
}