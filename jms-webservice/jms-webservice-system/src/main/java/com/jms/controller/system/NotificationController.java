package com.jms.controller.system;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jms.domain.ws.WSNotification;
import com.jms.domain.ws.WSPageRequest;
import com.jms.system.INotificationService;


@RestController
public class NotificationController {

	@Autowired
	private INotificationService notificationService;
	private static final Logger logger = LogManager.getLogger(NotificationController.class.getCanonicalName());

	@Transactional(readOnly = true)
	@RequestMapping(value="/notifations",consumes=MediaType.APPLICATION_JSON_VALUE)
	public List<WSNotification>  infoTest(@RequestBody WSPageRequest wsPageRequest) throws ClassNotFoundException  {
		logger.debug("page: " + wsPageRequest.getPage() +", size: " + wsPageRequest.getSize());
		Pageable pageable = new PageRequest(wsPageRequest.getPage(),wsPageRequest.getSize());
		return notificationService.loadNotifactions(pageable);
	}
	


}