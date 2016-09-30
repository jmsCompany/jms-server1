package com.jms.controller.maintenance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.MMachine;
import com.jms.domain.db.MMachineGroup;
import com.jms.domain.db.MMainCycle;
import com.jms.domain.db.MMainItem;
import com.jms.domain.db.MMainRecord;
import com.jms.domain.db.MSparePart;
import com.jms.domain.db.PCPp;
import com.jms.domain.db.PLine;
import com.jms.domain.db.PUnplannedStops;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.m.WSBreakdownRate;
import com.jms.domain.ws.m.WSMSparePart;
import com.jms.domain.ws.m.WSMachineGroup;
import com.jms.domain.ws.m.WSMainCycle;
import com.jms.domain.ws.m.WSMainItem;
import com.jms.domain.ws.m.WSMainItemResult;
import com.jms.domain.ws.m.WSMainRecord;
import com.jms.domain.ws.m.WSMtbf;
import com.jms.domain.ws.m.WSThreeDates;
import com.jms.domain.ws.p.WSPActualSetup;
import com.jms.domain.ws.p.WSPCheckPlan;
import com.jms.domain.ws.p.WSPLine;
import com.jms.repositories.m.MMachineGroupRepository;
import com.jms.repositories.m.MMachineRepository;
import com.jms.repositories.m.MMainCycleRepository;
import com.jms.repositories.m.MMainItemRepository;
import com.jms.repositories.m.MMainRecordRepository;
import com.jms.repositories.m.MSparePartRepository;
import com.jms.repositories.p.PCPpRepository;
import com.jms.repositories.p.PUnplannedStopsRepository;
import com.jms.service.maintenance.MMachineGroupService;
import com.jms.service.maintenance.MMachineService;
import com.jms.service.maintenance.MMainRecordService;
import com.jms.service.maintenance.MSparePartService;
import com.jms.web.security.SecurityUtils;

@RestController
@Transactional(readOnly = true)
public class MMainReportController {

	@Autowired
	private MMainRecordService mMainRecordService;
	@Autowired
	private MSparePartRepository mSparePartRepository;
	@Autowired
	private MMainCycleRepository mMainCycleRepository;
	@Autowired
	private MMachineGroupService mMachineGroupService;
	@Autowired
	private MMachineGroupRepository mMachineGroupRepository;
	@Autowired
	private MMachineRepository mMachineRepository;
	@Autowired
	private PCPpRepository pCPpRepository;
	@Autowired
	private PUnplannedStopsRepository pUnplannedStopsRepository;
	@Autowired
	private MMainItemRepository mMainItemRepository;
	@Autowired
	private MMainRecordRepository mMainRecordRepository;
	@Autowired
	private SecurityUtils securityUtils;
	private static final Logger logger = LogManager.getLogger(MMainReportController.class.getCanonicalName());

	@Transactional(readOnly = true)
	@RequestMapping(value = "/m/getMantence", method = RequestMethod.GET)
	public List<WSMainCycle> getMantence(@RequestParam int year, @RequestParam Long idMachine) throws ParseException {
		List<WSMainCycle> ws = new ArrayList<WSMainCycle>();

		for (MMainCycle m : mMainCycleRepository.getPreventMainCycles()) {
			WSMainCycle w = new WSMainCycle();
			w.setIdMainCycle(m.getIdMainCycle());
			w.setMainCycle(m.getMainCycle());
			List<WSMainItem> items = new ArrayList<WSMainItem>();
			for (MMainItem item : mMainItemRepository.getByIdMachineAndIdMainCycle(idMachine, m.getIdMainCycle())) {
				WSMainItem i = new WSMainItem();
				i.setIdMachine(idMachine);
				i.setIdMainItem(item.getIdMainItem());
				i.setItem(item.getItem());
				List<WSMainItemResult> itemValues = new ArrayList<WSMainItemResult>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
				Calendar cal = Calendar.getInstance();

				Date beginDate = sdf.parse("" + year + "-01-01");
				cal.setTime(beginDate);
				int counts = 0;
				if (m.getIdMainCycle().equals(2l)) // 周计划
				{
					counts = 52;
				} else if (m.getIdMainCycle().equals(3l)) // 月
				{
					counts = 12;
				} else if (m.getIdMainCycle().equals(4l)) // 季度
				{
					counts = 4;
				} else if (m.getIdMainCycle().equals(5l)) // 半年
				{
					counts = 2;
				} else if (m.getIdMainCycle().equals(6l)) // 年
				{
					counts = 1;
				}

				int timeValue = mMainRecordService.getTimeValue(beginDate, m.getIdMainCycle()); // timevalue
																								// for
																								// begin
																								// day

				for (int k = 0; k < counts; k++) {
					logger.debug("idItem: " + item.getIdMainItem() + ", timeValue: " + timeValue + ", year: " + year);
					MMainRecord record = mMainRecordRepository.getByIdMainItemAndTimeValueAndYear(item.getIdMainItem(),
							(long) timeValue, (long) year);
					WSMainItemResult ir = new WSMainItemResult();
					if (record != null) {
						ir.setMain(true);
					} else {
						ir.setMain(false);

					}
					itemValues.add(ir);
					timeValue++;
				}
				i.setItemValues(itemValues);
				items.add(i);

			}
			w.setItems(items);
			ws.add(w);
		}
		return ws;
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/m/getMantenceDaily", method = RequestMethod.GET)
	public List<WSMainCycle> getMantenceDaily(@RequestParam int year, @RequestParam Long month,
			@RequestParam Long idMachine) throws ParseException {
		List<WSMainCycle> ws = new ArrayList<WSMainCycle>();

		for (MMainCycle m : mMainCycleRepository.getDailyMainCycles()) {
			WSMainCycle w = new WSMainCycle();
			w.setIdMainCycle(m.getIdMainCycle());
			w.setMainCycle(m.getMainCycle());
			List<WSMainItem> items = new ArrayList<WSMainItem>();
			for (MMainItem item : mMainItemRepository.getByIdMachineAndIdMainCycle(idMachine, m.getIdMainCycle())) {
				WSMainItem i = new WSMainItem();
				i.setIdMachine(idMachine);
				i.setIdMainItem(item.getIdMainItem());
				i.setItem(item.getItem());
				List<WSMainItemResult> itemValues = new ArrayList<WSMainItemResult>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
				Calendar cal = Calendar.getInstance();
				String sMonth = "";
				if (month < 10) {
					sMonth = "0" + month;
				} else {
					sMonth = "" + month;
				}
				Date beginDate = sdf.parse("" + year + "-" + sMonth + "-01");
				cal.setTime(beginDate);
				if (m.getIdMainCycle().equals(1l)) // 日计划
				{

					int timeValue = mMainRecordService.getTimeValue(beginDate, m.getIdMainCycle()); // timevalue
																									// for
																									// begin
																									// day
					int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
					for (int day = 0; day < days; day++) {
						MMainRecord record = mMainRecordRepository.getByIdMainItemAndTimeValueAndYear(
								item.getIdMainItem(), (long) timeValue, (long) year);
						WSMainItemResult ir = new WSMainItemResult();
						if (record != null) {
							ir.setMain(true);
						} else {
							ir.setMain(false);

						}
						itemValues.add(ir);
						timeValue++;
					}
					i.setItemValues(itemValues);

				} else { // 周计划
					int timeValue = mMainRecordService.getTimeValue(beginDate, m.getIdMainCycle()); // timevalue
																									// for
																									// begin
																									// day
					for (int week = 0; week < 4; week++) {
						MMainRecord record = mMainRecordRepository.getByIdMainItemAndTimeValueAndYear(
								item.getIdMainItem(), (long) timeValue, (long) year);
						WSMainItemResult ir = new WSMainItemResult();
						if (record != null) {
							ir.setMain(true);
						} else {
							ir.setMain(false);

						}
						itemValues.add(ir);
						timeValue++;
					}
					i.setItemValues(itemValues);
				}

				items.add(i);
			}
			w.setItems(items);
			ws.add(w);
		}
		return ws;
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/m/getWSMtbf", method = RequestMethod.GET)
	public List<WSMtbf> getWSMtbf(@RequestParam int year, @RequestParam Long idMachine) throws ParseException {

		MMachine machine = mMachineRepository.findOne(idMachine);
		Calendar cl = Calendar.getInstance();
		cl.setTime(new Date());
		int c_year = cl.get(Calendar.YEAR);
		int month = 12;
		if (year == c_year) {

			month = cl.get(Calendar.MONTH) + 1;
		}

		List<WSMtbf> ws = new ArrayList<WSMtbf>();
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();

		for (int x = 1; x < month; x++) {
			WSMtbf w = new WSMtbf();
			w.setX(x);
			w.setMttrTarget(0);
			if (machine.getMttr() != null) {
				w.setMttrTarget(machine.getMttr().floatValue());
			}

			w.setMtbfTarget(0);
			if (machine.getMtbf() != null) {
				w.setMtbfTarget(machine.getMtbf().floatValue());
			}

			WSThreeDates dates = calculateBeginAndEndDates(year, month);
			List<PCPp> cpps = pCPpRepository.getActualByFromDateToDateAndMachineId(companyId, dates.getMonday(),
					dates.getSunday(), idMachine);
			Long actTime = 0l;
			for (PCPp p : cpps) {
				actTime = actTime + (p.getActFt().getTime() - p.getActSt().getTime());
			}

			long breakdownTime = 0;
			List<PUnplannedStops> ss = pUnplannedStopsRepository.getByMachineIdAndDuration(idMachine, dates.getMonday(),
					dates.getSunday());
			int i = 0;
			for (PUnplannedStops s : ss) {
				i++;
				breakdownTime = breakdownTime + (s.getOpFt().getTime() - s.getOpSt().getTime());
			}

			float mtbf=0f;
			float mttr = 0f;
			if(i==0)
			{
				mtbf = (float) (actTime - breakdownTime);
			    mttr = 0f;
			}
			else
			{	 
				mtbf = (float) (actTime - breakdownTime) / (float) i;
			    mttr = (float) (breakdownTime) / (float) i;
				
			}
		

			w.setMtbfActual(mtbf);
			w.setMttrActual(mttr);
			ws.add(w);
		}

		return ws;
	}

	// public static void main(String[] args)
	// {
	// try {
	// convertWeekByDate(2016,1);
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	// }
	//
	private static WSThreeDates calculateBeginAndEndDates(Date beginDate, int week) throws ParseException {

		WSThreeDates w = new WSThreeDates();
		// w.setCurrentDate(beginDate);
		// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
		// Calendar cal = Calendar.getInstance();
		// Date beginDate = sdf.parse(""+year +"-01-01");
		Calendar cal = Calendar.getInstance();
		cal.setTime(beginDate);

		System.out.println(cal.getTime().toString());
		// //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		// int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
		// if(1 == dayWeek) {
		// cal.add(Calendar.DAY_OF_MONTH, -1);
		// }
		// System.out.println("要计算日期为:"+sdf.format(cal.getTime())); //输出要计算日期
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		// String imptimeBegin = sdf.format(cal.getTime());
		w.setMonday(cal.getTime());
		// System.out.println("所在周星期一的日期："+imptimeBegin);
		cal.add(Calendar.DATE, 6);
		w.setSunday(cal.getTime());
		// String imptimeEnd = sdf.format(cal.getTime());
		// System.out.println("所在周星期日的日期："+imptimeEnd);
		cal.add(Calendar.DATE, 1);
		w.setCurrentDate(cal.getTime());

		return w;

	}

	private static WSThreeDates calculateBeginAndEndDates(int year, int month) throws ParseException {

		WSThreeDates w = new WSThreeDates();
		// w.setCurrentDate(beginDate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		Calendar cal = Calendar.getInstance();
		String sMonth = "";
		if (month < 10) {
			sMonth = "0" + month;
		} else {
			sMonth = "" + month;
		}
		Date beginDate = sdf.parse("" + year + "-" + sMonth + "-01");

		w.setMonday(beginDate);
		cal.setTime(beginDate);

		month++;
		String sMonth1 = "";
		if (month < 10) {
			sMonth1 = "0" + month;
		} else {
			sMonth1 = "" + month;
		}
		Date endDate = sdf.parse("" + year + "-" + sMonth1 + "-01");

		w.setSunday(endDate);

		return w;

	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/m/getWSBreakdownRate", method = RequestMethod.GET)
	public List<WSBreakdownRate> getWSBreakdownRate(@RequestParam Long year, @RequestParam Long idMachine)
			throws ParseException {

		MMachine machine = mMachineRepository.findOne(idMachine);

		Calendar cl = Calendar.getInstance();
		cl.setTime(new Date());
		int c_year = cl.get(Calendar.YEAR);
		int week = 52;
		if (year == c_year) {
			week = cl.get(Calendar.WEEK_OF_YEAR);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		Calendar cal = Calendar.getInstance();
		Date beginDate = sdf.parse("" + year + "-01-01");
		cal.setTime(beginDate);
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}

		List<WSBreakdownRate> ws = new ArrayList<WSBreakdownRate>();
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();

		for (int x = 1; x < week; x++) {
			WSThreeDates dates = calculateBeginAndEndDates(beginDate, x);
			beginDate = dates.getCurrentDate();
			List<PCPp> cpps = pCPpRepository.getActualByFromDateToDateAndMachineId(companyId, dates.getMonday(),
					dates.getSunday(), idMachine);
			Long actTime = 0l;
			for (PCPp p : cpps) {
				actTime = actTime + (p.getActFt().getTime() - p.getActSt().getTime());
			}
			long breakdownTime = 0;
			List<PUnplannedStops> ss = pUnplannedStopsRepository.getByMachineIdAndDuration(idMachine, dates.getMonday(),
					dates.getSunday());

			for (PUnplannedStops s : ss) {
				breakdownTime = breakdownTime + (s.getOpFt().getTime() - s.getOpSt().getTime());
			}

			float rate = (actTime.equals(0l))?0l:(float) breakdownTime / actTime;

			WSBreakdownRate w = new WSBreakdownRate();
			w.setX(x);
			if (machine.getDowntime() != null) {
				w.setTarget(machine.getDowntime().floatValue());
			} else {
				w.setTarget(0);
			}

			w.setY(rate);
			ws.add(w);
		}

		return ws;
	}

}