package com.jms.system;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;

import com.jms.domain.EventTypeEnum;
import com.jms.domain.NotificationMethodEnum;
import com.jms.domain.db.AbstractSecuredEntity;
import com.jms.domain.db.Company;
import com.jms.domain.db.EventReceiver;
import com.jms.domain.ws.WSNotification;

public interface INotificationService {
	
	public void createNotification(Company company,AbstractSecuredEntity entity,EventTypeEnum eventTypeEnum,NotificationMethodEnum notificationMethodEnum, List<Long> groups);
	public List<WSNotification> loadNotifactions(Pageable pageable) throws ClassNotFoundException ;
	public void createNotificationToReceivers(Company company,Long eventId,Long sourceId,NotificationMethodEnum notificationMethodEnum,  List<EventReceiver> receivers,Date msgTime);
	public void sendEmails();
	
	public void sendEmails(List<EventReceiver> eventReceivers,String title,String template,Map<String, Object> model,String[] files);
}
