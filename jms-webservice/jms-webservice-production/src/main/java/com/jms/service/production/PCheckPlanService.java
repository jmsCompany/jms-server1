package com.jms.service.production;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.PCheckPlan;
import com.jms.domain.db.PCheckTime;
import com.jms.domain.db.PRoutineD;
import com.jms.domain.db.PWip;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.production.WSPCheckPlan;
import com.jms.domain.ws.production.WSPCheckTime;
import com.jms.domain.ws.production.WSPRoutineD;
import com.jms.domain.ws.production.WSPWip;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.m.MMachineRepository;
import com.jms.repositories.p.PCPpRepository;
import com.jms.repositories.p.PCheckPlanRepository;
import com.jms.repositories.p.PCheckTimeRepository;
import com.jms.repositories.p.PPUTimeRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWipRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class PCheckPlanService {

	private static final Logger logger = LogManager.getLogger(PCheckPlanService.class
			.getCanonicalName());
	@Autowired
	private PCheckPlanRepository pCheckPlanRepository;
	@Autowired
	private  PPUTimeRepository pPUTimeRepository;
	
	
	@Autowired 
	private CompanyRepository companyRepository;
	
	@Autowired 
	private PStatusDicRepository pStatusDicRepository;
	@Autowired 
	private MMachineRepository  mMachineRepository;
	
	@Autowired 
	private PCPpRepository pCPpRepository;
	

	@Autowired
	private SecurityUtils securityUtils;
		
		
	@Transactional(readOnly=false)
	public WSPCheckPlan saveWSPCheckPlan(WSPCheckPlan wsPCheckPlan) throws Exception {
		PCheckPlan pCheckPlan = new PCheckPlan();
		PCheckPlan dbPCheckPlan= toDBPCheckPlan(wsPCheckPlan,pCheckPlan);
		dbPCheckPlan = pCheckPlanRepository.save(dbPCheckPlan);
		return wsPCheckPlan;		
		
	}

	@Transactional(readOnly=true)
	public List<WSPCheckPlan> findWSPCheckPlans(Long cppId) throws Exception {
		
		List<WSPCheckPlan>  ws = new ArrayList<WSPCheckPlan>();
		for(PCheckPlan p: pCheckPlanRepository.getByUserIdAndCppId(securityUtils.getCurrentDBUser().getIdUser(), cppId))
		{
			ws.add(toWSPCheckPlan(p));
		}
		
		return ws;		
		
	}
	
	protected PCheckPlan toDBPCheckPlan(WSPCheckPlan wsPCheckPlan,PCheckPlan pCheckPlan) throws Exception
	{
	
		PCheckPlan dbPCheckPlan = (PCheckPlan)BeanUtil.shallowCopy(wsPCheckPlan, PCheckPlan.class, pCheckPlan);

        if(wsPCheckPlan.getPcppId()!=null)
        {
        	dbPCheckPlan.setPCPp(pCPpRepository.findOne(wsPCheckPlan.getPcppId()));
        }
        dbPCheckPlan.setCreationTime(new Date());
        dbPCheckPlan.setCheckTime(new Date());
        if(wsPCheckPlan.getFinQty()<wsPCheckPlan.getToBeQty())
        {
        	 dbPCheckPlan.setPStatusDic(pStatusDicRepository.findOne(23l)); //不满意
        }
        else
        {
        	 dbPCheckPlan.setPStatusDic(pStatusDicRepository.findOne(22l)); //满意
        }
       
		return dbPCheckPlan;
	}
	
	protected WSPCheckPlan toWSPCheckPlan(PCheckPlan pCheckPlan) throws Exception
	{
		WSPCheckPlan pc = (WSPCheckPlan)BeanUtil.shallowCopy(pCheckPlan, WSPCheckPlan.class, null);
	    if(pCheckPlan.getPStatusDic()!=null)
	    {
	    	pc.setStatus(pCheckPlan.getPStatusDic().getName());
	    
	    }
	    pc.setTotalQty(pCheckPlan.getPCPp().getQty());
	    pc.setPcppId(pCheckPlan.getPCPp().getIdCPp());
		return pc;
	}
}
