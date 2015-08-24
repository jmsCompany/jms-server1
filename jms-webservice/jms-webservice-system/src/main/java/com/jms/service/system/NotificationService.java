package com.jms.service.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.EventTypeEnum;
import com.jms.domain.NotificationMethodEnum;
import com.jms.domain.db.AbstractSecuredEntity;
import com.jms.domain.db.Company;
import com.jms.domain.db.Groups;
import com.jms.domain.db.JmsEvent;
import com.jms.domain.db.NotiMethod;
import com.jms.domain.db.Notification;
import com.jms.domain.db.Receiver;
import com.jms.domain.db.Users;
import com.jms.domain.ws.WSNotification;
import com.jms.repositories.system.JmsEventRepository;
import com.jms.repositories.system.NotiMethodRepository;
import com.jms.repositories.system.NotificationRepository;
import com.jms.repositories.system.ReceiverRepository;
import com.jms.repositories.user.GroupRepository;
import com.jms.system.INotificationService;
import com.jms.web.security.JMSUserDetails;
import com.jms.web.security.SecurityUtils;


@Service
@Transactional(readOnly=false)
public class NotificationService implements INotificationService{

	@Autowired
	private NotiMethodRepository notiMethodRepository;
	
	@Autowired
	private JmsEventRepository jmsEventRepository;
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private ReceiverRepository receiverRepository;
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private SecurityUtils securityUtils;
	
	@Override
	public void createNotification(Company company,
			AbstractSecuredEntity entity, EventTypeEnum eventTypeEnum,
			NotificationMethodEnum notificationMethodEnum, List<Long> groups) {
		NotiMethod method = notiMethodRepository.findByMethod(notificationMethodEnum.name());
		JmsEvent jmsEvent = jmsEventRepository.findByClassAndName(entity.getClass().getCanonicalName(), eventTypeEnum.name());
		Notification noti = new Notification();
		noti.setCompany(company);
		noti.setCreationTime(new Date());
		noti.setIdSource(entity.getId());
		noti.setJmsEvent(jmsEvent);
		noti.setNotiMethod(method);
		Users users = securityUtils.getCurrentDBUser();
		noti.setUsers(users);
		notificationRepository.save(noti);
		for(Long idGroup:groups)
		{
			Groups group = groupRepository.findOne(idGroup);
			Receiver receiver = new Receiver();
			receiver.setGroups(group);
			receiver.setNotification(noti);
			receiver.setUnsubscribe(0l);
			receiverRepository.save(receiver);
		}
	
	}

	@Override
	public List<WSNotification> loadNotifactions() {
		 JMSUserDetails jmsUserDetails = securityUtils.getCurrentUser();
		 jmsUserDetails.getAuthorities();
		 List<WSNotification> notiList = new ArrayList<WSNotification>();
		 return notiList;
	}

	
}
