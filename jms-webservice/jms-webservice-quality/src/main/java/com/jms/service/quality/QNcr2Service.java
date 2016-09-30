package com.jms.service.quality;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.jms.domain.db.QCar;
import com.jms.domain.db.QCheckList;
import com.jms.domain.db.QG8d;
import com.jms.domain.db.QNcr2;
import com.jms.domain.db.QNoProcess;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMtfNo;
import com.jms.domain.db.Users;
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
import com.jms.repositories.s.SMtfNoRepository;
import com.jms.repositories.user.UsersRepository;
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
	
	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private SMaterialRepository sMaterialRepository;
	@Autowired
	private SMtfNoRepository sMtfNoRepository;

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
		
		
		
		if(wsQNcr2.getNcrNo()==null||wsQNcr2.getNcrNo().isEmpty())
		{
			  //不合格品编码
		    SMtfNo smtfNo = sMtfNoRepository.getByCompanyIdAndType(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), 14l);
		    if(smtfNo==null)
		    {
		    	smtfNo = new SMtfNo();
		    	smtfNo.setCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		    	smtfNo.setCurrentVal(0l);
		    	smtfNo.setDes("抱怨单");
		    	smtfNo.setPrefix("N");
		    	smtfNo.setType(14l);
		    	smtfNo.setToday(new Date());
		    	smtfNo = sMtfNoRepository.save(smtfNo);
		    }
		    long currentVal;
		    SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd"); 
		    String dd = myFmt.format(new Date());
		    
		    
		    if(smtfNo.getToday()!=null&&myFmt.format(smtfNo.getToday()).equals(dd))
		    {
		    	   currentVal =smtfNo.getCurrentVal()+1;
				    smtfNo.setCurrentVal(currentVal);
		    }
		    else
		    {
		    	 currentVal=1l;
		    	 smtfNo.setToday(new Date());
		    	 smtfNo.setCurrentVal(1l);
		    }
		    logger.debug("dd: "  + dd+", today: " + myFmt.format(smtfNo.getToday()));
		    sMtfNoRepository.save(smtfNo);
		    SimpleDateFormat myFmt1=new SimpleDateFormat("yyyyMMdd"); 
		    String dd2= myFmt1.format(new Date());;
		    String mtNo = smtfNo.getPrefix()+dd2+"-"+String.format("%03d", currentVal);
		    dbQNcr2.setNcrNo(mtNo);
		}
		
		
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
			pc.setIdMaterial(qNcr2.getQNoProcess().getIdMaterial());
			
		}
		
		if(!qNcr2.getQCars().isEmpty())
		{
			QCar c = qNcr2.getQCars().iterator().next();
			pc.setIdCar(c.getIdCar());
			//pc.setCar(c.getCarNo());
			pc.setCarNo(c.getCarNo());
		}
		if(!qNcr2.getQG8ds().isEmpty())
		{
			QG8d g = qNcr2.getQG8ds().iterator().next();
			pc.setIdQ8d(g.getIdG8d());
			pc.setQ8dNo(g.getG8dNo());
		}
		
		if(qNcr2.getWho()!=null)
		{
			Users u = usersRepository.findOne(qNcr2.getWho());
			String who = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setWhoName(who);
		}
		
		if(qNcr2.getRespnose()!=null)
		{
			Users u = usersRepository.findOne(qNcr2.getRespnose());
			String response = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setRespnoseName(response);
		}
		if(qNcr2.getQNoProcess()!=null)
		{
			if(qNcr2.getQNoProcess().getIdMaterial()!=null)
			{
				SMaterial material = sMaterialRepository.findOne(qNcr2.getQNoProcess().getIdMaterial());
				pc.setMaterial(material.getPno()+"_"+material.getRev()+"_"+material.getDes());
			}
		}
	
		return pc;
	}
	
}
