package com.jms.service.production;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.Config;
import com.jms.domain.db.FCostCenter;
import com.jms.domain.db.PStopsCode;
import com.jms.domain.db.PSubCode;
import com.jms.domain.db.PWorkCenter;
import com.jms.domain.db.SStk;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.p.WSPStopsCode;
import com.jms.domain.ws.p.WSPWorkCenter;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.f.FCostCenterRepository;
import com.jms.repositories.p.PStopsCodeRepository;
import com.jms.repositories.p.PSubCodeRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class PStopsCodeService {

	private static final Logger logger = LogManager.getLogger(PStopsCodeService.class
			.getCanonicalName());
	@Autowired
	private PStopsCodeRepository pStopsCodeRepository;
	@Autowired
	private  PSubCodeRepository pSubCodeRepository;
	
	@Autowired 
	private CompanyRepository companyRepository;
	

	@Autowired
	private SecurityUtils securityUtils;
	


	
	@Transactional(readOnly=false)
	public WSPStopsCode saveWSPStopsCode(WSPStopsCode wsPStopsCode) throws Exception {
		PSubCode pSubCode ;
		if(wsPStopsCode.getIdStopsCode()!=null&&!wsPStopsCode.getIdStopsCode().equals(0l))
		{
			pSubCode = pSubCodeRepository.findOne(wsPStopsCode.getIdStopsCode());
		}
		else
		{
			pSubCode = new PSubCode();
		}
		PSubCode dbPSubCode= toDBPSubCode(wsPStopsCode,pSubCode);
		dbPSubCode = pSubCodeRepository.save(dbPSubCode);
		wsPStopsCode.setIdStopsCode(dbPSubCode.getIdSubCode());
		return wsPStopsCode;		
		
	}
	
	
	@Transactional(readOnly=false)
	public void loadSubCodes()  {
		
	for(String s: Config.subCodes)
	{
		PSubCode pSubCode  = new PSubCode();
		pSubCode.setSubCode(s);
		pSubCodeRepository.save(pSubCode);
		
	}
	
		
	}

	@Transactional(readOnly=false)
	public Valid deletePStopsCode(Long idStopsCode)
	{
		Valid valid = new Valid();
		
		pSubCodeRepository.delete(idStopsCode);
		valid.setValid(true);
		
		return valid;
	}

	
	@Transactional(readOnly=true) 
	public WSPStopsCode findWSPStopsCode(Long idStopsCode) throws Exception
	{	
		PSubCode pSubCode = pSubCodeRepository.findOne(idStopsCode);
		return  toWSPStopsCode(pSubCode);
		
	}

	
	private PSubCode toDBPSubCode(WSPStopsCode wsPStopsCode,PSubCode pSubCode) throws Exception
	{
	
		pSubCode.setSubDes(wsPStopsCode.getDes());
		pSubCode.setSubCode(wsPStopsCode.getCode());
		
		return pSubCode;
	}
	
	private WSPStopsCode toWSPStopsCode(PSubCode pSubCode) throws Exception
	{
		WSPStopsCode pc = new WSPStopsCode();
		pc.setCode(pSubCode.getSubCode());
		pc.setDes(pSubCode.getSubDes());
		pc.setIdStopsCode(pSubCode.getIdSubCode());
		return pc;
	}

}
