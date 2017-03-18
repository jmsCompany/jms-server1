package com.jms.service.store;


import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.Config;
import com.jms.domain.db.SStkTypeDic;
import com.jms.domain.db.STypeDic;
import com.jms.domain.db.Users;
import com.jms.domain.ws.s.WSSType;
import com.jms.domain.ws.s.WSStkType;
import com.jms.repositories.s.SStkTypeDicRepository;
import com.jms.repositories.s.STypeDicRepository;
import com.jms.web.security.SecurityUtils;


@Service
@Transactional
public class STypeDicService {

	private static final Logger logger = LogManager.getLogger(STypeDicService.class
			.getCanonicalName());
	@Autowired
	private STypeDicRepository sTypeDicRepository;
	@Autowired private SecurityUtils securityUtils;

	public void loadSTypes() {
		
		
		
		for(String sType: Config.sTypes)
		{
			STypeDic s = new STypeDic();
			s.setName(sType);
			sTypeDicRepository.save(s);
		}
	}
	
	@Transactional(readOnly=true)
	public List<WSSType> getSTypes()
	{
		
		 Users u =securityUtils.getCurrentDBUser();
		    boolean isEn = false;
		    if(u.getLang()!=null&&u.getLang().equals("en"))
		    {
		    	isEn =true;
		    }
		List<WSSType> ls = new ArrayList<WSSType>();
		for(STypeDic s :sTypeDicRepository.findAll())
		{
			WSSType ws = new WSSType();
			ws.setId(s.getId());
			if(isEn)
			{
				ws.setName(s.getNameEn());
			}else
			{
				ws.setName(s.getName());
			}
			
			ls.add(ws);
		}
		return ls;
	}


}
