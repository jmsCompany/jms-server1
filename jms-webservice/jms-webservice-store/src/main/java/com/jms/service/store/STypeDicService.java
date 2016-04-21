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
import com.jms.domain.ws.store.WSSType;
import com.jms.domain.ws.store.WSStkType;
import com.jms.repositories.s.SStkTypeDicRepository;
import com.jms.repositories.s.STypeDicRepository;


@Service
@Transactional
public class STypeDicService {

	private static final Logger logger = LogManager.getLogger(STypeDicService.class
			.getCanonicalName());
	@Autowired
	private STypeDicRepository sTypeDicRepository;
	

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
		List<WSSType> ls = new ArrayList<WSSType>();
		for(STypeDic s :sTypeDicRepository.findAll())
		{
			WSSType ws = new WSSType();
			ws.setId(s.getId());
			ws.setName(s.getName());
			ls.add(ws);
		}
		return ls;
	}


}
