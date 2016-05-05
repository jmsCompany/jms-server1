package com.jms.service.production;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jms.domain.db.FCostCenter;
import com.jms.domain.db.PBom;
import com.jms.domain.db.PWo;
import com.jms.domain.db.PWoRoute;
import com.jms.domain.db.PWorkCenter;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SStk;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.production.WSPRoutineD;
import com.jms.domain.ws.production.WSPWo;
import com.jms.domain.ws.production.WSPWorkCenter;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.f.FCostCenterRepository;
import com.jms.repositories.p.PBomRepository;
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
	private PBomRepository pBomRepository;
	
	
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
			SMaterial s = pWo.getSSo().getSMaterial();
			if(s!=null)
			{
				pc.setMaterialId(s.getIdMaterial());
				pc.setPno(s.getPno());
				pc.setRev(s.getRev());
				pc.setDes(s.getDes());

			}
		}
		for(PWoRoute routine:pWo.getPWoRoutes())
		{
			WSPRoutineD wd = new WSPRoutineD();
			wd.setIdRoutineD(routine.getPRoutineD().getIdRoutineD());
			wd.setRouteNo(routine.getPRoutineD().getRouteNo());
			pc.getRoutines().add(wd);
		}
	//	pc.setQty(pWo.getQty());
		return pc;
	}
	
	
	public List<WSSelectObj> getMaterialsByWoId(@RequestParam("woId") Long woId) throws Exception {
		
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		SMaterial s = pWoRepository.findByWoId(woId); //
		logger.debug("woId: " + woId + " , materialId: " + s.getIdMaterial());
		PBom pBom = pBomRepository.findProductByMaterialId(s.getIdMaterial());
		
		
		if(pBom!=null)
		{
			for(PBom p: pBom.getPBoms())
			{
				SMaterial material =p.getSMaterial();
				WSSelectObj w = new WSSelectObj(material.getIdMaterial(),material.getPno()+"-"+ material.getRev()+"-"+material.getDes());
			    ws.add(w);
			   
			}
			
		}


		return ws;
	}
	

}
