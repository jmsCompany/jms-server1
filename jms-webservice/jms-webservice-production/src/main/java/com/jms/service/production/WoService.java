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
import com.jms.domain.db.PWo;
import com.jms.domain.db.PWorkCenter;
import com.jms.domain.db.SStk;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.production.WSPWo;
import com.jms.domain.ws.production.WSPWorkCenter;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.f.FCostCenterRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.repositories.s.SSoRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class WoService {

	private static final Logger logger = LogManager.getLogger(WoService.class
			.getCanonicalName());
	@Autowired
	private PWoRepository pWoRepository;
	@Autowired
	private SSoRepository sSoRepository;
	
	
	@Autowired 
	private CompanyRepository companyRepository;
	@Autowired 
	private PStatusDicRepository pStatusDicRepository;
	

	@Autowired
	private SecurityUtils securityUtils;
	

	
	@Transactional(readOnly=false)
	public WSPWo saveWSPWo(WSPWo wsPWo) throws Exception {
	    PWo pWo;
		if(wsPWo.getIdWo()!=null&&!wsPWo.getIdWo().equals(0l))
		{
			pWo = pWoRepository.findOne(wsPWo.getIdWo());
		}
		else
		{
			pWo = new PWo();
			pWo.setCreationTime(new Date());
		}
		PWo dbPWo= toDBPWo(wsPWo,pWo);
		dbPWo = pWoRepository.save(dbPWo);
		wsPWo.setIdWo(dbPWo.getIdWo());
		return wsPWo;		
		
	}

	@Transactional(readOnly=false)
	public Valid deletePWo(Long woId)
	{
		Valid valid = new Valid();
		
		pWoRepository.delete(woId);
		valid.setValid(true);
		
		return valid;
	}

	
	@Transactional(readOnly=true) 
	public WSPWo findWSPwo(Long woId) throws Exception
	{	
		PWo pWo = pWoRepository.findOne(woId);
		return  toWSPWo(pWo);
		
	}

	
	private PWo toDBPWo(WSPWo wsPWo,PWo pWo) throws Exception
	{
	
		PWo dbPWo = (PWo)BeanUtil.shallowCopy(wsPWo, PWo.class, pWo);

        if(wsPWo.getSoId()!=null)
        	dbPWo.setSSo(sSoRepository.findOne(wsPWo.getSoId()));
		dbPWo.setUsers(securityUtils.getCurrentDBUser());
		if(wsPWo.getStatusId()!=null)
		dbPWo.setPStatusDic(pStatusDicRepository.findOne(wsPWo.getStatusId()));
		return dbPWo;
	}
	
	private WSPWo toWSPWo(PWo pWo) throws Exception
	{
		WSPWo pc = (WSPWo)BeanUtil.shallowCopy(pWo, WSPWo.class, null);
	
		if(pWo.getUsers()!=null)
		{
			pc.setCreator(pWo.getUsers().getName());
		}
		if(pWo.getPStatusDic()!=null)
		{
			pc.setStatus(pWo.getPStatusDic().getName());
			pc.setStatusId(pWo.getPStatusDic().getIdPstatus());
		}
		if(pWo.getSSo()!=null)
		{
			pc.setSo(pWo.getSSo().getCodeSo());
			pc.setSoId(pWo.getSSo().getIdSo());
		}
		return pc;
	}

}
