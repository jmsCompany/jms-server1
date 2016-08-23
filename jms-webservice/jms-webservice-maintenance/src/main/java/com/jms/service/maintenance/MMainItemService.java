package com.jms.service.maintenance;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.domain.db.MMachine;
import com.jms.domain.db.MMainItem;
import com.jms.domain.db.MSparePart;
import com.jms.domain.db.SInventory;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.m.WSMSparePart;
import com.jms.domain.ws.m.WSMachine;
import com.jms.domain.ws.m.WSMainItem;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.m.MDeptRepository;
import com.jms.repositories.m.MMachineGroupRepository;
import com.jms.repositories.m.MMachineRepository;
import com.jms.repositories.m.MMainCycleRepository;
import com.jms.repositories.m.MMainItemRepository;
import com.jms.repositories.m.MSparePartRepository;
import com.jms.repositories.m.MStatusDicRepository;
import com.jms.repositories.p.PLineRepository;
import com.jms.repositories.p.PWipRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.repositories.s.SBinRepository;
import com.jms.repositories.s.SInventoryRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class MMainItemService {

	private static final Logger logger = LogManager.getLogger(MMainItemService.class);


	@Autowired
	private MMachineRepository mMachineRepository;
	@Autowired
	private MMainItemRepository mMainItemRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private  MMainCycleRepository mMainCycleRepository;
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
	
	@Autowired private  MDeptRepository mDeptRepository;
	

	
	
	
	@Transactional(readOnly=true)
	public WSMainItem findWSMainItem(Long idMainItem) throws Exception{	
		MMainItem mMainItem = mMainItemRepository.findOne(idMainItem);
		return toWSMainItem(mMainItem);
	}
	public Valid saveWSMainItem(WSMainItem wsMainItem) throws Exception
	{
		Valid  v = new Valid();
		MMainItem mMainItem;
		if(wsMainItem.getIdMainItem()!=null&&!wsMainItem.getIdMainItem().equals(0l))
		{
			mMainItem = mMainItemRepository.findOne(wsMainItem.getIdMainItem());
			
		}
		else
		{
			mMainItem = new MMainItem();
	
		}
		MMainItem dbMMainItem = toDBMMainItem( wsMainItem, mMainItem);
		mMainItemRepository.save(dbMMainItem);
		v.setValid(true);
		return v;
		
	}
	
	
	@Transactional(readOnly=false)
	public Valid deleteWSMainItem(Long idMainItem)
	{
		Valid valid = new Valid();
		mMainItemRepository.delete(idMainItem);
		valid.setValid(true);
		return valid;
	}
	
	protected MMainItem toDBMMainItem(WSMainItem wsMainItem,MMainItem mMainItem) throws Exception
	{
		MMainItem dbMMainItem= (MMainItem)BeanUtil.shallowCopy(wsMainItem, MMainItem.class, mMainItem);
		if(wsMainItem.getIdMachine()!=null)
		{
			dbMMainItem.setMMachine(mMachineRepository.findOne(wsMainItem.getIdMachine()));
		}
		if(wsMainItem.getIdMainCycle()!=null)
		{
			dbMMainItem.setMMainCycle(mMainCycleRepository.findOne(wsMainItem.getIdMainCycle()));
		}
		if(wsMainItem.getIdMDept()!=null)
		{
			dbMMainItem.setMDept(mDeptRepository.findOne(wsMainItem.getIdMDept()));
		}
	    return dbMMainItem;
	}
	public WSMainItem toWSMainItem(MMainItem mMainItem) throws Exception
	{
		WSMainItem pc = (WSMainItem)BeanUtil.shallowCopy(mMainItem, WSMainItem.class, null);
		if(mMainItem.getMMachine()!=null)
		{
			pc.setIdMachine(mMainItem.getMMachine().getIdMachine());
			pc.setMachine(mMainItem.getMMachine().getCode());
		}
		if(mMainItem.getMDept()!=null)
		{
			pc.setIdMDept(mMainItem.getMDept().getIdDept());
			pc.setmDept(mMainItem.getMDept().getDes());
		}
		if(mMainItem.getMMainCycle()!=null)
		{
			pc.setIdMainCycle(mMainItem.getMMainCycle().getIdMainCycle());
			pc.setMainCycle(mMainItem.getMMainCycle().getMainCycle());
			
		}
	    return pc;
	}
}
