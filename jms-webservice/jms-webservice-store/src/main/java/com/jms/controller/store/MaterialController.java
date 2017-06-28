package com.jms.controller.store;


import java.io.FileInputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.csvreader.CsvReader;
import com.jms.domain.db.SBin;
import com.jms.domain.db.SCompanyCo;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMaterialCategory;
import com.jms.domain.db.SMaterialPic;
import com.jms.domain.db.SMaterialTypeDic;
import com.jms.domain.db.SPic;
import com.jms.domain.db.SPoMaterial;
import com.jms.domain.db.SUnitDic;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSPOMaterialObj;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.f.WSFCostCenter;
import com.jms.domain.ws.s.WSCompanyCo;
import com.jms.domain.ws.s.WSLotNo;
import com.jms.domain.ws.s.WSMaterial;
import com.jms.domain.ws.s.WSSpoMaterial;
import com.jms.file.FileMeta;
import com.jms.file.FileUploadService;
import com.jms.repositories.f.FCostCenterRepository;
import com.jms.repositories.s.SMaterialCategoryRepository;
import com.jms.repositories.s.SMaterialPicRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SMaterialTypeDicRepository;
import com.jms.repositories.s.SMtfMaterialRepository;
import com.jms.repositories.s.SPicRepository;
import com.jms.repositories.s.SSpoMaterialRepository;
import com.jms.repositories.s.SUnitDicRepository;
import com.jms.service.store.CostCenterService;
import com.jms.service.store.MaterialService;
import com.jms.service.store.SpoMaterialService;
import com.jms.web.security.SecurityUtils;

@RestController
@Transactional(readOnly = true)
public class MaterialController {

	@Autowired
	private MaterialService materialService;
	@Autowired private SMaterialTypeDicRepository sMaterialTypeDicRepository;
	@Autowired private SMaterialCategoryRepository sMaterialCategoryRepository;
	@Autowired private SUnitDicRepository sUnitDicRepository;
	@Autowired private SecurityUtils securityUtils;
	@Autowired private CostCenterService costCenterService;
	@Autowired private SMaterialRepository sMaterialRepository;
	@Autowired private SSpoMaterialRepository sSpoMaterialRepository;
	@Autowired private SpoMaterialService spoMaterialService;
	@Autowired private SMtfMaterialRepository sMtfMaterialRepository;
    @Value("${filePath}") private String filePath;
	@Autowired private FileUploadService fileUploadService;
	@Autowired private SPicRepository sPicRepository;
	@Autowired private SMaterialPicRepository sMaterialPicRepository;


	
	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/saveMaterial", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public WSMaterial saveWSMaterial(@RequestBody WSMaterial wsMaterial) throws Exception {
		return materialService.saveMaterial(wsMaterial);
	}

	
	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/deleteMaterial", method = RequestMethod.GET)
	public Valid deleteMaterial(@RequestParam("materialId") Long materialId) {
		return materialService.deleteMaterial(materialId);

	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/findMaterial", method = RequestMethod.GET)
	public WSMaterial findWSMaterial(@RequestParam("materialId") Long materialId) throws Exception {
		return materialService.findMaterial(materialId);

	}

	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/materialList", method = RequestMethod.POST)
	public WSTableData getMaterialList(@RequestParam(value = "materialTypeId", required = false) Long materialTypeId,
			@RequestParam(value = "q", required = false) String q, @RequestParam Integer draw,
			@RequestParam Integer start, @RequestParam Integer length) throws Exception {

		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<WSMaterial> wsMaterials = materialService.getMaterials(companyId, materialTypeId, q);
		List<String[]> lst = new ArrayList<String[]>();
		int end = 0;
		if (wsMaterials.size() < start + length)
			end = wsMaterials.size();
		else
			end = start + length;
		for (int i = start; i < end; i++) {
			WSMaterial w = wsMaterials.get(i);
			String[] d = { w.getPno(), w.getDes(), w.getRev(), w.getsMaterialTypeDic(), w.getMaterialCategory(),
					w.getsUnitDicByUnitInf(), w.getsStatusDic(), "" + w.getIdMaterial() };
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(wsMaterials.size());
		t.setRecordsFiltered(wsMaterials.size());
		t.setData(lst);
		return t;
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/getMaterialListObjs", method = RequestMethod.GET)
	public List<WSSelectObj> getMaterialListObjs(
			@RequestParam(value = "q", required = false) String q,@RequestParam(value = "types", required = false) List<Long> types) throws Exception {
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<WSSelectObj> ws =new ArrayList<WSSelectObj>();
		List<WSMaterial> wsMaterials;
		if(types==null)
		{
			 wsMaterials = materialService.getMaterials(companyId, null, q);	
		}
		else
		{
			 wsMaterials = materialService.getMaterialsByTypesAndQ(companyId, types, q);
		
		}
		
		
		for (WSMaterial m:wsMaterials) {
		
			WSSelectObj w = new WSSelectObj(m.getIdMaterial(),m.getPno()+"_"+m.getRev()+"_"+m.getDes());
			ws.add(w);
		}

		return ws;
	}

	

	// 物料大类
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/materialTypes", method = RequestMethod.GET)
	public List<WSSelectObj> findMaterialTypes() {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		for (SMaterialTypeDic s : sMaterialTypeDicRepository.findAll()) {
			if(!s.getName().equals("备件"))
			{
				wso.add(new WSSelectObj(s.getId(), s.getName()));
			}
			
		}
		return wso;
	}

	// 物料小类
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/materialCategories", method = RequestMethod.GET)
	public List<WSSelectObj> findMateriaCategories() {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		for (SMaterialCategory s : sMaterialCategoryRepository
				.findByIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany())) {
			wso.add(new WSSelectObj(s.getId(), s.getName()));
		}
		return wso;
	}

	
	// 物料Unit
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/units", method = RequestMethod.GET)
	public List<WSSelectObj> findUnits() {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		for (SUnitDic s : sUnitDicRepository.findAll()) {
			wso.add(new WSSelectObj(s.getId(), s.getName()));
		}
		return wso;
	}

	
	// costCenter
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/costCenters", method = RequestMethod.GET)
	public List<WSSelectObj> findCostCenters() throws Exception {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		for (WSFCostCenter s : costCenterService.getCostCenterList()) {
			wso.add(new WSSelectObj(s.getIdCostCenter(), s.getDes()));
		}
		return wso;
	}

	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/materials", method = RequestMethod.GET)
	public List<WSSelectObj> findMaterials(@RequestParam(required=false, value="typeId") Long typeId) {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		List<SMaterial> sms;
		if(typeId!=null)
		{
			sms =sMaterialRepository
					.getByCompanyIdAndMaterialType(securityUtils.getCurrentDBUser().getCompany().getIdCompany(),typeId);
		}
		else
		{
			sms =sMaterialRepository
					.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		}
		for (SMaterial s : sms) {
			wso.add(new WSSelectObj(s.getIdMaterial(), s.getPno() + "-" + s.getDes() + "-" + s.getRev()));
		}
		return wso;
	}

	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/findPoMaterialsBySpoId", method = RequestMethod.GET)
	public List<WSPOMaterialObj> findPoMaterialsBySpoId(@RequestParam("spoId") Long spoId) {
		List<WSPOMaterialObj> wso = new ArrayList<WSPOMaterialObj>();
		for (SPoMaterial s : sSpoMaterialRepository.getBySpoId(spoId)) {
			Long qtyreceived = (s.getQtyReceived()==null)?0l:s.getQtyReceived();
			
			if(s.getQtyPo()>qtyreceived)
			{
				wso.add(new WSPOMaterialObj(s.getIdPoMaterial()+"_"+s.getSMaterial().getIdMaterial(),
						s.getSMaterial().getPno() + "-" + s.getSMaterial().getDes() + "-" + s.getSMaterial().getRev()));
			}
			
		}
		return wso;
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/findMaterialsBySpoMaterialId", method = RequestMethod.GET)
	public WSSpoMaterial findMaterialsBySpoMaterialId(@RequestParam("spoMaterialId") Long spoMaterialId)
			throws Exception {
		SPoMaterial spoMaterial = sSpoMaterialRepository.findOne(spoMaterialId);
		return spoMaterialService.toWSSpoMaterial(spoMaterial);

	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/findLotNos", method = RequestMethod.GET)
	public List<WSLotNo> findLotNos(@RequestParam("spoId") Long spoId, @RequestParam("materialId") Long materialId) {
		List<WSLotNo> wso = new ArrayList<WSLotNo>();
		for (String s : sMtfMaterialRepository.getLotNosBySpoIdAndMaterialId(spoId, materialId)) {
			wso.add(new WSLotNo(s, s));
		}
		return wso;
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/findToBinsBySpoIdAndMaterialID", method = RequestMethod.GET)
	public List<WSSelectObj> findToBinsBySpoIdAndMaterialID(@RequestParam("spoId") Long spoId,
			@RequestParam("materialId") Long materialId) {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		for (SBin s : sMtfMaterialRepository.getToBinsBySpoIdAndMaterialId(spoId, materialId)) {
			wso.add(new WSSelectObj(s.getIdBin(), s.getBin()));
		}
		return wso;
	}

	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/uploadMaterialImage", method = RequestMethod.POST)
	public FileMeta uploadFile(@RequestParam("materialId") Long materialId, MultipartHttpServletRequest request,
			HttpServletResponse response) {
		FileMeta fileMeta = new FileMeta();
		if (request.getFileNames().hasNext()) {
			fileMeta = fileUploadService.upload(request, response,false);
			SPic spic = new SPic();
			spic.setOrgFilename(fileMeta.getOrgName());
			spic.setFilename(fileMeta.getFileName());
			spic.setUsers(securityUtils.getCurrentDBUser());
			spic = sPicRepository.save(spic);
			fileMeta.setFileId(spic.getId());
			fileMeta.setBytes(null);
			if (materialId != null && !materialId.equals(0l)) {
				SMaterial sMaterial = sMaterialRepository.findOne(materialId);
				SMaterialPic sp;
				List<SMaterialPic> mps = sMaterialPicRepository.findBySMaterialId(sMaterial.getIdMaterial());
				if (mps != null && !mps.isEmpty()) {
					sp = mps.get(0);
				} else {
					sp = new SMaterialPic();
					sp.setSMaterial(sMaterial);
				}
				sp.setSPic(spic);
				sMaterialPicRepository.save(sp);
			}

		}
		return fileMeta;
	}

	
	@Transactional(readOnly = true)
	@RequestMapping(value="s/checkPno", method=RequestMethod.GET)
	public Valid checkPno(@RequestParam("pno") String pno,@RequestParam(required=false,value="idMaterial") Long idMaterial) throws Exception {
		Boolean returnVal= materialService.checkPno(pno,idMaterial);
		Valid valid = new Valid();
		valid.setValid(returnVal);
		return valid;
	}


	
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/uploadMaterialFile", method = RequestMethod.POST)
	public Valid uploadMaterialCoFile(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {

		Long idCompany = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		//物料编码	版本	描述	大类（原料，成品，半成品，消耗品，量具）	小类	品牌	重量	采购单位（个，千只，…）	库存单位（个，千只）	成本	状态（有效，无效）	最小起订量	最小包装量	安全库存	成本中心	检测周期	检测周期单位	备注	自动备注
		Valid v = new Valid();
		v.setValid(true);
		FileMeta fileMeta = new FileMeta();
		Map<Integer,String> errorMap = new LinkedHashMap<Integer,String>();
		Map<String,String> materialMap = new LinkedHashMap<String,String>();
		List<WSMaterial> wsMaterials = new ArrayList<WSMaterial>();
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
					WSMaterial wsMaterial = new WSMaterial();
					String pno = reader.get(0);
					if(pno==null||pno.isEmpty()||pno.length()>64)
					{
						flag =false;
						if(errorMap.containsKey(i))
						{
							errorMap.put(i, errorMap.get(i) + " 物料编码长度必须在0-64之间: " + pno);
						}
						else
						{
							errorMap.put(i,  "第" + i +"行出错，物料编码长度必须在0-64之间:  " + pno);
						}
					}
					else
					{
						Boolean returnVal= materialService.checkPno(pno,null);
						if(!returnVal||materialMap.containsKey(pno))
						{
							flag =false;
							if(errorMap.containsKey(i))
							{
								errorMap.put(i, errorMap.get(i) + " 物料编码已经存在: " + pno);
							}
							else
							{
								errorMap.put(i,  "第" + i +"行出错，物料编码已经存在:  " +pno );
							}
						}
					}
					wsMaterial.setPno(pno);
					materialMap.put(pno, pno);
					
					String rev = reader.get(1);
					wsMaterial.setRev(rev);
					
					String des = reader.get(2);
					wsMaterial.setDes(des);
					
					String mType = reader.get(3);
					if(mType==null||mType.isEmpty()||mType.length()>64)
					{
						flag =false;
						if(errorMap.containsKey(i))
						{
							errorMap.put(i, errorMap.get(i) + " 物料大类必须在2-64之间: " + mType);
						}
						else
						{
							errorMap.put(i,  "第" + i +"行出错，物料大类长度必须在2-64之间: " + mType);
						}
					}
					else
					{
						SMaterialTypeDic sMaterialTypeDic = sMaterialTypeDicRepository.findByName(mType);
						if(sMaterialTypeDic==null)
						{
							flag =false;
							if(errorMap.containsKey(i))
							{
								errorMap.put(i, errorMap.get(i) + " 无此物料大类: " + mType);
							}
							else
							{
								errorMap.put(i,  "第" + i +"行出错，无此物料大类:  " + mType);
							}
						}
						else
						{
							wsMaterial.setsMaterialTypeDicId(sMaterialTypeDic.getId());
						}
					}
				
					
					//小类
					String cat = reader.get(4);
					
					if(cat!=null&&!cat.isEmpty())
					{
						cat = cat.trim();
						SMaterialCategory sMaterialCategory = sMaterialCategoryRepository.findByIdCompanyAndName(idCompany, cat);
						if(sMaterialCategory==null)
						{
							flag =false;
							if(errorMap.containsKey(i))
							{
								errorMap.put(i, errorMap.get(i) + " 无此物料小类: " + cat +", ");
							}
							else
							{
								errorMap.put(i,  "第" + i +"行出错，无此物料小类: " + cat +", ");
							}
						}
						else
						{
							wsMaterial.setMaterialCategoryId(sMaterialCategory.getId());
						}
					}
					
					String brand = reader.get(5);
					wsMaterial.setBrand(brand);
					
				//	String weight = reader.get(6);
				//	wsMaterial.setWeight(weight);
					
					//采购单位
					String punit = reader.get(7);
					
					if(punit!=null&&!punit.isEmpty())
					{
						punit = punit.trim();
						SUnitDic sUnitDic  = sUnitDicRepository.findByName(punit);
						
						if(sUnitDic==null)
						{
							flag =false;
							if(errorMap.containsKey(i))
							{
								errorMap.put(i, errorMap.get(i) + " 无此采购单位: " + punit);
							}
							else
							{
								errorMap.put(i,  "第" + i +"行出错，无此采购单位: " +punit);
							}
						}
						else
						{
							wsMaterial.setsUnitDicByUnitPurId(sUnitDic.getId());
						}
					}
					
					//库存单位
					String iunit = reader.get(8);
					
					
					if(iunit!=null&&!iunit.isEmpty())
					{
						iunit = iunit.trim();
						SUnitDic sUnitDic  = sUnitDicRepository.findByName(iunit);
						
						if(sUnitDic==null)
						{
							flag =false;
							if(errorMap.containsKey(i))
							{
								errorMap.put(i, errorMap.get(i) + " 无此库存单位: " +iunit);
							}
							else
							{
								errorMap.put(i,  "第" + i +"行出错，无此库存单位:  " +iunit);
							}
						}
						else
						{
							wsMaterial.setsUnitDicByUnitInfId(sUnitDic.getId());
						}
					}
					
					String cost = reader.get(9);
					if(cost!=null&&!cost.isEmpty())
					{
						try
						{
							BigDecimal co=new BigDecimal(cost); 
						
							wsMaterial.setCost(co);
						
						}
						catch (Exception e)
						{

							
							flag =false;
							if(errorMap.containsKey(i))
							{
								errorMap.put(i, errorMap.get(i) + " 成本必须是数字: " +cost);
							}
							else
							{
								errorMap.put(i,  "第" + i +" 成本必须是数字: " +cost);
							}
						
						}
					}
					
					
					
					
					
					//wsMaterial.setCost(cost);
					
					String status = reader.get(10);
					
				//	System.out.println("status: " + status);
		
					if((!status.isEmpty())&&(!status.equals("有效")&&!status.equals("无效")))
					{
						
						flag =false;
						if(errorMap.containsKey(i))
						{
							errorMap.put(i, errorMap.get(i) + " 状态必须是有效或无效: " + status);
						}
						else
						{
							errorMap.put(i,  "第" + i +"行出错，状态必须是有效或无效: " +status);
						}
					}
					else
					{
						status =status.trim();
						if(status.equals("有效"))
						{
							wsMaterial.setsStatusDicId(23l);
						}
						else
						{
							wsMaterial.setsStatusDicId(24l);
						}
					}
					
					String moq = reader.get(11);
					if(moq!=null&&!moq.isEmpty())
					{
						try
						{
							wsMaterial.setMoq(Long.parseLong(moq));
						}
						catch (Exception e)
						{

							
							flag =false;
							if(errorMap.containsKey(i))
							{
								errorMap.put(i, errorMap.get(i) + " 最小起订量必须是整数: " +moq);
							}
							else
							{
								errorMap.put(i,  "第" + i +"行出错，最小起订量必须是整数: " +moq);
							}
						
						}
					}
					
					
					
					String mpq = reader.get(11);
					if(mpq!=null&&!mpq.isEmpty())
					{
						try
						{
							wsMaterial.setMpq(Long.parseLong(mpq));
						}
						catch (Exception e)
						{

							
							flag =false;
							if(errorMap.containsKey(i))
							{
								errorMap.put(i, errorMap.get(i) + " 最小起订量必须是整数: "+mpq);
							}
							else
							{
								errorMap.put(i,  "第" + i +"行出错，最小起订量必须是整数: " +mpq);
							}
						
						}
					}
					
					
					
					
					String saftyInv = reader.get(12);
					if(saftyInv!=null&&!saftyInv.isEmpty())
					{
						try
						{
							wsMaterial.setSafetyInv(Long.parseLong(saftyInv));
						
						}
						catch (Exception e)
						{

							
							flag =false;
							if(errorMap.containsKey(i))
							{
								errorMap.put(i, errorMap.get(i) + " 安全库存必须是整数: " +saftyInv);
							}
							else
							{
								errorMap.put(i,  "第" + i +"行出错，安全库存必须是整数: " +saftyInv);
							}
						
						}
					}
					
					//
					String costCenter = reader.get(13);
					
					String checkCycle = reader.get(14);
					if(checkCycle!=null&&!checkCycle.isEmpty())
					{
						try
						{
							wsMaterial.setCheckCycle(Long.parseLong(checkCycle));
						
						}
						catch (Exception e)
						{

							
							flag =false;
							if(errorMap.containsKey(i))
							{
								errorMap.put(i, errorMap.get(i) + " 检测周期必须是整数: " +checkCycle);
							}
							else
							{
								errorMap.put(i,  "第" + i +"行出错，检测周期必须是整数: " +checkCycle);
							}
						
						}
						
					}
					
				
					
					String checkCycleUnit = reader.get(15);
					if(checkCycleUnit!=null&&!checkCycleUnit.isEmpty())
					{
						try
						{
							wsMaterial.setCycleUnit(Long.parseLong(checkCycleUnit));
						
						}
						catch (Exception e)
						{

							
							flag =false;
							if(errorMap.containsKey(i))
							{
								errorMap.put(i, errorMap.get(i) + " 检测周期单位必须是整数： 1代表月， 2代表周， 3代表天: " + checkCycleUnit);
							}
							else
							{
								errorMap.put(i,  "第" + i +" 检测周期单位必须是整数： 1代表月， 2代表周， 3代表天: " +checkCycleUnit);
							}
						
						}
					}
					
					
		
					wsMaterial.setRemark(reader.get(16));
					wsMaterial.setAutoRemark(reader.get(17));
					
					
					wsMaterials.add(wsMaterial);
				}
				
			}
			String msg = "";
			for(String err: errorMap.values())
			{
				msg = msg+ err + "<br/> \r\n";
			}
			v.setMsg(msg);
			v.setValid(flag);
			//return v;
		} catch (Exception e) {
			e.printStackTrace();
			v.setMsg("上传物料失败，请检查文件格式：必须是csv类型文件，必须用utf-8编码。");
			v.setValid(false);
			//return v;
		}
		
		if(v.getValid())
		{
			
			for(WSMaterial wsMaterial: wsMaterials)
			{
				materialService.saveMaterial(wsMaterial);
			//	System.out.println(" save: material pno: " + wsMaterial.getPno());
			}
	
		}
		
		return v;
	
	}

	
	
	
	
	
	
	
}