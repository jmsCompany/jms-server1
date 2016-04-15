package com.jms.controller.store;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.SSo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.store.WSSso;
import com.jms.repositories.s.SSoRepository;
import com.jms.service.SsoService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class SsoController {
	

	@Autowired private SecurityUtils securityUtils;
	@Autowired private SsoService ssoService;
	@Autowired private SSoRepository sSoRepository;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/saveSo", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid saveSo(@RequestBody WSSso wsSso) throws Exception {
	//	System.out.println("save So");
		return ssoService.saveSSo(wsSso);
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findSo", method=RequestMethod.GET)
	public WSSso findWSSso(@RequestParam("soId") Long soId) throws Exception {
		return ssoService.findSso(soId);
	}
	

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getSoList", method=RequestMethod.GET)
	public WSTableData  getSoList( @RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<SSo> ssos = sSoRepository.findByCompanyId(companyId);
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(ssos.size()<start + length)
			end =ssos.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			SSo w = ssos.get(i);
			String status=(w.getSStatusDic()==null)?"":w.getSStatusDic().getName();
			String unit =(w.getSMaterial().getSUnitDicByUnitInf()==null)?"":w.getSMaterial().getSUnitDicByUnitInf().getName();
			String companyCoCode =(w.getSCompanyCo()==null)?"":w.getSCompanyCo().getCode();
			String[] d = {w.getCodeSo(),""+w.getDateOrder(),w.getUsers().getName(),companyCoCode,status,w.getSMaterial().getPno(),w.getSMaterial().getRev(),w.getSMaterial().getDes(),unit,""+w.getQtySo(),""+w.getTotalAmount(),w.getDeliveryDate().toString(),""+w.getQtyDelivered(),""+w.getIdSo()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(ssos.size());
		t.setRecordsFiltered(ssos.size());
	    t.setData(lst);
	    return t;
	}
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getSoListObjsByCompanyCoId", method=RequestMethod.GET)
	public List<WSSelectObj> getSoListByCompanyCoId(@RequestParam("companyCoId") Long companyCoId) throws Exception {
		List<WSSelectObj>  ws = new ArrayList<WSSelectObj>();
		for(SSo s :sSoRepository.findByCompanyCoId(companyCoId))
		{
			WSSelectObj w = new WSSelectObj(s.getIdSo(),s.getCodeSo());
			ws.add(w);
		}
		return ws;
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getSoListObjs", method=RequestMethod.GET)
	public List<WSSelectObj> getSoList() throws Exception {
		List<WSSelectObj>  ws = new ArrayList<WSSelectObj>();
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		for(SSo s :sSoRepository.findByCompanyId(companyId))
		{
			WSSelectObj w = new WSSelectObj(s.getIdSo(),s.getCodeSo());
			ws.add(w);
		}
		return ws;
	}


}