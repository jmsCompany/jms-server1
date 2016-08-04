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
import com.jms.domain.db.MSparePart;
import com.jms.domain.db.SInventory;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.m.WSMSparePart;
import com.jms.domain.ws.m.WSMachine;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
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
	

	
	
//	
//	@Transactional(readOnly=true)
//	public WSMSparePart findWSMSparePart(Long idPart) throws Exception{	
//		MSparePart mSparePart = mSparePartRepository.findOne(idPart);
//		return toWSMSparePart(mSparePart);
//	}
//	public Valid saveWSMSparePart(WSMSparePart wsMSparePart) throws Exception
//	{
//		Valid  v = new Valid();
//		MSparePart mSparePart;
//		if(wsMSparePart.getIdPart()!=null&&!wsMSparePart.getIdPart().equals(0l))
//		{
//			mSparePart = mSparePartRepository.findOne(wsMSparePart.getIdPart());
//			MSparePart m = mSparePartRepository.findByMaterialIdAndMachineId(wsMSparePart.getIdMaterial(), wsMSparePart.getIdMachine());
//			if(m!=null&&!m.getIdPart().equals(wsMSparePart.getIdPart()))
//			{
//				v.setValid(false);
//				v.setMsg("该配件已经存在！");
//				return v;
//			}
//			
//		}
//		else
//		{
//			mSparePart = new MSparePart();
//			MSparePart m = mSparePartRepository.findByMaterialIdAndMachineId(wsMSparePart.getIdMaterial(), wsMSparePart.getIdMachine());
//			if(m!=null)
//			{
//				v.setValid(false);
//				v.setMsg("该配件已经存在！");
//				return v;
//			}
//	
//		}
//		MSparePart dbMSparePart = toDBMSparePart( wsMSparePart, mSparePart);
//		dbMSparePart = mSparePartRepository.save(dbMSparePart);
//		v.setValid(true);
//		return v;
//		
//	}
//	
//	
//	@Transactional(readOnly=false)
//	public Valid deleteMSparePart(Long idPart)
//	{
//		Valid valid = new Valid();
//		mSparePartRepository.delete(idPart);
//		valid.setValid(true);
//		return valid;
//	}
//	
//	protected MSparePart toDBMSparePart(WSMSparePart wsMSparePart,MSparePart mSparePart) throws Exception
//	{
//		MSparePart dbMSparePart= (MSparePart)BeanUtil.shallowCopy(wsMSparePart, MSparePart.class, mSparePart);
//		if(wsMSparePart.getIdMachine()!=null)
//		{
//			mSparePart.setMMachine(mMachineRepository.findOne(wsMSparePart.getIdMachine()));
//		}
//		if(wsMSparePart.getIdMaterial()!=null)
//		{
//			mSparePart.setSMaterial(sMaterialRepository.findOne(wsMSparePart.getIdMaterial()));
//		}
//		if(wsMSparePart.getIdStatus()!=null)
//		{
//			mSparePart.setMStatusDic(mStatusDicRepository.findOne(wsMSparePart.getIdStatus()));
//		}
//	    return dbMSparePart;
//	}
//	public WSMSparePart toWSMSparePart(MSparePart mSparePart) throws Exception
//	{
//		WSMSparePart pc = (WSMSparePart)BeanUtil.shallowCopy(mSparePart, WSMSparePart.class, null);
//		if(mSparePart.getMMachine()!=null)
//		{
//			pc.setIdMachine(mSparePart.getMMachine().getIdMachine());
//			pc.setMachine(mSparePart.getMMachine().getCode());
//		}
//		if(mSparePart.getMStatusDic()!=null)
//		{
//			pc.setStatus(mSparePart.getMStatusDic().getName());
//			pc.setIdStatus(mSparePart.getMStatusDic().getIdMstatusDic());
//		}
//		if(mSparePart.getSMaterial()!=null)
//		{
//			pc.setIdMaterial(mSparePart.getSMaterial().getIdMaterial());
//			pc.setMaterial(mSparePart.getSMaterial().getPno()+"_"+mSparePart.getSMaterial().getRev()+"_"+mSparePart.getSMaterial().getDes());
//			if(mSparePart.getSMaterial().getSafetyInv()!=null)
//			{
//				pc.setSafetyQty(mSparePart.getSMaterial().getSafetyInv());
//			}
//			pc.setRemark(mSparePart.getSMaterial().getRemark());
//			long qty=0;
//			for(SInventory s:sInventoryRepository.findByMaterialId(mSparePart.getSMaterial().getIdMaterial()))
//			{
//				if(s.getQty()>0)
//				{
//					qty = qty + s.getQty();
//				}
//						
//			}
//			pc.setQty(qty);
//			
//			pc.setOutgoing(0l);
//			pc.setIncoming(0l);
//			
//		}
//	    return pc;
//	}
}
