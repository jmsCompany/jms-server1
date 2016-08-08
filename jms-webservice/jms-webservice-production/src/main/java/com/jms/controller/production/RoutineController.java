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
import com.jms.domain.db.PWo;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMaterialPic;
import com.jms.domain.db.SPic;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.p.WSPBom;
import com.jms.domain.ws.p.WSPRoutine;
import com.jms.domain.ws.p.WSPRoutineD;
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
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.repositories.s.SMaterialRepository;
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
	@RequestMapping(value="/p/saveRoutine", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSPRoutine saveRoutine(@RequestBody WSPRoutine wsPRoutine) throws Exception {
		return routineService.saveWSPRoutine(wsPRoutine);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/deletePRoutine", method=RequestMethod.GET)
	public Valid deletePRoutine(@RequestParam("idPRoutine") Long idPRoutine) {
		return routineService.deletePRoutine(idPRoutine);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findPRoutine", method=RequestMethod.GET)
	public WSPRoutine findPRoutine(@RequestParam("idPRoutine") Long idPRoutine) throws Exception {
		return routineService.findWSPRoutine(idPRoutine);
		
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findPRoutineD", method=RequestMethod.GET)
	public WSPRoutineD findPRoutineD(@RequestParam("idPRoutineD") Long idPRoutineD) throws Exception {
		return routineDService.findWSPRoutineD(idPRoutineD);
		
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/savePRoutineD", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSPRoutineD savePRoutineD(@RequestBody WSPRoutineD wsPRoutineD) throws Exception {
		return routineDService.saveWSPRoutineD(wsPRoutineD);
		
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findRoutineDObjsByWoId", method=RequestMethod.GET)
	public List<WSSelectObj> findRoutineDObjsByWoId(@RequestParam("woId") Long woId) throws Exception {
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		SMaterial s = pWoRepository.findByWoId(woId);
		if(s!=null)
		{
			for(PRoutineD r: pRoutineDRepository.findByMaterialId(s.getIdMaterial()))
			{
				WSSelectObj w = new WSSelectObj(r.getIdRoutineD(),r.getRouteNo());
				ws.add(w);
			}
			
		}
		return ws;
		
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findRoutineDObjsByMaterialId", method=RequestMethod.GET)
	public List<WSSelectObj> findRoutineDObjsByMaterialId(@RequestParam("materialId") Long materialId) throws Exception {
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		SMaterial s = sMaterialRepository.findOne(materialId);
		if(s!=null)
		{
			for(PRoutineD r: pRoutineDRepository.findByMaterialId(s.getIdMaterial()))
			{
				WSSelectObj w = new WSSelectObj(r.getIdRoutineD(),r.getRouteNo());
				ws.add(w);
			}
			
		}
		return ws;
		
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getRoutineList", method=RequestMethod.POST)
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
			String drawNo="";
			if(w.getPDraw()!=null&&w.getPDraw().getDrawNo()!=null)
				{
				    drawNo=""+w.getPDraw().getDrawNo();
				}
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
	
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value = "/p/uploadDrawImage", method = RequestMethod.POST)
	public FileMeta uploadDrawImage(@RequestParam(required=false, value="drawId") Long drawId, MultipartHttpServletRequest request,
			HttpServletResponse response) {
		FileMeta fileMeta = new FileMeta();
		if (request.getFileNames().hasNext()) {
			fileMeta = fileUploadService.upload(request, response,true);
			if (drawId != null && !drawId.equals(0l)) {
				PDraw pdraw = pDrawRepository.findOne(drawId);
				pdraw.setDrawAtt(fileMeta.getFileName());
			}
			fileMeta.setFileId(drawId);
			fileMeta.setBytes(null);

		}
		return fileMeta;
	}
	
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value = "/p/uploadRoutineDAtt", method = RequestMethod.POST)
	public FileMeta uploadRoutineDAtt( @RequestParam("routineDId") Long routineDId, MultipartHttpServletRequest request,
			HttpServletResponse response) {
		FileMeta fileMeta = new FileMeta();
		if (request.getFileNames().hasNext()) {
			fileMeta = fileUploadService.upload(request, response,true);

				PAttDraw pAttDraw = new PAttDraw();
				pAttDraw.setCreationTime(new Date());
				String des = fileMeta.getOrgName();
				if(des.lastIndexOf(".")!=-1)
				{
					des = des.substring(0, des.lastIndexOf("."));
				}
				pAttDraw.setDes(des);
				pAttDraw.setName(des);
				pAttDraw.setFilename(fileMeta.getFileName());
				pAttDraw.setOrgFilename(fileMeta.getOrgName());
				pAttDraw.setUsers(securityUtils.getCurrentDBUser());
				pAttDraw = pAttDrawRepository.save(pAttDraw);
				PRoutineDAtt pRoutineDAtt = new PRoutineDAtt();
				pRoutineDAtt.setPAttDraw(pAttDraw);
				pRoutineDAtt.setPRoutineD(pRoutineDRepository.findOne(routineDId));
				pRoutineDAttRepository.save(pRoutineDAtt);
			    fileMeta.setBytes(null);

		}
		return fileMeta;
	}
	
	
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/p/getRoutineDAtts", method = RequestMethod.GET)
	public List<FileMeta> getRoutineDAtts( @RequestParam("routineDId") Long routineDId) {
		List<FileMeta> fileMetas = new ArrayList<FileMeta>();
		
		for(PRoutineDAtt pRoutineDAtt: pRoutineDAttRepository.getByRoutineDId(routineDId))
		{
			FileMeta f = new FileMeta();
			f.setFileId(pRoutineDAtt.getIdRoutineDAtt());
			f.setFileName(pRoutineDAtt.getPAttDraw().getFilename());
			f.setOrgName(pRoutineDAtt.getPAttDraw().getOrgFilename());
			f.setDes(pRoutineDAtt.getPAttDraw().getDes());
			
			fileMetas.add(f);
		}
		
		return fileMetas;
		
	}
	
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value = "/p/deleteRoutineDAttById", method = RequestMethod.GET)
	public Valid deleteRoutineDAttById( @RequestParam("fileId") Long fileId) {
		Valid v = new Valid();
		
		pRoutineDAttRepository.delete(fileId);
	
		v.setValid(true);
		return v;
		
	}
	

}