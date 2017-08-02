package com.jms.controller.store;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.jms.domain.db.Company;
import com.jms.domain.db.SAttachment;
import com.jms.domain.db.SCompanyCo;
import com.jms.domain.db.SCurrencyType;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SPo;
import com.jms.domain.db.SPoMaterial;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.p.WSPBom;
import com.jms.domain.ws.s.WSPoPrint;
import com.jms.domain.ws.s.WSSpo;
import com.jms.domain.ws.s.WSSpoMaterial;
import com.jms.domain.ws.s.WSSpoRemark;
import com.jms.file.FileMeta;
import com.jms.file.FileUploadService;
import com.jms.repositories.s.SAttachmentRepository;
import com.jms.repositories.s.SCompanyCoRepository;
import com.jms.repositories.s.SCurrencyTypeRepository;
import com.jms.repositories.s.SMaterialRepository;
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
	
	@Autowired private SAttachmentRepository sAttachmentRepository;
	@Autowired private SSpoMaterialRepositoryCustom sSpoMaterialRepositoryCustom;
	@Autowired private SCompanyCoRepository sCompanyCoRepository;
	
	@Autowired private SCurrencyTypeRepository sCurrencyTypeRepository;
	@Autowired private SMaterialRepository sMaterialRepository;
	@Autowired private FileUploadService fileUploadService;
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
	public Valid uploadSpoFile(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {

		//1采购单号，2供应商名称（全称），3汇率，4税率，5币别（不填写默认人民币），6物料编码，7版本，8单价，9采购单位，10数量，11交货日期，12备注
		// poNo,coComId,exchange,tax,currTypeId,statusId,totalprice,materialId,unitprice,unit,qtyNum,price,deliveryDate,remark,sno
	    Company c=	securityUtils.getCurrentDBUser().getCompany();
		Long myCompanyId = c.getIdCompany();
		Valid v = new Valid();
		v.setValid(true);

		Map<Integer,String> errorMap = new LinkedHashMap<Integer,String>();
		Map<String,WSSpo> spoMap = new LinkedHashMap<String,WSSpo>();
		FileMeta fileMeta = new FileMeta();
		try {
			 boolean flag = true;
			if (request.getFileNames().hasNext()) {
				fileMeta = fileUploadService.upload(request, response, true);
				FileInputStream inputStream = new FileInputStream(filePath + fileMeta.getFileName());
				CsvReader reader = new CsvReader(inputStream, ',', Charset.forName("UTF-8"));
				reader.readHeaders();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String currentPoNo = "";
				Long currentPoId =0l;
				WSSpo currentPo;
				float totalAmount = 0f;
				float totalPrice = 0f;
				//WSSpo spo = new WSSpo();
			    Map<String,WSSpoMaterial> poItems = new LinkedHashMap<String,WSSpoMaterial>();
			   
				Integer i = 1;
				int line =0;
				while (reader.readRecord()) {
					i++;
					line++;
					 WSSpoMaterial wsSpoMaterial =  new WSSpoMaterial();
					
					// System.out.println("woId:" + reader.get(0));
					 ////1采购单号，2供应商名称（全称），3汇率，4税率，5币别（不填写默认人民币），6物料编码，7版本，8单价，9采购单位，10数量，11交货日期，12备注
					String codePo = reader.get(0);// 订单编码
					codePo =codePo.trim();
					currentPo = new WSSpo();
					if(codePo==null||codePo.isEmpty())
					{
						flag =false;
						if(errorMap.containsKey(i))
						{
							errorMap.put(i, errorMap.get(i) + "  订单编码不能为空： " + codePo);
						}
						else
						{
							errorMap.put(i,  "第" + i +"行出错，订单编码不能为空：：  " +codePo);
						}
					}
					else
					{
						if(spoMap.containsKey(codePo))
						{
							currentPo = spoMap.get(codePo);
							//currentPo.setDateOrder(new Date());
						}
						else
						{
							currentPo = new WSSpo();
							currentPo.setCodePo(codePo);
							poItems = new LinkedHashMap<String,WSSpoMaterial>();
							currentPo.setPoItems(poItems);
							currentPo.setDateOrder(new Date());
							currentPo.setsStatusId(7l);//编辑状态
							
							System.out.println(" 新建订单： " + codePo);
							spoMap.put(codePo, currentPo);
							line =1;
							//currentPo.ge
						}
					 }
					////1采购单号，2供应商名称（全称），3汇率，4税率，5币别（不填写默认人民币），6物料编码，7版本，8单价，9采购单位，10数量，11交货日期，12备注
				String coCompanyName = reader.get(1); // 供应商公司名称
				coCompanyName =coCompanyName.trim();
   				SCompanyCo sCompanyCo = sCompanyCoRepository.findByCompanyIdAndNameAndType(myCompanyId, coCompanyName,1l);
				if(sCompanyCo==null)
				{
		             flag =false;
					 if(errorMap.containsKey(i))
						{
								errorMap.put(i, errorMap.get(i) + " 无此供应商名称: " + coCompanyName);
						}
						else
						{
								errorMap.put(i,  "第" + i +"行出错，无此供应商名称:  " + coCompanyName);
						}
				 }
				else
				{
					currentPo.setsCompanyCoId(sCompanyCo.getId());
					
				}

				
				//1采购单号，2供应商名称（全称），3汇率，4税率，5币别（不填写默认人民币），6物料编码，7版本，8单价，9采购单位，10数量，11交货日期，12备注
				String exchanges =  reader.get(2);
				exchanges =exchanges.trim();
				if(exchanges!=null&&!exchanges.isEmpty())
				{
					try
					{
					
						currentPo.setExchange(Float.parseFloat(exchanges));
				
					}
					catch (Exception e)
					{

						flag =false;
						if(errorMap.containsKey(i))
						{
							errorMap.put(i, errorMap.get(i) + " 汇率必须是数字: " + exchanges);
						}
						else
						{
							errorMap.put(i,  "第" + i +"行出错，汇率必须是数字: "+exchanges);
						}
					
					
					}
				}

				//1采购单号，2供应商名称（全称），3汇率，4税率，5币别（不填写默认人民币），6物料编码，7版本，8单价，9采购单位，10数量，11交货日期，12备注
				String tax =  reader.get(3);
				tax = tax.trim();
				if(tax!=null&&!tax.isEmpty())
				{
					try
					{
					
						currentPo.setTaxRate(Float.parseFloat(tax));
				
					}
					catch (Exception e)
					{


						
						flag =false;
						if(errorMap.containsKey(i))
						{
							errorMap.put(i, errorMap.get(i) + " 税率必须是数字: " + exchanges);
						}
						else
						{
							errorMap.put(i,  "第" + i +"行出错，税率必须是数字: "+exchanges);
						}
					
					
					}
				}
				//1采购单号，2供应商名称（全称），3汇率，4税率，5币别（不填写默认人民币），6物料编码，7版本，8单价，9采购单位，10数量，11交货日期，12备注
				String currType = reader.get(4);
				currType = currType.trim();
				if(currType!=null&&!currType.isEmpty())
				{
					SCurrencyType	sCurrencyType = sCurrencyTypeRepository.findByCurrency(currType);
					if(sCurrencyType==null)
					{
						flag =false;
						if(errorMap.containsKey(i))
						{
							errorMap.put(i, errorMap.get(i) + " 币别出错: " + currType);
						}
						else
						{
							errorMap.put(i,  "第" + i +"行出错，币别: "+currType);
						}
					
					}
				}
				else
				{
					currentPo.setsCurrencyTypeId(1l);
				}
				
			
				//1采购单号，2供应商名称（全称），3汇率，4税率，5币别（不填写默认人民币），6物料编码，7版本，8单价，9采购单位，10数量，11交货日期，12备注
				String pno = reader.get(5);
				pno = pno.trim();
				if(pno==null||pno.isEmpty())
				{
					flag =false;
					if(errorMap.containsKey(i))
					{
						errorMap.put(i, errorMap.get(i) + "  物料编码长度必须在0-64之间： " + pno);
					}
					else
					{
						errorMap.put(i,  "第" + i +"行出错，物料编码长度必须在0-64之间：  " +pno);
					}
				}
				else
				{
					//采购的物料
				   SMaterial mat =	sMaterialRepository.getByCompanyIdAndPno(myCompanyId, pno);
				   if(mat ==null)
				   {
						flag =false;
						if(errorMap.containsKey(i))
						{
							errorMap.put(i, errorMap.get(i) + "  无此物料编码： " + pno);
						}
						else
						{
							errorMap.put(i,  "第" + i +"行出错，无此物料编码：  " +pno);
						}
				
				   }
				   else
				   {
					  // currentPo.getPoItems();
					   wsSpoMaterial.setCodePo(codePo);
					   wsSpoMaterial.setsMaterialId(mat.getIdMaterial());
					   System.out.println("输入物料： item"+line   + " id: " +mat.getIdMaterial() );
					   poItems.put("item"+line, wsSpoMaterial);
				   }
				   }
				//1采购单号，2供应商名称（全称），3汇率，4税率，5币别（不填写默认人民币），6物料编码，7版本，8单价，9采购单位，10数量，11交货日期，12备注
				try{
					String s = reader.get(7);
					s = s.trim();
					BigDecimal uprice = new BigDecimal(s); //单位价格
					
					wsSpoMaterial.setUprice(uprice);
				}
				catch (Exception e)
				{

					flag =false;
					if(errorMap.containsKey(i))
					{
						errorMap.put(i, errorMap.get(i) + "  单位价格输入错误： " + reader.get(7));
					}
					else
					{
						errorMap.put(i,  "第" + i +"行出错，单位价格输入错误：  " +reader.get(7));
					}
			
			   
				}
				//1采购单号，2供应商名称（全称），3汇率，4税率，5币别（不填写默认人民币），6物料编码，7版本，8单价，9采购单位，10数量，11交货日期，12备注
				String unit = reader.get(8); // 采购单位
				wsSpoMaterial.setUnit(unit);
				
				
				try{
					String s  = reader.get(9);
					s = s.trim();
					Long qty = Long.parseLong(s); // 数量
					
					wsSpoMaterial.setQtyPo(qty);
				}
				catch (Exception e)
				{

					e.printStackTrace();
					flag =false;
					if(errorMap.containsKey(i))
					{
						errorMap.put(i, errorMap.get(i) + "  数量输入错误： " + reader.get(9));
					}
					else
					{
						errorMap.put(i,  "第" + i +"行出错，数量输入错误：：  " +reader.get(9));
					}
			
			   
				}
				
				if(wsSpoMaterial.getQtyPo()!=null&&wsSpoMaterial.getUprice()!=null)
				{
					totalPrice = wsSpoMaterial.getQtyPo()*wsSpoMaterial.getUprice().floatValue();
					wsSpoMaterial.setTotalPrice(totalPrice);
					totalAmount = totalAmount + totalPrice;
					currentPo.setTotalAmount(totalAmount);
					
				}
			
				try{
					String s  = reader.get(10);
					s = s.trim();
					Date deliveryDate = formatter.parse(s);// 运货日期
					
					wsSpoMaterial.setDeliveryDate(deliveryDate);
					//wsSpoMaterial.setQtyPo(qty);
				}
				catch (Exception e)
				{
					e.printStackTrace();

					flag =false;
					if(errorMap.containsKey(i))
					{
						errorMap.put(i, errorMap.get(i) + "  日期输入错误： " + reader.get(10));
					}
					else
					{
						errorMap.put(i,  "第" + i +"行出错，日期输入错误：：  " +reader.get(10));
					}
			
			   
				}

				String remark = reader.get(12);//
				wsSpoMaterial.setRemark(remark);
				}
			}
			String msg = "";
			for(String err: errorMap.values())
			{
				msg = msg+ err + "<br/> \r\n";
			}
			v.setMsg(msg);
			v.setValid(flag);
		} catch (Exception e) {
			e.printStackTrace();
			v.setMsg("上传采购订单失败！");
			v.setValid(false);
			return v;
		}
		if(v.getValid())
		{
			
			for(WSSpo wsSPo: spoMap.values())
			{
				// bomLabelService.savePBomLabel(wsPBom);
				//materialService.saveMaterial(wsMaterial);
				System.out.println(" save: wsSpo  spno: " + wsSPo.getCodePo() +" spo size: " + wsSPo.getPoItems().size());
				
				if(c.getAutoPo()!=null&&c.getAutoPo().equals(1l))//自动增
				{
					wsSPo.setCodePo(null);
				}
				for(WSSpoMaterial wpm: wsSPo.getPoItems().values())
				{
					System.out.println(" mat: " + wpm.getsMaterialId());
				}
				spoService.saveSpo(wsSPo);
			}
	
		}
		
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
	@RequestMapping(value = "/s/spoPrint", method = RequestMethod.GET)
	public WSPoPrint spoPrint(@RequestParam("spoId") Long spoId)  {
		return spoService.printSpo(spoId);
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


	
	@Transactional(readOnly = true)
	@RequestMapping(value="/check/spoCode", method=RequestMethod.GET)
	public Valid checkCodePo(@RequestParam("codePo") String codePo) {
		Valid valid = new Valid();
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<SPo> spos = sSpoRepository.findByCompanyIdAndCodePoAll(companyId, codePo);
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
