package com.jms.controller.store;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.Company;
import com.jms.domain.db.SMtf;
import com.jms.domain.db.SMtfC;
import com.jms.domain.db.SMtfMaterial;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.s.WSMaterialChecked;
import com.jms.domain.ws.s.WSSMtf;
import com.jms.domain.ws.s.WSSMtfC;
import com.jms.domain.ws.s.WSSMtfMaterial;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.s.SMtfCRepository;
import com.jms.repositories.s.SMtfCRepositoryCustom;
import com.jms.repositories.s.SMtfMaterialRepository;
import com.jms.repositories.s.SMtfMaterialRepositoryCustom;
import com.jms.repositories.s.SMtfRepository;
import com.jms.service.store.MtfMaterialService;
import com.jms.service.store.MtfService;
import com.jms.service.store.SMtfCService;
import com.jms.service.store.SMtfTypeDicService;
import com.jms.web.security.SecurityUtils;
import org.springframework.data.domain.PageRequest;


@RestController
@Transactional(readOnly=true)
public class MtfCController {
	
	@Autowired private SecurityUtils securityUtils;
	@Autowired private SMtfCService sMtfCService;
	@Autowired private MtfMaterialService mtfMaterialService;
	@Autowired private SMtfTypeDicService sMtfTypeDicService;
	@Autowired private SMtfCRepository sMtfCRepository;
	@Autowired private CompanyRepository companyRepository;
	@Autowired private SMtfCRepositoryCustom sMtfCRepositoryCustom;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/saveSmtfC", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid saveSMtfC(@RequestBody WSSMtfC wsSMtfC) throws Exception {
		return sMtfCService.saveMtfC(wsSMtfC);
	}


	
	private String formatDate(Date d)
	{
		if(d==null)
			return "";
		SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		return myFmt1.format(d);
	}
	
	
	
	//往来公司报告明细
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getSmtfCList", method=RequestMethod.POST)
	public WSTableData  getSmtfCList(
			@RequestParam(required=false,value="q") String q,
			@RequestParam(required=false,value="companyName") String companyName,
			@RequestParam(required=false,value="fromDay") String fromDay,
			@RequestParam(required=false,value="toDay") String toDay,
			@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length)  {	   
		
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
	
		List<SMtfC> sMtfCs = sMtfCRepositoryCustom.getCustomSMtfC(companyId, q,companyName, fromDay, toDay);
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(sMtfCs.size()<start + length)
			end =sMtfCs.size();
		else
			end =start + length;
		for(int i=start;i<end;i++)
		{
			SMtfC w = sMtfCs.get(i);
			
			Company c1 = companyRepository.findOne(w.getIdCompany1());
			Company c2 = companyRepository.findOne(w.getIdCompany2());
			Date date = w.getAuditDate();
			String sDate = "";
			if(date!=null)
			{
				SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd"); 
				sDate = myFmt1.format(date);;
			}
			String[] d = {
					w.getCompany1(),
					w.getCompany2(),
					sDate,
					w.getPno()+"_"+w.getMaterialName(),
					""+w.getQty(),
					w.getPno()
					};
			lst.add(d);
		}

	
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(sMtfCs.size());
		t.setRecordsFiltered(sMtfCs.size());
	    t.setData(lst);
	    return t;
	}
	
	
	//往来公司汇总
		@Transactional(readOnly = true)
		@RequestMapping(value="/s/getSmtfCSum", method=RequestMethod.POST)
		public WSTableData  getSmtfCSum(
				@RequestParam(required=false,value="q") String q,
				@RequestParam(required=false,value="companyName") String companyName,
				@RequestParam(required=false,value="fromDay") String fromDay,
				@RequestParam(required=false,value="toDay") String toDay,
				@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length)  {	   
			
			List<String[]> lst = new ArrayList<String[]>();
			for(int i=0;i<1;i++)
			{
				
				String[] d = {
						"公司",
						"物料",
						"期初",
						"收",
						"法",
						"存"
						
						};
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