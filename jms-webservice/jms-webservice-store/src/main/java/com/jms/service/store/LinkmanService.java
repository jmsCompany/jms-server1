package com.jms.service.store;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.SCompanyCo;
import com.jms.domain.db.SLinkman;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.s.WSCompanyCo;
import com.jms.domain.ws.s.WSLinkman;
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
		if(wsLinkman.getIdLinkman()!=null&&!wsLinkman.getIdLinkman().equals(0l))
		{
			sLinkman = sLinkmanRepository.findOne(wsLinkman.getIdLinkman());
		}
		else
		{
			sLinkman = new SLinkman();
		}
		SLinkman dbSLinkman = toDBSLinkman(wsLinkman,sLinkman);
		sLinkmanRepository.save(dbSLinkman);
		wsLinkman.setIdLinkman(dbSLinkman.getIdLinkman());
		return wsLinkman;		
		
	}

	@Transactional(readOnly=false)
	public Valid deleteLinkman(Long linkmanId)
	{
		Valid valid = new Valid();
		
		sLinkmanRepository.delete(linkmanId);
		valid.setValid(true);
		
		return valid;
	}

	
	@Transactional(readOnly=true) 
	public WSLinkman findLinkman(Long linkmanId) throws Exception
	{	
		SLinkman linkman = sLinkmanRepository.findOne(linkmanId);
		return  toWSLinkman(linkman);
		
	}

	
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
		dbLinkman.setEMail(wsLinkman.getEmail());
		logger.warn("set email: " +wsLinkman.getEmail() );
		return dbLinkman;
	}
	
	private WSLinkman toWSLinkman(SLinkman sLinkman) throws Exception
	{
		WSLinkman wsLinkman = (WSLinkman)BeanUtil.shallowCopy(sLinkman, WSLinkman.class, null);
		if(sLinkman.getSStatusDic()!=null)
		{
			wsLinkman.setStatus(sLinkman.getSStatusDic().getName());
			wsLinkman.setStatusId(sLinkman.getSStatusDic().getId());
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
		wsLinkman.setEmail(sLinkman.getEMail());	
		logger.warn("set email: " +sLinkman.getEMail() );
		return wsLinkman;
	}

}
