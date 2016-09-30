package com.jms.service.quality;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.QDeviation;
import com.jms.domain.db.QNcr2;
import com.jms.domain.db.QNoProcess;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMtfNo;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.q.WSQNoProcess;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.q.QNoProcessRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SMtfNoRepository;
import com.jms.repositories.user.UsersRepository;
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
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private SMtfNoRepository sMtfNoRepository;

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
		if(wsQNoProcess.getNcpNo()==null||wsQNoProcess.getNcpNo().isEmpty())
		{
			  //不合格品编码
		    SMtfNo smtfNo = sMtfNoRepository.getByCompanyIdAndType(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), 13l);
		    if(smtfNo==null)
		    {
		    	smtfNo = new SMtfNo();
		    	smtfNo.setCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		    	smtfNo.setCurrentVal(0l);
		    	smtfNo.setDes("不合格品");
		    	smtfNo.setPrefix("Q");
		    	smtfNo.setType(13l);
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
		    String dd1 = myFmt1.format(new Date());;
		    String mtNo = smtfNo.getPrefix()+dd1+String.format("%03d", currentVal);
		    qNoProcess.setNcpNo(mtNo);
		}
		if(wsQNoProcess.getTime()==null)
		{
			dbQNoProcess.setCreationTime(new Date());
		}
		//if()
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
		
		dbQNoProcess.setCreationTime(wsQNoProcess.getTime());
		
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
		
		if(!qNoProcess.getQDeviations().isEmpty())
		{
			 QDeviation q = qNoProcess.getQDeviations().iterator().next();
			 pc.setIdDeviation(q.getIdDeviation());
			 pc.setDeviationNo(q.getDeviationNo());
		}
		if(!qNoProcess.getQNcr2s().isEmpty())
		{
			  QNcr2 q = qNoProcess.getQNcr2s().iterator().next();
			  pc.setIdNcr(q.getIdNcr());
			  pc.setNcrNo(q.getNcrNo());
		}
		if(qNoProcess.getAudit01()!=null)
		{
			Users u = usersRepository.findOne(qNoProcess.getAudit01());
			String audit01Name = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setAudit01Name(audit01Name);
		}
		if(qNoProcess.getAudit02()!=null)
		{
			Users u = usersRepository.findOne(qNoProcess.getAudit02());
			String audit02Name = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setAudit02Name(audit02Name);
		}
		if(qNoProcess.getAudit03()!=null)
		{
			Users u = usersRepository.findOne(qNoProcess.getAudit03());
			String audit03Name = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setAudit03Name(audit03Name);
		}
		if(qNoProcess.getRelease1()!=null)
		{
			Users u = usersRepository.findOne(qNoProcess.getRelease1());
			String release1Name = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setRealease1Name(release1Name);
		}
		
		if(qNoProcess.getCountersign()!=null)
		{
			Users u = usersRepository.findOne(qNoProcess.getCountersign());
			String countersign = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setCountersignName(countersign);
		}
		
		if(qNoProcess.getResponse()!=null)
		{
			Users u = usersRepository.findOne(qNoProcess.getResponse());
			String responseName = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setResponseName(responseName);
		}
		
		if(qNoProcess.getApproval02()!=null)
		{
			Users u = usersRepository.findOne(qNoProcess.getApproval02());
			String approval02 = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setApproval02Name(approval02);
		}
		
		if(qNoProcess.getApproval03()!=null)
		{
			Users u = usersRepository.findOne(qNoProcess.getApproval03());
			String approval03 = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setApproval03Name(approval03);
		}
		
		if(qNoProcess.getOwner()!=null)
		{
			Users u = usersRepository.findOne(qNoProcess.getOwner());
			String ownerName = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setOwnerName(ownerName);
		}
		
		if(qNoProcess.getCreationTime()!=null)
		{
			pc.setTime(qNoProcess.getCreationTime());
		}
		return pc;
	}
	
}
