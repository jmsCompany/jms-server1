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
import com.jms.domain.db.PActualSetup;
import com.jms.domain.db.PBom;
import com.jms.domain.db.PCPp;
import com.jms.domain.db.PDraw;
import com.jms.domain.db.PMr;
import com.jms.domain.db.PStopsPlan;
import com.jms.domain.db.PUnplannedStops;
import com.jms.domain.db.PWo;
import com.jms.domain.db.SInventory;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMtfNo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.p.WSOEE;
import com.jms.domain.ws.p.WSPCpp;
import com.jms.domain.ws.p.WSPMr;
import com.jms.domain.ws.p.WSPlannedMaterialSending;
import com.jms.domain.ws.p.WSPlannedMaterialSendingItem;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.m.MMachineRepository;
import com.jms.repositories.p.PActualSetupRepository;
import com.jms.repositories.p.PBomRepository;
import com.jms.repositories.p.PCPpRepository;
import com.jms.repositories.p.PMrRepository;
import com.jms.repositories.p.PPUTimeRepository;
import com.jms.repositories.p.PRoutineDRepository;
import com.jms.repositories.p.PShiftPlanDRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PStopsPlanRepository;
import com.jms.repositories.p.PUnplannedStopsRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.s.SInventoryRepository;
import com.jms.repositories.s.SMtfNoRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class PCppService {

	private static final Logger logger = LogManager.getLogger(PCppService.class
			.getCanonicalName());
	@Autowired private PCPpRepository pCPpRepository;
	@Autowired private CompanyRepository companyRepository;
	@Autowired private MMachineRepository  mMachineRepository;
	@Autowired private PWoRepository pWoRepository;
	@Autowired private SecurityUtils securityUtils;
	@Autowired private PRoutineDRepository pRoutineDRepository;
	@Autowired private PShiftPlanDRepository pShiftPlanDRepository;
	@Autowired private UsersRepository usersRepository;
	@Autowired private PBomRepository pBomRepository;
	@Autowired private PMrRepository pMrRepository;
	@Autowired private SMtfNoRepository sMtfNoRepository;
	@Autowired private SInventoryRepository sInventoryRepository;
	@Autowired private PActualSetupRepository pActualSetupRepository;
	@Autowired private PStopsPlanRepository pStopsPlanRepository;
	@Autowired private PUnplannedStopsRepository pUnplannedStopsRepository;

		
	@Transactional(readOnly=false)
	public WSPCpp saveWSPCPp(WSPCpp wsPCpp) throws Exception {
		PCPp pCPp;
		if(wsPCpp.getIdCpp()!=null&&!wsPCpp.getIdCpp().equals(0l))
		{
			pCPp = pCPpRepository.findOne(wsPCpp.getIdCpp());
		}
		else
		{
			pCPp = new PCPp();
		}
		PCPp dbPCPp= toDBPCPp(wsPCpp,pCPp);
		dbPCPp = pCPpRepository.save(dbPCPp);
		wsPCpp.setIdCpp(dbPCPp.getIdCPp());
		
		//生成需料报告
		
//         SMaterial material =  pCPp.getPWo().getSSo().getSMaterial();
//		 
//		 PBom pBom = pBomRepository.findProductByMaterialId(material.getIdMaterial());
//			
//			
//			if(pBom!=null)
//			{
//				for(PBom p: pBom.getPBoms())
//				{
//					
//					PMr pmr = new PMr();
//					pmr.setPBom(p);
//					pmr.setPCPp(dbPCPp);
//					pmr.setPStatusDic(pStatusDicRepository.findOne(9l));
//					pmr.setQty(p.getQpu()*dbPCPp.getQty());
//					pmr.setSt(dbPCPp.getPlanSt());
//					pmr.setType(2l); //自动生成需料
//					pMrRepository.save(pmr);
//				   
//				}
//				
//			}
//		
		
		return wsPCpp;		
		
	}

	@Transactional(readOnly=false)
	public Valid deletePCpp(Long idPcpp)
	{
		Valid valid = new Valid();
		pCPpRepository.delete(idPcpp);
		valid.setValid(true);
		return valid;
	}
	
	
	
	@Transactional(readOnly=false)
	public Valid startCpp(Long idCpp) throws Exception {
		Valid v = new Valid();
		
		PCPp dbPCPp = pCPpRepository.findOne(idCpp);
		dbPCPp.setActSt(new Date());
		pCPpRepository.save(dbPCPp);
		
		PWo pwo = dbPCPp.getPWo();
		if(pwo.getActSt()==null)
		{
			pwo.setActFt(new Date());
			pWoRepository.save(pwo);
		}
		
		v.setValid(true);
		return v;
	}
	
	
	@Transactional(readOnly=true)
	public Valid hasCheckPlans(Long idCpp) {
		Valid v = new Valid();	
		PCPp dbPCPp = pCPpRepository.findOne(idCpp);
		if(dbPCPp.getPCheckPlans().isEmpty())
		{
			v.setValid(false);
		}
		else
		{
			v.setValid(true);
		}
	
		return v;
	}
	
	
	
	@Transactional(readOnly=false)
	public Valid finishCpp(Long idCpp) throws Exception {
		Valid v = new Valid();
		
		PCPp dbPCPp = pCPpRepository.findOne(idCpp);
		dbPCPp.setActFt(new Date());
		pCPpRepository.save(dbPCPp);
		//判断是否有检查数据，如果无 返回
		if(dbPCPp.getPCheckPlans().isEmpty()) //无检查数据
		{
			v.setValid(false);
			
		}
		else
		{
			v.setValid(true);
		}
			
		return v;
	}
	
	
	
	@Transactional(readOnly=true)
	public Valid isStarted(Long idCpp) throws Exception {
		Valid v = new Valid();
		
		PCPp dbPCPp = pCPpRepository.findOne(idCpp);
		if(dbPCPp.getActSt()==null)
		{
			v.setValid(false);
		}
		else
		{
			v.setValid(true);
		}
		
		
		return v;
	}
	
	
	
	@Transactional(readOnly=true) 
	public WSPCpp findWSCPp(Long idPcpp) throws Exception
	{	
		PCPp dbPCPp = pCPpRepository.findOne(idPcpp);
		return  toWSPCpp(dbPCPp);
		
	}
	
	protected PCPp toDBPCPp(WSPCpp wsPCpp,PCPp pCPp) throws Exception
	{
	
		PCPp dbPCPp = (PCPp)BeanUtil.shallowCopy(wsPCpp, PCPp.class, pCPp);
		dbPCPp.setIdCPp(wsPCpp.getIdCpp());
	
		if(wsPCpp.getIdCpp()==null||wsPCpp.getIdCpp().equals(0l))
		{
		SMtfNo smtfNo = sMtfNoRepository.getByCompanyIdAndType(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), 12l);
		
	    long currentVal =smtfNo.getCurrentVal()+1;
	    smtfNo.setCurrentVal(currentVal);
	    sMtfNoRepository.save(smtfNo);
		
	    String mtNo = smtfNo.getPrefix()+String.format("%08d", currentVal);
	    pCPp.setCPpCode(mtNo);
		}
		
		if(wsPCpp.getActSt()==null)
		{
			pCPp.setActSt(null);
		}
		if(wsPCpp.getActFt()==null)
		{
			pCPp.setActFt(null);
		}
		
		if(wsPCpp.getPlanSt()==null)
		{
			pCPp.setPlanSt(null);
		}
		if(wsPCpp.getPlanFt()==null)
		{
			pCPp.setPlanFt(null);
		}

        if(wsPCpp.getmMachineId()!=null)
        {
        	dbPCPp.setMMachine(mMachineRepository.findOne(wsPCpp.getmMachineId()));
        }
        if(wsPCpp.getpRoutineDId()!=null)
        {
        	dbPCPp.setPRoutineD(pRoutineDRepository.findOne(wsPCpp.getpRoutineDId()));
        }
        if(wsPCpp.getpShiftPlanDId()!=null)
        {
        	dbPCPp.setPShiftPlanD(pShiftPlanDRepository.findOne(wsPCpp.getpShiftPlanDId()));
        }
        if(wsPCpp.getCompanyId()!=null)
        {
        	dbPCPp.setCompany(companyRepository.findOne(wsPCpp.getCompanyId()));
        }
        else
        {
        	dbPCPp.setCompany(securityUtils.getCurrentDBUser().getCompany());
        }
        if(wsPCpp.getOpId()!=null)
        {
        	dbPCPp.setUsers(usersRepository.findOne(wsPCpp.getOpId()));
        }
        if(wsPCpp.getPwoId()!=null)
        {
        	dbPCPp.setPWo(pWoRepository.findOne(wsPCpp.getPwoId()));
        }
		return dbPCPp;
	}
	
	protected WSPCpp toWSPCpp(PCPp pCPp) throws Exception
	{
		WSPCpp pc = (WSPCpp)BeanUtil.shallowCopy(pCPp, WSPCpp.class, null);
	    if(pCPp.getMMachine()!=null)
	    {
	    	pc.setmMachine(pCPp.getMMachine().getCode()+"_"+pCPp.getMMachine().getSpec());
	    	pc.setmMachineId(pCPp.getMMachine().getIdMachine());
	    
	    }
	    if(pCPp.getPRoutineD()!=null)
	    {
	    	pc.setpRoutineD(pCPp.getPRoutineD().getRouteNo());
	    	pc.setpRoutineDId(pCPp.getPRoutineD().getIdRoutineD());
	    	pc.setRouteDes(pCPp.getPRoutineD().getDes());
	    	PDraw draw = pCPp.getPRoutineD().getPRoutine().getPDraw();
	    	if(draw!=null)
	    	{
	    		pc.setDrawNo(draw.getDrawNo());
	    		pc.setDrawVer(draw.getDrawVer());
	    	}
	    
	    }
	    if(pCPp.getPShiftPlanD()!=null)
	    {
	    	pc.setpShiftPlanD(pCPp.getPShiftPlanD().getShift());
	    	pc.setpShiftPlanDId(pCPp.getPShiftPlanD().getIdShiftD());
	    }
	    if(pCPp.getPWo()!=null)
	    {
	    	pc.setPwo(pCPp.getPWo().getWoNo());
	    	pc.setPwoId(pCPp.getPWo().getIdWo());
	    }
	    if(pCPp.getUsers()!=null)
	    {
	    	pc.setOp(pCPp.getUsers().getName());
	    	pc.setOpId(pCPp.getUsers().getIdUser());
	    }
	    pc.setCppCode(pCPp.getCPpCode());
		return pc;
	}
	
	@Transactional(readOnly = true)
	public List<WSOEE> findWSOEE(Long fromDate,
			 Long toDate,Long machineId,Long materialId)
	{
		List<WSOEE> ws = new ArrayList<WSOEE>();
		Long companyId =securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		Date from = new Date(fromDate);
		Date to = new Date(toDate);
		List<PCPp> cpps;
		if(materialId!=null)
		{
			cpps = pCPpRepository.getByFromDateToDateAndMachineIdAndMaterialId(companyId, from, to, machineId,materialId);
		}
		else
		{
			cpps = pCPpRepository.getByFromDateToDateAndMachineId(companyId, from, to, machineId);
		}

		for(PCPp cpp:cpps)
		{
			if(cpp.getPRoutineD()==null)
			{
				continue;
			}
			if(cpp.getPlanSt()==null||cpp.getPlanFt()==null||cpp.getActSt()==null||cpp.getActFt()==null)
			{
				continue;
			}
			WSOEE w = new WSOEE();
			long actQty = (cpp.getActQty()==null)?0l:cpp.getActQty();
			w.setActQty(actQty);
			w.setActTime(cpp.getActFt().getTime()-cpp.getActSt().getTime());
			w.setCppSt(cpp.getPlanFt());
			
			long loadingTime = 0l;
	
			for(PActualSetup p: pActualSetupRepository.findByCppId(cpp.getIdCPp()))
			{
				if(p.getActFt()==null||p.getActSt()==null)
				{
					continue;
				}
				loadingTime = loadingTime + p.getActFt().getTime()-p.getActSt().getTime();
			}
			if(loadingTime==0l)
			{
				long setupTime = (cpp.getPRoutineD().getStdWtSetup()==null)?0l:cpp.getPRoutineD().getStdWtSetup().longValue();
				//System.out.println("std setup: " +cpp.getPRoutineD().getStdWtSetup());
				loadingTime = (long)(setupTime*60*60*1000);
			}
			w.setLoadingTime(loadingTime);
			w.setMachine(cpp.getMMachine().getCode());
			w.setMachineId(cpp.getMMachine().getIdMachine());
			long maTime = (cpp.getPRoutineD().getStdWtMachine()==null)?0l:cpp.getPRoutineD().getStdWtMachine().longValue();
	        long machineTime =(long)(maTime*60*60*1000);
			w.setMachineTime(machineTime);  //理论加工时长.设备公司

			w.setPassedQty(actQty); //to be modified
			w.setPlanQty(cpp.getQty());
			Long planStopTime =0l;
			for(PStopsPlan p:pStopsPlanRepository.getPStopsPlansByMachineIdAndDuration(cpp.getMMachine().getIdMachine(), cpp.getActSt(), cpp.getActFt()))
			{
				if(p.getActFt()!=null)
				{
					planStopTime = planStopTime + p.getPlanFt().getTime()-p.getActSt().getTime();
				}
				else
				{
					planStopTime = planStopTime + cpp.getPlanFt().getTime()-p.getActSt().getTime();
				}
				
			}
			w.setPlanStopTime(planStopTime);
			
			w.setPlanTime(cpp.getPlanFt().getTime()-cpp.getPlanSt().getTime());
			
			w.setRoutineD(cpp.getPRoutineD().getRouteNo() +"_" +cpp.getPRoutineD().getDes());
			w.setRoutineDId(cpp.getPRoutineD().getIdRoutineD());
			w.setShiftD(cpp.getPShiftPlanD().getShift());
			
			Long unplannnedStopTime =0l;
		
			for(PUnplannedStops p:pUnplannedStopsRepository.getByMachineIdAndDuration(machineId, cpp.getActSt(), cpp.getActFt()))
			{
				if(p.getEqFt()!=null)
				{
					unplannnedStopTime = unplannnedStopTime + p.getEqFt().getTime()-p.getOpSt().getTime();
				}
				else
				{
					unplannnedStopTime = unplannnedStopTime + cpp.getActFt().getTime()-p.getOpSt().getTime();
				}
			}
			
			w.setUnPlannedStopTime(unplannnedStopTime);
            w.setWo(cpp.getPWo().getWoNo());
            w.setWoId(cpp.getPWo().getIdWo());
            
 
            float passedEff =1f;
            w.setPassedEff(passedEff);
            
            long pt = cpp.getPlanFt().getTime()-cpp.getPlanSt().getTime()-planStopTime-unplannnedStopTime-loadingTime;
            long at = cpp.getPlanFt().getTime()-cpp.getPlanSt().getTime();
            
            
           
            float timeEff = (float)pt/(float)at;
            w.setTimeEff(timeEff);
           	
          //  logger.debug(" actQty: " +actQty +", machineTime: " + machineTime +", pt: " + pt );
            
         
            float machineEff =(float)(actQty*machineTime)/(float)pt; //性能开动率
            if(pt==0l)
            {
            	machineEff=1f;
            }
            w.setMachineEff(machineEff);
		
            float oee = timeEff*machineEff*passedEff;
            
            //logger.debug(" timeEff: " +timeEff +", machineEff: " + machineEff +", passedEff: " + passedEff );
			w.setOee(oee);
       
			ws.add(w);
		}
		return ws;
	}
	
	
	
	
	@Transactional(readOnly = true)
	public List<WSOEE> findLongWSOEE(Long fromDate,
			 Long toDate,Long machineId,Long materialId)
	{
		List<WSOEE> ws = new ArrayList<WSOEE>();
		Long companyId =securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		Date from = new Date(fromDate);
		Date to = new Date(toDate);
		
		//key 工单 ＋工序
		Map<String,WSOEE> oeeMap = new LinkedHashMap<String,WSOEE>();
		
		List<PCPp> cpps;
		if(materialId!=null)
		{
			cpps = pCPpRepository.getByFromDateToDateAndMachineIdAndMaterialId(companyId, from, to, machineId,materialId);
		}
		else
		{
			cpps = pCPpRepository.getByFromDateToDateAndMachineId(companyId, from, to, machineId);
		}
		
		for(PCPp cpp:cpps)
		{
			if(cpp.getPRoutineD()==null)
			{
				continue;
			}
			if(cpp.getPlanSt()==null||cpp.getPlanFt()==null||cpp.getActSt()==null||cpp.getActFt()==null)
			{
				continue;
			}
			Long woId = cpp.getPWo().getIdWo();
			Long routineDId = cpp.getPRoutineD().getIdRoutineD();
			WSOEE w;
			if(oeeMap.containsKey(woId+"_"+routineDId))
			{
				w = oeeMap.get(woId+"_"+routineDId);
			}
			else
			{
				w = new WSOEE();
				w.setWo(cpp.getPWo().getWoNo());
				w.setRoutineD(cpp.getPRoutineD().getRouteNo() +"_" +cpp.getPRoutineD().getDes());
				w.setPlanTime(0l);
				w.setActTime(0l);
				w.setMachine(cpp.getMMachine().getCode());
				w.setMachineId(cpp.getMMachine().getIdMachine());
				oeeMap.put(woId+"_"+routineDId, w);
				ws.add(w);
			}
			
			//计划时长
			long plant = w.getPlanTime()+cpp.getPlanFt().getTime()-cpp.getPlanSt().getTime();
		    w.setPlanTime(plant);
			
		    //实际时长
			long actT = w.getActTime() + cpp.getActFt().getTime()-cpp.getActSt().getTime();
			w.setActTime(actT);
			
			
			Long planStopTime =0l;
			for(PStopsPlan p:pStopsPlanRepository.getPStopsPlansByMachineIdAndDuration(cpp.getMMachine().getIdMachine(), cpp.getActSt(), cpp.getActFt()))
			{
				if(p.getActFt()!=null)
				{
					planStopTime = planStopTime + p.getPlanFt().getTime()-p.getActSt().getTime();
				}
				else
				{
					planStopTime = planStopTime + cpp.getPlanFt().getTime()-p.getActSt().getTime();
				}
				
			}
			w.setPlanStopTime(planStopTime);
			
			
			Long unplannnedStopTime =0l;
			
			for(PUnplannedStops p:pUnplannedStopsRepository.getByMachineIdAndDuration(machineId, cpp.getActSt(), cpp.getActFt()))
			{
				if(p.getEqFt()!=null)
				{
					unplannnedStopTime = unplannnedStopTime + p.getEqFt().getTime()-p.getOpSt().getTime();
				}
				else
				{
					unplannnedStopTime = unplannnedStopTime + cpp.getActFt().getTime()-p.getOpSt().getTime();
				}
			}
			
			w.setUnPlannedStopTime(unplannnedStopTime);

            float oee = (float)plant/(float)actT;
			w.setOee(oee);
       
		
		}
		return ws;
	}
	
	
	
	
	@Transactional(readOnly = true)
	public WSPlannedMaterialSending findWSPlannedMaterialSending(Long fromDate,
			 Long toDate,
			 Long fromStkId,
			 Long toStkId)  {
	 //  logger.debug("fromDate: " + fromDate  +", toDate: " + toDate +", fromStkId: " + fromStkId +", toStkId: " + toStkId);
		WSPlannedMaterialSending ws = new WSPlannedMaterialSending();
		ws.setFromStkId(fromStkId);
		ws.setToStkId(toStkId);
		List<WSPlannedMaterialSendingItem> items = new ArrayList<WSPlannedMaterialSendingItem>();
		Long companyId =securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		Date from = new Date(fromDate);
		Date to = new Date(toDate);
		List<PCPp> cpps = pCPpRepository.getByFromDateToDate(companyId, from, to);
		for(PCPp cpp:cpps)
		{
	//		 logger.debug("cppId: " + cpp.getIdCPp()  +", code: " + cpp.getCPpCode());
			 PWo pwo =cpp.getPWo();
//			 logger.debug("woNo: " + pwo.getWoNo());
			 SMaterial product =  pwo.getSSo().getSMaterial();	
//			 logger.debug("product: " + product);
			 Long idMachine = cpp.getMMachine().getIdMachine();
			 String machine = cpp.getMMachine().getCode();
//			 logger.debug("idMachine: " + idMachine +", machine: " + machine);
			 if(cpp.getMMachine().getSBin()==null)
			 {
				// logger.debug("此机台无位置设定，不能发料！" +"idMachine: " + idMachine +", machine: " + machine);
				 continue;
			 }
			 if(!cpp.getMMachine().getSBin().getSStk().getId().equals(toStkId))
			 {
				 //logger.debug("skip this machine, " +"idMachine: " + idMachine +", machine: " + machine);
				 continue;
			 }
			 PBom pBom = pBomRepository.findProductByMaterialId(product.getIdMaterial());
				if(pBom!=null)
				{
					if(cpp.getPRoutineD()!=null)
					{
						for(PBom p: pBomRepository.findMaterialsByRoutineDId(cpp.getPRoutineD().getIdRoutineD()))
						{	
							SMaterial material =p.getSMaterial();
//							logger.debug("bomId: " + p.getIdBom() + ", material id: " +material.getIdMaterial() + ", material: " + material.getPno());
							for(SInventory inv:sInventoryRepository.findInventoryByMaterialAndStk(material.getIdMaterial(), companyId, fromStkId))
							{
								if(inv.getQty()!=null&&inv.getQty().intValue()<=0)
								{
									continue;
								}
								String lotNo = (inv.getLotNo()==null)?"":inv.getLotNo();
//								logger.debug("inv qty: " + inv.getQty() + ", lotNo: " + lotNo);
								WSPlannedMaterialSendingItem w = new WSPlannedMaterialSendingItem();
								w.setBomId(p.getIdBom());
								w.setBin(inv.getSBin().getBin());
								w.setCppId(cpp.getIdCPp());
								w.setFromBinId(inv.getSBin().getIdBin());
								w.setIdMachine(idMachine);
								w.setIdMaterial(material.getIdMaterial());
								w.setInvId(inv.getIdInv());
								w.setLotNo(lotNo);
								w.setMachine(machine);
								w.setMaterial(material.getPno()+"_"+material.getRev()+"_"+material.getDes());
								w.setPlannedQty(cpp.getQty());
								w.setPlannedSt(cpp.getPlanSt());
								w.setProduction(product.getPno()+"_"+product.getRev()+"_" + product.getDes());
								w.setQty(0l);
								Long qtyDelivered=0l;
								for(PMr pmr: pMrRepository.getByBomIdAndCppId(p.getIdBom(), cpp.getIdCPp()))
								{
									Long d = (pmr.getQtyDelivered()==null)?0l:pmr.getQtyDelivered();
									qtyDelivered =qtyDelivered+d;
								}
								w.setQtyDelivered(qtyDelivered);
								w.setQtyStored(inv.getQty());
								w.setShiftD(cpp.getPShiftPlanD().getShift());
								w.setToBinId(cpp.getMMachine().getSBin().getIdBin());
								Long shouldQty = 0l;
								if(p.getQpu()!=null)
								{
									float a  = (float)cpp.getQty();
									float t = a * p.getQpu();
									shouldQty = (long)t;
								}
								w.setShouldQty(shouldQty);
								w.setWip(cpp.getMMachine().getSBin().getSStk().getStkName());
								w.setWoId(pwo.getIdWo());
								w.setWoNo(pwo.getWoNo());
								items.add(w);
				
							}
						}
					}

				}
		}
		
		
		ws.setItems(items);
		return ws;
		
		
	}
	
	

	@Transactional(readOnly=true)
	public List<WSPMr>  findWSPMrsByCppId(Long cppId)
	{
		 List<WSPMr> ws = new ArrayList<WSPMr>();
		 PCPp pCPp = pCPpRepository.findOne(cppId);
		 SMaterial material =  pCPp.getPWo().getSSo().getSMaterial();	 
		 PBom pBom = pBomRepository.findProductByMaterialId(material.getIdMaterial());

			if(pBom!=null)
			{
				for(PBom p: pBom.getPBoms())
				{
				//	PMr pmr = pMrRepository.getByBomIdAndCppId(p.getIdBom(), cppId);
					SMaterial s =p.getSMaterial();
					WSPMr w = new WSPMr();
					w.setDes(s.getDes());
					w.setIdMaterial(s.getIdMaterial());
					w.setPno(s.getPno());
					w.setQty(s.getMpq());
					w.setCppId(cppId);
					w.setRev(s.getRev());
					w.setBomId(p.getIdBom());
//					if(pmr!=null)
//					{
//						if(pmr.getQtyDelivered()!=null)
//						{
//							w.setQtyDelivered(pmr.getQtyDelivered());
//						}
//						
//					}
//	                w.setQtyDelivered(p.getq);
				    ws.add(w);
				   
				}
				
			}
		 return ws;
	}
}
