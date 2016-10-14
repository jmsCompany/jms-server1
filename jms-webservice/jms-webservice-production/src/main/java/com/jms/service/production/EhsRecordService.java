package com.jms.service.production;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.EhsItem;
import com.jms.domain.db.EhsRecord;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.ehs.WSEHSItem;
import com.jms.domain.ws.ehs.WSEHSRecord;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.p.EhsItemRepository;
import com.jms.repositories.p.EhsRecordRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class EhsRecordService {

	private static final Logger logger = LogManager.getLogger(EhsRecordService.class
			.getCanonicalName());
	@Autowired
	private EhsRecordRepository ehsRecordRepository;
	@Autowired
	private EhsItemRepository ehsItemRepository;
	
	@Autowired private SecurityUtils securityUtils;
	
	@Transactional(readOnly=false)
	public WSEHSRecord saveWSEHSRecord(WSEHSRecord wsEHSRecord) throws Exception {
		EhsRecord ehsRecord;
		if(wsEHSRecord.getIdEhsRecord()!=null&&!wsEHSRecord.getIdEhsRecord().equals(0l))
		{
			ehsRecord = ehsRecordRepository.findOne(wsEHSRecord.getIdEhsRecord());
		}
		else
		{
			ehsRecord = new EhsRecord();
	
		}
		EhsRecord dbEhsRecord= toDBEhsRecord(wsEHSRecord,ehsRecord);
		dbEhsRecord = ehsRecordRepository.save(dbEhsRecord);
		return wsEHSRecord;		
		
	}

	@Transactional(readOnly=false)
	public Valid deleteWSEHSRecord(Long idEhsRecord)
	{
		Valid valid = new Valid();
		ehsRecordRepository.delete(idEhsRecord);
		valid.setValid(true);
		
		return valid;
	}

	
	@Transactional(readOnly=true) 
	public WSEHSRecord findWSEHSRecord(Long idEhsRecord) throws Exception
	{	
		EhsRecord ehRecord= ehsRecordRepository.findOne(idEhsRecord);
		return  toWSEHSRecord(ehRecord);
		
	}
	
	protected EhsRecord toDBEhsRecord(WSEHSRecord wsEHSRecord,EhsRecord ehsRecord) throws Exception
	{
	
		EhsRecord dbEhsItem = (EhsRecord)BeanUtil.shallowCopy(wsEHSRecord, EhsRecord.class, ehsRecord);
		dbEhsItem.setIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		if(wsEHSRecord.getIdEhsItem()!=null)
		{
			dbEhsItem.setEhsItem(ehsItemRepository.findOne(wsEHSRecord.getIdEhsItem()));
		}
		return dbEhsItem;
	}
	
	private WSEHSRecord toWSEHSRecord(EhsRecord ehsRecord) throws Exception
	{
		WSEHSRecord pc = (WSEHSRecord)BeanUtil.shallowCopy(ehsRecord, WSEHSRecord.class, null);
		if(ehsRecord.getEhsItem()!=null)
		{
			pc.setIdEhsItem(ehsRecord.getEhsItem().getIdEhs());
			pc.setEhsItem(ehsRecord.getEhsItem().getDes());
		}

		if(ehsRecord.getStatus().equals(0l))
		{
			pc.setSstatus("未处理");
		}else
		{
			pc.setSstatus("已处理");
		}
		return pc;
	}
}
