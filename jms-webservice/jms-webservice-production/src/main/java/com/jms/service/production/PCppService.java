package com.jms.service.production;


import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.PCPp;
import com.jms.domain.db.PCheckTime;
import com.jms.domain.db.PDraw;
import com.jms.domain.db.PRoutineD;
import com.jms.domain.db.PWip;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.production.WSPCheckTime;
import com.jms.domain.ws.production.WSPCpp;
import com.jms.domain.ws.production.WSPRoutineD;
import com.jms.domain.ws.production.WSPWip;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.m.MMachineRepository;
import com.jms.repositories.p.PCPpRepository;
import com.jms.repositories.p.PCheckTimeRepository;
import com.jms.repositories.p.PPUTimeRepository;
import com.jms.repositories.p.PRoutineDRepository;
import com.jms.repositories.p.PShiftPlanDRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWipRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class PCppService {

	private static final Logger logger = LogManager.getLogger(PCheckTimeService.class
			.getCanonicalName());
	@Autowired
	private PCPpRepository pCPpRepository;
	@Autowired
	private  PPUTimeRepository pPUTimeRepository;
	
	
	@Autowired 
	private CompanyRepository companyRepository;
	
	@Autowired 
	private PStatusDicRepository pStatusDicRepository;
	@Autowired 
	private MMachineRepository  mMachineRepository;
	
	@Autowired 
	private PWoRepository pWoRepository;
	@Autowired
	private SecurityUtils securityUtils;
	
	@Autowired
	private PRoutineDRepository pRoutineDRepository;
	@Autowired
	private PShiftPlanDRepository pShiftPlanDRepository;
	@Autowired
	private  UsersRepository usersRepository;
		
		
	@Transactional(readOnly=false)
	public WSPCpp saveWSPCPp(WSPCpp wsPCpp) throws Exception {
		PCPp pCPp;
		if(wsPCpp.getIdCpp()!=null&&!wsPCpp.getIdCpp().equals(0l))
		{
			pCPp = pCPpRepository.findOne(wsPCpp.getIdCpp());
		}
		else
		{
			pCPp = new PCPp();
	
		}
		PCPp dbPCPp= toDBPCPp(wsPCpp,pCPp);
		dbPCPp = pCPpRepository.save(dbPCPp);
		wsPCpp.setIdCpp(dbPCPp.getIdCPp());
		return wsPCpp;		
		
	}

	@Transactional(readOnly=false)
	public Valid deletePCpp(Long idPcpp)
	{
		Valid valid = new Valid();
		pCPpRepository.delete(idPcpp);
		valid.setValid(true);
		
		return valid;
	}
	
	
	@Transactional(readOnly=true) 
	public WSPCpp findWSCPp(Long idPcpp) throws Exception
	{	
		PCPp dbPCPp = pCPpRepository.findOne(idPcpp);
		return  toWSPCpp(dbPCPp);
		
	}
	
	protected PCPp toDBPCPp(WSPCpp wsPCpp,PCPp pCPp) throws Exception
	{
	
		PCPp dbPCPp = (PCPp)BeanUtil.shallowCopy(wsPCpp, PCPp.class, pCPp);
		dbPCPp.setIdCPp(wsPCpp.getIdCpp());
		dbPCPp.setCPpCode(wsPCpp.getCppCode());

        if(wsPCpp.getmMachineId()!=null)
        {
        	dbPCPp.setMMachine(mMachineRepository.findOne(wsPCpp.getmMachineId()));
        }
        if(wsPCpp.getpRoutineDId()!=null)
        {
        	dbPCPp.setPRoutineD(pRoutineDRepository.findOne(wsPCpp.getpRoutineDId()));
        }
        if(wsPCpp.getpShiftPlanDId()!=null)
        {
        	dbPCPp.setPShiftPlanD(pShiftPlanDRepository.findOne(wsPCpp.getpShiftPlanDId()));
        }
        if(wsPCpp.getCompanyId()!=null)
        {
        	dbPCPp.setCompany(companyRepository.findOne(wsPCpp.getCompanyId()));
        }
        else
        {
        	dbPCPp.setCompany(securityUtils.getCurrentDBUser().getCompany());
        }
        if(wsPCpp.getOpId()!=null)
        {
        	dbPCPp.setUsers(usersRepository.findOne(wsPCpp.getOpId()));
        }
        if(wsPCpp.getPwoId()!=null)
        {
        	dbPCPp.setPWo(pWoRepository.findOne(wsPCpp.getPwoId()));
        }
		return dbPCPp;
	}
	
	protected WSPCpp toWSPCpp(PCPp pCPp) throws Exception
	{
		WSPCpp pc = (WSPCpp)BeanUtil.shallowCopy(pCPp, WSPCpp.class, null);
	    if(pCPp.getMMachine()!=null)
	    {
	    	pc.setmMachine(pCPp.getMMachine().getCode()+"_"+pCPp.getMMachine().getSpec());
	    	pc.setmMachineId(pCPp.getMMachine().getIdMachine());
	    
	    }
	    if(pCPp.getPRoutineD()!=null)
	    {
	    	pc.setpRoutineD(pCPp.getPRoutineD().getRouteNo());
	    	pc.setpRoutineDId(pCPp.getPRoutineD().getIdRoutineD());
	    	PDraw draw = pCPp.getPRoutineD().getPRoutine().getPDraw();
	    	if(draw!=null)
	    	{
	    		pc.setDrawNo(draw.getDrawNo());
	    		pc.setDrawVer(draw.getDrawVer());
	    	}
	    
	    }
	    if(pCPp.getPShiftPlanD()!=null)
	    {
	    	pc.setpShiftPlanD(pCPp.getPShiftPlanD().getShift());
	    	pc.setpShiftPlanDId(pCPp.getPShiftPlanD().getIdShiftD());
	    }
	    if(pCPp.getPWo()!=null)
	    {
	    	pc.setPwo(pCPp.getPWo().getWoNo());
	    	pc.setPwoId(pCPp.getPWo().getIdWo());
	    }
	    if(pCPp.getUsers()!=null)
	    {
	    	pc.setOp(pCPp.getUsers().getName());
	    	pc.setOpId(pCPp.getUsers().getIdUser());
	    }
	    pc.setCppCode(pCPp.getCPpCode());
		return pc;
	}
}
