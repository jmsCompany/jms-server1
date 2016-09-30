package com.jms.service.production;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.NotificationMethodEnum;
import com.jms.domain.db.EventReceiver;
import com.jms.domain.db.FCostCenter;
import com.jms.domain.db.Groups;
import com.jms.domain.db.MMachine;
import com.jms.domain.db.NotiMethod;
import com.jms.domain.db.Notification;
import com.jms.domain.db.PCPp;
import com.jms.domain.db.PStopsCode;
import com.jms.domain.db.PStopsPlan;
import com.jms.domain.db.PSubCode;
import com.jms.domain.db.PWorkCenter;
import com.jms.domain.db.Receiver;
import com.jms.domain.db.SStk;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.p.WSPStopsCode;
import com.jms.domain.ws.p.WSPStopsPlan;
import com.jms.domain.ws.p.WSPWorkCenter;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.f.FCostCenterRepository;
import com.jms.repositories.m.MMachineRepository;
import com.jms.repositories.p.PCPpRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PStopsCodeRepository;
import com.jms.repositories.p.PStopsPlanRepository;
import com.jms.repositories.p.PSubCodeRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.repositories.system.EventReceiverRepository;
import com.jms.repositories.system.JmsEventRepository;
import com.jms.repositories.system.NotiMethodRepository;
import com.jms.repositories.system.NotificationRepository;
import com.jms.repositories.system.ReceiverRepository;
import com.jms.repositories.user.GroupRepository;
import com.jms.system.INotificationService;
import com.jms.user.IUserService;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class PStopsPlanService {

	private static final Logger logger = LogManager.getLogger(PStopsPlanService.class
			.getCanonicalName());
	@Autowired
	private PStopsPlanRepository pStopsPlanRepository;
	@Autowired
	private  PSubCodeRepository pSubCodeRepository;
	
	@Autowired 
	private MMachineRepository mMachineRepository;
	
	@Autowired 
	private  PStatusDicRepository pStatusDicRepository;
	

	@Autowired
	private SecurityUtils securityUtils;
	
	@Autowired
	private EventReceiverRepository eventReceiverRepository;


	@Autowired
	private  INotificationService notificationService;
	
	@Autowired
	private  PCPpRepository cCPpRepository;
	@Autowired
	private ReceiverRepository receiverRepository;
	
	@Autowired
	private GroupRepository groupRepository;

	@Autowired @Qualifier("iUserServiceImpl")
	private IUserService iUserServiceImpl;
	
	@Autowired private JmsEventRepository jmsEventRepository;
	
	@Autowired private NotiMethodRepository notiMethodRepository;
	
	@Autowired private	NotificationRepository notificationRepository;
	@Transactional(readOnly=false)
	public Valid saveWSPStopsPlan(WSPStopsPlan wsPStopsPlan) throws Exception {
	
		//所有机台停机！
		if(wsPStopsPlan.getMachineId().equals(-1l))
		{
			for(MMachine m:mMachineRepository.getActiveMachinesByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany()))
			{
				PStopsPlan pStopsPlan = new PStopsPlan();
				
				PStopsPlan dbPStopsPlan= toDBPStopsPlan(wsPStopsPlan,pStopsPlan);
				dbPStopsPlan.setMMachine(m);
			    pStopsPlanRepository.save(dbPStopsPlan);
			}
			
			List<EventReceiver> eventReceivers = eventReceiverRepository.findByIdEventAndIdCompany(16l,securityUtils.getCurrentDBUser().getCompany().getIdCompany());
			if(eventReceivers!=null)
			{
				notificationService.createNotificationToReceivers(securityUtils.getCurrentDBUser().getCompany(), 16l,-1l, NotificationMethodEnum.sys, eventReceivers,wsPStopsPlan.getPlanSt());
			}
		}
		else
		{
			PStopsPlan pStopsPlan;
			if(wsPStopsPlan.getIdStopsPlan()!=null&&!wsPStopsPlan.getIdStopsPlan().equals(0l))
			{
				pStopsPlan = pStopsPlanRepository.findOne(wsPStopsPlan.getIdStopsPlan());
			}
			else
			{
				pStopsPlan = new PStopsPlan();
			}
			PStopsPlan dbPStopsPlan= toDBPStopsPlan(wsPStopsPlan,pStopsPlan);
			dbPStopsPlan =  pStopsPlanRepository.save(dbPStopsPlan);
			List<EventReceiver> eventReceivers = eventReceiverRepository.findByIdEventAndIdCompany(16l,securityUtils.getCurrentDBUser().getCompany().getIdCompany());
			if(eventReceivers!=null)
			{
				notificationService.createNotificationToReceivers(securityUtils.getCurrentDBUser().getCompany(), 16l,dbPStopsPlan.getIdStopsPlan(), NotificationMethodEnum.sys, eventReceivers,dbPStopsPlan.getPlanSt());
			}
		}
		
		Valid v = new Valid();
		v.setValid(true);
		return v;		
		
	}

	@Transactional(readOnly=false)
	public Valid deletePStopsPlan(Long idStopsPlan)
	{
		Valid valid = new Valid();
		
		pStopsPlanRepository.delete(idStopsPlan);
		valid.setValid(true);
		
		return valid;
	}

	
	@Transactional(readOnly=true) 
	public WSPStopsPlan findWSPStopsPlan(Long idStopsPlan) throws Exception
	{	
		PStopsPlan pStopsPlan = pStopsPlanRepository.findOne(idStopsPlan);
		return  toWSPStopsPlan(pStopsPlan);
		
	}

	@Transactional(readOnly=true) 
	public List<WSPStopsPlan> findWSPStopsPlans() throws Exception
	{	
		List<PStopsPlan> pStopsPlans = pStopsPlanRepository.getPStopsPlansByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		List<WSPStopsPlan>  ws = new ArrayList<WSPStopsPlan>();
		for(PStopsPlan p: pStopsPlans)
		{
			ws.add(toWSPStopsPlan(p));
		}
		return  ws;
		
	}
	
	@Transactional(readOnly=false) 
	public void remind()
	{
		//logger.debug(" plan stop remind: ");
		Date remind = new Date(new Date().getTime() + 1000*60*60);
		List<PStopsPlan> pStopsPlans = pStopsPlanRepository.getPStopsPlansBydRemindTime(remind);
		NotiMethod method = notiMethodRepository.findByMethod(NotificationMethodEnum.sys.name());
		for(PStopsPlan p : pStopsPlans)
		{
			Notification noti = new Notification();
			noti.setCompany(p.getCompany());
			noti.setCreationTime(new Date());
			noti.setIdSource(p.getIdStopsPlan());
			noti.setJmsEvent(jmsEventRepository.findOne(16l));
			noti.setNotiMethod(method);
			notificationRepository.save(noti);
			
			MMachine machine = p.getMMachine();
			for(PCPp cpp: cCPpRepository.getByMachineIdAndTime(machine.getIdMachine(), new Date()))
            {

		        Users u = cpp.getUsers();
            	Groups group = iUserServiceImpl.getGroupsByUserId(u.getIdUser());
            	Receiver receiver = new Receiver();
    			receiver.setGroups(group);
    			receiver.setNotification(noti);
    			receiver.setUnsubscribe(0l);
    			receiver.setChecked(0l);
    			receiver.setReceiveTime(new Date());
    			receiverRepository.save(receiver);
            }
			
			p.setRemind(1l); //已经通知
			pStopsPlanRepository.save(p);
		}
	}
	
	@Transactional(readOnly=true) 
	public List<WSPStopsPlan> findWSPStopsPlansByMachineId(Long machineId) throws Exception
	{	
		Date future = new Date(new Date().getTime()+1000*60*60*2);
		
		List<PStopsPlan> pStopsPlans = pStopsPlanRepository.getPStopsPlansByCompanyIdAndMachineId(securityUtils.getCurrentDBUser().getCompany().getIdCompany(),machineId,future);
		List<WSPStopsPlan>  ws = new ArrayList<WSPStopsPlan>();
		for(PStopsPlan p: pStopsPlans)
		{
			ws.add(toWSPStopsPlan(p));
		}
		return  ws;
		
	}
	
	
	private PStopsPlan toDBPStopsPlan(WSPStopsPlan wsPStopsPlan,PStopsPlan pStopsPlan) throws Exception
	{
	
	
		 //   logger.debug("actSt: " + wsPStopsPlan.getActSt() +", actFt: " + wsPStopsPlan.getActFt());
			PStopsPlan dbPStopsPlan = (PStopsPlan)BeanUtil.shallowCopy(wsPStopsPlan, PStopsPlan.class, pStopsPlan);
			dbPStopsPlan.setCompany(securityUtils.getCurrentDBUser().getCompany());
			if(!wsPStopsPlan.getMachineId().equals(-1l))
			{
				dbPStopsPlan.setMMachine(mMachineRepository.findOne(wsPStopsPlan.getMachineId()));
			}
			if(wsPStopsPlan.getActSt()==null)
			{
				dbPStopsPlan.setActSt(null);
			}
			if(wsPStopsPlan.getActFt()==null)
			{
				dbPStopsPlan.setActFt(null);
			}
			dbPStopsPlan.setPSubCode(pSubCodeRepository.findOne(wsPStopsPlan.getpSubCodeId()));
			dbPStopsPlan.setPStatusDic(pStatusDicRepository.findOne(wsPStopsPlan.getStatusId()));
			return dbPStopsPlan;
		
	}
	
	private WSPStopsPlan toWSPStopsPlan(PStopsPlan pStopsPlan) throws Exception
	{
		WSPStopsPlan pc = (WSPStopsPlan)BeanUtil.shallowCopy(pStopsPlan, WSPStopsPlan.class, null);
		pc.setCompanyId(pStopsPlan.getCompany().getIdCompany());
		pc.setCompanyName(pStopsPlan.getCompany().getCompanyName());
		pc.setMachineCode(pStopsPlan.getMMachine().getCode());
		pc.setMachineId(pStopsPlan.getMMachine().getIdMachine());
		pc.setStatus(pStopsPlan.getPStatusDic().getName());
		pc.setStatusId(pStopsPlan.getPStatusDic().getIdPstatus());
		pc.setpSubCode(pStopsPlan.getPSubCode().getSubCode());
		pc.setpSubCodeId(pStopsPlan.getPSubCode().getIdSubCode());
		
		return pc;
	}

}
