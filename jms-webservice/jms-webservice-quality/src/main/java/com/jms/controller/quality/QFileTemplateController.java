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

import com.jms.domain.db.PAttDraw;
import com.jms.domain.db.PRoutineD;
import com.jms.domain.db.PRoutineDAtt;
import com.jms.domain.db.PWo;
import com.jms.domain.db.QFileTemplate;
import com.jms.domain.db.QFileType;
import com.jms.domain.db.QNcr2;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMaterialPic;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.q.WSQFileTemplate;
import com.jms.file.FileMeta;
import com.jms.file.FileUploadService;
import com.jms.repositories.p.PRoutineDRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.q.QFileTemplateRepository;
import com.jms.repositories.q.QFileTypeRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.service.quality.QFileTemplateService;
import com.jms.web.security.SecurityUtils;

@RestController
@Transactional(readOnly = true)
public class QFileTemplateController {

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
	private QFileTemplateRepository qFileTemplateRepository;
	
	@Autowired
	private  FileUploadService fileUploadService;
	
	@Autowired
	private QFileTemplateService qFileTemplateService;
	@Autowired
	private SMaterialRepository sMaterialRepository;
	
//	@Autowired
//	private QFileTemplateRepositoryCustom	qFileTemplateRepositoryCustomImpl;
//	
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/q/saveQFileTemplate", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSQFileTemplate saveQFileTemplate(@RequestBody WSQFileTemplate wsQFileTemplate) throws Exception {
		return qFileTemplateService.saveWSQFileTemplate(wsQFileTemplate);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/q/deleteQFileTemplate", method=RequestMethod.GET)
	public Valid deleteQFileTemplate(@RequestParam("idFileTemplate") Long idFileTemplate) {
		return qFileTemplateService.deleteQFileTemplate(idFileTemplate);
		
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/q/findQFileTemplate", method=RequestMethod.GET)
	public WSQFileTemplate findQFileTemplate(@RequestParam("idFileTemplate") Long idFileTemplate) throws Exception {
		return qFileTemplateService.findQFileTemplate(idFileTemplate);
		
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/q/findQFileTemplateByIdType", method=RequestMethod.GET)
	public WSQFileTemplate findQFileTemplateByIdType(@RequestParam("idFileType") Long idFileType) throws Exception {
		return qFileTemplateService.findQFileTemplateByIdType(idFileType);
		
	}
	
	
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value = "/p/uploadTemplateFile", method = RequestMethod.POST)
	public FileMeta uploadRoutineDAtt( @RequestParam("idFileTemplate") Long idFileTemplate, MultipartHttpServletRequest request,
			HttpServletResponse response) {
		FileMeta fileMeta = new FileMeta();
		if (request.getFileNames().hasNext()) {
			fileMeta = fileUploadService.upload(request, response,true);
			
			if (idFileTemplate != null && !idFileTemplate.equals(0l)) {
				QFileTemplate qFileTemplate = qFileTemplateRepository.findOne(idFileTemplate);
				qFileTemplate.setFilename(fileMeta.getFileName());
				qFileTemplate.setOrgFilename(fileMeta.getOrgName());
				qFileTemplateRepository.save(qFileTemplate);
			}

		}
		return fileMeta;
	}
	
	
	
//	@Transactional(readOnly = true)
//	@RequestMapping(value="/q/getQFileTemplateList", method=RequestMethod.POST)
//	public List<WSQFileTemplate>  getQFileTemplateList() throws Exception {	   
//		
//		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
//
//		List<QFileTemplate> qFileTemplateList =qFileTemplateRepository.findByIdCompany(companyId);
//		
//		List<String[]> lst = new ArrayList<String[]>();
//		int end=0;
//		if(qFileTemplateList.size()<start + length)
//			end =qFileTemplateList.size();
//		else
//			end =start + length;
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
//		for (int i = start; i < end; i++) {
//			QFileTemplate w = qFileTemplateList.get(i);
//			
//			String person ="";
//			if(w.getUploadBy()!=null)
//			{
//				Users  u = usersRepository.findOne(w.getUploadBy());
//				if(u.getName()!=null)
//				{
//					person = u.getName();
//				}
//			}
//			String date = "";
//
//			if(w.getCreationTime()!=null)
//			{
//				
//				date= formatter.format(w.getCreationTime());
//			}
//			String fileType ="";
//			if(w.getIdFileType()!=null)
//			{
//				fileType = qFileTypeRepository.findOne(w.getIdFileType()).getType();
//			}
//			String[] d = {fileType, w.getFilename(),date,person,""+w.getIdFileTemplate()};
//			lst.add(d);
//
//		}
//		WSTableData t = new WSTableData();
//		t.setDraw(draw);
//		t.setRecordsTotal(qFileTemplateList.size());
//		t.setRecordsFiltered(qFileTemplateList.size());
//	    t.setData(lst);
//	    return t;
//	}
//	
//	
//	
	@Transactional(readOnly = true)
	@RequestMapping(value="/q/getQFileTemplateList", method=RequestMethod.GET)
	public List<WSQFileTemplate>  getQFileTemplateList() throws Exception {	   
		
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();

		List<QFileTemplate> qFileTemplateList =qFileTemplateRepository.findByIdCompany(companyId);
		List<WSQFileTemplate>  wsFileTemplateLis = new ArrayList<WSQFileTemplate>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		for (QFileTemplate w : qFileTemplateList) {
						
			String person ="";
			if(w.getUploadBy()!=null)
			{
				Users  u = usersRepository.findOne(w.getUploadBy());
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
			String fileType ="";
			if(w.getIdFileType()!=null)
			{
				fileType = qFileTypeRepository.findOne(w.getIdFileType()).getType();
			}
			WSQFileTemplate ws = new WSQFileTemplate();
			ws.setCreationTime(w.getCreationTime());
			ws.setFilename(w.getFilename());
			ws.setOrgFilename(w.getOrgFilename());
			ws.setIdFileTemplate(w.getIdFileTemplate());
			ws.setIdFileType(w.getIdFileType());
			ws.setFileType(fileType);
			wsFileTemplateLis.add(ws);

		}
		
	    return wsFileTemplateLis;
	}
	
	
	
}