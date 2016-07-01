package com.jms.service.store;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMtfNo;
import com.jms.domain.db.SPic;
import com.jms.domain.db.SPo;
import com.jms.domain.db.SPoMaterial;
import com.jms.domain.db.SSo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.store.WSMaterial;
import com.jms.domain.ws.store.WSSSoRemark;
import com.jms.domain.ws.store.WSSpo;
import com.jms.domain.ws.store.WSSpoMaterial;
import com.jms.domain.ws.store.WSSpoRemark;
import com.jms.domain.ws.store.WSSso;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.s.SCompanyCoRepository;
import com.jms.repositories.s.SCurrencyTypeRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SMtfNoRepository;
import com.jms.repositories.s.SSoRepository;
import com.jms.repositories.s.SSpoMaterialRepository;
import com.jms.repositories.s.SSpoRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.s.SStermDicRepository;
import com.jms.repositories.s.SStkTypeDicRepository;
import com.jms.web.security.SecurityUtils;


@Service
@Transactional
public class SsoService {

	private static final Logger logger = LogManager.getLogger(SsoService.class
			.getCanonicalName());
	@Autowired
	private SSoRepository sSoRepository;
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

	@Autowired
	private  SMaterialRepository sMaterialRepository;
	
	@Autowired private SMtfNoRepository sMtfNoRepository;
	@Autowired private SMtfNoService sMtfNoService;

	public Valid saveSSo(WSSso wsSso) throws Exception {
		
		SSo sso;
		//create
		if(wsSso.getIdSo()==null||wsSso.getIdSo().equals(0l))
		{
			sso=new SSo();
			sso.setDateOrder(new Date());
		}
		//update
		else
		{
			sso = sSoRepository.findOne(wsSso.getIdSo());	
		}
		
		sso=toDBSso(wsSso,sso);
		SSo sp =sSoRepository.save(sso);
		
		Valid valid = new Valid();
		valid.setValid(true);
		return valid;
	}
	

	
	
	public WSSso findSso(Long ssoId) throws Exception 
	{
		SSo sso = sSoRepository.findOne(ssoId);
		return toWSSso(sso);
	}
	
	


	
	private SSo toDBSso(WSSso wsSso,SSo sso) throws Exception
	{
	
		SSo dbSso = (SSo)BeanUtil.shallowCopy(wsSso, SSo.class, sso);
		
		if(wsSso.getIdSo()==null||wsSso.getIdSo().equals(0l))
		{
		SMtfNo smtfNo = sMtfNoRepository.getByCompanyIdAndType(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), 11l);
	    if(smtfNo==null)
	    {
	    	sMtfNoService.loadSmtfNosByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
	    	smtfNo = sMtfNoRepository.getByCompanyIdAndType(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), 11l);
	    }
	    long currentVal =smtfNo.getCurrentVal()+1;
	    smtfNo.setCurrentVal(currentVal);
	    sMtfNoRepository.save(smtfNo);
		
	    String codeSo = smtfNo.getPrefix()+String.format("%08d", currentVal);
	    dbSso.setCodeSo(codeSo);
		}
		
		if(wsSso.getFreightTermId()!=null)
		{
			dbSso.setSTermDicByFreightTerm(sStermDicRepository.findOne(wsSso.getFreightTermId()));
		}
		if(wsSso.getPaymentTermId()!=null)
		{
			dbSso.setSTermDicByPaymentTerm(sStermDicRepository.findOne(wsSso.getPaymentTermId()));
		}
		if(wsSso.getsCompanyCoId()!=null)
		{
			dbSso.setSCompanyCo(sCompanyCoRepository.findOne(wsSso.getsCompanyCoId()));
		}
		
		if(wsSso.getsStatusId()!=null)
		{
			dbSso.setSStatusDic(sStatusDicRepository.findOne(wsSso.getsStatusId()));
		}
		if(wsSso.getMaterialId()!=null)
		{
			dbSso.setSMaterial(sMaterialRepository.findOne(wsSso.getMaterialId()));
		}
		dbSso.setUsers(securityUtils.getCurrentDBUser());
		dbSso.setCompany(securityUtils.getCurrentDBUser().getCompany());
		dbSso.setUPrice(wsSso.getUprice());
		

		return dbSso;
	}
	
	

	public Valid saveSoAutoRemark(WSSSoRemark wsSSoRemark) {
		
		SSo sso = sSoRepository.findOne(wsSSoRemark.getIdSo());	
		sso.setAutoRemark(wsSSoRemark.getAutoRemark());
		
		sSoRepository.save(sso);
		
		Valid valid = new Valid();
		valid.setValid(true);
		return valid;
	}
	
	protected WSSso toWSSso(SSo sso) throws Exception
	{
		WSSso wsSso = (WSSso)BeanUtil.shallowCopy(sso, WSSso.class, null);
		if(sso.getSCompanyCo()!=null)
		{
			wsSso.setsCompanyCoId(sso.getSCompanyCo().getId());
			wsSso.setCodeCo(sso.getSCompanyCo().getCode());
		}
	
		if(sso.getSTermDicByFreightTerm()!=null)
		{
			wsSso.setFreightTerm(sso.getSTermDicByFreightTerm().getName());
			wsSso.setFreightTermId(sso.getSTermDicByFreightTerm().getId());
		}
		if(sso.getSTermDicByPaymentTerm()!=null)
		{
			wsSso.setPaymentTerm(sso.getSTermDicByPaymentTerm().getName());
			wsSso.setPaymentTermId(sso.getSTermDicByPaymentTerm().getId());
		}
		if(sso.getSStatusDic()!=null)
		{
			wsSso.setsStatus(sso.getSStatusDic().getName());
			wsSso.setsStatusId(sso.getSStatusDic().getId());
		}
		if(sso.getSMaterial()!=null)
		{
			wsSso.setMaterialId(sso.getSMaterial().getIdMaterial());
		
		}
	
		wsSso.setUserName(sso.getUsers().getName());
		wsSso.setUprice(sso.getUPrice());
		return wsSso;
	}

	
	
	@Transactional(readOnly=true)
	public WSMaterial getMaterialBySoId(Long soId) throws Exception
	{	
		WSMaterial wsMc = new WSMaterial();
		SMaterial m = sSoRepository.findOne(soId).getSMaterial();
			if(m==null)
				return wsMc;
			wsMc = materialService.toWSMaterial(m); 
			if(!m.getSMaterialPics().isEmpty())
			{
				SPic spic = m.getSMaterialPics().iterator().next().getSPic();
				if(spic!=null)
				{
					wsMc.setFileName(spic.getFilename());
					wsMc.setFileId(spic.getId());
				}
				
			}
			return wsMc;

	}

	
	

}
