
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
import com.jms.domain.db.PRoutine;
import com.jms.domain.db.PRoutineD;
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
import com.jms.domain.ws.production.WSPRoutine;
import com.jms.domain.ws.production.WSPRoutineD;
import com.jms.domain.ws.production.WSPWo;
import com.jms.domain.ws.production.WSPWorkCenter;
import com.jms.domain.ws.production.WSShiftPlan;
import com.jms.domain.ws.production.WSShiftPlanD;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.f.FCostCenterRepository;
import com.jms.repositories.p.PAttDrawRepository;
import com.jms.repositories.p.PBomLabelRepository;
import com.jms.repositories.p.PBomRepository;
import com.jms.repositories.p.PDrawRepository;
import com.jms.repositories.p.PLineRepository;
import com.jms.repositories.p.PRoutineDRepository;
import com.jms.repositories.p.PRoutineRepository;
import com.jms.repositories.p.PShiftPlanDRepository;
import com.jms.repositories.p.PShiftPlanRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SSoRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class RoutineService {

	private static final Logger logger = LogManager.getLogger(RoutineService.class
			.getCanonicalName());
	@Autowired
	private RoutineDService routineDService;
	@Autowired
	private PRoutineDRepository pRoutineDRepository;
	@Autowired
	private PRoutineRepository pRoutineRepository;
	@Autowired
	private PLineRepository pLineRepository;
	
	@Autowired
	private PWorkCenterRepository pWorkCenterRepository;
	@Autowired
	private PAttDrawRepository pAttDrawRepository;
	
	@Autowired
	private PDrawRepository pDrawRepository;
	@Autowired
	private UsersRepository usersRepository;
	
	
	@Autowired
	private SMaterialRepository sMaterialRepository;
	@Autowired 
	private CompanyRepository companyRepository;
	@Autowired 
	private PStatusDicRepository pStatusDicRepository;
	

	@Autowired
	private SecurityUtils securityUtils;
	
	
	@Transactional(readOnly=false)
	public WSPRoutine saveWSPRoutine(WSPRoutine wsPRoutine) throws Exception {
		PRoutine pRoutine;
		if(wsPRoutine.getIdRoutine()!=null&&!wsPRoutine.getIdRoutine().equals(0l))
		{
			pRoutine = pRoutineRepository.findOne(wsPRoutine.getIdRoutine());
			pRoutineDRepository.delete(pRoutine.getPRoutineDs());
		}
		else
		{
			pRoutine = new PRoutine();
	
		}
		PRoutine dbPRoutine= toDBPRoutine(wsPRoutine,pRoutine);
		dbPRoutine = pRoutineRepository.save(dbPRoutine);
	

		
		for(String k:wsPRoutine.getWsRoutineDs().keySet())
		{
			WSPRoutineD wm =wsPRoutine.getWsRoutineDs().get(k);
			wm.setRoutineId(dbPRoutine.getIdRoutine());
			routineDService.saveWSPRoutineD(wm);
		}
	
		wsPRoutine.setIdRoutine(dbPRoutine.getIdRoutine());
		return wsPRoutine;		
		
	}

	@Transactional(readOnly=false)
	public Valid deletePRoutine(Long idPRoutine)
	{
		Valid valid = new Valid();
		pRoutineRepository.delete(idPRoutine);
		valid.setValid(true);
		
		return valid;
	}
	
	
	@Transactional(readOnly=true) 
	public WSPRoutine findWSPRoutine(Long idPRoutine) throws Exception
	{	
		PRoutine pRoutine= pRoutineRepository.findOne(idPRoutine);
		return  toWSPRoutine(pRoutine);
		
	}
	
	protected PRoutine toDBPRoutine(WSPRoutine wsPRoutine,PRoutine pRoutine) throws Exception
	{
	
		PRoutine dbPRoutine = (PRoutine)BeanUtil.shallowCopy(wsPRoutine, PRoutine.class, pRoutine);

        if(wsPRoutine.getCompanyId()!=null)
        {
        	dbPRoutine.setCompany(companyRepository.findOne(wsPRoutine.getCompanyId()));
        }
        if(wsPRoutine.getDrawId()!=null)
        {
        	dbPRoutine.setPDraw(pDrawRepository.findOne(wsPRoutine.getDrawId()));
        }
        if(wsPRoutine.getLineId()!=null)
        {
        	dbPRoutine.setPLine(pLineRepository.findOne(wsPRoutine.getLineId()));
        }
        if(wsPRoutine.getMaterialId()!=null)
        {
        	dbPRoutine.setSMaterial(sMaterialRepository.findOne(wsPRoutine.getMaterialId()));
        }
        if(wsPRoutine.getStatusId()!=null)
        {
        	dbPRoutine.setPStatusDic(pStatusDicRepository.findOne(wsPRoutine.getStatusId()));
        }

		return dbPRoutine;
	}
	
	protected WSPRoutine toWSPRoutine(PRoutine pRoutine) throws Exception
	{
		WSPRoutine pc = (WSPRoutine)BeanUtil.shallowCopy(pRoutine, WSPRoutine.class, null);
	    if(pRoutine.getCompany()!=null)
	    {
	    	pc.setCompanyId(pRoutine.getCompany().getIdCompany());
	    	pc.setCompanyName(pRoutine.getCompany().getCompanyName());
	    
	    }
	    if(pRoutine.getPDraw()!=null)
	    {
	    	pc.setDrawId(pRoutine.getPDraw().getIdDraw());
	    	pc.setDrawNo(pRoutine.getPDraw().getDrawNo());
	    	pc.setDrawVer(pRoutine.getPDraw().getDrawVer());

	    }
	    if(pRoutine.getPLine()!=null)
	    {
	    	pc.setLine(pRoutine.getPLine().getPline());
	    	pc.setLineId(pRoutine.getPLine().getIdPline());
	    }
	    if(pRoutine.getPStatusDic()!=null)
	    {
	    	pc.setStatus(pRoutine.getPStatusDic().getName());
	    	pc.setStatusId(pRoutine.getPStatusDic().getIdPstatus());
	    }
	    if(pRoutine.getSMaterial()!=null)
	    {
	    	pc.setMaterialDes(pRoutine.getSMaterial().getDes());
	    	pc.setMaterialId(pRoutine.getSMaterial().getIdMaterial());
	    	pc.setMaterialRev(pRoutine.getSMaterial().getRev());
	    	pc.setpNo(pRoutine.getSMaterial().getPno());
	    }
	    int i=0;
	    for(PRoutineD p: pRoutine.getPRoutineDs())
	    {
	    	pc.getWsRoutineDs().put("item"+i, routineDService.toWSPRoutineD(p));
	    	i++;
	    }
	  
		return pc;
	}

}
