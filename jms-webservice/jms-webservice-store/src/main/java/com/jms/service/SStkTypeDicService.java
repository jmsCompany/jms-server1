package com.jms.service;


import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.Config;
import com.jms.domain.db.SStkTypeDic;
import com.jms.domain.ws.store.WSStkType;
import com.jms.repositories.s.SStkTypeDicRepository;


@Service
@Transactional
public class SStkTypeDicService {

	private static final Logger logger = LogManager.getLogger(SStkTypeDicService.class
			.getCanonicalName());
	@Autowired
	private SStkTypeDicRepository sStkTypeDicRepository;
	

	//导入仓库类型
	public void loadStkTypes() {
		
		for(String stkType: Config.stkTypes)
		{
			SStkTypeDic s = new SStkTypeDic();
			s.setName(stkType);
			sStkTypeDicRepository.save(s);
		}
	}
	
	@Transactional(readOnly=true)
	public List<WSStkType> getStkTypes()
	{
		List<WSStkType> ls = new ArrayList<WSStkType>();
		for(SStkTypeDic s :sStkTypeDicRepository.findAll())
		{
			WSStkType ws = new WSStkType();
			ws.setId(s.getIdStkType());
			ws.setName(s.getName());
			ls.add(ws);
		}
		return ls;
	}


}
