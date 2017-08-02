package com.jms.service.store;


import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.Company;
import com.jms.domain.db.PWo;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMtfNo;
import com.jms.domain.db.SPic;
import com.jms.domain.db.SSo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.s.WSMaterial;
import com.jms.domain.ws.s.WSSSoRemark;
import com.jms.domain.ws.s.WSSso;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.s.SCompanyCoRepository;
import com.jms.repositories.s.SCurrencyTypeRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SMtfNoRepository;
import com.jms.repositories.s.SSoRepository;
import com.jms.repositories.s.SSpoMaterialRepository;
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
	private PWoRepository pWoRepository;
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
	@Autowired private PStatusDicRepository pStatusDicRepository;
	@Autowired private MrpService mrpService;

	public Valid saveSSo(WSSso wsSso) throws Exception {
		
		Company company = securityUtils.getCurrentDBUser().getCompany();
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
		sso =sSoRepository.save(sso);
		//System.out.println("status: " + wsSso.getsStatusId() +", sowo: " + company.getSoWo());
		if(wsSso.getsStatusId().equals(18l)&&company.getSoWo()!=null&&company.getSoWo().equals(1l))//激活状态
		{
			//System.out.println("生成工单！");
			genertorWoBySo(sso);
		}
		
		
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
	
		//System.out.println("xxxx: " + wsSso.getsCompanyCoId());
		SSo dbSso = (SSo)BeanUtil.shallowCopy(wsSso, SSo.class, sso);
		
		if(wsSso.getIdSo()==null||wsSso.getIdSo().equals(0l))
	
		{
			if(wsSso.getCodeSo()==null)
			{
				SMtfNo smtfNo = sMtfNoRepository.getByCompanyIdAndType(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), 11l);
			    long currentVal =smtfNo.getCurrentVal()+1;
			    smtfNo.setCurrentVal(currentVal);
			    sMtfNoRepository.save(smtfNo);
			    String codeSo = smtfNo.getPrefix()+String.format("%08d", currentVal);
			    dbSso.setCodeSo(codeSo);
			}

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
		
//		if(wsSso.getIdCompany2()!=null)
//		{
//			Co
//		}
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
		//dbSso.setIdCompany2(idCompany2);
		dbSso.setUPrice(wsSso.getUprice());
		

		return dbSso;
	}
	
	

	public Valid saveSoAutoRemark(WSSSoRemark wsSSoRemark) {
		Company company = securityUtils.getCurrentDBUser().getCompany();
		SSo sso = sSoRepository.findOne(wsSSoRemark.getIdSo());	
		sso.setAutoRemark(wsSSoRemark.getAutoRemark());
		if(!sso.getSStatusDic().getId().equals(wsSSoRemark.getStatusId()))
		{
			if(wsSSoRemark.getStatusId()!=null)
			{
				sso.setSStatusDic(sStatusDicRepository.findOne(wsSSoRemark.getStatusId()));
				
				if(wsSSoRemark.getStatusId().equals(18l)&&company.getSoWo()!=null&&company.getSoWo().equals(1l))//激活状态
				{
					genertorWoBySo(sso);
				}
				
			}
			if(wsSSoRemark.getStatusId().equals(16l)) // 作废
			{
				Long id = securityUtils.getCurrentDBUser().getCompany().getAutoSo();
				Long autoSo = (id==null)?1l:id;
				if(autoSo.equals(0l))
				{
					String co = sso.getCodeSo();
					sso.setCodeSo(co+"-void");
				}
			
			}
		
		}
	
		
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
//		else
//		{
//			if(sso.get)
//		}
//	
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
	
		if(sso.getUsers()!=null)
		{
			wsSso.setUserName(sso.getUsers().getName());
		}
		
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

	@Transactional(readOnly=false)
	public Valid genertorWoBySo(SSo so)
	{

	 
	    Long materialId = so.getSMaterial().getIdMaterial();
	   // Long qtySo =so.getQtySo();
	    //todo:v
	    Long inv = mrpService.getInv(materialId);
	    Long openSo = mrpService.getOpenSo(materialId);
	    Long openPo = mrpService.getOpenPo(materialId);
	    Long openWo = mrpService.getOpenWo(materialId);
	    
	    Long qty = openSo-inv-openPo-openWo;
	  
	    
	 //   System.out.println("open so: " + openSo +", inv: " + inv +", openPo: " + openPo +", open Wo: " + openWo);
	    
	    if(qty>0)
	    {
			PWo wo = new PWo();
			SMtfNo smtfNo = sMtfNoRepository.getByCompanyIdAndType(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), 9l);
		    long currentVal =smtfNo.getCurrentVal()+1;
		    smtfNo.setCurrentVal(currentVal);
		    sMtfNoRepository.save(smtfNo);
		    String codePo = smtfNo.getPrefix()+String.format("%08d", currentVal);
		    wo.setWoNo(codePo);
		    
		    wo.setCreationTime(new Date());
		    wo.setUsers(securityUtils.getCurrentDBUser());
		    wo.setSSo(so);
		    wo.setSt(new Date());
		    wo.setFt(so.getDeliveryDate());
		    wo.setPStatusDic(pStatusDicRepository.findOne(12l)); //开放状态
		    wo.setQty(qty);
	    	pWoRepository.save(wo);
	    }
	   
	   // wo.set
		Valid v = new Valid();
		v.setValid(true);
		return v;
	}
	

}
