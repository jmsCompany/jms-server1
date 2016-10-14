package com.jms.controller.production;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jms.domain.db.PAttDraw;
import com.jms.domain.db.PBomLabel;
import com.jms.domain.db.PDraw;
import com.jms.domain.db.PRoutine;
import com.jms.domain.db.PRoutineD;
import com.jms.domain.db.PRoutineDAtt;
import com.jms.domain.db.PShiftPlan;
import com.jms.domain.db.PStandardRoutined;
import com.jms.domain.db.PWip;
import com.jms.domain.db.PWo;
import com.jms.domain.db.PWorkCenter;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMaterialPic;
import com.jms.domain.db.SPic;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.p.WSPBom;
import com.jms.domain.ws.p.WSPRoutine;
import com.jms.domain.ws.p.WSPRoutineD;
import com.jms.domain.ws.p.WSPStandardRoutined;
import com.jms.domain.ws.p.WSPWo;
import com.jms.domain.ws.p.WSShiftPlan;
import com.jms.file.FileMeta;
import com.jms.file.FileUploadService;
import com.jms.repositories.p.PAttDrawRepository;
import com.jms.repositories.p.PBomLabelRepository;
import com.jms.repositories.p.PDrawRepository;
import com.jms.repositories.p.PLineRepository;
import com.jms.repositories.p.PRoutineDAttRepository;
import com.jms.repositories.p.PRoutineDRepository;
import com.jms.repositories.p.PRoutineRepository;
import com.jms.repositories.p.PShiftPlanDRepository;
import com.jms.repositories.p.PShiftPlanRepository;
import com.jms.repositories.p.PStandardRoutinedRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.service.production.BomLabelService;
import com.jms.service.production.BomService;
import com.jms.service.production.PStandardRoutineDService;
import com.jms.service.production.RoutineDService;
import com.jms.service.production.RoutineService;
import com.jms.service.production.ShiftPlanDService;
import com.jms.service.production.ShiftPlanService;
import com.jms.service.production.WoService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class PStandardRoutineDController {
	
	
	@Autowired
	private RoutineService routineService;
	
	@Autowired
	private RoutineDService routineDService;
	@Autowired
	private PRoutineDRepository pRoutineDRepository;
	@Autowired
	private PRoutineRepository pRoutineRepository;
	@Autowired
	private PStandardRoutinedRepository pStandardRoutinedRepository;
	@Autowired
	private PStandardRoutineDService pStandardRoutineDService;
	
	@Autowired
	private PRoutineDAttRepository pRoutineDAttRepository;
	@Autowired
	private PWorkCenterRepository pWorkCenterRepository;
	@Autowired
	private PAttDrawRepository pAttDrawRepository;
	
	@Autowired
	private PDrawRepository pDrawRepository;
	
	@Autowired private SecurityUtils securityUtils;
	
	@Autowired
	private PWoRepository pWoRepository;
	@Autowired
	private SMaterialRepository sMaterialRepository;
	@Autowired
	private FileUploadService fileUploadService;

	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/savePStandardRoutineD", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSPStandardRoutined savePStandardRoutineD(@RequestBody WSPStandardRoutined wsPStandardRoutined) throws Exception {
		return pStandardRoutineDService.saveWSPStandardRoutined(wsPStandardRoutined);
	}

	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/deletePStandardRoutineD", method=RequestMethod.GET)
	public Valid deletePStandardRoutineD(@RequestParam("idStandardRoutined") Long idStandardRoutined) {
		return pStandardRoutineDService.deleteWSPStandardRoutined(idStandardRoutined);
		
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findPStandardRoutineD", method=RequestMethod.GET)
	public WSPStandardRoutined findPStandardRoutineD(@RequestParam("idStandardRoutined") Long idStandardRoutined) throws Exception {
		return pStandardRoutineDService.findWSPStandardRoutined(idStandardRoutined);
		
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getPStandardRoutineDList", method=RequestMethod.POST)
	public WSTableData  getPStandardRoutineDList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		List<PStandardRoutined> pRoutineDs =pStandardRoutinedRepository.findByIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(pRoutineDs.size()<start + length)
			end =pRoutineDs.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			PStandardRoutined w = pRoutineDs.get(i);
			String wc = "";
			if(w.getIdWorkcenter()!=null)
			{
				PWorkCenter wk = pWorkCenterRepository.findOne(w.getIdWorkcenter());
				wc= wk.getWorkCenter();
			}
		
			String[] d = {""+w.getOederBy(),w.getName(),wc,""+w.getIdStandardRoutined()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(pRoutineDs.size());
		t.setRecordsFiltered(pRoutineDs.size());
	    t.setData(lst);
	    return t;
	}

	

}