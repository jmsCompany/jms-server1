package com.jms.service;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.Config;
import com.jms.domain.db.SMaterialTypeDic;
import com.jms.domain.db.STermDic;
import com.jms.domain.db.SYesOrNoDic;
import com.jms.domain.db.SysDic;
import com.jms.domain.db.SysDicD;
import com.jms.repositories.s.SMaterialTypeDicRepository;
import com.jms.repositories.s.SStermDicRepository;
import com.jms.repositories.s.SYesOrNoDicRepository;
import com.jms.repositories.system.SysDicDRepository;
import com.jms.repositories.system.SysDicRepository;

@Service
@Transactional
public class StermDicService {

	private static final Logger logger = LogManager.getLogger(StermDicService.class
			.getCanonicalName());
	@Autowired
	private SStermDicRepository sStermDicRepository;
	
	@Transactional(readOnly=true)
	public List<STermDic> getStermDics() {
		
		return sStermDicRepository.findAll();
		
	}

	public void loadStermDics() {
		
		String[] terms = new String[] { "是", "否"};
		for (String m : terms) {
			STermDic mt = new STermDic();
			mt.setName(m);
			sStermDicRepository.save(mt);
		}

	}

}
