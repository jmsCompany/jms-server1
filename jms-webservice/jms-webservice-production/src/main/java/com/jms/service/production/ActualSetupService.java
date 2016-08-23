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
import com.jms.domain.db.PActualSetup;
import com.jms.domain.db.PBom;
import com.jms.domain.db.PCPp;
import com.jms.domain.db.PWo;
import com.jms.domain.db.PWorkCenter;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SStk;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.p.WSPActualSetup;
import com.jms.domain.ws.p.WSPBom;
import com.jms.domain.ws.p.WSPBomItem;
import com.jms.domain.ws.p.WSPWo;
import com.jms.domain.ws.p.WSPWorkCenter;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.f.FCostCenterRepository;
import com.jms.repositories.p.PActualSetupRepository;
import com.jms.repositories.p.PBomLabelRepository;
import com.jms.repositories.p.PBomRepository;
import com.jms.repositories.p.PCPpRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SSoRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class ActualSetupService {

	private static final Logger logger = LogManager.getLogger(ActualSetupService.class.getCanonicalName());
	@Autowired
	private PActualSetupRepository pActualSetupRepository;

	@Autowired
	private PCPpRepository pCPpRepository;

	@Autowired
	private SMaterialRepository sMaterialRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private PStatusDicRepository pStatusDicRepository;

	@Autowired
	private SecurityUtils securityUtils;

	@Transactional(readOnly = false)
	public WSPActualSetup saveWSPActualSetup(WSPActualSetup wsPActualSetup) {

		PActualSetup pActualSetup;
		if (wsPActualSetup.getIdActualSetup() != null && !wsPActualSetup.getIdActualSetup().equals(0l)) {
			pActualSetup = pActualSetupRepository.findOne(wsPActualSetup.getIdActualSetup());
		} else {
			pActualSetup = new PActualSetup();

		}

		if (wsPActualSetup.getActFt() == null) {
			pActualSetup.setActSt(new Date());
		} else {
			pActualSetup.setActFt(wsPActualSetup.getActFt());
		}
		pActualSetup.setActSt(wsPActualSetup.getActSt());
		pActualSetup.setPCPp(pCPpRepository.findOne(wsPActualSetup.getCppId()));
		pActualSetup = pActualSetupRepository.save(pActualSetup);
		wsPActualSetup.setIdActualSetup(pActualSetup.getIdActualSetup());
		wsPActualSetup.setActFt(pActualSetup.getActFt());
		wsPActualSetup.setActSt(pActualSetup.getActSt());
		return wsPActualSetup;

	}

	@Transactional(readOnly = true)
	public List<WSPActualSetup> findWSPActualSetups(Long cppId) {
		List<WSPActualSetup> ws = new ArrayList<WSPActualSetup>(0);
		List<PActualSetup> ps = pActualSetupRepository.findByCppId(cppId);

		PCPp cpp = pCPpRepository.findOne(cppId);

		PWo wo = cpp.getPWo();
		SMaterial material = wo.getSSo().getSMaterial();
		WSPActualSetup w = new WSPActualSetup();
		w.setWoNo(wo.getWoNo());

		w.setDes(material.getDes());
		w.setPno(material.getPno());
		w.setRev(material.getRev());

		w.setMachine(cpp.getMMachine().getCode());

		w.setShift(cpp.getPShiftPlanD().getShift());
		w.setCppId(cpp.getIdCPp());
		ws.add(w);

		for (PActualSetup p : ps) {
			WSPActualSetup w1 = new WSPActualSetup();
			w1.setIdActualSetup(p.getIdActualSetup());
			w1.setActFt(p.getActFt());
			w1.setActSt(p.getActSt());

			w1.setWoNo(wo.getWoNo());

			w1.setDes(material.getDes());
			w1.setPno(material.getPno());
			w1.setRev(material.getRev());

			w1.setMachine(cpp.getMMachine().getCode());

			w1.setShift(cpp.getPShiftPlanD().getShift());
			w1.setCppId(cpp.getIdCPp());
			ws.add(w1);
		}

		return ws;

	}

	@Transactional(readOnly = true)
	public WSPActualSetup findWSPActualSetupById(Long actSetupId) {

		PActualSetup ps = pActualSetupRepository.findOne(actSetupId);

		PWo wo = ps.getPCPp().getPWo();
		SMaterial material = wo.getSSo().getSMaterial();
		WSPActualSetup w = new WSPActualSetup();
		w.setWoNo(wo.getWoNo());

		w.setDes(material.getDes());
		w.setPno(material.getPno());
		w.setRev(material.getRev());

		w.setMachine(ps.getPCPp().getMMachine().getCode());

		w.setShift(ps.getPCPp().getPShiftPlanD().getShift());
		w.setCppId(ps.getPCPp().getIdCPp());

		w.setActFt(ps.getActFt());
		w.setActSt(ps.getActSt());

		w.setShift(ps.getPCPp().getPShiftPlanD().getShift());
		w.setCppId(ps.getPCPp().getIdCPp());
		w.setCpp(ps.getPCPp().getCPpCode()+"_"+ps.getPCPp().getPlanSt().toString());

		
		w.setIdActualSetup(ps.getIdActualSetup());
		return w;

	}

}
