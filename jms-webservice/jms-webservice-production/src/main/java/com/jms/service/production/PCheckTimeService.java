package com.jms.service.production;


import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.PCheckTime;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.p.WSPCheckTime;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.m.MMachineRepository;
import com.jms.repositories.p.PCheckTimeRepository;
import com.jms.repositories.p.PPUTimeRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class PCheckTimeService {

	private static final Logger logger = LogManager.getLogger(PCheckTimeService.class
			.getCanonicalName());
	@Autowired
	private PCheckTimeRepository pCheckTimeRepository;
	@Autowired
	private  PPUTimeRepository pPUTimeRepository;
	
	
	@Autowired 
	private CompanyRepository companyRepository;
	
	@Autowired 
	private PStatusDicRepository pStatusDicRepository;
	@Autowired 
	private MMachineRepository  mMachineRepository;
	

	@Autowired
	private SecurityUtils securityUtils;
		
		
	@Transactional(readOnly=false)
	public Valid saveWSPCheckTime(WSPCheckTime wsPCheckTime) throws Exception {
		
		Valid v = new Valid();
		
		PCheckTime pCheckTime;
		Long machineId = wsPCheckTime.getmMachineId();
		
		 List<PCheckTime> pcs = pCheckTimeRepository.getByMachineId(machineId);
		 Long dbCId = 0l;
		 if(pcs!=null&&!pcs.isEmpty())
		 {
			 dbCId = pcs.get(0).getIdCheckTime();
		 }
		if(wsPCheckTime.getIdCheckTime()!=null&&!wsPCheckTime.getIdCheckTime().equals(0l))
		{
			pCheckTime = pCheckTimeRepository.findOne(wsPCheckTime.getIdCheckTime());
			if(!dbCId.equals(0l)&&!pCheckTime.getIdCheckTime().equals(dbCId))
			{
				v.setValid(false);
				return v;
			}
		}
		else
		{
			if(!dbCId.equals(0l))
			{
				v.setValid(false);
				return v;
			}
	
		}
		pCheckTime = new PCheckTime();
		PCheckTime dbPCheckTime= toDBPCheckTime(wsPCheckTime,pCheckTime);
		dbPCheckTime = pCheckTimeRepository.save(dbPCheckTime);

		v.setValid(true);
		return v;		
		
	}

	@Transactional(readOnly=false)
	public Valid deletePCheckTime(Long idCheckTime)
	{
		Valid valid = new Valid();
		pCheckTimeRepository.delete(idCheckTime);
		valid.setValid(true);
		
		return valid;
	}
	
	
	@Transactional(readOnly=true) 
	public WSPCheckTime findWSPCheckTime(Long idCheckTime) throws Exception
	{	
		PCheckTime pCheckTime= pCheckTimeRepository.findOne(idCheckTime);
		return  toWSPCheckTime(pCheckTime);
		
	}
	
	protected PCheckTime toDBPCheckTime(WSPCheckTime wsPCheckTime,PCheckTime pCheckTime) throws Exception
	{
	
		PCheckTime dbPCheckTime = (PCheckTime)BeanUtil.shallowCopy(wsPCheckTime, PCheckTime.class, pCheckTime);

        if(wsPCheckTime.getmMachineId()!=null)
        {
        	dbPCheckTime.setMMachine(mMachineRepository.findOne(wsPCheckTime.getmMachineId()));
        }
        if(wsPCheckTime.getpUTimeId()!=null)
        {
        	dbPCheckTime.setPUTime(pPUTimeRepository.findOne(wsPCheckTime.getpUTimeId()));
        }
		return dbPCheckTime;
	}
	
	
	
	protected WSPCheckTime toWSPCheckTime(PCheckTime pCheckTime) throws Exception
	{
		WSPCheckTime pc = (WSPCheckTime)BeanUtil.shallowCopy(pCheckTime, WSPCheckTime.class, null);
	    if(pCheckTime.getMMachine()!=null)
	    {
	    	pc.setmMachineDes(pCheckTime.getMMachine().getCode()+"_"+pCheckTime.getMMachine().getSpec());
	    	pc.setmMachineId(pCheckTime.getMMachine().getIdMachine());
	    
	    }
	    if(pCheckTime.getPUTime()!=null)
	    {
	    	pc.setpUTime(pCheckTime.getPUTime().getUTime());
	    	pc.setpUTimeId(pCheckTime.getPUTime().getIdUTime());
	    
	    }
		return pc;
	}
	
	
}
