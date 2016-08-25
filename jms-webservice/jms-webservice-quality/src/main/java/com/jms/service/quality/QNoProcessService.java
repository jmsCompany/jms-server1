package com.jms.service.quality;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.jms.domain.db.QCheckList;
import com.jms.domain.db.QNoProcess;
import com.jms.domain.db.SMaterial;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.q.WSQCheckList;
import com.jms.domain.ws.q.WSQNoProcess;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.p.PRoutineDRepository;
import com.jms.repositories.q.QCheckListRepository;
import com.jms.repositories.q.QItemTypeRepository;
import com.jms.repositories.q.QNoProcessRepository;
import com.jms.repositories.q.QTesterRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.web.security.SecurityUtils;


@Service
@Transactional
public class QNoProcessService {
	private static final Logger logger = LogManager.getLogger(QNoProcessService.class
			.getCanonicalName());
	@Autowired
	private QNoProcessRepository qNoProcessRepository;
	@Autowired
	private SMaterialRepository sMaterialRepository;
	@Autowired
	private  SecurityUtils securityUtils;
	

	public WSQNoProcess saveWSQNoProcess( WSQNoProcess wsQNoProcess) throws Exception {
		QNoProcess qNoProcess;
		if(wsQNoProcess.getIdNoProcess()!=null&&!wsQNoProcess.getIdNoProcess().equals(0l))
		{
			qNoProcess = qNoProcessRepository.findOne(wsQNoProcess.getIdNoProcess());
		}
		else
		{
			qNoProcess = new QNoProcess();
		
		}
		
		QNoProcess dbQNoProcess = toDBQNoProcess(wsQNoProcess,qNoProcess);
		dbQNoProcess.setIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		qNoProcessRepository.save(dbQNoProcess);
		wsQNoProcess.setIdNoProcess(dbQNoProcess.getIdNoProcess());
		return wsQNoProcess;
	}
	
	
	public Valid deleteQNoProcess(Long idNoProcess) {
		Valid v = new Valid();
		v.setValid(true);
		qNoProcessRepository.delete(idNoProcess);
		return v;
		
	}
	
	@Transactional(readOnly=true)
	public WSQNoProcess findWSQNoProcess(Long idNoProcess) throws Exception {
		return toWSQNoProcess(qNoProcessRepository.findOne(idNoProcess));
	}
	

	
	private QNoProcess toDBQNoProcess(WSQNoProcess wsQNoProcess,QNoProcess qNoProcess) throws Exception
	{
	
		QNoProcess dbQNoProcess = (QNoProcess)BeanUtil.shallowCopy(wsQNoProcess, QNoProcess.class, qNoProcess);
		
		

		
		return dbQNoProcess;
	}
	
	private WSQNoProcess toWSQNoProcess(QNoProcess qNoProcess) throws Exception
	{
		WSQNoProcess pc = (WSQNoProcess)BeanUtil.shallowCopy(qNoProcess, WSQNoProcess.class, null);
		
		if(qNoProcess.getIdMaterial()!=null)
		{
			SMaterial material = sMaterialRepository.findOne(qNoProcess.getIdMaterial());
			pc.setMaterial(material.getPno()+"_"+material.getRev()+"_"+material.getDes());
		}
		
		return pc;
	}
	
}
