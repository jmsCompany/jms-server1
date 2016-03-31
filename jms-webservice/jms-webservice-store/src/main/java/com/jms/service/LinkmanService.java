package com.jms.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.SCompanyCo;
import com.jms.domain.db.SLinkman;
import com.jms.domain.db.SLinkmanId;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.store.WSCompanyCo;
import com.jms.domain.ws.store.WSLinkman;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.s.SCompanyCoRepository;
import com.jms.repositories.s.SCountryDicRepository;
import com.jms.repositories.s.SGenderDicRepository;
import com.jms.repositories.s.SLevelDicRepository;
import com.jms.repositories.s.SLinkmanRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.s.SStermDicRepository;
import com.jms.repositories.s.STypeDicRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class LinkmanService {

	private static final Logger logger = LogManager.getLogger(LinkmanService.class
			.getCanonicalName());
	@Autowired
	private SCompanyCoRepository sCompanyCoRepository;

	@Autowired 
	private SLinkmanRepository sLinkmanRepository;
	
	@Autowired 
	private SGenderDicRepository sGenderDicRepository;
	
	@Autowired 
	private SStatusDicRepository sStatusDicRepository;
	
	@Autowired 
	private SStermDicRepository sStermDicRepository;
	
	@Autowired 
	private STypeDicRepository sTypeDicRepository;
	
	
	@Transactional(readOnly=true)
	public List<WSLinkman> getLinkmans(Long companyCoId) throws Exception {
		List<SLinkman> sLinkmans = sLinkmanRepository.getByCompanyCoId(companyCoId);
		List<WSLinkman> wsLinkmans = new ArrayList<WSLinkman>();
		for(SLinkman s:sLinkmans)
		{
			wsLinkmans.add(toWSLinkman(s));
		}
		
		return wsLinkmans;
		
	}

	
	@Transactional(readOnly=false)
	public WSLinkman saveWSLinkman(WSLinkman wsLinkman) throws Exception {
		SLinkman sLinkman;
		if(wsLinkman.getId()!=null&&!wsLinkman.getId().equals(0l))
		{
			SLinkmanId id = new SLinkmanId();
			id.setId(wsLinkman.getId());
			id.setName(wsLinkman.getName());
			sLinkman = sLinkmanRepository.findOne(id);
		}
		else
		{
			sLinkman = new SLinkman();
		}
		SLinkman dbSLinkman = toDBSLinkman(wsLinkman,sLinkman);
		sLinkmanRepository.save(dbSLinkman);
		//wsLinkman.setId(dbSLinkman.geti);
		return wsLinkman;		
		
	}
	/*
	@Transactional(readOnly=false)
	public Valid deleteCompanyCo(Long companyCoId)
	{
		Valid valid = new Valid();
		SCompanyCo dbCompanyCo = sCompanyCoRepository.findOne(companyCoId);
		if(!dbCompanyCo.getSMtfResiduals().isEmpty()||!dbCompanyCo.getSPos().isEmpty()||!dbCompanyCo.getSSos().isEmpty())
		{
			valid.setValid(false);
		}
		else
		{
			sCompanyCoRepository.delete(companyCoId);
			valid.setValid(true);
		}
		
		return valid;
	}
	
	
	@Transactional(readOnly=true)
	public WSCompanyCo findCompanyCo(Long companyCoId)
	{	
		SCompanyCo mc = sCompanyCoRepository.findOne(companyCoId);
		return  toWSCompanyCo(mc);
		
	}
*/
	
	private SLinkman toDBSLinkman(WSLinkman wsLinkman,SLinkman sLinkman) throws Exception
	{
	
		SLinkman dbLinkman = (SLinkman)BeanUtil.shallowCopy(wsLinkman, SLinkman.class, sLinkman);
		if(wsLinkman.getCoCompanyId()!=null)
		{
			dbLinkman.setSCompanyCo(sCompanyCoRepository.findOne(wsLinkman.getCoCompanyId()));
		}
		if(wsLinkman.getGenderId()!=null)
		{
			dbLinkman.setSGenderDic(sGenderDicRepository.findOne(wsLinkman.getGenderId()));
		}
		if(wsLinkman.getStatusId()!=null)
		{
			dbLinkman.setSStatusDic(sStatusDicRepository.findOne(wsLinkman.getStatusId()));
		}
		return dbLinkman;
	}
	
	private WSLinkman toWSLinkman(SLinkman sLinkman) throws Exception
	{
		WSLinkman wsLinkman = (WSLinkman)BeanUtil.shallowCopy(sLinkman, WSLinkman.class, null);
		if(sLinkman.getSStatusDic()!=null)
		{
			wsLinkman.setStatus(sLinkman.getSStatusDic().getName());
		}
		if(sLinkman.getSCompanyCo()!=null)
		{
			wsLinkman.setCoCompanyId(sLinkman.getSCompanyCo().getId());
			wsLinkman.setCoCompanyName(sLinkman.getSCompanyCo().getName());
		}
		if(sLinkman.getSGenderDic()!=null)
		{
			wsLinkman.setGender(sLinkman.getSGenderDic().getName());
			wsLinkman.setGenderId(sLinkman.getSGenderDic().getId());
		}
		
		
		return wsLinkman;
	}

}