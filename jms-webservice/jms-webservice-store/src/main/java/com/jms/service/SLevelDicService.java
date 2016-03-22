package com.jms.service;


import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.Config;
import com.jms.domain.db.SLevelDic;
import com.jms.domain.ws.store.WSStkType;
import com.jms.repositories.s.SLevelDicRepository;


@Service
@Transactional
public class SLevelDicService {

	private static final Logger logger = LogManager.getLogger(SLevelDicService.class
			.getCanonicalName());
	@Autowired
	private SLevelDicRepository sLevelDicRepository;
	

	//导入仓库类型
	public void loadLevels() {
		
		for(String lvl: Config.sLevels)
		{
			SLevelDic s = new SLevelDic();
			s.setName(lvl);
			sLevelDicRepository.save(s);
		}
	}
	
	@Transactional(readOnly=true)
	public List<WSStkType> getLevels()
	{
		List<WSStkType> ls = new ArrayList<WSStkType>();
		for(SLevelDic s :sLevelDicRepository.findAll())
		{
			WSStkType ws = new WSStkType();
			ws.setId(s.getId());
			ws.setName(s.getName());
			ls.add(ws);
		}
		return ls;
	}


}
