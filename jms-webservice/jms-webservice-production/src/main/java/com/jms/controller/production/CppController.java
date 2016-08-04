package com.jms.controller.production;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jms.domain.db.PCPp;
import com.jms.domain.db.PWo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.p.WSPCpp;
import com.jms.domain.ws.p.WSPMr;
import com.jms.domain.ws.p.WSPlannedMaterialSending;
import com.jms.repositories.p.PCPpRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.service.production.PCppService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class CppController {
	
	@Autowired private PCppService pCppService;
	@Autowired private PCPpRepository pCPpRepository;
	@Autowired private PWoRepository pWoRepository;
	@Autowired private SecurityUtils securityUtils;

	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/saveWSPCpp", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSPCpp saveWSPCpp(@RequestBody WSPCpp wsPCpp) throws Exception {
		return pCppService.saveWSPCPp(wsPCpp);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/startCpp", method=RequestMethod.GET)
	public Valid startCpp(@RequestParam("idCpp") Long idCpp) throws Exception {
		return pCppService.startCpp(idCpp);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/finishCpp", method=RequestMethod.GET)
	public Valid finishCpp(@RequestParam("idCpp") Long idCpp) throws Exception {
		return pCppService.finishCpp(idCpp);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/isStarted", method=RequestMethod.GET)
	public Valid isStarted(@RequestParam("idCpp") Long idCpp) throws Exception {
		return pCppService.isStarted(idCpp);
	}
	
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/deleteWSCpp", method=RequestMethod.GET)
	public Valid deleteCpp(@RequestParam("cppId") Long cppId) {
		return pCppService.deletePCpp(cppId);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findCpp", method=RequestMethod.GET)
	public WSPCpp findCpp(@RequestParam("cppId") Long cppId) throws Exception {
		return pCppService.findWSCPp(cppId);
		
	}
	

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getCppList", method=RequestMethod.POST)
	public WSTableData  getCppList(@RequestParam(required=false,value="q") String q, @RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		//System.out.println("q: " + q);
		List<String[]> lst = new ArrayList<String[]>();
		List<PCPp> pCpps;
		List<Long> woIds = new ArrayList<Long>();

		if(q==null)
		{
			for(PWo pwo:pWoRepository.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany()))
			{
				woIds.add(pwo.getIdWo());
			}
		}
		else
		{
			q= '%'+q+'%';
			for(PWo pwo:pWoRepository.getByCompanyIdAndQuery(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), q))
			{
				woIds.add(pwo.getIdWo());
			}
		}
	
	
		if(!woIds.isEmpty())
		{
			pCpps =pCPpRepository.getByCompanyIdAndWoIds(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), woIds);	
		}
		else
		{
			pCpps = new ArrayList<PCPp>();
		}
		 	
			
		
		int end=0;
		if(pCpps.size()<start + length)
			end =pCpps.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			PCPp w = pCpps.get(i);
			String routineDNo ="";
			if(w.getPRoutineD()!=null)
			{
				routineDNo=w.getPRoutineD().getRouteNo();
			}
			String actSt = (w.getActSt()==null)?"":w.getActSt().toString();
			String actFt = (w.getActFt()==null)?"":w.getActFt().toString();
			String product =w.getPWo().getSSo().getSMaterial().getPno()+"_"+w.getPWo().getSSo().getSMaterial().getRev()+"_"+w.getPWo().getSSo().getSMaterial().getDes();
			String[] d = {""+w.getCPpCode(),w.getPWo().getWoNo(),routineDNo,product,""+w.getPWo().getQty(),w.getPShiftPlanD().getShift(),w.getPlanSt().toString(),w.getPlanFt().toString(),actSt,actFt,w.getMMachine().getCode()+""
					+ "-"+w.getMMachine().getSpec(),w.getUsers().getName(),""+w.getQty(),""+w.getIdCPp()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(pCpps.size());
		t.setRecordsFiltered(pCpps.size());
	    t.setData(lst);
	    return t;
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findWSPMrsByCppId", method=RequestMethod.GET)
	public List<WSPMr> findWSPMrsByCppId(@RequestParam("cppId") Long cppId) throws Exception {
		return pCppService.findWSPMrsByCppId(cppId);
		
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findWSPlannedMaterialSending", method=RequestMethod.GET)
	public WSPlannedMaterialSending findWSPlannedMaterialSending(
			@RequestParam("fromDate") Long fromDate,
			@RequestParam("toDate") Long toDate,
			@RequestParam("fromStkId") Long fromStkId,
			@RequestParam("toStkId") Long toStkId)  {
       System.out.println("find planned materials sending");
		return pCppService.findWSPlannedMaterialSending(fromDate, toDate, fromStkId, toStkId);		
		
	}
	
	
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/p/hasCheckPlans", method=RequestMethod.GET)
	public Valid hasCheckPlans(@RequestParam("cppId") Long cppId)  {
		Valid v =  pCppService.hasCheckPlans(cppId);
		return v;
	}
}