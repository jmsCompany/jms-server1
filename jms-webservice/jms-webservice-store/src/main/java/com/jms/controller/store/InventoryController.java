package com.jms.controller.store;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.csvreader.CsvReader;
import com.jms.domain.db.Company;
import com.jms.domain.db.Document;
import com.jms.domain.db.SBin;
import com.jms.domain.db.SCompanyCo;
import com.jms.domain.db.SCurrencyType;
import com.jms.domain.db.SInventory;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMtf;
import com.jms.domain.db.SMtfMaterial;
import com.jms.domain.db.SMtfNo;
import com.jms.domain.db.SStk;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.s.WSInventory;
import com.jms.domain.ws.s.WSInventoryInfo;
import com.jms.domain.ws.s.WSMaterial;
import com.jms.domain.ws.s.WSSpo;
import com.jms.domain.ws.s.WSSpoMaterial;
import com.jms.file.FileMeta;
import com.jms.file.FileUploadService;
import com.jms.repositories.s.SBinRepository;
import com.jms.repositories.s.SInventoryRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SMtfMaterialRepository;
import com.jms.repositories.s.SMtfNoRepository;
import com.jms.repositories.s.SMtfRepository;
import com.jms.repositories.s.SMtfTypeDicRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.s.SStkRepository;
import com.jms.service.store.SInventoryService;
import com.jms.web.security.SecurityUtils;

import com.csvreader.CsvWriter;
@RestController
@Transactional(readOnly=true)
public class InventoryController {
	
	@Autowired private SInventoryService sInventoryService;
	private static final Logger logger = LogManager.getLogger(InventoryController.class
			.getCanonicalName());
	@Autowired private SecurityUtils securityUtils;
	@Autowired
	private SStkRepository sStkRepository;
	@Autowired
	private SBinRepository sBinRepository;
	@Autowired private FileUploadService fileUploadService;
	@Value("${filePath}")
	private String filePath;
	@Autowired
	private SInventoryRepository sInventoryRepository;
	@Autowired private SMaterialRepository sMaterialRepository;
	@Autowired private  SMtfTypeDicRepository sMtfTypeDicRepository;
	@Autowired private  SStatusDicRepository sStatusDicRepository;
	@Autowired private  SMtfRepository sMtfRepository;
	
	@Autowired private  SMtfMaterialRepository sMtfMaterialRepository;
	@Autowired private  SMtfNoRepository sMtfNoRepository;
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findInventorySummaryByMaterialAndStk", method=RequestMethod.GET)
	public List<WSInventoryInfo> findInventorySummaryByMaterialAndStk(@RequestParam(required=false,value="materialId") Long materialId,@RequestParam(required=false,value="stkId") Long stkId) throws Exception {
		return sInventoryService.findInventorySummaryByMaterialAndStk(materialId, stkId);
	}

	
	
	
	
	@RequestMapping(value = "/s/inventorySummary/export", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> inventorySummaryDownload( @RequestParam(required=false,value="materialId") Long materialId,
			@RequestParam(required=false,value="stkId") Long stkId) throws IOException
	{
		
//		CsvWriter x  = new CsvWriter();
//		x.w
		//List<WSInventory> is= sInventoryService.findInventoryDetailByMaterialAndStk(materialId, stkId);
		List<WSInventoryInfo> is= sInventoryService.findInventorySummaryByMaterialAndStk(materialId, stkId);
		String str="仓库,物料编码,物料版本,物料描述,数量\r\n";
		
	//	WSInventoryInfo w = is.get(i);
	//	String[] d = { w.getStkName(), w.getPno(), w.getRev(),w.getDes(), "" + w.getQty() };
		
		for(WSInventoryInfo w :is)
		{
			//System.out.println("ssss");
			String stk=(w.getStkName()==null)?"":w.getStkName();
			stk = rp(stk);
			String pno =(w.getPno()==null)?"":w.getPno();
			pno = rp(pno);
			String rev = (w.getRev()==null)?"":w.getRev();
			rev = rp(rev);
			String des = (w.getDes()==null)?"":w.getDes();
			des = rp(des);
			
			String qty =(w.getQty()==null)?"":""+w.getQty();
			qty=rp(qty);
			
			str =str +stk+"," +pno +","+ rev+","+des+","
					 +qty+"\r\n";
		}
		InputStream   in_withcode   =   new  ByteArrayInputStream(str.getBytes("UTF-8"));
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		return ResponseEntity
				.ok()
				.headers(headers)
				//.contentLength(str.length())
				.contentType(
						MediaType
								.parseMediaType("application/octet-stream"))
				.body(new InputStreamResource(in_withcode));
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/inventorySummary", method = RequestMethod.POST)
	public WSTableData inventorySummary(@RequestParam(required=false,value="materialId") Long materialId,
			@RequestParam(required=false,value="stkId") Long stkId, 
			@RequestParam Integer draw,
			@RequestParam Integer start,
			@RequestParam Integer length) throws Exception {

		List<WSInventoryInfo> is= sInventoryService.findInventorySummaryByMaterialAndStk(materialId, stkId);
		List<String[]> lst = new ArrayList<String[]>();
		int end = 0;
		if (is.size() < start + length)
			end = is.size();
		else
			end = start + length;
		for (int i = start; i < end; i++) {
			WSInventoryInfo w = is.get(i);
			
			String[] d = { w.getStkName(), w.getPno(), w.getRev(),w.getDes(), w.getInvUnit(), "" + w.getQty() };
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(is.size());
		t.setRecordsFiltered(is.size());
		t.setData(lst);
		return t;
	}
	
	
	
	
	
	
	@RequestMapping(value = "/s/inventoryDetail/export", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> inventoryDetailDownload( @RequestParam(required=false,value="materialId") Long materialId,
			@RequestParam(required=false,value="stkId") Long stkId) throws IOException
	{
		
		List<WSInventory> is= sInventoryService.findInventoryDetailByMaterialAndStk(materialId, stkId);
		String str="仓库,物料编码,物料版本,物料描述,货架,批次,数量\r\n";
		for(WSInventory w :is)
		{
			//System.out.println("ssss");
			
			
			String stk=(w.getStkName()==null)?"":w.getStkName();
			stk = rp(stk);
			String pno =(w.getPno()==null)?"":w.getPno();
			pno = rp(pno);
			String rev = (w.getRev()==null)?"":w.getRev();
			rev = rp(rev);
			String des = (w.getDes()==null)?"":w.getDes();
			des = rp(des);
			String bin =(w.getBinName()==null)?"":w.getBinName();
			bin = rp(bin);
			String qty =(w.getQty()==null)?"":""+w.getQty();
			qty=rp(qty);
			String lotno = (w.getLotNo()==null)?"":w.getLotNo();
			lotno = rp(lotno);
			str =str +stk+"," +pno +","+ rev+","+des+","+bin+","+lotno+","
					 +qty+"\r\n";
			
	
		}
		InputStream   in_withcode   =   new  ByteArrayInputStream(str.getBytes("UTF-8"));
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		return ResponseEntity
				.ok()
				.headers(headers)
				//.contentLength(str.length())
				.contentType(
						MediaType
								.parseMediaType("application/octet-stream"))
				.body(new InputStreamResource(in_withcode));
	}
	
	
	
	
	
	

	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/inventoryDetail", method = RequestMethod.POST)
	public WSTableData inventoryDetail(@RequestParam(required=false,value="materialId") Long materialId,
			@RequestParam(required=false,value="stkId") Long stkId, 
			@RequestParam Integer draw,
			@RequestParam Integer start,
			@RequestParam Integer length) throws Exception {

		List<WSInventory> is= sInventoryService.findInventoryDetailByMaterialAndStk(materialId, stkId);
		List<String[]> lst = new ArrayList<String[]>();
		int end = 0;
		if (is.size() < start + length)
			end = is.size();
		else
			end = start + length;
		for (int i = start; i < end; i++) {
			WSInventory w = is.get(i);
			String[] d = { w.getStkName(), w.getPno(), w.getRev(),w.getDes(), w.getBinName(),w.getLotNo(),""+w.getQty() };
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(is.size());
		t.setRecordsFiltered(is.size());
		t.setData(lst);
		return t;
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/findInventoryDetails", method = RequestMethod.GET)
	public List<WSInventory> findInventoryDetails(@RequestParam(required=false,value="materialId") Long materialId) throws Exception {

		return sInventoryService.findInventoryDetailByMaterialAndStk(materialId, null);
		
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/findInventoryDetailsByMaterialIdAndStkId", method = RequestMethod.GET)
	public List<WSInventory> findInventoryDetailsByMaterialIdAndStkId(@RequestParam(required=false,value="materialId") Long materialId,@RequestParam(required=false,value="stkId") Long stkId) throws Exception {

		return sInventoryService.findInventoryDetailByMaterialAndStk(materialId, stkId);
		
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/findInventoryDetailsObjs", method = RequestMethod.GET)
	public List<WSSelectObj> findInventoryDetailsObjs(@RequestParam(required=false,value="materialId") Long materialId) throws Exception {

		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		List<WSInventory> wsis = sInventoryService.findInventoryDetailByMaterialAndStk(materialId, null);
		for(WSInventory i: wsis)
		{
			WSSelectObj w = new WSSelectObj(i.getInventoryId(),i.getStkName()+"_"+i.getBinName()+", 库存: " +i.getQty());
			ws.add(w);
		}
		
		return ws;
		
	}
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/findInventoryDetailsObjsByMaterialIdAndStkId", method = RequestMethod.GET)
	public List<WSSelectObj> findInventoryDetailsObjsByMaterialIdAndStkId(@RequestParam(required=false,value="materialId") Long materialId,@RequestParam(required=false,value="stkId") Long stkId) throws Exception {

		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		//System.out.println("matId: "+materialId +", stkId: " + stkId);
		List<WSInventory> wsis = sInventoryService.findInventoryDetailByMaterialAndStk(materialId, stkId);
		for(WSInventory i: wsis)
		{
			//System.out.println("invId: "+i.getInventoryId() +", bin: " + i.getBinName());
			WSSelectObj w = new WSSelectObj(i.getInventoryId(),i.getStkName()+"_"+i.getBinName()+", 库存: " +i.getQty());
			ws.add(w);
		}
		
		return ws;
		
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/findInventoryDetailsByMaterialAndStk", method = RequestMethod.GET)
	public List<WSInventory> findInventoryDetailsByMaterialAndStk(@RequestParam(required=false,value="materialId") Long materialId,
	@RequestParam(required=false,value="stkId") Long stkId) throws Exception {

		return sInventoryService.findInventoryDetailByMaterialAndStk(materialId, stkId);
		
	}
	
	
	
	
	//仓库名称，货架名称，物料编码，版本，批号，盘点的库存数量，备注。
	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/uploadInvFile", method = RequestMethod.POST)
	public Valid uploadInvFile(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {

	    Company c=	securityUtils.getCurrentDBUser().getCompany();
		Long myCompanyId = c.getIdCompany();
		Valid v = new Valid();
		v.setValid(true);

		Map<Integer,String> errorMap = new LinkedHashMap<Integer,String>();
		Map<Long,List<WSInventory>> smtfMap = new LinkedHashMap<Long,List<WSInventory>>();
		
		List<WSInventory> invList = new ArrayList<WSInventory>();
		FileMeta fileMeta = new FileMeta();
		
		 boolean flag = true;
		SBin ccaBin = sBinRepository.getByCompanyIdAndBinName(myCompanyId, "CCA");
		
		if(ccaBin==null)
		{
			flag =false;
			errorMap.put(0,  "无盘点仓货架，请创建货架，名称为： CCA");
		}
		try {
			
			if (request.getFileNames().hasNext()) {
				fileMeta = fileUploadService.upload(request, response, true);
				FileInputStream inputStream = new FileInputStream(filePath + fileMeta.getFileName());
				CsvReader reader = new CsvReader(inputStream, ',', Charset.forName("UTF-8"));
				reader.readHeaders();
				Integer i = 1;
				while (reader.readRecord()) {
					i++;
					WSInventory wsInventory =  new WSInventory();
					
					//仓库名称，货架名称，物料编码，版本，批号，盘点的库存数量，备注。
					String stkName = reader.get(0);// 仓库名称
					stkName =stkName.trim();
					SStk stk = sStkRepository.findActiveStkByIdCompanyAndStkName(myCompanyId, stkName);
					if(stk==null)
					{
						flag =false;
						if(errorMap.containsKey(i))
						{
							errorMap.put(i, errorMap.get(i) + "  无此仓库： " + stkName);
						}
						else
						{
							errorMap.put(i,  "第" + i +"行出错，无此仓库：  " +stkName);
						}
					}
					else
					{
						if(smtfMap.containsKey(stk.getId()))
						{
							invList = smtfMap.get(stk.getId());
							invList.add(wsInventory);
						}
						else
						{
							invList = new ArrayList<WSInventory>();
							wsInventory = new WSInventory();
							invList.add(wsInventory);
							smtfMap.put(stk.getId(), invList);
						
						}
					 }
					//仓库名称，货架名称，物料编码，版本，批号，盘点的库存数量，备注。
				String binName = reader.get(1); // 货架名称
				binName =binName.trim();
				SBin bin = sBinRepository.getByCompanyIdAndBinName(myCompanyId, binName);
				if(bin==null)
				{
		             flag =false;
					 if(errorMap.containsKey(i))
						{
								errorMap.put(i, errorMap.get(i) + " 无此货架名称: " + binName);
						}
						else
						{
								errorMap.put(i,  "第" + i +"行出错，无此货架名称:  " + binName);
						}
				 }
				else
				{
					//currentPo.setsCompanyCoId(sCompanyCo.getId());
					wsInventory.setBinId(bin.getIdBin());
					//wsInventory.s
					
				}

				//仓库名称，货架名称，物料编码，版本，批号，盘点的库存数量，备注。
				String pno = reader.get(2);
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
					   wsInventory.setIdMaterial(mat.getIdMaterial());
				   }
				   }
			
				
				
				
				//仓库名称，货架名称，物料编码，版本，批号，盘点的库存数量，备注。
				String lotno = reader.get(4);
				lotno = lotno.trim();
				if(!lotno.isEmpty())
				{
					wsInventory.setLotNo(lotno);
				}
				
				
				//仓库名称，货架名称，物料编码，版本，批号，盘点的库存数量，备注。
				try{
					String s  = reader.get(5);
					s = s.trim();
					Long newqty = Long.parseLong(s); // 数量
					
					wsInventory.setNewQty(newqty);
				}
				catch (Exception e)
				{

					e.printStackTrace();
					flag =false;
					if(errorMap.containsKey(i))
					{
						errorMap.put(i, errorMap.get(i) + "  目标库存数量输入错误： " + reader.get(5));
					}
					else
					{
						errorMap.put(i,  "第" + i +"行出错，目标库存数量输入错误：：  " +reader.get(5));
					}
			   
				}
				
				String remark = reader.get(6);//
				wsInventory.setRemark(remark);
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
			v.setMsg("上传库存失败！");
			v.setValid(false);
			return v;
		}
		if(v.getValid())
		{
			
			
			for(Long idStk: smtfMap.keySet())
			{
				SMtf smtf = new SMtf();
				smtf.setCompany(c);
				smtf.setCreationTime(new Date());
				smtf.setDateMt(new Date());
				smtf.setSMtfTypeDic(sMtfTypeDicRepository.findOne(3l)); //手动流转
				
				smtf.setSStatusDic(sStatusDicRepository.findOne(5l));//接收
				smtf.setSStkByFromStk(ccaBin.getSStk());
				smtf.setSStkByToStk(sStkRepository.findOne(idStk));
				
				
				
				SMtfNo smtfNo = sMtfNoRepository.getByCompanyIdAndType(myCompanyId, 3l);
				if (smtfNo == null) {
					smtfNo = new SMtfNo();
					smtfNo.setDes("手动流转");
					smtfNo.setPrefix("AA");
					smtfNo.setCompanyId(myCompanyId);
					smtfNo.setType(3l);
					smtfNo.setCurrentVal(1l);
					smtfNo = sMtfNoRepository.save(smtfNo);
				}
				long currentVal = smtfNo.getCurrentVal() + 1;
				smtfNo.setCurrentVal(currentVal);
				sMtfNoRepository.save(smtfNo);
				String mtNo = smtfNo.getPrefix() + String.format("%08d", currentVal);
				smtf.setMtNo(mtNo);
				
				
				
				smtf = sMtfRepository.save(smtf);
				//sMtfMaterialRepository
				System.out.println("转移物料仓库ID： " + idStk);
				for(WSInventory i :smtfMap.get(idStk) )
				{
					
					List<SInventory> invLists = new ArrayList<SInventory>();
					
					List<SInventory> ccaLists = new ArrayList<SInventory>();
					if(i.getLotNo()==null)
					{
					    invLists = sInventoryRepository.findByMaterialIdAndBinId(i.getIdMaterial(), i.getBinId());
					    ccaLists= sInventoryRepository.findByMaterialIdAndBinId(i.getIdMaterial(), ccaBin.getIdBin());
					}else
					{
						SInventory	sInventory = sInventoryRepository.findByMaterialIdAndBinIdAndLotNo(i.getIdMaterial(), i.getBinId(),i.getLotNo());
					    if(sInventory!=null)
					    {
					    	invLists.add(sInventory);
					    }
					    
					    
					    SInventory	ccaInventory = sInventoryRepository.findByMaterialIdAndBinIdAndLotNo(i.getIdMaterial(), ccaBin.getIdBin(),i.getLotNo());
					    if(ccaInventory!=null)
					    {
					    	ccaLists.add(ccaInventory);
					    }
					}
					Long qty = 0l; //库存数量
					for(SInventory s: invLists)
					{
						qty = qty + s.getQty();
					}
					
					Long numforT= i.getNewQty() - qty ; //转移物料数量,如果大于0则盘盈，否则盘亏,如果盘亏，肯定之前有库存，invLists不能为空
					System.out.println("目标库存: " +  i.getNewQty() +", 目前库存： " + qty  +", 差额： " + numforT);
					
					SBin sbin = sBinRepository.findOne(i.getBinId());
					SMaterial	sMaterial = sMaterialRepository.findOne(i.getIdMaterial());
					if(numforT>0)
					{
						if(invLists.isEmpty())
						{
							SInventory	sInventory =new SInventory();
							sInventory.setCreationTime(new Date());
							sInventory.setLotNo(i.getLotNo());
							sInventory.setQty(numforT);
							sInventory.setSBin(sbin);
							sInventory.setSMaterial(sMaterial);
							//sInventory.s
							sInventoryRepository.save(sInventory);
						}
						else
						{
							SInventory	sInventory  = invLists.get(0);
							sInventory.setQty(sInventory.getQty()+numforT);
							sInventoryRepository.save(sInventory);
						}
					}
					else
					{
						
						long  t = -numforT;
						System.out.println(" 盘亏了， 数量差： "  +t );
						for(SInventory s: invLists)
						{
							if( s.getQty()>=t)
							{
								System.out.println(" 减去库存： "  +t );
								s.setQty(s.getQty()-t);
								sInventoryRepository.save(s);
								break;
							}
							else
							{
								t = t- s.getQty();
								s.setQty(0l);
								System.out.println(" 减去库存： "  +s.getQty() );
								sInventoryRepository.save(s);
							}
							
						}
					}
					
					
					if(ccaLists.isEmpty())
					{

						SInventory	ccaInventory =new SInventory();
						ccaInventory.setCreationTime(new Date());
						ccaInventory.setLotNo(i.getLotNo());
						ccaInventory.setQty(-numforT);
						ccaInventory.setSBin(ccaBin);
						ccaInventory.setSMaterial(sMaterial);
						sInventoryRepository.save(ccaInventory);
					}
					else
					{
						SInventory	ccaInventory = ccaLists.get(0);
						ccaInventory.setQty(ccaInventory.getQty()-numforT);
						sInventoryRepository.save(ccaInventory);
					}
					
					
					
					
					//sInventoryRepository.f
					SMtfMaterial sMtfMaterial = new SMtfMaterial();
					//sMtfMaterial.setIdMtfMaterial(i.getIdMaterial());
					sMtfMaterial.setLotNo(i.getLotNo());
					sMtfMaterial.setQty(numforT);
					sMtfMaterial.setQty3417(numforT);
					sMtfMaterial.setRemark(i.getRemark());
					sMtfMaterial.setSBinByFromBin(ccaBin);
					sMtfMaterial.setSBinByToBin(sbin);
					sMtfMaterial.setSMaterial(sMaterial);
					sMtfMaterial.setSMtf(smtf);
					sMtfMaterialRepository.save(sMtfMaterial);
					System.out.println("库存 binId： " + i.getBinId() + ", 物料Id: " + i.getIdMaterial() +", 盘点数量：" + i.getNewQty() +", 备注： " + i.getRemark());
				}
			}
	
		}
		
		return v;
	}

	
	private String rp(String s)
	{
		s =s.trim();
		s =s.replaceAll("\r", "");
		s =s.replaceAll("\n", "");
		if(s.contains(" ")||s.contains(",")||s.contains("	"))
			s = "\""+s+"\"";
			
		return s;
	}
	
	
}