package com.jms.controller.store;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.SMaterial;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.store.WSSpo;
import com.jms.domain.ws.store.WSSpoMaterial;
import com.jms.domain.ws.store.WSSpoRemark;
import com.jms.service.store.SpoMaterialService;
import com.jms.service.store.SpoService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class SpoController {
	

	@Autowired private SecurityUtils securityUtils;
	@Autowired private SpoService spoService;
	@Autowired private SpoMaterialService spoMaterialService;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/saveSpo", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid saveSpo(@RequestBody WSSpo wsSpo) throws Exception {
		//System.out.println("save Spo!");
		return spoService.saveSpo(wsSpo);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/saveSpoRemark", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid saveSpoRemark(@RequestBody WSSpoRemark wsSpoRemark) throws Exception {
		return spoService.saveSpoRemark(wsSpoRemark);
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/deleteSpo", method=RequestMethod.GET) 
	public Valid deleteSpo(@RequestParam("spoId") Long spoId) {
		return spoService.deleteSpo(spoId);
	}


	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findSpo", method=RequestMethod.GET)
	public WSSpo findWSSpo(@RequestParam("spoId") Long spoId) throws Exception {
		return spoService.findSpo(spoId);
	}
	

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/spoMaterialList", method=RequestMethod.GET)
	public WSTableData  getSpoList( @RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<WSSpoMaterial> wsSpoMaterials = spoMaterialService.findWSSpos(companyId);
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(wsSpoMaterials.size()<start + length)
			end =wsSpoMaterials.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			WSSpoMaterial w = wsSpoMaterials.get(i);
			String[] d = {w.getCodePo(),""+w.getDateOrder(),w.getUsername(),w.getCodeCo(),w.getsStatus(),w.getsMaterial(),w.getRev(),w.getDes(),w.getUnit(),""+w.getQtyPo(),""+w.getTotalPrice(),w.getDeliveryDate().toString(),""+w.getQtyReceived(),""+w.getsPoId()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(wsSpoMaterials.size());
		t.setRecordsFiltered(wsSpoMaterials.size());
	    t.setData(lst);
	    return t;
	}

	@Transactional(readOnly = true)
	@RequestMapping(value="/s/spoList", method=RequestMethod.GET)
	public List<WSSelectObj> findSpoList(@RequestParam("codeCo") Long codeCo) throws Exception {
		return spoService.findSpoListByCodeCo(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), codeCo);
	}
	

	
}