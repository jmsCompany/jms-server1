package com.jms.controller.quality;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.QCheckList;
import com.jms.domain.db.QNoProcess;
import com.jms.domain.db.QTester;
import com.jms.domain.db.SMaterial;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.p.WSPRoutine;
import com.jms.domain.ws.q.WSQCheckList;
import com.jms.domain.ws.q.WSQNoProcess;
import com.jms.repositories.q.QCheckListRepository;
import com.jms.repositories.q.QNoProcessRepository;
import com.jms.repositories.q.QTesterRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.service.quality.CheckListService;
import com.jms.service.quality.QNoProcessService;
import com.jms.service.quality.TesterService;
import com.jms.web.security.SecurityUtils;

@RestController
@Transactional(readOnly = true)
public class QNoProcessController {

	@Autowired
	private QNoProcessService qNoProcessService;
	@Autowired
	private QNoProcessRepository qNoProcessRepository;
	
	@Autowired
	private SMaterialRepository sMaterialRepository;
	@Autowired
	private  SecurityUtils securityUtils;

	@Transactional(readOnly = false)
	@RequestMapping(value="/q/saveQNoProcess", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSQNoProcess saveQNoProcess(@RequestBody WSQNoProcess wsQNoProcess) throws Exception {
		return qNoProcessService.saveWSQNoProcess(wsQNoProcess);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/q/deleteQNoProcess", method=RequestMethod.GET)
	public Valid deleteQNoProcess(@RequestParam("qNoProcessId") Long qNoProcessId) {
		return qNoProcessService.deleteQNoProcess(qNoProcessId);
		
	}
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/q/findQNoProcess", method=RequestMethod.GET)
	public WSQNoProcess findQNoProcess(@RequestParam("qNoProcessId") Long qNoProcessId) throws Exception {
		return qNoProcessService.findWSQNoProcess(qNoProcessId);	
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/q/getQNoProcessList", method=RequestMethod.POST)
	public WSTableData  getQNoProcessList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		Long idCompany =securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<QNoProcess> qNoProcessList =qNoProcessRepository.getByIdCompany(idCompany);
		
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(qNoProcessList.size()<start + length)
			end =qNoProcessList.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			QNoProcess w = qNoProcessList.get(i);
			
			String sMaterial="";
			if(w.getIdMaterial()!=null)
			{
				SMaterial material = sMaterialRepository.findOne(w.getIdMaterial());
				sMaterial= material.getPno()+"_"+material.getRev()+"_"+material.getDes();
			}
			String lotNo = (w.getLotNo()==null)?"":w.getLotNo();

			String qty = (w.getQty()==null)?"":""+w.getQty();

			String[] d = {w.getNcpNo(),sMaterial,lotNo,qty,""+w.getIdNoProcess()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(qNoProcessList.size());
		t.setRecordsFiltered(qNoProcessList.size());
	    t.setData(lst);
	    return t;
	}
	


}