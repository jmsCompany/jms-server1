package com.jms.controller.production;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
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
import com.jms.domain.db.PBom;
import com.jms.domain.db.PBomLabel;
import com.jms.domain.db.PRoutineD;
import com.jms.domain.db.PWorkCenter;
import com.jms.domain.db.SBin;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMaterialCategory;
import com.jms.domain.db.SMaterialTypeDic;
import com.jms.domain.db.SUnitDic;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.p.WSPBom;
import com.jms.domain.ws.p.WSPBomItem;
import com.jms.domain.ws.s.WSBomComs;
import com.jms.domain.ws.s.WSMaterial;
import com.jms.file.FileMeta;
import com.jms.file.FileUploadService;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.f.FCostCenterRepository;
import com.jms.repositories.p.PBomLabelRepository;
import com.jms.repositories.p.PBomRepository;
import com.jms.repositories.p.PRoutineDRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.repositories.s.SBinRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.service.production.BomLabelService;
import com.jms.service.production.BomService;
import com.jms.web.security.SecurityUtils;

@RestController
@Transactional(readOnly = true)
public class BomController {
	
	@Autowired
	private BomService bomService;
	@Autowired
	private BomLabelService bomLabelService;
	@Autowired
	private PWoRepository pWoRepository;
	@Autowired
	private PBomLabelRepository pBomLabelRepository;
	@Autowired
	private SecurityUtils securityUtils;
	@Autowired
	private PBomRepository pBomRepository;
	@Autowired
	private SMaterialRepository sMaterialRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private PStatusDicRepository pStatusDicRepository;
	@Autowired
	private	PRoutineDRepository pRoutineDRepository;
	
	@Autowired
	private FCostCenterRepository fCostCenterRepository;
	
	
	@Autowired
	private PWorkCenterRepository pWorkCenterRepository;
	
    @Value("${filePath}") private String filePath;
	@Autowired private FileUploadService fileUploadService;
	@Autowired private SBinRepository sBinRepository;
	
	private static final Logger logger = LogManager.getLogger(BomController.class.getCanonicalName());

	@Transactional(readOnly = false)
	@RequestMapping(value = "/p/saveBom", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public WSPBom saveWSPBom(@RequestBody WSPBom wsPBom) throws Exception {
		return bomLabelService.savePBomLabel(wsPBom);
	}

	@Transactional(readOnly = false)
	@RequestMapping(value = "/p/shareBom", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Valid shareWSPBom(@RequestBody WSBomComs wsBomComs) {
		// return bomLabelService.savePBomLabel(wsPBom);
		Valid v = new Valid();

		Long idBom = wsBomComs.getIdBom();
		// PBomLabel pBomLabel = pBomLabelRepository.findOne(idBom);
		List<PBom> boms = pBomRepository.findByBomLabelId(idBom);
		if (!boms.isEmpty()) {
			PBom parent = boms.get(0);
			logger.debug("parent bom is: " + parent.getIdBom());
			SMaterial product = parent.getSMaterial();
			logger.debug("product: " + product.getPno());
			logger.debug("idBom: " + idBom);
			for (WSSelectObj o : wsBomComs.getComs()) {
				Long idComCompany = o.getId();
				logger.debug("company Id: " + idComCompany);
				Company sharedCompany = companyRepository.findOne(idComCompany);
				SMaterial sm = sMaterialRepository.getByCompanyIdAndPno(idComCompany, product.getPno());
				if (sm == null) // 该公司无该物料
				{
					sm = new SMaterial();
					sm.setCompany(sharedCompany);
					sm.setCycleUnit(product.getCycleUnit());
					sm.setDes(product.getDes());
					sm.setPno(product.getPno());
					sm.setBrand(product.getBrand());
					sm.setRemark(product.getRemark());
					sm.setRev(product.getRev());
					sm.setWeight(product.getWeight());
					sm.setSUnitDicByUnitInf(product.getSUnitDicByUnitInf());
					sm.setSUnitDicByUnitPur(product.getSUnitDicByUnitPur());
					sm.setSStatusDic(product.getSStatusDic());
					sm.setSMaterialCategory(product.getSMaterialCategory());
					sm.setMoq(product.getMoq());
					sm.setMpq(product.getMpq());
					sm.setSMaterialTypeDic(product.getSMaterialTypeDic());
					sm = sMaterialRepository.save(sm);
				}
				else
				{
					sm.setMoq(product.getMoq());
					sm.setMpq(product.getMpq());
					sm.setSMaterialTypeDic(product.getSMaterialTypeDic());
					sm = sMaterialRepository.save(sm);
				}
				PBom p = pBomRepository.findProductByMaterialId(sm.getIdMaterial());
				if (p != null) // 已有BOM
				{
					logger.debug("company Id: " + idComCompany + "已有Bom，要删除已有bom");
					// PBomLabel myBomLabel =
					// pBomLabelRepository.findOne(p.getPBomLabel().getIdBomLabel());

					pBomRepository.deleteByBomLabelIdAnPidIdNotNull(p.getPBomLabel().getIdBomLabel());
					pBomRepository.deleteByBomLabelIdAnPidIdNull(p.getPBomLabel().getIdBomLabel());
					pBomLabelRepository.delete(p.getPBomLabel().getIdBomLabel());
					// myBomLabel.getPBoms().clear();
				}
				PBomLabel newBomLabel = new PBomLabel();
				newBomLabel.setCreationTime(new Date());
				newBomLabel.setCompany(sharedCompany);
				newBomLabel.setPStatusDic(pStatusDicRepository.findOne(3l));
				newBomLabel = pBomLabelRepository.save(newBomLabel);
				PBom parentPbom = new PBom();
				parentPbom.setLvl(parent.getLvl());
				parentPbom.setOrderBy(parent.getOrderBy());
				parentPbom.setQpu(parent.getQpu());
				parentPbom.setWastage(parent.getWastage());
				parentPbom.setPBomLabel(newBomLabel);
				parentPbom.setSMaterial(sm);
				parentPbom = pBomRepository.save(parentPbom);
				for (PBom pbom : boms) {

					if (pbom.getPBom() != null) {
						PBom newPbom = new PBom();
						newPbom.setPBom(parentPbom);
						newPbom.setOrderBy(pbom.getOrderBy());
						newPbom.setQpu(pbom.getQpu());
						newPbom.setWastage(pbom.getWastage());
						newPbom.setPBomLabel(newBomLabel);
						SMaterial s = pbom.getSMaterial();
						SMaterial sm1 = sMaterialRepository.getByCompanyIdAndPno(idComCompany, s.getPno());
						if (sm1 != null) {
							newPbom.setSMaterial(sm1);
							sm1.setMoq(s.getMoq());
							sm1.setMpq(s.getMpq());
							sm1.setSMaterialTypeDic(s.getSMaterialTypeDic());
							sMaterialRepository.save(sm1);
						} else {
							SMaterial sn = new SMaterial();

							sn.setCompany(sharedCompany);
							sn.setCycleUnit(s.getCycleUnit());
							sn.setDes(s.getDes());
							sn.setPno(s.getPno());
							sn.setBrand(s.getBrand());
							sn.setRemark(s.getRemark());
							sn.setRev(s.getRev());
							sn.setWeight(s.getWeight());
							sn.setSUnitDicByUnitInf(s.getSUnitDicByUnitInf());
							sn.setSUnitDicByUnitPur(s.getSUnitDicByUnitPur());
							sn.setSStatusDic(s.getSStatusDic());
							sn.setMoq(s.getMoq());
							sn.setMpq(s.getMpq());
							sn.setSMaterialTypeDic(s.getSMaterialTypeDic());
							sn = sMaterialRepository.save(sn);
							sn.setSMaterialCategory(s.getSMaterialCategory());
							newPbom.setSMaterial(sn);

						}

						newPbom = pBomRepository.save(newPbom);
					}

				}
			}
		}
		v.setValid(true);
		v.setMsg("Bom are shared");
		return v;

	}

	@Transactional(readOnly = false)
	@RequestMapping(value = "/p/updateBomStatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public WSPBom updateBomStatus(@RequestBody WSPBom wsPBom) throws Exception {
		return bomLabelService.updateBomStatus(wsPBom);
	}

	@Transactional(readOnly = false)
	@RequestMapping(value = "/p/updateBomItem", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public WSPBomItem updateBomItem(@RequestBody WSPBomItem wsPBomItem) throws Exception {
		return bomService.updateWSPBomItem(wsPBomItem);
	}

	@Transactional(readOnly = false)
	@RequestMapping(value = "/p/deleteBom", method = RequestMethod.GET)
	public Valid deleteBom(@RequestParam("bomLabelId") Long bomLabelId) {
		System.out.println("delete bomLabelId: " + bomLabelId);
		return bomLabelService.deletePWSPBom(bomLabelId);

	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/p/findBom", method = RequestMethod.GET)
	public WSPBom findWo(@RequestParam("bomLabelId") Long bomLabelId) throws Exception {
		return bomLabelService.findWSPBom(bomLabelId, null);

	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/p/findBomByProductId", method = RequestMethod.GET)
	public WSPBom findBomByProductId(@RequestParam("productId") Long productId) throws Exception {
		PBom pBom = pBomRepository.findProductByMaterialId(productId);
		return bomLabelService.findWSPBom(pBom.getPBomLabel().getIdBomLabel(), null);
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/p/findBomByProductIdAndComComannyId", method = RequestMethod.GET)
	public WSPBom findBomByProductIdAndComComannyId(@RequestParam("productId") Long productId,
			@RequestParam(value="comCompanyId",required=false) Long comCompanyId) throws Exception {
		PBom pBom = pBomRepository.findProductByMaterialId(productId);
		// Company comCompany = companyRepository.findOne(comCompanyId);
		if(pBom==null)
		{
			//System.out.println("bom is empty ");
			return new WSPBom();
		}
		if(pBom.getPBomLabel()==null)
		{
			//System.out.println("bom label is empty ");
			return new WSPBom();
		}
		return bomLabelService.findWSPBom(pBom.getPBomLabel().getIdBomLabel(), comCompanyId);
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/p/findBomItem", method = RequestMethod.GET)
	public WSPBomItem findBomItem(@RequestParam("bomId") Long bomId) throws Exception {
		return bomService.findWSPBomItem(bomId);

	}

	@Transactional(readOnly = false)
	@RequestMapping(value = "/p/saveBomItem", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public WSPBomItem saveWSPBomItem(@RequestBody WSPBomItem wsPBomItem) throws Exception {
		return bomService.saveWSPBomItem(wsPBomItem);
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/p/getBomList", method = RequestMethod.POST)
	public WSTableData getBomList(@RequestParam Integer draw, @RequestParam Integer start, @RequestParam Integer length)
			throws Exception {

		List<WSPBom> wsPBoms = bomLabelService.findWSPBomList();

		List<String[]> lst = new ArrayList<String[]>();
		int end = 0;
		if (wsPBoms.size() < start + length)
			end = wsPBoms.size();
		else
			end = start + length;
		for (int i = start; i < end; i++) {
			WSPBom w = wsPBoms.get(i);
			String[] d = { w.getPno(), w.getRev(), w.getMaterial(), w.getCreationTime().toString(), "" + w.getCreator(),
					"" + w.getStatus(), "" + w.getIdBomLabel() };
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(wsPBoms.size());
		t.setRecordsFiltered(wsPBoms.size());
		t.setData(lst);
		return t;
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/uploadBomFile", method = RequestMethod.POST)
	public Valid uploadBomFile(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {

		Long idCompany = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		// 产品编码	产品版本	物料编码	物料版本 工序编号（010,020..) 工作中心	级别	排序	单位用料	损耗
		
		
		//WSPBom 
		Valid v = new Valid();
		v.setValid(true);
		FileMeta fileMeta = new FileMeta();
		Map<Integer,String> errorMap = new LinkedHashMap<Integer,String>();
		Map<String,WSPBom> bomLabelMap = new LinkedHashMap<String,WSPBom>();
		//Map<String,String> bomLabelMap = new LinkedHashMap<String,String>();
		//List<WSMaterial> wsMaterials = new ArrayList<WSMaterial>();
		try {
			boolean flag = true;
			if (request.getFileNames().hasNext()) {
				fileMeta = fileUploadService.upload(request, response, true);
				FileInputStream inputStream = new FileInputStream(filePath + fileMeta.getFileName());
				CsvReader reader = new CsvReader(inputStream, ',', Charset.forName("UTF-8"));
				reader.readHeaders();
			
				Integer i = 1;
				String currentProduct ="";
				Long currentProductId =0l;
			    Map<String,WSPBomItem> currentBomItems = new LinkedHashMap<String,WSPBomItem>(0);
				while (reader.readRecord()) {
					i++;
				//	WSMaterial wsMaterial = new WSMaterial();
					String pno = reader.get(0);
					if(pno==null||pno.isEmpty())
					{
						flag =false;
						if(errorMap.containsKey(i))
						{
							errorMap.put(i, errorMap.get(i) + "  产品编码长度必须在0-64之间： " + pno);
						}
						else
						{
							errorMap.put(i,  "第" + i +"行出错，产品编码长度必须在0-64之间：  " +pno);
						}
					}
					else
					{
						//成品
					   SMaterial mat =	sMaterialRepository.getByCompanyIdAndPno(idCompany, pno);
					   if(mat ==null)
					   {
							flag =false;
							if(errorMap.containsKey(i))
							{
								errorMap.put(i, errorMap.get(i) + "  无此产品编码： " + pno);
							}
							else
							{
								errorMap.put(i,  "第" + i +"行出错，无此产品编码：  " +pno);
							}
					   }
					   else
					   {
							List<PBom> pBoms= pBomRepository.findProductsByMaterialId(mat.getIdMaterial());
							if(!pBoms.isEmpty())
							{
								flag =false;
								if(errorMap.containsKey(i))
								{
									errorMap.put(i, errorMap.get(i) + "  该产品已有物料清单： " +mat.getPno());
								}
								else
								{
									errorMap.put(i,  "第" + i +"行出错，该产品已有物料清单： " +mat.getPno());
								}
							}
							else
							{
								  if( !bomLabelMap.containsKey(mat.getPno()))
								  {
									  WSPBom w = new WSPBom();
									  w.setCompanyId(idCompany);
									  w.setCreationTime(new Date());
									  w.setMaterialId(mat.getIdMaterial());
									  w.setStatusId(3l); //有效 4 无效
									  currentBomItems = new LinkedHashMap<String,WSPBomItem>(0);
									  w.setBomItems(currentBomItems);
									  bomLabelMap.put(mat.getPno(), w);
									  currentProduct = mat.getPno();
									  currentProductId = mat.getIdMaterial();
									  System.out.println("新增");
								  }
								  
							}
						   
						   
					
					   }
					}
					String mpno = reader.get(2);
					if(mpno==null||mpno.isEmpty())
					{
						flag =false;
						if(errorMap.containsKey(i))
						{
							errorMap.put(i, errorMap.get(i) + "  物料编码长度必须在0-64之间:  " + mpno);
						}
						
						{
							errorMap.put(i,  "第" + i +"行出错，物料编码长度必须在0-64之间:  " +mpno);
						}
					}
					else
					{
						 SMaterial matr =	sMaterialRepository.getByCompanyIdAndPno(idCompany, mpno);
						 if(matr ==null)
						   {
								flag =false;
								if(errorMap.containsKey(i))
								{
									errorMap.put(i, errorMap.get(i) + "  无此物料编码: " +mpno);
								}
								else
								{
									errorMap.put(i,  "第" + i +"行出错，无此物料编码:  " +mpno);
								}
						   }
						   else
						   {
							     WSPBom wsPom =   bomLabelMap.get(currentProduct);
							     if(wsPom !=null)
							     {
							    	 Map<String, WSPBomItem> bomItemMap =  wsPom.getBomItems();
							    	 WSPBomItem w = new WSPBomItem();
							    	 w.setMaterialId(matr.getIdMaterial());
							    	
							    	 //工序
							    	 String routeNo = reader.get(4);
							    	 if(routeNo!=null&&!routeNo.isEmpty())
							    	 {
							    		 PRoutineD pRoutineD =  pRoutineDRepository.findByMaterialIdAndRouteNo(currentProductId, routeNo);
							    		 if(pRoutineD==null)
							    		 {

												flag =false;
												if(errorMap.containsKey(i))
												{
													errorMap.put(i, errorMap.get(i) + " 无此工序：" +routeNo);
												}
												else
												{
													errorMap.put(i,  "第" + i +"行出错，无此工序： " +routeNo);
												}
											
											
							    		 }
							    		 else
							    		 {
							    			 w.setIdRoutineD(pRoutineD.getIdRoutineD());
							    		 }
							    	 }
							    	
							    	 //工作中心
							    	 String workCenter = reader.get(5);
							    	 workCenter = workCenter.trim();
							    	 if(workCenter!=null&&!workCenter.isEmpty())
							    	 {
							    	//PWorkCenter pWorkCenter =  pWorkCenterRepository.getByCompanyIdAndWorkCenter(idCompany, workCenter);
							    		SBin pWorkCenter = sBinRepository.getByCompanyIdAndStkTypeAndBinName(idCompany,2l,workCenter);
							    		if(pWorkCenter==null)
							    		 {

												flag =false;
												if(errorMap.containsKey(i))
												{
													errorMap.put(i, errorMap.get(i) + " 无此工作中心：" +workCenter);
												}
												else
												{
													errorMap.put(i,  "第" + i +"行出错，无此工作中心： " +workCenter);
												}
											
											
							    		 }
							    		 else
							    		 {
							    			 w.setWorkCenterId(pWorkCenter.getIdBin());
							    		 }
							    	 }
							    	
							    	//级别
							    	 String lvl = reader.get(6);
							    	 
							    	 if(lvl!=null&&!lvl.isEmpty())
										{
											try
											{
												w.setLvl(Long.parseLong(lvl));
												
											}
											catch (Exception e)
											{

												
												flag =false;
												if(errorMap.containsKey(i))
												{
													errorMap.put(i, errorMap.get(i) + " 级别必须是数字: " + lvl);
												}
												else
												{
													errorMap.put(i,  "第" + i +"行出错，级别必须是数字: "+lvl);
												}
											
											}
										}
									
							    	 
							    	//排序
							    	 String seq = reader.get(7);
							    	 if(seq!=null&&!seq.isEmpty())
										{
											try
											{
												w.setOrderBy(Long.parseLong(seq));
												
											}
											catch (Exception e)
											{

												
												flag =false;
												if(errorMap.containsKey(i))
												{
													errorMap.put(i, errorMap.get(i) + " 单位用料必须是数字: " +seq);
												}
												else
												{
													errorMap.put(i,  "第" + i +"行出错，单位用料必须是数字: " +seq);
												}
											
											}
										}
										
							    	 
							    	   	//单位用料
							    	 String pqu = reader.get(8);
							    		if(pqu!=null&&!pqu.isEmpty())
										{
											try
											{
												
												w.setQpu(Float.parseFloat(pqu));
											}
											catch (Exception e)
											{

												
												flag =false;
												if(errorMap.containsKey(i))
												{
													errorMap.put(i, errorMap.get(i) + " 单位用料必须是数字: " +pqu);
												}
												else
												{
													errorMap.put(i,  "第" + i +"行出错，单位用料必须是数字: " +pqu);
												}
											
											}
										}
										
							    	 
							    	   	//损耗
							    	 String wastage = reader.get(9);
							    	 
							    	 if(wastage!=null&&!wastage.isEmpty())
										{
											try
											{
												
												w.setWastage(Float.parseFloat(wastage));
											}
											catch (Exception e)
											{

												
												flag =false;
												if(errorMap.containsKey(i))
												{
													errorMap.put(i, errorMap.get(i) + " 单位损耗必须是数字: " +wastage);
												}
												else
												{
													errorMap.put(i,  "第" + i +"行出错，单位损耗必须是数字: "+wastage);
												}
											
											}
										}
										
							    	 
							    	 bomItemMap.put(""+matr.getIdMaterial(), w);
							    	 wsPom.setBomItems(bomItemMap);
							    	 bomLabelMap.put(currentProduct, wsPom);
							    	 
							    	 
							    	 System.out.println("bom size: " + bomLabelMap.size() + ", current product: " + currentProduct +", item size: " + bomItemMap.size() + "," +", 物料ID： " +matr.getIdMaterial() );
							    	
							     }
						   }
					
					
					}
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
			v.setMsg("上传BOM 失败，请检查文件格式：必须是csv类型文件，必须用utf-8编码。");
			v.setValid(false);
			//return v;
		}
		
		if(v.getValid())
		{
			
			for(WSPBom wsPBom: bomLabelMap.values())
			{
				 bomLabelService.savePBomLabel(wsPBom);
				//materialService.saveMaterial(wsMaterial);
				System.out.println(" save: wsPom  pno: " + wsPBom.getMaterialId());
			}
	
		}
		
		return v;
	
	}

	
	
	
	
	
	
}