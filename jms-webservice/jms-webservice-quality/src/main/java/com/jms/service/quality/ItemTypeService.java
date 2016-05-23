package com.jms.service.quality;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.Config;
import com.jms.domain.db.QItemType;
import com.jms.domain.db.QTester;
import com.jms.repositories.q.QItemTypeRepository;
import com.jms.repositories.q.QTesterRepository;



@Service
@Transactional
public class ItemTypeService {
	private static final Logger logger = LogManager.getLogger(ItemTypeService.class
			.getCanonicalName());
	@Autowired
	private QItemTypeRepository qItemTypeRepository;
	
	public void loadItemTypes()
	{
		for(String s: Config.itemTypes)
		{
			QItemType q = new QItemType();
			q.setDes(s);
			qItemTypeRepository.save(q);
		}
	}
}
