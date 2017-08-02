package com.jms.controller.store;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.csvreader.CsvReader;
import com.jms.domain.db.BuyMehod;
import com.jms.domain.db.SAttachment;
import com.jms.domain.db.SComMatPrice;
import com.jms.domain.db.SCompanyCo;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SNomaterialReport;
import com.jms.domain.db.SNomaterialReportSum;
import com.jms.domain.db.SPo;
import com.jms.domain.db.SPoMaterial;
import com.jms.domain.db.SPoTemp;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSCompanyPrice;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.s.WSCompanyCo;
import com.jms.domain.ws.s.WSMatReport;
import com.jms.domain.ws.s.WSMrpNo;
import com.jms.domain.ws.s.WSMrpPo;
import com.jms.domain.ws.s.WSSpo;
import com.jms.domain.ws.s.WSSpoMaterial;
import com.jms.domain.ws.s.WSSpoRemark;
import com.jms.file.FileMeta;
import com.jms.file.FileUploadService;
import com.jms.repositories.s.BuyMehodRepository;
import com.jms.repositories.s.SAttachmentRepository;
import com.jms.repositories.s.SComMatPriceRepository;
import com.jms.repositories.s.SCompanyCoRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SNomaterialReportRepository;
import com.jms.repositories.s.SNomaterialReportSumRepository;
import com.jms.repositories.s.SNomaterialReportSumRepositoryCustom;
import com.jms.repositories.s.SPoTempRepository;
import com.jms.repositories.s.SSpoMaterialRepositoryCustom;
import com.jms.repositories.s.SSpoRepository;
import com.jms.service.store.MrpService;
import com.jms.service.store.SpoMaterialService;
import com.jms.service.store.SpoService;
import com.jms.web.security.SecurityUtils;

@RestController
@Transactional(readOnly = true)
public class MrpController {

	@Autowired
	private SecurityUtils securityUtils;
	@Autowired
	private SMaterialRepository sMaterialRepository;
	@Autowired
	private SSpoRepository sSpoRepository;
	@Autowired
	private SpoMaterialService spoMaterialService;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private SAttachmentRepository sAttachmentRepository;
	@Autowired
	private SComMatPriceRepository sComMatPriceRepository;
	@Autowired
	private BuyMehodRepository buyMehodRepository;

	@Autowired
	private SCompanyCoRepository sCompanyCoRepository;
	@Autowired
	private SNomaterialReportRepository sNomaterialReporRepository;
	@Autowired
	private SNomaterialReportSumRepository sNomaterialReportSumRepository;
	@Autowired
	private SPoTempRepository sPoTempRepository;
	@Autowired
	private MrpService mrpService;
	@Autowired
	private SpoService spoService;
	
	@Autowired
	private SNomaterialReportSumRepositoryCustom sNomaterialReportSumRepositoryCustom;

	@Value("${filePath}")
	private String filePath;
	private static final Logger logger = LogManager.getLogger(MrpController.class.getCanonicalName());

	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/mrp/sourcingPriceList")
	public WSTableData sourcingPriceList(@RequestParam(required = false, value = "materialId") Long materialId,
			@RequestParam(required = false, value = "q") String q, @RequestParam Integer draw,
			@RequestParam Integer start, @RequestParam Integer length) throws Exception {

		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();

		Long idCom = null;
		if (q != null && !q.isEmpty()) {
			SCompanyCo sCompanyCo = sCompanyCoRepository.findByCompanyIdAndNameAndType(companyId, q, 1l);
			if (sCompanyCo != null) {
				idCom = sCompanyCo.getId();
			}

		}
		List<SComMatPrice> sComMatPriceList = null;
		if (materialId != null) {

			if (idCom != null) {
				sComMatPriceList = sComMatPriceRepository.findByIdCompanyAndIdComAndIdMat(companyId, idCom, materialId);
			} else {
				sComMatPriceList = sComMatPriceRepository.findByIdMat(materialId);
			}
		} else {
			if (idCom != null) {
				sComMatPriceList = sComMatPriceRepository.findByIdCompanyAndIdCom(companyId, idCom);
			} else {
				sComMatPriceList = sComMatPriceRepository.findByIdCompany(companyId);
			}

		}

		// logger.debug("idCompany: " + companyId +", matId: " + materialId +",
		// idCom: " + idCom );

		List<String[]> lst = new ArrayList<String[]>();
		int end = 0;
		if (sComMatPriceList.size() < start + length)
			end = sComMatPriceList.size();
		else
			end = start + length;
		// 6、8、9、11
		for (int i = start; i < end; i++) {

			SComMatPrice sComMatPrice = sComMatPriceList.get(i);
			Long matId = sComMatPrice.getIdMat();
			SMaterial m = sMaterialRepository.findOne(matId);
			String des = (m.getDes() == null) ? "" : m.getDes();
			String ver = (m.getRev() == null) ? "" : m.getRev();
			String isP = (sComMatPrice.getIsPrim().equals(0l)) ? "否" : "是";
			SCompanyCo sCompanyCo = sCompanyCoRepository.findOne(sComMatPrice.getIdCom());
			String punit = "";
			if (m.getSUnitDicByUnitPur() != null) {
				punit = m.getSUnitDicByUnitPur().getName();
			} else {
				punit = m.getSUnitDicByUnitInf().getName();
			}
			// String[] d = {"物料编码", "物料描述","版本","供应商代码",
			// "供应商名称","采购百分比","采购单位","采购价格","是否是主供应商","货运期（天）","运费单件",""+2907
			// };
			String[] d = { m.getPno(), des, ver, sCompanyCo.getCode(), sCompanyCo.getName(),
					"" + sComMatPrice.getPrec(), punit, "" + sComMatPrice.getPrice(), isP, "" + sComMatPrice.getDura(),
					"" + sComMatPrice.getUnitPrice(), "" + sComMatPrice.getId(), "" + sComMatPrice.getIdMat() };
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(sComMatPriceList.size());
		t.setRecordsFiltered(sComMatPriceList.size());
		t.setData(lst);
		return t;
	}

	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/mrp/deletesourcingPrice")
	public Valid deletesourcingPrice(@RequestParam Long id) {
		// System.out.println(" delete 采购价格： " + id);
		Valid v = new Valid();
		// SComMatPrice sComMatPrice = sComMatPriceRepository.findOne(id);
		// System.out.println(" idcom: " + sComMatPrice.getIdCom());
		sComMatPriceRepository.delete(id);
		v.setValid(true);
		return v;
	}

	// //todo
	// @Transactional(readOnly = false)
	// @RequestMapping(value = "/s/mrp/getsourcingPrice")
	// public Valid getsourcingPrice(@RequestParam Long id)
	// {
	// System.out.println(" delete 采购价格： " + id);
	// Valid v = new Valid();
	//
	// v.setValid(true);
	// return v;
	// }

	// todo
	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/mrp/savecocompanyPriceList")
	public Valid savecocompanyPriceList(@RequestBody List<WSCompanyPrice> ws) {

		if (ws.isEmpty()) {
			Valid v = new Valid();
			v.setValid(true);
			return v;
		}
		Long idMat = ws.get(0).getMaterialId();
		List<SComMatPrice> dbs = sComMatPriceRepository.findByIdMat(idMat);

		for (SComMatPrice db : dbs) {
			sComMatPriceRepository.delete(db);
		}

		for (WSCompanyPrice w : ws) {
			SComMatPrice sComMatPrice = new SComMatPrice();
			sComMatPrice.setIdCom(w.getIdCocompany());
			sComMatPrice.setIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
			sComMatPrice.setIdMat(w.getMaterialId());
			sComMatPrice.setDura(w.getDays());
			sComMatPrice.setIsPrim(w.getIsPrim());
			sComMatPrice.setPrec(w.getPerc());
			sComMatPrice.setPrice(w.getPrice());
			sComMatPrice.setUnitPrice(w.getCarriage());
			sComMatPriceRepository.save(sComMatPrice);
		}

		Valid v = new Valid();
		v.setValid(true);
		return v;

	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/mrp/cocompanyPriceList")
	public List<WSCompanyPrice> cocompanyPriceList(
			@RequestParam(required = false, value = "materialId") Long materialId) throws Exception {

		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();

		List<SComMatPrice> dbs = sComMatPriceRepository.findByIdMat(materialId);
		List<WSCompanyPrice> ws = new ArrayList<WSCompanyPrice>();
		for (SComMatPrice db : dbs) {

			WSCompanyPrice w = new WSCompanyPrice();
			w.setCarriage(db.getUnitPrice());
			w.setDays(db.getDura());
			w.setId(db.getId());
			w.setIdCocompany(db.getIdCom());
			w.setIsPrim(db.getIsPrim());
			w.setMaterialId(db.getIdMat());
			w.setPerc(db.getPrec());
			w.setPrice(db.getPrice());

			ws.add(w);
		}

		return ws;
	}

	// 明细
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/mrp/noMatDetailReportList")
	public WSTableData noMatDetailReportList(@RequestParam(required = false, value = "idMat") Long idMat,
			@RequestParam(required = false, value = "type") Long type,
			@RequestParam(required = false, value = "q") String q, @RequestParam Integer draw,
			@RequestParam Integer start, @RequestParam Integer length) throws Exception {

		Long idCompany = securityUtils.getCurrentDBUser().getCompany().getIdCompany();

		//System.out.println("idMat: " + idMat + ", type: " + type + ", q: " + q);
		List<SNomaterialReport> lsss = null;
		if (idMat != null) {
			if (type != null) {
				lsss = sNomaterialReporRepository.findByIdCompanyAndIdMatAndType(idCompany, idMat, type);
			} else {
				lsss = sNomaterialReporRepository.findByIdCompanyAndIdMat(idCompany, idMat);
			}
		} else {
			if (type != null) {
				lsss = sNomaterialReporRepository.findByIdCompanyAndType(idCompany, type);
			} else {
				lsss = sNomaterialReporRepository.findByIdCompany(idCompany);
			}

		}

		// sNomaterialReporRepository.findByIdCompany(idCompany)

		//List<SNomaterialReport> lsz = 
		//		new ArrayList<SNomaterialReport>();
		//boolean woFilter=false;
		if(q!=null&&!q.isEmpty())
		{
			lsss=lsss.stream().filter(w->w.getPWNo().equals(q)).
			 collect(Collectors.toList());
		}
		
		List<String[]> lst = new ArrayList<String[]>();
		int end = 0;
		if (lsss.size() < start + length)
			end = lsss.size();
		else
			end = start + length;
		for (int i = start; i < end; i++) {
			SNomaterialReport s = lsss.get(i);
			String invUnit = (s.getInvUnit() == null) ? "" : s.getInvUnit();
			String stype = getType(s.getType());
			String qty = (s.getQty() == null) ? "" : "" + s.getQty();
			String pDate = formatDate(s.getProDate());
			String woFinishDate = formatDate(s.getWoFinishDate());

			String sQty = (s.getSQty() == null) ? "" : "" + s.getSQty();
			String status = (s.getStatus() == null) ? "" : "" + s.getStatus();
			String pwCode = (s.getPWNo() == null) ? "" : s.getPWNo();
			String woQty = (s.getWoQty() == null) ? "" : "" + s.getWoQty();
			String openWo = (s.getOpenWoQty() == null) ? "" : "" + s.getOpenWoQty();
			String pro = (s.getProduct() == null) ? "" : s.getProduct();
			String ppCode = (s.getPlanNo() == null) ? "" : s.getPlanNo();
			String planQty = (s.getPpQty() == null) ? "" : "" + s.getPpQty();
			String openPPQty = (s.getOpenPpQty() == null) ? "" : "" + s.getOpenPpQty();
			String[] d = { "" + s.getId(), s.getMaterial(), invUnit, stype, qty, pDate, woFinishDate, sQty, status,
					pwCode, woQty, openWo, pro, ppCode, planQty, openPPQty };
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(lsss.size());
		t.setRecordsFiltered(lsss.size());
		t.setData(lst);
		return t;
	}

	// type 2保存确认 1 删除确认 3.临时PO
	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/mrp/handleNoMatReportSum")
	public Valid handleNoMatReportSum(@RequestBody WSMrpNo ws) {
		Long idCompany = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		Valid v = new Valid();
		//System.out.println("type: " + ws.getType() +", poType: " + ws.getPoType());
		
		List<Long> aqtys = ws.getAqtys();
		if (ws.getType().equals(1l) || ws.getType().equals(2l)) {
			int i =0;
			for (Long id : ws.getIds()) {
				// System.out.println("id: " + id +", type: " + ws.getType());
				SNomaterialReportSum sNomaterialReportSum = sNomaterialReportSumRepository.findOne(id);
				sNomaterialReportSum.setStatus(ws.getType());
				sNomaterialReportSum.setPQty(aqtys.get(i));
				sNomaterialReportSumRepository.save(sNomaterialReportSum);
				i++;
			
			}
			v.setValid(true);
			return v;

		} else {

			Boolean check = true;
			int i =0;
			for (Long id : ws.getIds()) {
				// System.out.println("id: " + id +", type: " + ws.getType());
				SNomaterialReportSum sNomaterialReportSum = sNomaterialReportSumRepository.findOne(id);
		
				SComMatPrice sComMatPrice = sComMatPriceRepository.findByIdMatAndIsPrim(sNomaterialReportSum.getIdMat(),
						1l);
				if (sComMatPrice == null) {
					v.setValid(false);
					check = false;
					v.setMsg("无此物料供应商，物料: " + sNomaterialReportSum.getMaterial());
					return v;
				}

			}

			if (check) {


				for (Long id : ws.getIds()) {
				
					//System.out.println("id: " + id);
				if(ws.getPoType().equals(0l))  //主供应商
				{
					//System.out.println("xhuid: " + id);
					SNomaterialReportSum sNomaterialReportSum = sNomaterialReportSumRepository.findOne(id);

					sNomaterialReportSum.setStatus(3l);
					sNomaterialReportSum.setPQty(aqtys.get(i));
					//System.out.println("i : " + i + ", v: "  +aqtys.get(i));
				//	sNomaterialReportSumRepository.save(sNomaterialReportSum);
					i++;
					sNomaterialReportSumRepository.save(sNomaterialReportSum);
					SComMatPrice sComMatPrice = sComMatPriceRepository
							.findByIdMatAndIsPrim(sNomaterialReportSum.getIdMat(), 1l);
					SPoTemp spoTemp = new SPoTemp();
					spoTemp.setIdMat(sNomaterialReportSum.getIdMat());
					spoTemp.setMaterial(sNomaterialReportSum.getMaterial());
					spoTemp.setRDate(sNomaterialReportSum.getRDate());
					spoTemp.setSQty(-sNomaterialReportSum.getSQty());
					spoTemp.setInvUnit(sNomaterialReportSum.getInvUnit());
					
					
				//	Float yc = -sNomaterialReportSum.getSQty();
					Long ycl =sNomaterialReportSum.getPQty();
					
					
					spoTemp.setSPoQty(ycl);  //应采购
					
					SMaterial mt = sMaterialRepository.findOne(sNomaterialReportSum.getIdMat());
					
					Long moq = mt.getMoq()==null?0l:mt.getMoq();
					Long mpq = mt.getMpq()==null?0l:mt.getMpq();
					spoTemp.setMoq(moq);
					spoTemp.setMpq(mpq);
					Long j =ycl; //实际采购
					if(j<moq)
					{
						 j= moq;
					}
					if(mpq>0)
					{
						if(j%mpq!=0)
						{
							j=(j/mpq+1)*mpq;
						}
					}
				//	System.out.println("实际采购：" +j);
					spoTemp.setAPoQty(j);

					spoTemp.setUPrice(sComMatPrice.getPrice()); // 单价
					spoTemp.setAUPrice(sComMatPrice.getPrice()); // 本次单价
					spoTemp.setPrice(j * sComMatPrice.getPrice());
					spoTemp.setYsDiff(ycl-j);
					spoTemp.setDiff((ycl-j)*sComMatPrice.getPrice());
					spoTemp.setDura(sComMatPrice.getDura()); // 货运期

					Date d = sNomaterialReportSum.getRDate(); // 需求时间
					Long days = sComMatPrice.getDura();
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(d);
					calendar.add(calendar.DATE, -days.intValue());// 把日期往后增加一天.整数往后推,负数往前移动
					spoTemp.setLateDate(calendar.getTime()); // 最晚下单

					Date d1 = new Date(); // 需求时间
					Calendar calendar1 = new GregorianCalendar();
					calendar1.setTime(d1);
					calendar1.add(calendar1.DATE, days.intValue());// 把日期往后增加一天.整数往后推,负数往前移动

					spoTemp.setEDate(calendar1.getTime()); // 最早到货

					spoTemp.setCPrice(sComMatPrice.getUnitPrice()); // 运费单价
					spoTemp.setCUPrice(j * sComMatPrice.getUnitPrice()); // 运费总价
					spoTemp.setPurUnit(sNomaterialReportSum.getInvUnit());
					
					SCompanyCo sCompanyCo = sCompanyCoRepository.findOne(sComMatPrice.getIdCom());
					spoTemp.setComCo(sCompanyCo.getName());
					spoTemp.setIsPrim(1l);
					spoTemp.setIdCompany(idCompany);
					spoTemp.setIdComCo(sComMatPrice.getIdCom());
					spoTemp.setStatus(0l);
					spoTemp.setPrec(100f);
					sPoTempRepository.save(spoTemp);
				}
				else  //按百分比
				{
					

					SNomaterialReportSum sNomaterialReportSum = sNomaterialReportSumRepository.findOne(id);

					sNomaterialReportSum.setStatus(3l);
					
					sNomaterialReportSum.setPQty(aqtys.get(i));
					System.out.println("i : " + i + ", v: "  +aqtys.get(i));
					sNomaterialReportSumRepository.save(sNomaterialReportSum);
					i++;
					List<SComMatPrice> sComMatPrices = sComMatPriceRepository
							.findByIdMat(sNomaterialReportSum.getIdMat());
					for(SComMatPrice sComMatPrice:sComMatPrices)
					{
					SPoTemp spoTemp = new SPoTemp();
					spoTemp.setIdMat(sNomaterialReportSum.getIdMat());
					spoTemp.setMaterial(sNomaterialReportSum.getMaterial());
					spoTemp.setRDate(sNomaterialReportSum.getRDate());
			
					spoTemp.setSQty(-sNomaterialReportSum.getSQty());
					spoTemp.setInvUnit(sNomaterialReportSum.getInvUnit());
					Float yc = sNomaterialReportSum.getPQty()*sComMatPrice.getPrec()/100;
					Long ycl = (long)Math.ceil(yc);
					spoTemp.setSPoQty(ycl);  //应采购
					
					SMaterial mt = sMaterialRepository.findOne(sNomaterialReportSum.getIdMat());
					Long moq = mt.getMoq()==null?0l:mt.getMoq();
					Long mpq = mt.getMpq()==null?0l:mt.getMpq();
					spoTemp.setMoq(moq);
					spoTemp.setMpq(mpq);
					Long j =ycl; //实际采购
					if(j<moq)
					{
						 j= moq;
					}
					if(mpq>0)
					{
						if(j%mpq!=0)
						{
							j=(j/mpq+1)*mpq;
						}
					}
				
					spoTemp.setAPoQty(j); //实际采购
					//System.out.println("bili实际采购：" +j);
					spoTemp.setUPrice(sComMatPrice.getPrice()); // 单价
					spoTemp.setAUPrice(sComMatPrice.getPrice()); // 本次单价
					spoTemp.setPrice(j * sComMatPrice.getPrice());
					spoTemp.setYsDiff(ycl-j);
					spoTemp.setDiff((ycl-j)*sComMatPrice.getPrice());
					spoTemp.setDura(sComMatPrice.getDura()); // 货运期

					Date d = sNomaterialReportSum.getRDate(); // 需求时间
					Long days = sComMatPrice.getDura();
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(d);
					calendar.add(calendar.DATE, -days.intValue());// 把日期往后增加一天.整数往后推,负数往前移动
					spoTemp.setLateDate(calendar.getTime()); // 最晚下单

					Date d1 = new Date(); // 需求时间
					Calendar calendar1 = new GregorianCalendar();
					calendar1.setTime(d1);
					calendar1.add(calendar1.DATE, days.intValue());// 把日期往后增加一天.整数往后推,负数往前移动

					spoTemp.setEDate(calendar1.getTime()); // 最早到货

					spoTemp.setCPrice(sComMatPrice.getUnitPrice()); // 运费单价
					//sss
					spoTemp.setCUPrice(j * sComMatPrice.getUnitPrice()); // 运费总价
					spoTemp.setPurUnit(sNomaterialReportSum.getInvUnit());
		
					SCompanyCo sCompanyCo = sCompanyCoRepository.findOne(sComMatPrice.getIdCom());
					spoTemp.setComCo(sCompanyCo.getName());
					spoTemp.setIsPrim(sComMatPrice.getIsPrim());
					spoTemp.setIdCompany(idCompany);
					spoTemp.setIdComCo(sComMatPrice.getIdCom());
					spoTemp.setStatus(0l);
					spoTemp.setPrec(sComMatPrice.getPrec());
					sPoTempRepository.save(spoTemp);
					}
				
				}

					

				}

			}
			v.setValid(true);
			return v;
		}

		
	}

	//
	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/mrp/exportMatReportList")
	public Valid exportMatReportList(@RequestBody List<WSMatReport> ws) {
		Valid v = new Valid();

		v.setValid(true);
		return v;
	}

	// 汇总
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/mrp/noMatReportList")
	public WSTableData noMatReportList(@RequestParam(required = false, value = "fromDay") String fromDay,
			@RequestParam(required = false, value = "toDay") String toDay,
			@RequestParam(required = false, value = "materialId") Long materialId,

			@RequestParam Integer draw, @RequestParam Integer start, @RequestParam Integer length) throws Exception {

		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();

		List<SNomaterialReportSum> ws = sNomaterialReportSumRepositoryCustom.findByIdCompany(companyId, fromDay, toDay, materialId);

		//System.out.println(" fromDay: " + fromDay + ", toDay:  " + toDay + ", materialId: " + materialId);
		List<String[]> lst = new ArrayList<String[]>();
		int end = 0;
		if (ws.size() < start + length)
			end = ws.size();
		else
			end = start + length;
		// 6、8、9、11
		for (int i = start; i < end; i++) {
			SNomaterialReportSum w = ws.get(i);
			String id = "" + w.getId();
			String mat = w.getMaterial() == null ? "" : w.getMaterial();
			String invUnit = w.getInvUnit() == null ? "" : w.getInvUnit();
			String rDate = formatDate(w.getRDate());
			String fDate = formatDate(w.getFDate());
			String noMat = w.getSQty() == null ? "" : "" + w.getSQty();
			String pMate = w.getPQty() == null ? "" : "" + w.getPQty();
			String status = getStatus(w.getStatus());
			String[] d = { id, id, mat, invUnit, rDate, fDate, noMat, pMate, status };
			lst.add(d);

		}

		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(ws.size());
		t.setRecordsFiltered(ws.size());
		t.setData(lst);
		return t;
	}

	// shuaxin
	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/mrp/refreshMatReportList")
	public Valid refreshMatReportList(@RequestParam(required = false, value = "timeType") Long timetype,
			@RequestParam(required = false, value = "time") Integer time, // 采购分期
																			// 年月。。。。
			@RequestParam(required = false, value = "level") String level // 分期物料级别
	) {
		mrpService.loadComprice(level);
		// mrpService.loadNoMaterialReportSum(timetype, time);
		Valid v = new Valid();
		v.setMsg("");
		v.setValid(true);
		return v;

	}

	
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/mrp/generatePo")
	public Valid generatePo(@RequestBody WSMrpPo ps) throws Exception {
		Valid v = new Valid();
		//System.out.println("poType: " + ps.getPoType());
		//有多少个供应商，就生成多少个订单
		Map<Long,List<SPoTemp>> map = new HashMap<Long,List<SPoTemp> >();
		//List<Long> qtys = ps.getQtys();
		for (Long i : ps.getIds()) {
		//	System.out.println("id: " + i);
			SPoTemp t = sPoTempRepository.findOne(i);
			List<SPoTemp> ls = map.get(t.getIdComCo());
			if(ls == null)
			{
				ls = new ArrayList<SPoTemp>();
			}
			ls.add(t);
		//	System.out.println("tmp po id: " + i +  "coId: " + t.getIdComCo() + " , ls sizeL " + ls.size());
			map.put(t.getIdComCo(), ls);
		}
		for(Long idCom: map.keySet())
		{
			//System.out.println("idCom: " + idCom);
			WSSpo wsSpo = new WSSpo();
			wsSpo.setDateOrder(new Date());
			wsSpo.setsCompanyCoId(idCom);
			wsSpo.setsStatusId(7l);
			//wsSpo.setCodeCo(codeCo);
			List<SPoTemp> ls =map.get(idCom);
			Map<String,WSSpoMaterial> poItems = new LinkedHashMap<String,WSSpoMaterial>();
			Long i=1l;
			for(SPoTemp l:ls)
			{
				WSSpoMaterial w = new WSSpoMaterial();
				w.setDeliveryDate(l.getRDate());
				w.setLine(i-1);
				w.setQtyPo(l.getSQty());
				w.setsMaterialId(l.getIdMat());
				w.setUprice(new BigDecimal(l.getUPrice()));
				w.setTotalPrice(l.getUPrice()*l.getSQty());
				poItems.put("line"+i, w);
				i++;
				l.setStatus(1l);
				System.out.println("close temp Po: " + l.getId());
				sPoTempRepository.save(l);
			}
			
			
			 wsSpo.setPoItems(poItems);
			 spoService.saveSpo(wsSpo);
			
		}
		v.setValid(true);
		return v;
	}

	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/mrp/tempSourcingList")
	public WSTableData tempSourcingList(@RequestParam(required = false, value = "q") Long q,
			@RequestParam Integer draw, @RequestParam Integer start, @RequestParam Integer length) throws Exception {

		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();

		List<SPoTemp> tt = sPoTempRepository.findByIdCompany(companyId);
		System.out.println("q: " + q);
		if(q!=null)
		{
			//
			tt = tt.stream().filter(w->w.getIdMat().equals(q)).collect(Collectors.toList());
		}

		List<String[]> lst = new ArrayList<String[]>();
		int end = 0;
		if (tt.size() < start + length)
			end = tt.size();
		else
			end = start + length;
		// 6、8、9、11
		for (int i = start; i < end; i++) {
			SPoTemp t = tt.get(i);
			Long isP = t.getIsPrim();
			String isPrim = isP.equals(1l)?"是":"否";
			String[] d = { "" + t.getId(), t.getMaterial(), formatDate(t.getRDate()), "" + t.getSQty(), t.getInvUnit(),
					"" + t.getSPoQty(), "" + t.getAPoQty(), "" + t.getUPrice(), "" + t.getAUPrice(), "" + t.getPrice(),
					"" + t.getYsDiff(), "" + t.getDiff(), "" + t.getDura(), formatDate(t.getLateDate()),
					formatDate(t.getEDate()), "" + t.getUPrice(), "" + t.getCUPrice(), t.getPurUnit(), "" + t.getMpq(),
					"" + t.getMoq(), t.getComCo(), "" + t.getPrec(), isPrim };
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(tt.size());
		t.setRecordsFiltered(tt.size());
		t.setData(lst);
		return t;
	}

	//
	// @Transactional(readOnly = true)
	// @RequestMapping(value = "/s/mrp/buyMethodsTable")
	// public WSTableData buyMethodsTable(
	// @RequestParam Integer draw,
	// @RequestParam Integer start, @RequestParam Integer length) throws
	// Exception {
	//
	// Long companyId =
	// securityUtils.getCurrentDBUser().getCompany().getIdCompany();
	// List<BuyMehod> buyMethods =
	// buyMehodRepository.findByIdCompany(companyId);
	// List<String[]> lst = new ArrayList<String[]>();
	// int end = 0;
	// if (buyMethods.size() < start + length)
	// end = buyMethods.size();
	// else
	// end = start + length;
	// // 6、8、9、11
	// for (int i = start; i < end; i++) {
	// BuyMehod bm =buyMethods.get(i);
	// String unit = (bm.getUnit()==null)?"":bm.getUnit();
	// String remark = (bm.getRemark()==null)?"":bm.getRemark();
	// String descr = (bm.getDescr()==null)?"":bm.getDescr();
	// String[] d = {""+bm.getVl(),unit,descr,remark, ""+bm.getId() };
	// lst.add(d);
	//
	// }
	// WSTableData t = new WSTableData();
	// t.setDraw(draw);
	// t.setRecordsTotal(buyMethods.size());
	// t.setRecordsFiltered(buyMethods.size());
	// t.setData(lst);
	// return t;
	// }
	//
	//
	// //todo
	// @Transactional(readOnly = false)
	// @RequestMapping(value = "/s/mrp/saveBuyMethod")
	// public Valid saveBuyMethod(@RequestBody BuyMehod buyMethod)
	// {
	// Valid v = new Valid();
	// Long companyId =
	// securityUtils.getCurrentDBUser().getCompany().getIdCompany();
	// //新建
	// if(buyMethod.getId()==null||buyMethod.getId().equals(0l))
	// {
	// buyMethod.setId(null);
	// buyMethod.setIdCompany(companyId);
	// buyMehodRepository.save(buyMethod);
	// }
	// else
	// {
	// buyMethod.setIdCompany(companyId);
	// buyMehodRepository.save(buyMethod);
	// }
	// v.setValid(true);
	// return v;
	// }

	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/uploadComPriceFile", method = RequestMethod.POST)
	public Valid uploadComPriceFile(MultipartHttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Long idCompany = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		// String[] d =
		// {"物料编码","版本","供应商名称","采购百分比","采购价格","是否是主供应商","货运期（天）","运费单价"};
		Valid v = new Valid();
		v.setValid(true);
		FileMeta fileMeta = new FileMeta();
		Map<Integer, String> errorMap = new LinkedHashMap<Integer, String>();
		List<WSCompanyPrice> wsCompanyPrices = new ArrayList<WSCompanyPrice>();
		try {
			boolean flag = true;
			if (request.getFileNames().hasNext()) {
				fileMeta = fileUploadService.upload(request, response, true);
				FileInputStream inputStream = new FileInputStream(filePath + fileMeta.getFileName());
				CsvReader reader = new CsvReader(inputStream, ',', Charset.forName("UTF-8"));
				reader.readHeaders();

				Integer i = 1;
				while (reader.readRecord()) {
					i++;
					WSCompanyPrice wsCompanyPrice = new WSCompanyPrice();
					String pno = reader.get(0);

					SMaterial sMaterial = sMaterialRepository.getByCompanyIdAndPno(idCompany, pno);
					if (sMaterial == null) {
						flag = false;
						if (errorMap.containsKey(i)) {
							errorMap.put(i, errorMap.get(i) + " 无此物料: " + pno);
						} else {
							errorMap.put(i, "第" + i + "行出错，无此物料: " + pno);
						}
					} else {
						wsCompanyPrice.setMaterialId(sMaterial.getIdMaterial());
					}
					String comName = reader.get(2);
					SCompanyCo sCompanyCo = sCompanyCoRepository.findByCompanyIdAndNameAndType(idCompany, comName, 1l);

					if (sCompanyCo == null) {
						flag = false;
						if (errorMap.containsKey(i)) {
							errorMap.put(i, errorMap.get(i) + " 无此供应商: " + comName);
						} else {
							errorMap.put(i, "第" + i + "行出错，无此供应商:  " + comName);
						}
					} else {
						wsCompanyPrice.setIdCocompany(sCompanyCo.getId());

					}

					String prec = reader.get(3); // 采购百分比
					try {
						wsCompanyPrice.setPerc(Float.parseFloat(prec));
					} catch (Exception e) {

						flag = false;
						if (errorMap.containsKey(i)) {
							errorMap.put(i, errorMap.get(i) + " 采购百分比必须是数字: " + prec);
						} else {
							errorMap.put(i, "第" + i + "行出错，采购百分比必须是数字: " + prec);
						}

					}

					String price = reader.get(4); // 采购价格
					try {
						wsCompanyPrice.setPrice(Float.parseFloat(price));
					} catch (Exception e) {

						flag = false;
						if (errorMap.containsKey(i)) {
							errorMap.put(i, errorMap.get(i) + " 采购价格必须是数字: " + prec);
						} else {
							errorMap.put(i, "第" + i + "行出错，采购价格必须是数字: " + prec);
						}

					}

					String isPrim = reader.get(5); // 0不是主供应商，1是
					try {
						wsCompanyPrice.setIsPrim(Long.parseLong(isPrim));
					} catch (Exception e) {

						flag = false;
						if (errorMap.containsKey(i)) {
							errorMap.put(i, errorMap.get(i) + " 主供应商必须是数字: 0 否， 1是  " + isPrim);
						} else {
							errorMap.put(i, "第" + i + " 主供应商必须是数字: 0 否， 1是  " + isPrim);
						}

					}

					String dura = reader.get(6); // 货运周期
					try {
						wsCompanyPrice.setDays(Long.parseLong(dura));
					} catch (Exception e) {

						flag = false;
						if (errorMap.containsKey(i)) {
							errorMap.put(i, errorMap.get(i) + " 货运周期必须是数字:   " + dura);
						} else {
							errorMap.put(i, "第" + i + " 货运周期必须是数字: " + dura);
						}

					}

					String uprice = reader.get(7); // 运费单价
					try {
						wsCompanyPrice.setCarriage(Float.parseFloat(uprice));
					} catch (Exception e) {

						flag = false;
						if (errorMap.containsKey(i)) {
							errorMap.put(i, errorMap.get(i) + " 运费单价必须是数字:   " + uprice);
						} else {
							errorMap.put(i, "第" + i + " 运费单价必须是数字: " + uprice);
						}

					}

					wsCompanyPrices.add(wsCompanyPrice);

				}
				String msg = "";
				for (String err : errorMap.values()) {
					msg = msg + err + "<br/> \r\n";
				}
				v.setMsg(msg);
				v.setValid(flag);
				// return v;
			}
		} catch (Exception e) {
			e.printStackTrace();
			v.setMsg("上传失败，请检查文件格式：必须是csv类型文件,必须用utf-8编码。");
			v.setValid(false);
			// return v;
		}

		if (v.getValid()) {

			for (WSCompanyPrice w : wsCompanyPrices) {
				SComMatPrice sComMatPrice = new SComMatPrice();
				sComMatPrice.setIdCom(w.getIdCocompany());
				sComMatPrice.setIdCompany(idCompany);
				sComMatPrice.setIdMat(w.getMaterialId());
				sComMatPrice.setDura(w.getDays());
				sComMatPrice.setIsPrim(w.getIsPrim());
				sComMatPrice.setPrec(w.getPerc());
				sComMatPrice.setPrice(w.getPrice());
				sComMatPrice.setUnitPrice(w.getCarriage());
				sComMatPriceRepository.save(sComMatPrice);
			}

		}

		return v;

	}

	//
	// @Transactional(readOnly = true)
	// @RequestMapping(value = "/s/mrp/findBuyMethod")
	// public BuyMehod findBuyMethod(@RequestParam Long buyMethodId)
	// {
	// BuyMehod m = buyMehodRepository.findOne(buyMethodId);
	// return m;
	// }
	//

	private String formatDate(Date d) {
		if (d == null)
			return "";
		SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy-MM-dd");
		return myFmt1.format(d);
	}

	// 1未确认 2 确认 3 临时po
	private String getStatus(Long status) {
		String t = "";
		if (status != null) {
			switch (status.intValue()) {
			case 1: {
				t = "未确认";
				break;
			}
			case 2: {
				t = "已确认";
				break;
			}
			case 3: {
				t = "临时PO";
				break;
			}

			}

		}
		return t;
	}

	// <option value="1">生产原料数</option>
	// <option value="2">库存</option>
	// <option value="3">OPEN PO</option>
	// <option value="4">安全库存</option>
	// <option value="5">确认缺料</option>
	// <option value="6">临时PO</option>
	// <option value="7">采购申请</option>
	private String getType(Long type) {
		String t = "";
		if (type != null) {
			switch (type.intValue()) {
			case 1: {
				t = "生产原料数";
				break;
			}
			case 2: {
				t = "库存";
				break;
			}
			case 3: {
				t = "OPEN PO";
				break;
			}
			case 4: {
				t = "安全库存";
				break;
			}
			case 5: {
				t = "确认缺料";
				break;
			}
			case 6: {
				t = "临时PO";
				break;
			}
			case 7: {
				t = "采购申请";
				break;
			}
			}
		}

		return t;
	}

}
