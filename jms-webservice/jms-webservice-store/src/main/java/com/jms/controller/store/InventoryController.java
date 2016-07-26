package com.jms.controller.store;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.s.WSInventory;
import com.jms.domain.ws.s.WSInventoryInfo;
import com.jms.domain.ws.s.WSMaterial;
import com.jms.service.store.SInventoryService;

@RestController
@Transactional(readOnly=true)
public class InventoryController {
	
	@Autowired private SInventoryService sInventoryService;
	private static final Logger logger = LogManager.getLogger(InventoryController.class
			.getCanonicalName());

	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findInventorySummaryByMaterialAndStk", method=RequestMethod.GET)
	public List<WSInventoryInfo> findInventorySummaryByMaterialAndStk(@RequestParam(required=false,value="materialId") Long materialId,@RequestParam(required=false,value="stkId") Long stkId) throws Exception {
		return sInventoryService.findInventorySummaryByMaterialAndStk(materialId, stkId);
	}

	
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/inventorySummary", method = RequestMethod.POST)
	public WSTableData inventorySummary(@RequestParam(required=false,value="materialId") Long materialId,
			@RequestParam(required=false,value="stkId") Long stkId, 
			@RequestParam Integer draw,
			@RequestParam Integer start,
			@RequestParam Integer length) throws Exception {

		List<WSInventoryInfo> is= sInventoryService.findInventorySummaryByMaterialAndStk(materialId, stkId);
		List<String[]> lst = new ArrayList<String[]>();
		int end = 0;
		if (is.size() < start + length)
			end = is.size();
		else
			end = start + length;
		for (int i = start; i < end; i++) {
			WSInventoryInfo w = is.get(i);
			String[] d = { w.getStkName(), w.getPno(), w.getRev(),w.getDes(), "" + w.getQty() };
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(is.size());
		t.setRecordsFiltered(is.size());
		t.setData(lst);
		return t;
	}
	
	

	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/inventoryDetail", method = RequestMethod.POST)
	public WSTableData inventoryDetail(@RequestParam(required=false,value="materialId") Long materialId,
			@RequestParam(required=false,value="stkId") Long stkId, 
			@RequestParam Integer draw,
			@RequestParam Integer start,
			@RequestParam Integer length) throws Exception {

		List<WSInventory> is= sInventoryService.findInventoryDetailByMaterialAndStk(materialId, stkId);
		List<String[]> lst = new ArrayList<String[]>();
		int end = 0;
		if (is.size() < start + length)
			end = is.size();
		else
			end = start + length;
		for (int i = start; i < end; i++) {
			WSInventory w = is.get(i);
			String[] d = { w.getStkName(), w.getPno(), w.getRev(),w.getDes(), w.getBinName(),w.getLotNo(),""+w.getQty() };
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(is.size());
		t.setRecordsFiltered(is.size());
		t.setData(lst);
		return t;
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/findInventoryDetails", method = RequestMethod.GET)
	public List<WSInventory> findInventoryDetails(@RequestParam(required=false,value="materialId") Long materialId) throws Exception {

		return sInventoryService.findInventoryDetailByMaterialAndStk(materialId, null);
		
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/findInventoryDetailsByMaterialIdAndStkId", method = RequestMethod.GET)
	public List<WSInventory> findInventoryDetailsByMaterialIdAndStkId(@RequestParam(required=false,value="materialId") Long materialId,@RequestParam(required=false,value="stkId") Long stkId) throws Exception {

		return sInventoryService.findInventoryDetailByMaterialAndStk(materialId, stkId);
		
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/findInventoryDetailsObjs", method = RequestMethod.GET)
	public List<WSSelectObj> findInventoryDetailsObjs(@RequestParam(required=false,value="materialId") Long materialId) throws Exception {

		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		List<WSInventory> wsis = sInventoryService.findInventoryDetailByMaterialAndStk(materialId, null);
		for(WSInventory i: wsis)
		{
			WSSelectObj w = new WSSelectObj(i.getInventoryId(),i.getStkName()+"_"+i.getBinName()+", 库存: " +i.getQty());
			ws.add(w);
		}
		
		return ws;
		
	}
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/findInventoryDetailsObjsByMaterialIdAndStkId", method = RequestMethod.GET)
	public List<WSSelectObj> findInventoryDetailsObjsByMaterialIdAndStkId(@RequestParam(required=false,value="materialId") Long materialId,@RequestParam(required=false,value="stkId") Long stkId) throws Exception {

		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		List<WSInventory> wsis = sInventoryService.findInventoryDetailByMaterialAndStk(materialId, stkId);
		for(WSInventory i: wsis)
		{
			WSSelectObj w = new WSSelectObj(i.getInventoryId(),i.getStkName()+"_"+i.getBinName()+", 库存: " +i.getQty());
			ws.add(w);
		}
		
		return ws;
		
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/findInventoryDetailsByMaterialAndStk", method = RequestMethod.GET)
	public List<WSInventory> findInventoryDetailsByMaterialAndStk(@RequestParam(required=false,value="materialId") Long materialId,
	@RequestParam(required=false,value="stkId") Long stkId) throws Exception {

		return sInventoryService.findInventoryDetailByMaterialAndStk(materialId, stkId);
		
	}
	
	
}