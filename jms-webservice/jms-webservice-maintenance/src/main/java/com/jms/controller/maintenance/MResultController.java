package com.jms.controller.maintenance;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.PBomLabel;
import com.jms.domain.db.PWo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.p.WSPBom;
import com.jms.domain.ws.p.WSPWo;
import com.jms.domain.ws.s.WSSStatus;
import com.jms.repositories.p.PBomLabelRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.service.maintenance.MResultService;
import com.jms.service.maintenance.MStatusDicService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class MResultController {
	

	@Autowired private MResultService mResultService;

	@Autowired private SecurityUtils securityUtils;

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/m/getMResults", method=RequestMethod.GET)
	public List<WSSelectObj> getMResults() {
		return mResultService.getMResults();
	}

}