package com.jms.service.system;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.domain.EventTypeEnum;
import com.jms.domain.GroupTypeEnum;
import com.jms.domain.db.Apps;
import com.jms.domain.db.Company;
import com.jms.domain.db.EventReceiver;
import com.jms.domain.db.Groups;
import com.jms.domain.db.JmsEvent;
import com.jms.domain.db.PMr;
import com.jms.domain.db.PUnplannedStops;
import com.jms.domain.db.SMtf;
import com.jms.domain.db.Users;
import com.jms.domain.db.WProject;
import com.jms.domain.db.WTask;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.system.EventReceiverRepository;
import com.jms.repositories.system.JmsEventRepository;
import com.jms.repositories.user.GroupRepository;
import com.jms.repositories.user.GroupTypeRepository;
import com.jms.repositories.user.UsersRepository;

@Service
@Transactional(readOnly=false)
public class EventReceiverService {
	@Autowired
	private JmsEventRepository jmsEventRepository;
	@Autowired
	private EventReceiverRepository eventReceiverRepository;
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private  GroupTypeRepository groupTypeRepository;
	@Autowired
	private  UsersRepository usersRepository;

	public void loadReceivers(InputStream inputStream, Long companyId)throws IOException {
		
		CsvReader reader = new CsvReader(inputStream,',', Charset.forName("UTF-8"));
		reader.readHeaders();
	
		while(reader.readRecord())
		{
			String login = reader.get(0);
			Users u = usersRepository.findByUsername(login);
			if(u==null)
			{
				u=usersRepository.findByUsername(login+"@@"+companyId);
			}
			//System.out.println(login+"@@"+companyId);
			Groups g = groupRepository.findHimselfGroupByIdUser(""+u.getIdUser());
			if(g==null)
			{
				g = new Groups();
				g.setCompany(companyRepository.findOne(companyId));
				g.setCreationTime(new Date());
				g.setGroupName(""+u.getIdUser());
				g.setGroupType(groupTypeRepository.findOne(2l));
				g= groupRepository.save(g);
			}
			EventReceiver er = new EventReceiver();

			er.setIdGroup(g.getIdGroup());
			er.setIdEvent(Long.parseLong(reader.get(1)));
			er.setDelay(Long.parseLong(reader.get(2)));
			er.setIdCompany(companyId);
			eventReceiverRepository.save(er);
	
		}
		
		
		
	}
	
}
