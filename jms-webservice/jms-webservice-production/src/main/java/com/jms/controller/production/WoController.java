package com.jms.controller.production;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jms.domain.db.PWo;
import com.jms.domain.db.SMaterial;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.production.WSPWo;
import com.jms.repositories.p.PWoRepository;
import com.jms.service.production.WoService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class WoController {
	
	@Autowired private WoService woService;
	@Autowired private PWoRepository pWoRepository;
	@Autowired private SecurityUtils securityUtils;

	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/saveWo", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSPWo saveWSWo(@RequestBody WSPWo wsPWo) throws Exception {
		return woService.saveWSPWo(wsPWo);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/deleteWo", method=RequestMethod.GET)
	public Valid deleteWorkCenter(@RequestParam("woId") Long woId) {
		return woService.deletePWo(woId);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findWo", method=RequestMethod.GET)
	public WSPWo findWo(@RequestParam("woId") Long woId) throws Exception {
		return woService.findWSPwo(woId);
		
	}
	

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getWoList", method=RequestMethod.GET)
	public WSTableData  getWorkCenterList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		//todo: how to get Wos???? current by So.......
		List<PWo> pWos =pWoRepository.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(pWos.size()<start + length)
			end =pWos.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			PWo w = pWos.get(i);
			String idSo=(w.getSSo()==null)?"":""+w.getSSo().getIdSo();
			String codeSo=(w.getSSo()==null)?"":w.getSSo().getCodeSo();
			String status=(w.getPStatusDic()==null)?"":w.getPStatusDic().getName();
			String[] d = {w.getWoNo(),codeSo,""+w.getQty(),w.getCreationTime().toString(),""+w.getUsers().getName(),status,""+w.getIdWo()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(pWos.size());
		t.setRecordsFiltered(pWos.size());
	    t.setData(lst);
	    return t;
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getWoObjsList", method=RequestMethod.GET)
	public List<WSSelectObj>  getWoObjsList() throws Exception {	   
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		for(PWo p:pWoRepository.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany()))
		 {
			WSSelectObj w = new WSSelectObj(p.getIdWo(),p.getWoNo());
			ws.add(w);
		 }
		
	    return ws;
	}

	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getMaterialByWoId", method=RequestMethod.GET)
	public WSSelectObj getMaterialByWoId(@RequestParam("woId") Long woId) throws Exception {
		
		SMaterial s = pWoRepository.findByWoId(woId);

	    WSSelectObj w = new WSSelectObj(s.getIdMaterial(),s.getPno()+"-"+ s.getRev()+"-"+s.getDes());
	
		return w;
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getMaterialByWoBom", method=RequestMethod.GET)
	public List<WSSelectObj> getMaterialByWoBom(@RequestParam("woId") Long woId) throws Exception {	
	   return woService.getMaterialsByWoId(woId);
	}
	
	
	
}