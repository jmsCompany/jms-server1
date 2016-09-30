package com.jms.controller.quality;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.QCar;
import com.jms.domain.db.QCheckList;
import com.jms.domain.db.QNcr2;
import com.jms.domain.db.QNoProcess;
import com.jms.domain.db.QTester;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.p.WSPRoutine;
import com.jms.domain.ws.q.WSQCar;
import com.jms.domain.ws.q.WSQCheckList;
import com.jms.domain.ws.q.WSQNcr2;
import com.jms.domain.ws.q.WSQNoProcess;
import com.jms.repositories.q.QCarRepository;
import com.jms.repositories.q.QCheckListRepository;
import com.jms.repositories.q.QNcr2Repository;
import com.jms.repositories.q.QNoProcessRepository;
import com.jms.repositories.q.QTesterRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.service.quality.CheckListService;
import com.jms.service.quality.QCarService;
import com.jms.service.quality.QNcr2Service;
import com.jms.service.quality.QNoProcessService;
import com.jms.web.security.SecurityUtils;

@RestController
@Transactional(readOnly = true)
public class QCarController {

	@Autowired
	private QCarService qCarService;
	@Autowired
	private QCarRepository qCarRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private QNcr2Service qNcr2Service;
	
	@Autowired
	private QNcr2Repository qNcr2Repository;
	@Autowired
	private SMaterialRepository sMaterialRepository;
	@Autowired
	private  SecurityUtils securityUtils;

	@Transactional(readOnly = false)
	@RequestMapping(value="/q/saveQCar", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSQCar saveQCar(@RequestBody WSQCar wsQCar) throws Exception {
		return qCarService.saveWSQCar(wsQCar);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/q/deleteQCar", method=RequestMethod.GET)
	public Valid deleteQCar(@RequestParam("qCarId") Long qCarId) {
		return qCarService.deleteQCar(qCarId);
		
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/q/findQCar", method=RequestMethod.GET)
	public WSQCar findQCar(@RequestParam("qCarId") Long qCarId) throws Exception {
		return qCarService.findWSQcar(qCarId);
		
	}
	
	
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/q/getQCarList", method=RequestMethod.POST)
	public WSTableData  getQCarList(@RequestParam Long from,@RequestParam Long to,@RequestParam Long materialId,@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		Date fromDate = new Date(from);
		Date toDate = new Date(to);
		List<QCar> qCarList =qCarRepository.getByFromToAndMaterialId(fromDate, toDate, materialId);
		
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(qCarList.size()<start + length)
			end =qCarList.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			QCar w = qCarList.get(i);
			
		    String to1 = (w.getTo1()==null)?"":w.getTo1();
		    String ncrNo = (w.getQNcr2()==null)?"":w.getQNcr2().getNcrNo();
		
		    String date1 = (w.getDate1()==null)?"":w.getDate1().toString();
		    String date2 = (w.getDate2()==null)?"":w.getDate2().toString();
		    String confirmor="";
		    if(w.getConfirmor()!=null)
		    {
		    	Users u = usersRepository.findOne(w.getConfirmor());
		    	confirmor = (u.getName()==null)?"":u.getName();
		    }
		

			String[] d = {to1,ncrNo,date1,confirmor,date2,""+w.getIdCar()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(qCarList.size());
		t.setRecordsFiltered(qCarList.size());
	    t.setData(lst);
	    return t;
	}
	
	


}