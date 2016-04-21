package com.jms.service.maintenance;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.Company;
import com.jms.domain.db.MMachineGroup;
import com.jms.domain.db.SGenderDic;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.m.MMachineGroupRepository;
import com.jms.repositories.s.SGenderDicRepository;


@Service
@Transactional
public class MMachineGroupService {

	private static final Logger logger = LogManager.getLogger(MMachineGroupService.class
			.getCanonicalName());
	@Autowired
	private MMachineGroupRepository sMachineGroupRepository;
	@Autowired
	private CompanyRepository companyRepository;
	


	public void loadMMachineGroupForSandVik() {
		
		Company company = companyRepository.findByCompanyName("SandVik");
		
		String[] machineGroups = new String[] { "pulleys", "roller line","facility"};
		for (String m : machineGroups) {
			MMachineGroup mg = new MMachineGroup();
			mg.setGroupName(m);
			sMachineGroupRepository.save(mg);
		}

	}

}
