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
import com.jms.service.MtfMaterialService;
import com.jms.service.MtfService;
import com.jms.service.SMtfTypeDicService;
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
	public Valid saveSMtf(@RequestBody WSSMtf wsSMtf) throws Exception {
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
		
		System.out.println("get smtf materials:  type = " + typeId);
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<WSSMtfMaterial> wsSMtfMaterials = mtfMaterialService.findWSSMtfMaterial(companyId, typeId);
		System.out.println("get smtf materials:  size = " + wsSMtfMaterials.size());
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		System.out.println("start = " +start);
		System.out.println( ", length = " + length);
		if(wsSMtfMaterials.size()<start + length)
			end =wsSMtfMaterials.size();
		else
			end =start + length;
		switch(typeId.intValue())
		{
		    case 1: //来料入库
		    {
		    	System.out.println("case 1 来料入库 " + typeId);
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
	@RequestMapping(value="/s/getMtfTypes", method=RequestMethod.GET)
	public List<WSSelectObj> getMtfTypes() {
		return  sMtfTypeDicService.getMtfTypes();
	}
	
}