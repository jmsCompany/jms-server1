package com.jms.controller.system;



import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jms.domain.db.Groups;
import com.jms.domain.db.Notification;
import com.jms.domain.db.Receiver;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSNotification;
import com.jms.repositories.system.ReceiverRepository;
import com.jms.repositories.user.GroupRepository;
import com.jms.system.INotificationService;
import com.jms.web.security.SecurityUtils;


@RestController
public class NotificationController {

	@Autowired
	private INotificationService notificationService;
	@Autowired private SecurityUtils securityUtils;
	private static final Logger logger = LogManager.getLogger(NotificationController.class.getCanonicalName());
	@Autowired private GroupRepository groupRepository;
	@Autowired private ReceiverRepository receiverRepository;

//	@Transactional(readOnly = true)
//	@RequestMapping(value="/notifations",consumes=MediaType.APPLICATION_JSON_VALUE)
//	public List<WSNotification>  infoTest(@RequestBody WSPageRequest wsPageRequest) throws ClassNotFoundException  {
//		logger.debug("page: " + wsPageRequest.getPage() +", size: " + wsPageRequest.getSize());
//		Pageable pageable = new PageRequest(wsPageRequest.getPage(),wsPageRequest.getSize());
//		return notificationService.loadNotifactions(pageable);
//	}
//	

	@Transactional(readOnly = true)
	@RequestMapping(value="/notifations")
	public List<WSNotification>  notifations()  {
		List<WSNotification> ws = new ArrayList<WSNotification>();
		Groups g = groupRepository.findHimselfGroupByIdUser(""+securityUtils.getCurrentDBUser().getIdUser());
		if(g!=null)
		{
			List<Receiver> receivers = receiverRepository.findReceiversByGroupId(g.getIdGroup());
			for(Receiver r:receivers)
			{
				WSNotification w = new WSNotification();
				w.setCreationTime(r.getNotification().getCreationTime());
				w.setEvent(""+r.getNotification().getJmsEvent().getIdEvent());
				w.setDetails(r.getNotification().getJmsEvent().getDescription());
				w.setIdNotification(r.getIdReceiver());
				w.setUsername(r.getNotification().getUsers().getName());
				ws.add(w);
			}
		}

		return ws;
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/readNotifation", method=RequestMethod.GET)
	public Valid readNotifation(@RequestParam("idNoti") Long idNoti) {
		Valid v = new Valid();
		Receiver r = receiverRepository.findOne(idNoti);
		Notification n = r.getNotification();
		Long eventId = n.getJmsEvent().getIdEvent();
		
		for(Receiver re:receiverRepository.findReceiversByEventIdAndGroupsId(eventId,r.getGroups().getIdGroup()))
		{
			re.setChecked(1l);
			receiverRepository.save(re);
		}
		v.setValid(true);
		return v;
	}

	
}