package com.jms.service.store;


import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.Company;
import com.jms.domain.db.SComCom;
import com.jms.domain.db.SCompanyCo;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMtfNo;
import com.jms.domain.db.SPo;
import com.jms.domain.db.SPoMaterial;
import com.jms.domain.db.SSo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.s.WSSpo;
import com.jms.domain.ws.s.WSSpoMaterial;
import com.jms.domain.ws.s.WSSpoRemark;
import com.jms.domainadapter.BeanUtil;
import com.jms.email.EmailSenderService;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.s.SAttachmentRepository;
import com.jms.repositories.s.SComComRepository;
import com.jms.repositories.s.SCompanyCoRepository;
import com.jms.repositories.s.SCurrencyTypeRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SMtfNoRepository;
import com.jms.repositories.s.SSoRepository;
import com.jms.repositories.s.SSoRepositoryCustom;
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
	@Autowired private SAttachmentRepository sAttachmentRepository;
	@Autowired private SMtfNoRepository sMtfNoRepository;
	@Autowired private SMtfNoService sMtfNoService;
	
	@Autowired private SsoService ssoService;
	@Autowired private SSoRepository sSoRepository;

	@Autowired private CompanyRepository companyRepository;
	@Autowired private SMaterialRepository sMaterialRepository;
	

	@Autowired
	private EmailSenderService emailSenderService;
	@Autowired
	private SComComRepository sComComRepository;
	
	public WSSpo saveSpo(WSSpo wsSpo) throws Exception {
		
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
			if(!spo.getSPoMaterials().isEmpty())
			{
				
				//logger.debug("delete po material: " + spo.getSPoMaterials().size());
				for(SPoMaterial spoMaterial: spo.getSPoMaterials())
				{
					sSpoMaterialRepository.delete(spoMaterial);
				}
				//spo.getSPoMaterials().clear();
				
			}
			//spo.set
			
		}
		spo.getSPoMaterials().clear();
		
		spo=toDBSpo(wsSpo,spo);
		if(spo.getRemark()!=null&&spo.getRemark().equals("_error"))
		{
			wsSpo.setValid(0l);
			wsSpo.setMsg("保存失败，已有该订单号！");
			return wsSpo;
		}
		SPo sp =sSpoRepository.save(spo);
		wsSpo.setIdPo(sp.getIdPo());
		for(String k: wsSpo.getPoItems().keySet())
		{
		//	logger.debug("save po material: " + k);
			WSSpoMaterial wm =wsSpo.getPoItems().get(k);
			wm.setLine(Long.parseLong(k.substring(4)));
		
			wm.setsPoId(sp.getIdPo());
		
			spoMaterialService.saveSpoMaterial(wm);
		
		}
		Long myCompanyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		//是否是往来公司流转？
		if(wsSpo.getIdComCom()!=null)
		{
		
			Long idComcompany;
			SComCom comcom=sComComRepository.findOne(wsSpo.getIdComCom());
			System.out.println("往来公司comcomID: " + wsSpo.getIdComCom());
			if(comcom!=null)
			{
				if(comcom.getIdCompany1().equals(myCompanyId))
				{
					idComcompany=comcom.getIdCompany2();
				}
				else
				{
					idComcompany=comcom.getIdCompany1();
				}
				
				Company wanglaiCompany = companyRepository.findOne(idComcompany);
				
				Long sCompanyCoId = wsSpo.getsCompanyCoId();
				
				SCompanyCo sCompanyCo = sCompanyCoRepository.findOne(sCompanyCoId);
				Company soCompany =companyRepository.findByCompanyName(sCompanyCo.getName());
				
				if(soCompany==null)
				{
			
						wsSpo.setValid(0l);
						wsSpo.setMsg("保存失败，供应商必须是往来公司");
						return wsSpo;
		
				}
				
				SComCom comcom1 = sComComRepository.findByTwoCompanyId(myCompanyId, soCompany.getIdCompany());
				
			
					if(comcom1==null)
					{
				
							wsSpo.setValid(0l);
							wsSpo.setMsg("保存失败，供应商必须是往来公司");
							return wsSpo;
			
					}
				boolean sendemail=false;
				for(String k: wsSpo.getPoItems().keySet())
				{
				//	logger.debug("save po material: " + k);
					WSSpoMaterial wm =wsSpo.getPoItems().get(k);
					
					SMaterial m = sMaterialRepository.findOne(wm.getsMaterialId());
					SMaterial cm = sMaterialRepository.getByCompanyIdAndPno(soCompany.getIdCompany(), m.getPno());
					if(cm!=null)
					{
						//create so
						//System.out.println("新建销售订单：id_company: " +soCompany.getIdCompany() );
						SSo sSo = new SSo();
						SMtfNo smtfNo = sMtfNoRepository.getByCompanyIdAndType(soCompany.getIdCompany(), 11l);
					    long currentVal =smtfNo.getCurrentVal()+1;
					    smtfNo.setCurrentVal(currentVal);
					    sMtfNoRepository.save(smtfNo);
					    String codeSo = smtfNo.getPrefix()+String.format("%08d", currentVal);
					    sSo.setCodeSo(codeSo);
					    sSo.setCompany(soCompany);
					    sSo.setCoOrderNo(spo.getCodePo());
					    sSo.setDateOrder(spo.getDateOrder());
					    sSo.setDeliveryDate(wm.getDeliveryDate());
					    sSo.setSMaterial(cm);
					    sSo.setIdCompany2(myCompanyId); //客户
					    sSo.setQtySo(wm.getQtyPo());
					    sSo.setIdCompany1(idComcompany); //往来公司，需要发给下一家的公司
					    try
					    {
					    	  SCompanyCo sCompanyCo1=sCompanyCoRepository.findByCompanyIdAndName(soCompany.getIdCompany(), securityUtils.getCurrentDBUser().getCompany().getCompanyName());
					    	  sSo.setSCompanyCo(sCompanyCo1);
					    }catch(Exception e)
					    {
					    	e.printStackTrace();
					    }

					    sSo.setSStatusDic(sStatusDicRepository.findOne(14l)); //编辑
					    sSo.setRemark("客户: " +securityUtils.getCurrentDBUser().getCompany().getCompanyName() +", 合作公司： " +wanglaiCompany.getCompanyName());
					    //sSo.setQtyDelivered(wm.getQtyPo());
					    sSo.setUPrice(wm.getUprice().floatValue());
					    sSo.setTotalAmount(wm.getQtyPo()*wm.getUprice().floatValue());
					    sSoRepository.save(sSo);
					    sendemail = true;
					}
					
				
				}
				
				
				if(sendemail)
				{
					 String email="";
						
						if(comcom1.getIdCompany1().equals(myCompanyId))
						{
							email = comcom1.getEmail2();
						}
						else
						{
							email = comcom1.getEmail1();
						}
						//sComComRepository.findMyCompanies(idCompany)
						Map<String,Object> model = new LinkedHashMap<String,Object>();
						String[] emails = new String[]{email};
//						采购订单
//						公司：$!{company}
//						订单编码: $!{poNo}, 总价： $!{totalPrice}
//						条目：
//						$!{materials}
//						备注：
//						$!{remark}
						String materials ="";
						for(WSSpoMaterial pm: wsSpo.getPoItems().values())
						{
							materials =materials +"物料: " + pm.getsMaterial() +", 数量: " +pm.getQtyPo() +",单价: " + pm.getUprice() +", 总价： " + pm.getTotalPrice() +", 备注： " +pm.getRemark()+"\r\n";
						}
						model.put("company", securityUtils.getCurrentDBUser().getCompany().getCompanyName());
						model.put("poNo", wsSpo.getCodePo());
						model.put("totalPrice", wsSpo.getTotalAmount());
						model.put("remarks", wsSpo.getRemark());
						model.put("materials", materials);
						System.out.println("matertials: " + materials);
						try{
							emailSenderService.sendEmail(emails,"poTemplate.vm",  "新订单", model, null);
						}catch(Exception e)
						{
							e.printStackTrace();
						}
				}
			  
			}
			
			else
			{
					
				Long sCompanyCoId = wsSpo.getsCompanyCoId();
				
				SCompanyCo sCompanyCo = sCompanyCoRepository.findOne(sCompanyCoId);
				Company soCompany =companyRepository.findByCompanyName(sCompanyCo.getName());
				
				if(soCompany==null)
				{
			
						wsSpo.setValid(0l);
						wsSpo.setMsg("保存失败，供应商必须是往来公司");
						return wsSpo;
		
				}
				
				SComCom comcom1 = sComComRepository.findByTwoCompanyId(myCompanyId, soCompany.getIdCompany());
				
			
					if(comcom1==null)
					{
				
							wsSpo.setValid(0l);
							wsSpo.setMsg("保存失败，供应商必须是往来公司");
							return wsSpo;
			
					}
				boolean sendemail=false;
				for(String k: wsSpo.getPoItems().keySet())
				{
				//	logger.debug("save po material: " + k);
					WSSpoMaterial wm =wsSpo.getPoItems().get(k);
					
					SMaterial m = sMaterialRepository.findOne(wm.getsMaterialId());
					SMaterial cm = sMaterialRepository.getByCompanyIdAndPno(soCompany.getIdCompany(), m.getPno());
					if(cm!=null)
					{
						//create so
						//System.out.println("新建销售订单：id_company: " +soCompany.getIdCompany() );
						SSo sSo = new SSo();
						SMtfNo smtfNo = sMtfNoRepository.getByCompanyIdAndType(soCompany.getIdCompany(), 11l);
					    long currentVal =smtfNo.getCurrentVal()+1;
					    smtfNo.setCurrentVal(currentVal);
					    sMtfNoRepository.save(smtfNo);
					    String codeSo = smtfNo.getPrefix()+String.format("%08d", currentVal);
					    sSo.setCodeSo(codeSo);
					    sSo.setCompany(soCompany);
					    sSo.setCoOrderNo(spo.getCodePo());
					    sSo.setDateOrder(spo.getDateOrder());
					    sSo.setDeliveryDate(wm.getDeliveryDate());
					    sSo.setSMaterial(cm);
					    sSo.setIdCompany2(myCompanyId); //客户
					    sSo.setQtySo(wm.getQtyPo());
					  //  sSo.setIdCompany1(idComcompany); //往来公司，需要发给下一家的公司
					    try
					    {
					    	  SCompanyCo sCompanyCo1=sCompanyCoRepository.findByCompanyIdAndName(soCompany.getIdCompany(), securityUtils.getCurrentDBUser().getCompany().getCompanyName());
					    	  sSo.setSCompanyCo(sCompanyCo1);
					    }catch(Exception e)
					    {
					    	e.printStackTrace();
					    }

					    sSo.setSStatusDic(sStatusDicRepository.findOne(14l)); //编辑
					    sSo.setRemark("客户: " +securityUtils.getCurrentDBUser().getCompany().getCompanyName() +", 无合作公司： ");
					    //sSo.setQtyDelivered(wm.getQtyPo());
					    sSo.setUPrice(wm.getUprice().floatValue());
					    sSo.setTotalAmount(wm.getQtyPo()*wm.getUprice().floatValue());
					    sSoRepository.save(sSo);
					    sendemail = true;
					}
					
				
				}
				
				if(sendemail)
				{
					   String email="";
						
						if(comcom1.getIdCompany1().equals(myCompanyId))
						{
							email = comcom1.getEmail2();
						}
						else
						{
							email = comcom1.getEmail1();
						}
						//sComComRepository.findMyCompanies(idCompany)
						Map<String,Object> model = new LinkedHashMap<String,Object>();
						String[] emails = new String[]{email};
//						采购订单
//						公司：$!{company}
//						订单编码: $!{poNo}, 总价： $!{totalPrice}
//						条目：
//						$!{materials}
//						备注：
//						$!{remark}
						String materials ="";
						for(WSSpoMaterial pm: wsSpo.getPoItems().values())
						{
							materials =materials +"物料: " + pm.getsMaterial() +", 数量: " +pm.getQtyPo() +",单价: " + pm.getUprice() +", 总价： " + pm.getTotalPrice() +", 备注： " +pm.getRemark()+"\r\n";
						}
						model.put("company", securityUtils.getCurrentDBUser().getCompany().getCompanyName());
						model.put("poNo", wsSpo.getCodePo());
						model.put("totalPrice", wsSpo.getTotalAmount());
						model.put("remarks", wsSpo.getRemark());
						model.put("materials", materials);
						System.out.println("matertials: " + materials);
						try{
							emailSenderService.sendEmail(emails,"poTemplate.vm",  "新订单", model, null);
						}catch(Exception e)
						{
							e.printStackTrace();
						}
					
				}

				
			}
	
	
			
		}
		
		return wsSpo;
	}
	

	

	public Valid saveSpoRemark(WSSpoRemark wsSpoRemark) {
		
		SPo spo = sSpoRepository.findOne(wsSpoRemark.getIdPo());	
		spo.setRemark(wsSpoRemark.getRemark());
		if(wsSpoRemark.getStatusId()!=null)
		{
			spo.setSStatusDic(sStatusDicRepository.findOne(wsSpoRemark.getStatusId()));
		}

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
	//	System.out.println("companyId: " + companyId +", codeCo: " + codeCo);
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		for(SPo s : sSpoRepository.findByCompanyIdAndCodeCo(companyId,codeCo))
		{
			if(s.getSStatusDic()!=null&&s.getSStatusDic().getId().equals(11l)){ //激活状态
				WSSelectObj w = new WSSelectObj(s.getIdPo(),s.getCodePo());
				ws.add(w);
			}
		
		}
		
		return ws;
	}
	
	
	
	private SPo toDBSpo(WSSpo wsSpo,SPo spo) throws Exception
	{
	
		SPo dbSpo = (SPo)BeanUtil.shallowCopy(wsSpo, SPo.class, spo);
		if(wsSpo.getIdPo()==null||wsSpo.getIdPo().equals(0l))
		{
			if(wsSpo.getCodePo()==null)
			{
				SMtfNo smtfNo = sMtfNoRepository.getByCompanyIdAndType(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), 10l);
			    long currentVal =smtfNo.getCurrentVal()+1;
			    smtfNo.setCurrentVal(currentVal);
			    sMtfNoRepository.save(smtfNo);
			    String codePo = smtfNo.getPrefix()+String.format("%08d", currentVal);
				dbSpo.setCodePo(codePo);
			}
			else
			{
				if(!sSpoRepository.findByCompanyIdAndCodePo(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), wsSpo.getCodePo()).isEmpty())
				{
					dbSpo.setRemark("_error");
					return dbSpo;
				}
			}

		}
		
		
		
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
		if(wsSpo.getFileId()!=null)
		{
			dbSpo.setSAttachment(sAttachmentRepository.findOne(wsSpo.getFileId()));
		}
		dbSpo.setUsers(securityUtils.getCurrentDBUser());
		dbSpo.setCompany(securityUtils.getCurrentDBUser().getCompany());
		
		
		if(wsSpo.getIdComCom()!=null)
		{
			
			SComCom comcom=sComComRepository.findOne(wsSpo.getIdComCom());
			System.out.println("comcomid: " + wsSpo.getIdComCom() );
			if(comcom!=null)
			{
				if(comcom.getIdCompany1().equals(securityUtils.getCurrentDBUser().getCompany().getIdCompany()))
				{
					dbSpo.setIdCompany1(comcom.getIdCompany2());
				}
				else
				{
					dbSpo.setIdCompany1(comcom.getIdCompany1());
				}
			}
	
			
		}

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
		if(spo.getSAttachment()!=null)
		{
			wsSpo.setFileId(spo.getSAttachment().getId());
			wsSpo.setFileName(spo.getSAttachment().getFilename());
		}
		for(SPoMaterial s: spo.getSPoMaterials())
		{
			wsSpo.getPoItems().put("item"+s.getLine(), spoMaterialService.toWSSpoMaterial(s));
		}
		wsSpo.setUserName(spo.getUsers().getName());
		return wsSpo;
	}


}
