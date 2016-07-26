package com.jms.service.production;


import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.jms.domain.db.PCPp;
import com.jms.domain.db.PCheckPlan;
import com.jms.domain.db.PRoutine;
import com.jms.domain.db.PRoutineD;
import com.jms.domain.db.PWo;
import com.jms.domain.db.PWoRoute;
import com.jms.domain.db.PWorkCenter;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMtfNo;
import com.jms.domain.db.SStk;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSBomMaterialObj;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.p.WSPRoutineD;
import com.jms.domain.ws.p.WSPWo;
import com.jms.domain.ws.p.WSPWorkCenter;
import com.jms.domain.ws.p.WSShiftPlanDStatus;
import com.jms.domain.ws.p.WSWoStatus;
import com.jms.domain.ws.s.WSMaterialQty;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.f.FCostCenterRepository;
import com.jms.repositories.p.PBomRepository;
import com.jms.repositories.p.PCPpRepository;
import com.jms.repositories.p.PCheckPlanRepository;
import com.jms.repositories.p.PRoutineDRepository;
import com.jms.repositories.p.PRoutineRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.repositories.s.SMtfNoRepository;
import com.jms.repositories.s.SSoRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class WoService {

	private static final Logger logger = LogManager.getLogger(WoService.class
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
	private CompanyRepository companyRepository;
	@Autowired 
	private PStatusDicRepository pStatusDicRepository;
	

	@Autowired
	private SecurityUtils securityUtils;
	@Autowired
	private PCheckPlanRepository pCheckPlanRepository;
	@Autowired private SMtfNoRepository sMtfNoRepository;
	@Autowired private PRoutineRepository pRoutineRepository;
	@Autowired private PRoutineDRepository pRoutineDRepository;
	
	

	
	@Transactional(readOnly=false)
	public WSPWo saveWSPWo(WSPWo wsPWo) throws Exception {
	    PWo pWo;
		if(wsPWo.getIdWo()!=null&&!wsPWo.getIdWo().equals(0l))
		{
			pWo = pWoRepository.findOne(wsPWo.getIdWo());
		}
		else
		{
			pWo = new PWo();
			pWo.setCreationTime(new Date());
		}
		PWo dbPWo= toDBPWo(wsPWo,pWo);
		dbPWo = pWoRepository.save(dbPWo);
		wsPWo.setIdWo(dbPWo.getIdWo());
		return wsPWo;		
		
	}

	@Transactional(readOnly=false)
	public Valid deletePWo(Long woId)
	{
		Valid valid = new Valid();
		
		pWoRepository.delete(woId);
		valid.setValid(true);
		
		return valid;
	}

	
	@Transactional(readOnly=true) 
	public WSPWo findWSPwo(Long woId) throws Exception
	{	
		PWo pWo = pWoRepository.findOne(woId);
		return  toWSPWo(pWo);
		
	}

	
	@Transactional(readOnly=true) 
	public List<WSWoStatus> findWSPwoStatus(Long companyId, Long delay) 
	{	
		
		Map<String,String> routineDMap = new LinkedHashMap<String,String>();
		routineDMap.put("r1", "010");
		routineDMap.put("r2", "020");
		routineDMap.put("r3", "030");

		routineDMap.put("r4", "050");
		routineDMap.put("r5", "060");
		routineDMap.put("r6", "080");
		routineDMap.put("r7", "090");
		routineDMap.put("r8", "100");
		routineDMap.put("r9", "105");
		routineDMap.put("r10", "110");
		routineDMap.put("r11", "120");
		routineDMap.put("r12", "130");
		routineDMap.put("r13", "140");
		routineDMap.put("r14", "150");
		routineDMap.put("r15", "160");
		routineDMap.put("r16", "170");
		
		List<WSWoStatus> pwos = new ArrayList<WSWoStatus>();
		//Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<PWo> pWos = pWoRepository.getActiveWosByCompanyId(companyId);
		long i=1;
		for(PWo p:pWos)
		{
			logger.debug("woNo: " + p.getWoNo());
			SMaterial s = p.getSSo().getSMaterial();
			PRoutine routine = pRoutineRepository.getByMaterialIdAndPline(s.getIdMaterial(), "PULLEY");
			if(routine==null)
			{
				logger.debug("no such routine!");
			}
			List<PCPp> cpps = pCPpRepository.getShouldStartedByWoId(p.getIdWo());

			
			if(routine!=null&&cpps!=null&&!cpps.isEmpty())
			{
				logger.debug("has cpps: " + cpps.size());
				List<PRoutineD> routineDList = pRoutineDRepository.findByRoutineId(routine.getIdRoutine());
				WSWoStatus w = new WSWoStatus();
				w.setId(i);
				w.setpNo(s.getPno());
				w.setWoNo(p.getWoNo());
		
				WSShiftPlanDStatus r1 = new WSShiftPlanDStatus();
				r1.setShiftNo("010");
				w.setR1(r1);
				
				WSShiftPlanDStatus r2 = new WSShiftPlanDStatus();
				r2.setShiftNo("020");
				w.setR2(r2);
				
				WSShiftPlanDStatus r3 = new WSShiftPlanDStatus();
				r3.setShiftNo("030");
				w.setR3(r3);
				
				WSShiftPlanDStatus r4 = new WSShiftPlanDStatus();
				r4.setShiftNo("050");
				w.setR4(r4);
				
				WSShiftPlanDStatus r5 = new WSShiftPlanDStatus();
				r5.setShiftNo("060");
				w.setR5(r5);
				
				WSShiftPlanDStatus r6 = new WSShiftPlanDStatus();
				r6.setShiftNo("080");
				w.setR6(r6);
				
				WSShiftPlanDStatus r7 = new WSShiftPlanDStatus();
				r7.setShiftNo("090");
				w.setR7(r7);
				
				WSShiftPlanDStatus r8 = new WSShiftPlanDStatus();
				r8.setShiftNo("100");
				w.setR8(r8);
				
				WSShiftPlanDStatus r9 = new WSShiftPlanDStatus();
				r9.setShiftNo("105");
				w.setR9(r9);
				
				WSShiftPlanDStatus r10 = new WSShiftPlanDStatus();
				r10.setShiftNo("110");
				w.setR10(r10);
				
				WSShiftPlanDStatus r11 = new WSShiftPlanDStatus();
				r11.setShiftNo("120");
				w.setR11(r11);
				
				
				WSShiftPlanDStatus r12 = new WSShiftPlanDStatus();
				r12.setShiftNo("130");
				w.setR12(r12);
				
				
				WSShiftPlanDStatus r13 = new WSShiftPlanDStatus();
				r13.setShiftNo("140");
				w.setR13(r13);
				
				WSShiftPlanDStatus r14= new WSShiftPlanDStatus();
				r14.setShiftNo("150");
				w.setR14(r14);
				
				WSShiftPlanDStatus r15 = new WSShiftPlanDStatus();
				r15.setShiftNo("160");
				w.setR15(r15);
				
				WSShiftPlanDStatus r16 = new WSShiftPlanDStatus();
				r16.setShiftNo("170");
				w.setR16(r16);
				
				for(PRoutineD rd:routineDList)
				{
					
					List<PCPp> dcpps = pCPpRepository.getFinishedCppByRoutineDId(rd.getIdRoutineD());
					if(dcpps!=null&&!dcpps.isEmpty())
					{
						logger.debug("cppsize for rd: " + rd.getRouteNo() +", cpps size: " + dcpps.size());
						long dev =0; //延迟，小时
						for(PCPp c:dcpps)
						{
							if(c.getPlanSt()!=null&&c.getPlanFt()!=null&&c.getActSt()!=null&&c.getActFt()!=null)
							{
								logger.debug("ps: " + c.getPlanSt().getTime());
								logger.debug("pf: " + c.getPlanFt().getTime());
								logger.debug("as: " + c.getActSt().getTime());
								logger.debug("af: " + c.getActFt().getTime());
								
								dev =dev + ((c.getActFt().getTime()-c.getActSt().getTime())-(c.getPlanFt().getTime()-c.getPlanSt().getTime()))/(1000*60*60);
							}
						}
						
						if(rd.getRouteNo().equals("010"))
						{
							
							if(dev>=delay)
							{
								r1.setDelay(""+dev);
								r1.setStatus("3.png");
							}else if(dev<delay&&dev>=0)
							{
								r1.setStatus("2.png");
							}
							else
							{
								r1.setStatus("1.png");
							}
							
						}
						
						
						if(rd.getRouteNo().equals("020"))
						{
							if(dev>=delay)
							{
								r2.setDelay(""+dev);
								r2.setStatus("3.png");
							}else if(dev<delay&&dev>=0)
							{
								r2.setStatus("2.png");
							}
							else
							{
								r2.setStatus("1.png");
							}
							
						}
						
						

						if(rd.getRouteNo().equals("030"))
						{
							if(dev>=delay)
							{
								r3.setDelay(""+dev);
								r3.setStatus("3.png");
							}else if(dev<delay&&dev>=0)
							{
								r3.setStatus("2.png");
							}
							else
							{
								r3.setStatus("1.png");
							}
							
						}
						

						if(rd.getRouteNo().equals("050"))
						{
							if(dev>=delay)
							{
								r4.setDelay(""+dev);
								r4.setStatus("3.png");
							}else if(dev<delay&&dev>=0)
							{
								r4.setStatus("2.png");
							}
							else
							{
								r4.setStatus("1.png");
							}
							
						}
						
						
						
						if(rd.getRouteNo().equals("060"))
						{
							if(dev>=delay)
							{
								r5.setDelay(""+dev);
								r5.setStatus("3.png");
							}else if(dev<delay&&dev>=0)
							{
								r5.setStatus("2.png");
							}
							else
							{
								r5.setStatus("1.png");
							}
							
						}
						
						
						if(rd.getRouteNo().equals("080"))
						{
							if(dev>=delay)
							{
								r6.setDelay(""+dev);
								r6.setStatus("3.png");
							}else if(dev<delay&&dev>=0)
							{
								r6.setStatus("2.png");
							}
							else
							{
								r6.setStatus("1.png");
							}
							
						}
						
						if(rd.getRouteNo().equals("090"))
						{
							if(dev>=delay)
							{
								r7.setDelay(""+dev);
								r7.setStatus("3.png");
							}else if(dev<delay&&dev>=0)
							{
								r7.setStatus("2.png");
							}
							else
							{
								r7.setStatus("1.png");
							}
							
						}
						
						
						if(rd.getRouteNo().equals("100"))
						{
							if(dev>=delay)
							{
								r8.setDelay(""+dev);
								r8.setStatus("3.png");
							}else if(dev<delay&&dev>=0)
							{
								r8.setStatus("2.png");
							}
							else
							{
								r8.setStatus("1.png");
							}
							
						}
						
						
						if(rd.getRouteNo().equals("105"))
						{
							if(dev>=delay)
							{
								r9.setDelay(""+dev);
								r9.setStatus("3.png");
							}else if(dev<delay&&dev>=0)
							{
								r9.setStatus("2.png");
							}
							else
							{
								r9.setStatus("1.png");
							}
							
						}
						
						
						if(rd.getRouteNo().equals("110"))
						{
							if(dev>=delay)
							{
								r10.setDelay(""+dev);
								r10.setStatus("3.png");
							}else if(dev<delay&&dev>=0)
							{
								r10.setStatus("2.png");
							}
							else
							{
								r10.setStatus("1.png");
							}
						
							
						}
						
						if(rd.getRouteNo().equals("120"))
						{
							if(dev>=delay)
							{
								r11.setDelay(""+dev);
								r11.setStatus("3.png");
							}else if(dev<delay&&dev>=0)
							{
								r11.setStatus("2.png");
							}
							else
							{
								r11.setStatus("1.png");
							}
							
						}
						
						
						if(rd.getRouteNo().equals("130"))
						{
							if(dev>=delay)
							{
								r12.setDelay(""+dev);
								r12.setStatus("3.png");
							}else if(dev<delay&&dev>=0)
							{
								r12.setStatus("2.png");
							}
							else
							{
								r12.setStatus("1.png");
							}
							
						}
						
						
						if(rd.getRouteNo().equals("140"))
						{
							if(dev>=delay)
							{
								r13.setDelay(""+dev);
								r13.setStatus("3.png");
							}else if(dev<delay&&dev>=0)
							{
								r13.setStatus("2.png");
							}
							else
							{
								r13.setStatus("1.png");
							}
							
						}
						
						
						if(rd.getRouteNo().equals("150"))
						{
							if(dev>=delay)
							{
								r14.setDelay(""+dev);
								r14.setStatus("3.png");
							}else if(dev<delay&&dev>=0)
							{
								r14.setStatus("2.png");
							}
							else
							{
								r14.setStatus("1.png");
							}
							
						}
						
						
						if(rd.getRouteNo().equals("160"))
						{
							if(dev>=delay)
							{
								r15.setDelay(""+dev);
								r15.setStatus("3.png");
							}else if(dev<delay&&dev>=0)
							{
								r15.setStatus("2.png");
							}
							else
							{
								r15.setStatus("1.png");
							}
							
						}
						
						
						if(rd.getRouteNo().equals("170"))
						{
							if(dev>=delay)
							{
								r16.setDelay(""+dev);
								r16.setStatus("3.png");
							}else if(dev<delay&&dev>=0)
							{
								r16.setStatus("2.png");
							}
							else
							{
								r16.setStatus("1.png");
							}
							
						}
						
						
						
					}
				}

				pwos.add(w);
				i++;
			}
	
		}
		return pwos;

	}
	
	
	private WSShiftPlanDStatus setS(String no,WSShiftPlanDStatus s)
	{
	
		return s;
	}
	
	
	@Transactional(readOnly=true) 
	public WSMaterialQty findWSMaterialQtyByWoId(Long woId) 
	{	
		WSMaterialQty mq = new WSMaterialQty();
		PWo pWo = pWoRepository.findOne(woId);
		SMaterial material =pWo.getSSo().getSMaterial();
		mq.setIdMaterial(material.getIdMaterial());
		mq.setPno(material.getPno());
		mq.setRev(material.getRev());
		mq.setDes(material.getDes());
		mq.setQty(pWo.getQty());
		return mq;
		
	}
	

	
	private PWo toDBPWo(WSPWo wsPWo,PWo pWo) throws Exception
	{
	
		PWo dbPWo = (PWo)BeanUtil.shallowCopy(wsPWo, PWo.class, pWo);
		
//		SMtfNo smtfNo = sMtfNoRepository.getByCompanyIdAndType(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), 9l);
//	    long currentVal =smtfNo.getCurrentVal()+1;
//	    smtfNo.setCurrentVal(currentVal);
//	    sMtfNoRepository.save(smtfNo);
//		
//	    String codeWo = smtfNo.getPrefix()+String.format("%08d", currentVal);
//	    dbPWo.setWoNo(codeWo);
		
	//	dbPWo.setWoNo(wsPWo.getWoNo());

        if(wsPWo.getSoId()!=null)
        	dbPWo.setSSo(sSoRepository.findOne(wsPWo.getSoId()));
		dbPWo.setUsers(securityUtils.getCurrentDBUser());
		if(wsPWo.getStatusId()!=null)
		dbPWo.setPStatusDic(pStatusDicRepository.findOne(wsPWo.getStatusId()));
		return dbPWo;
	}
	
	private WSPWo toWSPWo(PWo pWo) throws Exception
	{
		WSPWo pc = (WSPWo)BeanUtil.shallowCopy(pWo, WSPWo.class, null);
	
		if(pWo.getUsers()!=null)
		{
			pc.setCreator(pWo.getUsers().getName());
		}
		if(pWo.getPStatusDic()!=null)
		{
			pc.setStatus(pWo.getPStatusDic().getName());
			pc.setStatusId(pWo.getPStatusDic().getIdPstatus());
		}
		if(pWo.getSSo()!=null)
		{
			pc.setSo(pWo.getSSo().getCodeSo());
			pc.setSoId(pWo.getSSo().getIdSo());
			SMaterial s = pWo.getSSo().getSMaterial();
			if(s!=null)
			{
				pc.setMaterialId(s.getIdMaterial());
				pc.setPno(s.getPno());
				pc.setRev(s.getRev());
				pc.setDes(s.getDes());

			}
		}
		for(PWoRoute routine:pWo.getPWoRoutes())
		{
			WSPRoutineD wd = new WSPRoutineD();
			wd.setIdRoutineD(routine.getPRoutineD().getIdRoutineD());
			wd.setRouteNo(routine.getPRoutineD().getRouteNo());
			pc.getRoutines().add(wd);
		}
	   
		//pc.setQty(pWo.getQty());
		Long qtyFinished =0l;
		for(PCPp p: pWo.getPCPps())
		{
			List<PCheckPlan> pCheckPlans =pCheckPlanRepository.getMaxCheckPlanByCppId(p.getIdCPp());
			if(pCheckPlans!=null&&!pCheckPlans.isEmpty())
			qtyFinished =qtyFinished +pCheckPlans.get(0).getFinQty();
		}
		
		pc.setQtyFinished(qtyFinished);
		return pc;
	}
	
	
	public List<WSBomMaterialObj> getMaterialsByWoId(@RequestParam("woId") Long woId) throws Exception {
		
		List<WSBomMaterialObj> ws = new ArrayList<WSBomMaterialObj>();
		SMaterial s = pWoRepository.findByWoId(woId); //
	//	logger.debug("woId: " + woId + " , materialId: " + s.getIdMaterial());
		PBom pBom = pBomRepository.findProductByMaterialId(s.getIdMaterial());
		
		
		if(pBom!=null)
		{
			for(PBom p: pBom.getPBoms())
			{
				SMaterial material =p.getSMaterial();
				WSBomMaterialObj w = new WSBomMaterialObj(p.getIdBom()+"_"+material.getIdMaterial(),material.getPno()+"-"+ material.getRev()+"-"+material.getDes());
			    ws.add(w);
			   
			}
			
		}


		return ws;
	}
	

}
