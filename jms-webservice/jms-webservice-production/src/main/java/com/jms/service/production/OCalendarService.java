package com.jms.service.production;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.OCalendar;
import com.jms.domain.db.OCalendarType;
import com.jms.domain.db.PBom;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.p.WSOCalendar;
import com.jms.domain.ws.p.WSOCalendarTypes;
import com.jms.domain.ws.p.WSPBomItem;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.o.OCalendarRepository;
import com.jms.repositories.o.OCalendarTypeRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class OCalendarService {

	private static final Logger logger = LogManager.getLogger(PStatusDicService.class
			.getCanonicalName());
	@Autowired
	private OCalendarTypeRepository oCalendarTypeRepository;
	
	@Autowired
	private OCalendarRepository oCalendarRepository;
	
	@Autowired private SecurityUtils securityUtils;
	
	@Transactional(readOnly=false)
	public WSOCalendar saveWSOCalendar(WSOCalendar wsOCalendar) throws Exception {
		OCalendar oCalendar;
		if(wsOCalendar.getIdCalendar()!=null&&!wsOCalendar.getIdCalendar().equals(0l))
		{
			oCalendar = oCalendarRepository.findOne(wsOCalendar.getIdCalendar());
		}
		else
		{
			oCalendar = new OCalendar();
	
		}
		OCalendar dbOCalendar= toDBOCalendar(wsOCalendar,oCalendar);
		dbOCalendar = oCalendarRepository.save(dbOCalendar);
		return wsOCalendar;		
		
	}

	@Transactional(readOnly=false)
	public Valid deleteWSOCalendar(Long idCalendar)
	{
		Valid valid = new Valid();
		
		oCalendarRepository.delete(idCalendar);
		valid.setValid(true);
		
		return valid;
	}

	
	@Transactional(readOnly=true) 
	public WSOCalendar findWSOCalendar(Long idCalendar) throws Exception
	{	
		OCalendar oCalendar= oCalendarRepository.findOne(idCalendar);
		return  toWSOCalendar(oCalendar);
		
	}
	
	protected OCalendar toDBOCalendar(WSOCalendar wsOCalendar,OCalendar oCalendar) throws Exception
	{
	
		OCalendar dbCalendar = (OCalendar)BeanUtil.shallowCopy(wsOCalendar, OCalendar.class, oCalendar);

        if(wsOCalendar.getIdOCalendarType()!=null)
        	dbCalendar.setOCalendarType(oCalendarTypeRepository.findOne(wsOCalendar.getIdOCalendarType()));
		
        dbCalendar.setIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		return dbCalendar;
	}
	
	private WSOCalendar toWSOCalendar(OCalendar oCalendar) throws Exception
	{
		WSOCalendar pc = (WSOCalendar)BeanUtil.shallowCopy(oCalendar, WSOCalendar.class, null);
	  
        if(oCalendar.getOCalendarType()!=null)
        {
        	pc.setIdOCalendarType(oCalendar.getOCalendarType().getIdCalendarType());
        	pc.setType(oCalendar.getOCalendarType().getType());
        }
		return pc;
	}
}
