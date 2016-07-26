package com.jms.service.production;


import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.PWip;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.p.WSPWip;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWipRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class PWipService {

	private static final Logger logger = LogManager.getLogger(PWipService.class
			.getCanonicalName());
	@Autowired
	private PWipRepository pWipRepository;
	
	
	@Autowired 
	private CompanyRepository companyRepository;
	
	@Autowired 
	private PStatusDicRepository pStatusDicRepository;
	

	@Autowired
	private SecurityUtils securityUtils;
	
	@Transactional(readOnly=true)
	public List<WSSelectObj> findWipObjs()
	{
		List<WSSelectObj> wsWorkCenterList = new ArrayList<WSSelectObj>();
		for(PWip w: pWipRepository.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany()))
		{
			WSSelectObj o = new WSSelectObj(w.getIdWip(),w.getWip());
			wsWorkCenterList.add(o);
		}
		
		return wsWorkCenterList;
	}
	


	
	@Transactional(readOnly=false)
	public WSPWip saveWSPWip(WSPWip wsPWip) throws Exception {
		 PWip pWip;
		if(wsPWip.getIdWip()!=null&&!wsPWip.getIdWip().equals(0l))
		{
			pWip = pWipRepository.findOne(wsPWip.getIdWip());
		}
		else
		{
			pWip = new PWip();
		}
		PWip dbPWip= toDBPWip(wsPWip,pWip);
		dbPWip = pWipRepository.save(dbPWip);
		wsPWip.setIdWip(dbPWip.getIdWip());
		return wsPWip;		
		
	}

	@Transactional(readOnly=false)
	public Valid deletePWip(Long wipId)
	{
		Valid valid = new Valid();
		
		pWipRepository.delete(wipId);
		valid.setValid(true);
		
		return valid;
	}

	
	@Transactional(readOnly=true) 
	public WSPWip findWSPWip(Long wipId) throws Exception
	{	
		PWip pWip = pWipRepository.findOne(wipId);
		return  toWSPWip(pWip);
		
	}

	
	private PWip toDBPWip(WSPWip wsPWip,PWip pWip) throws Exception
	{
	
		PWip dbPWip = (PWip)BeanUtil.shallowCopy(wsPWip, PWip.class, pWip);

		dbPWip.setCompany(securityUtils.getCurrentDBUser().getCompany());
		if(wsPWip.getStatusId()!=null)
		{
			dbPWip.setPStatusDic(pStatusDicRepository.findOne(wsPWip.getStatusId()));
		}
		
		return dbPWip;
	}
	
	private WSPWip  toWSPWip(PWip pWip) throws Exception
	{
		WSPWip pc = (WSPWip)BeanUtil.shallowCopy(pWip, WSPWip.class, null);
		if(pWip.getCompany()!=null)
		{
			pc.setCompanyId(pWip.getCompany().getIdCompany());
		}
		if(pWip.getPStatusDic()!=null)
		{
			pc.setStatus(pWip.getPStatusDic().getName());
			pc.setStatusId(pWip.getPStatusDic().getIdPstatus());
		}
		return pc;
	}

}
