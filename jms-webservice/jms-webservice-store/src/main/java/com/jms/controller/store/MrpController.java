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
import com.jms.domain.db.SAttachment;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SPo;
import com.jms.domain.db.SPoMaterial;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.s.WSSpo;
import com.jms.domain.ws.s.WSSpoMaterial;
import com.jms.domain.ws.s.WSSpoRemark;
import com.jms.file.FileMeta;
import com.jms.file.FileUploadService;
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
	
	@Value("${filePath}")
	private String filePath;
	private static final Logger logger = LogManager.getLogger(MrpController.class.getCanonicalName());


	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/mrp/sourcingPriceList")
	public WSTableData sourcingPriceList(
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
			
			String[] d = {"物料编码",	"物料描述","版本","供应商代码",	 "供应商名称","采购百分比","	采购价格","主采购",""+1 };
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
	@RequestMapping(value = "/s/mrp/noMatReportList")
	public WSTableData noMatReportList(
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
					"选择",	"物料编码","物料描述",	"版本",	"工单号","有效库存","open so",	"open wo",	"open po",	"缺料数",	" 采购数",
					""+1 };
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


}
