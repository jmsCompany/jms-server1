package com.jms.service.maintenance;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.Company;
import com.jms.domain.db.MMachineGroup;
import com.jms.domain.db.PBom;
import com.jms.domain.db.PCheckPlan;
import com.jms.domain.db.SGenderDic;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.m.WSMachineGroup;
import com.jms.domain.ws.p.WSPCheckPlan;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.m.MMachineGroupRepository;
import com.jms.repositories.s.SGenderDicRepository;
import com.jms.web.security.SecurityUtils;


@Service
@Transactional
public class MMachineGroupService {

	private static final Logger logger = LogManager.getLogger(MMachineGroupService.class
			.getCanonicalName());
	@Autowired
	private MMachineGroupRepository sMachineGroupRepository;
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired private SecurityUtils securityUtils;

	public void loadMMachineGroupForSandVik() {
		
		Company company = companyRepository.findByCompanyName("SandVik");
		
		String[] machineGroups = new String[] { "pulleys", "roller line","facility"};
		for (String m : machineGroups) {
			MMachineGroup mg = new MMachineGroup();
			mg.setGroupName(m);
			sMachineGroupRepository.save(mg);
		}

	}
	public WSMachineGroup saveMachineGroup(WSMachineGroup wsMachineGroup) throws Exception
	{
		MMachineGroup mMachineGroup;
		if(wsMachineGroup.getIdGroup()!=null&&!wsMachineGroup.getIdGroup().equals(0l))
		{
			mMachineGroup = sMachineGroupRepository.findOne(wsMachineGroup.getIdGroup());
		}
		else
		{
			mMachineGroup = new MMachineGroup();
	
		}
		MMachineGroup dbMMachineGroup= toDBMMachineGroup(wsMachineGroup,mMachineGroup);
		dbMMachineGroup.setIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		dbMMachineGroup = sMachineGroupRepository.save(dbMMachineGroup);
		wsMachineGroup.setIdGroup(dbMMachineGroup.getIdGroup());
		return wsMachineGroup;
		
	}
	
	@Transactional(readOnly=true)
	public WSMachineGroup getWSMachineGroup(Long idMachineGroup) throws Exception
	{
		MMachineGroup mMachineGroup = sMachineGroupRepository.findOne(idMachineGroup);
		
		return toWSMachineGroup(mMachineGroup);
		
	}
	
	@Transactional(readOnly=false)
	public Valid deleteWSMachineGroup(Long idMachineGroup)
	{
		Valid valid = new Valid();
		MMachineGroup mMachineGroup = sMachineGroupRepository.findOne(idMachineGroup);
		if(!mMachineGroup.getMMachines().isEmpty())
		{
			valid.setValid(false);
			valid.setMsg("该机组有设备，故不能删除！");
			
			return valid;
		}
		else
		{
			sMachineGroupRepository.delete(idMachineGroup);
			valid.setValid(true);
			return valid;
		}
	
	}

	
	protected MMachineGroup toDBMMachineGroup(WSMachineGroup wsMachineGroup,MMachineGroup mMachineGroup) throws Exception
	{
		MMachineGroup dbMMachineGroup = (MMachineGroup)BeanUtil.shallowCopy(wsMachineGroup, MMachineGroup.class, mMachineGroup);
	    return dbMMachineGroup;
	}

	protected WSMachineGroup toWSMachineGroup(MMachineGroup mMachineGroup) throws Exception
	{
		WSMachineGroup pc = (WSMachineGroup)BeanUtil.shallowCopy(mMachineGroup, WSMachineGroup.class, null);
	    return pc;
	}

}
