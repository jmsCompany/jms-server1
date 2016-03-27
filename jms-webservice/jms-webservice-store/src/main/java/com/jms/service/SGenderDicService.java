package com.jms.service;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.Config;
import com.jms.domain.db.SGenderDic;
import com.jms.domain.db.SMaterialTypeDic;
import com.jms.domain.db.SYesOrNoDic;
import com.jms.domain.db.SysDic;
import com.jms.domain.db.SysDicD;
import com.jms.repositories.s.SGenderDicRepository;
import com.jms.repositories.s.SMaterialTypeDicRepository;
import com.jms.repositories.s.SYesOrNoDicRepository;
import com.jms.repositories.system.SysDicDRepository;
import com.jms.repositories.system.SysDicRepository;

@Service
@Transactional
public class SGenderDicService {

	private static final Logger logger = LogManager.getLogger(SGenderDicService.class
			.getCanonicalName());
	@Autowired
	private SGenderDicRepository sGenderDicRepository;
	


	public void loadGenders() {
		
		String[] genders = new String[] { "男", "女","未指定"};
		for (String m : genders) {
			SGenderDic mt = new SGenderDic();
			mt.setName(m);
			sGenderDicRepository.save(mt);
		}

	}

}
