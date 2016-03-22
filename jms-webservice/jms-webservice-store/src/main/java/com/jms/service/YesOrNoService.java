package com.jms.service;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.Config;
import com.jms.domain.db.SMaterialTypeDic;
import com.jms.domain.db.SYesOrNoDic;
import com.jms.domain.db.SysDic;
import com.jms.domain.db.SysDicD;
import com.jms.repositories.s.SMaterialTypeDicRepository;
import com.jms.repositories.s.SYesOrNoDicRepository;
import com.jms.repositories.system.SysDicDRepository;
import com.jms.repositories.system.SysDicRepository;

@Service
@Transactional
public class YesOrNoService {

	private static final Logger logger = LogManager.getLogger(YesOrNoService.class
			.getCanonicalName());
	@Autowired
	private SYesOrNoDicRepository sYesOrNoDicRepository;
	
	@Transactional(readOnly=true)
	public List<SYesOrNoDic> getSYesOrNoDics() {
		
		return sYesOrNoDicRepository.findAll();
		
	}

	public void loadSYesOrNoDics() {
		
		String[] yesAndNo = new String[] { "是", "否"};
		for (String m : yesAndNo) {
			SYesOrNoDic mt = new SYesOrNoDic();
			mt.setName(m);
			sYesOrNoDicRepository.save(mt);
		}

	}

}
