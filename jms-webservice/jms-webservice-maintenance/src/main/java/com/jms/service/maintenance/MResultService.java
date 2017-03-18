package com.jms.service.maintenance;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.Config;
import com.jms.domain.db.MResult;
import com.jms.domain.db.Users;
import com.jms.domain.ws.WSSelectObj;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.m.MDeptRepository;
import com.jms.repositories.m.MMainCycleRepository;
import com.jms.repositories.m.MMainItemRepository;
import com.jms.repositories.m.MResultRepository;
import com.jms.repositories.p.PLineRepository;
import com.jms.repositories.p.PWipRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.repositories.s.SBinRepository;
import com.jms.repositories.s.SInventoryRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class MResultService {

	private static final Logger logger = LogManager.getLogger(MResultService.class);


	@Autowired
	private MResultRepository mResultRepository;
	@Autowired
	private MMainItemRepository mMainItemRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private  MMainCycleRepository mMainCycleRepository;
	@Autowired
	private  PLineRepository pLineRepository;
	@Autowired
	private PWipRepository pWipRepository;
	@Autowired
	private PWorkCenterRepository pWorkCenterRepository;
	@Autowired
	private SBinRepository sBinRepository;
	@Autowired private SecurityUtils securityUtils;
	@Autowired private SMaterialRepository sMaterialRepository;

	@Autowired private SInventoryRepository sInventoryRepository;
	
	@Autowired private  MDeptRepository mDeptRepository;


	@Transactional(readOnly=true)
	public List<WSSelectObj> getMResults()
	{
		List<WSSelectObj> wss = new ArrayList<WSSelectObj>();
		    Users u =securityUtils.getCurrentDBUser();
		    boolean isEn = false;
		    if(u.getLang()!=null&&u.getLang().equals("en"))
		    {
		    	isEn =true;
		    }
	    	for(MResult s :mResultRepository.findAll())
		   {
			if(isEn)
			{	
				WSSelectObj w = new WSSelectObj(s.getIdResult(),s.getDesEn());
			    wss.add(w);
				
			}
			else
			{
				WSSelectObj w = new WSSelectObj(s.getIdResult(),s.getDes());
				wss.add(w);
			}
		
		}
		return wss;
	}
	
	
	public void loadMResults() {
		
		for(String s : Config.mresults)
		{
			MResult m = new MResult();
			m.setDes(s);
			mResultRepository.save(m);
		}
	
	}

	
	

}
