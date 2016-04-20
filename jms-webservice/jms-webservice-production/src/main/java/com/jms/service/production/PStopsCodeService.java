package com.jms.service.production;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.FCostCenter;
import com.jms.domain.db.PStopsCode;
import com.jms.domain.db.PWorkCenter;
import com.jms.domain.db.SStk;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.production.WSPStopsCode;
import com.jms.domain.ws.production.WSPWorkCenter;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.f.FCostCenterRepository;
import com.jms.repositories.p.PStopsCodeRepository;
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
	private CompanyRepository companyRepository;
	

	@Autowired
	private SecurityUtils securityUtils;
	


	
	@Transactional(readOnly=false)
	public WSPStopsCode saveWSPStopsCode(WSPStopsCode wsPStopsCode) throws Exception {
		PStopsCode pStopsCode ;
		if(wsPStopsCode.getIdStopsCode()!=null&&!wsPStopsCode.getIdStopsCode().equals(0l))
		{
			pStopsCode = pStopsCodeRepository.findOne(wsPStopsCode.getIdStopsCode());
		}
		else
		{
			pStopsCode = new PStopsCode();
		}
		PStopsCode dbpPStopsCode= toDBPStopsCode(wsPStopsCode,pStopsCode);
		dbpPStopsCode = pStopsCodeRepository.save(dbpPStopsCode);
		wsPStopsCode.setIdStopsCode(dbpPStopsCode.getIdStopsCode());
		return wsPStopsCode;		
		
	}

	@Transactional(readOnly=false)
	public Valid deletePStopsCode(Long idStopsCode)
	{
		Valid valid = new Valid();
		
		pStopsCodeRepository.delete(idStopsCode);
		valid.setValid(true);
		
		return valid;
	}

	
	@Transactional(readOnly=true) 
	public WSPStopsCode findWSPStopsCode(Long idStopsCode) throws Exception
	{	
		PStopsCode pStopsCode = pStopsCodeRepository.findOne(idStopsCode);
		return  toWSPStopsCode(pStopsCode);
		
	}

	
	private PStopsCode toDBPStopsCode(WSPStopsCode wsPStopsCode,PStopsCode pStopsCode) throws Exception
	{
	
		PStopsCode dbPWorkCenter = (PStopsCode)BeanUtil.shallowCopy(wsPStopsCode, PStopsCode.class, pStopsCode);
		
		return dbPWorkCenter;
	}
	
	private WSPStopsCode toWSPStopsCode(PStopsCode pStopsCode) throws Exception
	{
		WSPStopsCode pc = (WSPStopsCode)BeanUtil.shallowCopy(pStopsCode, WSPStopsCode.class, null);
		return pc;
	}

}
