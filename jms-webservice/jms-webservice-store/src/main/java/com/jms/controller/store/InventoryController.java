package com.jms.controller.store;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jms.domain.ws.store.WSInventoryInfo;
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

	
}