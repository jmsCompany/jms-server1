package com.jms.controller.production;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.PBomLabel;
import com.jms.domain.db.PWo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.production.WSPBom;
import com.jms.domain.ws.production.WSPWo;
import com.jms.domain.ws.store.WSSStatus;
import com.jms.repositories.p.PBomLabelRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.service.production.BomLabelService;
import com.jms.service.production.BomService;
import com.jms.service.production.PStatusDicService;
import com.jms.service.production.WoService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class PStatusController {
	
	@Autowired private BomService bomService;
	@Autowired private PStatusDicService pStatusDicService;
	@Autowired private PWoRepository pWoRepository;
	@Autowired private PBomLabelRepository pBomLabelRepository;
	@Autowired private SecurityUtils securityUtils;

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getPStatusList", method=RequestMethod.GET)
	public List<WSSStatus> getPStatusList(@RequestParam("source") String source) {
		return pStatusDicService.getSStatus(source);
	}

}