package com.jms.service.store;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.Company;
import com.jms.domain.db.PBom;
import com.jms.domain.db.PWo;
import com.jms.domain.db.SComCom;
import com.jms.domain.db.SInventory;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SNomaterialReport;
import com.jms.domain.db.SNomaterialReportSum;
import com.jms.domain.db.SPic;
import com.jms.domain.db.SPoMaterial;
import com.jms.domain.db.SPoTemp;
import com.jms.domain.db.SSo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.s.WSMaterial;
import com.jms.domain.ws.s.WSSComCom;
import com.jms.domainadapter.BeanUtil;
import com.jms.file.FileUploadService;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.p.PBomRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.s.BuyMehodRepository;
import com.jms.repositories.s.SAttachmentRepository;
import com.jms.repositories.s.SComComRepository;
import com.jms.repositories.s.SComMatPriceRepository;
import com.jms.repositories.s.SCompanyCoRepository;
import com.jms.repositories.s.SInventoryRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SNomaterialReportRepository;
import com.jms.repositories.s.SNomaterialReportSumRepository;
import com.jms.repositories.s.SPoTempRepository;
import com.jms.repositories.s.SSoRepository;
import com.jms.repositories.s.SSpoMaterialRepository;
import com.jms.repositories.s.SSpoRepository;
import com.jms.web.security.SecurityUtils;


@Service
@Transactional
public class MrpService {

	private static final Logger logger = 
			LogManager.getLogger(
			MrpService.class
			.getCanonicalName());
	
	
	@Autowired private SecurityUtils securityUtils;
	@Autowired private SMaterialRepository sMaterialRepository;
	@Autowired private SSpoRepository sSpoRepository;
	@Autowired private SpoMaterialService spoMaterialService;
	@Autowired private FileUploadService fileUploadService;
	@Autowired private SAttachmentRepository sAttachmentRepository;
	@Autowired private SComMatPriceRepository sComMatPriceRepository;
	@Autowired private  SNomaterialReportRepository sNomaterialReportRepository;
	
	@Autowired private SInventoryRepository sInventoryRepository;
	@Autowired private SCompanyCoRepository sCompanyCoRepository;
	@Autowired private PBomRepository pBomRepository;
	@Autowired private PWoRepository pWoRepository;
	@Autowired private SSpoMaterialRepository sSpoMaterialRepository;
	@Autowired private SSoRepository sSoRepository;
	@Autowired private SNomaterialReportSumRepository sNomaterialReportSumRepository;
	@Autowired private SPoTempRepository sPoTempRepository;
	
	//type
//	<option value="1">生产原料数</option>
//	<option value="2">库存</option>
//	<option value="3">OPEN PO</option>
//	<option value="4">安全库存</option>
//	<option value="5">确认缺料</option>
//	<option value="6">临时PO</option>
//	<option value="7">采购申请</option>
	
	
	//生成缺料详情
	@Transactional(readOnly=false)
	public Valid loadComprice(String lvl)
	{	
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<SMaterial> mats = null;
		if(lvl==null||lvl.isEmpty())
		{
			mats = sMaterialRepository.getByCompanyId(companyId);
		}
		else
		{
			mats = sMaterialRepository.getByCompanyIdAndLvl(companyId,lvl);
		}
		List<SNomaterialReport>  ss = sNomaterialReportRepository.findByIdCompany(companyId);//未确认的
		sNomaterialReportRepository.delete(ss);
		   
		 //以前工单
		 Map<Long, List<SNomaterialReport>> yiqiangongdan = new HashMap<Long,List<SNomaterialReport>>();
		   
		
		
		//List<SNomaterialReport>  ws = new ArrayList<SNomaterialReport>();  //生产原料数
		for(SMaterial m: mats)
		{
			//type=4 安全库存. 
			if(m.getSafetyInv()!=null&&!m.getSafetyInv().equals(0l))
			{
				SNomaterialReport s = new SNomaterialReport();
				s.setIdCompany(companyId);
				s.setMaterial(m.getPno()+"_"+m.getRev());
				s.setIdMat(m.getIdMaterial());
				if(m.getSUnitDicByUnitInf()!=null)
				{
					s.setInvUnit(m.getSUnitDicByUnitInf().getName());
				}
				s.setQty(-m.getSafetyInv());
				s.setType(4l);
				sNomaterialReportRepository.save(s);
			}
			
			//type=2 库存
		   List<SInventory>	invs = sInventoryRepository.findByMaterialId(m.getIdMaterial());
		   Long invQty=0l;
		   for(SInventory i:invs)
		   {
			   invQty = invQty + i.getQty();
		   }
		   if(!invQty.equals(0l))
		   {
			    SNomaterialReport s = new SNomaterialReport();
				s.setIdCompany(companyId);
				s.setMaterial(m.getPno()+"_"+m.getRev());
				s.setIdMat(m.getIdMaterial());
				if(m.getSUnitDicByUnitInf()!=null)
				{
					s.setInvUnit(m.getSUnitDicByUnitInf().getName());
				}
				s.setType(2l);
				s.setQty(invQty);
				sNomaterialReportRepository.save(s);
		   }
		   
		   //type =1, 生产原料数， 及时open wo
		 // System.out.println("材料： " + m.getIdMaterial() +", mat: " + m.getPno());
		  List<PBom> boms = pBomRepository.findBomsByMaterialId(m.getIdMaterial());
		  for(PBom bom: boms)
		  {
			 SMaterial product = bom.getPBom().getSMaterial();
			// System.out.println("成品： " + product.getIdMaterial() +", 成品: " + product.getPno());
			 List<PWo>  wos =pWoRepository.findOpenWoByCompanyIdAndProductId(companyId, product.getIdMaterial());
			 for(PWo wo:wos)
			 {
				// System.out.println("工单： " +wo.getWoNo() +", 原料: " + m.getPno());
				Long qty_finished = wo.getActQty()==null?0l:wo.getActQty();
				Long qty = wo.getQty()-qty_finished;
				if(qty>0l)
				{
					    Float q = bom.getQpu()==null?1l:bom.getQpu();
					    Float waste = bom.getWastage()==null?0f: bom.getWastage();
					    SNomaterialReport s = new SNomaterialReport();
						s.setIdCompany(companyId);
						s.setMaterial(m.getPno()+"_"+m.getRev());
						s.setIdMat(m.getIdMaterial());
						if(m.getSUnitDicByUnitInf()!=null)
						{
							s.setInvUnit(m.getSUnitDicByUnitInf().getName());
						}
						s.setType(1l);
						double a = (qty*q)/(1-waste);
						
						double b = Math.ceil(a);
						s.setQty((long)(-b));
						
						s.setOpenWoQty(qty);
						s.setProduct(product.getPno()+product.getRev());
						s.setProDate(wo.getSt());
						s.setPWNo(wo.getWoNo());
						s.setWoQty(wo.getQty());
						s.setWoFinishDate(wo.getFt());
						sNomaterialReportRepository.save(s);
						
						List<SNomaterialReport> ls = yiqiangongdan.get(s.getIdMat());
						if(ls==null)
						{
							ls = new ArrayList<SNomaterialReport>();
						}
						ls.add(s);
						yiqiangongdan.put(s.getIdMat(), ls);
				//		ws.add(s);
					
				}
				 
			 }
		  }
		  
		  
		  
		  //type=3 open PO
		 List<SPoMaterial> spoMaterials =  sSpoMaterialRepository.findOpenSposByMaterialId(m.getIdMaterial());
		 for(SPoMaterial sm: spoMaterials)
		 {
			 Long qty = sm.getQtyPo();
			 Long qtyRec = sm.getQtyReceived()==null?0l:sm.getQtyReceived();
			 Long openQty = qty-qtyRec;
			 
			 
			    SNomaterialReport s = new SNomaterialReport();
				s.setIdCompany(companyId);
				s.setMaterial(m.getPno()+"_"+m.getRev());
				s.setIdMat(m.getIdMaterial());
				if(m.getSUnitDicByUnitInf()!=null)
				{
					s.setInvUnit(m.getSUnitDicByUnitInf().getName());
				}
				s.setType(3l);
				
				s.setQty(openQty);
				s.setPWNo(sm.getSPo().getCodePo());
				
				s.setProDate(sm.getDeliveryDate());

				sNomaterialReportRepository.save(s);
 
		 }
		 
		   
		 
		 
		 
		  //type=5 确认缺料
		     List<SNomaterialReportSum> sNomaterialReportSums = sNomaterialReportSumRepository.findByIdCompanyAndIdMatAndStatus(companyId, m.getIdMaterial(), 2l);
			
			 for(SNomaterialReportSum sum: sNomaterialReportSums)
			 {
				    SNomaterialReport s = new SNomaterialReport();
					s.setIdCompany(companyId);
					s.setMaterial(m.getPno()+"_"+m.getRev());
					s.setIdMat(m.getIdMaterial());
					if(m.getSUnitDicByUnitInf()!=null)
					{
						s.setInvUnit(m.getSUnitDicByUnitInf().getName());
					}
					s.setType(5l);
					
					s.setQty(-sum.getSQty());
					s.setProDate(sum.getRDate());		
					sNomaterialReportRepository.save(s);
	 
			 }
			 
			 
			 
			  //type=6 临时po
		     List<SPoTemp> sPoTemps = sPoTempRepository.findByIdCompanyAndIdMat(companyId, m.getIdMaterial());
			
			 for(SPoTemp spo: sPoTemps)
			 {
				    SNomaterialReport s = new SNomaterialReport();
					s.setIdCompany(companyId);
					s.setMaterial(m.getPno()+"_"+m.getRev());
					s.setIdMat(m.getIdMaterial());
					if(m.getSUnitDicByUnitInf()!=null)
					{
						s.setInvUnit(m.getSUnitDicByUnitInf().getName());
					}
					s.setType(6l);
					
					s.setQty(spo.getSPoQty());
				    s.setProDate(spo.getRDate());
					
					sNomaterialReportRepository.save(s);
	 
			 }
			 
			
		}
		
		
		
		Map<Long,List<SNomaterialReportSum>> sumMap = new HashMap<Long,List<SNomaterialReportSum>>();
		
		//status 1未确认，2已确认，3临时po
		List<SNomaterialReportSum> sums = sNomaterialReportSumRepository.findByIdCompanyAndStatus(companyId,1l);
		sNomaterialReportSumRepository.delete(sums);
		List<SNomaterialReportSum> sums1 = sNomaterialReportSumRepository.findByIdCompanyAndStatus(companyId,3l);
		sNomaterialReportSumRepository.delete(sums1);
		List<SPoTemp> spos = sPoTempRepository.findByIdCompany(companyId);
		sPoTempRepository.delete(spos);
		//生产汇总
		List<SNomaterialReport> ws = sNomaterialReportRepository.findByIdCompanyAndTypeOrderByProDateAsc(companyId, 1l);
	//	Long noMa = 0l;  //上次结转
		
		
		for(SNomaterialReport s :ws)
		{
		
		    //System.out.println(" 缺料： " + s.getMaterial() +", 时间： "  +s.getProDate() +", 完成时间： " +s.getWoFinishDate());
		  // noMa = noMa + s.getQty();
		   
		   Long invQty= getInv( s.getIdMat() );
		   Long safeQty = 0l;
		   SMaterial mat = sMaterialRepository.findOne(s.getIdMat());
		   if(mat.getSafetyInv()!=null)
		   {
			   safeQty = mat.getSafetyInv();
		   }
		   Long openPo = getOpenPoBefore(s.getIdMat(),s.getProDate());
		   
		   Long sureNoMat=0l;
		   List<SNomaterialReportSum> sNomaterialReportSums = sNomaterialReportSumRepository.findByIdCompanyAndIdMatAndStatus(companyId, s.getIdMat(), 2l);
		   
		  for(SNomaterialReportSum srm:sNomaterialReportSums)
		  {
			  if(srm.getRDate().before(s.getProDate())||srm.getRDate().equals(s.getProDate()))
			  {
				  sureNoMat = sureNoMat+ srm.getPQty();
			  }
		  }
		   
		  
		  Long tmpPo = 0l;
		     List<SPoTemp> sPoTemps = sPoTempRepository.findByIdCompanyAndIdMat(companyId, s.getIdMat());
			
			 for(SPoTemp spo: sPoTemps)
			 {
				 if(spo.getRDate().before(s.getProDate())||spo.getRDate().equals(s.getProDate()))
				 {
					 tmpPo = tmpPo + spo.getSPoQty();
				 }
			 }
		  
			System.out.println("以前工单： mat: " + s.getMaterial() +", date:  " + s.getProDate());
			Long openWo =  getOpenWoBefore( s.getIdMat(),s.getProDate(),yiqiangongdan); //以前工单数
		  
//		  System.out.println(" 物料： " +s.getMaterial() +", wo: " +s.getPWNo() +", 需料日期: " + s.getProDate() +", 工单数量: "+ s.getQty()
//		  +",  安全库存： " + safeQty +", 库存 : " + invQty +", openPo: " + openPo +", 确认缺料: " + sureNoMat +", 临时PO： " + tmpPo +", 以前工单数：  " + openWo);
//		  
		 
		  Long jiezhuan = 0l;
		  if(sumMap.get(s.getIdMat())!=null)
		  {
			  for(SNomaterialReportSum k: sumMap.get(s.getIdMat()))
			  {
				  jiezhuan = jiezhuan+ k.getPQty();
			  }
		  }
		  
		  //计算缺料状况    
		  Long qty = s.getQty() + invQty + sureNoMat + tmpPo -safeQty + openWo +jiezhuan +openPo;
		  
		  System.out.println("上次结转： " + jiezhuan);
		  System.out.println("缺料状况: " + qty);
		  
		   if(qty<0l)
		   {
			   SNomaterialReportSum sr = new  SNomaterialReportSum();
			   sr.setPQty(-qty);
			   sr.setSQty(qty);
			   sr.setIdCompany(companyId);
			   sr.setMaterial(s.getMaterial());
			   sr.setIdMat(s.getIdMat());
			   sr.setInvUnit(s.getInvUnit());
			   sr.setFDate(s.getWoFinishDate());
			   sr.setRDate(s.getProDate());
			   sr.setStatus(1l);
			   sNomaterialReportSumRepository.save(sr);
			   //noMa = -qty;
			   
			  List<SNomaterialReportSum>  sss = sumMap.get(s.getIdMat());
			  if(sss==null)
			  {
				  sss = new ArrayList<SNomaterialReportSum>();
			  }
			  sss.add(sr);
			  sumMap.put(s.getIdMat(), sss);
		   }
		   
		
		}

		Valid v = new Valid();
		v.setValid(true);
		return v;
	}
	
	
	
	
	
	
	
	//生成缺料汇总
	//timetype 1,2,3,4,5 天，周，月，季，年
	@Transactional(readOnly=false)
	public Valid loadNoMaterialReportSum(Long timetype,Integer  time)
	{	
		
		Valid v =new Valid();
		
		
		
		v.setValid(true);
		return v;
		
	}
	
	
	
	
	
	

	@Transactional(readOnly=true)
	public Long getOpenSo(Long materialId)
	{
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<SSo> sos = sSoRepository.findOpenSo(companyId, materialId);
		Long openSo =0l;
		for(SSo so:sos)
		{
			 Long qty = so.getQtySo();
			 Long qtySend = so.getQtyDelivered()==null?0l:so.getQtyDelivered();
			 Long openQty = qty-qtySend;
			 openSo = openSo+openQty;
		}
		return openSo;
	}
	
	@Transactional(readOnly=true)
	public Long getOpenPo(Long materialId)
	{
		 List<SPoMaterial> spoMaterials =  sSpoMaterialRepository.findOpenSposByMaterialId(materialId);
		 Long openPo=0l;
		 for(SPoMaterial sm: spoMaterials)
		 {
			 Long qty = sm.getQtyPo();
			 Long qtyRec = sm.getQtyReceived()==null?0l:sm.getQtyReceived();
			 Long openQty = qty-qtyRec;
			
			 openPo =openPo+ openQty;
		 }
		 return openPo;
	}
	
	
	@Transactional(readOnly=true)
	public Long getOpenPoBefore(Long materialId,Date pDate)
	{
	
		 //System.out.println("pdate: " + pDate.toString());
		 List<SPoMaterial> spoMaterials =  sSpoMaterialRepository.findOpenSposByMaterialIdBeforeDate(materialId,pDate);
		 Long openPo=0l;
		 for(SPoMaterial sm: spoMaterials)
		 {
			// System.out.println("open PO before:   " + sm.getDeliveryDate());
			 Long qty = sm.getQtyPo();
			 Long qtyRec = sm.getQtyReceived()==null?0l:sm.getQtyReceived();
			 Long openQty = qty-qtyRec;
			
			 openPo =openPo+ openQty;
		 }
		 return openPo;
	}
	
	
	
	
	@Transactional(readOnly=true)
	public Long getOpenWo(Long materialId)
	{
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		Long opeWo =0l;
		//List<PWo>  wos =pWoRepository.findOpenWoByCompanyIdAndProductId(companyId, product.getIdMaterial());
	    List<PWo>  wos =pWoRepository.findOpenWoByCompanyIdAndProductId(companyId, materialId);
	    for(PWo wo:wos)
		{
			Long qty_finished = wo.getActQty()==null?0l:wo.getActQty();
			Long qty = wo.getQty()-qty_finished;	
			opeWo = opeWo+qty;
		}		 
       return opeWo;
	}
	
	
	
	//yiqiangongdan
	public Long getOpenWoBefore(Long materialId, Date d,  Map<Long, List<SNomaterialReport>> yiqiangongdan)
	{
		Long opeWo =0l;
		
		List<SNomaterialReport> ls = yiqiangongdan.get(materialId);
		
		for(SNomaterialReport l:ls)
		{
			if(l.getProDate().before(d))
			{
				opeWo = opeWo+ l.getQty();
			}
		}
		return opeWo;
	}
	
//	
//	@Transactional(readOnly=true)
//	public Long getOpenWoBefore(Long materialId, Date d)
//	{
//		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
//		Long opeWo =0l;
//		  List<PBom> boms = pBomRepository.findBomsByMaterialId(materialId);
//		  for(PBom bom: boms)
//		  {
//	    List<PWo>  wos =pWoRepository.findOpenWoByCompanyIdAndProductId(companyId, materialId);  
//	    for(PWo wo:wos)
//		{
//	    	if(wo.getSt().before(d))
//	    	{
//	    		System.out.println("以前的工单: " + wo.getWoNo() +", woId: " + wo.getIdWo() +", qty: " + wo.getQty() +", date: " + wo.getSt());
//	    		Long qty_finished = wo.getActQty()==null?0l:wo.getActQty();
//				Long qty = wo.getQty()-qty_finished;	
//				opeWo = opeWo+qty;
//	    	}
//		
//		}	
//		  }
//       return opeWo;
//	}
//	
	
	
	@Transactional(readOnly=true)
	public Long getInv(Long materialId)
	{
		   List<SInventory>	invs = sInventoryRepository.findByMaterialId(materialId);
		   Long invQty=0l;
		   for(SInventory i:invs)
		   {
			   invQty = invQty + i.getQty();
		   }
		   return invQty;
	}
}
