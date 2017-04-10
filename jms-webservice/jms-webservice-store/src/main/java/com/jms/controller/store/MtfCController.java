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
import com.jms.service.store.SMtfCService;
import com.jms.service.store.SMtfTypeDicService;
import com.jms.web.security.SecurityUtils;


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
	@Autowired private SMtfMaterialRepositoryCustom sMtfMaterialRepositoryCustom;
	
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
			@RequestParam(required=false,value="idCompany2") Long idCompany2,
			@RequestParam(required=false,value="fromDay") String fromDay,
			@RequestParam(required=false,value="toDay") String toDay,
			@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length)  {	   
		
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<SMtfMaterial> wsSMtfMaterials = sMtfMaterialRepositoryCustom.getCustomCSMtf(companyId, idCompany2, q, fromDay, toDay);
	
		//List<SMtfC> sMtfCs = sMtfCRepositoryCustom.getCustomSMtfC(companyId, q,companyName, fromDay, toDay);
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(wsSMtfMaterials.size()<start + length)
			end =wsSMtfMaterials.size();
		else
			end =start + length;
		for(int i=start;i<end;i++)
		{
			
			SMtfMaterial w = wsSMtfMaterials.get(i);
		//	System.out.println("id_mt: " +w.getSMtf().getIdMt() );
			Company c1 = w.getSMtf().getCompany();
			String  c2="";
			if(w.getSMtf().getIdToCompany()!=null)
			{
				Company c = companyRepository.findOne(w.getSMtf().getIdToCompany());
				c2 =c.getCompanyName();
			}
			
			Date date = w.getSMtf().getCreationTime();
			String sDate = "";
			if(date!=null)
			{
				SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd"); 
				sDate = myFmt1.format(date);;
			}
			String material ="";
			if(w.getSMaterial()!=null)
			{
				material = w.getSMaterial().getPno()+"_"+w.getSMaterial().getDes();
			}
			String[] d = {
					c1.getCompanyName(),
					c2,
					sDate,
					material,
					""+w.getQty()
					//w.
					};
			lst.add(d);
		}

	
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(wsSMtfMaterials.size());
		t.setRecordsFiltered(wsSMtfMaterials.size());
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
						"",
						"",
						"",
						"",
						""
						
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