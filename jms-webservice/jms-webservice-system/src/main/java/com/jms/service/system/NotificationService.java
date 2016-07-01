package com.jms.service.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.acl.SecuredObjDAO;
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
	@Autowired
	private SecuredObjDAO securedObjDAO;
	private static final Logger logger = LogManager.getLogger(NotificationService.class.getCanonicalName());
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
			receiver.setChecked(0l);
			receiverRepository.save(receiver);
		}
	
	}

	@Override
	public List<WSNotification> loadNotifactions(Pageable pageable) throws ClassNotFoundException {
		 JMSUserDetails jmsUserDetails = securityUtils.getCurrentUser();
		 Collection<? extends GrantedAuthority> ls= jmsUserDetails.getAuthorities();
		 List<Long> groups = new ArrayList<Long>();
		 for(GrantedAuthority ga: ls)
		 {
			 String au = ga.getAuthority();
			 try{
				 Long idGroup =Long.parseLong(au);
				 groups.add(idGroup);
				 }catch(NumberFormatException e){}
			
		 }
		Page<Receiver> rs = receiverRepository.findReceivers(groups,pageable);
		List<WSNotification> notiList = new ArrayList<WSNotification>();
		/*	for(Receiver r: rs)
		{
			WSNotification wn = new WSNotification();
			wn.setCreationTime(r.getNotification().getCreationTime());
			wn.setEvent(r.getNotification().getJmsEvent().getDescription());
			wn.setIdNotification(r.getNotification().getIdNoti());
			wn.setUsername(r.getNotification().getUsers().getUsername());
		    Class t =	Class.forName(r.getNotification().getJmsEvent().getClass_());
		    wn.setDetails(securedObjDAO.find(t, r.getNotification().getIdSource()).toString());
			notiList.add(wn);
		}
		*/
		int pagenum= pageable.getPageNumber();
		int pagesize= pageable.getPageSize();
		
		for(int i=pagenum*pagesize; i<pagenum*pagesize +pagesize;i++)
		{
			WSNotification wn = new WSNotification();
			wn.setCreationTime(new Date());
			wn.setEvent("第 " + i +", 事件");
			wn.setIdNotification((long)i);
			wn.setUsername("张三");
		   
		    wn.setDetails("详情略");
			notiList.add(wn);
		}
		 return notiList;
	}

	
}
