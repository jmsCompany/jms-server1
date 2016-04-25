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
import com.jms.domain.db.PBomLabel;
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
import com.jms.domain.ws.store.WSSMtfMaterial;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.f.FCostCenterRepository;
import com.jms.repositories.p.PBomLabelRepository;
import com.jms.repositories.p.PBomRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.repositories.s.SSoRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class BomLabelService {

	private static final Logger logger = LogManager.getLogger(BomLabelService.class
			.getCanonicalName());
	@Autowired
	private PBomRepository pBomRepository;
	@Autowired
	private PBomLabelRepository pBomLabelRepository;
	
	
	@Autowired 
	private CompanyRepository companyRepository;
	@Autowired 
	private PStatusDicRepository pStatusDicRepository;
	

	@Autowired
	private SecurityUtils securityUtils;
	
	@Autowired
	private BomService bomService;
	
	@Transactional(readOnly=false)
	public WSPBom savePBomLabel(WSPBom wsPBom) throws Exception {
		PBomLabel pBomLabel;
		List<PBom> pBoms=	pBomRepository.findProductsByMaterialId(wsPBom.getMaterialId());
		if(pBoms!=null&&!pBoms.isEmpty())
		{
			wsPBom.setIdBomLabel(0l);
			return wsPBom;
		}
			
		if(wsPBom.getIdBomLabel()!=null&&!wsPBom.getIdBomLabel().equals(0l))
		{
			pBomLabel = pBomLabelRepository.findOne(wsPBom.getIdBomLabel());
		}
		else
		{
			pBomLabel = new PBomLabel();
			pBomLabel.setCreationTime(new Date());
			pBomLabel.setUsers(securityUtils.getCurrentDBUser());
		
		}
		PBomLabel dbPBomLabel= toDBPBomLabel(wsPBom,pBomLabel);
		dbPBomLabel = pBomLabelRepository.save(dbPBomLabel);
	
	
		if(wsPBom.getIdBomLabel()!=null&&!wsPBom.getIdBomLabel().equals(0l))
		{
			//System.out.println("bom label id: " + wsPBom.getIdBomLabel());
			pBomRepository.deleteByBomLabelId(wsPBom.getIdBomLabel());
		}
		wsPBom.setIdBomLabel(dbPBomLabel.getIdBomLabel());
		WSPBomItem wsPBomItem = new WSPBomItem();
		wsPBomItem.setIdBomLabel(dbPBomLabel.getIdBomLabel());
		wsPBomItem.setLvl(1l);
		wsPBomItem.setMaterial(wsPBom.getMaterial());
		wsPBomItem.setMaterialId(wsPBom.getMaterialId());
	//	wsPBomItem.setWorkCenterId(workCenterId);

	
	
		wsPBomItem =bomService.saveWSPBomItem(wsPBomItem);
	
		for(String k:wsPBom.getBomItems().keySet())
		{
			WSPBomItem wm =wsPBom.getBomItems().get(k);
			wm.setIdBomLabel(dbPBomLabel.getIdBomLabel());
			wm.setIdParentBom(wsPBomItem.getIdBom());
			wm.setLvl(2l);
			bomService.saveWSPBomItem(wm);
		}
	
		
		
		return wsPBom;		
		
	}

	@Transactional(readOnly=false)
	public Valid deletePWSPBom(Long bomLabelId)
	{
		Valid valid = new Valid();
		
		pBomLabelRepository.delete(bomLabelId);
		valid.setValid(true);
		
		return valid;
	}

	
	@Transactional(readOnly=true) 
	public WSPBom findWSPBom(Long bomLabelId) throws Exception
	{	
		PBomLabel pBomLabel = pBomLabelRepository.findOne(bomLabelId);
		return  toWSPBom(pBomLabel);
		
	}

	@Transactional(readOnly=true) 
	public List<WSPBom> findWSPBomList() throws Exception
	{	
		 List<WSPBom> ws = new ArrayList<WSPBom>();
		List<PBomLabel> pBomLabels =pBomLabelRepository.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		for(PBomLabel pl :pBomLabels)
		{
			ws.add(toWSPBom(pl));
		}
		return  ws;
		
	}

	private PBomLabel toDBPBomLabel(WSPBom wsPBom,PBomLabel pBomLabel) throws Exception
	{
	
		PBomLabel dbPBomLabel = (PBomLabel)BeanUtil.shallowCopy(wsPBom, PBomLabel.class, pBomLabel);

        if(wsPBom.getCompanyId()!=null)
        	pBomLabel.setCompany(companyRepository.findOne(wsPBom.getCompanyId()));
        else
        {
        	pBomLabel.setCompany(securityUtils.getCurrentDBUser().getCompany());
        }
        pBomLabel.setUsers(securityUtils.getCurrentDBUser());
		if(wsPBom.getStatusId()!=null)
			pBomLabel.setPStatusDic(pStatusDicRepository.findOne(wsPBom.getStatusId()));

		return dbPBomLabel;
	}
	
	private WSPBom toWSPBom(PBomLabel pBomLabel) throws Exception
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
		for(PBom p:pBomLabel.getPBoms())
		{
			if(p.getPBom()!=null)
			{

				WSPBomItem item = new WSPBomItem();
				item.setIdBom(p.getIdBom());
				item.setIdBomLabel(pBomLabel.getIdBomLabel());
				item.setLvl(p.getLvl());
		
				item.setQpu(p.getQpu());
				if(p.getPWorkCenter()!=null)
				{
					item.setWorkCenter(p.getPWorkCenter().getWorkCenter());
					item.setWorkCenterId(p.getPWorkCenter().getIdWc());
				}
	
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
		return pc;
	}

}
