package com.jms.service.production;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.FCostCenter;
import com.jms.domain.db.PBom;
import com.jms.domain.db.PWo;
import com.jms.domain.db.PWorkCenter;
import com.jms.domain.db.SStk;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.production.WSPBom;
import com.jms.domain.ws.production.WSPBomItem;
import com.jms.domain.ws.production.WSPWo;
import com.jms.domain.ws.production.WSPWorkCenter;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.f.FCostCenterRepository;
import com.jms.repositories.p.PBomLabelRepository;
import com.jms.repositories.p.PBomRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SSoRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class BomService {

	private static final Logger logger = LogManager.getLogger(BomService.class
			.getCanonicalName());
	@Autowired
	private PBomRepository pBomRepository;
	@Autowired
	private PBomLabelRepository pBomLabelRepository;
	
	@Autowired
	private PWorkCenterRepository pWorkCenterRepository;
	
	
	@Autowired
	private SMaterialRepository sMaterialRepository;
	@Autowired 
	private CompanyRepository companyRepository;
	@Autowired 
	private PStatusDicRepository pStatusDicRepository;
	

	@Autowired
	private SecurityUtils securityUtils;
	

	
	@Transactional(readOnly=false)
	public WSPBomItem saveWSPBomItem(WSPBomItem wsPBomItem) throws Exception {
		PBom pBom;
		if(wsPBomItem.getIdBom()!=null&&!wsPBomItem.getIdBom().equals(0l))
		{
			pBom = pBomRepository.findOne(wsPBomItem.getIdBom());
		}
		else
		{
			pBom = new PBom();
	
		}
		PBom dbPBom= toDBPBom(wsPBomItem,pBom);
		dbPBom = pBomRepository.save(dbPBom);
		wsPBomItem.setIdBom(dbPBom.getIdBom());
		return wsPBomItem;		
		
	}

	@Transactional(readOnly=false)
	public Valid deletePBom(Long bomId)
	{
		Valid valid = new Valid();
		
		pBomRepository.delete(bomId);
		valid.setValid(true);
		
		return valid;
	}

	
	@Transactional(readOnly=true) 
	public WSPBomItem findWSPBomItem(Long bomId) throws Exception
	{	
		PBom pBom= pBomRepository.findOne(bomId);
		return  toWSPBomItem(pBom);
		
	}

	
	protected PBom toDBPBom(WSPBomItem wsPBomItem,PBom pBom) throws Exception
	{
	
		// if(wsPBomItem.getIdBom()!=null)
		//	 pBom.setPBom(pBomRepository.findOne(wsPBomItem.getIdBom()));
		PBom dbPBom = (PBom)BeanUtil.shallowCopy(wsPBomItem, PBom.class, pBom);

        if(wsPBomItem.getWorkCenterId()!=null)
		dbPBom.setPWorkCenter(pWorkCenterRepository.findOne(wsPBomItem.getWorkCenterId()));
		
		if(wsPBomItem.getMaterialId()!=null)
			dbPBom.setSMaterial(sMaterialRepository.findOne(wsPBomItem.getMaterialId()));
		if(wsPBomItem.getIdBomLabel()!=null)
		{
			dbPBom.setPBomLabel(pBomLabelRepository.findOne(wsPBomItem.getIdBomLabel()));
		}
		if(wsPBomItem.getIdParentBom()!=null)
		{
			dbPBom.setPBom(pBomRepository.findOne(wsPBomItem.getIdParentBom()));
		}
		return dbPBom;
	}
	
	private WSPBomItem toWSPBomItem(PBom pBom) throws Exception
	{
		WSPBomItem pc = (WSPBomItem)BeanUtil.shallowCopy(pBom, WSPBomItem.class, null);
	    if(pBom.getPBomLabel()!=null)
	    {
	    	pc.setIdBomLabel(pBom.getPBomLabel().getIdBomLabel());
	    }
	    if(pBom.getPWorkCenter()!=null)
	    {
	    	pc.setWorkCenter(pBom.getPWorkCenter().getWorkCenter());
	    	pc.setWorkCenterId(pBom.getPWorkCenter().getIdWc());
	    }
	    if(pBom.getSMaterial()!=null)
	    {
	    	pc.setMaterial(pBom.getSMaterial().getDes());
	    	pc.setMaterialId(pBom.getSMaterial().getIdMaterial());
	    	pc.setRev(pBom.getSMaterial().getRev());
	    	pc.setPno(pBom.getSMaterial().getPno());
	    }
        if(pBom.getPBom()!=null)
        {
        	pc.setIdParentBom(pBom.getPBom().getIdBom());
        }
		return pc;
	}

}
