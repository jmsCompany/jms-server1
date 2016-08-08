
package com.jms.service.production;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.PDraw;
import com.jms.domain.db.PRoutine;
import com.jms.domain.db.PRoutineD;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.p.WSPRoutine;
import com.jms.domain.ws.p.WSPRoutineD;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.p.PAttDrawRepository;
import com.jms.repositories.p.PDrawRepository;
import com.jms.repositories.p.PLineRepository;
import com.jms.repositories.p.PRoutineDRepository;
import com.jms.repositories.p.PRoutineRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class RoutineService {

	private static final Logger logger = LogManager.getLogger(RoutineService.class
			.getCanonicalName());
	@Autowired
	private RoutineDService routineDService;
	@Autowired
	private PRoutineDRepository pRoutineDRepository;
	@Autowired
	private PRoutineRepository pRoutineRepository;
	@Autowired
	private PLineRepository pLineRepository;
	
	@Autowired
	private PWorkCenterRepository pWorkCenterRepository;
	@Autowired
	private PAttDrawRepository pAttDrawRepository;
	
	@Autowired
	private PDrawRepository pDrawRepository;
	@Autowired
	private UsersRepository usersRepository;
	
	
	@Autowired
	private SMaterialRepository sMaterialRepository;
	@Autowired 
	private CompanyRepository companyRepository;
	@Autowired 
	private PStatusDicRepository pStatusDicRepository;
	

	@Autowired
	private SecurityUtils securityUtils;
	
	
	@Transactional(readOnly=false)
	public WSPRoutine saveWSPRoutine(WSPRoutine wsPRoutine) throws Exception {
		PRoutine pRoutine;
		if(wsPRoutine.getLineId()==null||wsPRoutine.getLineId().equals(0l))
			return wsPRoutine;
	
		if(wsPRoutine.getIdRoutine()!=null&&!wsPRoutine.getIdRoutine().equals(0l))
		{
			pRoutine = pRoutineRepository.findOne(wsPRoutine.getIdRoutine());
			pRoutineDRepository.delete(pRoutine.getPRoutineDs());
			pRoutine.getPRoutineDs().clear();
		}
		else
		{
			pRoutine = new PRoutine();
	
		}
		
		PRoutine dbPRoutine= toDBPRoutine(wsPRoutine,pRoutine);
		dbPRoutine = pRoutineRepository.save(dbPRoutine);
	
		
		PDraw draw;
		if(wsPRoutine.getDrawId()==null||wsPRoutine.getDrawId().equals(0l))
		{
			draw= new PDraw();
			draw.setDrawNo(wsPRoutine.getDrawNo());
			draw.setDrawVer(wsPRoutine.getDrawVer());
			draw.setDrawAtt(wsPRoutine.getDrawAtt());
			draw = pDrawRepository.save(draw);
		}
		else
		{
		    draw =pDrawRepository.findOne(wsPRoutine.getDrawId());
			draw.setDrawNo(wsPRoutine.getDrawNo());
			draw.setDrawVer(wsPRoutine.getDrawVer());
			draw.setDrawAtt(wsPRoutine.getDrawAtt());
			draw = pDrawRepository.save(draw);
		}
		dbPRoutine.setPDraw(draw);
		dbPRoutine = pRoutineRepository.save(dbPRoutine);
		
	
		for(String k:wsPRoutine.getWsRoutineDs().keySet())
		{
			WSPRoutineD wm =wsPRoutine.getWsRoutineDs().get(k);
			//logger.debug("" + wm.g);
			//logger.debug("std ma: " +wm.getStdWtMachine());
			wm.setRoutineId(dbPRoutine.getIdRoutine());
			routineDService.saveWSPRoutineD(wm);
		}

		wsPRoutine.setIdRoutine(dbPRoutine.getIdRoutine());
		return wsPRoutine;		
		
	}

	@Transactional(readOnly=false)
	public Valid deletePRoutine(Long idPRoutine)
	{
		Valid valid = new Valid();
		pRoutineRepository.delete(idPRoutine);
		valid.setValid(true);
		
		return valid;
	}
	
	
	@Transactional(readOnly=true) 
	public WSPRoutine findWSPRoutine(Long idPRoutine) throws Exception
	{	
		PRoutine pRoutine= pRoutineRepository.findOne(idPRoutine);
		return  toWSPRoutine(pRoutine);
		
	}
	
	protected PRoutine toDBPRoutine(WSPRoutine wsPRoutine,PRoutine pRoutine) throws Exception
	{
	
		PRoutine dbPRoutine = (PRoutine)BeanUtil.shallowCopy(wsPRoutine, PRoutine.class, pRoutine);

        if(wsPRoutine.getCompanyId()!=null)
        {
        	dbPRoutine.setCompany(companyRepository.findOne(wsPRoutine.getCompanyId()));
        }
        else
        {
        	dbPRoutine.setCompany(securityUtils.getCurrentDBUser().getCompany());
        }
        if(wsPRoutine.getDrawId()!=null)
        {
        	dbPRoutine.setPDraw(pDrawRepository.findOne(wsPRoutine.getDrawId()));
        }
        if(wsPRoutine.getLineId()!=null)
        {
        	dbPRoutine.setPLine(pLineRepository.findOne(wsPRoutine.getLineId()));
        }
        if(wsPRoutine.getMaterialId()!=null)
        {
        	dbPRoutine.setSMaterial(sMaterialRepository.findOne(wsPRoutine.getMaterialId()));
        }
        if(wsPRoutine.getStatusId()!=null)
        {
        	dbPRoutine.setPStatusDic(pStatusDicRepository.findOne(wsPRoutine.getStatusId()));
        }

		return dbPRoutine;
	}
	
	protected WSPRoutine toWSPRoutine(PRoutine pRoutine) throws Exception
	{
		WSPRoutine pc = (WSPRoutine)BeanUtil.shallowCopy(pRoutine, WSPRoutine.class, null);
	    if(pRoutine.getCompany()!=null)
	    {
	    	pc.setCompanyId(pRoutine.getCompany().getIdCompany());
	    	pc.setCompanyName(pRoutine.getCompany().getCompanyName());
	    
	    }
	    if(pRoutine.getPDraw()!=null)
	    {
	    	pc.setDrawId(pRoutine.getPDraw().getIdDraw());
	    	pc.setDrawNo(pRoutine.getPDraw().getDrawNo());
	    	pc.setDrawVer(pRoutine.getPDraw().getDrawVer());
	    	pc.setDrawAtt(pRoutine.getPDraw().getDrawAtt());

	    }
	    if(pRoutine.getPLine()!=null)
	    {
	    	pc.setLine(pRoutine.getPLine().getPline());
	    	pc.setLineId(pRoutine.getPLine().getIdPline());
	    }
	    if(pRoutine.getPStatusDic()!=null)
	    {
	    	pc.setStatus(pRoutine.getPStatusDic().getName());
	    	pc.setStatusId(pRoutine.getPStatusDic().getIdPstatus());
	    }
	    if(pRoutine.getSMaterial()!=null)
	    {
	    	pc.setMaterialDes(pRoutine.getSMaterial().getDes());
	    	pc.setMaterialId(pRoutine.getSMaterial().getIdMaterial());
	    	pc.setMaterialRev(pRoutine.getSMaterial().getRev());
	    	pc.setpNo(pRoutine.getSMaterial().getPno());
	    	
	    }
	    int i=0;
	    Boolean edit = true;
	    
	    for(PRoutineD p: pRoutineDRepository.findByRoutineId(pRoutine.getIdRoutine()))
	    {
	    	pc.getWsRoutineDs().put("item"+i, routineDService.toWSPRoutineD(p));
	    	if(edit&&p.getPCPps()!=null&&!p.getPCPps().isEmpty())
	    	{
	    		edit =false;
	    	}
	    	i++;
	    }
	  
	    pc.setEdit(edit);
		return pc;
	}

}
