package com.jms.controller.production;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.Company;
import com.jms.domain.db.PBom;
import com.jms.domain.db.PBomLabel;
import com.jms.domain.db.SMaterial;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.p.WSPBom;
import com.jms.domain.ws.p.WSPBomItem;
import com.jms.domain.ws.s.WSBomComs;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.p.PBomLabelRepository;
import com.jms.repositories.p.PBomRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.service.production.BomLabelService;
import com.jms.service.production.BomService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class BomController {
	
	@Autowired private BomService bomService;
	@Autowired private BomLabelService bomLabelService;
	@Autowired private PWoRepository pWoRepository;
	@Autowired private PBomLabelRepository pBomLabelRepository;
	@Autowired private SecurityUtils securityUtils;
	@Autowired private PBomRepository pBomRepository;
	@Autowired private SMaterialRepository sMaterialRepository;
	@Autowired private CompanyRepository companyRepository;
	@Autowired private PStatusDicRepository pStatusDicRepository;
	private static final Logger logger = LogManager.getLogger(BomController.class.getCanonicalName());

	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/saveBom", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSPBom saveWSPBom(@RequestBody WSPBom wsPBom) throws Exception {
		return bomLabelService.savePBomLabel(wsPBom);
	}
	
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/shareBom", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid shareWSPBom(@RequestBody WSBomComs wsBomComs) {
		//return bomLabelService.savePBomLabel(wsPBom);
		Valid v = new Valid();
		
	    Long idBom = wsBomComs.getIdBom();
	    PBomLabel pBomLabel = pBomLabelRepository.findOne(idBom);
	    List<PBom> boms=   pBomRepository.findByBomLabelId(idBom);
	    if(!boms.isEmpty())
	    {
		    PBom parent = boms.get(0);
		    SMaterial product = parent.getSMaterial();
		    logger.debug("idBom: " + idBom);
		    for(WSSelectObj o:wsBomComs.getComs())
		    {
		    	Long idComCompany = o.getId();
		    	logger.debug("company Id: " + idComCompany);
		    	Company sharedCompany =companyRepository.findOne(idComCompany);
		    	SMaterial sm = sMaterialRepository.getByCompanyIdAndPno(idComCompany, product.getPno());
		    	if(sm==null) //该公司无该物料
		    	{
		    		sm = new SMaterial();
					sm.setCompany(sharedCompany);
					sm.setCycleUnit(product.getCycleUnit());
					sm.setDes(product.getDes());
					sm.setPno(product.getPno());
					sm.setBrand(product.getBrand());
					sm.setRemark(product.getRemark());
					sm.setRev(product.getRev());
					sm.setWeight(product.getWeight());
					sm.setSUnitDicByUnitInf(product.getSUnitDicByUnitInf());
					sm.setSUnitDicByUnitPur(product.getSUnitDicByUnitPur());
					sm.setSStatusDic(product.getSStatusDic());
					sm.setSMaterialCategory(product.getSMaterialCategory());
					sm = sMaterialRepository.save(sm);
		    	}
		    		PBom p = pBomRepository.findProductByMaterialId(sm.getIdMaterial());
		    		if(p!=null) //已有BOM
		    		{
		    			logger.debug("company Id: " + idComCompany +"已有Bom，要删除已有bom");
		    			PBomLabel myBomLabel = pBomLabelRepository.findOne(p.getPBomLabel().getIdBomLabel());
		    			pBomRepository.deleteByBomLabelIdAnPidIdNotNull(myBomLabel.getIdBomLabel());
		    			pBomRepository.deleteByBomLabelIdAnPidIdNull(myBomLabel.getIdBomLabel());
		    			myBomLabel.getPBoms().clear();
		    		}
		    		PBomLabel newBomLabel = new PBomLabel();
		    		newBomLabel.setCreationTime(new Date());
		    		newBomLabel.setCompany(sharedCompany);
		    		newBomLabel.setPStatusDic(pStatusDicRepository.findOne(3l));
		    		newBomLabel = pBomLabelRepository.save(newBomLabel);
		    		PBom parentPbom = new PBom();
		    		parentPbom.setLvl(parent.getLvl());
		    		parentPbom.setOrderBy(parent.getOrderBy());
		    		parentPbom.setQpu(parent.getQpu());
		    		parentPbom.setWastage(parent.getWastage());
		    		parentPbom.setPBomLabel(newBomLabel);
		    		parentPbom.setSMaterial(sm);
		    		parentPbom = pBomRepository.save(parentPbom);
		    		for(PBom pbom :boms )
		    		{
		    		
		    			if(pbom.getPBom()!=null)
		    			{
		    				PBom  newPbom = new PBom();
		    				newPbom.setPBom(parentPbom);
		    				newPbom.setOrderBy(pbom.getOrderBy());
		    				newPbom.setQpu(pbom.getQpu());
		    				newPbom.setWastage(pbom.getWastage());
		    				newPbom.setPBomLabel(newBomLabel);
		    				SMaterial  s = pbom.getSMaterial();
		    				SMaterial sm1 = sMaterialRepository.getByCompanyIdAndPno(idComCompany, s.getPno());
		    				if(sm1!=null)
		    				{
		    					newPbom.setSMaterial(sm1);
		    				}
		    				else
		    				{
		    					SMaterial sn = new SMaterial();
		    				
		    					sn.setCompany(sharedCompany);
		    					sn.setCycleUnit(s.getCycleUnit());
		    					sn.setDes(s.getDes());
		    					sn.setPno(s.getPno());
		    					sn.setBrand(s.getBrand());
		    					sn.setRemark(s.getRemark());
		    					sn.setRev(s.getRev());
		    					sn.setWeight(s.getWeight());
		    					sn.setSUnitDicByUnitInf(s.getSUnitDicByUnitInf());
		    					sn.setSUnitDicByUnitPur(s.getSUnitDicByUnitPur());
		    					sn.setSStatusDic(s.getSStatusDic());
		    					sn = sMaterialRepository.save(sn);
		    					sn.setSMaterialCategory(s.getSMaterialCategory());
		    					newPbom.setSMaterial(sn);
		    					
		    				}
		    				
		    				newPbom = pBomRepository.save(newPbom);
		    			}
		    	
		    		}
		    }
	   }
	   v.setValid(true);
	   v.setMsg("Bom are shared");
    	return v;

}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/updateBomStatus", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSPBom updateBomStatus(@RequestBody WSPBom wsPBom) throws Exception {
		return bomLabelService.updateBomStatus(wsPBom);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/updateBomItem", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSPBomItem updateBomItem(@RequestBody WSPBomItem wsPBomItem) throws Exception {
		return bomService.updateWSPBomItem(wsPBomItem);
	}
	
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/deleteBom", method=RequestMethod.GET)
	public Valid deleteBom(@RequestParam("bomLabelId") Long bomLabelId) {
		return bomLabelService.deletePWSPBom(bomLabelId);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findBom", method=RequestMethod.GET)
	public WSPBom findWo(@RequestParam("bomLabelId") Long bomLabelId) throws Exception {
		return bomLabelService.findWSPBom(bomLabelId);
		
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findBomItem", method=RequestMethod.GET)
	public WSPBomItem findBomItem(@RequestParam("bomId") Long bomId) throws Exception {
		return bomService.findWSPBomItem(bomId);
		
	}

	@Transactional(readOnly = false)
	@RequestMapping(value="/p/saveBomItem", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSPBomItem saveWSPBomItem(@RequestBody WSPBomItem wsPBomItem) throws Exception {
		return bomService.saveWSPBomItem(wsPBomItem);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getBomList", method=RequestMethod.POST)
	public WSTableData  getBomList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		List<WSPBom> wsPBoms =bomLabelService.findWSPBomList();
		
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(wsPBoms.size()<start + length)
			end =wsPBoms.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			WSPBom w = wsPBoms.get(i);
			String[] d = {w.getPno(),w.getRev(),w.getMaterial(),w.getCreationTime().toString(),""+w.getCreator(),""+w.getStatus(),""+w.getIdBomLabel()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(wsPBoms.size());
		t.setRecordsFiltered(wsPBoms.size());
	    t.setData(lst);
	    return t;
	}
	

}