package com.jms.service.production;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.Config;
import com.jms.domain.db.EWorkCategoryDic;
import com.jms.domain.db.FCostCenter;
import com.jms.domain.db.PUTime;
import com.jms.domain.db.PWorkCenter;
import com.jms.domain.db.SStk;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.p.WSPWorkCenter;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.e.EWorkCategoryDicRepository;
import com.jms.repositories.f.FCostCenterRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class EWorkCategoryDicService {

	private static final Logger logger = LogManager.getLogger(WorkCenterService.class
			.getCanonicalName());
	@Autowired
	private EWorkCategoryDicRepository eWorkCategoryDicRepository;
	
	
	@Autowired 
	private CompanyRepository companyRepository;
	

	@Autowired
	private SecurityUtils securityUtils;
	
	@Transactional(readOnly=true)
	public List<WSSelectObj> findWorkCategoriesSelectObjs()
	{
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		for(EWorkCategoryDic w: eWorkCategoryDicRepository.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany()))
		{
			WSSelectObj o = new WSSelectObj(w.getIdWorkCategory(),w.getWorkCategory());
			ws.add(o);
		}
		
		return ws;
	}
	

	//导入
	public void loadEWorkCategoryDicForSandVik() {
		
		for(String work: Config.workCategories)
		{
			EWorkCategoryDic s = new EWorkCategoryDic();
			s.setCompany(companyRepository.findByCompanyName("SandVik"));
			s.setDes(work);
			s.setWorkCategory(work);
			eWorkCategoryDicRepository.save(s);
		}
	}
}
