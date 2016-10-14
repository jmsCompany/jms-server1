package com.jms.service.quality;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.QFileManagent;
import com.jms.domain.db.QFileTemplate;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.q.WSQFileManagent;
import com.jms.domain.ws.q.WSQFileTemplate;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.p.PRoutineDRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.q.QFileManagentRepository;
import com.jms.repositories.q.QFileTemplateRepository;
import com.jms.repositories.q.QFileTypeRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.web.security.SecurityUtils;


@Service
@Transactional
public class QFileTemplateService {
	private static final Logger logger = LogManager.getLogger(QFileTemplateService.class
			.getCanonicalName());
	@Autowired
	private QFileTypeRepository qFileTypeRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private PRoutineDRepository pRoutineDRepository;
	
	@Autowired
	private QFileTemplateRepository qFileTemplateRepository;
	
	@Autowired
	private QFileManagentRepository qFileManagentRepository;
	
	@Autowired
	private SMaterialRepository sMaterialRepository;
	@Autowired
	private  SecurityUtils securityUtils;
	

	public WSQFileTemplate saveWSQFileTemplate( WSQFileTemplate wsQFileTemplate) throws Exception {
		
		QFileTemplate qFileTemplate;
		if(wsQFileTemplate.getIdFileTemplate()!=null&&!wsQFileTemplate.getIdFileTemplate().equals(0l))
		{
			qFileTemplate = qFileTemplateRepository.findOne(wsQFileTemplate.getIdFileTemplate());
		}
		else
		{
			qFileTemplate = new QFileTemplate();
			qFileTemplate.setCreationTime(new Date());
			
		}
		//logger.debug("ws idFile: " + wsQFileManagent.getIdFile());
		qFileTemplate = toDBQFileTemplate(wsQFileTemplate,qFileTemplate);
		//logger.debug("before: " + qFileManagent.getIdFile());
		//qFileManagent.setIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		qFileTemplateRepository.save(qFileTemplate);
		//logger.debug("after: " + qFileManagent.getIdFile());
		return wsQFileTemplate;
		
	}
	
	
	public Valid deleteQFileTemplate(Long idFileTemplate) {
		Valid v = new Valid();
		v.setValid(true);
		qFileTemplateRepository.delete(idFileTemplate);
		return v;
		
	}
	
	@Transactional(readOnly=true)
	public WSQFileTemplate findQFileTemplate(Long idFileTemplate) throws Exception {
		return toWSQFileTemplate(qFileTemplateRepository.findOne(idFileTemplate));
	}
	

	
	private QFileTemplate toDBQFileTemplate(WSQFileTemplate wsQFileTemplate,QFileTemplate qFileTemplate) throws Exception
	{
	

		QFileTemplate dbQFileTemplate = (QFileTemplate)BeanUtil.shallowCopy(wsQFileTemplate, QFileTemplate.class, qFileTemplate);
		dbQFileTemplate.setIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		dbQFileTemplate.setUploadBy(securityUtils.getCurrentDBUser().getIdUser());
	//	dbQFileTemplate.setCreationTime(new Date());
		return dbQFileTemplate;
	}
	
	private WSQFileTemplate toWSQFileTemplate(QFileTemplate qFileTemplate) throws Exception
	{
		WSQFileTemplate pc = (WSQFileTemplate)BeanUtil.shallowCopy(qFileTemplate, WSQFileTemplate.class, null);
		
		if(qFileTemplate.getIdFileType()!=null)
		{
			pc.setFileType(qFileTypeRepository.findOne(qFileTemplate.getIdFileType()).getType());
		}
		if(qFileTemplate.getUploadBy()!=null)
		{
			pc.setUploader(usersRepository.findOne(qFileTemplate.getUploadBy()).getName());
		}
		
		return pc;
	}
	
}
