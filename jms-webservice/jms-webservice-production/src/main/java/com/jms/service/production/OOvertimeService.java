package com.jms.service.production;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.OCalendar;
import com.jms.domain.db.OCalendarType;
import com.jms.domain.db.OOvertime;
import com.jms.domain.db.PBom;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.p.WSOCalendar;
import com.jms.domain.ws.p.WSOCalendarTypes;
import com.jms.domain.ws.p.WSOOvertime;
import com.jms.domain.ws.p.WSPBomItem;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.o.OCalendarRepository;
import com.jms.repositories.o.OCalendarTypeRepository;
import com.jms.repositories.o.OOvertimeRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class OOvertimeService {

	private static final Logger logger = LogManager.getLogger(PStatusDicService.class
			.getCanonicalName());

	
	@Autowired
	private OOvertimeRepository oOvertimeRepository;
	
	
	@Autowired private SecurityUtils securityUtils;
	
	@Transactional(readOnly=false)
	public WSOOvertime saveWSOOvertime(WSOOvertime wsOOvertime) throws Exception {
		OOvertime oOvertime;
		if(wsOOvertime.getIdOvertime()!=null&&!wsOOvertime.getIdOvertime().equals(0l))
		{
			oOvertime = oOvertimeRepository.findOne(wsOOvertime.getIdOvertime());
		}
		else
		{
			oOvertime = new OOvertime();
	
		}
		OOvertime dbOOvertime= toDBOOvertime(wsOOvertime,oOvertime);
		dbOOvertime = oOvertimeRepository.save(dbOOvertime);
		return wsOOvertime;		
		
	}

	@Transactional(readOnly=false)
	public Valid deleteWSOOvertime(Long idOvertime)
	{
		Valid valid = new Valid();
		
		oOvertimeRepository.delete(idOvertime);
		valid.setValid(true);
		
		return valid;
	}

	
	@Transactional(readOnly=true) 
	public WSOOvertime findWSOOvertime(Long idOvertime) throws Exception
	{	
		OOvertime oOvertime= oOvertimeRepository.findOne(idOvertime);
		return  toWSOOvertime(oOvertime);
		
	}
	
	
	protected OOvertime toDBOOvertime(WSOOvertime wsOOvertime,OOvertime oOvertime) throws Exception
	{
		
		OOvertime dbOOvertime = (OOvertime)BeanUtil.shallowCopy(wsOOvertime, OOvertime.class, oOvertime);

		dbOOvertime.setIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		
		dbOOvertime.setCreationTime(new Date());
		
		return dbOOvertime;
	}

	
	private WSOOvertime toWSOOvertime(OOvertime oOvertime) throws Exception
	{
		WSOOvertime pc = (WSOOvertime)BeanUtil.shallowCopy(oOvertime, WSOOvertime.class, null);
		return pc;
	}
	
}
