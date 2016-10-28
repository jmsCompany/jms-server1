package com.jms.service.production;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.EhsItem;
import com.jms.domain.db.EhsRecord;
import com.jms.domain.db.PShiftPlanD;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.ehs.WSEHSItem;
import com.jms.domain.ws.ehs.WSEHSRecord;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.p.EhsItemRepository;
import com.jms.repositories.p.EhsRecordRepository;
import com.jms.repositories.p.PShiftPlanDRepository;
import com.jms.repositories.user.UsersRepository;
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
	@Autowired private UsersRepository usersRepository;
	
	@Autowired private PShiftPlanDRepository pShiftPlanDRepository;
	
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
	@Transactional(readOnly=true) 
	public List<WSEHSRecord> findWSEHSRecordList(Long idShiftD) throws Exception
	{	
		//EhsRecord ehRecord= ehsRecordRepository.findOne(idEhsRecord);
		//return  toWSEHSRecord(ehRecord);
		 List<WSEHSRecord> records = new ArrayList<WSEHSRecord>();
		 List<EhsItem> items =  ehsItemRepository.findByIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		 for(EhsItem item : items){
			 WSEHSRecord record;
			 EhsRecord ehsRecord  =ehsRecordRepository.findByUserAndShiftDAndDate(securityUtils.getCurrentDBUser().getIdUser(), idShiftD,item.getIdEhs());
			 if(ehsRecord==null)
			 {
				 record = new WSEHSRecord();
				 record.setEhsItem(item.getDes());
				 record.setIdCompany(item.getIdCompany());
				 record.setIdEhsItem(item.getIdEhs());
				 record.setIdOp(securityUtils.getCurrentDBUser().getIdUser());
				 record.setStatus(0l);
				 record.setSstatus("未处理");
				// record.setShiftD(shiftD);
				 record.setIdShiftD(idShiftD);
			 }
			 else
			 {
				 record =toWSEHSRecord(ehsRecord);
			 }
			 records.add(record);
		 }
		 
		 return records;
		
	}
	
	
	@Transactional(readOnly=true) 
	public List<WSEHSRecord> findWSEHSRecordList() throws Exception
	{	
		 List<WSEHSRecord> records = new ArrayList<WSEHSRecord>();
		 List<EhsItem> items =  ehsItemRepository.findByIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		 for(EhsItem item : items){
			 WSEHSRecord record;
			 EhsRecord ehsRecord  =ehsRecordRepository.findByUserAndShiftDAndDate(securityUtils.getCurrentDBUser().getIdUser(), item.getIdEhs());
			 if(ehsRecord==null)
			 {
				 record = new WSEHSRecord();
				 record.setEhsItem(item.getDes());
				 record.setIdCompany(item.getIdCompany());
				 record.setIdEhsItem(item.getIdEhs());
				 record.setIdOp(securityUtils.getCurrentDBUser().getIdUser());
				 record.setStatus(1l);
				 record.setSstatus("已处理");
				// record.setShiftD(shiftD);
				// record.setIdShiftD(idShiftD);
			 }
			 else
			 {
				 record =toWSEHSRecord(ehsRecord);
			 }
			 records.add(record);
		 }
		 
		 return records;
		
	}
	
	@Transactional(readOnly=true) 
	public Valid ehsValid(Long idShiftD) throws Exception
	{	
		 Valid v = new Valid();
		
		 List<EhsItem> items =  ehsItemRepository.findByIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		 for(EhsItem item : items){
			 WSEHSRecord record;
			 EhsRecord ehsRecord  =ehsRecordRepository.findByUserAndShiftDAndDate(securityUtils.getCurrentDBUser().getIdUser(), idShiftD,item.getIdEhs());
			 if(ehsRecord==null)
			 {
			     v.setValid(false);
				return v;
			 }
			 else
			 {
				 if(ehsRecord.getStatus().equals(0l))
				 {
					 v.setValid(false);
					 return v;
				 }
			 }
			
		 }
		 v.setValid(true);
		 return v;
		
	}
	
	
	@Transactional(readOnly=true) 
	public Valid ehsValid() throws Exception
	{	
		 Valid v = new Valid();
		
		 List<EhsItem> items =  ehsItemRepository.findByIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		 for(EhsItem item : items){
			 WSEHSRecord record;
			 EhsRecord ehsRecord  =ehsRecordRepository.findByUserAndShiftDAndDate(securityUtils.getCurrentDBUser().getIdUser(),item.getIdEhs());
			 if(ehsRecord==null)
			 {
			     v.setValid(false);
				return v;
			 }
			 else
			 {
				 if(ehsRecord.getStatus().equals(0l))
				 {
					 v.setValid(false);
					 return v;
				 }
			 }
			
		 }
		 v.setValid(true);
		 return v;
		
	}
	
	
	protected EhsRecord toDBEhsRecord(WSEHSRecord wsEHSRecord,EhsRecord ehsRecord) throws Exception
	{
		wsEHSRecord.setEhsItem(null);
		EhsRecord dbEhsItem = (EhsRecord)BeanUtil.shallowCopy(wsEHSRecord, EhsRecord.class, ehsRecord);
		dbEhsItem.setIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		if(wsEHSRecord.getIdEhsItem()!=null)
		{
			System.out.println("idEhsItem:: " +  wsEHSRecord.getIdEhsItem());
			EhsItem item = ehsItemRepository.findOne(wsEHSRecord.getIdEhsItem());
			System.out.println("item:: " +  item.getType());
			dbEhsItem.setEhsItem(item);
		}
		dbEhsItem.setCreationTime(new Date());
		dbEhsItem.setDate(new Date());
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
		
		if(ehsRecord.getIdOp()!=null){
			Users u = usersRepository.findOne(ehsRecord.getIdOp());
			String op="";
			if(u.getName()!=null)
			{
				op = u.getName();
			}
			pc.setOp(op);
			
		}
		
		
		if(ehsRecord.getIdSup()!=null){
			Users u = usersRepository.findOne(ehsRecord.getIdSup());
			String sup="";
			if(u.getName()!=null)
			{
				sup = u.getName();
			}
			pc.setSup(sup);
			
		}
		
		
		if(ehsRecord.getIdShiftD()!=null)
		{
			PShiftPlanD pShiftPlanD = pShiftPlanDRepository.findOne(ehsRecord.getIdShiftD());
			pc.setShiftD(pShiftPlanD.getShift());
		}
		return pc;
	}
}
