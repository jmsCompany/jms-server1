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
import com.jms.domain.db.PLine;
import com.jms.domain.db.PWorkCenter;
import com.jms.domain.db.SStk;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.production.WSPLine;
import com.jms.domain.ws.production.WSPWorkCenter;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.f.FCostCenterRepository;
import com.jms.repositories.p.PLineRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class PlineService {

	private static final Logger logger = LogManager.getLogger(PlineService.class
			.getCanonicalName());
	@Autowired
	private PLineRepository pLineRepository;
	
	
	@Autowired 
	private CompanyRepository companyRepository;
	
	@Autowired 
	private PStatusDicRepository pStatusDicRepository;
	@Autowired
	private SecurityUtils securityUtils;
	
	@Transactional(readOnly=true)
	public List<WSSelectObj> findWorkCenterSelectObjs()
	{
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		for(PLine w: pLineRepository.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany()))
		{
			WSSelectObj o = new WSSelectObj(w.getIdPline(),w.getPline());
			ws.add(o);
		}
		
		return ws;
	}
	


	
	@Transactional(readOnly=false)
	public WSPLine saveWSPLine(WSPLine wsPLine) throws Exception {
		PLine pLine;
		if(wsPLine.getIdPline()!=null&&!wsPLine.getIdPline().equals(0l))
		{
			pLine = pLineRepository.findOne(wsPLine.getIdPline());
		}
		else
		{
			pLine = new PLine();
		}
		PLine dbPLine= toDBPLine(wsPLine,pLine);
		dbPLine = pLineRepository.save(dbPLine);
		wsPLine.setIdPline(dbPLine.getIdPline());
		return wsPLine;		
		
	}

	@Transactional(readOnly=false)
	public Valid deleteWSPLine(Long lineId)
	{
		Valid valid = new Valid();
		
		pLineRepository.delete(lineId);
		valid.setValid(true);
		
		return valid;
	}

	
	@Transactional(readOnly=true) 
	public WSPLine findWSPLine(Long lineId) throws Exception
	{	
		PLine pLine = pLineRepository.findOne(lineId);
		return  toWSPLine(pLine);
		
	}

	
	private PLine toDBPLine(WSPLine wsPLine,PLine pLine) throws Exception
	{
	
		PLine dbPLine = (PLine)BeanUtil.shallowCopy(wsPLine, PLine.class, pLine);

		dbPLine.setCompany(securityUtils.getCurrentDBUser().getCompany());
		if(wsPLine.getStatusId()!=null)
		{
			dbPLine.setPStatusDic(pStatusDicRepository.findOne(wsPLine.getStatusId()));
		}
		
		return dbPLine;
	}
	
	private WSPLine toWSPLine(PLine pLine) throws Exception
	{
		WSPLine pc = (WSPLine)BeanUtil.shallowCopy(pLine, WSPLine.class, null);
		if(pLine.getCompany()!=null)
		{
			pc.setCompanyId(pLine.getCompany().getIdCompany());
		}
		if(pLine.getPStatusDic()!=null)
		{
			pc.setStatus(pLine.getPStatusDic().getName());
			pc.setStatusId(pLine.getPStatusDic().getIdPstatus());
		}
		return pc;
	}

}
