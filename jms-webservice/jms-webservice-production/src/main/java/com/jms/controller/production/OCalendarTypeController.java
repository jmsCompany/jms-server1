package com.jms.controller.production;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.OCalendarType;
import com.jms.domain.db.PBomLabel;
import com.jms.domain.db.PWo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.p.WSOCalendarTypes;
import com.jms.domain.ws.p.WSPBom;
import com.jms.domain.ws.p.WSPWo;
import com.jms.domain.ws.s.WSSStatus;
import com.jms.repositories.o.OCalendarTypeRepository;
import com.jms.repositories.p.PBomLabelRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.service.production.BomLabelService;
import com.jms.service.production.BomService;
import com.jms.service.production.OCalendarTypeService;
import com.jms.service.production.PStatusDicService;
import com.jms.service.production.WoService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class OCalendarTypeController {
	
	@Autowired private OCalendarTypeService oCalendarTypeService;
	@Autowired private OCalendarTypeRepository oCalendarTypeRepository;

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/o/getOCalendarTypes", method=RequestMethod.GET)
	public List<WSSelectObj> getOCalendarTypes() {
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		
		for(OCalendarType o: oCalendarTypeRepository.findAll())
		{
			WSSelectObj w = new WSSelectObj(o.getIdCalendarType(),o.getType());
			ws.add(w);
		}
		
		return ws;
		
	}

}