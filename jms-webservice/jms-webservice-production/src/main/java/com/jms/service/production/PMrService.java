package com.jms.service.production;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jms.domain.db.FCostCenter;
import com.jms.domain.db.PBom;
import com.jms.domain.db.PMr;
import com.jms.domain.db.PWo;
import com.jms.domain.db.PWoRoute;
import com.jms.domain.db.PWorkCenter;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SStk;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.production.WSPMr;
import com.jms.domain.ws.production.WSPRoutineD;
import com.jms.domain.ws.production.WSPWo;
import com.jms.domain.ws.production.WSPWorkCenter;
import com.jms.domain.ws.store.WSMaterialQty;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.f.FCostCenterRepository;
import com.jms.repositories.p.PBomRepository;
import com.jms.repositories.p.PCPpRepository;
import com.jms.repositories.p.PMrRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.repositories.s.SSoRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class PMrService {

	private static final Logger logger = LogManager.getLogger(PMrService.class
			.getCanonicalName());
	@Autowired
	private PWoRepository pWoRepository;
	@Autowired
	private SSoRepository sSoRepository;
	
	@Autowired
	private PBomRepository pBomRepository;
	@Autowired
	private PCPpRepository pCPpRepository;
	
	
	@Autowired 
	private PMrRepository pMrRepository;
	@Autowired 
	private PStatusDicRepository pStatusDicRepository;
	

	@Autowired
	private SecurityUtils securityUtils;
	

	
	@Transactional(readOnly=false)
	public WSPMr saveWSPMr(WSPMr wsPMr) throws Exception {
		PMr pMr;
		if(wsPMr.getIdMr()!=null)
		{
			pMr = pMrRepository.findOne(wsPMr.getIdMr());
		}
		else
		{
			pMr = new PMr();
			pMr.setSt(new Date());
		}
		PMr dbPMr= toDBPMr(wsPMr,pMr);
		dbPMr = pMrRepository.save(dbPMr);
		wsPMr.setIdMr(dbPMr.getIdMr());
		return wsPMr;		
		
	}

	
	
	@Transactional(readOnly=true)
	public List<WSPMr> getWSPMrsByType(Long type) throws Exception {
		List<WSPMr> wsPMrs = new ArrayList<WSPMr>();
		
		for(PMr p: pMrRepository.getByType(type))
		{
			WSPMr w = new WSPMr();
			w.setBomId(p.getPBom().getIdBom());
			w.setCppId(p.getPCPp().getIdCPp());
			SMaterial s = p.getPBom().getSMaterial();
			w.setDes(s.getDes());
			w.setIdMaterial(s.getIdMaterial());
			w.setIdMr(p.getIdMr());
			w.setMachine(p.getPCPp().getMMachine().getCode());
			w.setOp(p.getPCPp().getUsers().getName());
			w.setPno(s.getPno());
			w.setQty(p.getQty());
			w.setRemark(p.getRemark());
			w.setRev(s.getRev());
			w.setStatus(p.getPStatusDic().getName());
			w.setStatusId(p.getPStatusDic().getIdPstatus());
			w.setType(p.getType());
			
			wsPMrs.add(w);
		}
		
		return wsPMrs;
		
	}
	
	
	@Transactional(readOnly=true)
	public WSPMr getWSPMrById(Long idMr) throws Exception {
		    PMr p=pMrRepository.getOne(idMr);
	
			WSPMr w = new WSPMr();
			w.setBomId(p.getPBom().getIdBom());
			w.setCppId(p.getPCPp().getIdCPp());
			SMaterial s = p.getPBom().getSMaterial();
			w.setDes(s.getDes());
			w.setIdMaterial(s.getIdMaterial());
			w.setIdMr(p.getIdMr());
			w.setMachine(p.getPCPp().getMMachine().getCode());
			w.setOp(p.getPCPp().getUsers().getName());
			w.setPno(s.getPno());
			w.setQty(p.getQty());
			w.setRemark(p.getRemark());
			w.setRev(s.getRev());
			w.setStatus(p.getPStatusDic().getName());
			w.setStatusId(p.getPStatusDic().getIdPstatus());
			w.setType(p.getType());
			
	      return w;
	}
	
	
	
	
	
	@Transactional(readOnly=false)
	public Valid saveWSPMrs(List<WSPMr> wsPMrs) throws Exception {
		Valid v = new Valid();
		
		
		for(WSPMr w : wsPMrs)
		{
			saveWSPMr(w);
		}
		
		
		v.setMsg("OK");
		v.setValid(true);
		
		return v;
		
	}


	

	
	
	private PMr toDBPMr(WSPMr wsPMr,PMr pMr) throws Exception
	{
	
		PMr dbPMr = (PMr)BeanUtil.shallowCopy(wsPMr, PMr.class, pMr);

        if(wsPMr.getStatusId()!=null)
        	dbPMr.setPStatusDic(pStatusDicRepository.findOne(wsPMr.getStatusId()));
        dbPMr.setPCPp(pCPpRepository.findOne(wsPMr.getCppId()));
        dbPMr.setPBom(pBomRepository.findOne(wsPMr.getBomId()));
  
		return dbPMr;
	}
	
	private WSPMr toWSPMr(PMr pMr) throws Exception
	{
		WSPMr pc = (WSPMr)BeanUtil.shallowCopy(pMr, WSPMr.class, null);
	
		
		if(pMr.getPStatusDic()!=null)
		{
			pc.setStatus(pMr.getPStatusDic().getName());
			pc.setStatusId(pMr.getPStatusDic().getIdPstatus());
		}
		
		return pc;
	}
	
	


}
