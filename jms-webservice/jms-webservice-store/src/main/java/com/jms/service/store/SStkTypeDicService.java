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
import com.jms.domain.db.Users;
import com.jms.domain.ws.s.WSStkType;
import com.jms.repositories.s.SStkTypeDicRepository;
import com.jms.web.security.SecurityUtils;


@Service
@Transactional
public class SStkTypeDicService {

	private static final Logger logger = LogManager.getLogger(SStkTypeDicService.class
			.getCanonicalName());
	@Autowired
	private SStkTypeDicRepository sStkTypeDicRepository;
	@Autowired private SecurityUtils securityUtils;
	//导入仓库类型
	public void loadStkTypes() {
		int i=0;
		for(String stkType: Config.stkTypes)
		{
			SStkTypeDic s = new SStkTypeDic();
			s.setName(stkType);
			s.setCode(Config.stkTypeCodes[i]);
			sStkTypeDicRepository.save(s);
			i++;
		}
	}
	
	
	@Transactional(readOnly=true)
	public List<WSStkType> getStkTypes()
	{
	    Users u =securityUtils.getCurrentDBUser();
	    boolean isEn = false;
	    if(u.getLang()!=null&&u.getLang().equals("en"))
	    {
	    	isEn =true;
	    }
		List<WSStkType> ls = new ArrayList<WSStkType>();
		for(SStkTypeDic s :sStkTypeDicRepository.findAll())
		{
			WSStkType ws = new WSStkType();
			ws.setId(s.getIdStkType());
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
