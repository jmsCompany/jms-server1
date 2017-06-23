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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.csvreader.CsvReader;
import com.jms.domain.db.SCompanyCo;
import com.jms.domain.db.SCountryDic;
import com.jms.domain.db.SLevelDic;
import com.jms.domain.db.SStatusDic;
import com.jms.domain.db.STermDic;
import com.jms.domain.db.STypeDic;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.s.WSCompanyCo;
import com.jms.domain.ws.s.WSSpo;
import com.jms.domain.ws.s.WSSpoMaterial;
import com.jms.file.FileMeta;
import com.jms.file.FileUploadService;
import com.jms.repositories.s.SCompanyCoRepository;
import com.jms.repositories.s.SCountryDicRepository;
import com.jms.repositories.s.SLevelDicRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.s.SStermDicRepository;
import com.jms.repositories.s.STypeDicRepository;
import com.jms.service.store.CompanyCoService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class CompanyCoController {
	
	@Autowired private CompanyCoService companyCoService;
	@Autowired private SecurityUtils securityUtils;
	@Autowired private SCountryDicRepository sCountryDicRepository;
	@Autowired private STypeDicRepository sTypeDicRepository;
	@Autowired private SStermDicRepository sStermDicRepository;
	@Autowired private SLevelDicRepository sLevelDicRepository;
	@Autowired private SStatusDicRepository	sStatusDicRepository;
	@Autowired private SCompanyCoRepository sCompanyCoRepository;
	@Autowired private FileUploadService fileUploadService;
	@Value("${filePath}")
	private String filePath;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/saveCompanyCo", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSCompanyCo saveCompanyCo(@RequestBody WSCompanyCo wSCompanyCo) throws Exception {
	
		return companyCoService.saveWSCompanyCo(wSCompanyCo);
	}
	
//	@Transactional(readOnly = false)
//	@RequestMapping(value="/s/auditCompanyCo", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
//	public WSCompanyCo auditCompanyCo(@RequestBody WSCompanyCo wSCompanyCo) throws Exception {
//	
//		SCompanyCo sCompanyCo = sCompanyCoRepository.getOne(wSCompanyCo.getIdParent());
//		sCompanyCo.setAuditBy(""+securityUtils.getCurrentDBUser().getIdUser());
//		sCompanyCo.setAuditStatus(wSCompanyCo.getAuditStatusId());
//		sCompanyCoRepository.save(sCompanyCo);
//		return companyCoService.saveWSCompanyCo(wSCompanyCo);
//	}
//	
//	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/deleteCompanyCo", method=RequestMethod.GET)
	public Valid deletecCompanyCo(@RequestParam("companyCoId") Long companyCoId) {
		return companyCoService.deleteCompanyCo(companyCoId);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findCompanyCo", method=RequestMethod.GET)
	public WSCompanyCo findCompanyCo(@RequestParam("companyCoId") Long companyCoId) {
		return companyCoService.findCompanyCo(companyCoId);
		
	}
	
	//国家
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/countries", method=RequestMethod.GET)
	public List<WSSelectObj> findCountries() {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		for(SCountryDic c : sCountryDicRepository.findAll())
		{
			wso.add(new WSSelectObj(c.getId(),c.getName()));
		}
		return wso;
		
	}

	
	//类型
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/companyCoTypes", method=RequestMethod.GET)
	public List<WSSelectObj> findCompanyCoTypes() {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		for(STypeDic s : sTypeDicRepository.findAll())
		{
			wso.add(new WSSelectObj(s.getId(),s.getName()));
		}
		return wso;
	}
	
	//条款 source=freight or =payment
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/terms", method=RequestMethod.GET)
	public List<WSSelectObj> findFreightTerms(@RequestParam("source") String source) {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		for(STermDic s : sStermDicRepository.getBySource(source))
		{
			wso.add(new WSSelectObj(s.getId(),s.getName()));
		}
		return wso;
	}
	
	//级别
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/companyCoLevels", method=RequestMethod.GET)
	public List<WSSelectObj> findCompanyCoLevels() {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		for(SLevelDic s : sLevelDicRepository.findAll())
		{
			wso.add(new WSSelectObj(s.getId(),s.getName()));
		}
		return wso;
	}
	
	//状态
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/status", method=RequestMethod.GET)
	public List<WSSelectObj> findSStatusBySource(@RequestParam("source") String source) {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		for(SStatusDic s : sStatusDicRepository.getBySource(source))
		{
			wso.add(new WSSelectObj(s.getId(),s.getName()));
		}
		return wso;
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/companyCoList", method=RequestMethod.POST)
	public WSTableData  getCompanyCoList(@RequestParam(required=false, value="companyCoTypeId") Long companyCoTypeId, @RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
// 	System.out.println("co Type: " + companyCoTypeId);
		List<WSCompanyCo> wsCompanyCos = companyCoService.getCoCompanies(companyCoTypeId,securityUtils.getCurrentDBUser().getCompany().getId());
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(wsCompanyCos.size()<start + length)
			end =wsCompanyCos.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			WSCompanyCo w = wsCompanyCos.get(i);
			String[] d = {w.getCode(),w.getShortName(),w.getType(),w.getTel(),w.getFax(),w.getAddressAct(),""+w.getId()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(wsCompanyCos.size());
		t.setRecordsFiltered(wsCompanyCos.size());
	    t.setData(lst);
	    return t;
	}
	
	
	
	
//	
//	//外协公司？
//	@Transactional(readOnly = true)
//	@RequestMapping(value="/s/companyCos", method=RequestMethod.POST)
//	public WSTableData  getCompanyCos(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
//// 	System.out.println("co Type: " + companyCoTypeId);
//		List<WSCompanyCo> wsCompanyCos = companyCoService.getCoCompanies();
//		List<String[]> lst = new ArrayList<String[]>();
//		int end=0;
//		if(wsCompanyCos.size()<start + length)
//			end =wsCompanyCos.size();
//		else
//			end =start + length;
//		for (int i = start; i < end; i++) {
//			WSCompanyCo w = wsCompanyCos.get(i);
//			String[] d = {w.getShortName(),w.getAuditStatus(),""+w.getId()};
//			lst.add(d);
//
//		}
//		WSTableData t = new WSTableData();
//		t.setDraw(draw);
//		t.setRecordsTotal(wsCompanyCos.size());
//		t.setRecordsFiltered(wsCompanyCos.size());
//	    t.setData(lst);
//	    return t;
//	}
//	
//	
//	
//	
//	
//	

	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getCompanyCoList", method=RequestMethod.GET)
	public List<WSSelectObj> companyCoList(@RequestParam(value="coCompanyType", required=false) Long coCompanyType) {
		return companyCoService.getCoCompaniesByType(securityUtils.getCurrentDBUser().getCompany().getId(), coCompanyType);
	}
	
	
	
	
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/uploadCompanyCoFile", method = RequestMethod.POST)
	public Valid uploadCompanyCoFile(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {

		// 公司编码	公司简称	公司名称	重要等级（A，B，C)	公司传真	实际地址	注册地址	注册国家	法人代表	公司网址	开户银行	银行账号	货运贷款	付款条件	检核人	状态（有效，无效）	备注	自动备注	类别（客户/供应商）

		Valid v = new Valid();
		v.setValid(true);
		FileMeta fileMeta = new FileMeta();
		Map<Integer,String> errorMap = new LinkedHashMap<Integer,String>();
		List<WSCompanyCo> wsCompanyCos = new ArrayList<WSCompanyCo>();
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
					WSCompanyCo wSCompanyCo = new WSCompanyCo();
					String code = reader.get(0);
					if(code==null||code.isEmpty()||code.length()>20)
					{
						flag =false;
						if(errorMap.containsKey(i))
						{
							errorMap.put(i, errorMap.get(i) + " 公司编码长度必须在0-20之间，");
						}
						else
						{
							errorMap.put(i,  "第" + i +"行出错，公司编码长度必须在0-20之间， ");
						}
					}
					wSCompanyCo.setCode(code);
					
					String companyName = reader.get(2);
					if(companyName==null||companyName.isEmpty()||companyName.length()>64||companyName.length()<2)
					{
						flag =false;
						if(errorMap.containsKey(i))
						{
							errorMap.put(i, errorMap.get(i) + " 公司名称长度必须在2-64之间，");
						}
						else
						{
							errorMap.put(i,  "第" + i +"行出错，公司名称长度必须在2-64之间， ");
						}
					}
					wSCompanyCo.setName(companyName);
					wSCompanyCo.setShortName(companyName);
					
					
					String lvl = reader.get(3);
					if((lvl!=null)&&(!lvl.equals("A")||!lvl.equals("B")||!lvl.equals("C")))
					{
						flag =false;
						if(errorMap.containsKey(i))
						{
							errorMap.put(i, errorMap.get(i) + " 公司重要等级必须是A，B，C，");
						}
						else
						{
							errorMap.put(i,  "第" + i +"行出错，公司重要等级必须是A，B，C， ");
						}
					}
				
					
					String type = reader.get(18);
					if((type!=null)&&(!type.equals("供应商")||!type.equals("客户")))
					{
						flag =false;
						if(errorMap.containsKey(i))
						{
							errorMap.put(i, errorMap.get(i) + " 类别必须是客户或供应商");
						}
						else
						{
							errorMap.put(i,  "第" + i +"行出错，类别必须是客户或供应商");
						}
					}
					wsCompanyCos.add(wSCompanyCo);
				}
				
			}
			String msg = "";
			for(String err: errorMap.values())
			{
				msg = msg+ err + "\r\n";
			}
			v.setMsg(msg);
			v.setValid(flag);
			//return v;
		} catch (Exception e) {
			e.printStackTrace();
			v.setMsg("上传合作公司失败，请检查文件格式：必须是csv类型文件");
			v.setValid(false);
			//return v;
		}
		
		if(v.getValid())
		{
			
			for(WSCompanyCo wsCompanyCo: wsCompanyCos)
			{
				companyCoService.saveWSCompanyCo(wsCompanyCo);
			}
			
			
		}
		
		return v;
	
	}

	

}