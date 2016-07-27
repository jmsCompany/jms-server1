package com.jms.controller.store;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SSo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.s.WSMaterial;
import com.jms.domain.ws.s.WSMaterialDelivered;
import com.jms.domain.ws.s.WSSSoRemark;
import com.jms.domain.ws.s.WSSso;
import com.jms.repositories.s.SSoRepository;
import com.jms.repositories.s.SSoRepositoryCustom;
import com.jms.service.store.SsoService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class SsoController {
	

	@Autowired private SecurityUtils securityUtils;
	@Autowired private SsoService ssoService;
	@Autowired private SSoRepository sSoRepository;
	@Autowired private SSoRepositoryCustom sSoRepositoryCustom;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/saveSo", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid saveSo(@RequestBody WSSso wsSso) throws Exception {
		return ssoService.saveSSo(wsSso);
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findSo", method=RequestMethod.GET)
	public WSSso findWSSso(@RequestParam("soId") Long soId) throws Exception {
		return ssoService.findSso(soId);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findCoOrderNosByCompanyCoId", method=RequestMethod.GET)
	public List<WSSelectObj> findCoOrderNosByCompanyCoId(@RequestParam("coId") Long coId) throws Exception {
		List<WSSelectObj> ws =new ArrayList<WSSelectObj>();
		for(String s: sSoRepository.findCoOrderNosByCompanyCoId(coId))
		{
			WSSelectObj w = new WSSelectObj();
			w.setSid(s);
			w.setName(s);
			ws.add(w);
		}
		return ws;
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getSoList", method=RequestMethod.POST)
	public WSTableData  getSoList( 
			@RequestParam(required=false,value="q") String q,
			@RequestParam(required=false,value="fromDay") String fromDay,
			@RequestParam(required=false,value="toDay") String toDay,
			@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<SSo> ssos = sSoRepositoryCustom.getCustomSsos(companyId, q, fromDay, toDay);
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(ssos.size()<start + length)
			end =ssos.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			SSo w = ssos.get(i);
			String status=(w.getSStatusDic()==null)?"":w.getSStatusDic().getName();
			String unit =(w.getSMaterial().getSUnitDicByUnitPur()==null)?"":w.getSMaterial().getSUnitDicByUnitPur().getName();
			String companyCoShortName =(w.getSCompanyCo()==null)?"":w.getSCompanyCo().getShortName();
			String qtyDel = (w.getQtyDelivered()==null)?"":""+w.getQtyDelivered();
			String[] d = {w.getCodeSo(),""+w.getDateOrder(),w.getUsers().getName(),companyCoShortName,status,w.getSMaterial().getPno()+"_"+w.getSMaterial().getRev()+"_"+w.getSMaterial().getDes(),unit,""+w.getQtySo(),""+w.getTotalAmount(),w.getDeliveryDate().toString(),qtyDel,""+w.getIdSo()};
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
	@RequestMapping(value="/s/getSoListByCompanyCoIdAndOrderNo", method=RequestMethod.GET)
	public List<WSSelectObj> getSoListByCompanyCoIdAndOrderNo(@RequestParam("companyCoId") Long companyCoId,@RequestParam("orderNo") String orderNo) throws Exception {
//		System.out.println("coId: " + companyCoId +", orderNo: " +orderNo);
		List<WSSelectObj>  ws = new ArrayList<WSSelectObj>();
		for(SSo s :sSoRepository.findByCompanyIdAndOrderNo(companyCoId,orderNo))
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

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getMaterialDeliveredBySoId", method=RequestMethod.GET)
	public WSMaterialDelivered getMaterialDeliveredBySoId(@RequestParam("soId") Long soId) throws Exception {
		
		SSo sSo = sSoRepository.findOne(soId);
		WSMaterialDelivered wd = new WSMaterialDelivered();
		SMaterial s = sSo.getSMaterial();
	    wd.setIdMaterial(s.getIdMaterial());
	    wd.setPno(s.getPno());
	    wd.setRev(s.getRev());
	    wd.setDes(s.getDes());
	    if(s.getSUnitDicByUnitInf()!=null)
	    {
	    	 wd.setUnitInv(s.getSUnitDicByUnitInf().getName());
	    }
	   wd.setQtySo(sSo.getQtySo());
	   if(sSo.getQtyDelivered()==null)
	   {
		   wd.setQtyDelivered(0l);
	   }
	   else
	   {
		   wd.setQtyDelivered(sSo.getQtyDelivered());
	   }
	
	   wd.setDeliveredDate(sSo.getDeliveryDate());
		return wd;
	}

	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/getMaterialBySoId", method = RequestMethod.GET)
	public WSMaterial findWSMaterial(@RequestParam("soId") Long soId) throws Exception {
		return ssoService.getMaterialBySoId(soId);

	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/saveSSoAutoRemark", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid saveSSoRemark(@RequestBody WSSSoRemark wsSSoRemark) throws Exception {
		return ssoService.saveSoAutoRemark(wsSSoRemark);
	}
	
	
}