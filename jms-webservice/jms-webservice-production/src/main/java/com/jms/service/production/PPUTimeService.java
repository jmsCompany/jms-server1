package com.jms.service.production;


import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.Config;
import com.jms.domain.db.PUTime;
import com.jms.domain.db.PWip;
import com.jms.domain.db.SMtfTypeDic;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.p.WSPWip;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.p.PCheckTimeRepository;
import com.jms.repositories.p.PPUTimeRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWipRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class PPUTimeService {

	private static final Logger logger = LogManager.getLogger(PPUTimeService.class
			.getCanonicalName());
	@Autowired
	private PPUTimeRepository pPUTimeRepository;
	

	@Autowired
	private SecurityUtils securityUtils;
	
	@Transactional(readOnly=true)
	public List<WSSelectObj> findPutimess()
	{
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		for(PUTime w: pPUTimeRepository.findAll())
		{
			WSSelectObj o = new WSSelectObj(w.getIdUTime(),w.getUTime());
			ws.add(o);
		}
		
		return ws;
	}
	

	
	//导入
	public void loadPuTimes() {
		
		long i=1;
		for(String puTime: Config.puTimes)
		{
			PUTime s = new PUTime();
			s.setIdUTime(i);
			s.setUTime(puTime);
			pPUTimeRepository.save(s);
			i++;
		}
	}
	

}
