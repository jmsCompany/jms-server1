package com.jms.service.production;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.PBom;
import com.jms.domain.db.PCPp;
import com.jms.domain.db.PCheckPlan;
import com.jms.domain.db.PRoutine;
import com.jms.domain.db.PRoutineD;
import com.jms.domain.db.PWo;
import com.jms.domain.db.PWoBom;
import com.jms.domain.db.PWoRoute;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMtfMaterial;
import com.jms.domain.db.SMtfNo;
import com.jms.domain.db.SSo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSBomMaterialObj;
import com.jms.domain.ws.p.WSPRoutineD;
import com.jms.domain.ws.p.WSPWo;
import com.jms.domain.ws.p.WSShiftPlanDStatus;
import com.jms.domain.ws.p.WSWoStatus;
import com.jms.domain.ws.s.WSMaterialQty;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.p.PBomRepository;
import com.jms.repositories.p.PCPpRepository;
import com.jms.repositories.p.PCheckPlanRepository;
import com.jms.repositories.p.PRoutineDRepository;
import com.jms.repositories.p.PRoutineRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWoBomRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.s.SMtfMaterialRepository;
import com.jms.repositories.s.SMtfNoRepository;
import com.jms.repositories.s.SSoRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class WoService {

	private static final Logger logger = LogManager.getLogger(WoService.class.getCanonicalName());
	@Autowired private PWoRepository pWoRepository;
	@Autowired private SSoRepository sSoRepository;
	@Autowired private PBomRepository pBomRepository;
	@Autowired private PCPpRepository pCPpRepository;
	@Autowired private PStatusDicRepository pStatusDicRepository;
	@Autowired private SecurityUtils securityUtils;
	@Autowired private PCheckPlanRepository pCheckPlanRepository;
	@Autowired private SMtfNoRepository sMtfNoRepository;
	@Autowired private PRoutineRepository pRoutineRepository;
	@Autowired private PRoutineDRepository pRoutineDRepository;
	@Autowired private PWoBomRepository pWoBomRepository;
	@Autowired private  SMtfMaterialRepository sMtfMaterialRepository;

	@Transactional(readOnly = false)
	public WSPWo saveWSPWo(WSPWo wsPWo) throws Exception {
		PWo pWo;
		boolean isNew = true;
		Long sQty = 0l;
		if (wsPWo.getIdWo() != null && !wsPWo.getIdWo().equals(0l)) {
			pWo = pWoRepository.findOne(wsPWo.getIdWo());
			sQty = pWo.getQty();
			isNew = false;
		} else {
			pWo = new PWo();
			pWo.setCreationTime(new Date());
		}
		PWo dbPWo = toDBPWo(wsPWo, pWo);
		dbPWo = pWoRepository.save(dbPWo);

		SSo so = dbPWo.getSSo();
		if (so != null) {
			SMaterial mat = so.getSMaterial();
			if (mat != null) {
				if (isNew) {
					PBom pbom = pBomRepository.findProductByMaterialId(mat.getIdMaterial());
					List<PBom> boms = pBomRepository.findMaterialsByProductId(pbom.getIdBom());
					Long qty = dbPWo.getQty();
					for (PBom bom : boms) {
						PWoBom woBom = new PWoBom();
						woBom.setIdMat(bom.getSMaterial().getIdMaterial());
						woBom.setIdBomLabel(pbom.getPBomLabel().getIdBomLabel());
						woBom.setLvl(bom.getLvl());
						woBom.setOrderBy(bom.getOrderBy());
						woBom.setPWo(dbPWo);
						woBom.setQpu(bom.getQpu());
						woBom.setWastage(bom.getWastage());
						woBom.setWip(bom.getWip());
						Float waste = bom.getWastage() == null ? 0f : bom.getWastage();
						Float q = bom.getQpu() == null ? 1l : bom.getQpu();
						double a = (qty * q) / (1 - waste);
						double b = Math.ceil(a);
						woBom.setQty((long) (b));
						woBom.setQtyRev(0l);
						pWoBomRepository.save(woBom);
					}
				} else {
					if (!wsPWo.getQty().equals(sQty)) {
						List<PWoBom> boms = pWoBomRepository.findByIdWo(dbPWo.getIdWo());
						Long qty = wsPWo.getQty();
						for (PWoBom bom : boms) {
							Float waste = bom.getWastage() == null ? 0f : bom.getWastage();
							Float q = bom.getQpu() == null ? 1l : bom.getQpu();
							double a = (qty * q) / (1 - waste);
							double b = Math.ceil(a);
							bom.setQty((long) (b));
							pWoBomRepository.save(bom);
						}

					}
				}
			}

		}

		wsPWo.setIdWo(dbPWo.getIdWo());
		return wsPWo;

	}

	
	@Transactional(readOnly = false)
	public Valid deletePWo(Long woId) {
		Valid valid = new Valid();
		pWoRepository.delete(woId);
		valid.setValid(true);
		return valid;
	}

	
	@Transactional(readOnly = true)
	public WSPWo findWSPwo(Long woId) throws Exception {
		PWo pWo = pWoRepository.findOne(woId);
		return toWSPWo(pWo);
	}

	
	
	@Transactional(readOnly = true)
	public List<WSWoStatus> findWSPwoStatus(Long companyId, Long delay) {
		Map<String, String> routineDMap = new LinkedHashMap<String, String>();
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
		// Long companyId =
		// securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<PWo> pWos = pWoRepository.getActiveWosByCompanyId(companyId);
		long i = 1;
		for (PWo p : pWos) {
			// logger.debug("woNo: " + p.getWoNo());
			SMaterial s = p.getSSo().getSMaterial();
			PRoutine routine = pRoutineRepository.getByMaterialIdAndPline(s.getIdMaterial(), "PULLEY");
			if (routine == null) {
				logger.debug("no such routine!");
			}
			List<PCPp> cpps = pCPpRepository.getShouldStartedByWoId(p.getIdWo());

			if (routine != null && cpps != null && !cpps.isEmpty()) {
				// logger.debug("has cpps: " + cpps.size());
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

				WSShiftPlanDStatus r14 = new WSShiftPlanDStatus();
				r14.setShiftNo("150");
				w.setR14(r14);

				WSShiftPlanDStatus r15 = new WSShiftPlanDStatus();
				r15.setShiftNo("160");
				w.setR15(r15);

				WSShiftPlanDStatus r16 = new WSShiftPlanDStatus();
				r16.setShiftNo("170");
				w.setR16(r16);

				for (PRoutineD rd : routineDList) {

					List<PCPp> dcpps = pCPpRepository.getFinishedCppByRoutineDId(rd.getIdRoutineD());
					if (dcpps != null && !dcpps.isEmpty()) {
						// logger.debug("cppsize for rd: " + rd.getRouteNo() +",
						// cpps size: " + dcpps.size());
						long dev = 0; // 延迟，小时
						long devStart = 0;
						for (PCPp c : dcpps) {
							if (c.getPlanSt() != null && c.getPlanFt() != null && c.getActSt() != null
									&& c.getActFt() != null) {
								if (devStart < (c.getActSt().getTime() - c.getPlanSt().getTime())) {
									devStart = c.getActSt().getTime() - c.getPlanSt().getTime();
								}

								dev = dev + ((c.getActFt().getTime() - c.getActSt().getTime())
										- (c.getPlanFt().getTime() - c.getPlanSt().getTime())) / (1000 * 60 * 60);
							}
						}
						devStart = devStart / (1000 * 60 * 60);
						if (rd.getRouteNo().equals("010")) {
							r1.setDelayStart("" + devStart);
							if (dev >= delay) {
								r1.setDelay("" + dev);
								r1.setStatus("3.png");

							} else if (dev < delay && dev >= 0) {
								r1.setStatus("2.png");
							} else {
								r1.setStatus("1.png");
							}

						}

						if (rd.getRouteNo().equals("020")) {
							r2.setDelayStart("" + devStart);
							if (dev >= delay) {

								r2.setDelay("" + dev);
								r2.setStatus("3.png");
							} else if (dev < delay && dev >= 0) {
								r2.setStatus("2.png");
							} else {
								r2.setStatus("1.png");
							}

						}

						if (rd.getRouteNo().equals("030")) {
							r3.setDelayStart("" + devStart);
							if (dev >= delay) {

								r3.setDelay("" + dev);
								r3.setStatus("3.png");
							} else if (dev < delay && dev >= 0) {
								r3.setStatus("2.png");
							} else {
								r3.setStatus("1.png");
							}

						}

						if (rd.getRouteNo().equals("050")) {
							r4.setDelayStart("" + devStart);
							if (dev >= delay) {

								r4.setDelay("" + dev);
								r4.setStatus("3.png");
							} else if (dev < delay && dev >= 0) {
								r4.setStatus("2.png");
							} else {
								r4.setStatus("1.png");
							}
						}

						
						if (rd.getRouteNo().equals("060")) {
							r5.setDelayStart("" + devStart);
							if (dev >= delay) {
								r5.setDelay("" + dev);
								r5.setStatus("3.png");
							} else if (dev < delay && dev >= 0) {
								r5.setStatus("2.png");
							} else {
								r5.setStatus("1.png");
							}

						}

						if (rd.getRouteNo().equals("080")) {
							r6.setDelayStart("" + devStart);
							if (dev >= delay) {
								r6.setDelay("" + dev);
								r6.setStatus("3.png");
							} else if (dev < delay && dev >= 0) {
								r6.setStatus("2.png");
							} else {
								r6.setStatus("1.png");
							}

						}

						if (rd.getRouteNo().equals("090")) {
							r7.setDelayStart("" + devStart);
							if (dev >= delay) {
								r7.setDelay("" + dev);
								r7.setStatus("3.png");
							} else if (dev < delay && dev >= 0) {
								r7.setStatus("2.png");
							} else {
								r7.setStatus("1.png");
							}

						}

						if (rd.getRouteNo().equals("100")) {
							r8.setDelayStart("" + devStart);
							if (dev >= delay) {
								r8.setDelay("" + dev);
								r8.setStatus("3.png");
							} else if (dev < delay && dev >= 0) {
								r8.setStatus("2.png");
							} else {
								r8.setStatus("1.png");
							}

						}

						if (rd.getRouteNo().equals("105")) {
							r9.setDelayStart("" + devStart);
							if (dev >= delay) {
								r9.setDelay("" + dev);
								r9.setStatus("3.png");
							} else if (dev < delay && dev >= 0) {
								r9.setStatus("2.png");
							} else {
								r9.setStatus("1.png");
							}

						}

						if (rd.getRouteNo().equals("110")) {
							r10.setDelayStart("" + devStart);
							if (dev >= delay) {
								r10.setDelay("" + dev);
								r10.setStatus("3.png");
							} else if (dev < delay && dev >= 0) {
								r10.setStatus("2.png");
							} else {
								r10.setStatus("1.png");
							}

						}

						if (rd.getRouteNo().equals("120")) {
							r11.setDelayStart("" + devStart);
							if (dev >= delay) {
								r11.setDelay("" + dev);
								r11.setStatus("3.png");
							} else if (dev < delay && dev >= 0) {
								r11.setStatus("2.png");
							} else {
								r11.setStatus("1.png");
							}
						}

						if (rd.getRouteNo().equals("130")) {
							r12.setDelayStart("" + devStart);
							if (dev >= delay) {
								r12.setDelay("" + dev);
								r12.setStatus("3.png");
							} else if (dev < delay && dev >= 0) {
								r12.setStatus("2.png");
							} else {
								r12.setStatus("1.png");
							}

						}

						if (rd.getRouteNo().equals("140")) {
							r13.setDelayStart("" + devStart);
							if (dev >= delay) {

								r13.setDelay("" + dev);
								r13.setStatus("3.png");
							} else if (dev < delay && dev >= 0) {
								r13.setStatus("2.png");
							} else {
								r13.setStatus("1.png");
							}

						}

						if (rd.getRouteNo().equals("150")) {
							r14.setDelayStart("" + devStart);
							if (dev >= delay) {

								r14.setDelay("" + dev);
								r14.setStatus("3.png");
							} else if (dev < delay && dev >= 0) {
								r14.setStatus("2.png");
							} else {
								r14.setStatus("1.png");
							}

						}

						if (rd.getRouteNo().equals("160")) {
							r15.setDelayStart("" + devStart);
							if (dev >= delay) {
								r15.setDelay("" + dev);
								r15.setStatus("3.png");
							} else if (dev < delay && dev >= 0) {
								r15.setStatus("2.png");
							} else {
								r15.setStatus("1.png");
							}

						}

						if (rd.getRouteNo().equals("170")) {
							if (dev >= delay) {
								r16.setDelayStart("" + devStart);
								r16.setDelay("" + dev);
								r16.setStatus("3.png");
							} else if (dev < delay && dev >= 0) {
								r16.setStatus("2.png");
							} else {
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

	@Transactional(readOnly = true)
	public WSMaterialQty findWSMaterialQtyByWoId(Long woId) {
		WSMaterialQty mq = new WSMaterialQty();
		PWo pWo = pWoRepository.findOne(woId);
		SMaterial material = pWo.getSSo().getSMaterial();
		mq.setIdMaterial(material.getIdMaterial());
		mq.setPno(material.getPno());
		mq.setRev(material.getRev());
		mq.setDes(material.getDes());
		mq.setQty(pWo.getQty());
		return mq;

	}

	private PWo toDBPWo(WSPWo wsPWo, PWo pWo) throws Exception {
		PWo dbPWo = (PWo) BeanUtil.shallowCopy(wsPWo, PWo.class, pWo);
		if (wsPWo.getIdWo() == null || wsPWo.getIdWo().equals(0l)) {
			if (wsPWo.getWoNo() == null) {
				SMtfNo smtfNo = sMtfNoRepository
						.getByCompanyIdAndType(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), 9l);
				long currentVal = smtfNo.getCurrentVal() + 1;
				smtfNo.setCurrentVal(currentVal);
				sMtfNoRepository.save(smtfNo);
				String codePo = smtfNo.getPrefix() + String.format("%08d", currentVal);
				dbPWo.setWoNo(codePo);
			}
		}

		if (wsPWo.getSoId() != null)
			dbPWo.setSSo(sSoRepository.findOne(wsPWo.getSoId()));
		dbPWo.setUsers(securityUtils.getCurrentDBUser());
		if (wsPWo.getStatusId() != null)
			dbPWo.setPStatusDic(pStatusDicRepository.findOne(wsPWo.getStatusId()));
		return dbPWo;
	}

	
	private WSPWo toWSPWo(PWo pWo) throws Exception {
		WSPWo pc = (WSPWo) BeanUtil.shallowCopy(pWo, WSPWo.class, null);
		if (pWo.getUsers() != null) {
			pc.setCreator(pWo.getUsers().getName());
		}
		if (pWo.getPStatusDic() != null) {
			pc.setStatus(pWo.getPStatusDic().getName());
			pc.setStatusId(pWo.getPStatusDic().getIdPstatus());
		}
		if (pWo.getSSo() != null) {
			pc.setSo(pWo.getSSo().getCodeSo());
			pc.setSoId(pWo.getSSo().getIdSo());
			SMaterial s = pWo.getSSo().getSMaterial();
			if (s != null) {
				pc.setMaterialId(s.getIdMaterial());
				pc.setPno(s.getPno());
				pc.setRev(s.getRev());
				pc.setDes(s.getDes());

			}
		}
		for (PWoRoute routine : pWo.getPWoRoutes()) {
			WSPRoutineD wd = new WSPRoutineD();
			wd.setIdRoutineD(routine.getPRoutineD().getIdRoutineD());
			wd.setRouteNo(routine.getPRoutineD().getRouteNo());
			pc.getRoutines().add(wd);
		}

		// pc.setQty(pWo.getQty());
		Long qtyFinished = 0l;
		for (PCPp p : pWo.getPCPps()) {
			List<PCheckPlan> pCheckPlans = pCheckPlanRepository.getMaxCheckPlanByCppId(p.getIdCPp());
			if (pCheckPlans != null && !pCheckPlans.isEmpty())
				qtyFinished = qtyFinished + pCheckPlans.get(0).getFinQty();
		}

		pc.setQtyFinished(qtyFinished);
		return pc;
	}

	
	public List<WSBomMaterialObj> getMaterialsByWoId(Long woId) throws Exception {
		List<WSBomMaterialObj> ws = new ArrayList<WSBomMaterialObj>();
		SMaterial s = pWoRepository.findByWoId(woId); //
		// logger.debug("woId: " + woId + " , materialId: " +
		// s.getIdMaterial());
		PBom pBom = pBomRepository.findProductByMaterialId(s.getIdMaterial());
		if (pBom != null) {
			for (PBom p : pBom.getPBoms()) {
				SMaterial material = p.getSMaterial();
				WSBomMaterialObj w = new WSBomMaterialObj(p.getIdBom() + "_" + material.getIdMaterial(),
						material.getPno() + "-" + material.getRev() + "-" + material.getDes());
				ws.add(w);

			}

		}

		return ws;
	}
	
	
	public List<WSBomMaterialObj> getMaterialsByWoIdAndQ(Long woId, String q) throws Exception {
		List<WSBomMaterialObj> ws = new ArrayList<WSBomMaterialObj>();
		SMaterial s = pWoRepository.findByWoId(woId); //
		PBom pBom = pBomRepository.findProductByMaterialId(s.getIdMaterial());
		if (pBom != null) {
			for (PBom p : pBom.getPBoms()) {
				SMaterial material = p.getSMaterial();
				if(q!=null&&!q.isEmpty())
				{
					String des = "";
					if(material.getDes()!=null)
					{
						des = material.getDes();
					}
					if(material.getPno().contains(q)||des.contains(q))
					{
						WSBomMaterialObj w = new WSBomMaterialObj(
								p.getIdBom() + "_" + material.getIdMaterial(),
								material.getPno() + "-" + material.getRev() + "-" + material.getDes());
						ws.add(w);
					}
				}
				else
				{
					WSBomMaterialObj w = new WSBomMaterialObj(
							p.getIdBom() + "_" + material.getIdMaterial(),
							material.getPno() + "-" + material.getRev() + "-" + material.getDes());
					ws.add(w);
				}
			

			}

		}

		return ws;
	}
	

	public void getboms(Long ...woIds)
	{
		for (Long woId : woIds)
		{
			PWo pWo =pWoRepository.findOne(woId);
			SSo sSo =pWo.getSSo();		
			PBom pBom = pBomRepository.findProductByMaterialId(sSo.getSMaterial().getIdMaterial());
			Set<PBom> pBoms =pBom.getPBoms();
			for (PBom w : pBoms) {
				List<SMtfMaterial> mats = sMtfMaterialRepository.getByWoIdAndMaterialId(woId, w.getSMaterial().getIdMaterial());
	            Long delivered = 0l; //已发数量
				if(mats!=null&&!mats.isEmpty())
				{
					for(SMtfMaterial s: mats)
					{
						delivered = delivered+ s.getQty();
					}
					PWoBom pWoBom = pWoBomRepository.findByIdWoAndIdMaterial(woId, w.getSMaterial().getIdMaterial());
					pWoBom.setQtyRev(delivered);
				}
			}
		}
		
	}
	
	
	public void copyWoPom(Long ...woIds) {
		for (Long idWo : woIds)
		{
			PWo pwo = pWoRepository.findOne(idWo);
			SSo so = pwo.getSSo();
			if (so != null) {
				SMaterial mat = so.getSMaterial();
				if (mat != null) {
					PBom pbom = pBomRepository.findProductByMaterialId(mat.getIdMaterial());
					List<PBom> boms = pBomRepository.findMaterialsByProductId(pbom.getIdBom());
					Long qty = pwo.getQty();
					for (PBom bom : boms) {
						PWoBom woBom = new PWoBom();
						woBom.setIdMat(bom.getSMaterial().getIdMaterial());
						woBom.setIdBomLabel(pbom.getPBomLabel().getIdBomLabel());
						woBom.setLvl(bom.getLvl());
						woBom.setOrderBy(bom.getOrderBy());
						woBom.setPWo(pwo);
						woBom.setQpu(bom.getQpu());
						woBom.setWastage(bom.getWastage());
						woBom.setWip(bom.getWip());
						Float waste = bom.getWastage() == null ? 0f : bom.getWastage();
						Float q = bom.getQpu() == null ? 1l : bom.getQpu();
						double a = (qty * q) / (1 - waste);
						double b = Math.ceil(a);
						woBom.setQty((long) (b));
						woBom.setQtyRev(0l);
						pWoBomRepository.save(woBom);
					}
				}
			}
		}
	}

	
}
