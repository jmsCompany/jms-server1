package com.jms.controller.store;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SPo;
import com.jms.domain.db.SPoMaterial;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSCompanyPrice;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.s.WSSpo;
import com.jms.domain.ws.s.WSSpoMaterial;
import com.jms.domain.ws.s.WSSpoRemark;
import com.jms.file.FileMeta;
import com.jms.file.FileUploadService;
import com.jms.repositories.s.BuyMehodRepository;
import com.jms.repositories.s.SAttachmentRepository;
import com.jms.repositories.s.SSpoMaterialRepositoryCustom;
import com.jms.repositories.s.SSpoRepository;
import com.jms.service.store.SpoMaterialService;
import com.jms.service.store.SpoService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly = true)
public class MrpController {
	
	@Autowired private SecurityUtils securityUtils;
	@Autowired private SpoService spoService;
	@Autowired private SSpoRepository sSpoRepository;
	@Autowired private SpoMaterialService spoMaterialService;
	@Autowired private FileUploadService fileUploadService;
	@Autowired private SAttachmentRepository sAttachmentRepository;
	@Autowired private SSpoMaterialRepositoryCustom sSpoMaterialRepositoryCustom;
	
	@Autowired private  BuyMehodRepository buyMehodRepository;
	
	@Value("${filePath}")
	private String filePath;
	private static final Logger logger = LogManager.getLogger(MrpController.class.getCanonicalName());


	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/mrp/sourcingPriceList")
	public WSTableData sourcingPriceList(
			@RequestParam(required = false, value = "materialId") Long materialId,
			@RequestParam(required = false, value = "q") String q,
		    @RequestParam Integer draw,
			@RequestParam Integer start, @RequestParam Integer length) throws Exception {

		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
	
		List<String[]> lst = new ArrayList<String[]>();
		int end = 0;
		if (1 < start + length)
			end = 1;
		else
			end = start + length;
		// 6、8、9、11
		for (int i = start; i < end; i++) {
			
			String[] d = {"物料编码",	"物料描述","版本","供应商代码",	"供应商名称","采购百分比","	采购价格","主采购","交货周期","采购策略",""+1 };
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(1);
		t.setRecordsFiltered(1);
		t.setData(lst);
		return t;
	}
	
	//todo
	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/mrp/savecocompanyPriceList")
	public Valid savecocompanyPriceList(@RequestBody List<WSCompanyPrice> ws)
	{
		Valid v = new Valid();
		
		v.setValid(true);
		return v;
	}
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/mrp/cocompanyPriceList")
	public List<WSCompanyPrice> cocompanyPriceList(
			@RequestParam(required = false, value = "materialId") Long materialId) throws Exception {

		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
	
		List<WSCompanyPrice>  ws = new ArrayList<WSCompanyPrice>();
		WSCompanyPrice w1 = new WSCompanyPrice();
		w1.setCompanyName("Id为156的供应商");
		w1.setDays(3);
		w1.setId(1l);
		w1.setIdCocompany(156l);
		w1.setIsPrim(1);
		w1.setPerc(30f);
		w1.setPrice(2.4f);
		w1.setMeth("策略1");
		ws.add(w1);
		
		
		WSCompanyPrice w2 = new WSCompanyPrice();
		w2.setCompanyName("Id为157的供应商");
		w2.setDays(6);
		w2.setId(2l);
		w2.setIdCocompany(157l);
		w2.setIsPrim(0);
		w2.setPerc(70f);
		w2.setPrice(2.5f);
		w2.setMeth("策略2");
		ws.add(w2);
		
		
		return ws;
	}
	
	
	
	
	//明细
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/mrp/noMatDetailReportList")
	public WSTableData noMatDetailReportList(
			@RequestParam(required = false, value = "woCode") String woCode,
			@RequestParam(required = false, value = "type") Long type,
			@RequestParam(required = false, value = "q") String q,
		    @RequestParam Integer draw,
			@RequestParam Integer start, @RequestParam Integer length) throws Exception {

		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
	
		List<String[]> lst = new ArrayList<String[]>();
		int end = 0;
		if (1 < start + length)
			end = 1;
		else
			end = start + length;
		// 6、8、9、11
		for (int i = start; i < end; i++) {
			
			String[] d = {
					"序号",	"原料","库存单位",	"类别",	"数量","生产到货日期","缺料数量",	"状态",	"采购单号",	"工单号",	" 工单数","open WO","成品／半成品","计划单号","计划数量","open PP"};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(1);
		t.setRecordsFiltered(1);
		t.setData(lst);
		return t;
	}
	
	
	
	//汇总
		@Transactional(readOnly = true)
		@RequestMapping(value = "/s/mrp/noMatReportList")
		public WSTableData noMatReportList(
				@RequestParam(required = false, value = "fromDay") Date fromDay,
				@RequestParam(required = false, value = "toDay") Date  toDay,
				@RequestParam(required = false, value = "q") String q,
			    @RequestParam Integer draw,
				@RequestParam Integer start, @RequestParam Integer length) throws Exception {

			Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		
			List<String[]> lst = new ArrayList<String[]>();
			int end = 0;
			if (1 < start + length)
				end = 1;
			else
				end = start + length;
			// 6、8、9、11
			for (int i = start; i < end; i++) {
				
				String[] d = {
						"选择",	"序号","物料",	"库存单位",	"需料日期","缺料数量","采购数量",	"状态" };
				lst.add(d);

			}
			WSTableData t = new WSTableData();
			t.setDraw(draw);
			t.setRecordsTotal(1);
			t.setRecordsFiltered(1);
			t.setData(lst);
			return t;
		}
		
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/mrp/tempSourcingList")
	public WSTableData tempSourcingList(
			@RequestParam(required = false, value = "q") String q,
		    @RequestParam Integer draw,
			@RequestParam Integer start, @RequestParam Integer length) throws Exception {

		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
	
		List<String[]> lst = new ArrayList<String[]>();
		int end = 0;
		if (1 < start + length)
			end = 1;
		else
			end = start + length;
		// 6、8、9、11
		for (int i = start; i < end; i++) {
			
			String[] d = {
					""+1,	"物料编码",	"物料描述",	"版本",	"缺料数量",	"最小包装量",
					"最小起订量",	"供应商编码",	"供应商名称",	"百分比",	"主供应商",	"单价",	"采购数量","总价"
					,""+1 };
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(1);
		t.setRecordsFiltered(1);
		t.setData(lst);
		return t;
	}


	

	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/mrp/buyMethodsTable")
	public WSTableData buyMethodsTable(
		    @RequestParam Integer draw,
			@RequestParam Integer start, @RequestParam Integer length) throws Exception {

		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<BuyMehod> buyMethods =  buyMehodRepository.findByIdCompany(companyId);
		List<String[]> lst = new ArrayList<String[]>();
		int end = 0;
		if (buyMethods.size() < start + length)
			end = buyMethods.size();
		else
			end = start + length;
		// 6、8、9、11
		for (int i = start; i < end; i++) {
			BuyMehod bm =buyMethods.get(i);
			String unit = (bm.getUnit()==null)?"":bm.getUnit();
			String remark = (bm.getRemark()==null)?"":bm.getRemark();
			String descr = (bm.getDescr()==null)?"":bm.getDescr();
			String[] d = {""+bm.getVl(),unit,descr,remark, ""+bm.getId() };
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(buyMethods.size());
		t.setRecordsFiltered(buyMethods.size());
		t.setData(lst);
		return t;
	}
	
	
	//todo
	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/mrp/saveBuyMethod")
	public Valid saveBuyMethod(@RequestBody BuyMehod buyMethod)
	{
		Valid v = new Valid();
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		//新建
		if(buyMethod.getId()==null||buyMethod.getId().equals(0l))
		{
			buyMethod.setId(null);
			buyMethod.setIdCompany(companyId);
			buyMehodRepository.save(buyMethod);
		}
		else
		{
			buyMethod.setIdCompany(companyId);
			buyMehodRepository.save(buyMethod);
		}
		v.setValid(true);
		return v;
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/mrp/findBuyMethod")
	public BuyMehod findBuyMethod(@RequestParam Long buyMethodId)
	{
		BuyMehod m =	buyMehodRepository.findOne(buyMethodId);
		return m;
	}
	
}
