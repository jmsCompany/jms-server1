package com.jms.service.maintenance;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.domain.Config;
import com.jms.domain.GroupTypeEnum;
import com.jms.domain.db.Apps;
import com.jms.domain.db.Company;
import com.jms.domain.db.Groups;
import com.jms.domain.db.MMachine;
import com.jms.domain.db.MStatusDic;
import com.jms.domain.db.PStatusDic;
import com.jms.domain.db.SMaterialTypeDic;
import com.jms.domain.db.SStatusDic;
import com.jms.domain.db.SysDic;
import com.jms.domain.db.SysDicD;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.store.WSSStatus;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.m.MMachineGroupRepository;
import com.jms.repositories.m.MMachineRepository;
import com.jms.repositories.m.MStatusDicRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.s.SMaterialTypeDicRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.system.SysDicDRepository;
import com.jms.repositories.system.SysDicRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class MMachineService {

	private static final Logger logger = LogManager.getLogger(MMachineService.class
			.getCanonicalName());
	@Autowired
	private MMachineRepository mMachineRepository;
	@Autowired
	private MMachineGroupRepository mMachineGroupRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private  MStatusDicRepository mStatusDicRepository;
	@Autowired private SecurityUtils securityUtils;

	
	
	@Transactional(readOnly=true)
	public List<WSSelectObj> getMachinesObj()
	{
		List<WSSelectObj> wss = new ArrayList<WSSelectObj>();
		for(MMachine m :mMachineRepository.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany()))
		//for(MMachine m :mMachineRepository.findAll())
		{
			WSSelectObj ws = new WSSelectObj();
			ws.setId(m.getIdMachine());
			ws.setName(m.getCode()+"_" +m.getSpec());
			wss.add(ws);
		}
		return wss;
	}
	
	
	@Transactional(readOnly=true)
	public List<WSSelectObj> getMachinesObjByLineId(Long lineId)
	{
		List<WSSelectObj> wss = new ArrayList<WSSelectObj>();
		//for(MMachine m :mMachineRepository.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany()))
		for(MMachine m :mMachineRepository.getByLineId(lineId))
		{
			WSSelectObj ws = new WSSelectObj();
			ws.setId(m.getIdMachine());
			ws.setName(m.getCode()+"_" +m.getSpec());
			wss.add(ws);
		}
		return wss;
	}
	
	
	public void loadMachinesForSandVik(InputStream inputStream)throws IOException {
		
		CsvReader reader = new CsvReader(inputStream,',', Charset.forName("UTF-8"));
		//reader.readHeaders();
	
		while(reader.readRecord())
		{
          MMachine m = new MMachine();
          m.setCode(reader.get(0));
          m.setSpec(reader.get(1));
          if(reader.get(2)!=null&&!reader.get(2).isEmpty())
          {
        	  m.setTotalKwa(Long.parseLong(reader.get(2)));
          }
          m.setMMachineGroup(mMachineGroupRepository.findByGroupName(reader.get(3)));
          m.setCompany(companyRepository.findByCompanyName("SandVik"));
	
          m.setMStatusDic(mStatusDicRepository.getBySourceAndName("m_machine", reader.get(4)));
          mMachineRepository.save(m);
		}
		
	}


}
