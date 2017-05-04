package com.jms.controller.quality;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jms.domain.db.PRoutineD;
import com.jms.domain.db.PWo;
import com.jms.domain.db.QFileManagent;
import com.jms.domain.db.QFileType;
import com.jms.domain.db.QNcr2;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.q.WSQFileManagent;
import com.jms.domain.ws.q.WSQFileType;
import com.jms.file.FileMeta;
import com.jms.file.FileUploadService;
import com.jms.repositories.p.PRoutineDRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.q.QFileManagementRepositoryCustom;
import com.jms.repositories.q.QFileManagentRepository;
import com.jms.repositories.q.QFileTypeRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.service.quality.QFileManagementService;
import com.jms.web.security.SecurityUtils;

@RestController
@Transactional(readOnly = true)
public class QFileManagementController {

	@Autowired
	private QFileTypeRepository qFileTypeRepository;
	
	@Autowired
	private  SecurityUtils securityUtils;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private PRoutineDRepository pRoutineDRepository;
	
	@Autowired
	private PWoRepository pWoRepository;
	@Autowired
	private QFileManagentRepository qFileManagentRepository;
	
	@Autowired
	private  FileUploadService fileUploadService;
	
	@Autowired
	private QFileManagementService qFileManagementService;
	@Autowired
	private SMaterialRepository sMaterialRepository;
	
	@Autowired
	private QFileManagementRepositoryCustom	qFileManagementRepositoryCustomImpl;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/q/getQFileTypes", method=RequestMethod.GET)
	public List<WSSelectObj> getQFileTypes() {
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		for(QFileType f :qFileTypeRepository.getByIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany()) )
		{
			WSSelectObj w = new WSSelectObj(f.getIdFileType(),f.getType());
			ws.add(w);
		}
		return ws;
		
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/q/saveQFileType", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid saveQFileType(@RequestBody WSQFileType wsQFileType) throws Exception {
		//return qFileManagementService.saveWSQFileManagent(wsQFileManagement);
     
      if(wsQFileType.getIdFileType()==null||wsQFileType.getIdFileType().equals(0l))
      {
    	  QFileType qFileType = new QFileType();
          qFileType.setIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
          qFileType.setDes(wsQFileType.getDes());
          qFileType.setType(wsQFileType.getType());
          qFileTypeRepository.save(qFileType);
      }
      else
      {
    	  QFileType qFileType = qFileTypeRepository.findOne(wsQFileType.getIdFileType());
    	  qFileType.setIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
          qFileType.setDes(wsQFileType.getDes());
          qFileType.setType(wsQFileType.getType());
          qFileTypeRepository.save(qFileType);
      }

       Valid v = new Valid();
       v.setValid(true);
       return v;
	
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/q/deleteQFileType", method=RequestMethod.GET)
	public Valid deleteQFileType(@RequestParam("fileTypeId") Long fileTypeId) {
		Valid v = new Valid();
		QFileType qFileType = qFileTypeRepository.findOne(fileTypeId);
		if(qFileType!=null&&qFileType.getQFileManagents().isEmpty())
		{
			qFileTypeRepository.delete(fileTypeId);
			v.setValid(true);
			return v;
		}
		else
		{
			v.setValid(false);
			v.setMsg("该文件类型被使用，不能被删除");
			return v;
		}

	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/q/getQFileTypeList", method=RequestMethod.POST)
	public WSTableData  getQFileTypeList(
			@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();

		List<QFileType> qFileTypeList =qFileTypeRepository.getByIdCompany(companyId);
		
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(qFileTypeList.size()<start + length)
			end =qFileTypeList.size();
		else
			end =start + length;
		
		for (int i = start; i < end; i++) {
			QFileType w = qFileTypeList.get(i);
			
		String des ="";
		if(w.getDes()!=null)
		{
			des= w.getDes();
		}

			String[] d = {""+w.getIdFileType(),w.getType(),des,""+w.getIdFileType()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(qFileTypeList.size());
		t.setRecordsFiltered(qFileTypeList.size());
	    t.setData(lst);
	    return t;
	}
	
	
	
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/q/saveQFileManagement", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSQFileManagent saveQFileManagement(@RequestBody WSQFileManagent wsQFileManagement) throws Exception {
		return qFileManagementService.saveWSQFileManagent(wsQFileManagement);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/q/deleteQFileManagement", method=RequestMethod.GET)
	public Valid deleteQFileManagement(@RequestParam("fileId") Long fileId) {
		return qFileManagementService.deleteQFileManagent(fileId);
		
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/q/findQFileManagement", method=RequestMethod.GET)
	public WSQFileManagent findQFileManagement(@RequestParam("fileId") Long fileId) throws Exception {
		return qFileManagementService.findWSQFileManagent(fileId);
		
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value = "/q/uploadQFile", method = RequestMethod.POST)
	public FileMeta uploadQFile(MultipartHttpServletRequest request,
			HttpServletResponse response) {
		FileMeta fileMeta = new FileMeta();
		if (request.getFileNames().hasNext()) {
			return fileUploadService.upload(request, response,false);
		}
		return fileMeta;
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/q/getQFileManagementList", method=RequestMethod.POST)
	public WSTableData  getQFileManagementList(
			@RequestParam(required=false) String from,
			@RequestParam(required=false) String to,
			@RequestParam(required=false) Long materialId,
			@RequestParam(required=false) Long woId,
			@RequestParam(required=false) Long routineDId,
			@RequestParam(required=false) Long fileTypeId,
			@RequestParam(required=false) Long creatorId,
			@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();

		List<QFileManagent> qFileManagementList =qFileManagementRepositoryCustomImpl.getQFiles(companyId, from, to, materialId, woId, routineDId, fileTypeId, creatorId);
		
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(qFileManagementList.size()<start + length)
			end =qFileManagementList.size();
		else
			end =start + length;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		for (int i = start; i < end; i++) {
			QFileManagent w = qFileManagementList.get(i);
			
			String sMaterial="";
			if(w.getIdMaterial()!=null&&!w.getIdMaterial().equals(0l))
			{
				SMaterial material = sMaterialRepository.findOne(w.getIdMaterial());
				//System.out.println(" matid: " +w.getIdMaterial() );
				sMaterial= material.getPno()+"_"+material.getRev()+"_"+material.getDes();
			}
			String fileType = (w.getQFileType()==null)?"":w.getQFileType().getType();
			String fileNo = (w.getFileNo()==null)?"":w.getFileNo();

			String wo ="";
			if(w.getIdWo()!=null&&!w.getIdWo().equals(0l)){
				PWo pwo = pWoRepository.findOne(w.getIdWo());
				wo=pwo.getWoNo();
			}
			
			String routineD ="";
			if(w.getIdRoutineD()!=null&&!w.getIdRoutineD().equals(0l)){
				PRoutineD pRoutineD = pRoutineDRepository.findOne(w.getIdRoutineD());
				if(pRoutineD!=null){
					routineD = pRoutineD.getRouteNo()+"_"+pRoutineD.getDes();
				}
				
			}

			String person ="";
			if(w.getCreator()!=null&&!w.getCreator().equals(0l))
			{
				Users  u = usersRepository.findOne(w.getCreator());
				if(u.getName()!=null)
				{
					person = u.getName();
				}
			}
			String date = "";

			if(w.getCreationTime()!=null)
			{
				
				date= formatter.format(w.getCreationTime());
			}
			
			

			String[] d = {fileNo, fileType,sMaterial,wo,routineD,date,person,""+w.getIdFile()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(qFileManagementList.size());
		t.setRecordsFiltered(qFileManagementList.size());
	    t.setData(lst);
	    return t;
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/q/getQFileManagements", method=RequestMethod.POST)
	public List<WSQFileManagent>  getQFileManagements(
			@RequestParam(required=false) String from,
			@RequestParam(required=false) String to,
			@RequestParam(required=false) Long materialId,
			@RequestParam(required=false) Long woId,
			@RequestParam(required=false) Long routineDId,
			@RequestParam(required=false) Long fileTypeId,
			@RequestParam(required=false) Long creatorId
			) throws Exception {	   
		
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();

		List<QFileManagent> qFileManagementList =qFileManagementRepositoryCustomImpl.getQFiles(companyId, from, to, materialId, woId, routineDId, fileTypeId, creatorId);
		List<WSQFileManagent> wss = new ArrayList<WSQFileManagent>();
		
		for (QFileManagent q: qFileManagementList) {
			
			WSQFileManagent w =qFileManagementService.toWSQFileManagent(q);
			wss.add(w);
		}
		
	    return wss;
	}
	
	
	
	
	
}