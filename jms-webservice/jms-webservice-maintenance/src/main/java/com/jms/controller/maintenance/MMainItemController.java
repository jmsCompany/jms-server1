package com.jms.controller.maintenance;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.MMainItem;
import com.jms.domain.db.MSparePart;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.m.WSMSparePart;
import com.jms.domain.ws.m.WSMainItem;
import com.jms.repositories.m.MMainItemRepository;
import com.jms.service.maintenance.MMainCycleService;
import com.jms.service.maintenance.MMainItemService;
import com.jms.service.maintenance.MStatusDicService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class MMainItemController {
	

	@Autowired private MStatusDicService mStatusDicService;
	

	@Autowired private MMainItemService mMainItemService;

	@Autowired private SecurityUtils securityUtils;
	
	@Autowired private MMainItemRepository  mMainItemRepository;

	@Transactional(readOnly = false)
	@RequestMapping(value="/m/saveMainItem", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid saveMainItem(@RequestBody WSMainItem wsMainItem) throws Exception  {
		return mMainItemService.saveWSMainItem(wsMainItem);
	}
	

	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/m/deleteMainItem", method=RequestMethod.GET)
	public Valid deleteMainItem(@RequestParam("idMainItem") Long idMainItem) {
		return mMainItemService.deleteWSMainItem(idMainItem);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/m/findMainItem", method=RequestMethod.GET)
	public WSMainItem findSparePart(@RequestParam("idMainItem") Long idMainItem) throws Exception {
		return mMainItemService.findWSMainItem(idMainItem);
		
	}
	


	
	@Transactional(readOnly = true)
	@RequestMapping(value="/m/mainItemList", method=RequestMethod.POST)
	public WSTableData  sparePartsList(@RequestParam Long idMachine, @RequestParam Long idDept,@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		List<MMainItem> items = mMainItemRepository.getByIdMachineAndIdDept(idMachine, idDept);
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(items.size()<start + length)
			end =items.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			MMainItem item = items.get(i);
			String mainCycle = (item.getMMainCycle()==null)?"":""+item.getMMainCycle().getMainCycle();
			String dept = (item.getMDept()==null)?"":""+item.getMDept().getDes();
			String machine = (item.getMMachine()==null)?"":item.getMMachine().getCode();
			String[] d = {mainCycle,dept,machine,item.getItem(),""+item.getIdMainItem()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(items.size());
		t.setRecordsFiltered(items.size());
	    t.setData(lst);
	    return t;
	}
	
	


}