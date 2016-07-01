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
public class ShiftPlanService {

	private static final Logger logger = LogManager.getLogger(ShiftPlanService.class
			.getCanonicalName());
	@Autowired
	private PShiftPlanDRepository pShiftPlanDRepository;
	@Autowired
	private ShiftPlanDService shiftPlanDService;
	@Autowired
	private PShiftPlanRepository pShiftPlanRepository;
	

	
	
	@Autowired
	private SMaterialRepository sMaterialRepository;
	@Autowired 
	private CompanyRepository companyRepository;
	@Autowired 
	private PStatusDicRepository pStatusDicRepository;
	

	@Autowired
	private SecurityUtils securityUtils;
	

	
	@Transactional(readOnly=false)
	public WSShiftPlan saveWSPShiftPlan(WSShiftPlan wsShiftPlan) throws Exception {
		PShiftPlan pShiftPlan;
		if(wsShiftPlan.getIdShiftPlan()!=null&&!wsShiftPlan.getIdShiftPlan().equals(0l))
		{
			pShiftPlan = pShiftPlanRepository.findOne(wsShiftPlan.getIdShiftPlan());
			//delete pshiftPlanD
			pShiftPlanDRepository.delete(pShiftPlan.getPShiftPlanDs());
			pShiftPlan.getPShiftPlanDs().clear();
		}
		else
		{
			pShiftPlan = new PShiftPlan();
	
		}
		PShiftPlan dbPShiftPlan= toDBPShiftPlan(wsShiftPlan,pShiftPlan);
		dbPShiftPlan = pShiftPlanRepository.save(dbPShiftPlan);
		wsShiftPlan.setIdShiftPlan(dbPShiftPlan.getIdShiftPlan());
		
		for(WSShiftPlanD w: wsShiftPlan.getShifPlanItems().values())
		{
			w.setShiftPlanId(wsShiftPlan.getIdShiftPlan());
			shiftPlanDService.saveWSPShiftPlanD(w);
		}
		return wsShiftPlan;		
		
	}

	@Transactional(readOnly=false)
	public Valid deletePShiftPlan(Long idShiftPlan)
	{
		Valid valid = new Valid();
		pShiftPlanRepository.delete(idShiftPlan);
		valid.setValid(true);
		
		return valid;
	}
	
	
	@Transactional(readOnly=true) 
	public WSShiftPlan findWSShiftPlan(Long idShiftPlan) throws Exception
	{	
		PShiftPlan pShiftPlan= pShiftPlanRepository.findOne(idShiftPlan);
		return  toWSShiftPlan(pShiftPlan);
		
	}
	
	protected PShiftPlan toDBPShiftPlan(WSShiftPlan wsShiftPlan,PShiftPlan pShiftPlan) throws Exception
	{
	

		PShiftPlan dbPShiftPlan = (PShiftPlan)BeanUtil.shallowCopy(wsShiftPlan, PShiftPlan.class, pShiftPlan);

		if(wsShiftPlan.getCompanyId()!=null)
		{
			dbPShiftPlan.setCompany(companyRepository.findOne(wsShiftPlan.getCompanyId()));
		}
		else
		{
			dbPShiftPlan.setCompany(securityUtils.getCurrentDBUser().getCompany());
		}
		if(wsShiftPlan.getStatusId()!=null)
		{
			dbPShiftPlan.setPStatusDic(pStatusDicRepository.findOne(wsShiftPlan.getStatusId()));
		}
		return dbPShiftPlan;
	}
	
	private WSShiftPlan toWSShiftPlan(PShiftPlan pShiftPlan) throws Exception
	{
		WSShiftPlan pc = (WSShiftPlan)BeanUtil.shallowCopy(pShiftPlan, WSShiftPlan.class, null);
	    if(pShiftPlan.getPStatusDic()!=null)
	    {
	    	pc.setStatus(pShiftPlan.getPStatusDic().getName());
	    	pc.setStatusId(pShiftPlan.getPStatusDic().getIdPstatus());
	    }
	    if(pShiftPlan.getCompany()!=null)
	    {
	    	pc.setCompanyId(pShiftPlan.getCompany().getIdCompany());
	    	pc.setCompanyName(pShiftPlan.getCompany().getCompanyName());
	    }
	    int i=0;
	    for(PShiftPlanD ps: pShiftPlanDRepository.getByShiftId(pShiftPlan.getIdShiftPlan()))
	    {
	    	pc.getShifPlanItems().put("item"+i, shiftPlanDService.toWSShiftPlanD(ps));
	    	i++;
	    	
	    }
	  
		return pc;
	}

}
