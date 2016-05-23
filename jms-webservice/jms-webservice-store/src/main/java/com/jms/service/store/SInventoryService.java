package com.jms.service.store;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.SInventory;
import com.jms.domain.ws.store.WSInventory;
import com.jms.domain.ws.store.WSInventoryInfo;
import com.jms.repositories.s.SBinRepository;
import com.jms.repositories.s.SInventoryRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.s.SStkRepository;
import com.jms.repositories.s.SStkTypeDicRepository;
import com.jms.repositories.s.SYesOrNoDicRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class SInventoryService {

	private static final Logger logger = LogManager.getLogger(SInventoryService.class.getCanonicalName());
	
	@Autowired
	private SInventoryRepository sInventoryRepository;
	
	@Autowired
	private SStkRepository sStkRepository;
	@Autowired
	private SStatusDicRepository sStatusDicRepository;
	@Autowired
	private SStkTypeDicRepository sStkTypeDicRepository;

	@Autowired
	private SecurityUtils securityUtils;

	@Autowired
	private SYesOrNoDicRepository sYesOrNoDicRepository;

	@Autowired
	private SBinRepository sBinRepository;

	@Transactional(readOnly=true)
	public List<WSInventoryInfo> findInventorySummaryByMaterialAndStk(Long idMaterial,Long stkId)
	{
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<SInventory> ls;
		if(idMaterial==null)
		{
			if(stkId==null)
			{
				ls = new ArrayList<SInventory>();
			}
			else
			{
				ls = sInventoryRepository.findInventorySummaryByStk(companyId, stkId);
			}
			
		}
		else
		{
			if(stkId==null)
			{
				ls = sInventoryRepository.findInventorySummaryByMaterial(idMaterial, companyId);
			}
			else
			{
				ls = sInventoryRepository.findInventorySummaryByMaterialAndStk(idMaterial, companyId, stkId);
			}
		}
		List<WSInventoryInfo> infos = new ArrayList<WSInventoryInfo>();
		Long currentStkId=0l,currentMaterialId=0l,currentQty=0l;
		SInventory before=null;
		int num=0;
	    for(SInventory s: ls)
	    {
	    	Long sId = s.getSBin().getSStk().getId();
	    	Long mId = s.getSMaterial().getIdMaterial();
	    	if(sId.equals(currentStkId)&&mId.equals(currentMaterialId))
	    	{
	    		currentQty =currentQty + s.getQty();
	    	}
	    	else
	    	{
	    		currentStkId = sId;
	    		currentMaterialId = mId;
	    	//	currentQty=s.getQty();
	    		if(!currentQty.equals(0l))
	    		{
	    			WSInventoryInfo i = new WSInventoryInfo();
	    			i.setDes(before.getSMaterial().getDes());
	    			i.setPno(before.getSMaterial().getPno());
	    			i.setRev(before.getSMaterial().getRev());
	    			i.setIdMaterial(before.getSMaterial().getIdMaterial());
	    			i.setStkId(before.getSBin().getSStk().getId());
	    			i.setStkName(before.getSBin().getSStk().getStkName());
	    			i.setQty(currentQty);
	    			infos.add(i);
	    		}
	    		currentQty = s.getQty();
	    	}
	    	
	    	before = s;
	    	num++;
	    	if(num==ls.size())
	    	{
	    		WSInventoryInfo i = new WSInventoryInfo();
    			i.setDes(before.getSMaterial().getDes());
    			i.setPno(before.getSMaterial().getPno());
    			i.setRev(before.getSMaterial().getRev());
    			i.setIdMaterial(before.getSMaterial().getIdMaterial());
    			i.setStkId(before.getSBin().getSStk().getId());
    			i.setStkName(before.getSBin().getSStk().getStkName());
    			i.setQty(currentQty);
    			infos.add(i);
	    	}
	    }
	    return infos;
	}
	
	
	
	
	
	
	
	@Transactional(readOnly=true)
	public List<WSInventory> findInventoryDetailByMaterialAndStk(Long idMaterial,Long stkId)
	{
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<SInventory> ls;
		if(idMaterial==null)
		{
			if(stkId==null)
			{
				ls = new ArrayList<SInventory>();
			}
			else
			{
				ls = sInventoryRepository.findInventorySummaryByStk(companyId, stkId);
			}
			
		}
		else
		{
			if(stkId==null)
			{
				ls = sInventoryRepository.findInventorySummaryByMaterial(idMaterial, companyId);
			}
			else
			{
				ls = sInventoryRepository.findInventorySummaryByMaterialAndStk(idMaterial, companyId, stkId);
			}
		}
		List<WSInventory> infos = new ArrayList<WSInventory>();
	
	    for(SInventory s: ls)
	    {
	    	
	    			WSInventory i = new WSInventory();
	    			i.setDes(s.getSMaterial().getDes());
	    			i.setPno(s.getSMaterial().getPno());
	    			i.setRev(s.getSMaterial().getRev());
	    			i.setIdMaterial(s.getSMaterial().getIdMaterial());
	    			i.setStkId(s.getSBin().getSStk().getId());
	    			i.setStkName(s.getSBin().getSStk().getStkName());
	    			i.setBinName(s.getSBin().getBin());
	    			i.setBinId(s.getSBin().getIdBin());
	    			i.setQty(s.getQty());
	    			i.setBox(s.getBox());
	    			i.setLotNo(s.getLotNo());
	    			infos.add(i);
	    	
	    	
	    }
	    return infos;
	}
  
}
