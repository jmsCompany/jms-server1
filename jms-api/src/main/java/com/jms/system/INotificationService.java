package com.jms.system;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jms.domain.EventTypeEnum;
import com.jms.domain.NotificationMethodEnum;
import com.jms.domain.db.AbstractSecuredEntity;
import com.jms.domain.db.Company;
import com.jms.domain.ws.WSNotification;

public interface INotificationService {
	
	public void createNotification(Company company,AbstractSecuredEntity entity,EventTypeEnum eventTypeEnum,NotificationMethodEnum notificationMethodEnum, List<Long> groups);
	public List<WSNotification> loadNotifactions(Pageable pageable) throws ClassNotFoundException ;

}
