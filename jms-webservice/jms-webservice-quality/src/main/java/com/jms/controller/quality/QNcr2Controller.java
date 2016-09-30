package com.jms.controller.quality;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jms.domain.db.QCheckList;
import com.jms.domain.db.QNcr2;
import com.jms.domain.db.QNoProcess;
import com.jms.domain.db.QTester;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMaterialPic;
import com.jms.domain.db.SPic;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.p.WSPRoutine;
import com.jms.domain.ws.q.WSQCheckList;
import com.jms.domain.ws.q.WSQNcr2;
import com.jms.domain.ws.q.WSQNoProcess;
import com.jms.file.FileMeta;
import com.jms.file.FileUploadService;
import com.jms.repositories.q.QCheckListRepository;
import com.jms.repositories.q.QNcr2Repository;
import com.jms.repositories.q.QNoProcessRepository;
import com.jms.repositories.q.QTesterRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.service.quality.CheckListService;
import com.jms.service.quality.QNcr2Service;
import com.jms.service.quality.QNoProcessService;
import com.jms.service.quality.TesterService;
import com.jms.web.security.SecurityUtils;

@RestController
@Transactional(readOnly = true)
public class QNcr2Controller {

	@Autowired
	private QNoProcessService qNoProcessService;
	@Autowired
	private QNoProcessRepository qNoProcessRepository;
	
	
	@Autowired
	private QNcr2Service qNcr2Service;
	
	@Autowired
	private QNcr2Repository qNcr2Repository;
	@Autowired
	private  SecurityUtils securityUtils;
	@Autowired
	private SMaterialRepository sMaterialRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private  FileUploadService fileUploadService;

	@Transactional(readOnly = false)
	@RequestMapping(value="/q/saveQNcr2", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSQNcr2 saveQNcr2(@RequestBody WSQNcr2 wsQNcr2) throws Exception {
		return qNcr2Service.saveWSQNcr2(wsQNcr2);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/q/deleteQNcr2", method=RequestMethod.GET)
	public Valid deleteQNcr2(@RequestParam("qNcr2Id") Long qNcr2Id) {
		return qNcr2Service.deleteQNcr2(qNcr2Id);
		
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/q/findQNcr2", method=RequestMethod.GET)
	public WSQNcr2 findQNcr2(@RequestParam("qNcr2Id") Long qNcr2Id) throws Exception {
		return qNcr2Service.findWSQNcr2(qNcr2Id);
		
	}

	@Transactional(readOnly = true)
	@RequestMapping(value="/q/getQNcr2List", method=RequestMethod.POST)
	public WSTableData  getQNcr2List(@RequestParam Long from,@RequestParam Long to,@RequestParam Long materialId,@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		Date fromDate = new Date(from);
		Date toDate = new Date(to);
		List<QNcr2> qNcr2List =qNcr2Repository.getByFromToAndMaterialId(fromDate, toDate, materialId);
		
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(qNcr2List.size()<start + length)
			end =qNcr2List.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			QNcr2 w = qNcr2List.get(i);
			
			String sMaterial="";
			if(w.getQNoProcess().getIdMaterial()!=null)
			{
				SMaterial material = sMaterialRepository.findOne(w.getQNoProcess().getIdMaterial());
				sMaterial= material.getPno()+"_"+material.getRev()+"_"+material.getDes();
			}
			String ncrNo = (w.getNcrNo()==null)?"":w.getNcrNo();

			String emergencyAction = (w.getEmergencyAction()==null)?"":w.getEmergencyAction();
			String person ="";
			if(w.getWho()!=null)
			{
				Users  u = usersRepository.findOne(w.getWho());
				if(u.getName()!=null)
				{
					person = u.getName();
				}
			}
			String date = (w.getDate()==null)?"":""+w.getDate().toString();

			String[] d = {sMaterial,ncrNo,emergencyAction,person,date,""+w.getIdNcr()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(qNcr2List.size());
		t.setRecordsFiltered(qNcr2List.size());
	    t.setData(lst);
	    return t;
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value = "/q/uploadNcrImage", method = RequestMethod.POST)
	public FileMeta uploadFile(MultipartHttpServletRequest request,
			HttpServletResponse response) {
		FileMeta fileMeta = new FileMeta();
		if (request.getFileNames().hasNext()) {
			return fileUploadService.upload(request, response,false);
		}
		return fileMeta;
	}

}