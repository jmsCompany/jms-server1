package com.jms.service.store;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.Config;
import com.jms.domain.db.SMaterialTypeDic;
import com.jms.domain.db.SysDic;
import com.jms.domain.db.SysDicD;
import com.jms.repositories.s.SMaterialTypeDicRepository;
import com.jms.repositories.system.SysDicDRepository;
import com.jms.repositories.system.SysDicRepository;

@Service
@Transactional
public class MaterialTypeService {

	private static final Logger logger = LogManager.getLogger(MaterialTypeService.class
			.getCanonicalName());
	@Autowired
	private SMaterialTypeDicRepository sMaterialTypeDicRepository;
	
	@Transactional(readOnly=true)
	public List<SMaterialTypeDic> getMaterialTypies() {
		
		return sMaterialTypeDicRepository.findAll();
		
	}

	public void loadMaterilaTypies() {
		
		String[] materialTypies = new String[] { "原材料", "成品",
				"半成品","消耗品","备件","量具"};
		for (String m : materialTypies) {
			SMaterialTypeDic mt = new SMaterialTypeDic();
			mt.setName(m);
			mt.setDes(m);
			sMaterialTypeDicRepository.save(mt);
		}

	}

}
