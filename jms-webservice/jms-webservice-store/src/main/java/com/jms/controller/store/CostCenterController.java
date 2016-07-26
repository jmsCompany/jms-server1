package com.jms.controller.store;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.f.WSFCostCenter;
import com.jms.service.store.CostCenterService;



@RestController
@Transactional(readOnly=true)
public class CostCenterController {
	
	@Autowired private CostCenterService costCenterService;

	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/saveCostCenter", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSFCostCenter saveWSFCostCenter(@RequestBody WSFCostCenter wsFCostCenter) throws Exception {
		return costCenterService.saveWSFCostCenter(wsFCostCenter);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/deleteCostCenter", method=RequestMethod.GET)
	public Valid deleteCostCenter(@RequestParam("costCenterId") Long costCenterId) {
		return costCenterService.deleteFCostCenter(costCenterId);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findCostCenter", method=RequestMethod.GET)
	public WSFCostCenter findCostCenter(@RequestParam("costCenterId") Long costCenterId) throws Exception {
		return costCenterService.findFCostCenter(costCenterId);
		
	}
	

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/costCenterList", method=RequestMethod.POST)
	public WSTableData  getCostCenterList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		List<WSFCostCenter> wsFCostCenters = costCenterService.getCostCenterList();
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(wsFCostCenters.size()<start + length)
			end =wsFCostCenters.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			WSFCostCenter w = wsFCostCenters.get(i);
			String[] d = {""+w.getCostCenterNo(),w.getDes(),""+w.getIdCostCenter()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(wsFCostCenters.size());
		t.setRecordsFiltered(wsFCostCenters.size());
	    t.setData(lst);
	    return t;
	}
	
	


}