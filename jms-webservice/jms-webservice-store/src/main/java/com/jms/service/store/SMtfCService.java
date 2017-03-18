package com.jms.service.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.Company;
import com.jms.domain.db.SComCom;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMtfC;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.s.WSSComCom;
import com.jms.domain.ws.s.WSSMtf;
import com.jms.domain.ws.s.WSSMtfC;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SMtfCRepository;
import com.jms.web.security.SecurityUtils;



@Service
@Transactional
public class SMtfCService {

	private static final Logger logger = LogManager.getLogger(SMtfCService.class
			.getCanonicalName());
	@Autowired
	private SMtfCRepository sMtfCRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired private SMaterialRepository sMaterialRepository;
	
	@Autowired 
	private  SecurityUtils securityUtils;

	public Valid saveMtfC(WSSMtfC wsSMtfC) throws Exception {
		Valid valid = new Valid();
		
		SMtfC smtfC;
		
		if(wsSMtfC.getId()!=null&&!wsSMtfC.getId().equals(0l))
		{
			smtfC = sMtfCRepository.findOne(wsSMtfC.getId());
		}
		else
		{
			smtfC = new SMtfC();
		}
		
		SMtfC dbSMtfC = toDBSMtfC(wsSMtfC,smtfC);
		
		dbSMtfC.setIdCompany1(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		SMaterial m = sMaterialRepository.getOne(wsSMtfC.getMaterialId());
		dbSMtfC.setPno(m.getPno());
		dbSMtfC.setMaterialName(m.getDes());

		
		sMtfCRepository.save(dbSMtfC);
		
		valid.setValid(true);
		return valid;
	}




	
	private SMtfC toDBSMtfC(WSSMtfC wsSMtfC,SMtfC sMtfC) throws Exception
	{

		SMtfC dbSMtfC = (SMtfC)BeanUtil.shallowCopy(wsSMtfC, SMtfC.class, sMtfC);

		return dbSMtfC;
	}
	
	private WSSMtfC toWSSMtfC(SMtfC sMtfC) throws Exception
	{
		WSSMtfC fc = (WSSMtfC)BeanUtil.shallowCopy(sMtfC, WSSMtfC.class, null);
	    return fc;
	}

}
