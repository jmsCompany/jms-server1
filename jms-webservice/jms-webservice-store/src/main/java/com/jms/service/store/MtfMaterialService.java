package com.jms.service.store;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.PWoBom;
import com.jms.domain.db.SInventory;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMtf;
import com.jms.domain.db.SMtfMaterial;
import com.jms.domain.db.SPo;
import com.jms.domain.db.SPoMaterial;
import com.jms.domain.db.SSo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.s.WSSMtfMaterial;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.p.PWoBomRepository;
import com.jms.repositories.s.SBinRepository;
import com.jms.repositories.s.SInventoryRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SMtfMaterialRepository;
import com.jms.repositories.s.SMtfRepository;
import com.jms.repositories.s.SSoRepository;
import com.jms.repositories.s.SSpoMaterialRepository;
import com.jms.repositories.s.SSpoRepository;
import com.jms.repositories.s.SStatusDicRepository;



@Service
@Transactional
public class MtfMaterialService {

	private static final Logger logger = LogManager.getLogger(MtfMaterialService.class
			.getCanonicalName());

	@Autowired
	private SStatusDicRepository  sStatusDicRepository;
	@Autowired
	private SMtfMaterialRepository sMtfMaterialRepository;

	@Autowired
	private SSpoMaterialRepository sSpoMaterialRepository;
	@Autowired
	private SMaterialRepository sMaterialRepository;
	@Autowired
	private SBinRepository sBinRepository;
	
	@Autowired
	private SSoRepository sSoRepository;
	
	@Autowired
	private SSpoRepository sSpoRepository;
	
	
	@Autowired
	private SMtfRepository sMtfRepository;
	
	
	@Autowired
	private PWoBomRepository pWoBomRepository;
	@Autowired
	private SInventoryRepository sInventoryRepository;


	public Valid saveMtfMaterial(WSSMtfMaterial wsSMtfMaterial) throws Exception {
		
		SMtfMaterial sMtfMaterial;
		//create
		if(wsSMtfMaterial.getIdMtfMaterial()==null||wsSMtfMaterial.getIdMtfMaterial().equals(0l))
		{
			sMtfMaterial=new SMtfMaterial();
		}
		//update
		else
		{
			sMtfMaterial = sMtfMaterialRepository.findOne(wsSMtfMaterial.getIdMtfMaterial());	
		}
		sMtfMaterial=toDBMtfMaterial(wsSMtfMaterial,sMtfMaterial);
		
		sMtfMaterialRepository.save(sMtfMaterial);
		Valid valid = new Valid();
		valid.setValid(true);
		return valid;
	}
	

	public List<WSSMtfMaterial> findWSSMtfMaterial(Long companyId,Long typeId) throws Exception
	{
		List<WSSMtfMaterial> ws = new ArrayList<WSSMtfMaterial>();
		List<SMtfMaterial> sms;
		if(typeId!=null)
		{
			sms=sMtfMaterialRepository.getByCompanyIdAndTypeId(companyId, typeId);
		}
		else
		{
			sms=sMtfMaterialRepository.getByCompanyId(companyId);
		}
		for(SMtfMaterial sm: sms)
		{
			ws.add(toWSSMtfMaterial(sm));
		}
		
		return ws;
	}
	

	public List<WSSMtfMaterial> findWSSMtfMaterial(Long companyId,Long typeId,Long materialId) throws Exception
	{
		List<WSSMtfMaterial> ws = new ArrayList<WSSMtfMaterial>();
		List<SMtfMaterial> sms;
		if(typeId!=null)
		{
			if(materialId!=null)
			{
				sms=sMtfMaterialRepository.getByCompanyIdAndTypeIdAndMaterialId(companyId, typeId,materialId);
			}
			else
			{
				sms=sMtfMaterialRepository.getByCompanyIdAndTypeId(companyId, typeId);
			}
			
		}
		else
		{
			if(materialId!=null)
			{
				sms=sMtfMaterialRepository.getByCompanyIdAndMaterialId(companyId,materialId);
			}
			else
			{
				sms=sMtfMaterialRepository.getByCompanyId(companyId);
			}
			
		}
		for(SMtfMaterial sm: sms)
		{
			ws.add(toWSSMtfMaterial(sm));
		}
		
		return ws;
	}
	
	
	public List<WSSMtfMaterial> findWSSMtfMaterialBySpoId(Long spoId,Long stkId) throws Exception
	{
		//SPo spo = sSpoRepository.findOne(spoId);
		List<WSSMtfMaterial> ws = new ArrayList<WSSMtfMaterial>();
		List<SPoMaterial> pms = sSpoMaterialRepository.getReceivedBySpoId(spoId);
		if(pms!=null&&!pms.isEmpty())
		{
			for(SPoMaterial pm:pms)
			{
				SMaterial material = pm.getSMaterial();
				Long materialId = material.getIdMaterial();
				Long received = pm.getQtyReceived();
				List<SMtfMaterial> sms =sMtfMaterialRepository.getBySpoIdAndMaterialId(spoId, materialId);
				Map<String,String> lotMap = new HashMap<String,String>();
				for(SMtfMaterial sm: sms)
				{
					String lotNo = sm.getLotNo();
					if(!lotMap.containsKey(lotNo))
					{
						lotMap.put(lotNo, lotNo);
					}
				}
				
				for(String lotNo:lotMap.keySet())
				{
					List<SInventory> sInventorys = sInventoryRepository.findBinsByMaterialIdAndLotNoAndStkId(materialId, lotNo, stkId);
					if(sInventorys!=null&&!sInventorys.isEmpty())
					{
						for(SInventory s: sInventorys)
						{
							WSSMtfMaterial w = new WSSMtfMaterial();
							w.setMaterialId(materialId);
							w.setMaterialDes(material.getDes());
							w.setMaterialPno(material.getPno());
							w.setMaterialRev(material.getRev());
							if(material.getSUnitDicByUnitPur()!=null)
							w.setMarterialUnit(material.getSUnitDicByUnitPur().getName());
							w.setDeliveryDate(pm.getDeliveryDate());
							w.setQtyPo(pm.getQtyPo());
							w.setLotNo(lotNo);
							w.setFromBin(s.getSBin().getBin());
							w.setFromBinId(s.getSBin().getIdBin());
							w.setRemark(pm.getRemark());
							w.setQtyStored(s.getQty());
							w.setPoMaterialId(pm.getIdPoMaterial());
							w.setQtyReceived(pm.getQtyReceived());
							ws.add(w);
						}
				    }
				}
				}
			}
			

		return ws;
	}
	
	
	
	public SMtfMaterial toDBMtfMaterial(WSSMtfMaterial wsSMtfMaterial,SMtfMaterial sMtfMaterial) throws Exception
	{
	
		SMtfMaterial dbSMtfMaterial = (SMtfMaterial)BeanUtil.shallowCopy(wsSMtfMaterial, SMtfMaterial.class, sMtfMaterial);
		
		if(wsSMtfMaterial.getFromBinId()!=null)
		{
			dbSMtfMaterial.setSBinByFromBin(sBinRepository.findOne(wsSMtfMaterial.getFromBinId()));
		
		}
		if(wsSMtfMaterial.getToBinId()!=null)
		{
			dbSMtfMaterial.setSBinByToBin(sBinRepository.findOne(wsSMtfMaterial.getToBinId()));
		}
		if(wsSMtfMaterial.getMaterialId()!=null)
		{
			dbSMtfMaterial.setSMaterial(sMaterialRepository.findOne(wsSMtfMaterial.getMaterialId()));
		}
		if(wsSMtfMaterial.getIdMt()!=null)
		{
			dbSMtfMaterial.setSMtf(sMtfRepository.findOne(wsSMtfMaterial.getIdMt()));
		}
		if(wsSMtfMaterial.getPoMaterialId()!=null)
		{
			 SPoMaterial sm=sSpoMaterialRepository.findOne(wsSMtfMaterial.getPoMaterialId());
			 dbSMtfMaterial.setSPoMaterial(sm);
			 dbSMtfMaterial.setSMaterial(sm.getSMaterial());
		}
		if(wsSMtfMaterial.getPwoBomId()!=null)
		{
			PWoBom pWoBom = pWoBomRepository.findOne(wsSMtfMaterial.getPwoBomId());
			dbSMtfMaterial.setPWoBom(pWoBom);
		}
		if(wsSMtfMaterial.getSoId()!=null)
		{
			SSo so =sSoRepository.findOne(wsSMtfMaterial.getSoId());
			dbSMtfMaterial.setSSo(so);
			dbSMtfMaterial.setSMaterial(so.getSMaterial());
		}
		if(wsSMtfMaterial.getStatusId()!=null)
		{
			dbSMtfMaterial.setSStatusDic(sStatusDicRepository.findOne(wsSMtfMaterial.getStatusId()));
		}
		dbSMtfMaterial.setUQty(wsSMtfMaterial.getUqty());
		if(wsSMtfMaterial.getQtyChecked()!=null)
		{
			dbSMtfMaterial.setQty3417(wsSMtfMaterial.getQtyChecked());
		}
		dbSMtfMaterial.setQty(wsSMtfMaterial.getQty());
		return dbSMtfMaterial;
	}
	
	public WSSMtfMaterial toWSSMtfMaterial(SMtfMaterial sMtfMaterial) throws Exception
	{
		WSSMtfMaterial wsSMtfMaterial = (WSSMtfMaterial)BeanUtil.shallowCopy(sMtfMaterial, WSSMtfMaterial.class, null);
		if(sMtfMaterial.getSMtf().getUsersByEmpMt()!=null)
		wsSMtfMaterial.setEmpMtUser(sMtfMaterial.getSMtf().getUsersByEmpMt().getName());
		if(sMtfMaterial.getPWoBom()!=null)
		{
			wsSMtfMaterial.setPwoBom(sMtfMaterial.getPWoBom().getPWo().getWoNo());
			wsSMtfMaterial.setPwoBomId(sMtfMaterial.getPWoBom().getIdWoBom());
		}
		if(sMtfMaterial.getSBinByFromBin()!=null)
		{
			wsSMtfMaterial.setFromBin(sMtfMaterial.getSBinByFromBin().getBin());
			wsSMtfMaterial.setFromBinId(sMtfMaterial.getSBinByFromBin().getIdBin());
		}
		if(sMtfMaterial.getSBinByToBin()!=null)
		{
			wsSMtfMaterial.setToBin(sMtfMaterial.getSBinByToBin().getBin());
			wsSMtfMaterial.setToBinId(sMtfMaterial.getSBinByToBin().getIdBin());
		}
//		if(sMtfMaterial.getSMtf().getSMtfTypeDic().getIdMtfType())
//		{
//			
//		}
	
		if(sMtfMaterial.getSMtf()!=null)
		{
			SMtf sMtf = sMtfMaterial.getSMtf();
			wsSMtfMaterial.setCreationTime(sMtf.getCreationTime());
			if(sMtf.getSMtfTypeDic()!=null)
			{
				wsSMtfMaterial.setType(sMtf.getSMtfTypeDic().getName());
				wsSMtfMaterial.setTypeId(sMtf.getSMtfTypeDic().getIdMtfType());
				
			}
			if(sMtf.getSStatusDic()!=null)
			{
				wsSMtfMaterial.setStatus(sMtf.getSStatusDic().getName());
				wsSMtfMaterial.setStatusId(sMtf.getSStatusDic().getId());
			}
			if(sMtf.getSStkByFromStk()!=null)
			{
				wsSMtfMaterial.setFromStk(sMtf.getSStkByFromStk().getStkName());
				wsSMtfMaterial.setFromStkId(sMtf.getSStkByFromStk().getId());
			}
			if(sMtf.getSStkByToStk()!=null)
			{
				wsSMtfMaterial.setToStk(sMtf.getSStkByToStk().getStkName());
				wsSMtfMaterial.setToStkId(sMtf.getSStkByToStk().getId());
			}
			if(sMtf.getUsersByEmpMt()!=null)
			{
				wsSMtfMaterial.setEmpMtUser(sMtf.getUsersByEmpMt().getName());
				wsSMtfMaterial.setEmpMtUserId(sMtf.getUsersByEmpMt().getIdUser());
			}
			if(sMtf.getUsersByRecMt()!=null)
			{
				wsSMtfMaterial.setRecMtUser(sMtf.getUsersByRecMt().getName());
				wsSMtfMaterial.setRecMtUserId(sMtf.getUsersByRecMt().getIdUser());
			}
			wsSMtfMaterial.setIdMt(sMtf.getIdMt());
			wsSMtfMaterial.setMtNo(sMtf.getMtNo());
			
		}
		if(sMtfMaterial.getSPoMaterial()!=null)
		{
			wsSMtfMaterial.setPoMaterialId(sMtfMaterial.getSPoMaterial().getIdPoMaterial());
			wsSMtfMaterial.setCodeCo(sMtfMaterial.getSPoMaterial().getSPo().getSCompanyCo().getShortName());
			//logger.debug("set codeCo from po: " +sMtfMaterial.getSPoMaterial().getSPo().getSCompanyCo().getName());
			wsSMtfMaterial.setCodePo(sMtfMaterial.getSPoMaterial().getSPo().getCodePo());
		//	logger.debug("set codePo: " +sMtfMaterial.getSPoMaterial().getSPo().getCodePo());
			wsSMtfMaterial.setDeliveryDate(sMtfMaterial.getSPoMaterial().getDeliveryDate());
			wsSMtfMaterial.setQtyPo(sMtfMaterial.getSPoMaterial().getQtyPo());
			wsSMtfMaterial.setQtyReceived(sMtfMaterial.getSPoMaterial().getQtyReceived());
			wsSMtfMaterial.setRemark(sMtfMaterial.getSPoMaterial().getRemark());
			if(sMtfMaterial.getSPoMaterial().getSMaterial()!=null)
			{
				SMaterial s = sMtfMaterial.getSPoMaterial().getSMaterial();
				wsSMtfMaterial.setMaterialId(s.getIdMaterial());
				wsSMtfMaterial.setMaterialPno(s.getPno());
				wsSMtfMaterial.setMaterialRev(s.getRev());
				wsSMtfMaterial.setMaterialDes(s.getDes());
				if(s.getSUnitDicByUnitInf()!=null)
				wsSMtfMaterial.setMarterialUnit(s.getSUnitDicByUnitInf().getName());
				
			}
		}
		if(sMtfMaterial.getSSo()!=null)
		{
			wsSMtfMaterial.setSoCode(sMtfMaterial.getSSo().getCodeSo());
			wsSMtfMaterial.setSoId(sMtfMaterial.getSSo().getIdSo());
			if(sMtfMaterial.getSSo().getSCompanyCo()!=null)
			{
				wsSMtfMaterial.setCodeCo(sMtfMaterial.getSSo().getSCompanyCo().getShortName());
				//logger.debug("set codeCo from so: " +sMtfMaterial.getSSo().getSCompanyCo().getName());
			}
		
			if(sMtfMaterial.getSSo().getSMaterial()!=null)
			{
				SMaterial s = sMtfMaterial.getSSo().getSMaterial();
				wsSMtfMaterial.setMaterialId(s.getIdMaterial());
				wsSMtfMaterial.setMaterialPno(s.getPno());
				wsSMtfMaterial.setMaterialRev(s.getRev());
				wsSMtfMaterial.setMaterialDes(s.getDes());
				if(s.getSUnitDicByUnitInf()!=null)
				wsSMtfMaterial.setMarterialUnit(s.getSUnitDicByUnitInf().getName());
				wsSMtfMaterial.setDeliveryDate(sMtfMaterial.getSSo().getDeliveryDate());
				
			}
		}

		if(sMtfMaterial.getSStatusDic()!=null)
		{
			wsSMtfMaterial.setStatus(sMtfMaterial.getSStatusDic().getName());
			wsSMtfMaterial.setStatusId(sMtfMaterial.getSStatusDic().getId());
		}
		wsSMtfMaterial.setUqty(sMtfMaterial.getUQty());
		//wsSMtfMaterial.setPoMaterialId(poMaterialId);
		

		if(sMtfMaterial.getSMaterial()!=null)
		{
			wsSMtfMaterial.setMaterialId(sMtfMaterial.getSMaterial().getIdMaterial());
			wsSMtfMaterial.setMaterialPno(sMtfMaterial.getSMaterial().getPno());
			wsSMtfMaterial.setMaterialRev(sMtfMaterial.getSMaterial().getRev());
			wsSMtfMaterial.setMaterialDes(sMtfMaterial.getSMaterial().getDes());
			if(sMtfMaterial.getSMaterial().getSUnitDicByUnitInf()!=null)
			wsSMtfMaterial.setMarterialUnit(sMtfMaterial.getSMaterial().getSUnitDicByUnitInf().getName());
			
		}

		return wsSMtfMaterial;
	}


}
