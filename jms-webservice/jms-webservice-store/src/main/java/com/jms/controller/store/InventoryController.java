package com.jms.controller.store;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.store.WSInventory;
import com.jms.domain.ws.store.WSInventoryInfo;
import com.jms.domain.ws.store.WSMaterial;
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
	@RequestMapping(value = "/s/inventorySummary", method = RequestMethod.GET)
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
	@RequestMapping(value = "/s/inventoryDetail", method = RequestMethod.GET)
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
			String[] d = { w.getStkName(), w.getPno(), w.getRev(),w.getDes(), w.getBinName(),w.getLotNo(),""+w.getBox(),""+w.getUQty(),""+w.getQty() };
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(is.size());
		t.setRecordsFiltered(is.size());
		t.setData(lst);
		return t;
	}
	
	
}