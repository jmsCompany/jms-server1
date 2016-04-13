package com.jms.service.production;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.FCostCenter;
import com.jms.domain.db.PWorkCenter;
import com.jms.domain.db.SStk;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.production.WSPWorkCenter;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.f.FCostCenterRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class WorkCenterService {

	private static final Logger logger = LogManager.getLogger(WorkCenterService.class
			.getCanonicalName());
	@Autowired
	private PWorkCenterRepository pWorkCenterRepository;
	
	
	@Autowired 
	private CompanyRepository companyRepository;
	

	@Autowired
	private SecurityUtils securityUtils;
	
	@Transactional(readOnly=true)
	public List<WSSelectObj> findWorkCenterSelectObjs()
	{
		List<WSSelectObj> wsWorkCenterList = new ArrayList<WSSelectObj>();
		for(PWorkCenter w: pWorkCenterRepository.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany()))
		{
			WSSelectObj o = new WSSelectObj(w.getIdWc(),w.getWorkCenter());
			wsWorkCenterList.add(o);
		}
		
		return wsWorkCenterList;
	}
	


	
	@Transactional(readOnly=false)
	public WSPWorkCenter saveWSPWorkCenter(WSPWorkCenter wsPWorkCenter) throws Exception {
	    PWorkCenter pWorkCenter;
		if(wsPWorkCenter.getIdWc()!=null&&!wsPWorkCenter.getIdWc().equals(0l))
		{
			pWorkCenter = pWorkCenterRepository.findOne(wsPWorkCenter.getIdWc());
		}
		else
		{
			pWorkCenter = new PWorkCenter();
		}
		PWorkCenter dbpWorkCenter= toDBPWorkCenter(wsPWorkCenter,pWorkCenter);
		dbpWorkCenter = pWorkCenterRepository.save(dbpWorkCenter);
		wsPWorkCenter.setIdWc(dbpWorkCenter.getIdWc());
		return wsPWorkCenter;		
		
	}

	@Transactional(readOnly=false)
	public Valid deletePWorkCenter(Long workCenterId)
	{
		Valid valid = new Valid();
		
		pWorkCenterRepository.delete(workCenterId);
		valid.setValid(true);
		
		return valid;
	}

	
	@Transactional(readOnly=true) 
	public WSPWorkCenter findWSPworkCenter(Long workCenterId) throws Exception
	{	
		PWorkCenter pWorkCenter = pWorkCenterRepository.findOne(workCenterId);
		return  toWSPWorkCenter(pWorkCenter);
		
	}

	
	private PWorkCenter toDBPWorkCenter(WSPWorkCenter wsPWorkCenter,PWorkCenter pWorkCenter) throws Exception
	{
	
		PWorkCenter dbPWorkCenter = (PWorkCenter)BeanUtil.shallowCopy(wsPWorkCenter, PWorkCenter.class, pWorkCenter);

		dbPWorkCenter.setCompany(securityUtils.getCurrentDBUser().getCompany());
		dbPWorkCenter.setUsers(securityUtils.getCurrentDBUser());
		dbPWorkCenter.setCreationTime(new Date());
		
		
		return dbPWorkCenter;
	}
	
	private WSPWorkCenter toWSPWorkCenter(PWorkCenter pWorkCenter) throws Exception
	{
		WSPWorkCenter pc = (WSPWorkCenter)BeanUtil.shallowCopy(pWorkCenter, WSPWorkCenter.class, null);
		if(pWorkCenter.getCompany()!=null)
		{
			pc.setCompanyId(pWorkCenter.getCompany().getIdCompany());
		}
		if(pWorkCenter.getUsers()!=null)
		{
			pc.setCreator(pWorkCenter.getUsers().getName());
		}
		return pc;
	}

}
