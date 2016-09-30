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
import com.jms.domain.ws.q.WSQNcr2;
import com.jms.domain.ws.q.WSQNoProcess;
import com.jms.repositories.p.PRoutineDRepository;
import com.jms.repositories.q.QCheckListRepository;
import com.jms.repositories.q.QDeviationRepository;
import com.jms.repositories.q.QNcr2Repository;
import com.jms.repositories.q.QNoProcessRepository;
import com.jms.repositories.q.QTesterRepository;
import com.jms.repositories.s.SCompanyCoRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.service.quality.CheckListService;
import com.jms.service.quality.QDeviationService;
import com.jms.service.quality.QNcr2Service;
import com.jms.service.quality.QNoProcessService;
import com.jms.service.quality.TesterService;
import com.jms.web.security.SecurityUtils;

@RestController
@Transactional(readOnly = true)
public class QDeviationController {

	@Autowired
	private QDeviationService qDeviationService;
	
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
	@RequestMapping(value="/q/saveQDeviation", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSQDeviation saveQDeviation(@RequestBody WSQDeviation wsQDeviation) throws Exception {
		return qDeviationService.saveWSQDeviation(wsQDeviation);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/q/deleteQDeviation", method=RequestMethod.GET)
	public Valid deleteQDeviation(@RequestParam("qDeviationId") Long qDeviationId) {
		return qDeviationService.deleteQDeviation(qDeviationId);
		
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/q/findQDeviation", method=RequestMethod.GET)
	public WSQDeviation findQDeviation(@RequestParam("qDeviationId") Long qDeviationId) throws Exception {
		return qDeviationService.findWSQDeviation(qDeviationId);
		
	}

	@Transactional(readOnly = true)
	@RequestMapping(value="/q/getQDeviationList", method=RequestMethod.POST)
	public WSTableData  getQDeviationList(@RequestParam Long from,@RequestParam Long to,@RequestParam Long materialId,@RequestParam(required =false) String reason,@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		Date fromDate = new Date(from);
		Date toDate = new Date(to);
		List<QDeviation> qDeviationList;
		if(reason==null)
		{
			qDeviationList=qDeviationRepository.getByFromToAndMaterialId(fromDate, toDate, materialId);
		}
		else
		{
			reason = "%"+reason+"%";
			qDeviationList=qDeviationRepository.getByFromToAndMaterialIdAndReason(fromDate, toDate, materialId, reason);
		}
		
		
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(qDeviationList.size()<start + length)
			end =qDeviationList.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			QDeviation w = qDeviationList.get(i);
			
			String deviationNo = (w.getDeviationNo()==null)?"":w.getDeviationNo();
			
			String sMaterial="";
			if(w.getQNoProcess().getIdMaterial()!=null)
			{
				SMaterial material = sMaterialRepository.findOne(w.getQNoProcess().getIdMaterial());
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
			String dept ="";
			
			String companyCo="";
			
			if(w.getIdCompanyCo()!=null)
			{
				SCompanyCo cc = sCompanyCoRepository.findOne(w.getIdCompanyCo());
				companyCo = cc.getCode();
			}

			
			String person ="";
			if(w.getIssuer()!=null)
			{
				Users  u = usersRepository.findOne(w.getIssuer());
				if(u.getName()!=null)
				{
					person = u.getName();
				}
			}
			String date1 = (w.getDate1()==null)?"":""+w.getDate1().toString();

			String[] d = {deviationNo,sMaterial,routineD,dept,companyCo,person,date1,""+w.getIdDeviation()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(qDeviationList.size());
		t.setRecordsFiltered(qDeviationList.size());
	    t.setData(lst);
	    return t;
	}
	


}