package com.jms.service.maintenance;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.Config;
import com.jms.domain.db.MDept;
import com.jms.domain.db.MMainCycle;
import com.jms.domain.ws.WSSelectObj;
import com.jms.repositories.m.MDeptRepository;
import com.jms.repositories.m.MMainCycleRepository;


@Service
@Transactional
public class MDeptService {

	private static final Logger logger = LogManager.getLogger(MDeptService.class
			.getCanonicalName());
	@Autowired
	private MDeptRepository mDeptRepository;
	
	
	@Transactional(readOnly=true)
	public List<WSSelectObj> getDepts()
	{
		List<WSSelectObj> wss = new ArrayList<WSSelectObj>();
		for(MDept s :mDeptRepository.findAll())
		{
			WSSelectObj w = new WSSelectObj(s.getIdDept(),s.getDes());
			wss.add(w);
		}
		return wss;
	}
	
	
	public void loadDepts() {
		
		for(String s : Config.depts)
		{
			MDept m = new MDept();
			m.setDes(s);
			mDeptRepository.save(m);
		}
	
	}


}
