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
import com.jms.domain.db.PBom;
import com.jms.domain.db.PShiftPlan;
import com.jms.domain.db.PShiftPlanD;
import com.jms.domain.db.PWo;
import com.jms.domain.db.PWorkCenter;
import com.jms.domain.db.SStk;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.production.WSPBom;
import com.jms.domain.ws.production.WSPBomItem;
import com.jms.domain.ws.production.WSPWo;
import com.jms.domain.ws.production.WSPWorkCenter;
import com.jms.domain.ws.production.WSShiftPlan;
import com.jms.domain.ws.production.WSShiftPlanD;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.f.FCostCenterRepository;
import com.jms.repositories.p.PBomLabelRepository;
import com.jms.repositories.p.PBomRepository;
import com.jms.repositories.p.PShiftPlanDRepository;
import com.jms.repositories.p.PShiftPlanRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SSoRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class ShiftPlanDService {

	private static final Logger logger = LogManager.getLogger(ShiftPlanDService.class
			.getCanonicalName());
	@Autowired
	private PShiftPlanDRepository pShiftPlanDRepository;
	@Autowired
	private PShiftPlanRepository pShiftPlanRepository;
	
	@Autowired
	private PWorkCenterRepository pWorkCenterRepository;
	
	
	@Autowired
	private SMaterialRepository sMaterialRepository;
	@Autowired 
	private CompanyRepository companyRepository;
	@Autowired 
	private PStatusDicRepository pStatusDicRepository;
	

	@Autowired
	private SecurityUtils securityUtils;
	
  /*
	
	@Transactional(readOnly=false)
	public WSShiftPlanD saveWSPShiftPlanD(WSShiftPlanD wsShiftPlanD) throws Exception {
		PShiftPlanD pShiftPlanD;
		if(wsShiftPlanD.get!=null&&!wsPBomItem.getIdBom().equals(0l))
		{
			pBom = pBomRepository.findOne(wsPBomItem.getIdBom());
		}
		else
		{
			pBom = new PBom();
	
		}
		PBom dbPBom= toDBPBom(wsPBomItem,pBom);
		dbPBom = pBomRepository.save(dbPBom);
		wsPBomItem.setIdBom(dbPBom.getIdBom());
		return wsPBomItem;		
		
	}

	@Transactional(readOnly=false)
	public Valid deletePBom(Long bomId)
	{
		Valid valid = new Valid();
		
		pBomRepository.delete(bomId);
		valid.setValid(true);
		
		return valid;
	}

	
	@Transactional(readOnly=true) 
	public WSPBomItem findWSPBomItem(Long bomId) throws Exception
	{	
		PBom pBom= pBomRepository.findOne(bomId);
		return  toWSPBomItem(pBom);
		
	}

	*/
	protected PShiftPlanD toDBPShiftPlanD(WSShiftPlanD wsShiftPlanD,PShiftPlanD pShiftPlanD) throws Exception
	{
	

		PShiftPlanD dbPShiftPlanD = (PShiftPlanD)BeanUtil.shallowCopy(wsShiftPlanD, PShiftPlanD.class, pShiftPlanD);

        if(wsShiftPlanD.getShiftPlanId()!=null)
        {
        	dbPShiftPlanD.setPShiftPlan(pShiftPlanRepository.getOne(wsShiftPlanD.getShiftPlanId()));
        }

		return dbPShiftPlanD;
	}
	
	private WSShiftPlanD toWSShiftPlanD(PShiftPlanD pShiftPlanD) throws Exception
	{
		WSShiftPlanD pc = (WSShiftPlanD)BeanUtil.shallowCopy(pShiftPlanD, WSShiftPlanD.class, null);
	    if(pShiftPlanD.getPShiftPlan()!=null)
	    {
	    	pc.setShiftPlanId(pShiftPlanD.getPShiftPlan().getIdShiftPlan());
	    }
	  
		return pc;
	}

}
