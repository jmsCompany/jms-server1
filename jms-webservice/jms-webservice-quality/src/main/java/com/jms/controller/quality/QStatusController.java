package com.jms.controller.quality;


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
import com.jms.domain.ws.p.WSPBom;
import com.jms.domain.ws.p.WSPWo;
import com.jms.domain.ws.s.WSSStatus;
import com.jms.repositories.p.PBomLabelRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.service.quality.QStatusDicService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class QStatusController {
	

	@Autowired private QStatusDicService qStatusDicService;

	@Autowired private SecurityUtils securityUtils;

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/q/getQStatusList", method=RequestMethod.GET)
	public List<WSSStatus> getQStatusList(@RequestParam("source") String source) {
		return qStatusDicService.getSStatus(source);
	}

}