package com.jms.service.store;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.domain.db.Company;
import com.jms.domain.db.FCostCenter;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMaterialCategory;
import com.jms.domain.db.SMaterialPic;
import com.jms.domain.db.SPic;
import com.jms.domain.db.SUnitDic;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.s.WSMaterial;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.f.FCostCenterRepository;
import com.jms.repositories.s.SMaterialCategoryRepository;
import com.jms.repositories.s.SMaterialPicRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SMaterialTypeDicRepository;
import com.jms.repositories.s.SPicRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.s.SUnitDicRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class MaterialService {

	private static final Logger logger = LogManager.getLogger(MaterialService.class
			.getCanonicalName());
	@Autowired
	private SMaterialCategoryRepository sMaterialCategoryRepository;
	@Autowired
	private SMaterialRepository sMaterialRepository;
	@Autowired
	private SStatusDicRepository sStatusDicRepository;
	
	@Autowired 
	private  SecurityUtils securityUtils;
	@Autowired 
	private FCostCenterRepository fCostCenterRepository;
	@Autowired 
	private SMaterialTypeDicRepository sMaterialTypeDicRepository;
	@Autowired 
	private SUnitDicRepository sUnitDicRepository;
	@Autowired 
	private CompanyRepository companyRepository;
	@Autowired 
	private SMaterialPicRepository sMaterialPicRepository;
	@Autowired
	private SPicRepository sPicRepository;
	
	
	@Transactional(readOnly=true)
	public List<WSMaterial> getMaterials(Long idCompany,Long idMaterialType,String q) throws Exception {
	
		
		List<WSMaterial> wsMaterialList = new ArrayList<WSMaterial>();
		List<SMaterial> sMaterialList;
		if(idMaterialType==null)
		{
			if(q==null)
			{
				sMaterialList =sMaterialRepository.getByCompanyId(idCompany);
			}
			else
			{
				//logger.debug("idCompany: " + idCompany +", q: " + q);
				q= '%'+q+'%';
				sMaterialList =sMaterialRepository.getByCompanyIdAndQuery(idCompany,q);
			}
			
		}
		else 
		{
			if(q==null)
			{
				sMaterialList =sMaterialRepository.getByCompanyIdAndMaterialType(idCompany, idMaterialType);
			}
			else
			{
				q= '%'+q+'%';
				sMaterialList =sMaterialRepository.getByCompanyIdAndMaterialTypeAndQuery(idCompany, idMaterialType, q);
			}
			
		}
		
		for(SMaterial s : sMaterialList)
		{
			//logger.debug("material: " + s.getPno());
			wsMaterialList.add(toWSMaterial(s));
		}
		
		
        return wsMaterialList;
		
	}

	
	
	@Transactional(readOnly=true)
	public List<WSMaterial> getMaterialsByTypesAndQ(Long idCompany,List<Long> types,String q) throws Exception {
	
		
		List<WSMaterial> wsMaterialList = new ArrayList<WSMaterial>();
		List<SMaterial> sMaterialList;
		if(types==null)
		{
			if(q==null)
			{
				sMaterialList =sMaterialRepository.getByCompanyId(idCompany);
			}
			else
			{
				//logger.debug("idCompany: " + idCompany +", q: " + q);
				q= '%'+q+'%';
				sMaterialList =sMaterialRepository.getByCompanyIdAndQuery(idCompany,q);
			}
			
		}
		else 
		{
			if(q==null)
			{
				sMaterialList =sMaterialRepository.getByCompanyIdAndMaterialTypes(idCompany, types);
			}
			else
			{
				q= '%'+q+'%';
				sMaterialList =sMaterialRepository.getByCompanyIdAndMaterialTypesAndQuery(idCompany, types, q);
			}
			
		}
		
		for(SMaterial s : sMaterialList)
		{
			//logger.debug("material: " + s.getPno());
			wsMaterialList.add(toWSMaterial(s));
		}
		
		
        return wsMaterialList;
		
	}
	
	
	
	
	
	
	
	
	@Transactional(readOnly=false)
	public WSMaterial saveMaterial(WSMaterial m) throws Exception {
		
		SMaterial dbMaterial;
		if(m.getIdMaterial()!=null&&!m.getIdMaterial().equals(0l))
		{
			dbMaterial = sMaterialRepository.findOne(m.getIdMaterial());
		}
		else
		{
			dbMaterial = new SMaterial();
		}
		dbMaterial = toDBSmaterial(m,dbMaterial);
		dbMaterial =sMaterialRepository.save(dbMaterial);
		m.setIdMaterial(dbMaterial.getIdMaterial());
		
		if(m.getFileId()!=null)
		{
			SMaterialPic sp;
			List<SMaterialPic> mps =sMaterialPicRepository.findBySMaterialId(dbMaterial.getIdMaterial());
			if(mps!=null&&!mps.isEmpty())
			{
				sp=mps.get(0);
			}
			else
			{
			    sp = new SMaterialPic();
				sp.setSMaterial(dbMaterial);
			}
		
			sp.setSPic(sPicRepository.findOne(m.getFileId()));
			sMaterialPicRepository.save(sp);
		}
		return m;				
		
	}
	
	@Transactional(readOnly=false)
	public Valid deleteMaterial(Long materialId)
	{
		Valid valid = new Valid();
		SMaterial m = sMaterialRepository.findOne(materialId);
		if(m.getPBoms().isEmpty()&&m.getPRoutines().isEmpty()&&m.getSInventories().isEmpty()&&m.getSMtfMaterials().isEmpty()&&m.getSPoMaterials().isEmpty())
		{
			 sMaterialRepository.delete(materialId);
			 valid.setValid(true);
		}
		else
		{	
			valid.setValid(false);
			
		}
	
		return valid;
	}
	
	
	@Transactional(readOnly=true)
	public WSMaterial findMaterial(Long materialId) throws Exception
	{	
		WSMaterial wsMc = new WSMaterial();
		if(materialId==null)
			return wsMc;
		SMaterial m = sMaterialRepository.findOne(materialId);
			if(m==null)
				return wsMc;
			wsMc = toWSMaterial(m); 
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

	public Boolean checkPno(String pno, Long idMaterial) {

		String dbPno = "";
		// 已有物料修改
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		if (idMaterial != null) {
			SMaterial material = sMaterialRepository.findOne(idMaterial);
			dbPno= material.getPno();
			if (pno != null && !pno.isEmpty()) {
				if (sMaterialRepository.getByCompanyIdAndPno(companyId, pno) == null
						|| pno.equals(dbPno)) {
					return true;
				} else {
					return false;
				}
			} else
				return true;
		} else {
			if (pno == null || pno.isEmpty())
				return false;
			else {
				if (sMaterialRepository.getByCompanyIdAndPno(companyId, pno) == null) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	
	
	public SMaterial toDBSmaterial(WSMaterial wsMaterial,SMaterial sMaterial) throws Exception
	{
	
		SMaterial dbMaterial= (SMaterial)BeanUtil.shallowCopy(wsMaterial, SMaterial.class, sMaterial);
		dbMaterial.setCompany(securityUtils.getCurrentDBUser().getCompany());
		
		if(wsMaterial.getfCostCenterId()!=null)
		{
			dbMaterial.setFCostCenter(fCostCenterRepository.findOne(wsMaterial.getfCostCenterId()));
		}
		if(wsMaterial.getMaterialCategoryId()!=null)
		{
			dbMaterial.setSMaterialCategory(sMaterialCategoryRepository.findOne(wsMaterial.getMaterialCategoryId()));
		}
		if(wsMaterial.getsMaterialTypeDicId()!=null)
		{
			dbMaterial.setSMaterialTypeDic(sMaterialTypeDicRepository.findOne(wsMaterial.getsMaterialTypeDicId()));
		}
		if(wsMaterial.getsStatusDicId()!=null)
		{
			dbMaterial.setSStatusDic(sStatusDicRepository.findOne(wsMaterial.getsStatusDicId()));
		}
		if(wsMaterial.getsUnitDicByUnitInfId()!=null)
		{
			dbMaterial.setSUnitDicByUnitInf(sUnitDicRepository.findOne(wsMaterial.getsUnitDicByUnitInfId()));
		}
		if(wsMaterial.getsUnitDicByUnitPurId()!=null)
		{
			dbMaterial.setSUnitDicByUnitPur(sUnitDicRepository.findOne(wsMaterial.getsUnitDicByUnitPurId()));
		}
		dbMaterial.setCost(wsMaterial.getCost());
		return dbMaterial;
	}
	
	
	public WSMaterial toWSMaterial(SMaterial sMaterial) throws Exception
	{
		WSMaterial wsMaterial = (WSMaterial)BeanUtil.shallowCopy(sMaterial, WSMaterial.class, null);
		if(sMaterial.getCompany()!=null)
		{
			wsMaterial.setCompanyId(sMaterial.getCompany().getIdCompany());
			wsMaterial.setCompanyName(sMaterial.getCompany().getCompanyName());
		}
		if(sMaterial.getFCostCenter()!=null)
		{
			wsMaterial.setfCostCenter(sMaterial.getFCostCenter().getDes());
			wsMaterial.setfCostCenterId(sMaterial.getFCostCenter().getIdCostCenter());
		}
		if(sMaterial.getSMaterialCategory()!=null)
		{
			wsMaterial.setMaterialCategory(sMaterial.getSMaterialCategory().getName());
			wsMaterial.setMaterialCategoryId(sMaterial.getSMaterialCategory().getId());
		}
		if(sMaterial.getSMaterialTypeDic()!=null)
		{
			wsMaterial.setsMaterialTypeDic(sMaterial.getSMaterialTypeDic().getName());
			wsMaterial.setsMaterialTypeDicId(sMaterial.getSMaterialTypeDic().getId());
		}
		if(sMaterial.getSStatusDic()!=null)
		{
			wsMaterial.setsStatusDic(sMaterial.getSStatusDic().getName());
			wsMaterial.setsStatusDicId(sMaterial.getSStatusDic().getId());
		}
		if(sMaterial.getSUnitDicByUnitInf()!=null)
		{
			wsMaterial.setsUnitDicByUnitInf(sMaterial.getSUnitDicByUnitInf().getName());
			wsMaterial.setsUnitDicByUnitInfId(sMaterial.getSUnitDicByUnitInf().getId());
		}
		if(sMaterial.getSUnitDicByUnitPur()!=null)
		{
			wsMaterial.setsUnitDicByUnitPur(sMaterial.getSUnitDicByUnitPur().getName());
			wsMaterial.setsUnitDicByUnitPurId(sMaterial.getSUnitDicByUnitPur().getId());
		}
		
		wsMaterial.setCost(sMaterial.getCost());

		
		return wsMaterial;
	}
	
	
	
	
public void loadMaterialsByCompanyId(InputStream inputStream,Long companyId)throws IOException {
		
	    Company company =companyRepository.findOne(companyId);
		CsvReader reader = new CsvReader(inputStream,',', Charset.forName("UTF-8"));
		reader.readHeaders();
	
		while(reader.readRecord())
		{
			SMaterial s = new  SMaterial();
			s.setPno(reader.get(0));
			s.setRev(reader.get(1));
			s.setDes(reader.get(2));
			
			Long type = Long.parseLong(reader.get(3));
			s.setSMaterialTypeDic(sMaterialTypeDicRepository.findOne(type));
			
			String pUnit = reader.get(4); //采购单位
		//	String sUnit = reader.get(5);//库存单位
			
			if(pUnit!=null&&!pUnit.trim().isEmpty())
			{
				SUnitDic sUnitDic = sUnitDicRepository.findByName(pUnit.trim());
				if(sUnitDic==null)
				{
					sUnitDic = new SUnitDic();
					sUnitDic.setName(pUnit.trim());
					sUnitDic =sUnitDicRepository.save(sUnitDic);
				}
				s.setSUnitDicByUnitPur(sUnitDic);
				s.setSUnitDicByUnitInf(sUnitDic);
			}
			s.setBrand(reader.get(6));
			String cost = reader.get(7);
			if(cost!=null&&!cost.trim().isEmpty())
			{
				s.setCost(new BigDecimal(cost.trim()));
			}
			
			String costCenter =reader.get(8);
			if(costCenter!=null&&!costCenter.trim().isEmpty())
			{
				FCostCenter fCostCenter= fCostCenterRepository.getByCompanyIdAndCostCenterNo(companyId, Long.parseLong(costCenter.trim()));
				if(fCostCenter==null)
				{
					fCostCenter = new FCostCenter();
					fCostCenter.setCompany(company);
					fCostCenter.setCostCenterNo(Long.parseLong(costCenter.trim()));
					fCostCenter = fCostCenterRepository.save(fCostCenter);
					
				
				}
				s.setFCostCenter(fCostCenter);
			}
			String category = reader.get(9);
			if(category!=null&&!category.trim().isEmpty())
			{
				SMaterialCategory sMaterialCategory =sMaterialCategoryRepository.findByIdCompanyAndName(companyId, category.trim());
				if(sMaterialCategory==null)
				{
					sMaterialCategory = new SMaterialCategory();
					sMaterialCategory.setCompany(company);
					sMaterialCategory.setName(category.trim());
					sMaterialCategory = sMaterialCategoryRepository.save(sMaterialCategory);
				}
				s.setSMaterialCategory(sMaterialCategory);
			}
			String mpq = reader.get(10);
			if(mpq!=null&&!mpq.trim().isEmpty())
			{
			   s.setMpq(Long.parseLong(mpq.trim()));
			}
			String safety_inv = reader.get(11);
			
			if(safety_inv!=null&&!safety_inv.trim().isEmpty())
			{
			   s.setSafetyInv(Long.parseLong(safety_inv.trim()));
			}
			s.setCompany(company);
			sMaterialRepository.save(s);
			
		}
		
	}

}
