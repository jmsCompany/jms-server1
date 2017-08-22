package com.jms.service.production;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.FCostCenter;
import com.jms.domain.db.PBom;
import com.jms.domain.db.PBomLabel;
import com.jms.domain.db.PRoutineD;
import com.jms.domain.db.SBin;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.p.WSPBom;
import com.jms.domain.ws.p.WSPBomItem;
import com.jms.domain.ws.p.WSPWo;
import com.jms.domain.ws.p.WSPWorkCenter;
import com.jms.domain.ws.s.WSSMtfMaterial;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.f.FCostCenterRepository;
import com.jms.repositories.p.PBomLabelRepository;
import com.jms.repositories.p.PBomRepository;
import com.jms.repositories.p.PRoutineDRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.repositories.s.SBinRepository;
import com.jms.repositories.s.SSoRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class BomLabelService {

	private static final Logger logger = LogManager.getLogger(BomLabelService.class
			.getCanonicalName());
	@Autowired private PBomRepository pBomRepository;
	@Autowired private PBomLabelRepository pBomLabelRepository;
	@Autowired private CompanyRepository companyRepository;
	@Autowired private PStatusDicRepository pStatusDicRepository;
	@Autowired private PRoutineDRepository pRoutineDRepository;
	@Autowired private SecurityUtils securityUtils;
	@Autowired private BomService bomService;
	@Autowired private SBinRepository sBinRepository;
	
	@Transactional(readOnly=false)
	public WSPBom savePBomLabel(WSPBom wsPBom) throws Exception {
		PBomLabel pBomLabel;
	
		boolean isNew=true;
		if(wsPBom.getIdBomLabel()!=null&&!wsPBom.getIdBomLabel().equals(0l))
		{
			isNew=false;
		}
		
		
		List<PBom> pBoms=	pBomRepository.findProductsByMaterialId(wsPBom.getMaterialId());
		if(isNew&&pBoms!=null&&!pBoms.isEmpty())
		{
			wsPBom.setIdBomLabel(0l);
			wsPBom.setSaved(false);
			
			return wsPBom;
		}
			
		if(!isNew)
		{
			pBomLabel = pBomLabelRepository.findOne(wsPBom.getIdBomLabel());
			pBomRepository.deleteByBomLabelIdAnPidIdNotNull(pBomLabel.getIdBomLabel());
			pBomRepository.deleteByBomLabelIdAnPidIdNull(pBomLabel.getIdBomLabel());
			pBomLabel.getPBoms().clear();
	
		}
		else
		{
			pBomLabel = new PBomLabel();
			pBomLabel.setCreationTime(new Date());
			pBomLabel.setUsers(securityUtils.getCurrentDBUser());
		
		}
		PBomLabel dbPBomLabel= toDBPBomLabel(wsPBom,pBomLabel);
		
		//logger.debug(" bomlabel: " + dbPBomLabel.getIdBomLabel());
		dbPBomLabel = pBomLabelRepository.save(dbPBomLabel);
	
	

		wsPBom.setIdBomLabel(dbPBomLabel.getIdBomLabel());
		WSPBomItem wsPBomItem = new WSPBomItem();
		wsPBomItem.setIdBomLabel(dbPBomLabel.getIdBomLabel());
		wsPBomItem.setLvl(1l);
		wsPBomItem.setMaterial(wsPBom.getMaterial());
		wsPBomItem.setMaterialId(wsPBom.getMaterialId());
		//wsPBomItem.setWorkCenterId(wsPBom.get);

	
	
		wsPBomItem =bomService.saveWSPBomItem(wsPBomItem);
	
		for(String k:wsPBom.getBomItems().keySet())
		{
			WSPBomItem wm =wsPBom.getBomItems().get(k);
			wm.setIdBomLabel(dbPBomLabel.getIdBomLabel());
			wm.setIdParentBom(wsPBomItem.getIdBom());
			wm.setLvl(wm.getLvl());
			bomService.saveWSPBomItem(wm);
		}
	
		
		wsPBom.setSaved(true);
		return wsPBom;		
		
	}
	
	
	
	@Transactional(readOnly=false)
	public WSPBom updateBomStatus(WSPBom wsPBom) throws Exception {
		PBomLabel pBomLabel = pBomLabelRepository.findOne(wsPBom.getIdBomLabel());
		pBomLabel.setPStatusDic(pStatusDicRepository.findOne(wsPBom.getStatusId()));
		pBomLabelRepository.save(pBomLabel);	
		return wsPBom;
	}

	@Transactional(readOnly=false)
	public Valid deletePWSPBom(Long bomLabelId)
	{
		Valid valid = new Valid();	
		
		PBomLabel pBomLabel = pBomLabelRepository.findOne(bomLabelId);
		
		
		for(PBom p: pBomLabel.getPBoms())
		{
			//p.get
			if(p.getIdRoutineD()!=null)
			{

				PRoutineD r = pRoutineDRepository.findOne(p.getIdRoutineD());
				
				if(r!=null)
				{
					valid.setValid(false);
					return valid;
					
				}
			
			}
			if(p.getPMrs()!=null&&!p.getPMrs().isEmpty())
			{
				valid.setValid(false);
				return valid;
				
			}
			
		}
		
		pBomLabelRepository.delete(bomLabelId);
		valid.setValid(true);
		return valid;
	}

	
	@Transactional(readOnly=true) 
	public WSPBom findWSPBom(Long bomLabelId, Long comCompanyId) throws Exception
	{	
		PBomLabel pBomLabel = pBomLabelRepository.findOne(bomLabelId);
		return  toWSPBom(pBomLabel,comCompanyId);
		
	}

	
	

	@Transactional(readOnly=true) 
	public List<WSPBom> findWSPBomList() throws Exception
	{	
		List<WSPBom> ws = new ArrayList<WSPBom>();
		List<PBomLabel> pBomLabels =pBomLabelRepository.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		for(PBomLabel pl :pBomLabels)
		{
			ws.add(toWSPBom(pl,null));
		}
		return  ws;
		
	}

	private PBomLabel toDBPBomLabel(WSPBom wsPBom,PBomLabel pBomLabel) throws Exception
	{
	
		PBomLabel dbPBomLabel = (PBomLabel)BeanUtil.shallowCopy(wsPBom, PBomLabel.class, pBomLabel);

		if(wsPBom.getIdBomLabel()!=null&&!wsPBom.getIdBomLabel().equals(0l))
		{
			dbPBomLabel.setIdBomLabel(wsPBom.getIdBomLabel());
		}
		//dbPBomLabel.se
        if(wsPBom.getCompanyId()!=null)
        	dbPBomLabel.setCompany(companyRepository.findOne(wsPBom.getCompanyId()));
        else
        {
        	dbPBomLabel.setCompany(securityUtils.getCurrentDBUser().getCompany());
        }
        dbPBomLabel.setUsers(securityUtils.getCurrentDBUser());
		if(wsPBom.getStatusId()!=null)
			dbPBomLabel.setPStatusDic(pStatusDicRepository.findOne(wsPBom.getStatusId()));

		return dbPBomLabel;
	}
	
	private WSPBom toWSPBom(PBomLabel pBomLabel,Long comCompanyId) throws Exception
	{
		WSPBom pc = (WSPBom)BeanUtil.shallowCopy(pBomLabel, WSPBom.class, null);
	
		if(pBomLabel.getUsers()!=null)
		{
			pc.setCreator(pBomLabel.getUsers().getName());
		}
		if(pBomLabel.getPStatusDic()!=null)
		{
			pc.setStatus(pBomLabel.getPStatusDic().getName());
			pc.setStatusId(pBomLabel.getPStatusDic().getIdPstatus());
		}
		if(pBomLabel.getCompany()!=null)
		{
			pc.setCompanyName(pBomLabel.getCompany().getCompanyName());
			pc.setCompanyId(pBomLabel.getCompany().getIdCompany());
		}
		int i=0;
		
		List<PBom> pboms = pBomRepository.findByBomLabelId(pBomLabel.getIdBomLabel());
		
		boolean edit =true;
		for(PBom p:pboms)
		{
			if(p.getPBom()!=null)
			{
				if(p.getIdRoutineD()!=null)
				{
					PRoutineD r = pRoutineDRepository.findOne(p.getIdRoutineD());
					if(edit&&r!=null&&!r.getPCPps().isEmpty())
					{
						edit = false;
						
					}
				}
				if(!p.getPMrs().isEmpty())
				{
					edit = false;
				}

		
				WSPBomItem item = new WSPBomItem();
				item.setIdBom(p.getIdBom());
				item.setIdBomLabel(pBomLabel.getIdBomLabel());
				item.setLvl(p.getLvl());
		
				item.setIdRoutineD(p.getIdRoutineD());
				item.setQpu(p.getQpu());
			    if(p.getWip()!=null)
			    {
			    	SBin bin = sBinRepository.findOne(p.getWip());
			    	if(bin!=null)
			    	{
			    		item.setWorkCenter(bin.getBin());
				    	item.setWorkCenterId(bin.getIdBin());
			    	}
			    	
			    }
//				if(p.getPWorkCenter()!=null)
//				{
//					item.setWorkCenter(p.getPWorkCenter().getWorkCenter());
//					item.setWorkCenterId(p.getPWorkCenter().getIdWc());
//				}
	
				item.setOrderBy(p.getOrderBy());
				item.setWastage(p.getWastage());
				item.setIdParentBom(p.getPBom().getIdBom());
				if(p.getSMaterial()!=null)
				{
					item.setMaterialId(p.getSMaterial().getIdMaterial());
					item.setMaterial(p.getSMaterial().getDes());
					item.setRev(p.getSMaterial().getRev());
					item.setPno(p.getSMaterial().getPno());
					item.setCost(p.getSMaterial().getCost());
					if(p.getSMaterial().getSUnitDicByUnitInf()!=null)
					{
						item.setsUnitDicByUnitInf(p.getSMaterial().getSUnitDicByUnitInf().getName());
					}
					if(p.getSMaterial().getSUnitDicByUnitInf()!=null)
					{
						item.setsUnitDicByUnitInfId(p.getSMaterial().getSUnitDicByUnitInf().getId());
					}
					item.setWeight(p.getSMaterial().getWeight());
					
					if(comCompanyId!=null)
					{
						//item.setQtyCoPo(0l);
					}
				}
				pc.getBomItems().put("item"+i, item);
				i++;
			}
			else
			{
				pc.setMaterialId(p.getSMaterial().getIdMaterial());
				pc.setMaterial(p.getSMaterial().getDes());
				pc.setRev(p.getSMaterial().getRev());
				pc.setPno(p.getSMaterial().getPno());
			}
		}
		pc.setEdit(edit);
		return pc;
	}

}
