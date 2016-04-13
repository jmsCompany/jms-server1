package com.jms.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.SPo;
import com.jms.domain.db.SPoMaterial;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.store.WSSpo;
import com.jms.domain.ws.store.WSSpoMaterial;
import com.jms.domain.ws.store.WSSpoRemark;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.s.SCompanyCoRepository;
import com.jms.repositories.s.SCurrencyTypeRepository;
import com.jms.repositories.s.SSpoMaterialRepository;
import com.jms.repositories.s.SSpoRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.s.SStermDicRepository;
import com.jms.repositories.s.SStkTypeDicRepository;
import com.jms.web.security.SecurityUtils;


@Service
@Transactional
public class SpoService {

	private static final Logger logger = LogManager.getLogger(SpoService.class
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
	private SpoMaterialService spoMaterialService;
	@Autowired
	private  SSpoMaterialRepository sSpoMaterialRepository;

	

	public Valid saveSpo(WSSpo wsSpo) throws Exception {
		
		SPo spo;
		//create
		if(wsSpo.getIdPo()==null||wsSpo.getIdPo().equals(0l))
		{
			spo=new SPo();
			spo.setDateOrder(new Date());
		}
		//update
		else
		{
			spo = sSpoRepository.findOne(wsSpo.getIdPo());	
		}
	
		spo=toDBSpo(wsSpo,spo);
		SPo sp =sSpoRepository.save(spo);
		//System.out.println("spoid: " +sp.getIdPo() );
		//remove all spo materials
		if(wsSpo.getIdPo()!=null&&!wsSpo.getIdPo().equals(0l))
		{
			sSpoMaterialRepository.deleteBySpoId(wsSpo.getIdPo());
		}
		for(String k: wsSpo.getPoItems().keySet())
		{
			WSSpoMaterial wm =wsSpo.getPoItems().get(k);
			wm.setLine(Long.parseLong(k.substring(4)));
		
			wm.setsPoId(sp.getIdPo());
			spoMaterialService.saveSpoMaterial(wm);
		}
		Valid valid = new Valid();
		valid.setValid(true);
		return valid;
	}
	

	
	
	

	public Valid saveSpoRemark(WSSpoRemark wsSpoRemark) {
		
		SPo spo = sSpoRepository.findOne(wsSpoRemark.getIdPo());	
		spo.setRemark(wsSpoRemark.getRemark());
		
		sSpoRepository.save(spo);
		
		Valid valid = new Valid();
		valid.setValid(true);
		return valid;
	}
	
	
	public Valid deleteSpo(Long spoId) 
	{
		sSpoRepository.delete(spoId);
		Valid valid = new Valid();
		valid.setValid(true);
		return valid;
	}
	
	public WSSpo findSpo(Long spoId) throws Exception 
	{
		SPo spo = sSpoRepository.findOne(spoId);
		return toWSSpo(spo);
	}
	
	
	public List<WSSpo> findSpoList(Long companyId) throws Exception 
	{
		List<WSSpo> ws = new ArrayList<WSSpo>();
		for(SPo s : sSpoRepository.findByCompanyId(companyId))
		{
			ws.add(toWSSpo(s));
		}
		
		return ws;
	}
	public List<WSSelectObj> findSpoListByCodeCo(Long companyId,Long codeCo) throws Exception 
	{
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		for(SPo s : sSpoRepository.findByCompanyIdAndCodeCo(companyId,codeCo))
		{
			WSSelectObj w = new WSSelectObj(s.getIdPo(),s.getCodePo());
			ws.add(w);
		}
		
		return ws;
	}
	
	
	
	private SPo toDBSpo(WSSpo wsSpo,SPo spo) throws Exception
	{
	
		SPo dbSpo = (SPo)BeanUtil.shallowCopy(wsSpo, SPo.class, spo);
		
		if(wsSpo.getFreightTermId()!=null)
		{
			dbSpo.setSTermDicByFreightTerm(sStermDicRepository.findOne(wsSpo.getFreightTermId()));
		}
		if(wsSpo.getPaymentTermId()!=null)
		{
			dbSpo.setSTermDicByPaymentTerm(sStermDicRepository.findOne(wsSpo.getPaymentTermId()));
		}
		if(wsSpo.getsCompanyCoId()!=null)
		{
			dbSpo.setSCompanyCo(sCompanyCoRepository.findOne(wsSpo.getsCompanyCoId()));
		}
		if(wsSpo.getsCurrencyTypeId()!=null)
		{
			dbSpo.setSCurrencyType(sCurrencyTypeRepository.findOne(wsSpo.getsCurrencyTypeId()));
		}
		if(wsSpo.getsStatusId()!=null)
		{
			dbSpo.setSStatusDic(sStatusDicRepository.findOne(wsSpo.getsStatusId()));
		}
		dbSpo.setUsers(securityUtils.getCurrentDBUser());
		dbSpo.setCompany(securityUtils.getCurrentDBUser().getCompany());

		return dbSpo;
	}
	
	protected WSSpo toWSSpo(SPo spo) throws Exception
	{
		WSSpo wsSpo = (WSSpo)BeanUtil.shallowCopy(spo, WSSpo.class, null);
		if(spo.getSCompanyCo()!=null)
		{
			wsSpo.setsCompanyCoId(spo.getSCompanyCo().getId());
			wsSpo.setCodeCo(spo.getSCompanyCo().getCode());
		}
		if(spo.getSCurrencyType()!=null)
		{
			wsSpo.setsCurrencyType(spo.getSCurrencyType().getCurrency());
			wsSpo.setsCurrencyTypeId(spo.getSCurrencyType().getIdCurrencyType());
		}
		if(spo.getSTermDicByFreightTerm()!=null)
		{
			wsSpo.setFreightTerm(spo.getSTermDicByFreightTerm().getName());
			wsSpo.setFreightTermId(spo.getSTermDicByFreightTerm().getId());
		}
		if(spo.getSTermDicByPaymentTerm()!=null)
		{
			wsSpo.setPaymentTerm(spo.getSTermDicByPaymentTerm().getName());
			wsSpo.setPaymentTermId(spo.getSTermDicByPaymentTerm().getId());
		}
		if(spo.getSStatusDic()!=null)
		{
			wsSpo.setsStatus(spo.getSStatusDic().getName());
			wsSpo.setsStatusId(spo.getSStatusDic().getId());
		}
		for(SPoMaterial s: spo.getSPoMaterials())
		{
			wsSpo.getPoItems().put("item"+s.getLine(), spoMaterialService.toWSSpoMaterial(s));
		}
		wsSpo.setUserName(spo.getUsers().getName());
		return wsSpo;
	}


}
