package com.jms.service.production;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.PCPp;
import com.jms.domain.db.PUnplannedStops;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.production.WSPUnplannedStops;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.p.PCPpRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PSubCodeRepository;
import com.jms.repositories.p.PUnplannedStopsRepository;

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
			List<PUnplannedStops> ws = pUnplannedStopsRepository.getByMachineIdAndStatusId(wsPUnplannedStops.getIdMachine(), 18l);
			if(ws!=null&&!ws.isEmpty())
				return wsPUnplannedStops;
			
			pUnplannedStops.setOpSt(new Date());
		}
		PUnplannedStops dbPUnplannedStops= toDBPUnplannedStops(wsPUnplannedStops,pUnplannedStops);
		dbPUnplannedStops = pUnplannedStopsRepository.save(dbPUnplannedStops);
		wsPUnplannedStops.setIdUnplannedStops(dbPUnplannedStops.getIdUnplannedStops());
		return wsPUnplannedStops;		
		
	}

	
	
	
	@Transactional(readOnly=false)
	public Valid updateWSPStopsPlan(Long machineId, Long type) {
		
		Valid v = new Valid();
		List<PUnplannedStops> pUnplannedStops =pUnplannedStopsRepository.getByMachineIdAndStatusId(machineId,18l);
		if(pUnplannedStops!=null)
		{
			PUnplannedStops p = pUnplannedStops.get(0);
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
	public List<WSPUnplannedStops> findWSPUnplannedStopsBySubCodeId(Long pSubCodeId) throws Exception
	{	
	
		List<PUnplannedStops> pUnplannedStops =pUnplannedStopsRepository.getByPSubCodeId(pSubCodeId);
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
		dbPUnplannedStops.setPSubCode(pSubCodeRepository.findOne(wsPUnplannedStops.getpSubCodeId()));
		
		if(wsPUnplannedStops.getIdCpp()!=null)
		{
			dbPUnplannedStops.setIdMachine(pCPpRepository.findOne(wsPUnplannedStops.getIdCpp()).getMMachine().getIdMachine());
			
		}
		
		return dbPUnplannedStops;
	}
	
	private WSPUnplannedStops toWSPUnplannedStops(PUnplannedStops pUnplannedStops) throws Exception
	{
		WSPUnplannedStops pc = (WSPUnplannedStops)BeanUtil.shallowCopy(pUnplannedStops, WSPUnplannedStops.class, null);
	    pc.setpSubCode(pUnplannedStops.getPSubCode().getSubCode());
	    pc.setpSubCodeId(pUnplannedStops.getPSubCode().getIdSubCode());
	    pc.setStatus(pUnplannedStops.getPStatusDic().getName());
	    pc.setStatusId(pUnplannedStops.getPStatusDic().getIdPstatus());
		
		return pc;
	}

}
