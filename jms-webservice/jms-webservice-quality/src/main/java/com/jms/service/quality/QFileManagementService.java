package com.jms.service.quality;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.PRoutineD;
import com.jms.domain.db.QFileManagent;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.q.WSQFileManagent;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.p.PRoutineDRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.q.QFileManagentRepository;
import com.jms.repositories.q.QFileTypeRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.web.security.SecurityUtils;


@Service
@Transactional
public class QFileManagementService {
	private static final Logger logger = LogManager.getLogger(QFileManagementService.class
			.getCanonicalName());
	@Autowired
	private QFileTypeRepository qFileTypeRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private PRoutineDRepository pRoutineDRepository;
	
	@Autowired
	private PWoRepository pWoRepository;
	
	@Autowired
	private QFileManagentRepository qFileManagentRepository;
	
	@Autowired
	private SMaterialRepository sMaterialRepository;
	@Autowired
	private  SecurityUtils securityUtils;
	

	public WSQFileManagent saveWSQFileManagent( WSQFileManagent wsQFileManagent) throws Exception {
		
		QFileManagent qFileManagent;
		if(wsQFileManagent.getIdFile()!=null&&!wsQFileManagent.getIdFile().equals(0l))
		{
			qFileManagent = qFileManagentRepository.findOne(wsQFileManagent.getIdFile());
		}
		else
		{
			qFileManagent = new QFileManagent();
			qFileManagent.setCreationTime(new Date());
			qFileManagent.setCreator(securityUtils.getCurrentDBUser().getIdUser());
			qFileManagent.setUploadByUser(securityUtils.getCurrentDBUser().getIdUser());
		}
		//logger.debug("ws idFile: " + wsQFileManagent.getIdFile());
		qFileManagent = toDBQFileManagent(wsQFileManagent,qFileManagent);
		//logger.debug("before: " + qFileManagent.getIdFile());
		qFileManagent.setIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		qFileManagentRepository.save(qFileManagent);
		//logger.debug("after: " + qFileManagent.getIdFile());
		return wsQFileManagent;
		
	}
	
	
	public Valid deleteQFileManagent(Long idFile) {
		Valid v = new Valid();
		v.setValid(true);
		qFileManagentRepository.delete(idFile);
		return v;
		
	}
	
	@Transactional(readOnly=true)
	public WSQFileManagent findWSQFileManagent(Long idFile) throws Exception {
		return toWSQFileManagent(qFileManagentRepository.findOne(idFile));
	}
	

	
	private QFileManagent toDBQFileManagent(WSQFileManagent wsQFileManagent,QFileManagent qFileManagent) throws Exception
	{
	
	//	wsQFileManagent.setCreationTime(creationTime);
		QFileManagent dbQFileManagent = (QFileManagent)BeanUtil.shallowCopy(wsQFileManagent, QFileManagent.class, qFileManagent);
		if(wsQFileManagent.getIdFileType()!=null)
		{
			dbQFileManagent.setQFileType(qFileTypeRepository.findOne(wsQFileManagent.getIdFileType()));
		}
		dbQFileManagent.setIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		//dbQFileManagent.setCreationTime(new);
		
		return dbQFileManagent;
	}
	
	public WSQFileManagent toWSQFileManagent(QFileManagent qFileManagent) throws Exception
	{
		WSQFileManagent pc = (WSQFileManagent)BeanUtil.shallowCopy(qFileManagent, WSQFileManagent.class, null);
		
		if(qFileManagent.getQFileType()!=null)
		{
			pc.setIdFileType(qFileManagent.getQFileType().getIdFileType());
			pc.setFileType(qFileManagent.getQFileType().getType());
		}
		if(qFileManagent.getCreationTime()!=null)
		{
			pc.setCreationTime(qFileManagent.getCreationTime());
		}
		String person ="";
		if(qFileManagent.getCreator()!=null)
		{
			
				Users  u = usersRepository.findOne(qFileManagent.getCreator());
				if(u.getName()!=null)
				{
					person = u.getName();
				}
		
		}
		pc.setCreatorName(person);
		
		String routineD ="";
		if(qFileManagent.getIdRoutineD()!=null){
			PRoutineD pRoutineD = pRoutineDRepository.findOne(qFileManagent.getIdRoutineD());
			if(pRoutineD!=null){
				routineD = pRoutineD.getRouteNo()+"_"+pRoutineD.getDes();
			}
			
		}
		pc.setRoutineD(routineD);
		
		String sMaterial="";
		if(qFileManagent.getIdMaterial()!=null)
		{
			SMaterial material = sMaterialRepository.findOne(qFileManagent.getIdMaterial());
			if(material!=null)
			sMaterial= material.getPno()+"_"+material.getRev()+"_"+material.getDes();
		}
		pc.setMaterial(sMaterial);
		
		
		if(qFileManagent.getIdWo()!=null)
		{
			pc.setWoNo(pWoRepository.findOne(qFileManagent.getIdWo()).getWoNo());
		}
		return pc;
	}
	
}
