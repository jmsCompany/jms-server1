package com.jms.service.maintenance;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jms.domain.db.MMainItem;
import com.jms.domain.db.MMainRecord;
import com.jms.domain.db.MSparePart;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.m.WSMSparePart;
import com.jms.domain.ws.m.WSMainItem;
import com.jms.domain.ws.m.WSMainRecord;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.m.MDeptRepository;
import com.jms.repositories.m.MMachineRepository;
import com.jms.repositories.m.MMainCycleRepository;
import com.jms.repositories.m.MMainItemRepository;
import com.jms.repositories.m.MMainRecordRepository;
import com.jms.repositories.m.MResultRepository;
import com.jms.repositories.p.PLineRepository;
import com.jms.repositories.p.PWipRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.repositories.s.SBinRepository;
import com.jms.repositories.s.SInventoryRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class MMainRecordService {

	private static final Logger logger = LogManager.getLogger(MMainRecordService.class);


	@Autowired
	private MMachineRepository mMachineRepository;
	@Autowired
	private MMainItemRepository mMainItemRepository;
	@Autowired
	private MMainRecordRepository mMainRecordRepository;
	@Autowired
	private  MMainCycleRepository mMainCycleRepository;

	@Autowired private SecurityUtils securityUtils;
	@Autowired private MResultRepository  mResultRepository;
	
	@Autowired private  MDeptRepository mDeptRepository;
	
	@Autowired private  UsersRepository usersRepository;
	
	
	
	

	@Transactional(readOnly = false)
	public Valid saveWSMainRecords(List<WSMainRecord> mainRecords) {
		
		for(WSMainRecord w:mainRecords)
		{
			
			
			MMainRecord m;
			if(w.getIdMainRecord()==null||w.getIdMainRecord().equals(-1l)||w.getIdMainRecord().equals(0l)) //new 
			{
				 m = new MMainRecord();
			}
			else
			{
				 m= mMainRecordRepository.findOne(w.getIdMainRecord());
			}
			
			MMainItem item =mMainItemRepository.findOne(w.getIdMainItem());
			m.setMMainItem(item);
			if(w.getIdResult()!=null)
			{
				m.setMResult(mResultRepository.findOne(w.getIdResult()));
			}
			Date d;
			if(w.getTime()!=null)
			{
				d= w.getTime();
			}
			else
			{
				d= new Date();
			}
			m.setTime(d);
			if(w.getIdUser()!=null&&!w.getIdUser().equals(0l))
			{
				m.setUsers(usersRepository.findOne(w.getIdUser()));
			}
			else
			{
				m.setUsers(securityUtils.getCurrentDBUser());
			}
			
			//logger.debug("date: " + d.toString() +", type: " + item.getMMainCycle().getIdMainCycle());
			m.setTimeValue((long)getTimeValue(d,item.getMMainCycle().getIdMainCycle()));
			
			
			
			
			Calendar cl = Calendar.getInstance();   
			cl.setTime(d);   
			
			int year = cl.get(Calendar.YEAR);
			m.setYear((long)year);
			MMainRecord  record = mMainRecordRepository.getByIdMainItemAndTimeValueAndYear(w.getIdMainItem(), m.getTimeValue(), m.getYear());
			Valid v = new Valid();
			
			if(record!=null)
			{
				v.setValid(false);
				return v;
			}
			mMainRecordRepository.save(m);
			
			
		}
		Valid v = new Valid();
		v.setValid(true);
		return v;
		
	}
	
	@Transactional(readOnly = true)
	public WSMainRecord  getMainRecordById(Long idMainRecord)  {	   
		MMainRecord m =  mMainRecordRepository.findOne(idMainRecord);
		MMainItem item = m.getMMainItem();
		WSMainRecord w =  new WSMainRecord();
		w.setDept(item.getMDept().getDes());
		w.setIdDept(item.getMDept().getIdDept());
		w.setIdMachine(item.getMMachine().getIdMachine());
		w.setMachine(item.getMMachine().getCode());
		w.setIdMainCycle(item.getMMainCycle().getIdMainCycle());
		w.setMainCycle(item.getMMainCycle().getMainCycle());
		w.setMainItem(item.getItem());
		w.setIdMainItem(item.getIdMainItem());
		w.setIdMainRecord(m.getIdMainRecord());
		if(m.getUsers()!=null)
		{
			String user = (m.getUsers().getName()==null)?"":m.getUsers().getName();
			w.setUser(user);
			w.setIdUser(m.getUsers().getIdUser());
		}
		
		String result = (m.getMResult()==null)?"":m.getMResult().getDes();
		w.setResult(result);
		w.setTime(m.getTime());
		
		return w;
	}
	
	
	@Transactional(readOnly = true)
	public WSMainRecord  getMainRecordByItemId(Long idMainItem)  {	   
		MMainItem item =  mMainItemRepository.findOne(idMainItem);
		
		WSMainRecord w =  new WSMainRecord();
		w.setDept(item.getMDept().getDes());
		w.setIdDept(item.getMDept().getIdDept());
		w.setIdMachine(item.getMMachine().getIdMachine());
		w.setMachine(item.getMMachine().getCode());
		w.setIdMainCycle(item.getMMainCycle().getIdMainCycle());
		w.setMainCycle(item.getMMainCycle().getMainCycle());
		w.setMainItem(item.getItem());
		w.setIdMainItem(item.getIdMainItem());
		w.setIdMainRecord(-1l);
		w.setUser("");
		w.setResult("");
	
		
		return w;
	}
	
	
	
	@Transactional(readOnly = true)
	public List<WSMainRecord>  mainRecordListForPad( Long idMachine, Long idDept)  {
		List<WSMainRecord> ws = new ArrayList<WSMainRecord>();
		List<MMainItem>  mainItems = mMainItemRepository.getByIdMachineAndIdDept(idMachine, idDept);
        Date d = new Date();
		
		Calendar cl = Calendar.getInstance();   
		cl.setTime(d);   
		int year = cl.get(Calendar.YEAR);
		
		for(MMainItem item:mainItems)
		{
			WSMainRecord w =  new WSMainRecord();
			w.setDept(item.getMDept().getDes());
			w.setIdDept(item.getMDept().getIdDept());
			w.setIdMachine(item.getMMachine().getIdMachine());
			w.setMachine(item.getMMachine().getCode());
			w.setIdMainCycle(item.getMMainCycle().getIdMainCycle());
			w.setMainCycle(item.getMMainCycle().getMainCycle());
			w.setMainItem(item.getItem());
			w.setIdMainRecord(-1l);
			w.setIdMainItem(item.getIdMainItem());
			w.setUser("");
			w.setResult("");
			int timeValue=getTimeValue(d,item.getMMainCycle().getIdMainCycle());
			MMainRecord  record = mMainRecordRepository.getByIdMainItemAndTimeValueAndYear(w.getIdMainItem(), (long)timeValue, (long)year);
			if(record !=null)
			{
				if(record.getUsers()!=null)
				{
					String user = (record.getUsers().getName()==null)?"":record.getUsers().getName();
					w.setUser(user);
					w.setIdUser(record.getUsers().getIdUser());
				}
				
				String result = (record.getMResult()==null)?"":record.getMResult().getDes();
				w.setResult(result);
				Long idMainRecord = record.getIdMainRecord();
				w.setIdMainRecord(idMainRecord);
				Date mainDate = record.getTime();
				w.setTime(mainDate);
			}
		
			
			ws.add(w);
		}
		
		return ws;
	}
	
	
	@Transactional(readOnly = true)
	public WSTableData  mainRecordList(Long date, Long idMachine, Long idDept, Integer draw, Integer start, Integer length)  {	   
		
		
		List<MMainItem>  mainItems = mMainItemRepository.getByIdMachineAndIdDept(idMachine, idDept);
		Date d = new Date(date);
		
		Calendar cl = Calendar.getInstance();   
		cl.setTime(d);   
		int year = cl.get(Calendar.YEAR);

		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(mainItems.size()<start + length)
			end =mainItems.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			MMainItem w = mainItems.get(i);
			String machine = (w.getMMachine().getName()==null)?"":w.getMMachine().getName();
			String machineCode = (w.getMMachine().getCode()==null)?"":w.getMMachine().getCode();
			String cycle = w.getMMainCycle().getMainCycle();
			String item = w.getItem();
			
			long type = w.getMMainCycle().getIdMainCycle(); //1日，2周，3月，4季度，5半年，6年
			
			int timeValue=getTimeValue(d,type);
			
			String user = "";
			String result = "";
			Long idMainRecord = -1l;
			String mainDate="";
			
			//logger.debug("item: " + w.getIdMainItem() + ", timeValue: " + timeValue +", year: " + year);
			MMainRecord  record = mMainRecordRepository.getByIdMainItemAndTimeValueAndYear(w.getIdMainItem(), (long)timeValue, (long)year);
			if(record !=null)
			{
				if(record.getUsers()!=null)
				{
					user = (record.getUsers().getName()==null)?"":record.getUsers().getName();
				}
			
				result = (record.getMResult()==null)?"":record.getMResult().getDes();
				idMainRecord = record.getIdMainRecord();
				mainDate = record.getTime().toString();
			}
			String[] s = {""+w.getIdMainItem(),machine,machineCode,cycle,item,mainDate, user,result,""+idMainRecord};
			lst.add(s);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(mainItems.size());
		t.setRecordsFiltered(mainItems.size());
	    t.setData(lst);
	    return t;
	}
	
	
	public int getTimeValue(Date date,long type)
	{
		
		Calendar cl = Calendar.getInstance();   
		cl.setTime(date);   
		int day = cl.get(Calendar.DAY_OF_YEAR);
		int week = cl.get(Calendar.WEEK_OF_YEAR);
		int month = cl.get(Calendar.MONTH)+1;
		int year = cl.get(Calendar.YEAR);
		int season =0;
		if(month <4)
		{
			season = 1;
		}
		else if(4<=month&& month<=6)
		{
			season =2;
		}
		else if(7<=month&& month<=9)
		{
			season =3;
		}
		else
		{
			season =4;
		}
		int halfYear = 0;
		if(month <=6)
		{
			halfYear= 1;
		}
		else
		{
			halfYear =2;
		}
		
		
		int timeValue=0;
		if(type==1l)
		{
			timeValue = day; 	
		}
		else if(type==2l)
		{
			timeValue= week;
		}
		else if(type==3l)
		{
			timeValue = month;
		}
		else if(type==4l)
		{
			timeValue = season;
		}
		else if(type==5l)
		{
			timeValue = halfYear;
		}
		else if(type==6l)
		{
			timeValue = year;
		}
		logger.debug("type: " + type +", day: " + day +", week: " + week +", month: " + month + ", season: " + season +", halfYear: " + halfYear +", year: " + year +", timeValue: " + timeValue);
		return timeValue;
		
	}
	

}
