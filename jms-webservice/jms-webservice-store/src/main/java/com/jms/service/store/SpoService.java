package com.jms.service.store;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.jms.domain.db.SLinkman;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMtfNo;
import com.jms.domain.db.SPo;
import com.jms.domain.db.SPoMaterial;
import com.jms.domain.db.SSo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.s.WSPoItem;
import com.jms.domain.ws.s.WSPoPrint;
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
		
		//System.out.println("新建采购订单： ");
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

		//是否是往来公司流转？
		if(wsSpo.getIdComCom()!=null)
		{
			Valid  v = checkCompanyCoSpo(wsSpo);
			if(!v.getValid())
			{
				wsSpo.setValid(0l);
				wsSpo.setMsg(v.getMsg());
				return wsSpo;
			}
			else
			{
				//发邮件，创建销售订单
				wsSpo.setCodePo(spo.getCodePo());
				Valid v1 =  generateSo(wsSpo);
				if(!v1.getValid())
				{
					wsSpo.setValid(0l);
					wsSpo.setMsg(v.getMsg());
					return wsSpo;
				}
				else
				{

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
				}
			}
		}

		else  //正常采购订单
		{
			
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
				}
	
		return wsSpo;
	}
	

	
	
	

	public Valid saveSpoRemark(WSSpoRemark wsSpoRemark) {
		
		SPo spo = sSpoRepository.findOne(wsSpoRemark.getIdPo());	
		spo.setRemark(wsSpoRemark.getRemark());
		if(!spo.getSStatusDic().getId().equals(wsSpoRemark.getStatusId()))
		{
			if(wsSpoRemark.getStatusId()!=null)
			{
				spo.setSStatusDic(sStatusDicRepository.findOne(wsSpoRemark.getStatusId()));
			}
			if(wsSpoRemark.getStatusId().equals(9l)) // 作废
			{
				Long id = securityUtils.getCurrentDBUser().getCompany().getAutoPo();
				Long autoPo = (id==null)?1l:id;
				if(autoPo.equals(0l))
				{
					String co = spo.getCodePo();
					spo.setCodePo(co+"-void");
				}
			
			}
			//todo
			if(wsSpoRemark.getStatusId().equals(11l)) //激活
			{
				//todo
			}
		
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
	
	
	public WSPoPrint printSpo(Long spoId) 
	{
		Company company = securityUtils.getCurrentDBUser().getCompany();
		SPo spo = sSpoRepository.findOne(spoId);
		WSPoPrint wsPoPrint = new  WSPoPrint();
		wsPoPrint.setAddress(company.getAddress()==null?"":company.getAddress());
		SCompanyCo sco = spo.getSCompanyCo();
		wsPoPrint.setCoAddress(sco.getAddressAct()==null?"":sco.getAddressAct());
		wsPoPrint.setCompanyName(company.getCompanyName());
		wsPoPrint.setCoName(sco.getName());
		wsPoPrint.setPoDate(formatDate(spo.getDateOrder()));
		
		wsPoPrint.setFax(company.getFax()==null?"":company.getFax());
		wsPoPrint.setTelephone(company.getTelephone()==null?"":company.getTelephone());
		wsPoPrint.setPoCode(spo.getCodePo());
		if(spo.getUsers()!=null)
		{
			wsPoPrint.setLinkMan(spo.getUsers().getName()==null?"":spo.getUsers().getName());
			wsPoPrint.setLinkTel(spo.getUsers().getMobile()==null?"":spo.getUsers().getMobile());
		}
		
		if(!sco.getSLinkmans().isEmpty())
		{
			SLinkman s =sco.getSLinkmans().iterator().next();
			wsPoPrint.setLinkMan1(s.getName());
			wsPoPrint.setLinkTel1(s.getPhoneNo());
		}
		wsPoPrint.setUrl(company.getUrl()==null?"":company.getUrl());
		
		
	   List<WSPoItem> items = new ArrayList<WSPoItem>();
	   int seq = 1;
	   Float tot = 0f;
		for(SPoMaterial s: spo.getSPoMaterials())
		{
			WSPoItem item = new WSPoItem();
			item.setC1(""+seq);
			SMaterial m = s.getSMaterial();
			item.setC2(m.getPno());
			item.setC3(m.getDes());
			item.setC4(m.getBrand());
			if(m.getSUnitDicByUnitPur()!=null)
			{
				item.setC5(m.getSUnitDicByUnitPur().getName());
			}
			else
			{
				item.setC5("");
			}
			item.setC6(""+s.getQtyPo());
			item.setC7(s.getUPrice().toString());
			item.setC8(""+s.getTotalPrice());
			tot = tot+ s.getTotalPrice();
			item.setC9(s.getRemark());
			item.setC10(formatDate(s.getDeliveryDate()));
			items.add(item);
			
			seq++;
		}
		wsPoPrint.setItems(items);
		
	    wsPoPrint.setPrice(""+tot);
		
		wsPoPrint.setPriceCap(NumberToCN.number2CNMontrayUnit(new BigDecimal(tot)));
	
		return wsPoPrint;
	}
	
	private String formatDate(Date d) {
		if (d == null)
			return "";
		SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy-MM-dd");
		return myFmt1.format(d);
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
				//Date d = new Date();
				String d=new SimpleDateFormat("yyyyMM").format(Calendar.getInstance().getTime());
				//System.out.println("现在年月: " + d);
				//SMtfNo smtfNo = sMtfNoRepository.getByCompanyIdAndType(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), 10l);
				SMtfNo smtfNo = sMtfNoRepository.getByCompanyIdAndPrefix(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), "PO"+d);
				
			if(smtfNo==null)
			{
				smtfNo  = new SMtfNo();
				smtfNo.setPrefix("PO" + d);
				smtfNo.setType(10l);
				smtfNo.setCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
				smtfNo.setCurrentVal(0l);
				smtfNo.setToday(new Date());
				smtfNo.setDes(d +"的销售订单");
				smtfNo = sMtfNoRepository.save(smtfNo);
			}
				
				long currentVal =smtfNo.getCurrentVal()+1;
			    smtfNo.setCurrentVal(currentVal);
			    sMtfNoRepository.save(smtfNo);
			    String codePo = smtfNo.getPrefix()+String.format("%03d", currentVal);
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
			 SCompanyCo sCompanyCo = sCompanyCoRepository.findOne(wsSpo.getsCompanyCoId());
			 dbSpo.setSCompanyCo(sCompanyCo);
			

			 Company soCompany =companyRepository.findByCompanyName(sCompanyCo.getName());
			 if(soCompany!=null)
			 {
				 dbSpo.setIdCompany2(soCompany.getIdCompany());
			 }

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
			//System.out.println("comcomid: " + wsSpo.getIdComCom() );
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
			else //往来公司是自己
			{
				dbSpo.setIdCompany1(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
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

	
	//检查往来公司采购条件
		private Valid checkCompanyCoSpo(WSSpo wsSpo)
		{
			Valid v = new Valid();
			v.setValid(true);
			String msg = "";
			//1.必须是激活状态
			if(!wsSpo.getsStatusId().equals(11l)) 
			{
				v.setValid(false);
				msg = msg + "在合作公司下采购，采购订单必须是激活状态。<br/>";
			}
			//2.供应商必须是注册公司
	         Long sCompanyCoId = wsSpo.getsCompanyCoId();
	         SCompanyCo sCompanyCo = sCompanyCoRepository.findOne(sCompanyCoId);
			 Company soCompany =companyRepository.findByCompanyName(sCompanyCo.getName());
			 if(soCompany==null)
			 {
				v.setValid(false);
				msg = msg + "在合作公司下采购，供应商必须是注册的往来公司。<br/>";
			 }
			 else
			 {
				 //3.供应商必须是往来公司
				SComCom comcom = sComComRepository.findByTwoCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), soCompany.getIdCompany());
		        if(comcom==null)
				{
		    	   v.setValid(false);
				   msg = msg + "在合作公司下采购，供应商必须是注册的往来公司。<br/>";
				}
					
				 //4.供应商的客户必须是当前公司
				 String customerName = securityUtils.getCurrentDBUser().getCompany().getCompanyName();
				 SCompanyCo sCompanyCoA = sCompanyCoRepository.findByCompanyIdAndNameAndType(soCompany.getIdCompany(), customerName,2l);
				 if(sCompanyCoA==null)
				 {
					v.setValid(false);
					msg = msg + "在合作公司下采购，供应商必须设置您公司为客户。<br/>";
				 }
			 }

			  Long myCompanyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
			  Long idWanglai;
				if(wsSpo.getIdComCom().equals(0l)) //往来公司是本公司
				{
					idWanglai = myCompanyId;
				}
				else
				{
					SComCom comcom=sComComRepository.findOne(wsSpo.getIdComCom());

					if(comcom.getIdCompany1().equals(myCompanyId))
					{
						idWanglai=comcom.getIdCompany2();
					}
					else
					{
						idWanglai=comcom.getIdCompany1();
					}
				}
				
			 //5.是否共享bom
			 if(soCompany!=null)
			 {
				for(String k: wsSpo.getPoItems().keySet())
				{
					WSSpoMaterial wm =wsSpo.getPoItems().get(k);
					SMaterial m = sMaterialRepository.findOne(wm.getsMaterialId());
					SMaterial cm = sMaterialRepository.getByCompanyIdAndPno(soCompany.getIdCompany(), m.getPno());
					SMaterial cm1 = sMaterialRepository.getByCompanyIdAndPno(idWanglai, m.getPno());
					if(cm==null ||cm1==null) 
					{
						v.setValid(false);
						msg = msg + "在合作公司下采购，bom必须在往来公司和供应商之间共享： " +m.getPno() +" <br/>";
					}
				}
				
			 }
			 v.setMsg(msg);
			 System.out.println(" valid: " + v.getValid() + ", msg: " + msg);
			 return v;
		}
		
		
		
		//检查往来公司采购条件
		private Valid generateSo(WSSpo wsSpo)
		{
			Valid v = new Valid();
			v.setValid(true);
			String msg = "";
			Long myCompanyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
			Long idWanglai;
			if(wsSpo.getIdComCom().equals(0l)) //往来公司是本公司
			{
				idWanglai = myCompanyId;
			}
			else
			{
				SComCom comcom=sComComRepository.findOne(wsSpo.getIdComCom());

				if(comcom.getIdCompany1().equals(myCompanyId))
				{
					idWanglai=comcom.getIdCompany2();
				}
				else
				{
					idWanglai=comcom.getIdCompany1();
				}
			}
			
			 Long sCompanyCoId = wsSpo.getsCompanyCoId();
		     SCompanyCo sCompanyCo = sCompanyCoRepository.findOne(sCompanyCoId);
		     Company soCompany =companyRepository.findByCompanyName(sCompanyCo.getName());
			SComCom comcom1 = sComComRepository.findByTwoCompanyId(myCompanyId, soCompany.getIdCompany());
			boolean sendemail=false;
			for(String k: wsSpo.getPoItems().keySet())
			{
			//	logger.debug("save po material: " + k);
				WSSpoMaterial wm =wsSpo.getPoItems().get(k);
				
				SMaterial m = sMaterialRepository.findOne(wm.getsMaterialId());
				SMaterial cm = sMaterialRepository.getByCompanyIdAndPno(soCompany.getIdCompany(), m.getPno());
				if(cm!=null) //
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
				    sSo.setCoOrderNo(wsSpo.getCodePo());
				    sSo.setDateOrder(new Date());
				    sSo.setDeliveryDate(wm.getDeliveryDate());
				    sSo.setSMaterial(cm);
				    sSo.setIdCompany2(myCompanyId); //客户
				    sSo.setQtySo(wm.getQtyPo());
				    sSo.setIdCompany1(idWanglai); //往来公司，需要发给下一家的公司
				    try
				    {
				    	SCompanyCo sCompanyCo1=sCompanyCoRepository.findByCompanyIdAndNameAndType
				    			(soCompany.getIdCompany(), securityUtils.getCurrentDBUser().getCompany().getCompanyName(),2l);
				         sSo.setSCompanyCo(sCompanyCo1);
				    }catch(Exception e)
				    {
				    	e.printStackTrace();
				    }

				    sSo.setSStatusDic(sStatusDicRepository.findOne(14l)); //编辑
				    sSo.setRemark("客户: " +securityUtils.getCurrentDBUser().getCompany().getCompanyName() +", 合作公司Id： " +idWanglai);
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
//					采购订单
//					公司：$!{company}
//					订单编码: $!{poNo}, 总价： $!{totalPrice}
//					条目：
//					$!{materials}
//					备注：
//					$!{remark}
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
				//	System.out.println("matertials: " + materials);
					try{
						emailSenderService.sendEmail(emails,"poTemplate.vm",  "新订单", model, null);
					}catch(Exception e)
					{
						e.printStackTrace();
					}
				
			}
			
			 v.setMsg(msg);
			// System.out.println(" valid: " + v.getValid() + ", msg: " + msg);
			 return v;
		}

}
