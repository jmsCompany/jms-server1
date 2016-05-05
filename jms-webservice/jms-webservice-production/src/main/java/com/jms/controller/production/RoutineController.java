package com.jms.controller.production;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.PBomLabel;
import com.jms.domain.db.PRoutine;
import com.jms.domain.db.PShiftPlan;
import com.jms.domain.db.PWo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.production.WSPBom;
import com.jms.domain.ws.production.WSPRoutine;
import com.jms.domain.ws.production.WSPWo;
import com.jms.domain.ws.production.WSShiftPlan;
import com.jms.repositories.p.PAttDrawRepository;
import com.jms.repositories.p.PBomLabelRepository;
import com.jms.repositories.p.PDrawRepository;
import com.jms.repositories.p.PLineRepository;
import com.jms.repositories.p.PRoutineDRepository;
import com.jms.repositories.p.PRoutineRepository;
import com.jms.repositories.p.PShiftPlanDRepository;
import com.jms.repositories.p.PShiftPlanRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.service.production.BomLabelService;
import com.jms.service.production.BomService;
import com.jms.service.production.RoutineDService;
import com.jms.service.production.RoutineService;
import com.jms.service.production.ShiftPlanDService;
import com.jms.service.production.ShiftPlanService;
import com.jms.service.production.WoService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class RoutineController {
	
	
	@Autowired
	private RoutineService routineService;
	
	@Autowired
	private RoutineDService routineDService;
	@Autowired
	private PRoutineDRepository pRoutineDRepository;
	@Autowired
	private PRoutineRepository pRoutineRepository;
	@Autowired
	private PLineRepository pLineRepository;
	
	@Autowired
	private PWorkCenterRepository pWorkCenterRepository;
	@Autowired
	private PAttDrawRepository pAttDrawRepository;
	
	@Autowired
	private PDrawRepository pDrawRepository;
	
	@Autowired private SecurityUtils securityUtils;

	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/saveRoutine", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSPRoutine saveWSShiftPlan(@RequestBody WSPRoutine wsPRoutine) throws Exception {
		return routineService.saveWSPRoutine(wsPRoutine);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/deletePRoutine", method=RequestMethod.GET)
	public Valid deleteWSPRoutine(@RequestParam("idPRoutine") Long idPRoutine) {
		return routineService.deletePRoutine(idPRoutine);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findPRoutine", method=RequestMethod.GET)
	public WSPRoutine findPRoutine(@RequestParam("idPRoutine") Long idPRoutine) throws Exception {
		return routineService.findWSPRoutine(idPRoutine);
		
	}
	

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getRoutineList", method=RequestMethod.GET)
	public WSTableData  getShiftPlanList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		List<PRoutine> pRoutines =pRoutineRepository.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(pRoutines.size()<start + length)
			end =pRoutines.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			PRoutine w = pRoutines.get(i);
			String drawNo=(w.getPDraw()==null)?"":""+w.getPDraw().getDrawNo();
			String[] d = {w.getSMaterial().getPno(),w.getSMaterial().getRev(),w.getSMaterial().getDes(),w.getPLine().getPline(),drawNo,""+w.getIdRoutine()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(pRoutines.size());
		t.setRecordsFiltered(pRoutines.size());
	    t.setData(lst);
	    return t;
	}
	

}