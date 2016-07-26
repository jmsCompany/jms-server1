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
import com.jms.domain.ws.p.WSPBom;
import com.jms.domain.ws.p.WSPWo;
import com.jms.repositories.p.PBomLabelRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.service.production.BomLabelService;
import com.jms.service.production.BomService;
import com.jms.service.production.WoService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class BomController {
	
	@Autowired private BomService bomService;
	@Autowired private BomLabelService bomLabelService;
	@Autowired private PWoRepository pWoRepository;
	@Autowired private PBomLabelRepository pBomLabelRepository;
	@Autowired private SecurityUtils securityUtils;

	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/saveBom", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSPBom saveWSPBom(@RequestBody WSPBom wsPBom) throws Exception {
		return bomLabelService.savePBomLabel(wsPBom);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/deleteBom", method=RequestMethod.GET)
	public Valid deleteBom(@RequestParam("bomLabelId") Long bomLabelId) {
		return bomLabelService.deletePWSPBom(bomLabelId);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findBom", method=RequestMethod.GET)
	public WSPBom findWo(@RequestParam("bomLabelId") Long bomLabelId) throws Exception {
		return bomLabelService.findWSPBom(bomLabelId);
		
	}
	

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getBomList", method=RequestMethod.POST)
	public WSTableData  getBomList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		List<WSPBom> wsPBoms =bomLabelService.findWSPBomList();
		
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(wsPBoms.size()<start + length)
			end =wsPBoms.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			WSPBom w = wsPBoms.get(i);
			String[] d = {w.getPno(),w.getRev(),w.getMaterial(),w.getCreationTime().toString(),""+w.getCreator(),""+w.getStatus(),""+w.getIdBomLabel()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(wsPBoms.size());
		t.setRecordsFiltered(wsPBoms.size());
	    t.setData(lst);
	    return t;
	}
	

}