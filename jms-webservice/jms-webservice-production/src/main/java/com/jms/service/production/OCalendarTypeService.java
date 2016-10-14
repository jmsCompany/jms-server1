package com.jms.service.production;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.OCalendarType;
import com.jms.domain.ws.p.WSOCalendarTypes;
import com.jms.repositories.o.OCalendarTypeRepository;

@Service
@Transactional
public class OCalendarTypeService {

	private static final Logger logger = LogManager.getLogger(PStatusDicService.class
			.getCanonicalName());
	@Autowired
	private OCalendarTypeRepository oCalendarTypeRepository;
	
	
	@Transactional(readOnly=true)
	public List<WSOCalendarTypes> getOCalendarTypes()
	{
		List<WSOCalendarTypes> wss = new ArrayList<WSOCalendarTypes>();
		for(OCalendarType s :oCalendarTypeRepository.findAll())
		{
			WSOCalendarTypes ws = new WSOCalendarTypes();
			ws.setIdCalendarType(s.getIdCalendarType());
			ws.setSalary(s.getSalary());
			ws.setType(s.getType());
			wss.add(ws);
		}
		return wss;
	}
	
	
	public void loadOCalendarTypes() {
		
		    OCalendarType sb = new OCalendarType();
		    sb.setIdCalendarType(1l);
		    sb.setSalary(1f);
		    sb.setType("上班");
		    oCalendarTypeRepository.save(sb);
	
		    OCalendarType zm = new OCalendarType();
		    zm.setIdCalendarType(2l);
		    zm.setSalary(2f);
		    zm.setType("周末");
		    oCalendarTypeRepository.save(zm);
		    
		    OCalendarType jr = new OCalendarType();
		    jr.setIdCalendarType(3l);
		    jr.setSalary(3f);
		    jr.setType("假日");
		    oCalendarTypeRepository.save(jr);
		
	}


}
