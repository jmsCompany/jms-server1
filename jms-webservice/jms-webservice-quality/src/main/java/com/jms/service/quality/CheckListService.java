package com.jms.service.quality;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.jms.domain.db.QCheckList;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.q.WSQCheckList;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.p.PRoutineDRepository;
import com.jms.repositories.q.QCheckListRepository;
import com.jms.repositories.q.QItemTypeRepository;
import com.jms.repositories.q.QTesterRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.web.security.SecurityUtils;


@Service
@Transactional
public class CheckListService {
	private static final Logger logger = LogManager.getLogger(CheckListService.class
			.getCanonicalName());
	@Autowired
	private QCheckListRepository qCheckListRepository;
	@Autowired
	private  SecurityUtils securityUtils;
	@Autowired
	private PRoutineDRepository pRoutineDRepository;
	@Autowired
	private QItemTypeRepository qItemTypeRepository;
	@Autowired
	private QTesterRepository qTesterRepository;
	@Autowired
	private SMaterialRepository sMaterialRepository;
	

	public WSQCheckList saveCheckList(@RequestBody WSQCheckList wsQCheckList) throws Exception {
		QCheckList qCheckList;
		if(wsQCheckList.getIdCheckList()!=null&&!wsQCheckList.getIdCheckList().equals(0l))
		{
			qCheckList = qCheckListRepository.findOne(wsQCheckList.getIdCheckList());
		}
		else
		{
			qCheckList = new QCheckList();
			qCheckList.setCompany(securityUtils.getCurrentDBUser().getCompany());
		}
		QCheckList dbQCheckList = toDBQCheckList(wsQCheckList,qCheckList);
		qCheckListRepository.save(dbQCheckList);
		wsQCheckList.setIdCheckList(dbQCheckList.getIdCheckList());
		return wsQCheckList;
	}
	
	
	public Valid deleteCheckList(@RequestParam("checkListlId") Long checkListlId) {
		Valid v = new Valid();
		v.setValid(true);
		qCheckListRepository.delete(checkListlId);
		return v;
		
	}
	
	@Transactional(readOnly=true)
	public WSQCheckList findCheckList(Long checkListId) throws Exception {
		return toWSQCheckList(qCheckListRepository.findOne(checkListId));
	}
	

	
	private QCheckList toDBQCheckList(WSQCheckList wsQCheckList,QCheckList qCheckList) throws Exception
	{
	
		QCheckList dbQCheckList = (QCheckList)BeanUtil.shallowCopy(wsQCheckList, QCheckList.class, qCheckList);

		dbQCheckList.setCompany(securityUtils.getCurrentDBUser().getCompany());
		logger.debug("routineDId: " + wsQCheckList.getpRoutineDId());
		dbQCheckList.setPRoutineD(pRoutineDRepository.findOne(wsQCheckList.getpRoutineDId()));
	//	dbQCheckList.setQItemType(qItemTypeRepository.findOne(wsQCheckList.getqItemTypeId()));
		dbQCheckList.setQTester(qTesterRepository.findOne(wsQCheckList.getqTesterId()));
		dbQCheckList.setSMaterial(sMaterialRepository.findOne(wsQCheckList.getMaterialId()));
		return dbQCheckList;
	}
	
	private WSQCheckList toWSQCheckList(QCheckList qCheckList) throws Exception
	{
		WSQCheckList pc = (WSQCheckList)BeanUtil.shallowCopy(qCheckList, WSQCheckList.class, null);
		pc.setCompanyId(qCheckList.getCompany().getIdCompany());
		pc.setCompany(qCheckList.getCompany().getCompanyName());
        pc.setMaterialId(qCheckList.getSMaterial().getIdMaterial());
        pc.setSmaterial(qCheckList.getSMaterial().getPno());
        pc.setRev(qCheckList.getSMaterial().getRev());
        pc.setDes(qCheckList.getSMaterial().getDes());
        pc.setpRoutineD(qCheckList.getPRoutineD().getRouteNo());
        pc.setpRoutineDId(qCheckList.getPRoutineD().getIdRoutineD());
      //  pc.setqItemType(qCheckList.getQItemType().getDes());
     //   pc.setqItemTypeId(qCheckList.getQItemType().getIdItemType());
        pc.setqTester(qCheckList.getQTester().getDes());
        pc.setqTesterId(qCheckList.getQTester().getIdTester());	
		return pc;
	}
	
}
