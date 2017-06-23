package com.jms.service.maintenance;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.Config;
import com.jms.domain.db.MDept;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.m.WSMDept;
import com.jms.repositories.m.MDeptRepository;
import com.jms.web.security.SecurityUtils;


@Service
@Transactional
public class MDeptService {

	private static final Logger logger = LogManager.getLogger(MDeptService.class
			.getCanonicalName());
	@Autowired
	private MDeptRepository mDeptRepository;
	@Autowired
	private SecurityUtils securityUtils;
	
	
	@Transactional(readOnly=true)
	public List<WSSelectObj> getDepts()
	{
		List<WSSelectObj> wss = new ArrayList<WSSelectObj>();
		for(MDept s :mDeptRepository.findByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany()))
		{
			WSSelectObj w = new WSSelectObj(s.getIdDept(),s.getDes());
			wss.add(w);
		}
		return wss;
	}
	
	
	public void loadDepts() {
		
		for(String s : Config.depts)
		{
			MDept m = new MDept();
			m.setDes(s);
			mDeptRepository.save(m);
		}
	
	}
	
	
	public WSMDept saveWSMDept(WSMDept wsMDept) throws Exception
	{
		MDept mDept;
		if(wsMDept.getId()!=null&&!wsMDept.getId().equals(0l))
		{
			mDept = mDeptRepository.findOne(wsMDept.getId());
		}
		else
		{
			mDept = new MDept();
	
		}
		
		mDept.setCompany(securityUtils.getCurrentDBUser().getCompany());
		mDept.setDes(wsMDept.getDes());
		mDeptRepository.save(mDept);
		return wsMDept;
		
	}
	
	

	@Transactional(readOnly=false)
	public Valid deleteDept(Long idDept)
	{
		Valid valid = new Valid();
		mDeptRepository.delete(idDept);
		valid.setValid(true);
		return valid;
	}


}
