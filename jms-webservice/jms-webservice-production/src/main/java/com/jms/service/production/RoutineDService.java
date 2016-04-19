
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
public class RoutineDService {

	private static final Logger logger = LogManager.getLogger(RoutineDService.class
			.getCanonicalName());
	@Autowired
	private PRoutineDRepository pRoutineDRepository;
	@Autowired
	private PRoutineRepository pRoutineRepository;
	
	@Autowired
	private PWorkCenterRepository pWorkCenterRepository;
	@Autowired
	private PAttDrawRepository pAttDrawRepository;
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
	public WSPRoutineD saveWSPRoutineD(WSPRoutineD wsPRoutineD) throws Exception {
		PRoutineD pRoutineD;
		if(wsPRoutineD.getIdRoutineD()!=null&&!wsPRoutineD.getIdRoutineD().equals(0l))
		{
			pRoutineD = pRoutineDRepository.findOne(wsPRoutineD.getIdRoutineD());
		}
		else
		{
			pRoutineD = new PRoutineD();
	
		}
		PRoutineD dbPRoutineD= toDBPRoutineD(wsPRoutineD,pRoutineD);
		dbPRoutineD = pRoutineDRepository.save(dbPRoutineD);
		wsPRoutineD.setIdRoutineD(dbPRoutineD.getIdRoutineD());
		return wsPRoutineD;		
		
	}

	@Transactional(readOnly=false)
	public Valid deletePRoutineD(Long idPRoutineD)
	{
		Valid valid = new Valid();
		pRoutineDRepository.delete(idPRoutineD);
		valid.setValid(true);
		
		return valid;
	}
	
	
	@Transactional(readOnly=true) 
	public WSPRoutineD findWSPRoutineD(Long idPRoutineD) throws Exception
	{	
		PRoutineD pRoutineD= pRoutineDRepository.findOne(idPRoutineD);
		return  toWSPRoutineD(pRoutineD);
		
	}
	
	protected PRoutineD toDBPRoutineD(WSPRoutineD wsPRoutineD,PRoutineD pRoutineD) throws Exception
	{
	
		PRoutineD dbPRoutineD = (PRoutineD)BeanUtil.shallowCopy(wsPRoutineD, PRoutineD.class, pRoutineD);

        if(wsPRoutineD.getRoutineId()!=null)
        {
        	dbPRoutineD.setPRoutine(pRoutineRepository.findOne(wsPRoutineD.getRoutineId()));
        }
        if(wsPRoutineD.getpAttDrawId()!=null)
        {
        	dbPRoutineD.setPAttDraw(pAttDrawRepository.findOne(wsPRoutineD.getpAttDrawId()));
        }
        if(wsPRoutineD.getUserId()!=null)
        {
        	dbPRoutineD.setUsers(usersRepository.findOne(wsPRoutineD.getUserId()));
        }
        if(wsPRoutineD.getWorkCenterId()!=null)
        {
        	dbPRoutineD.setPWorkCenter(pWorkCenterRepository.findOne(wsPRoutineD.getWorkCenterId()));
        }

		return dbPRoutineD;
	}
	
	protected WSPRoutineD toWSPRoutineD(PRoutineD pRoutineD) throws Exception
	{
		WSPRoutineD pc = (WSPRoutineD)BeanUtil.shallowCopy(pRoutineD, WSPRoutineD.class, null);
	    if(pRoutineD.getPRoutine()!=null)
	    {
	    	pc.setRoutineId(pRoutineD.getPRoutine().getIdRoutine());
	    
	    }
	    if(pRoutineD.getPAttDraw()!=null)
	    {
	    	pc.setpAttDraw(pRoutineD.getPAttDraw().getName());
	    	pc.setpAttDrawId(pRoutineD.getPAttDraw().getIdAttDraw());
	    
	    }
	    if(pRoutineD.getPWorkCenter()!=null)
	    {
	    	pc.setWorkCenter(pRoutineD.getPWorkCenter().getWorkCenter());
	    	pc.setWorkCenterId(pRoutineD.getPWorkCenter().getIdWc());
	    }
	    if(pRoutineD.getUsers()!=null)
	    {
	    	pc.setOrderBy(pRoutineD.getUsers().getName());
	    	pc.setUserId(pRoutineD.getUsers().getIdUser());
	    }
	  
		return pc;
	}

}
