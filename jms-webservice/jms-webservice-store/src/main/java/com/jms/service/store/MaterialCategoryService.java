package com.jms.service.store;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.Config;
import com.jms.domain.db.SBin;
import com.jms.domain.db.SMaterialCategory;
import com.jms.domain.db.SMaterialTypeDic;
import com.jms.domain.db.SStk;
import com.jms.domain.db.SysDic;
import com.jms.domain.db.SysDicD;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.s.WSBin;
import com.jms.domain.ws.s.WSMaterialCategory;
import com.jms.repositories.s.SMaterialCategoryPicRepository;
import com.jms.repositories.s.SMaterialCategoryRepository;
import com.jms.repositories.s.SMaterialTypeDicRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.system.SysDicDRepository;
import com.jms.repositories.system.SysDicRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class MaterialCategoryService {

	private static final Logger logger = LogManager.getLogger(MaterialCategoryService.class
			.getCanonicalName());
	@Autowired
	private SMaterialCategoryRepository sMaterialCategoryRepository;
	@Autowired
	private SMaterialCategoryPicRepository sMaterialCategoryPicRepository;
	@Autowired
	private SStatusDicRepository sStatusDicRepository;
	
	@Autowired 
	private  SecurityUtils securityUtils;
	
	
	@Transactional(readOnly=true)
	public List<WSMaterialCategory> getMaterialCategories(Long idCompany) {
		List<SMaterialCategory> dbCats = sMaterialCategoryRepository.findByIdCompany(idCompany);
		List<WSMaterialCategory> wsCats = new ArrayList<WSMaterialCategory>();
		for(SMaterialCategory dbcat:dbCats)
		{
			WSMaterialCategory wsCat = new WSMaterialCategory();
			wsCat.setId(dbcat.getId());
			wsCat.setName(dbcat.getName());
			wsCat.setDes(dbcat.getDes());
			wsCat.setOrderBy(dbcat.getOrderBy());
			if(dbcat.getSStatusDic()!=null)
			wsCat.setStatusName(dbcat.getSStatusDic().getName());
			wsCats.add(wsCat);
		}
		
		return wsCats;
		
	}

	
	@Transactional(readOnly=false)
	public WSMaterialCategory saveMaterialCategory(WSMaterialCategory mc) {
		
		SMaterialCategory dbMaterialCategory;
		if(mc.getId()!=null&&!mc.getId().equals(0l))
		{
			dbMaterialCategory = sMaterialCategoryRepository.findOne(mc.getId());
		}
		else
		{
			dbMaterialCategory = new SMaterialCategory();
		}
		dbMaterialCategory.setName(mc.getName());
		dbMaterialCategory.setDes(mc.getDes());
		dbMaterialCategory.setOrderBy(mc.getOrderBy());
		dbMaterialCategory.setSStatusDic(sStatusDicRepository.findOne(mc.getStatus()));
		dbMaterialCategory.setCompany(securityUtils.getCurrentDBUser().getCompany());
		sMaterialCategoryRepository.save(dbMaterialCategory);
		mc.setId(dbMaterialCategory.getId());
		return mc;		
		
	}
	
	@Transactional(readOnly=false)
	public Valid deleteMaterialCategory(Long catgegoryId)
	{
		Valid valid = new Valid();
		SMaterialCategory mc = sMaterialCategoryRepository.findOne(catgegoryId);
		if(!mc.getSMaterials().isEmpty())
		{
			valid.setValid(false);
		}
		else
		{
			sMaterialCategoryRepository.delete(catgegoryId);
			valid.setValid(true);
		}
		
		return valid;
	}
	
	
	@Transactional(readOnly=true)
	public WSMaterialCategory findMaterialCategory(Long materialCategoryId)
	{	
		SMaterialCategory mc = sMaterialCategoryRepository.findOne(materialCategoryId);
		WSMaterialCategory wsMc = new WSMaterialCategory();
			if(mc==null)
				return wsMc;
			wsMc.setDes(mc.getDes());
			wsMc.setId(mc.getId());
			wsMc.setName(mc.getName());
			wsMc.setOrderBy(mc.getOrderBy());
			wsMc.setStatus(mc.getSStatusDic().getId());
			wsMc.setStatusName(mc.getSStatusDic().getName());
	
		    return wsMc;
	}

	

}
