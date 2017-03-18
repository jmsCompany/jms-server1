package com.jms.controller.production;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.csvreader.CsvReader;
import com.jms.domain.db.PCPp;
import com.jms.domain.db.PCppOp;
import com.jms.domain.db.PWo;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.p.WSOEE;
import com.jms.domain.ws.p.WSPCpp;
import com.jms.domain.ws.p.WSPMr;
import com.jms.domain.ws.p.WSPlannedMaterialSending;
import com.jms.file.FileMeta;
import com.jms.file.FileUploadService;
import com.jms.repositories.p.PCPpRepository;
import com.jms.repositories.p.PCppOpRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.service.production.PCppService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class CppController {
	
	@Autowired private PCppService pCppService;
	@Autowired private PCPpRepository pCPpRepository;
	@Autowired private PWoRepository pWoRepository;
	@Autowired private SecurityUtils securityUtils;
	@Autowired private PCppOpRepository pCppOpRepository;
	@Autowired private UsersRepository usersRepository;
	@Autowired
	private  FileUploadService fileUploadService;
    @Value("${filePath}")
	private String filePath;

	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/uploadCppFile", method = RequestMethod.POST)
	public Valid uploadCppFile( MultipartHttpServletRequest request, HttpServletResponse response)  {
	
		Valid v= new Valid();
		FileMeta fileMeta = new FileMeta();
		try{
			if (request.getFileNames().hasNext()) {
				fileMeta = fileUploadService.upload(request, response,true);
				FileInputStream inputStream = new FileInputStream(filePath+fileMeta.getFileName());
				CsvReader reader = new CsvReader(inputStream,',', Charset.forName("UTF-8"));
				reader.readHeaders();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
				while(reader.readRecord())
				{
					//System.out.println("woId:" + reader.get(0));
					Long woId = Long.parseLong(reader.get(0)); //工单
				    Long machineID = Long.parseLong(reader.get(1)); //机器
				    Long routineDId = Long.parseLong(reader.get(2)); //工序
				    Long shiftDId = Long.parseLong(reader.get(3)); //排班
				    Long qty = Long.parseLong(reader.get(4)); //数量
				    Date planSt =formatter.parse(reader.get(5));//计划开始
				    Date planFt =formatter.parse(reader.get(6));//计划结束
					WSPCpp cpp = new WSPCpp();
					cpp.setPwoId(woId);
					cpp.setmMachineId(machineID);
					cpp.setpRoutineDId(routineDId);
					cpp.setpShiftPlanDId(shiftDId);
					cpp.setQty(qty);
					cpp.setPlanSt(planSt);
					cpp.setPlanFt(planFt);
					pCppService.saveWSPCPp(cpp);
				}
				}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			v.setMsg("上传排产计划失败");
			v.setValid(false);
			return v;
		}
		v.setMsg("成功");
		v.setValid(true);
		return v;
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/saveWSPCpp", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSPCpp saveWSPCpp(@RequestBody WSPCpp wsPCpp) throws Exception {
		return pCppService.saveWSPCPp(wsPCpp);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/startCpp", method=RequestMethod.GET)
	public Valid startCpp(@RequestParam("idCpp") Long idCpp) throws Exception {
		return pCppService.startCpp(idCpp);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/finishCpp", method=RequestMethod.GET)
	public Valid finishCpp(@RequestParam("idCpp") Long idCpp) throws Exception {
		return pCppService.finishCpp(idCpp);
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/isStarted", method=RequestMethod.GET)
	public Valid isStarted(@RequestParam("idCpp") Long idCpp) throws Exception {
		return pCppService.isStarted(idCpp);
	}
	
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/p/deleteWSCpp", method=RequestMethod.GET)
	public Valid deleteCpp(@RequestParam("cppId") Long cppId) {
		return pCppService.deletePCpp(cppId);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findCpp", method=RequestMethod.GET)
	public WSPCpp findCpp(@RequestParam("cppId") Long cppId) throws Exception {
		return pCppService.findWSCPp(cppId);
		
	}
	

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getCppList", method=RequestMethod.POST)
	public WSTableData  getCppList(@RequestParam(required=false,value="q") String q, @RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		//System.out.println("q: " + q);
		List<String[]> lst = new ArrayList<String[]>();
		List<PCPp> pCpps;
		List<Long> woIds = new ArrayList<Long>();

		if(q==null)
		{
			for(PWo pwo:pWoRepository.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany()))
			{
				woIds.add(pwo.getIdWo());
			}
		}
		else
		{
			q= '%'+q+'%';
			for(PWo pwo:pWoRepository.getByCompanyIdAndQuery(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), q))
			{
				woIds.add(pwo.getIdWo());
			}
		}
	
	
		if(!woIds.isEmpty())
		{
			pCpps =pCPpRepository.getByCompanyIdAndWoIds(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), woIds);	
		}
		else
		{
			pCpps = new ArrayList<PCPp>();
		}
		 	
			
		
		int end=0;
		if(pCpps.size()<start + length)
			end =pCpps.size();
		else
			end =start + length;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		for (int i = start; i < end; i++) {
			PCPp w = pCpps.get(i);
			String routineDNo ="";
			if(w.getPRoutineD()!=null)
			{
				routineDNo=w.getPRoutineD().getRouteNo();
			}
			
			String planSt = "";
			if(w.getPlanSt()!=null)
			{
				
				planSt= formatter.format(w.getPlanSt());
			}
			
			String planFt = "";
			if(w.getPlanFt()!=null)
			{
				
				planFt= formatter.format(w.getPlanFt());
			}
			
			
			String actSt = "";
			if(w.getActSt()!=null)
			{
				
				actSt= formatter.format(w.getActSt());
			}
			
			String actFt = "";
			if(w.getActFt()!=null)
			{
				
				actFt= formatter.format(w.getActFt());
			}
			String product =w.getPWo().getSSo().getSMaterial().getPno()+"_"+w.getPWo().getSSo().getSMaterial().getRev()+"_"+w.getPWo().getSSo().getSMaterial().getDes();
			
			
			List<PCppOp> ops = pCppOpRepository.getByCppId(w.getIdCPp());
			
			String opNames = "";
			for(PCppOp p: ops)
			{
			   
			 Users u =   usersRepository.getOne(p.getId().getIdUser());
			 
			// System.out.println("cppId: " +w.getIdCPp() + ", uid: " + p.getId().getIdUser() + ", name: " +  u.getName());
			 if(!opNames.isEmpty())
			 {
				 opNames = opNames +"," + u.getName(); 
			 }
			 else
			 {
				 opNames =  u.getName(); 
			 }
			 
			
			}
			
			String[] d = {""+w.getCPpCode(),w.getPWo().getWoNo(),routineDNo,product,""+w.getPWo().getQty(),w.getPShiftPlanD().getShift(),planSt,planFt,actSt,actFt,w.getMMachine().getCode()+""
					+ "-"+w.getMMachine().getSpec(),opNames,""+w.getQty(),""+w.getIdCPp()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(pCpps.size());
		t.setRecordsFiltered(pCpps.size());
	    t.setData(lst);
	    return t;
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findWSPMrsByCppId", method=RequestMethod.GET)
	public List<WSPMr> findWSPMrsByCppId(@RequestParam("cppId") Long cppId) throws Exception {
		return pCppService.findWSPMrsByCppId(cppId);
		
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findWSPlannedMaterialSending", method=RequestMethod.GET)
	public WSPlannedMaterialSending findWSPlannedMaterialSending(
			@RequestParam("fromDate") Long fromDate,
			@RequestParam("toDate") Long toDate,
			@RequestParam("fromStkId") Long fromStkId,
			@RequestParam("toStkId") Long toStkId)  {
      // System.out.println("find planned materials sending");
		return pCppService.findWSPlannedMaterialSending(fromDate, toDate, fromStkId, toStkId);		
		
	}
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findWSOEE", method=RequestMethod.GET)
	public List<WSOEE> findWSOEE(
			@RequestParam("fromDate") Long fromDate,
			@RequestParam("toDate") Long toDate,
			@RequestParam("machineId") Long machineId,
			@RequestParam(required=false,value="materialId") Long materialId)
    {
		return pCppService.findWSOEE(fromDate, toDate, machineId,materialId);
		
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findLongWSOEE", method=RequestMethod.GET)
	public List<WSOEE> findLongWSOEE(
			@RequestParam("fromDate") Long fromDate,
			@RequestParam("toDate") Long toDate,
			@RequestParam("machineId") Long machineId,
			@RequestParam(required=false,value="materialId") Long materialId)
    {
		return pCppService.findLongWSOEE(fromDate, toDate, machineId,materialId);
		
	}
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getCppListByMachineIdAndDate", method=RequestMethod.GET)
	public List<WSSelectObj>  getCppListByMachineIdAndDate(@RequestParam("machineId") Long machineId,@RequestParam("reportTime") Long reportTime) {	   
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		
		Date st = new Date(reportTime-86400000*7);
		Date ft = new Date(reportTime+86400000*7);
		for(PCPp p:pCPpRepository.getByMachineIdAndDate(securityUtils.getCurrentDBUser().getCompany().getIdCompany(),machineId,st,ft))
		 {
			String s = (p.getCPpCode()==null)?"":p.getCPpCode();
			s = s+ "_" +p.getPlanSt().toString() +"_"+p.getUsers().getName(); 
			WSSelectObj w = new WSSelectObj(p.getIdCPp(),s);
			ws.add(w);
		 }
		
	    return ws;
	}

	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/getCppListByMaterialId", method=RequestMethod.GET)
	public List<WSSelectObj>  getCppListByMaterialId(@RequestParam("materialId") Long materialId) {	   
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		for(PCPp cpp:pCPpRepository.getByMaterialId(materialId))
		 {
			
			Date d = cpp.getPlanFt();
			String dd = "";
			if(d!=null)
			{
				 
				dd= formatter.format(d);
			}
			String shift = "";
			if(cpp.getPShiftPlanD()!=null)
			{
				shift = cpp.getPShiftPlanD().getShift();
			}
			String s =cpp.getPWo().getWoNo()+"_"+cpp.getMMachine().getCode()+"_"+dd +" "+shift;

			WSSelectObj w = new WSSelectObj(cpp.getIdCPp(),s);
			ws.add(w);
		 }
		
	    return ws;
	}


	@Transactional(readOnly = true)
	@RequestMapping(value="/p/hasCheckPlans", method=RequestMethod.GET)
	public Valid hasCheckPlans(@RequestParam("cppId") Long cppId)  {
		Valid v =  pCppService.hasCheckPlans(cppId);
		return v;
	}
	
	
}