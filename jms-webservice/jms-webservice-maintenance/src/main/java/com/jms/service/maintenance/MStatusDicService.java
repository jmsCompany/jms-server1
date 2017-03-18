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
import com.jms.domain.db.MStatusDic;
import com.jms.domain.db.PStatusDic;
import com.jms.domain.db.SMaterialTypeDic;
import com.jms.domain.db.SStatusDic;
import com.jms.domain.db.SysDic;
import com.jms.domain.db.SysDicD;
import com.jms.domain.db.Users;
import com.jms.domain.ws.s.WSSStatus;
import com.jms.repositories.m.MStatusDicRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.s.SMaterialTypeDicRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.system.SysDicDRepository;
import com.jms.repositories.system.SysDicRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class MStatusDicService {

	private static final Logger logger = LogManager.getLogger(MStatusDicService.class
			.getCanonicalName());
	@Autowired
	private MStatusDicRepository mStatusDicRepository;
	@Autowired private SecurityUtils securityUtils;
	
	@Transactional(readOnly=true)
	public List<WSSStatus> getSStatus(String source)
	{
		List<WSSStatus> wss = new ArrayList<WSSStatus>();
	    Users u =securityUtils.getCurrentDBUser();
	    boolean isEn = false;
	    if(u.getLang()!=null&&u.getLang().equals("en"))
	    {
	    	isEn =true;
	    }
	    
		for(MStatusDic s :mStatusDicRepository.getBySource(source))
		{
			WSSStatus ws = new WSSStatus();
			ws.setId(s.getIdMstatusDic());
			if(isEn)
			{
				ws.setName(s.getNameEn());
			}else
			{
				ws.setName(s.getName());
				
			}
			
			wss.add(ws);
		}
		return wss;
	}
	
	
	public void loadStatus(InputStream inputStream)throws IOException {
		
		CsvReader reader = new CsvReader(inputStream,',', Charset.forName("UTF-8"));
		//reader.readHeaders();
	
		while(reader.readRecord())
		{
			
			MStatusDic s = new MStatusDic();
			s.setName(reader.get(0).trim());
			s.setDes(reader.get(1).trim());
			s.setSource(reader.get(2).trim());
			mStatusDicRepository.save(s);
	
		}
		
		
		
	}


}
