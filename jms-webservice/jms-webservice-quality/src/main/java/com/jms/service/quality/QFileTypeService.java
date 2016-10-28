package com.jms.service.quality;

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
import com.jms.domain.db.QFileType;
import com.jms.domain.db.QStatusDic;
import com.jms.domain.db.SMaterialTypeDic;
import com.jms.domain.db.SStatusDic;
import com.jms.domain.db.SysDic;
import com.jms.domain.db.SysDicD;
import com.jms.domain.ws.s.WSSStatus;
import com.jms.repositories.m.MStatusDicRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.q.QFileTypeRepository;
import com.jms.repositories.q.QStatusDicRepository;
import com.jms.repositories.s.SMaterialTypeDicRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.system.SysDicDRepository;
import com.jms.repositories.system.SysDicRepository;

@Service
@Transactional
public class QFileTypeService {

	private static final Logger logger = LogManager.getLogger(QFileTypeService.class
			.getCanonicalName());
	@Autowired
	private QFileTypeRepository qFileTypeRepository;
	
	
	
	
	public void loadQFileTypes(Long companyId) {
		
	
		for(String s : Config.qFileTypes)
		{
			
			//System.out.println(s);
			QFileType t = new QFileType();
			t.setDes(s);
			t.setIdCompany(companyId);
			t.setType(s);
			qFileTypeRepository.save(t);
		}
		
		
		
	}


}
