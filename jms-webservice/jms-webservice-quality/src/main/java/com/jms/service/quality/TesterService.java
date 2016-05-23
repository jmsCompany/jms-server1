package com.jms.service.quality;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.Config;
import com.jms.domain.db.QTester;
import com.jms.repositories.q.QTesterRepository;



@Service
@Transactional
public class TesterService {
	private static final Logger logger = LogManager.getLogger(TesterService.class
			.getCanonicalName());
	@Autowired
	private QTesterRepository qTesterRepository;
	
	public void loadTesters()
	{
		for(String s: Config.testerTypes)
		{
			QTester q = new QTester();
			q.setDes(s);
			qTesterRepository.save(q);
		}
	}
}
