package com.jms.service.production;

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
import com.jms.domain.db.PStatusDic;
import com.jms.domain.db.SMaterialTypeDic;
import com.jms.domain.db.SStatusDic;
import com.jms.domain.db.SysDic;
import com.jms.domain.db.SysDicD;
import com.jms.domain.ws.store.WSSStatus;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.s.SMaterialTypeDicRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.system.SysDicDRepository;
import com.jms.repositories.system.SysDicRepository;

@Service
@Transactional
public class PStatusDicService {

	private static final Logger logger = LogManager.getLogger(PStatusDicService.class
			.getCanonicalName());
	@Autowired
	private PStatusDicRepository pStatusDicRepository;
	
	
	@Transactional(readOnly=true)
	public List<WSSStatus> getSStatus(String source)
	{
		List<WSSStatus> wss = new ArrayList<WSSStatus>();
		for(PStatusDic s :pStatusDicRepository.getBySource(source))
		{
			WSSStatus ws = new WSSStatus();
			ws.setId(s.getIdPstatus());
			ws.setName(s.getName());
			wss.add(ws);
		}
		return wss;
	}
	
	
	public void loadStatus(InputStream inputStream)throws IOException {
		
		CsvReader reader = new CsvReader(inputStream,',', Charset.forName("UTF-8"));
		//reader.readHeaders();
	
		while(reader.readRecord())
		{
			
			PStatusDic s = new PStatusDic();
			s.setName(reader.get(0).trim());
			s.setDes(reader.get(1).trim());
			s.setSource(reader.get(2).trim());
			pStatusDicRepository.save(s);
	
		}
		
		
		
	}


}