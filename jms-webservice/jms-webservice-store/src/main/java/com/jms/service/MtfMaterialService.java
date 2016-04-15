package com.jms.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMtf;
import com.jms.domain.db.SMtfMaterial;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.store.WSSMtfMaterial;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.p.PWoBomRepository;
import com.jms.repositories.s.SBinRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SMtfMaterialRepository;
import com.jms.repositories.s.SMtfRepository;
import com.jms.repositories.s.SSoRepository;
import com.jms.repositories.s.SSpoMaterialRepository;
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
	private SMtfRepository sMtfRepository;
	
	
	@Autowired
	private PWoBomRepository pWoBomRepository;


	

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
		for(SMtfMaterial sm: sMtfMaterialRepository.getByCompanyIdAndTypeId(companyId, typeId))
		{
			ws.add(toWSSMtfMaterial(sm));
		}
		
		return ws;
	}
	
	protected SMtfMaterial toDBMtfMaterial(WSSMtfMaterial wsSMtfMaterial,SMtfMaterial sMtfMaterial) throws Exception
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
			dbSMtfMaterial.setSPoMaterial(sSpoMaterialRepository.findOne(wsSMtfMaterial.getPoMaterialId()));
		}
		if(wsSMtfMaterial.getPwoBomId()!=null)
		{
			dbSMtfMaterial.setPWoBom(pWoBomRepository.findOne(wsSMtfMaterial.getPwoBomId()));
		}
		if(wsSMtfMaterial.getSoId()!=null)
		{
			dbSMtfMaterial.setSSo(sSoRepository.findOne(wsSMtfMaterial.getSoId()));
		}
		if(wsSMtfMaterial.getStatusId()!=null)
		{
			dbSMtfMaterial.setSStatusDic(sStatusDicRepository.findOne(wsSMtfMaterial.getStatusId()));
		}
		dbSMtfMaterial.setUQty(wsSMtfMaterial.getUqty());
		

		return dbSMtfMaterial;
	}
	
	protected WSSMtfMaterial toWSSMtfMaterial(SMtfMaterial sMtfMaterial) throws Exception
	{
		WSSMtfMaterial wsSMtfMaterial = (WSSMtfMaterial)BeanUtil.shallowCopy(sMtfMaterial, WSSMtfMaterial.class, null);
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
		if(sMtfMaterial.getSMaterial()!=null)
		{
			wsSMtfMaterial.setMaterialId(sMtfMaterial.getSMaterial().getIdMaterial());
			wsSMtfMaterial.setMaterialPno(sMtfMaterial.getSMaterial().getPno());
			wsSMtfMaterial.setMaterialRev(sMtfMaterial.getSMaterial().getRev());
			wsSMtfMaterial.setMaterialDes(sMtfMaterial.getSMaterial().getDes());
			if(sMtfMaterial.getSMaterial().getSUnitDicByUnitInf()!=null)
			wsSMtfMaterial.setMarterialUnit(sMtfMaterial.getSMaterial().getSUnitDicByUnitInf().getName());
			
		}
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
				wsSMtfMaterial.setFromStk(sMtf.getSStkByFromStk().getDes());
				wsSMtfMaterial.setFromStkId(sMtf.getSStkByFromStk().getId());
			}
			if(sMtf.getSStkByToStk()!=null)
			{
				wsSMtfMaterial.setToStk(sMtf.getSStkByToStk().getDes());
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
			
		}
		if(sMtfMaterial.getSPoMaterial()!=null)
		{
			wsSMtfMaterial.setPoMaterialId(sMtfMaterial.getSPoMaterial().getIdPoMaterial());
			wsSMtfMaterial.setCodeCo(sMtfMaterial.getSPoMaterial().getSPo().getSCompanyCo().getCode());
			wsSMtfMaterial.setCodePo(sMtfMaterial.getSPoMaterial().getSPo().getCodePo());
			wsSMtfMaterial.setDeliveryDate(sMtfMaterial.getSPoMaterial().getDeliveryDate());
		}
		if(sMtfMaterial.getSSo()!=null)
		{
			wsSMtfMaterial.setSoCode(sMtfMaterial.getSSo().getCodeSo());
			wsSMtfMaterial.setSoId(sMtfMaterial.getSSo().getIdSo());
			wsSMtfMaterial.setCodeCo(sMtfMaterial.getSSo().getSCompanyCo().getCode());
		}

		if(sMtfMaterial.getSStatusDic()!=null)
		{
			wsSMtfMaterial.setStatus(sMtfMaterial.getSStatusDic().getName());
			wsSMtfMaterial.setStatusId(sMtfMaterial.getSStatusDic().getId());
		}
		wsSMtfMaterial.setUqty(sMtfMaterial.getUQty());
		
		
	

		return wsSMtfMaterial;
	}


}
