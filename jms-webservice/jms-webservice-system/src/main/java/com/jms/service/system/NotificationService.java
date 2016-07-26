package com.jms.service.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.acl.SecuredObjDAO;
import com.jms.domain.EventTypeEnum;
import com.jms.domain.NotificationMethodEnum;
import com.jms.domain.db.AbstractSecuredEntity;
import com.jms.domain.db.Company;
import com.jms.domain.db.EventReceiver;
import com.jms.domain.db.Groups;
import com.jms.domain.db.JmsEvent;
import com.jms.domain.db.NotiMethod;
import com.jms.domain.db.Notification;
import com.jms.domain.db.PCPp;
import com.jms.domain.db.PMr;
import com.jms.domain.db.PUnplannedStops;
import com.jms.domain.db.Receiver;
import com.jms.domain.db.SMtf;
import com.jms.domain.db.Users;
import com.jms.domain.ws.WSNotification;
import com.jms.email.EmailSenderService;
import com.jms.repositories.m.MMachineRepository;
import com.jms.repositories.p.PCPpRepository;
import com.jms.repositories.p.PMrRepository;
import com.jms.repositories.p.PUnplannedStopsRepository;
import com.jms.repositories.s.SMtfRepository;
import com.jms.repositories.system.JmsEventRepository;
import com.jms.repositories.system.NotiMethodRepository;
import com.jms.repositories.system.NotificationRepository;
import com.jms.repositories.system.ReceiverRepository;
import com.jms.repositories.user.GroupRepository;
import com.jms.repositories.user.UsersRepository;
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
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private EmailSenderService emailSenderService;
	@Autowired 
	private PMrRepository pMrRepository;
	@Autowired 
	private MMachineRepository mMachineRepository;
	
	@Autowired 
	private PCPpRepository pCPpRepository;
	
	@Autowired
	private  PUnplannedStopsRepository pUnplannedStopsRepository;
	@Autowired
	private  SMtfRepository sMtfRepository;
	
	
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
			//receiver.setReceiveTime(receiveTime);
			receiverRepository.save(receiver);
		}
	
	}
	
	@Override
	public void createNotificationToReceivers(Company company,
			Long eventId, Long sourceId, 
			NotificationMethodEnum notificationMethodEnum, List<EventReceiver> receivers) {
		NotiMethod method = notiMethodRepository.findByMethod(notificationMethodEnum.name());
		JmsEvent jmsEvent = jmsEventRepository.findOne(eventId);
		Notification noti = new Notification();
		noti.setCompany(company);
		noti.setCreationTime(new Date());
		noti.setIdSource(sourceId);
		noti.setJmsEvent(jmsEvent);
		noti.setNotiMethod(method);
		Users users = securityUtils.getCurrentDBUser();
		noti.setUsers(users);
		notificationRepository.save(noti);
		for(EventReceiver e:receivers)
		{
			Groups group = groupRepository.findOne(e.getIdGroup());
			Receiver receiver = new Receiver();
			receiver.setGroups(group);
			receiver.setNotification(noti);
			receiver.setUnsubscribe(0l);
			receiver.setChecked(0l);
			if(e.getDelay()!=null)
			{
				Long currentTime = new Date().getTime();
				currentTime =currentTime+e.getDelay()*60*1000;
			//	logger.debug("delay: " +e.getDelay()*60*1000);
				receiver.setReceiveTime(new Date(currentTime));
			}
			else
			{
				receiver.setReceiveTime(new Date());
			}
		
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

	/* (non-Javadoc)
	 * @see com.jms.system.INotificationService#sendEmails()
	 */
	@Override
	public void sendEmails() {
		logger.debug("send email.");
		for(Receiver r: receiverRepository.findEmailsNotSendReceivers())
		{
			Long idGroup = r.getGroups().getIdGroup();
			Groups g = groupRepository.findOne(idGroup);
			Users u = usersRepository.findOne(Long.parseLong(g.getGroupName()));
			String[] toEmailAddresses = new String[1];
			
			if(u.getEmail()!=null)
			{
				toEmailAddresses[0] = u.getEmail();
				Long sourceId = r.getNotification().getIdSource();
				JmsEvent event = r.getNotification().getJmsEvent();
				String emailTemplate="";
				String title="";
			    Map<String,Object> model =new HashMap<String, Object>();
//				if(event.getIdEvent().equals(7l)) //需料
//				{
//					title = "MR";
//					emailTemplate = "mrTemplate.vm";
//					PUnplannedStops	pUnplannedStops = pUnplannedStopsRepository.findOne(sourceId);
//					if(pUnplannedStops.getEqFt()==null)
//					{
//						List<PMr> pmrs = pMrRepository.getByIdUnplannedStop(pUnplannedStops.getIdUnplannedStops());
//						if(pmrs!=null)
//						{
//							PMr dbPMr = pmrs.get(0);
//							model.put("reason",title);
//							model.put("woNo",dbPMr.getPCPp().getPWo().getWoNo() );
//							model.put("pNo", dbPMr.getPBom().getSMaterial().getPno());
//							model.put("machine", dbPMr.getPCPp().getMMachine().getCode());
//							try{
//								emailSenderService.sendEmail(toEmailAddresses,emailTemplate,title, model, null);
//								r.setMsg("sended");
//							}catch(Exception e){
//								r.setMsg("wrong email address: " + u.getEmail());
//							}
//						}
//					}
//					else{
//						r.setMsg("solved");
//					}
//			
//				
//				}
//				else if(event.getIdEvent().equals(10l)) //缺料
//				{
//
//					title = "MS";
//					emailTemplate = "mrTemplate.vm";
//					PUnplannedStops	pUnplannedStops = pUnplannedStopsRepository.findOne(sourceId);
//					if(pUnplannedStops.getEqFt()==null)
//					{
//						List<PMr> pmrs = pMrRepository.getByIdUnplannedStop(pUnplannedStops.getIdUnplannedStops());
//						if(pmrs!=null)
//						{
//							PMr dbPMr = pmrs.get(0);
//							model.put("reason",title);
//							model.put("woNo",dbPMr.getPCPp().getPWo().getWoNo() );
//							model.put("pNo", dbPMr.getPBom().getSMaterial().getPno());
//							model.put("machine", dbPMr.getPCPp().getMMachine().getCode());
//							try{
//								emailSenderService.sendEmail(toEmailAddresses,emailTemplate,title, model, null);
//								r.setMsg("sended");
//							}catch(Exception e){
//								r.setMsg("wrong email address: " + u.getEmail());
//							}
//						}
//					}
//					else{
//						r.setMsg("solved");
//					}
//
//				}
//				
				
				 if(event.getIdEvent().equals(11l)) //来料入检查
				{
					title = "IQC";
					emailTemplate = "iqcTemplate.vm";
					SMtf sMtf = sMtfRepository.findOne(sourceId);
					if(sMtf.getSStatusDic()==null) //null, 5接收，目前用作全部物料已捡,
					{
						if(sMtf.getMtNo()!=null)
						{
							model.put("smtfNo",sMtf.getMtNo());
							try{
								emailSenderService.sendEmail(toEmailAddresses,emailTemplate,title, model, null);
								r.setMsg("sended");
							}catch(Exception e){
								r.setMsg("wrong email address: " + u.getEmail());
								e.printStackTrace();
							}
						}
					}
					else{
						r.setMsg("solved");
					}

				}

				else if(event.getIdEvent().equals(7l)||
						event.getIdEvent().equals(11l)||
						event.getIdEvent().equals(9l)||
						event.getIdEvent().equals(12l)||
						event.getIdEvent().equals(13l)||
						event.getIdEvent().equals(14l)||
						event.getIdEvent().equals(15l)) //停机
				{

					title = event.getDescription();
					emailTemplate = "mrTemplate.vm";
					PUnplannedStops	pUnplannedStops = pUnplannedStopsRepository.findOne(sourceId);
					if(pUnplannedStops!=null&&pUnplannedStops.getEqFt()==null)
					{
						PCPp cpp = pCPpRepository.findOne(pUnplannedStops.getIdCpp());
						if(cpp!=null)
						{
							
							model.put("reason",title);
							model.put("woNo",cpp.getPWo().getWoNo() );
							model.put("pNo", cpp.getPWo().getSSo().getSMaterial().getPno());
							model.put("machine", mMachineRepository.findOne(pUnplannedStops.getIdMachine()).getCode());
							try{
								emailSenderService.sendEmail(toEmailAddresses,emailTemplate,title, model, null);
								r.setMsg("sended");
							}catch(Exception e){
								e.printStackTrace();
								r.setMsg("wrong email address: " + u.getEmail());
							}
						}
						else
						{
							r.setMsg("no CPP!");
						}
					}
					else{
						r.setMsg("solved");
					}

				}	
			}
			else
			{
				r.setMsg("no email");
			}
			System.out.println("email: " +toEmailAddresses[0] +", msg: " +r.getMsg());
			receiverRepository.save(r);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.jms.system.INotificationService#sendEmails(java.util.List, java.lang.String, java.lang.String, java.util.Map, java.lang.String[])
	 */
	@Override
	public void sendEmails(List<EventReceiver> eventReceivers, String title, String template, Map<String, Object> model,
			String[] files) {
		List<String> ems = new ArrayList<String>();
		for(EventReceiver e : eventReceivers)
		{
			if(e.getDelay()==null||e.getDelay().equals(0l))
			{
				Groups g = groupRepository.findOne(e.getIdGroup());
				Users u = usersRepository.findOne(Long.parseLong(g.getGroupName()));
				
				if(u.getEmail()!=null)
				{
					ems.add(u.getEmail());
				}
			}
		
		}
		
		if(!ems.isEmpty()){
			
			int i=0;
			String[] toEmailAddresses = new String[ems.size()];
			for(String s:ems )
			{
				toEmailAddresses[i] = s;
				i++;
			}
		
			try{
				emailSenderService.sendEmail(toEmailAddresses,template,title, model, null);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
