package com.jms.controller.production;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.PBom;
import com.jms.domain.db.PCPp;
import com.jms.domain.db.PCheckPlan;
import com.jms.domain.db.PWo;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMtfMaterial;
import com.jms.domain.db.SSo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSBomMaterialObj;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.p.WSPRoutineDQty;
import com.jms.domain.ws.p.WSPWo;
import com.jms.domain.ws.p.WSWoStatus;
import com.jms.domain.ws.s.WSMaterialQty;
import com.jms.repositories.p.PBomRepository;
import com.jms.repositories.p.PCheckPlanRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.s.SMtfMaterialRepository;
import com.jms.service.production.WoService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class WoController {
	
	@Autowired private WoService woService;
	@Autowired private PWoRepository pWoRepository;
	@Autowired private PBomRepository pBomRepository;
	@Autowired private SecurityUtils securityUtils;
	@Autowired private PCheckPlanRepository pCheckPlanRepository;
	@Autowired private SMtfMaterialRepository sMtfMaterialRepository;
	@Autowired private PStatusDicRepository pStatusDicRepository;

	
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
	
	

	@Transactional(readOnly = false)
	@RequestMapping(value="/p/finishWo", method=RequestMethod.GET)
	public Valid finishWo(@RequestParam("woId") Long woId){
		
		Valid v = new Valid();
		v.setValid(true);
		PWo pwo =pWoRepository.findOne(woId);
		pwo.setPStatusDic(pStatusDicRepository.findOne(13l));
		pWoRepository.save(pwo);
		return v;
		
		
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findWSMaterialQtyByWoId", method=RequestMethod.GET)
	public WSMaterialQty findWSMaterialQtyByWoId(@RequestParam("woId") Long woId) throws Exception {
		return woService.findWSMaterialQtyByWoId(woId);
		
	}
	

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getBomByWoId", method=RequestMethod.POST)
	public WSTableData  getBomByWoId(@RequestParam Long woId,@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
	
		PWo pWo =pWoRepository.findOne(woId);
		SSo sSo =pWo.getSSo();
		PBom pBom = pBomRepository.findProductByMaterialId(sSo.getSMaterial().getIdMaterial());
		Set<PBom> pBoms =pBom.getPBoms();
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(pBoms.size()<start + length)
			end =pBoms.size();
		else
			end =start + length;
	
		List<PBom> pBomList = new ArrayList<PBom>(pBoms);

		for (int i = start; i < end; i++) {
			PBom w = pBomList.get(i);
			Long qpu = (w.getQpu()==null)?0l:w.getQpu();
			Long wasted = (w.getWastage()==null)?0l:w.getWastage();
			Long total = (qpu+wasted)*pWo.getQty(); //应发数量
			List<SMtfMaterial> mats = sMtfMaterialRepository.getByWoIdAndMaterialId(woId, w.getSMaterial().getIdMaterial());
            Long delivered = 0l; //已发数量
			if(mats!=null&&!mats.isEmpty())
			{
				for(SMtfMaterial s: mats)
				{
					delivered = delivered+ s.getQty();
				}
				
			}
			Long remainder = total-delivered;
			String[] d = {""+w.getLvl(), w.getSMaterial().getPno()+"-"+w.getSMaterial().getRev()+"-"+w.getSMaterial().getDes(),""+total,""+delivered,""+remainder};
			
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(pBomList.size());
		t.setRecordsFiltered(pBomList.size());
	    t.setData(lst);
	    return t;
	}
	
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getPaDayDetailListByWoId", method=RequestMethod.POST)
	public WSTableData  getPaDayDetailListByWoId(@RequestParam Long woId,@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
	
		PWo pWo =pWoRepository.findOne(woId);
		
		List<PCPp> pcppList = new ArrayList<PCPp>(pWo.getPCPps());
		
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(pcppList.size()<start + length)
			end =pcppList.size();
		else
			end =start + length;
	

		for (int i = start; i < end; i++) {
			PCPp w = pcppList.get(i);
		    String routineNo = (w.getPRoutineD()==null)?"":w.getPRoutineD().getRouteNo();
		    
			//pc.setQty(pWo.getQty());
			Long qtyFinished =0l;
			String checkTime="";
			String planCheckTime="";
			Long planQty =0l;
			Long finQty=0l;
			Long deviation=0l;
					
		
			List<PCheckPlan> pCheckPlans =pCheckPlanRepository.getMaxCheckPlanByCppId(w.getIdCPp());
		    if(pCheckPlans!=null&&!pCheckPlans.isEmpty())
		    {
		        qtyFinished =qtyFinished +pCheckPlans.get(0).getFinQty();
			    checkTime =pCheckPlans.get(0).getCheckTime().toString();
			    planCheckTime= pCheckPlans.get(0).getPlanCheckTime().toString();
			    planQty=(pCheckPlans.get(0).getPlanQty()==null)?0l:pCheckPlans.get(0).getPlanQty();
			    finQty = (pCheckPlans.get(0).getFinQty()==null)?0l:pCheckPlans.get(0).getFinQty();
			
			    deviation = planQty -finQty;
		    }
			
			
			
			
			String[] d = {pWo.getWoNo(),routineNo,w.getPlanSt().toString(),w.getPlanFt().toString(),""+w.getQty(),planCheckTime,checkTime,""+planQty,""+finQty,""+deviation};
			
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(pcppList.size());
		t.setRecordsFiltered(pcppList.size());
	    t.setData(lst);
	    return t;
	}
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/check/findWSPwoStatus", method=RequestMethod.GET)
	public List<WSWoStatus> findWSPwoStatus(@RequestParam Long companyId,@RequestParam Long delay) 
	{
		return woService.findWSPwoStatus(companyId,delay);
	}
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getRoutineDByWoId", method=RequestMethod.POST)
	public WSTableData  getRoutineDByWoId(@RequestParam Long woId,@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
	
		PWo pWo =pWoRepository.findOne(woId);
		
		Map<Long,WSPRoutineDQty> routineDQtyMap = new LinkedHashMap<Long,WSPRoutineDQty>();
	
		for(PCPp p: pWo.getPCPps())
		{
			Long qtyFinished =0l;
			List<PCheckPlan> pCheckPlans =pCheckPlanRepository.getMaxCheckPlanByCppId(p.getIdCPp());
			if(pCheckPlans!=null&&!pCheckPlans.isEmpty())
				qtyFinished =qtyFinished +pCheckPlans.get(0).getFinQty();
			if(routineDQtyMap.containsKey(p.getPRoutineD().getIdRoutineD()))
			{
				routineDQtyMap.get(p.getPRoutineD().getIdRoutineD()).setQty(routineDQtyMap.get(p.getPRoutineD().getIdRoutineD()).getQty()+p.getQty());
				routineDQtyMap.get(p.getPRoutineD().getIdRoutineD()).setQtyFinished(routineDQtyMap.get(p.getPRoutineD().getIdRoutineD()).getQtyFinished()+qtyFinished);
			}
				
			else
			{
				WSPRoutineDQty wsPRoutineDQty=new WSPRoutineDQty();
				wsPRoutineDQty.setIdRoutineD(p.getPRoutineD().getIdRoutineD());
				wsPRoutineDQty.setDes(p.getPRoutineD().getDes());
				wsPRoutineDQty.setQty(+p.getQty());
				wsPRoutineDQty.setQtyFinished(qtyFinished);
			}
		
		}
		List<WSPRoutineDQty> pRoutineList = new ArrayList<WSPRoutineDQty>(routineDQtyMap.values());
		
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(routineDQtyMap.size()<start + length)
			end =routineDQtyMap.size();
		else
			end =start + length;
		
		for (int i = start; i < end; i++) {
			WSPRoutineDQty w = pRoutineList.get(i);
			String[] d = {""+w.getIdRoutineD(),w.getDes(),""+w.getQty(),""+w.getQtyFinished()};
			lst.add(d);
		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(routineDQtyMap.size());
		t.setRecordsFiltered(routineDQtyMap.size());
	    t.setData(lst);
	    return t;
	}
	
	/**获得总生产详情列表*/
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getPaDetailList", method=RequestMethod.POST)
	public WSTableData  getPaDetailList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
	
		List<PWo> pWoList =pWoRepository.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(pWoList.size()<start + length)
			end =pWoList.size();
		else
			end =start + length;
		
		for (int i = start; i < end; i++) {
			PWo w = pWoList.get(i);
			Long qty = w.getQty(); //计划数量
			Long qtyFinished =0l;
			for(PCPp p: w.getPCPps())
			{
				List<PCheckPlan> pCheckPlans =pCheckPlanRepository.getMaxCheckPlanByCppId(p.getIdCPp());
				if(pCheckPlans!=null&&!pCheckPlans.isEmpty())
				qtyFinished =qtyFinished +pCheckPlans.get(0).getFinQty();
			}
			Long difference = qty-qtyFinished;
			String[] d = {w.getWoNo(),""+w.getQty(),""+qtyFinished,""+difference,w.getPStatusDic().getName(),""+w.getIdWo()};
			lst.add(d);
		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(pWoList.size());
		t.setRecordsFiltered(pWoList.size());
	    t.setData(lst);
	    return t;
	}
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getWoList", method=RequestMethod.POST)
	public WSTableData  getWorkCenterList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
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
	public List<WSBomMaterialObj> getMaterialByWoBom(@RequestParam("woId") Long woId) throws Exception {	
	   return woService.getMaterialsByWoId(woId);
	}
	
	
	
}