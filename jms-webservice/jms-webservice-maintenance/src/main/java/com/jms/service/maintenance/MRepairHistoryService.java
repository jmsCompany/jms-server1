package com.jms.service.maintenance;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.domain.db.MHistoryPart;
import com.jms.domain.db.MMachine;
import com.jms.domain.db.MRepairHistory;
import com.jms.domain.db.MSparePart;
import com.jms.domain.db.PUnplannedStops;
import com.jms.domain.db.SInventory;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.m.WSMHistoryPart;
import com.jms.domain.ws.m.WSMRepairHistory;
import com.jms.domain.ws.m.WSMSparePart;
import com.jms.domain.ws.m.WSMachine;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.m.MHistoryPartRepository;
import com.jms.repositories.m.MMachineGroupRepository;
import com.jms.repositories.m.MMachineRepository;
import com.jms.repositories.m.MRepairHistoryRepository;
import com.jms.repositories.m.MSparePartRepository;
import com.jms.repositories.m.MStatusDicRepository;
import com.jms.repositories.p.PLineRepository;
import com.jms.repositories.p.PUnplannedStopsRepository;
import com.jms.repositories.p.PWipRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.repositories.s.SBinRepository;
import com.jms.repositories.s.SInventoryRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class MRepairHistoryService {

	private static final Logger logger = LogManager.getLogger(MRepairHistoryService.class);


	@Autowired
	private MMachineRepository mMachineRepository;
	@Autowired
	private MRepairHistoryRepository mRepairHistoryRepository;
	@Autowired
	private MHistoryPartRepository mHistoryPartRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private  MStatusDicRepository mStatusDicRepository;
	@Autowired
	private  PLineRepository pLineRepository;
	@Autowired
	private PWipRepository pWipRepository;
	@Autowired
	private PWorkCenterRepository pWorkCenterRepository;
	@Autowired
	private SBinRepository sBinRepository;
	@Autowired private SecurityUtils securityUtils;
	@Autowired private SMaterialRepository sMaterialRepository;

	@Autowired private SInventoryRepository sInventoryRepository;
	@Autowired private UsersRepository usersRepository;
	
	@Autowired private PUnplannedStopsRepository pUnplannedStopsRepository;
	

	
	
	
	@Transactional(readOnly=true)
	public WSMRepairHistory findWSMRepairHistory(Long idRepairHistory) throws Exception{	
		MRepairHistory mRepairHistory = mRepairHistoryRepository.findOne(idRepairHistory);
		return toWSMRepairHistory(mRepairHistory);
	}
	
	@Transactional(readOnly=true)
	public WSMRepairHistory findRepairHistoryByIdUnplannedStop(Long idUnplannedStop) throws Exception{	
		MRepairHistory mRepairHistory = mRepairHistoryRepository.findByIdUnplannedStop(idUnplannedStop);
		return toWSMRepairHistory(mRepairHistory);
	}
	
	
	
	public Valid saveRepairHistory(WSMRepairHistory wsMRepairHistory) throws Exception
	{
		Valid  v = new Valid();
		MRepairHistory mRepairHistory;
		if(wsMRepairHistory.getIdRepairHistory()!=null&&!wsMRepairHistory.getIdRepairHistory().equals(0l))
		{
			mRepairHistory = mRepairHistoryRepository.findOne(wsMRepairHistory.getIdRepairHistory());		
//			if(mRepairHistory!=null&&mRepairHistory.getMStatusDic().getIdMstatusDic().equals(9l))//完成
//			{
//				v.setValid(false);
//				v.setMsg("该维修已经结束，不能修改");
//				return v;
//			}
			if(!mRepairHistory.getMHistoryParts().isEmpty())
			{
				for(MHistoryPart m: mRepairHistory.getMHistoryParts())
				{
					mHistoryPartRepository.delete(m);
				}
				mRepairHistory.getMHistoryParts().clear();
			}
			
			
		}
		else
		{
			mRepairHistory = new MRepairHistory();
		}
		MRepairHistory  dbRepairHistory = toDBRepairHistory( wsMRepairHistory, mRepairHistory);
		dbRepairHistory = mRepairHistoryRepository.save(dbRepairHistory);
		
	
		for(WSMHistoryPart p:wsMRepairHistory.getHistoryPartItems().values())
		{
			if(p.getMaterialId().equals(0l))
			{
				continue;
			}
			MHistoryPart h = new MHistoryPart();
			h.setMRepairHistory(dbRepairHistory);
			h.setQty(p.getQty());
			h.setSMaterial(sMaterialRepository.findOne(p.getMaterialId()));	
			mHistoryPartRepository.save(h);
		}
		v.setValid(true);
		return v;
		
	}
	
	
	@Transactional(readOnly=false)
	public Valid deleteWSMRepairHistory(Long idRepairHistory)
	{
		Valid valid = new Valid();
		mRepairHistoryRepository.delete(idRepairHistory);
		//mHistoryPartRepository.delete(idRepairHistory);
		valid.setValid(true);
		return valid;
	}
	
	protected MRepairHistory toDBRepairHistory( WSMRepairHistory wsMRepairHistory, MRepairHistory mRepairHistory) throws Exception
	{
		MRepairHistory dbMRepairHistory= (MRepairHistory)BeanUtil.shallowCopy(wsMRepairHistory, MRepairHistory.class, mRepairHistory);
		if(wsMRepairHistory.getMachineId()!=null)
		{
			dbMRepairHistory.setMMachine(mMachineRepository.findOne(wsMRepairHistory.getMachineId()));
		}
		
		if(wsMRepairHistory.getStatusId()!=null)
		{
			dbMRepairHistory.setMStatusDic(mStatusDicRepository.findOne(wsMRepairHistory.getStatusId()));
		}
		if(wsMRepairHistory.getOpId()!=null)
		{
			dbMRepairHistory.setUsersByOp(usersRepository.findOne(wsMRepairHistory.getOpId()));
		}
		if(wsMRepairHistory.getRepairId()!=null)
		{
			dbMRepairHistory.setUsersByRepair(usersRepository.findOne(wsMRepairHistory.getRepairId()));
		}
		if(wsMRepairHistory.getConfirmorId()!=null)
		{
			dbMRepairHistory.setUsersByConfirmor(usersRepository.findOne(wsMRepairHistory.getConfirmorId()));
		}
		if(wsMRepairHistory.getMaintainerId()!=null)
		{
			dbMRepairHistory.setUsersByMaintainer(usersRepository.findOne(wsMRepairHistory.getMaintainerId()));
		}
		if(wsMRepairHistory.getIdUnplannedStop()!=null)
		{
			PUnplannedStops pUnplannedStops = pUnplannedStopsRepository.findOne(wsMRepairHistory.getIdUnplannedStop());
			if(wsMRepairHistory.getResponseTime()==null)
			{
				dbMRepairHistory.setResponseTime(pUnplannedStops.getEqSt());
			}
		  if(wsMRepairHistory.getRepairingTime()==null)
		  {
			  dbMRepairHistory.setRepairingTime(pUnplannedStops.getEqFt());
		  }
			if(wsMRepairHistory.getRecoverTime()==null)
			{
				dbMRepairHistory.setRecoverTime(pUnplannedStops.getOpFt());
			}
		
			
			
		}
	    return dbMRepairHistory;
	}
	
	public WSMRepairHistory toWSMRepairHistory(MRepairHistory mRepairHistory) throws Exception
	{
		WSMRepairHistory pc = (WSMRepairHistory)BeanUtil.shallowCopy(mRepairHistory, WSMRepairHistory.class, null);
		if(mRepairHistory.getMMachine()!=null)
		{
			pc.setMachineId(mRepairHistory.getMMachine().getIdMachine());
			pc.setMachine(mRepairHistory.getMMachine().getCode());
		}
		if(mRepairHistory.getMStatusDic()!=null)
		{
			pc.setStatus(mRepairHistory.getMStatusDic().getName());
			pc.setStatusId(mRepairHistory.getMStatusDic().getIdMstatusDic());
		}
		if(mRepairHistory.getUsersByOp()!=null)
		{
			pc.setOp(mRepairHistory.getUsersByOp().getName());
			pc.setOpId(mRepairHistory.getUsersByOp().getIdUser());
		}
		if(mRepairHistory.getUsersByMaintainer()!=null)
		{
			pc.setMaintainer(mRepairHistory.getUsersByMaintainer().getName());
			pc.setMaintainerId(mRepairHistory.getUsersByMaintainer().getIdUser());
		}
		if(mRepairHistory.getUsersByConfirmor()!=null)
		{
			pc.setConfirmor(mRepairHistory.getUsersByConfirmor().getName());
			pc.setConfirmorId(mRepairHistory.getUsersByConfirmor().getIdUser());
		}
		if(mRepairHistory.getUsersByRepair()!=null)
		{
			pc.setRepair(mRepairHistory.getUsersByRepair().getName());
			pc.setRepairId(mRepairHistory.getUsersByRepair().getIdUser());
		}
	    Map<String,WSMHistoryPart> historyPartItems = new LinkedHashMap<String,WSMHistoryPart>(0);
	    int i = 1;
		for(MHistoryPart m:mRepairHistory.getMHistoryParts())
		{
			WSMHistoryPart w = new WSMHistoryPart();
			w.setIdHistoryPart(m.getIdHistoryPart());
			if(m.getSMaterial()!=null)
			{
				w.setMaterial(m.getSMaterial().getPno()+"_"+m.getSMaterial().getRev()+"_"+m.getSMaterial().getDes());
				w.setMaterialId(m.getSMaterial().getIdMaterial());
			}
			
			w.setQty(m.getQty());
			w.setRepairHistoryId(m.getMRepairHistory().getIdRepairHistory());
			historyPartItems.put("item"+i, w);
			i++;
		}
		pc.setHistoryPartItems(historyPartItems);
		
		
		if(mRepairHistory.getIdUnplannedStop()!=null)
		{
			PUnplannedStops pUnplannedStops = pUnplannedStopsRepository.findOne(mRepairHistory.getIdUnplannedStop());
			
			pc.setRecoverTime(pUnplannedStops.getOpFt());
			pc.setRepairingTime(pUnplannedStops.getEqFt());
			pc.setResponseTime(pUnplannedStops.getEqSt());
		}
		
	    return pc;
	}
}
