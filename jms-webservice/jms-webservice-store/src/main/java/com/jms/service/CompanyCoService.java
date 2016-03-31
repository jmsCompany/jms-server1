package com.jms.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.SCompanyCo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.store.WSCompanyCo;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.s.SCompanyCoRepository;
import com.jms.repositories.s.SCountryDicRepository;
import com.jms.repositories.s.SLevelDicRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.s.SStermDicRepository;
import com.jms.repositories.s.STypeDicRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class CompanyCoService {

	private static final Logger logger = LogManager.getLogger(CompanyCoService.class
			.getCanonicalName());
	@Autowired
	private SCompanyCoRepository sCompanyCoRepository;

	@Autowired 
	private  SecurityUtils securityUtils;
	@Autowired 
	private SCountryDicRepository sCountryDicRepository;
	@Autowired 
	private SLevelDicRepository sLevelDicRepository;
	@Autowired 
	private SStatusDicRepository sStatusDicRepository;
	@Autowired 
	private SStermDicRepository sStermDicRepository;
	@Autowired 
	private STypeDicRepository sTypeDicRepository;
	
	
	//to be modified
	@Transactional(readOnly=true)
	public List<WSCompanyCo> getCoCompanies(Long idCompany) {
		List<SCompanyCo> coCompanies = sCompanyCoRepository.findAll();
		List<WSCompanyCo> wsSCompanyCos = new ArrayList<WSCompanyCo>();
		for(SCompanyCo dbc:coCompanies)
		{
			wsSCompanyCos.add(toWSCompanyCo(dbc));
		}
		
		return wsSCompanyCos;
		
	}

	
	@Transactional(readOnly=false)
	public WSCompanyCo saveWSCompanyCo(WSCompanyCo wsCompanyCo) throws Exception {
		SCompanyCo companyCo;
		if(wsCompanyCo.getId()!=null&&!wsCompanyCo.getId().equals(0l))
		{
			companyCo = sCompanyCoRepository.findOne(wsCompanyCo.getId());
		}
		else
		{
			companyCo = new SCompanyCo();
		}
		SCompanyCo dbCompanyCo = toDBCompanyCo(wsCompanyCo,companyCo);
		sCompanyCoRepository.save(dbCompanyCo);
		wsCompanyCo.setId(dbCompanyCo.getId());
		return wsCompanyCo;		
		
	}
	
	@Transactional(readOnly=false)
	public Valid deleteCompanyCo(Long companyCoId)
	{
		Valid valid = new Valid();
		SCompanyCo dbCompanyCo = sCompanyCoRepository.findOne(companyCoId);
		if(!dbCompanyCo.getSMtfResiduals().isEmpty()||!dbCompanyCo.getSPos().isEmpty()||!dbCompanyCo.getSSos().isEmpty())
		{
			valid.setValid(false);
		}
		else
		{
			sCompanyCoRepository.delete(companyCoId);
			valid.setValid(true);
		}
		
		return valid;
	}
	
	
	@Transactional(readOnly=true)
	public WSCompanyCo findCompanyCo(Long companyCoId)
	{	
		SCompanyCo mc = sCompanyCoRepository.findOne(companyCoId);
		return  toWSCompanyCo(mc);
		
	}

	
	private SCompanyCo toDBCompanyCo(WSCompanyCo wsCompanyCo,SCompanyCo companyCo) throws Exception
	{
	
		SCompanyCo dbCompanyCo = (SCompanyCo)BeanUtil.shallowCopy(wsCompanyCo, SCompanyCo.class, companyCo);
		
		dbCompanyCo.setRemark(wsCompanyCo.getRemark());
		dbCompanyCo.setShortName(wsCompanyCo.getShortName());
		if(wsCompanyCo.getCountryId()!=null)
		{
			dbCompanyCo.setSCountryDic(sCountryDicRepository.findOne(wsCompanyCo.getCountryId()));
		}
		if(wsCompanyCo.getLvlId()!=null)
		{
			dbCompanyCo.setSLevelDic(sLevelDicRepository.findOne(wsCompanyCo.getLvlId()));
		}
		if(wsCompanyCo.getStatusId()!=null)
		{
			dbCompanyCo.setSStatusDic(sStatusDicRepository.findOne(wsCompanyCo.getStatusId()));
		}
		if(wsCompanyCo.getFreightTermId()!=null)
		{
			dbCompanyCo.setSTermDicByFreightTerm(sStermDicRepository.findOne(wsCompanyCo.getFreightTermId()));
		}
		if(wsCompanyCo.getPaymentTermId()!=null)
		{
			dbCompanyCo.setSTermDicByPaymentTerm(sStermDicRepository.findOne(wsCompanyCo.getPaymentTermId()));
		}
		if(wsCompanyCo.getTypeId()!=null)
		{
			dbCompanyCo.setSTypeDic(sTypeDicRepository.findOne(wsCompanyCo.getTypeId()));
		}
		

		return dbCompanyCo;
	}
	
	private WSCompanyCo toWSCompanyCo(SCompanyCo dbc)
	{
		WSCompanyCo wsCom = new WSCompanyCo();
		if(dbc==null)
			return wsCom;

	    wsCom = new WSCompanyCo();
		wsCom.setAddressAct(dbc.getAddressAct());
		wsCom.setAddressReg(dbc.getAddressReg());
		wsCom.setArtificialPerson(dbc.getArtificialPerson());
		wsCom.setAuditBy("");
		wsCom.setAutoRemark(dbc.getAutoRemark());
		wsCom.setBank(dbc.getBank());
		wsCom.setBankAccNo(dbc.getBankAccNo());
		wsCom.setCode(dbc.getCode());
		if(dbc.getSCountryDic()!=null)
		{
		    wsCom.setCountry(dbc.getSCountryDic().getName());
		    wsCom.setCountryId(dbc.getSCountryDic().getId());
		}
		wsCom.setFax(dbc.getFax());
		if(dbc.getSTermDicByFreightTerm()!=null)
		{
		   wsCom.setFreightTerm(dbc.getSTermDicByFreightTerm().getName());
		   wsCom.setFreightTermId(dbc.getSTermDicByFreightTerm().getId());
		}
		wsCom.setId(dbc.getId());
		if(dbc.getSLevelDic()!=null)
		{
			wsCom.setLvl(dbc.getSLevelDic().getName());
			wsCom.setLvlId(dbc.getSLevelDic().getId());
		}
	
		wsCom.setName(dbc.getName());
		if(dbc.getSTermDicByPaymentTerm()!=null)
		{
		   wsCom.setPaymentTermId(dbc.getSTermDicByPaymentTerm().getId());
		   wsCom.setPaymentTerm(dbc.getSTermDicByPaymentTerm().getName());
		}
		wsCom.setRemark(dbc.getRemark());
		wsCom.setShortName(dbc.getShortName());
		if(dbc.getSStatusDic()!=null)
		{
		   wsCom.setStatus(dbc.getSStatusDic().getName());
		    wsCom.setStatusId(dbc.getSStatusDic().getId());
		}
		wsCom.setTaxNo(dbc.getTaxNo());
		wsCom.setTel(dbc.getTel());
		if(dbc.getSTypeDic()!=null)
		{
			wsCom.setType(dbc.getSTypeDic().getName());
			wsCom.setTypeId(dbc.getSTypeDic().getId());
		}
        wsCom.setUrl(dbc.getUrl());
	    return wsCom;
	}

}
