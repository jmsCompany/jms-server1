package com.jms.service.store;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMaterialPic;
import com.jms.domain.db.SPic;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.store.WSMaterial;
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
				sMaterialList =sMaterialRepository.getByCompanyIdAndMaterialTypeAndQuery(idCompany, idMaterialType, q);
			}
			
		}
		
		for(SMaterial s : sMaterialList)
		{
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
	//	SMaterial m = sMaterialRepository.findOne(materialId);
		sMaterialRepository.delete(materialId);
		valid.setValid(true);
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
				wsMc.setFileName(spic.getFilename());
				wsMc.setFileId(spic.getId());
			}
			return wsMc;

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

}
