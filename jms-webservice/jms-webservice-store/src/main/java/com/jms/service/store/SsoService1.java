package com.jms.service.store;


import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.Company;
import com.jms.domain.db.PBom;
import com.jms.domain.db.PWo;
import com.jms.domain.db.PWoBom;
import com.jms.domain.db.SCompanyCo;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMtfNo;
import com.jms.domain.db.SPic;
import com.jms.domain.db.SSo;
import com.jms.domain.db.SSoNum;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.s.WSMaterial;
import com.jms.domain.ws.s.WSSSoRemark;
import com.jms.domain.ws.s.WSSso;
import com.jms.domain.ws.s.WSSsoItem;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.p.PBomRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWoBomRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.s.SCompanyCoRepository;
import com.jms.repositories.s.SCurrencyTypeRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SMtfNoRepository;
import com.jms.repositories.s.SSoNumRepository;
import com.jms.repositories.s.SSoRepository;
import com.jms.repositories.s.SSpoMaterialRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.s.SStermDicRepository;
import com.jms.repositories.s.SStkTypeDicRepository;
import com.jms.web.security.SecurityUtils;


@Service
@Transactional
public class SsoService1 {

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
	
	@Autowired private SSoNumRepository sSoNumRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private PBomRepository pBomRepository;
	@Autowired
	private  PWoBomRepository pWoBomRepository;

	public Valid saveSSo(WSSso wsSso) throws Exception {
		
		Company company = securityUtils.getCurrentDBUser().getCompany();
		SSoNum soNum;
		//create
		Long company2Id = null;
		if(wsSso.getSoNum()==null||wsSso.getSoNum().equals(0l))
		{
			if(wsSso.getCodeSo()==null)
			{
				SMtfNo smtfNo = sMtfNoRepository.getByCompanyIdAndType(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), 11l);
			    long currentVal =smtfNo.getCurrentVal()+1;
			    smtfNo.setCurrentVal(currentVal);
			    sMtfNoRepository.save(smtfNo);
			    String codeSo = smtfNo.getPrefix()+String.format("%08d", currentVal);
			    wsSso.setCodeSo(codeSo);
			}
		    soNum = new SSoNum(wsSso.getCodeSo());
		    soNum = sSoNumRepository.save(soNum);
		}
		//update
		else
		{
			soNum = sSoNumRepository.findOne(wsSso.getSoNum());	
			//System.out.println(wsSso.getSoNum());
			List<SSo> sos =sSoRepository.findBySoNum(wsSso.getSoNum());
			if(!sos.isEmpty())
			company2Id =sos.get(0).getIdCompany2(); 
			sSoRepository.delete(sos);
			wsSso.setCodeSo(soNum.getNam());
		}
		for(WSSsoItem item: wsSso.getSoItems().values())
		{
			SSo dbSso = new SSo();

			dbSso.setAutoRemark(item.getAutoRemark());
			dbSso.setCodeSo(wsSso.getCodeSo());
			dbSso.setCompany(company);
			dbSso.setCoOrderNo(wsSso.getCoOrderNo());
			dbSso.setDateOrder(new Date());
			dbSso.setDeliveryDate(item.getDeliveryDate());
			dbSso.setIdCompany1(wsSso.getIdCompany1());
			dbSso.setIdCompany2(wsSso.getIdCompany2());
			dbSso.setQtySo(item.getQtySo());
			dbSso.setRemark(item.getRemark());
			dbSso.setStatusAll(wsSso.getStatusAll());
			dbSso.setSoNum(soNum.getId());
			dbSso.setTotalAmount(item.getTotalAmount());
			dbSso.setTot(wsSso.getTotalAmount());
			dbSso.setTaxRate(wsSso.getTaxRate());
			dbSso.setExchange(wsSso.getExchange());
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
			if(wsSso.getIdCompany2()!=null)
			{
				Company c = companyRepository.findOne(wsSso.getIdCompany2());
				if(c!=null)
				{
					System.out.println("合作公司: " + c.getCompanyName());
					SCompanyCo sCompanyCo = sCompanyCoRepository.findByCompanyIdAndNameAndType(company.getIdCompany(), c.getCompanyName(), 2l);
					System.out.println("友好公司: " + sCompanyCo.getName());
					dbSso.setSCompanyCo(sCompanyCo);
				}
			}
			if(company2Id!=null)
			{
				dbSso.setIdCompany2(company2Id);
			}

			if(wsSso.getsStatusId()!=null)
			{
				dbSso.setSStatusDic(sStatusDicRepository.findOne(wsSso.getsStatusId()));
			}
			if(item.getMaterialId()!=null)
			{
				dbSso.setSMaterial(sMaterialRepository.findOne(item.getMaterialId()));
			}
			dbSso.setUsers(securityUtils.getCurrentDBUser());
			dbSso.setUPrice(item.getUprice());
			
			dbSso = sSoRepository.save(dbSso);
			//System.out.println("status: " + wsSso.getsStatusId() +", sowo: " + company.getSoWo());
			if(wsSso.getsStatusId().equals(18l)&&company.getSoWo()!=null&&company.getSoWo().equals(1l))//激活状态
			{
				//System.out.println("生成工单！");
				genertorWoBySo(dbSso);
			}
		}
		
		
		Valid valid = new Valid();
		valid.setValid(true);
		return valid;
	}
	

	
	//ssoId 变成soNum
	public WSSso findSso(Long ssoId) throws Exception 
	{
		//SSo sso = sSoRepository.findOne(ssoId);
		List<SSo> sos = sSoRepository.findBySoNum(ssoId);
		WSSso ws  = new WSSso();
		int i = 1;
		for(SSo so:sos)
		{
			System.out.println(i);
			if(i==1)
			{
				ws.setAutoRemark(so.getAutoRemark());
				if(so.getSCompanyCo()!=null)
				{
					ws.setsCompanyCoId(so.getSCompanyCo().getId());
					ws.setCodeCo(so.getSCompanyCo().getCode());
				}
				if(so.getSTermDicByFreightTerm()!=null)
				{
					ws.setFreightTerm(so.getSTermDicByFreightTerm().getName());
					ws.setFreightTermId(so.getSTermDicByFreightTerm().getId());
				}
				if(so.getSTermDicByPaymentTerm()!=null)
				{
					ws.setPaymentTerm(so.getSTermDicByPaymentTerm().getName());
					ws.setPaymentTermId(so.getSTermDicByPaymentTerm().getId());
				}
				if(so.getSStatusDic()!=null)
				{
					ws.setsStatus(so.getSStatusDic().getName());
					ws.setsStatusId(so.getSStatusDic().getId());
				}
				if(so.getSMaterial()!=null)
				{
					ws.setMaterialId(so.getSMaterial().getIdMaterial());
				
				}
			
				if(so.getUsers()!=null)
				{
					ws.setUserName(so.getUsers().getName());
				}
				ws.setSoNum(so.getSoNum());
				ws.setCodeSo(so.getCodeSo());
				ws.setDateOrder(so.getDateOrder());
				ws.setIdCompany1(so.getIdCompany1());
				ws.setIdCompany2(so.getIdCompany2());
				ws.setCodeCo(so.getCoOrderNo());
				ws.setTotalAmount(so.getTot());
				ws.setCoOrderNo(so.getCoOrderNo());
				ws.setTaxRate(so.getTaxRate());
				ws.setExchange(so.getExchange());
			}
		
			
			WSSsoItem item  = new WSSsoItem();
			item.setDeliveryDate(so.getDeliveryDate());
			item.setQtyDelivered(so.getQtyDelivered());
			item.setQtySo(so.getQtySo());
			item.setTotalAmount(so.getTotalAmount());
			if(so.getSMaterial()!=null)
			{
				item.setMaterialId(so.getSMaterial().getIdMaterial());
			
			}
			item.setUprice(so.getUPrice());
			item.setRemark(so.getRemark());
			ws.getSoItems().put("item"+i, item);
			  i++;
			}
		 
		//ws.setSoItems(soItems);
		return ws;
	}
	
	

	public Valid saveSoAutoRemark(WSSSoRemark wsSSoRemark) {
	//	System.out.println("change status: "  +wsSSoRemark.getIdSo() +", status: " + wsSSoRemark.getStatusId());
		Company company = securityUtils.getCurrentDBUser().getCompany();
		//SSo so = sSoRepository.findOne(wsSSoRemark.getIdSo());	
		//List<SSo> sos = sSoRepository.findBySoNum(wsSSoRemark.getIdSo());
		List<SSo> sos = sSoRepository.findBySoNum(wsSSoRemark.getIdSo());
		for(SSo sso:sos)
		{
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
		}
		

		
		Valid valid = new Valid();
		valid.setValid(true);
		return valid;
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
	    	wo = pWoRepository.save(wo);
	    	
	    	
			PBom pbom = pBomRepository.findProductByMaterialId(materialId);
			if(pbom!=null)
			{
				List<PBom> boms = pBomRepository.findMaterialsByProductId(pbom.getIdBom());
				//Long qty = wo.getQty();
				for(PBom bom:boms)
				{
					PWoBom woBom = new PWoBom();
					woBom.setIdMat(bom.getSMaterial().getIdMaterial());
					woBom.setIdBomLabel(pbom.getPBomLabel().getIdBomLabel());
					woBom.setLvl(bom.getLvl());
					woBom.setOrderBy(bom.getOrderBy());
					woBom.setPWo(wo);
					woBom.setQpu(bom.getQpu());
					woBom.setWastage(bom.getWastage());
					woBom.setWip(bom.getWip());
					Float waste = bom.getWastage()==null?0f: bom.getWastage();
					Float q = bom.getQpu()==null?1l:bom.getQpu(); 
	                double a = (qty*q)/(1-waste);
					double b = Math.ceil(a);
					woBom.setQty((long)(b));
					woBom.setQtyRev(0l);
					pWoBomRepository.save(woBom);
				}
			}
			
			
	    			
	    }
	   
	   // wo.set
		Valid v = new Valid();
		v.setValid(true);
		return v;
	}
	

}
