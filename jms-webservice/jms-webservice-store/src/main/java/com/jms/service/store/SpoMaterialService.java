package com.jms.service.store;


import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.Config;
import com.jms.domain.db.SBin;
import com.jms.domain.db.SCompanyCo;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SPo;
import com.jms.domain.db.SPoMaterial;
import com.jms.domain.db.SStk;
import com.jms.domain.db.SStkTypeDic;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.store.WSBin;
import com.jms.domain.ws.store.WSCompanyCo;
import com.jms.domain.ws.store.WSMaterial;
import com.jms.domain.ws.store.WSSpo;
import com.jms.domain.ws.store.WSSpoMaterial;
import com.jms.domain.ws.store.WSStk;
import com.jms.domain.ws.store.WSStkType;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.s.SBinRepository;
import com.jms.repositories.s.SCompanyCoRepository;
import com.jms.repositories.s.SCurrencyTypeRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SSpoMaterialRepository;
import com.jms.repositories.s.SSpoRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.s.SStermDicRepository;
import com.jms.repositories.s.SStkRepository;
import com.jms.repositories.s.SStkTypeDicRepository;
import com.jms.repositories.s.SYesOrNoDicRepository;
import com.jms.web.security.SecurityUtils;


@Service
@Transactional
public class SpoMaterialService {

	private static final Logger logger = LogManager.getLogger(SpoMaterialService.class
			.getCanonicalName());
	@Autowired
	private SSpoRepository sSpoRepository;
	@Autowired
	private SStatusDicRepository  sStatusDicRepository;
	@Autowired
	private SStkTypeDicRepository sStkTypeDicRepository;

	@Autowired 
	private  SecurityUtils securityUtils;
	@Autowired 
	private SStermDicRepository sStermDicRepository;
	@Autowired
	private SCompanyCoRepository sCompanyCoRepository;
	@Autowired
	private SCurrencyTypeRepository sCurrencyTypeRepository;
	@Autowired
	private MaterialService materialService;
	@Autowired
	private SSpoMaterialRepository sSpoMaterialRepository;
	@Autowired
	private SMaterialRepository sMaterialRepository;

	

	public Valid saveSpoMaterial(WSSpoMaterial wsSpoMaterial) throws Exception {
		
		SPoMaterial spoMaterial;
		//create
		if(wsSpoMaterial==null||wsSpoMaterial.getIdPoMaterial().equals(0l))
		{
			spoMaterial=new SPoMaterial();
		}
		//update
		else
		{
			spoMaterial = sSpoMaterialRepository.findOne(wsSpoMaterial.getIdPoMaterial());	
		}
		spoMaterial=toDBSpoMaterial(wsSpoMaterial,spoMaterial);
		sSpoMaterialRepository.save(spoMaterial);
		Valid valid = new Valid();
		valid.setValid(true);
		return valid;
	}
	
	
	public List<WSSpoMaterial> findWSSpos(Long companyId) throws Exception
	{
		List<WSSpoMaterial> ws = new ArrayList<WSSpoMaterial>();
		for(SPoMaterial sm: sSpoMaterialRepository.getByCompanyId(companyId))
		{
			ws.add(toWSSpoMaterial(sm));
		}
		
		return ws;
	}
	protected SPoMaterial toDBSpoMaterial(WSSpoMaterial wsSpoMaterial,SPoMaterial spoMaterial) throws Exception
	{
	
		SPoMaterial dbSpoMaterial = (SPoMaterial)BeanUtil.shallowCopy(wsSpoMaterial, SPoMaterial.class, spoMaterial);
		
		if(wsSpoMaterial.getsMaterialId()!=null)
		{
			dbSpoMaterial.setSMaterial(sMaterialRepository.findOne(wsSpoMaterial.getsMaterialId()));
		}
		if(wsSpoMaterial.getsPoId()!=null)
		{
			dbSpoMaterial.setSPo(sSpoRepository.findOne(wsSpoMaterial.getsPoId()));
		}
		if(wsSpoMaterial.getsStatusId()!=null)
		{
			dbSpoMaterial.setSStatusDic(sStatusDicRepository.findOne(wsSpoMaterial.getsStatusId()));
		}
		if(wsSpoMaterial.getsPoId()!=null)
		{
			dbSpoMaterial.setSPo(sSpoRepository.findOne(wsSpoMaterial.getsPoId()));
		}
		dbSpoMaterial.setUPrice(wsSpoMaterial.getUprice());

		return dbSpoMaterial;
	}
	
	public WSSpoMaterial toWSSpoMaterial(SPoMaterial spoMaterial) throws Exception
	{
		WSSpoMaterial wsSpoMaterial = (WSSpoMaterial)BeanUtil.shallowCopy(spoMaterial, WSSpoMaterial.class, null);
		if(spoMaterial.getSMaterial()!=null)
		{
			wsSpoMaterial.setsMaterial(spoMaterial.getSMaterial().getDes());
			wsSpoMaterial.setsMaterialId(spoMaterial.getSMaterial().getIdMaterial());
			wsSpoMaterial.setRev(spoMaterial.getSMaterial().getRev());
			wsSpoMaterial.setDes(spoMaterial.getSMaterial().getDes());
			wsSpoMaterial.setUnit(spoMaterial.getSMaterial().getSUnitDicByUnitPur().getName());
		}
		if(spoMaterial.getSStatusDic()!=null)
		{
			wsSpoMaterial.setsStatusId(spoMaterial.getSStatusDic().getId());
		}
		
		wsSpoMaterial.setCodePo(spoMaterial.getSPo().getCodePo());
		if(spoMaterial.getSPo().getSCompanyCo()!=null)
		{
			wsSpoMaterial.setCodeCo(spoMaterial.getSPo().getSCompanyCo().getCode());
		}
	
		wsSpoMaterial.setDateOrder(spoMaterial.getSPo().getDateOrder());
		wsSpoMaterial.setUsername(spoMaterial.getSPo().getUsers().getName());
		if(spoMaterial.getSPo().getSStatusDic()!=null)
		{
			wsSpoMaterial.setsStatus(spoMaterial.getSPo().getSStatusDic().getName());
		}
		wsSpoMaterial.setsPoId(spoMaterial.getSPo().getIdPo());
		wsSpoMaterial.setUprice(spoMaterial.getUPrice());
		return wsSpoMaterial;
	}

}
