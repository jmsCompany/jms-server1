package com.jms.service.system;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.acl.SecurityACLDAO;
import com.jms.domain.GroupTypeEnum;
import com.jms.domain.db.Apps;
import com.jms.domain.db.Company;
import com.jms.domain.db.Groups;
import com.jms.repositories.system.AppsRepository;
import com.jms.repositories.user.GroupRepository;


@Service
@Transactional(readOnly=false)
public class AppsService {

	@Autowired
	private AppsRepository appsRepository;
	@Autowired
	private SecurityACLDAO securityACLDAO;
	@Autowired
	private GroupRepository groupRepository;
	
	public void createInitalApps(InputStream inputStream, Company templateCompany)throws IOException {
		
		Groups group = groupRepository.findGroupByGroupNameAndCompany(
				"全公司", templateCompany.getIdCompany(), GroupTypeEnum.Company.name());
		GrantedAuthoritySid sid = new GrantedAuthoritySid(""
				+ group.getIdGroup());
		PrincipalSid pid = new PrincipalSid(""+templateCompany.getUsersByCreator().getIdUser());
		CsvReader reader = new CsvReader(inputStream,',', Charset.forName("UTF-8"));
		reader.readHeaders();
	
		while(reader.readRecord())
		{
			
			Apps apps = new Apps();
			apps.setScope(1l);
			apps.setGroups(reader.get(0).trim());
			apps.setAppName(reader.get(1).trim());
			apps.setDescription(reader.get(1).trim());
			apps.setUrl(reader.get(3).trim());
			appsRepository.save(apps);
			String isAdmin = reader.get(2).trim();
			if(isAdmin.equals("0"))
			{
				securityACLDAO.addPermission(apps, sid, BasePermission.READ);

			}
				securityACLDAO.addPermission(apps, pid, BasePermission.ADMINISTRATION);
	
		}
		
		
		
	}

	
}
