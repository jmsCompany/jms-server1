package com.jms.controller.quality;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.PRoutineD;
import com.jms.domain.db.QCheckList;
import com.jms.domain.db.QDeviation;
import com.jms.domain.db.QG8d;
import com.jms.domain.db.QNcr2;
import com.jms.domain.db.QNoProcess;
import com.jms.domain.db.QTester;
import com.jms.domain.db.SCompanyCo;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.p.WSPRoutine;
import com.jms.domain.ws.q.WSQCheckList;
import com.jms.domain.ws.q.WSQDeviation;
import com.jms.domain.ws.q.WSQG8d;
import com.jms.domain.ws.q.WSQNcr2;
import com.jms.domain.ws.q.WSQNoProcess;
import com.jms.repositories.p.PRoutineDRepository;
import com.jms.repositories.q.QCheckListRepository;
import com.jms.repositories.q.QDeviationRepository;
import com.jms.repositories.q.QG8dRepository;
import com.jms.repositories.q.QNcr2Repository;
import com.jms.repositories.q.QNoProcessRepository;
import com.jms.repositories.q.QTesterRepository;
import com.jms.repositories.s.SCompanyCoRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.service.quality.CheckListService;
import com.jms.service.quality.QDeviationService;
import com.jms.service.quality.QG8dService;
import com.jms.service.quality.QNcr2Service;
import com.jms.service.quality.QNoProcessService;
import com.jms.service.quality.TesterService;
import com.jms.web.security.SecurityUtils;

@RestController
@Transactional(readOnly = true)
public class QG8dController {

	@Autowired
	private QDeviationService qDeviationService;
	@Autowired
	private QG8dService qG8dService;
	
	@Autowired
	private QG8dRepository qG8dRepository;
	
	@Autowired
	private QDeviationRepository qDeviationRepository;

	@Autowired
	private QNoProcessRepository qNoProcessRepository;
	
	@Autowired
	private SCompanyCoRepository sCompanyCoRepository;
	
	@Autowired
	private QNcr2Service qNcr2Service;
	
	@Autowired
	private QNcr2Repository qNcr2Repository;
	@Autowired
	private  SecurityUtils securityUtils;
	@Autowired
	private SMaterialRepository sMaterialRepository;
	
	@Autowired
	private PRoutineDRepository pRoutineDRepository;
	
	@Autowired
	private UsersRepository usersRepository;

	@Transactional(readOnly = false)
	@RequestMapping(value="/q/saveQG8d", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSQG8d saveQG8d(@RequestBody WSQG8d wsQG8d) throws Exception {
		return qG8dService.saveWSQG8d(wsQG8d);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/q/deleteQG8d", method=RequestMethod.GET)
	public Valid deleteQG8d(@RequestParam("qDG8dId") Long qG8dId) {
		return qG8dService.deleteQG8d(qG8dId);
		
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/q/findQG8d", method=RequestMethod.GET)
	public WSQG8d findQG8d(@RequestParam("qDG8dId") Long qDG8dId) throws Exception {
		return qG8dService.findWSQG8d(qDG8dId);
		
	}

	@Transactional(readOnly = true)
	@RequestMapping(value="/q/getQG8dList", method=RequestMethod.POST)
	public WSTableData  getQG8dList(@RequestParam Long from,@RequestParam Long to,@RequestParam Long materialId,@RequestParam(required =false) String problemState,@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		Date fromDate = new Date(from);
		Date toDate = new Date(to);
		List<QG8d> qG8dList;
		if(problemState==null)
		{
			qG8dList=qG8dRepository.getByFromToAndMaterialId(fromDate, toDate, materialId);
		}
		else
		{
			problemState = "%"+problemState+"%";
			qG8dList=qG8dRepository.getByFromToAndMaterialIdAndReason(fromDate, toDate, materialId, problemState);
		}
		
		
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(qG8dList.size()<start + length)
			end =qG8dList.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			 QG8d w = qG8dList.get(i);
			
			String g8dNo = (w.getG8dNo()==null)?"":w.getG8dNo();
			String dateOpened = (w.getDateOpened()==null)?"":""+w.getDateOpened().toString();
			String sMaterial="";
			if(w.getIdMaterial()!=null)
			{
				SMaterial material = sMaterialRepository.findOne(w.getIdMaterial());
				sMaterial= material.getPno()+"_"+material.getRev()+"_"+material.getDes();
			}
			String routineD="";
			{
				if(w.getIdRoutineD()!=null)
				{
					PRoutineD rd = pRoutineDRepository.findOne(w.getIdRoutineD());
					routineD = rd.getRouteNo()+"_"+rd.getDes();
				}
			}
			
			String dateClosed = (w.getDateClosed()==null)?"":""+w.getDateClosed().toString();

			String[] d = {g8dNo,dateOpened,sMaterial,routineD,dateClosed,""+w.getIdG8d()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(qG8dList.size());
		t.setRecordsFiltered(qG8dList.size());
	    t.setData(lst);
	    return t;
	}
	


}