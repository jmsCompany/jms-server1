package com.jms.controller.production;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jms.domain.db.EhsItem;
import com.jms.domain.db.EhsRecord;
import com.jms.domain.db.PShiftPlanD;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.ehs.WSEHSItem;
import com.jms.domain.ws.ehs.WSEHSRecord;
import com.jms.repositories.p.EhsItemRepository;
import com.jms.repositories.p.EhsRecordRepository;
import com.jms.repositories.p.PShiftPlanDRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.service.production.EhsItemService;
import com.jms.service.production.EhsRecordService;
import com.jms.web.security.SecurityUtils;



@RestController
@Transactional(readOnly=true)
public class EhsRecordController {
	
	@Autowired private EhsRecordService ehsRecordService;
	@Autowired private EhsItemRepository ehsItemRepository;
	@Autowired private EhsRecordRepository ehsRecordRepository;
	@Autowired private UsersRepository usersRepository;
	@Autowired private PShiftPlanDRepository pShiftPlanDRepository;
	@Autowired private SecurityUtils securityUtils;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/e/saveWSEHSRecord", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSEHSRecord saveWSEHSRecord(@RequestBody WSEHSRecord wsEHSRecord) throws Exception {
		return ehsRecordService.saveWSEHSRecord(wsEHSRecord);
	}
	
		
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/e/saveWSEHSRecordList", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid saveWSEHSRecordList(@RequestBody List<WSEHSRecord> wsEHSRecordList) throws Exception {
		
		Valid v = new Valid();
		v.setValid(true);
		
		for(WSEHSRecord wsEHSRecord:wsEHSRecordList)
		{
			ehsRecordService.saveWSEHSRecord(wsEHSRecord);
		}
		return v;
		
	}


	@Transactional(readOnly = true)
	@RequestMapping(value="/e/findWSEHSRecord", method=RequestMethod.GET)
	public WSEHSRecord findWSEHSRecord(@RequestParam("idEhsRecord") Long idEhsRecord) throws Exception{
		return ehsRecordService.findWSEHSRecord(idEhsRecord);
		
	}
	@Transactional(readOnly = false)
	@RequestMapping(value="/e/deleteWSEHSRecord", method=RequestMethod.GET)
	public Valid deleteWSEHSRecord(@RequestParam("idEhsRecord") Long idEhsRecord) {
		return ehsRecordService.deleteWSEHSRecord(idEhsRecord);
		
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/e/findWSEHSRecordList", method=RequestMethod.GET)
	public List<WSEHSRecord> findWSEHSRecordList() throws Exception{
		return ehsRecordService.findWSEHSRecordList();
		
	}
	
//	
//	@Transactional(readOnly = true)
//	@RequestMapping(value="/e/findWSEHSRecordList", method=RequestMethod.GET)
//	public List<WSEHSRecord> findWSEHSRecordList(@RequestParam("idShiftD") Long idShiftD) throws Exception{
//		return ehsRecordService.findWSEHSRecordList(idShiftD);
//		
//	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/e/ehsValid", method=RequestMethod.GET)
	public Valid ehsValid() throws Exception{
		return ehsRecordService.ehsValid();
		
	}
	//date  op  shiftD   creationTime sup  remark   supTime  status   idEhsRecord;
	@Transactional(readOnly = true)
	@RequestMapping(value="/e/getWSEHSRecordList", method=RequestMethod.POST)
	public WSTableData  getWSEHSItemList(
			@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<EhsRecord> ehsRecordList =ehsRecordRepository.findByIdCompany(companyId);
		
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(ehsRecordList.size()<start + length)
			end =ehsRecordList.size();
		else
			end =start + length;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		for (int i = start; i < end; i++) {
			EhsRecord w = ehsRecordList.get(i);
			
			String date = "";
			if(w.getDate()!=null)
			{
				date= formatter.format(w.getDate());
			}
			
			Users u = usersRepository.findOne(w.getIdOp());
			String op="";
			if(u.getName()!=null)
			{
				op = u.getName();
			}
			String item="";
			if(w.getEhsItem()!=null)
			{
				item=w.getEhsItem().getDes();
			}
			String shiftD = "";
			
			if(w.getIdShiftD()!=null)
			{
				PShiftPlanD sp= pShiftPlanDRepository.findOne(w.getIdShiftD());
				if(sp!=null)
				{
					shiftD = sp.getShift();
				}
			}
		
			

			String ctime = "";
			if(w.getCreationTime()!=null)
			{
				ctime= formatter.format(w.getCreationTime());
			}
			String sup="";
			//String sup="";
			if(w.getIdSup()!=null)
			{
				Users usup = usersRepository.findOne(w.getIdSup());
				
				if(usup.getName()!=null)
				{
					sup = usup.getName();
				}
			}
			
			String remark="";
			if(w.getRemark()!=null)
			{
				remark = w.getRemark();
			}
			
			String supTime="";
			if(w.getSupTime()!=null)
			{
				ctime= formatter.format(w.getSupTime());
			}
			
			String status ="";

			if(w.getStatus()==null&&w.getStatus().equals(0l))
			{
				status ="未处理";
			}
			else
			{
				status ="已处理";
			}
			String[] d = {date,  op, item, shiftD, ctime, sup,  remark,   supTime,  status,""+w.getIdEhsRecord()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(ehsRecordList.size());
		t.setRecordsFiltered(ehsRecordList.size());
	    t.setData(lst);
	    return t;
	}
	
}