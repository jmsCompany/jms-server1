package com.jms.service.store;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.Config;
import com.jms.domain.db.SCurrencyType;
import com.jms.domain.db.SMaterialTypeDic;
import com.jms.domain.db.SysDic;
import com.jms.domain.db.SysDicD;
import com.jms.domain.ws.WSSelectObj;
import com.jms.repositories.s.SCurrencyTypeRepository;
import com.jms.repositories.s.SMaterialTypeDicRepository;
import com.jms.repositories.system.SysDicDRepository;
import com.jms.repositories.system.SysDicRepository;

@Service
@Transactional
public class CurrencyTypeService {

	private static final Logger logger = LogManager.getLogger(CurrencyTypeService.class
			.getCanonicalName());
	@Autowired
	private SCurrencyTypeRepository sCurrencyTypeRepository;
	
	@Transactional(readOnly=true)
	public List<WSSelectObj> getSCurrencyTypes() {
		
		List<WSSelectObj> objs = new ArrayList<WSSelectObj>();
		for(SCurrencyType s: sCurrencyTypeRepository.findAll())
		{
			WSSelectObj w = new WSSelectObj(s.getIdCurrencyType(),s.getCurrency());
			objs.add(w);
		}
		
		return objs;
		
	}

	public void loadCurrencyTypies() {
		
		String[] currencyTypies = new String[] { "人民币CNY", "美元USD",
				"日元JPY" , "欧元EUR ",
				"韩元KRW", "港元HKD",
				"澳元AUD",
				"加元CAD"};
		
		for (String m : currencyTypies) {
			SCurrencyType mt = new SCurrencyType();
			mt.setCurrency(m);
	
			sCurrencyTypeRepository.save(mt);
		}

	}

}
