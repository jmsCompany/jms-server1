package com.jms.service.maintenance;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.Config;
import com.jms.domain.db.MMainCycle;
import com.jms.domain.ws.WSSelectObj;
import com.jms.repositories.m.MMainCycleRepository;


@Service
@Transactional
public class MMainCycleService {

	private static final Logger logger = LogManager.getLogger(MMainCycleService.class
			.getCanonicalName());
	@Autowired
	private MMainCycleRepository mMainCycleRepository;
	
	
	@Transactional(readOnly=true)
	public List<WSSelectObj> getMainCycles()
	{
		List<WSSelectObj> wss = new ArrayList<WSSelectObj>();
		for(MMainCycle s :mMainCycleRepository.findAll())
		{
			WSSelectObj w = new WSSelectObj(s.getIdMainCycle(),s.getMainCycle());
			wss.add(w);
		}
		return wss;
	}
	
	
	public void loadMainCycles() {
		
		for(String s : Config.mainCycles)
		{
			MMainCycle m = new MMainCycle();
			m.setMainCycle(s);
			mMainCycleRepository.save(m);
		}
	
	}


}
