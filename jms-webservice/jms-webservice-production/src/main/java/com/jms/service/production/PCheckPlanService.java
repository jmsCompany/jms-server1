package com.jms.service.production;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.MMachineGroup;
import com.jms.domain.db.PCPp;
import com.jms.domain.db.PCheckPlan;
import com.jms.domain.db.PCheckTime;
import com.jms.domain.db.PRoutineD;
import com.jms.domain.db.PWip;
import com.jms.domain.db.PWo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.p.WSPCheckPlan;
import com.jms.domain.ws.p.WSPCheckTime;
import com.jms.domain.ws.p.WSPRoutineD;
import com.jms.domain.ws.p.WSPWip;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.m.MMachineRepository;
import com.jms.repositories.p.PCPpRepository;
import com.jms.repositories.p.PCheckPlanRepository;
import com.jms.repositories.p.PCheckTimeRepository;
import com.jms.repositories.p.PPUTimeRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWipRepository;
import com.jms.repositories.p.PWoRepository;
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
	private PWoRepository pWoRepository;
	@Autowired
	private SecurityUtils securityUtils;
		
		
	@Transactional(readOnly=false)
	public WSPCheckPlan saveWSPCheckPlan(WSPCheckPlan wsPCheckPlan) throws Exception {
		PCheckPlan pCheckPlan; 
		
		
		if(wsPCheckPlan.getIdCheck()!=null&&!wsPCheckPlan.getIdCheck().equals(0l))
		{
			pCheckPlan = pCheckPlanRepository.findOne(wsPCheckPlan.getIdCheck());
		}
		else
		{
			pCheckPlan = new PCheckPlan();
	
		}
		PCheckPlan dbPCheckPlan= toDBPCheckPlan(wsPCheckPlan,pCheckPlan);
		dbPCheckPlan = pCheckPlanRepository.save(dbPCheckPlan);
		PCPp cp = dbPCheckPlan.getPCPp();
		List<PCheckPlan> cpCheckPlans =pCheckPlanRepository.getMaxCheckPlanByCppId(cp.getIdCPp());
		cp.setActQty(cpCheckPlans.get(0).getFinQty());
		pCPpRepository.save(cp);
		
		PWo pwo = cp.getPWo();
		
		Long qtyFinished =0l;
		for(PCPp p: pwo.getPCPps())
		{
			List<PCheckPlan> pCheckPlans =pCheckPlanRepository.getMaxCheckPlanByCppId(p.getIdCPp());
			if(pCheckPlans!=null&&!pCheckPlans.isEmpty())
			{
				qtyFinished =qtyFinished +pCheckPlans.get(0).getFinQty();
			   
			}
				
		}
		if(qtyFinished>=pwo.getQty())
		{
			pwo.setPStatusDic(pStatusDicRepository.findOne(13l)); //结束工单

		}
		pwo.setActQty(qtyFinished);
		pWoRepository.save(pwo);
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
        dbPCheckPlan.setUsersByCreator(securityUtils.getCurrentDBUser());
        if(wsPCheckPlan.getFinQty()<wsPCheckPlan.getPlanQty())
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
	    
	    Date d = pCheckPlan.getPlanCheckTime();

	    if(d!=null)
	    {
	        DateFormat fmtDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    Date today = new Date();
		    
		    DateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd");
		    String s = fmtDate.format(today);
		    Date date = fmtDateTime.parse(s+" "+d.toString());  
		  //  logger.debug("new plan time: " + date);
		    pc.setPlanCheckTime(date);
	    	
	    }

	
		return pc;
	}
}
