package com.jms.service;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.Config;
import com.jms.domain.db.SMaterialTypeDic;
import com.jms.domain.db.SUnitDic;
import com.jms.domain.db.SysDic;
import com.jms.domain.db.SysDicD;
import com.jms.repositories.s.SMaterialTypeDicRepository;
import com.jms.repositories.s.SUnitDicRepository;
import com.jms.repositories.system.SysDicDRepository;
import com.jms.repositories.system.SysDicRepository;

@Service
@Transactional
public class SUnitDicService {

	private static final Logger logger = LogManager.getLogger(SUnitDicService.class
			.getCanonicalName());
	@Autowired
	private SUnitDicRepository sUnitDicRepository;
	


	public void loadUnits() {
		
		String[] units = new String[] { "个", "千只",
				"只" , "箱"};
		for (String m : units) {
			SUnitDic mt = new SUnitDic();
			mt.setName(m);
			mt.setDes(m);
			sUnitDicRepository.save(mt);
		}

	}

}
