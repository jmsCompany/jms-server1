package com.jms.service;


import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.SMtf;
import com.jms.domain.db.SMtfMaterial;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.store.WSSMtf;
import com.jms.domain.ws.store.WSSMtfMaterial;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.s.SMtfMaterialRepository;
import com.jms.repositories.s.SMtfRepository;
import com.jms.repositories.s.SMtfTypeDicRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.s.SStkRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.web.security.SecurityUtils;



@Service
@Transactional
public class MtfService {

	private static final Logger logger = LogManager.getLogger(MtfService.class
			.getCanonicalName());

	@Autowired
	private SStatusDicRepository  sStatusDicRepository;
	@Autowired
	private SMtfMaterialRepository sMtfMaterialRepository;
	@Autowired
	private MtfMaterialService mtfMaterialService;
	
	@Autowired
	private SMtfRepository sMtfRepository;
	@Autowired 
	private  SecurityUtils securityUtils;

	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private SStkRepository sStkRepository;
	@Autowired
	private SMtfTypeDicRepository sMtfTypeDicRepository;


	

	public Valid saveMtf(WSSMtf wsSMtf) throws Exception {
		
		SMtf sMtf;
		//create
		if(wsSMtf.getIdMt()==null||wsSMtf.getIdMt().equals(0l))
		{
			sMtf=new SMtf();
		}
		//update
		else
		{
			sMtf = sMtfRepository.findOne(wsSMtf.getIdMt());	
		}
		sMtf=toDBMtf(wsSMtf,sMtf);
		SMtf sMtf1= sMtfRepository.save(sMtf);
		
	
		if(wsSMtf.getIdMt()!=null&&!wsSMtf.getIdMt().equals(0l))
		{
			sMtfMaterialRepository.deleteByMtId(wsSMtf.getIdMt());
		}
		for(String k: wsSMtf.getSmtfItems().keySet())
		{
			System.out.println(k +", ... ");
			WSSMtfMaterial wm =wsSMtf.getSmtfItems().get(k);
			wm.setIdMt(sMtf1.getIdMt());
			mtfMaterialService.saveMtfMaterial(wm);
		}
		
		
		Valid valid = new Valid();
		valid.setValid(true);
		return valid;
	}
	

	public WSSMtf findSMtf (Long smtfId) throws Exception 
	{
		SMtf sMtf = sMtfRepository.findOne(smtfId);
		return toWSSMtf(sMtf);
	}
	
	private SMtf toDBMtf(WSSMtf wsSMtf,SMtf sMtf) throws Exception
	{
	
		SMtf dbSMtf = (SMtf)BeanUtil.shallowCopy(wsSMtf, SMtf.class, sMtf);
		dbSMtf.setCompany(securityUtils.getCurrentDBUser().getCompany());
		if(wsSMtf.getEmpMtUserId()!=null)
		{
			dbSMtf.setUsersByEmpMt(usersRepository.findOne(wsSMtf.getEmpMtUserId()));
		}
		if(wsSMtf.getFromStkId()!=null)
		{
			dbSMtf.setSStkByFromStk(sStkRepository.findOne(wsSMtf.getFromStkId()));
		}
		if(wsSMtf.getToStkId()!=null)
		{
			dbSMtf.setSStkByToStk(sStkRepository.findOne(wsSMtf.getToStkId()));
		}
		if(wsSMtf.getRecMtUserId()!=null)
		{
			dbSMtf.setUsersByRecMt(usersRepository.findOne(wsSMtf.getRecMtUserId()));
		}
		if(wsSMtf.getStatusId()!=null)
		{
			dbSMtf.setSStatusDic(sStatusDicRepository.findOne(wsSMtf.getStatusId()));
		}
		if(wsSMtf.getTypeId()!=null)
		{
			dbSMtf.setSMtfTypeDic(sMtfTypeDicRepository.findOne(wsSMtf.getTypeId()));
		}
		dbSMtf.setCreationTime(new Date());
	
		return dbSMtf;
	}
	
	private WSSMtf toWSSMtf(SMtf sMtf) throws Exception
	{
		WSSMtf wsSMtf = (WSSMtf)BeanUtil.shallowCopy(sMtf, WSSMtf.class, null);
		
		if(sMtf.getSMtfTypeDic()!=null)
		{
			wsSMtf.setType(sMtf.getSMtfTypeDic().getName());
			wsSMtf.setTypeId(sMtf.getSMtfTypeDic().getIdMtfType());
			
		}
		if(sMtf.getSStatusDic()!=null)
		{
			wsSMtf.setStatus(sMtf.getSStatusDic().getName());
			wsSMtf.setStatusId(sMtf.getSStatusDic().getId());
		}
		if(sMtf.getSStkByFromStk()!=null)
		{
			wsSMtf.setFromStk(sMtf.getSStkByFromStk().getDes());
			wsSMtf.setFromStkId(sMtf.getSStkByFromStk().getId());
		}
		if(sMtf.getSStkByToStk()!=null)
		{
			wsSMtf.setToStk(sMtf.getSStkByToStk().getDes());
			wsSMtf.setToStkId(sMtf.getSStkByToStk().getId());
		}
		if(sMtf.getUsersByEmpMt()!=null)
		{
			wsSMtf.setEmpMtUser(sMtf.getUsersByEmpMt().getName());
			wsSMtf.setEmpMtUserId(sMtf.getUsersByEmpMt().getIdUser());
		}
		if(sMtf.getUsersByRecMt()!=null)
		{
			wsSMtf.setRecMtUser(sMtf.getUsersByRecMt().getName());
			wsSMtf.setRecMtUserId(sMtf.getUsersByRecMt().getIdUser());
		}
		for(SMtfMaterial s: sMtf.getSMtfMaterials())
		{
			wsSMtf.getSmtfItems().put("item"+s.getIdMtfMaterial(), mtfMaterialService.toWSSMtfMaterial(s));
		}
	
		return wsSMtf;
	}


}