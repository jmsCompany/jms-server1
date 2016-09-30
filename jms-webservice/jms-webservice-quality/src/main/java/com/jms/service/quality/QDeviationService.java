package com.jms.service.quality;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.jms.domain.db.QCar;
import com.jms.domain.db.QCheckList;
import com.jms.domain.db.QDeviation;
import com.jms.domain.db.QNoProcess;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.q.WSQCar;
import com.jms.domain.ws.q.WSQCheckList;
import com.jms.domain.ws.q.WSQDeviation;
import com.jms.domain.ws.q.WSQNcr2;
import com.jms.domain.ws.q.WSQNoProcess;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.p.PRoutineDRepository;
import com.jms.repositories.q.QCarRepository;
import com.jms.repositories.q.QCheckListRepository;
import com.jms.repositories.q.QDeviationRepository;
import com.jms.repositories.q.QItemTypeRepository;
import com.jms.repositories.q.QNcr2Repository;
import com.jms.repositories.q.QNoProcessRepository;
import com.jms.repositories.q.QTesterRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.web.security.SecurityUtils;


@Service
@Transactional
public class QDeviationService {
	private static final Logger logger = LogManager.getLogger(QDeviationService.class
			.getCanonicalName());
	@Autowired
	private QNcr2Repository qNcr2Repository;
	@Autowired
	private QCarRepository qCarRepository;
	@Autowired
	private  SecurityUtils securityUtils;
	@Autowired
	private  QNoProcessRepository qNoProcessRepository;
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private SMaterialRepository sMaterialRepository;
	
	
	@Autowired
	private QDeviationRepository qDeviationRepository;

	public WSQDeviation saveWSQDeviation( WSQDeviation wsQDeviation) throws Exception {
		QDeviation qDeviation;
		if(wsQDeviation.getIdDeviation()!=null&&!wsQDeviation.getIdDeviation().equals(0l))
		{
			qDeviation = qDeviationRepository.findOne(wsQDeviation.getIdDeviation());
		}
		else
		{
			qDeviation = new QDeviation();
		
		}
		
		QDeviation dbQDeviation = toDBQDeviation(wsQDeviation,qDeviation);
		
		qDeviationRepository.save(dbQDeviation);
		wsQDeviation.setIdDeviation(dbQDeviation.getIdDeviation());
		return wsQDeviation;
	}
	
	
	public Valid deleteQDeviation(Long idQDeviation) {
		Valid v = new Valid();
		v.setValid(true);
		qDeviationRepository.delete(idQDeviation);
		return v;
		
	}
	
	@Transactional(readOnly=true)
	public WSQDeviation findWSQDeviation(Long idQDeviation) throws Exception {
		return toWSQDeviation(qDeviationRepository.findOne(idQDeviation));
	}
	

	
	private QDeviation toDBQDeviation(WSQDeviation wsQDeviation,QDeviation qDeviation) throws Exception
	{
	
		QDeviation dbQDeviation = (QDeviation)BeanUtil.shallowCopy(wsQDeviation, QDeviation.class, qDeviation);
		if(wsQDeviation.getIdNoProcess()!=null)
		{
			dbQDeviation.setQNoProcess(qNoProcessRepository.findOne(wsQDeviation.getIdNoProcess()));
		}

		return dbQDeviation;
	}
	
	private WSQDeviation toWSQDeviation(QDeviation qDeviation) throws Exception
	{
		WSQDeviation pc = (WSQDeviation)BeanUtil.shallowCopy(qDeviation, WSQDeviation.class, null);
		
		if(qDeviation.getQNoProcess()!=null)
		{
			pc.setIdNoProcess(qDeviation.getQNoProcess().getIdNoProcess());
		}
		if(qDeviation.getAeSign()!=null)
		{
			Users u = usersRepository.findOne(qDeviation.getAeSign());
			String name = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setAeSignName(name);
		}
		if(qDeviation.getQeSign()!=null)
		{
			Users u = usersRepository.findOne(qDeviation.getQeSign());
			String name = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setQeSignName(name);
		}
		if(qDeviation.getMfgSign()!=null)
		{
			Users u = usersRepository.findOne(qDeviation.getMfgSign());
			String name = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setMfgSignName(name);
		}
		if(qDeviation.getOfSign()!=null)
		{
			Users u = usersRepository.findOne(qDeviation.getOfSign());
			String name = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setOfSignName(name);
		}
		
		if(qDeviation.getIssuer()!=null)
		{
			Users u = usersRepository.findOne(qDeviation.getIssuer());
			String name = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setIssuerName(name);
		}
		if(qDeviation.getIssuer()!=null)
		{
			Users u = usersRepository.findOne(qDeviation.getIssuer());
			String name = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setIssuerName(name);
		}
		if(qDeviation.getIdMaterial()!=null)
		{
			SMaterial material = sMaterialRepository.findOne(qDeviation.getIdMaterial());
			pc.setMaterial(material.getPno()+"_"+material.getRev()+"_"+material.getDes());
		}
		return pc;
	}
	
}
