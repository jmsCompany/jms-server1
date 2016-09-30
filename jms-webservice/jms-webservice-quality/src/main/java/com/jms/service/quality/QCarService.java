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
import com.jms.domain.db.QNoProcess;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.q.WSQCar;
import com.jms.domain.ws.q.WSQCheckList;
import com.jms.domain.ws.q.WSQNcr2;
import com.jms.domain.ws.q.WSQNoProcess;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.p.PRoutineDRepository;
import com.jms.repositories.q.QCarRepository;
import com.jms.repositories.q.QCheckListRepository;
import com.jms.repositories.q.QItemTypeRepository;
import com.jms.repositories.q.QNcr2Repository;
import com.jms.repositories.q.QNoProcessRepository;
import com.jms.repositories.q.QTesterRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.web.security.SecurityUtils;


@Service
@Transactional
public class QCarService {
	private static final Logger logger = LogManager.getLogger(QCarService.class
			.getCanonicalName());
	@Autowired
	private QNcr2Repository qNcr2Repository;
	@Autowired
	private QCarRepository qCarRepository;
	@Autowired
	private  SecurityUtils securityUtils;
	@Autowired
	private UsersRepository usersRepository;

	public WSQCar saveWSQCar( WSQCar wsQCar) throws Exception {
		QCar qCar;
		if(wsQCar.getIdCar()!=null&&!wsQCar.getIdCar().equals(0l))
		{
			qCar = qCarRepository.findOne(wsQCar.getIdCar());
		}
		else
		{
			qCar = new QCar();
		
		}
		
		QCar dbQCar = toDBQCar(wsQCar,qCar);
		
		qCarRepository.save(dbQCar);
		wsQCar.setIdCar(dbQCar.getIdCar());
		return wsQCar;
	}
	
	
	public Valid deleteQCar(Long idQCar) {
		Valid v = new Valid();
		v.setValid(true);
		qCarRepository.delete(idQCar);
		return v;
		
	}
	
	@Transactional(readOnly=true)
	public WSQCar findWSQcar(Long idQCar) throws Exception {
		return toWSQCar(qCarRepository.findOne(idQCar));
	}
	

	
	private QCar toDBQCar(WSQCar wsQCar,QCar qCar) throws Exception
	{
	
		QCar dbQCar = (QCar)BeanUtil.shallowCopy(wsQCar, QCar.class, qCar);
		if(wsQCar.getIdQNcr2()!=null)
		{
			dbQCar.setQNcr2(qNcr2Repository.findOne(wsQCar.getIdQNcr2()));
		}

		return dbQCar;
	}
	
	private WSQCar toWSQCar(QCar qCar) throws Exception
	{
		WSQCar pc = (WSQCar)BeanUtil.shallowCopy(qCar, WSQCar.class, null);
		
		if(qCar.getQNcr2()!=null)
		{
			pc.setIdQNcr2(qCar.getQNcr2().getIdNcr());
			pc.setNcrNo(qCar.getQNcr2().getNcrNo());
		}
		if(qCar.getConfirmor()!=null)
		{
			Users u = usersRepository.findOne(qCar.getConfirmor());
			String name = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setConfirmorName(name);
		}
		if(qCar.getResponse()!=null)
		{
			Users u = usersRepository.findOne(qCar.getResponse());
			String name = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setResponseName(name);
		}
		
		return pc;
	}
	
}
