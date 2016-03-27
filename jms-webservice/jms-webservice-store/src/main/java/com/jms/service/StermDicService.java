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
		
		String[] freightTerms = new String[] { "CFR", "CIF","CIP","CPT","DAF","DAP","DDP","DDU","DEQ","DES","EXW","FAS","FCA","FOB"};
		for (String m : freightTerms) {
			STermDic mt = new STermDic();
			mt.setName(m);
			mt.setSource("freight");
			sStermDicRepository.save(mt);
		}
		
		
		String[] paymentTerms = new String[] { "T/T in advance", "T/T  30 days","T/T  60 days","T/T  90 days","T/T  120 days","T/T  50 days","T/T  150 days","T/T  45 days","T/T  37 days"};
		for (String m : paymentTerms) {
			STermDic mt = new STermDic();
			mt.setName(m);
			mt.setSource("payment");
			sStermDicRepository.save(mt);
		}

	}

}
