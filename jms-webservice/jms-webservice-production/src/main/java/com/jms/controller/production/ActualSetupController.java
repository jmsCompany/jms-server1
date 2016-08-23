package com.jms.controller.production;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.PActualSetup;
import com.jms.domain.db.SMaterial;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.p.WSPActualSetup;
import com.jms.repositories.p.PActualSetupRepository;
import com.jms.service.production.ActualSetupService;
import com.jms.web.security.SecurityUtils;



@RestController
@Transactional(readOnly=true)
public class ActualSetupController {
	
	@Autowired private ActualSetupService actualSetupService;
	@Autowired private PActualSetupRepository pActualSetupRepository;
	@Autowired private SecurityUtils securityUtils;

	@Transactional(readOnly = false)
	@RequestMapping(value="/p/saveWSPActualSetup", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSPActualSetup saveWSPActualSetup(@RequestBody WSPActualSetup wsPActualSetup) {
		return actualSetupService.saveWSPActualSetup(wsPActualSetup);
	}


	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findWSPActualSetups", method=RequestMethod.GET)
	public List<WSPActualSetup> findWSPActualSetups(@RequestParam("cppId") Long cppId){
		return actualSetupService.findWSPActualSetups(cppId);
		
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findWSPActualSetupById", method=RequestMethod.GET)
	public WSPActualSetup findWSPActualSetupById(@RequestParam("actSetupId") Long actSetupId){
		return actualSetupService.findWSPActualSetupById(actSetupId);
		
	}
	
	
//	工单woNO、物料pno-rev-des、班次shift、机器machineCode- machineName、实际开始停机actSt、实际结束停机actFt
	@Transactional(readOnly = true)
	@RequestMapping(value = "/p/getActSetupList", method = RequestMethod.POST)
	public WSTableData getActSetupList(
			@RequestParam Integer draw,
			@RequestParam Integer start,
			@RequestParam Integer length) throws Exception {

		List<PActualSetup>  pActualSetups= pActualSetupRepository.findByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		List<String[]> lst = new ArrayList<String[]>();
		int end = 0;
		if (pActualSetups.size() < start + length)
			end = pActualSetups.size();
		else
			end = start + length;
		for (int i = start; i < end; i++) {
			PActualSetup w = pActualSetups.get(i);
			SMaterial s = w.getPCPp().getPWo().getSSo().getSMaterial();
			String material = s.getPno()+"-"+s.getRev()+"-"+s.getDes();
			String actF = (w.getActFt()==null)?"":w.getActFt().toString();
			String actS = (w.getActSt()==null)?"":w.getActSt().toString();
			String[] d = { w.getPCPp().getPWo().getWoNo(), material,w.getPCPp().getPShiftPlanD().getShift(),w.getPCPp().getMMachine().getCode(),actS,actF,""+w.getIdActualSetup()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(pActualSetups.size());
		t.setRecordsFiltered(pActualSetups.size());
		t.setData(lst);
		return t;
	}
	
	

	

}