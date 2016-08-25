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
import com.jms.domain.db.QNcr2;
import com.jms.domain.db.QNoProcess;
import com.jms.domain.db.SMaterial;
import com.jms.domain.ws.Valid;
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
import com.jms.web.security.SecurityUtils;


@Service
@Transactional
public class QNcr2Service {
	private static final Logger logger = LogManager.getLogger(QNcr2Service.class
			.getCanonicalName());
	@Autowired
	private QNoProcessRepository qNoProcessRepository;
	@Autowired
	private QNcr2Repository qNcr2Repository;
	@Autowired
	private  SecurityUtils securityUtils;
	

	public WSQNcr2 saveWSQNcr2(WSQNcr2 wsQNcr2) throws Exception {
		QNcr2 qNcr2;
		if(wsQNcr2.getIdNcr()!=null&&!wsQNcr2.getIdNcr().equals(0l))
		{
			qNcr2 = qNcr2Repository.findOne(wsQNcr2.getIdNcr());
		}
		else
		{
			qNcr2 = new QNcr2();
		
		}
		
		QNcr2 dbQNcr2 = toDBQNcr2(wsQNcr2,qNcr2);
		
		qNcr2Repository.save(dbQNcr2);
		wsQNcr2.setIdNcr(dbQNcr2.getIdNcr());
		return wsQNcr2;
	}
	
	
	public Valid deleteQNcr2(Long idNcr2) {
		Valid v = new Valid();
		v.setValid(true);
		qNcr2Repository.delete(idNcr2);
		return v;
		
	}
	
	@Transactional(readOnly=true)
	public WSQNcr2 findWSQNcr2(Long idNcr2) throws Exception {
		return toWSQNcr2(qNcr2Repository.findOne(idNcr2));
	}
	

	
	private QNcr2 toDBQNcr2(WSQNcr2 wsQNcr2,QNcr2 qNcr2) throws Exception
	{
	
		QNcr2 dbQNcr2 = (QNcr2)BeanUtil.shallowCopy(wsQNcr2, QNcr2.class, qNcr2);
		
		if(wsQNcr2.getIdQNoProcess()!=null)
		{
			dbQNcr2.setQNoProcess(qNoProcessRepository.findOne(wsQNcr2.getIdQNoProcess()));	
		}

		return dbQNcr2;
	}
	
	private WSQNcr2 toWSQNcr2(QNcr2 qNcr2) throws Exception
	{
		WSQNcr2 pc = (WSQNcr2)BeanUtil.shallowCopy(qNcr2, WSQNcr2.class, null);
		
		if(qNcr2.getQNoProcess()!=null)
		{
			pc.setIdQNoProcess(qNcr2.getQNoProcess().getIdNoProcess());
		}
		
		return pc;
	}
	
}
