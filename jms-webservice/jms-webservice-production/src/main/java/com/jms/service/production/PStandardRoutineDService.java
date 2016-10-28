
package com.jms.service.production;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.PDraw;
import com.jms.domain.db.PRoutine;
import com.jms.domain.db.PRoutineD;
import com.jms.domain.db.PStandardRoutined;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.p.WSPRoutine;
import com.jms.domain.ws.p.WSPRoutineD;
import com.jms.domain.ws.p.WSPStandardRoutined;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.p.PAttDrawRepository;
import com.jms.repositories.p.PDrawRepository;
import com.jms.repositories.p.PLineRepository;
import com.jms.repositories.p.PRoutineDRepository;
import com.jms.repositories.p.PRoutineRepository;
import com.jms.repositories.p.PStandardRoutinedRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class PStandardRoutineDService {

	private static final Logger logger = LogManager.getLogger(PStandardRoutineDService.class
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
	private PStandardRoutinedRepository pStandardRoutinedRepository;
	
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
	public WSPStandardRoutined saveWSPStandardRoutined(WSPStandardRoutined wsPStandardRoutined) throws Exception {

		PStandardRoutined dbPStandardRoutined;
		
		if(wsPStandardRoutined.getIdStandardRoutined()!=null&&!wsPStandardRoutined.getIdStandardRoutined().equals(0l))
		{
		    dbPStandardRoutined = pStandardRoutinedRepository.getOne(wsPStandardRoutined.getIdStandardRoutined());
		}
		else
		{
			dbPStandardRoutined = new PStandardRoutined();
		}

	     dbPStandardRoutined = toDBPStandardRoutined(wsPStandardRoutined,dbPStandardRoutined);
	     
	    // System.out.println("save standerd routineD: " + dbPStandardRoutined.getName());
		
	     dbPStandardRoutined = pStandardRoutinedRepository.save(dbPStandardRoutined);
		 
	     return wsPStandardRoutined;		
	}
	

	
	

	@Transactional(readOnly=false)
	public Valid deleteWSPStandardRoutined(Long idStandardRoutined)
	{
		Valid valid = new Valid();

		pStandardRoutinedRepository.delete(idStandardRoutined);
		valid.setValid(true);
		
		return valid;
	}
	
	
	@Transactional(readOnly=true) 
	public WSPStandardRoutined findWSPStandardRoutined(Long idStandardRoutined) throws Exception
	{	
		PStandardRoutined pStandardRoutined= pStandardRoutinedRepository.findOne(idStandardRoutined);
		return  toWSPStandardRoutined(pStandardRoutined);
		
	}
	
	protected PStandardRoutined toDBPStandardRoutined(WSPStandardRoutined wsPStandardRoutined,PStandardRoutined pStandardRoutined) throws Exception
	{
		PStandardRoutined dbPStandardRoutined = (PStandardRoutined)BeanUtil.shallowCopy(wsPStandardRoutined, PStandardRoutined.class, pStandardRoutined);
		dbPStandardRoutined.setIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		return dbPStandardRoutined;
	}
	
	protected WSPStandardRoutined toWSPStandardRoutined(PStandardRoutined pStandardRoutined) throws Exception
	{
		WSPStandardRoutined pc = (WSPStandardRoutined)BeanUtil.shallowCopy(pStandardRoutined, WSPStandardRoutined.class, null);
	   
		return pc;
	}

}
