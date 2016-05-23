package com.jms.controller.system;

import java.util.ArrayList;
import java.util.Date;
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

//	@Transactional(readOnly = true)
//	@RequestMapping(value="/notifations",consumes=MediaType.APPLICATION_JSON_VALUE)
//	public List<WSNotification>  infoTest(@RequestBody WSPageRequest wsPageRequest) throws ClassNotFoundException  {
//		logger.debug("page: " + wsPageRequest.getPage() +", size: " + wsPageRequest.getSize());
//		Pageable pageable = new PageRequest(wsPageRequest.getPage(),wsPageRequest.getSize());
//		return notificationService.loadNotifactions(pageable);
//	}
//	

	
	@RequestMapping(value="/notifations")
	public List<WSNotification>  notifations()  {
		List<WSNotification> ws = new ArrayList<WSNotification>();
		WSNotification w = new WSNotification();
		w.setCreationTime(new Date());
		w.setDetails("需料");
		w.setEvent("MR");
		w.setIdNotification(1l);
		w.setUsername("OP");
		ws.add(w);
		
		WSNotification w1 = new WSNotification();
		w1.setCreationTime(new Date());
		w1.setDetails("缺料");
		w1.setEvent("MS");
		w1.setIdNotification(2l);
		w1.setUsername("OP");
		ws.add(w1);
		
		return ws;
	}
	
	
	

}