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
public class SpoController {
	
	@Autowired private SecurityUtils securityUtils;
	@Autowired private SpoService spoService;
	@Autowired private SSpoRepository sSpoRepository;
	@Autowired private SpoMaterialService spoMaterialService;
	@Autowired private FileUploadService fileUploadService;
	@Autowired private SAttachmentRepository sAttachmentRepository;
	@Autowired private SSpoMaterialRepositoryCustom sSpoMaterialRepositoryCustom;
	
	@Value("${filePath}")
	private String filePath;
	private static final Logger logger = LogManager.getLogger(SpoController.class.getCanonicalName());

	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/saveSpo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public WSSpo saveSpo(@RequestBody WSSpo wsSpo) throws Exception {
		// System.out.println("save Spo!");
		return spoService.saveSpo(wsSpo);
	}

	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/uploadSpoAttachment", method = RequestMethod.POST)
	public FileMeta uploadSpoAttachment(@RequestParam(required = false, value = "spoId") Long spoId,
			MultipartHttpServletRequest request, HttpServletResponse response) {

		// logger.debug("upload spo attachment: spo id: " + spoId);
		FileMeta fileMeta = new FileMeta();
		if (request.getFileNames().hasNext()) {
			fileMeta = fileUploadService.upload(request, response, false);
			SAttachment spic = new SAttachment();
			spic.setOrgFilename(fileMeta.getOrgName());
			spic.setFilename(fileMeta.getFileName());
			// logger.debug("orgin file name: " + fileMeta.getOrgName() +", file
			// name in server: " + fileMeta.getFileName());
			spic.setUsers(securityUtils.getCurrentDBUser());
			spic = sAttachmentRepository.save(spic);
			fileMeta.setFileId(spic.getId());
			fileMeta.setBytes(null);
			if (spoId != null && !spoId.equals(0l)) {
				SPo spo = sSpoRepository.findOne(spoId);
				spo.setSAttachment(spic);
				sSpoRepository.save(spo);
			}

		} else {
			logger.debug("no file was uploaded");
		}
		return fileMeta;
	}

	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/uploadSpoFile", method = RequestMethod.POST)
	public Valid uploadSpoFile(MultipartHttpServletRequest request, HttpServletResponse response) {

		// poNo,coComId,exchange,tax,currTypeId,statusId,totalprice,materialId,unitprice,unit,qtyNum,price,deliveryDate,remark,sno

		Valid v = new Valid();
		FileMeta fileMeta = new FileMeta();
		try {
			if (request.getFileNames().hasNext()) {
				fileMeta = fileUploadService.upload(request, response, true);
				FileInputStream inputStream = new FileInputStream(filePath + fileMeta.getFileName());
				CsvReader reader = new CsvReader(inputStream, ',', Charset.forName("UTF-8"));
				reader.readHeaders();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				WSSpo spo = new WSSpo();
				int i = 0;
				while (reader.readRecord()) {
					i++;
					// System.out.println("woId:" + reader.get(0));
					String codePo = reader.get(0);// 订单编码
					if (codePo.isEmpty()) {
						codePo = null;
					}
					Long coCompanyId = Long.parseLong(reader.get(1)); // 供应商公司ID
					Float exchange = Float.parseFloat(reader.get(2)); // 汇率
					Float tax = Float.parseFloat(reader.get(3)); // 税
					Long currTypeId = Long.parseLong(reader.get(4)); // 币别
					Long statusId = Long.parseLong(reader.get(5)); // 状态ID
					Float totalAmount = Float.parseFloat(reader.get(6)); // 总价
					Long materialId = Long.parseLong(reader.get(7)); // 物料Id

					BigDecimal uprice = new BigDecimal(reader.get(8)); // 单价
					String unit = reader.get(9); // 单价
					Long qty = Long.parseLong(reader.get(10)); // 数量
					Long price = Long.parseLong(reader.get(11)); // 价格
					Date deliveryDate = formatter.parse(reader.get(12));// 运货日期
					String remark = reader.get(13);//

					String sno = reader.get(14);// 缺货单号

					if (i == 1) {
						spo.setCodePo(codePo);
						spo.setsCompanyCoId(coCompanyId);
						spo.setExchange(exchange);
						spo.setsCurrencyTypeId(currTypeId);
						spo.setTaxRate(tax);
						spo.setRemark(remark);
						spo.setsStatusId(statusId);// 编辑状态
						spo.setTotalAmount(totalAmount);
						// spo.set
					}

					WSSpoMaterial pm = new WSSpoMaterial();
					pm.setQtyPo(qty);
					pm.setUnit(unit);
					pm.setUprice(uprice);
					pm.setTotalPrice(price);
					pm.setDeliveryDate(deliveryDate);
					pm.setsMaterialId(materialId);
					// pm.set
					// pm.setDes(des);
					spo.getPoItems().put("item" + i, pm);

				}
				spoService.saveSpo(spo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			v.setMsg("上传采购订单失败！");
			v.setValid(false);
			return v;
		}
		v.setMsg("成功");
		v.setValid(true);
		return v;
	}

	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/saveSpoRemark", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Valid saveSpoRemark(@RequestBody WSSpoRemark wsSpoRemark) throws Exception {
		return spoService.saveSpoRemark(wsSpoRemark);
	}

	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/deleteSpo", method = RequestMethod.GET)
	public Valid deleteSpo(@RequestParam("spoId") Long spoId) {
		return spoService.deleteSpo(spoId);
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/findSpo", method = RequestMethod.GET)
	public WSSpo findWSSpo(@RequestParam("spoId") Long spoId) throws Exception {
		return spoService.findSpo(spoId);
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/spoMaterialList", method = RequestMethod.POST)
	public WSTableData getSpoList(@RequestParam(required = false, value = "type") Long type,
			@RequestParam(required = false, value = "q") String q,
			@RequestParam(required = false, value = "fromDay") String fromDay,
			@RequestParam(required = false, value = "toDay") String toDay, @RequestParam Integer draw,
			@RequestParam Integer start, @RequestParam Integer length) throws Exception {

		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<SPoMaterial> spoMaterials = sSpoMaterialRepositoryCustom.getCustomSpoMaterials(companyId, type, q, fromDay,
				toDay);
		List<String[]> lst = new ArrayList<String[]>();
		int end = 0;
		if (spoMaterials.size() < start + length)
			end = spoMaterials.size();
		else
			end = start + length;
		// 6、8、9、11
		for (int i = start; i < end; i++) {
			SPoMaterial w = spoMaterials.get(i);
			String del = (w.getDeliveryDate() == null) ? "" : w.getDeliveryDate().toString();
			String pno = "";
			String rev = "";
			String des = "";
			String unit = "";
			SMaterial s = w.getSMaterial();
			if (s != null) {
				pno = (s.getPno() == null) ? "" : s.getPno();
				rev = (s.getRev() == null) ? "" : s.getRev();
				des = (s.getDes() == null) ? "" : s.getDes();
				if (s.getSUnitDicByUnitPur() != null) {
					unit = s.getSUnitDicByUnitPur().getName();
				}

			}

			String qtyPo = (w.getQtyPo() == null) ? "" : "" + w.getQtyPo();
			String totalPrice = (w.getTotalPrice() == null) ? "" : "" + w.getTotalPrice();
			String qtyRev = (w.getQtyReceived() == null) ? "" : "" + w.getQtyReceived();
			String userName = "";
			if (w.getSPo().getUsers() != null) {
				if (w.getSPo().getUsers().getUsername() != null)
					userName = w.getSPo().getUsers().getUsername();
			}
			String coShortName = "";
			if (w.getSPo().getSCompanyCo() != null) {
				coShortName = w.getSPo().getSCompanyCo().getShortName();
			}
			String status = "";
			if (w.getSPo().getSStatusDic() != null) {
				status = w.getSPo().getSStatusDic().getName();
			}

			String[] d = { w.getSPo().getCodePo(), "" + w.getSPo().getDateOrder(), userName, coShortName, status,
					pno + "_" + rev + "_" + des, unit, qtyPo, totalPrice, del, qtyRev, "" + w.getSPo().getIdPo() };
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(spoMaterials.size());
		t.setRecordsFiltered(spoMaterials.size());
		t.setData(lst);
		return t;
	}

	// 往来公司采购单
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/spoMaterialListfromCom", method = RequestMethod.POST)
	public WSTableData spoMaterialListfromCom(@RequestParam(required = false, value = "type") Long type,
			@RequestParam(required = false, value = "q") String q,
			@RequestParam(required = false, value = "fromDay") String fromDay,
			@RequestParam(required = false, value = "toDay") String toDay, @RequestParam Integer draw,
			@RequestParam Integer start, @RequestParam Integer length) throws Exception {

		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<SPoMaterial> spoMaterials = sSpoMaterialRepositoryCustom.getComComSpoMaterials(companyId, type, q, fromDay,
				toDay);
		List<String[]> lst = new ArrayList<String[]>();
		int end = 0;
		if (spoMaterials.size() < start + length)
			end = spoMaterials.size();
		else
			end = start + length;
		// 6、8、9、11
		for (int i = start; i < end; i++) {
			SPoMaterial w = spoMaterials.get(i);
			String del = (w.getDeliveryDate() == null) ? "" : w.getDeliveryDate().toString();
			String pno = "";
			String rev = "";
			String des = "";
			String unit = "";
			SMaterial s = w.getSMaterial();
			if (s != null) {
				pno = (s.getPno() == null) ? "" : s.getPno();
				rev = (s.getRev() == null) ? "" : s.getRev();
				des = (s.getDes() == null) ? "" : s.getDes();
				if (s.getSUnitDicByUnitPur() != null) {
					unit = s.getSUnitDicByUnitPur().getName();
				}

			}

			String qtyPo = (w.getQtyPo() == null) ? "" : "" + w.getQtyPo();
			String totalPrice = (w.getTotalPrice() == null) ? "" : "" + w.getTotalPrice();
			String qtyRev = (w.getQtyReceived() == null) ? "" : "" + w.getQtyReceived();
			String userName = "";
			if (w.getSPo().getUsers() != null) {
				if (w.getSPo().getUsers().getUsername() != null)
					userName = w.getSPo().getUsers().getUsername();
			}
			String coShortName = "";
			if (w.getSPo().getSCompanyCo() != null) {
				coShortName = w.getSPo().getSCompanyCo().getShortName();
			}
			String status = "";
			if (w.getSPo().getSStatusDic() != null) {
				status = w.getSPo().getSStatusDic().getName();
			}

			String[] d = { w.getSPo().getCodePo(), "" + w.getSPo().getDateOrder(), userName, coShortName, status,
					pno + "_" + rev + "_" + des, unit, qtyPo, totalPrice, del, qtyRev, "" + w.getSPo().getIdPo() };
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(spoMaterials.size());
		t.setRecordsFiltered(spoMaterials.size());
		t.setData(lst);
		return t;
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/spoList", method = RequestMethod.GET)
	public List<WSSelectObj> findSpoList(@RequestParam("codeCo") Long codeCo) throws Exception {
		return spoService.findSpoListByCodeCo(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), codeCo);
	}

	// 采购价格列表fake
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/spoprice", method = RequestMethod.POST)
	public WSTableData spoprice(@RequestParam Integer draw, @RequestParam Integer start, @RequestParam Integer length)
			throws Exception {

		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();

		List<String[]> lst = new ArrayList<String[]>();
		int end = 0;
		if (1 < start + length)
			end = 1;
		else
			end = start + length;
		// 6、8、9、11
		// for (int i = start; i < end; i++) {
		String[] d = { "1", "2", "3", "4", "5", "6", "7", "是", "9" };
		lst.add(d);

		// }
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(1);
		t.setRecordsFiltered(1);
		t.setData(lst);
		return t;
	}

	// 采购价格列表fake
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/shortmaterials", method = RequestMethod.POST)
	public WSTableData shortmaterials(@RequestParam(required = false, value = "materialId") Integer materialId,
			@RequestParam Integer draw, @RequestParam Integer start, @RequestParam Integer length) throws Exception {

		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();

		List<String[]> lst = new ArrayList<String[]>();
		int end = 0;
		if (1 < start + length)
			end = 1;
		else
			end = start + length;
		// 6、8、9、11
		// for (int i = start; i < end; i++) {
		String[] d = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17" };
		lst.add(d);

		// }
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(1);
		t.setRecordsFiltered(1);
		t.setData(lst);
		return t;
	}

	
	// 采购价格列表fake
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/shortmaterilasTotal", method = RequestMethod.POST)
	public WSTableData shortmaterilas(@RequestParam(required = false, value = "materialId") Integer materialId,
			@RequestParam Integer draw, @RequestParam Integer start, @RequestParam Integer length) throws Exception {

		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();

		List<String[]> lst = new ArrayList<String[]>();
		int end = 0;
		if (1 < start + length)
			end = 1;
		else
			end = start + length;
		// 6、8、9、11
		// for (int i = start; i < end; i++) {
		String[] d = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
		lst.add(d);

		// }
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(1);
		t.setRecordsFiltered(1);
		t.setData(lst);
		return t;
	}

	
	// 采购价格列表fake
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/tmpspo", method = RequestMethod.POST)
	public WSTableData tmpspo(@RequestParam(required = false, value = "materialId") Integer materialId,
			@RequestParam Integer draw, @RequestParam Integer start, @RequestParam Integer length) throws Exception {

		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();

		List<String[]> lst = new ArrayList<String[]>();
		int end = 0;
		if (1 < start + length)
			end = 1;
		else
			end = start + length;
		// 6、8、9、11
		// for (int i = start; i < end; i++) {
		String[] d = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14" };
		lst.add(d);
		// }
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(1);
		t.setRecordsFiltered(1);
		t.setData(lst);
		return t;
	}

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/check/spoCode", method=RequestMethod.GET)
	public Valid checkCodePo(@RequestParam("codePo") String codePo) {
		Valid valid = new Valid();
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<SPo> spos = sSpoRepository.findByCompanyIdAndCodePo(companyId, codePo);
		if(spos.isEmpty())
		{
			valid.setValid(true);
			return valid;
		}
		else
		{
			valid.setValid(false);
			valid.setMsg("该订单编码已经存在！");
			return valid;
		}
	
	}
	
}
