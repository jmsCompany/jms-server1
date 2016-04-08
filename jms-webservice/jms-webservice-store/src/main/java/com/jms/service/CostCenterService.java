package com.jms.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.FCostCenter;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.f.WSFCostCenter;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.f.FCostCenterRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class CostCenterService {

	private static final Logger logger = LogManager.getLogger(CostCenterService.class
			.getCanonicalName());
	@Autowired
	private FCostCenterRepository fCostCenterRepository;
	
	
	@Autowired 
	private CompanyRepository companyRepository;
	

	@Autowired
	private SecurityUtils securityUtils;
	
	
	@Transactional(readOnly=true)
	public List<WSFCostCenter> getCostCenterList() throws Exception {
		Users u = securityUtils.getCurrentDBUser();
		List<FCostCenter> costCenters = fCostCenterRepository.getByCompanyId(u.getCompany().getIdCompany());
		List<WSFCostCenter> wsCostCenters = new ArrayList<WSFCostCenter>();
		for(FCostCenter s:costCenters)
		{
			wsCostCenters.add(toWSCostCenter(s));
		}
		
		return wsCostCenters;
		
	}

	
	@Transactional(readOnly=false)
	public WSFCostCenter saveWSFCostCenter(WSFCostCenter wsFCostCenter) throws Exception {
		FCostCenter fCostCenter;
		if(wsFCostCenter.getIdCostCenter()!=null&&!wsFCostCenter.getIdCostCenter().equals(0l))
		{
			fCostCenter = fCostCenterRepository.findOne(wsFCostCenter.getIdCostCenter());
		}
		else
		{
			fCostCenter = new FCostCenter();
		}
		FCostCenter dbFCostCenter = toDBFCostCenter(wsFCostCenter,fCostCenter);
		fCostCenterRepository.save(dbFCostCenter);
		wsFCostCenter.setIdCostCenter(dbFCostCenter.getIdCostCenter());
		return wsFCostCenter;		
		
	}

	@Transactional(readOnly=false)
	public Valid deleteFCostCenter(Long fCostCenterId)
	{
		Valid valid = new Valid();
		
		fCostCenterRepository.delete(fCostCenterId);
		valid.setValid(true);
		
		return valid;
	}

	
	@Transactional(readOnly=true) 
	public WSFCostCenter findFCostCenter(Long fCostCenterId) throws Exception
	{	
		FCostCenter fCostCenter = fCostCenterRepository.findOne(fCostCenterId);
		return  toWSCostCenter(fCostCenter);
		
	}

	
	private FCostCenter toDBFCostCenter(WSFCostCenter wsFCostCenter,FCostCenter fCostCenter) throws Exception
	{
	
		FCostCenter dbFCostCenter = (FCostCenter)BeanUtil.shallowCopy(wsFCostCenter, FCostCenter.class, fCostCenter);

		dbFCostCenter.setCompany(securityUtils.getCurrentDBUser().getCompany());
		
		
		return dbFCostCenter;
	}
	
	private WSFCostCenter toWSCostCenter(FCostCenter fCostCenter) throws Exception
	{
		WSFCostCenter fc = (WSFCostCenter)BeanUtil.shallowCopy(fCostCenter, WSFCostCenter.class, null);
		if(fCostCenter.getCompany()!=null)
		{
			fc.setCompanyId(fCostCenter.getCompany().getIdCompany());
			fc.setCompanyName(fCostCenter.getCompany().getCompanyName());
		}
		
		
		
		return fc;
	}

}
