package com.jms.controller.store;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.store.WSSMtf;
import com.jms.domain.ws.store.WSSMtfMaterial;
import com.jms.service.store.MtfMaterialService;
import com.jms.service.store.MtfService;
import com.jms.service.store.SMtfTypeDicService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class MtfController {
	
	@Autowired private SecurityUtils securityUtils;
	@Autowired private MtfService mtfService;
	@Autowired private MtfMaterialService mtfMaterialService;
	@Autowired private SMtfTypeDicService sMtfTypeDicService;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/saveSmtf", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid saveSMtf(@RequestBody WSSMtf wsSMtf) {
		return mtfService.saveMtf(wsSMtf);
	}

	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findSmtf", method=RequestMethod.GET)
	public WSSMtf findWSSMtf(@RequestParam("smtfId") Long smtfId) throws Exception {
		return mtfService.findSMtf(smtfId);
	}

	@Transactional(readOnly = true)
	@RequestMapping(value="/s/smtfMaterialList", method=RequestMethod.GET)
	public WSTableData  getSmtfMaterialList(@RequestParam Long typeId, @RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		//System.out.println("get smtf materials:  type = " + typeId);
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<WSSMtfMaterial> wsSMtfMaterials = mtfMaterialService.findWSSMtfMaterial(companyId, typeId);
		//System.out.println("get smtf materials:  size = " + wsSMtfMaterials.size());
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
	//	System.out.println("start = " +start);
	//	System.out.println( ", length = " + length);
		if(wsSMtfMaterials.size()<start + length)
			end =wsSMtfMaterials.size();
		else
			end =start + length;
		switch(typeId.intValue())
		{
		    case 1: //来料入库
		    {
		    	//System.out.println("case 1 来料入库 " + typeId);
		    	for (int i = start; i < end; i++) {
					WSSMtfMaterial w = wsSMtfMaterials.get(i);
					String[] d = {w.getMtNo(),""+w.getCodePo(),w.getCodeCo(),w.getDeliveryDate().toString(),w.getCreationTime().toString(),w.getMaterialPno(),w.getMaterialRev(),w.getMaterialDes(),""+w.getQty(),""+w.getStatus(),""+w.getIdMt()};
					lst.add(d);
					//System.out.println(d[10]);

				}
		    	break;
		    }
		   case 2: //采购退货
		    {
		    	for (int i = start; i < end; i++) {
					WSSMtfMaterial w = wsSMtfMaterials.get(i);
					String[] d = {w.getEmpMtUser(),w.getCreationTime().toString(),w.getMtNo(),w.getCodeCo(),w.getCodePo(),w.getMaterialPno(),w.getMaterialRev(),w.getMaterialDes(),w.getLotNo(),""+w.getQty(),""+w.getIdMt()};
					lst.add(d);

				}
		    	break;
		    }
		   case 5: //出货
		    {
		    	for (int i = start; i < end; i++) {
					WSSMtfMaterial w = wsSMtfMaterials.get(i);
					
					String[] d = {w.getMtNo(),w.getSoCode(),w.getCodeCo(),w.getDeliveryDate().toString(),w.getCreationTime().toString(),w.getMaterialPno(),w.getMaterialRev(),w.getMaterialDes(),""+w.getQty(),""+w.getIdMt()};
			    	//System.out.println("mtNo ...... "+ w.getMtNo());
					lst.add(d);
				}
		    	break;
		    }
		   case 6: //销售退货
		    {
		    	for (int i = start; i < end; i++) {
					WSSMtfMaterial w = wsSMtfMaterials.get(i);
					
					String[] d = {w.getMtNo(),w.getSoCode(),w.getCodeCo(),w.getDeliveryDate().toString(),w.getMaterialPno(),w.getMaterialRev(),w.getMaterialDes(),""+w.getQty(),w.getRecMtUser(),w.getCreationTime().toString(),""+w.getIdMt()};
			    	//System.out.println("mtNo ...... "+ w.getMtNo());
					lst.add(d);
				}
		    	break;
		    }
		    
		
		}
	
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(wsSMtfMaterials.size());
		t.setRecordsFiltered(wsSMtfMaterials.size());
	    t.setData(lst);
	    return t;
	}
	
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getSmtfMaterialListByTypeAndQ", method=RequestMethod.GET)
	public WSTableData  getSmtfMaterialListByTypeAndQ(@RequestParam(required=false,value="typeId") Long typeId,
			@RequestParam(required=false,value="q") String q,
			@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
	
		List<WSSMtfMaterial> wsSMtfMaterials = mtfMaterialService.findWSSMtfMaterial(companyId, typeId,q);
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(wsSMtfMaterials.size()<start + length)
			end =wsSMtfMaterials.size();
		else
			end =start + length;
		for(int i=0;i<end;i++)
		{
			WSSMtfMaterial w = wsSMtfMaterials.get(i);
			String mtNo = (w.getMtNo()==null)?"":w.getMtNo();
			String material;
			if(w.getMaterialPno()!=null)
			{
				 material = (w.getMaterialPno()+"_"+w.getMaterialRev()+"_"+w.getMaterialDes());
			}
			else
			{
				material="";
			}
			String lotNo = w.getLotNo();
			
			String fromBin;
			if(w.getFromBin()!=null)
			{
				fromBin=w.getFromStk() +"_"+w.getFromBin();
			}
			else
			{
				fromBin="";
			}
			
			String toBin;
			if(w.getToBin()!=null)
			{
				toBin=w.getToStk() +"_"+w.getToBin();
			}
			else
			{
				toBin="";
			}

			
			String[] d = {mtNo,w.getType(),w.getMtNo(),material,lotNo,fromBin,toBin,""+w.getQty(),w.getEmpMtUser(),w.getRecMtUser(),w.getCreationTime().toString()};
			lst.add(d);
		}

	
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(wsSMtfMaterials.size());
		t.setRecordsFiltered(wsSMtfMaterials.size());
	    t.setData(lst);
	    return t;
	}
	
	
	
	
	
	
	
	
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getMtfTypes", method=RequestMethod.GET)
	public List<WSSelectObj> getMtfTypes() {
		return  sMtfTypeDicService.getMtfTypes();
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findWSSMtfMaterialBySpoIdAndStkId", method=RequestMethod.GET)
	public List<WSSMtfMaterial> findWSSMtfMaterialBySpoId(@RequestParam("spoId") Long spoId,@RequestParam("stkId") Long stkId) throws Exception {
		return mtfMaterialService.findWSSMtfMaterialBySpoId(spoId,stkId);
	}
	
	
	
	
}