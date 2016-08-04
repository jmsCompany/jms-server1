package com.jms.service.production;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.NotificationMethodEnum;
import com.jms.domain.db.EventReceiver;
import com.jms.domain.db.MRepairHistory;
import com.jms.domain.db.PCPp;
import com.jms.domain.db.PUnplannedStops;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.p.WSPUnplannedStops;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.m.MMachineRepository;
import com.jms.repositories.m.MRepairHistoryRepository;
import com.jms.repositories.m.MStatusDicRepository;
import com.jms.repositories.p.PCPpRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PSubCodeRepository;
import com.jms.repositories.p.PUnplannedStopsRepository;
import com.jms.repositories.system.EventReceiverRepository;
import com.jms.system.INotificationService;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class PUnplannedStopsService {

	private static final Logger logger = LogManager.getLogger(PUnplannedStopsService.class
			.getCanonicalName());
	@Autowired
	private PUnplannedStopsRepository pUnplannedStopsRepository;
	@Autowired
	private  PSubCodeRepository pSubCodeRepository;
	@Autowired
	private PCPpRepository pCPpRepository;
	@Autowired 
	private  PStatusDicRepository pStatusDicRepository;
	@Autowired private SecurityUtils securityUtils;
	
	@Autowired
	private  MMachineRepository mMachineRepository;
	@Autowired
	private EventReceiverRepository eventReceiverRepository;
	@Autowired
	private  INotificationService notificationService;
	@Autowired
	private MRepairHistoryRepository mRepairHistoryRepository;
	@Autowired
	private MStatusDicRepository mStatusDicRepository;
	
	@Transactional(readOnly=false)
	public WSPUnplannedStops saveWSPStopsPlan(WSPUnplannedStops wsPUnplannedStops) throws Exception {
		PUnplannedStops pUnplannedStops;
		if(wsPUnplannedStops.getIdUnplannedStops()!=null)
		{
			pUnplannedStops = pUnplannedStopsRepository.findOne(wsPUnplannedStops.getIdUnplannedStops());
		}
		else
		{
			pUnplannedStops = new PUnplannedStops();
			List<PUnplannedStops> ws = pUnplannedStopsRepository.getByMachineIdAndStatusIdAndHasSubCode(wsPUnplannedStops.getIdMachine(), 18l);
			if(wsPUnplannedStops.getpSubCodeId()!=null&&ws!=null&&!ws.isEmpty())
				return wsPUnplannedStops;
		
			pUnplannedStops.setOpSt(new Date());
		}
		
		PUnplannedStops dbPUnplannedStops= toDBPUnplannedStops(wsPUnplannedStops,pUnplannedStops);
		
		dbPUnplannedStops.setIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		dbPUnplannedStops = pUnplannedStopsRepository.save(dbPUnplannedStops);
		wsPUnplannedStops.setIdUnplannedStops(dbPUnplannedStops.getIdUnplannedStops());
		
		//设备质量停机，列入维修历史
		
		if(wsPUnplannedStops.getpSubCodeId()!=null&&(wsPUnplannedStops.getpSubCodeId().equals(1l)||wsPUnplannedStops.getpSubCodeId().equals(4l)))
		{
			MRepairHistory m = new MRepairHistory();
			m.setMStatusDic(mStatusDicRepository.findOne(7l)); //发起状态
			if(wsPUnplannedStops.getIdCpp()!=null)
			{
			    m.setMMachine(pCPpRepository.findOne(wsPUnplannedStops.getIdCpp()).getMMachine());
			}
			m.setUsersByOp(securityUtils.getCurrentDBUser());
			m.setRepairTime(new Date());
			m.setIssueDes(wsPUnplannedStops.getOpDes());
			m.setIdUnplannedStop(dbPUnplannedStops.getIdUnplannedStops());
			mRepairHistoryRepository.save(m);
		}
		//消息
		Long eventId = wsPUnplannedStops.getEventId();
		//logger.debug("idCpp: " + wsPUnplannedStops.getIdCpp() +", eventId: " + eventId);
		List<EventReceiver> eventReceivers = eventReceiverRepository.findByIdEventAndIdCompany(eventId,securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		if(eventReceivers!=null)
		{
			notificationService.createNotificationToReceivers(securityUtils.getCurrentDBUser().getCompany(), eventId, wsPUnplannedStops.getIdUnplannedStops(), NotificationMethodEnum.sys, eventReceivers);
		}
		
		
		return wsPUnplannedStops;		
		
	}

	
	
	
	@Transactional(readOnly=false)
	public Valid updateWSPStopsPlan(Long machineId, Long type) {
		
		//logger.debug("更改 停机状态： "+machineId+", type: " +type);
		Valid v = new Valid();
		List<PUnplannedStops> pUnplannedStops =pUnplannedStopsRepository.getByMachineIdAndStatusId(machineId,18l);
		if(pUnplannedStops!=null&&!pUnplannedStops.isEmpty())
		{
			PUnplannedStops p = pUnplannedStops.get(0);
			
		//	logger.debug("更改 停机状态： "+ p.getIdUnplannedStops() +",  " + p.getIdMachine() +" type: " +type);
		    if(type.equals(0l))
		    {
		    	p.setEqSt(new Date());
		    	v.setValid(true);
		    	pUnplannedStopsRepository.save(p);
		    }
		    else if(type.equals(1l))
		    {
		    	p.setEqFt(new Date());
		    	v.setValid(true);
		    	pUnplannedStopsRepository.save(p);
		    }
		    else if(type.equals(2l))
		    {
		    	p.setOpFt(new Date());
		    	p.setPStatusDic(pStatusDicRepository.findOne(19l));
		    	pUnplannedStopsRepository.save(p);
		    	v.setValid(true);
		    }
		    else
		    {
		    	v.setValid(false);
				v.setMsg("更新类型错误，必须是0，1，2， 0处理开始，1处理结束，2确认结束");
		    }
		}
		else
		{
			v.setValid(false);
			v.setMsg("该设备没有停机！");
		}
		return v;
	}

	

	@Transactional(readOnly=true) 
	public List<WSPUnplannedStops> findWSPUnplannedStopsByCppId(Long cppId) throws Exception
	{	
		PCPp cpp =pCPpRepository.findOne(cppId);
		List<PUnplannedStops> pUnplannedStops =pUnplannedStopsRepository.getByMachineId(cpp.getMMachine().getIdMachine());
		List<WSPUnplannedStops>  ws = new ArrayList<WSPUnplannedStops>();
		for(PUnplannedStops p: pUnplannedStops)
		{
			ws.add(toWSPUnplannedStops(p));
		}
		return  ws;
		
	}
	
	@Transactional(readOnly=true) 
	public List<WSPUnplannedStops> findWSPUnplannedStopsBySubCodeId(Long pSubCodeId, Long companyId) throws Exception
	{	
	
		List<PUnplannedStops> pUnplannedStops =pUnplannedStopsRepository.getByPSubCodeId(pSubCodeId,companyId);
		List<WSPUnplannedStops>  ws = new ArrayList<WSPUnplannedStops>();
		for(PUnplannedStops p: pUnplannedStops)
		{
			ws.add(toWSPUnplannedStops(p));
		}
		return  ws;
		
	}
	
	
	
	private PUnplannedStops toDBPUnplannedStops(WSPUnplannedStops wsPUnplannedStops,PUnplannedStops pUnplannedStops) throws Exception
	{
	
		PUnplannedStops dbPUnplannedStops = (PUnplannedStops)BeanUtil.shallowCopy(wsPUnplannedStops, PUnplannedStops.class, pUnplannedStops);

		dbPUnplannedStops.setPStatusDic(pStatusDicRepository.findOne(wsPUnplannedStops.getStatusId()));
		if(wsPUnplannedStops.getpSubCodeId()!=null)
		{
			dbPUnplannedStops.setPSubCode(pSubCodeRepository.findOne(wsPUnplannedStops.getpSubCodeId()));
		}
		
		
		if(wsPUnplannedStops.getIdCpp()!=null)
		{
			dbPUnplannedStops.setIdMachine(pCPpRepository.findOne(wsPUnplannedStops.getIdCpp()).getMMachine().getIdMachine());
			
		}
		dbPUnplannedStops.setIdMachine(wsPUnplannedStops.getIdMachine());
		
		return dbPUnplannedStops;
	}
	
	private WSPUnplannedStops toWSPUnplannedStops(PUnplannedStops pUnplannedStops) throws Exception
	{
		WSPUnplannedStops pc = (WSPUnplannedStops)BeanUtil.shallowCopy(pUnplannedStops, WSPUnplannedStops.class, null);
	    pc.setpSubCode(pUnplannedStops.getPSubCode().getSubCode());
	    pc.setpSubCodeId(pUnplannedStops.getPSubCode().getIdSubCode());
	    pc.setStatus(pUnplannedStops.getPStatusDic().getName());
	    pc.setStatusId(pUnplannedStops.getPStatusDic().getIdPstatus());
	    pc.setMachine(mMachineRepository.findOne(pUnplannedStops.getIdMachine()).getCode());
		
		return pc;
	}

}
