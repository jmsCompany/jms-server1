package com.jms.controller.store;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.SMtf;
import com.jms.domain.db.SMtfMaterial;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.store.WSMaterialChecked;
import com.jms.domain.ws.store.WSSMtf;
import com.jms.domain.ws.store.WSSMtfMaterial;
import com.jms.repositories.s.SMtfRepository;
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
	@Autowired private SMtfRepository sMtfRepository;
	
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
	@RequestMapping(value="/s/findSmtfMaterialObjs", method=RequestMethod.GET)
	public List<WSSelectObj> findSmtfMaterialObjs(@RequestParam("smtfId") Long smtfId) throws Exception {
		List<WSSelectObj> ws =new ArrayList<WSSelectObj>();
		SMtf sMtf = sMtfRepository.findOne(smtfId);
		for(SMtfMaterial sm: sMtf.getSMtfMaterials())
		{
			Long qty = (sm.getQty()==null)?0:sm.getQty();
			Long qtyChecked = (sm.getQty3417()==null)?0:sm.getQty3417();
			String lotNo =(sm.getLotNo()==null)?"":sm.getLotNo();
			WSSelectObj w = new WSSelectObj();
			w.setSid(sm.getIdMtfMaterial()+"_"+sm.getSMaterial().getIdMaterial());
			w.setName(sm.getSMaterial().getPno()+"_"+sm.getSMaterial().getRev()+"_"+sm.getSMaterial().getDes()+", 批号: "+lotNo+ ", 数量: " +qty+", 已检: " + qtyChecked);
		    ws.add(w);
		}
		return ws;
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findSmtfMaterialChecked", method=RequestMethod.GET)
	public List<WSMaterialChecked> findSmtfMaterialChecked(@RequestParam("smtfId") Long smtfId) throws Exception {
		List<WSMaterialChecked> ws =new ArrayList<WSMaterialChecked>();
		SMtf sMtf = sMtfRepository.findOne(smtfId);
		for(SMtfMaterial sm: sMtf.getSMtfMaterials())
		{
			Long qty = (sm.getQty()==null)?0:sm.getQty();
			Long qtyChecked = (sm.getQty3417()==null)?0:sm.getQty3417();
			String lotNo =(sm.getLotNo()==null)?"":sm.getLotNo();
			WSMaterialChecked w = new WSMaterialChecked();
		    w.setIdMaterial(sm.getSMaterial().getIdMaterial());
		    w.setDes(sm.getSMaterial().getDes());
		    w.setPno(sm.getSMaterial().getPno());
		    w.setIdMtfMaterial(sm.getIdMtfMaterial());
		    w.setQty(qty);
		    w.setQtyChecked(qtyChecked);
		    w.setRev(sm.getSMaterial().getRev());
		    w.setLotNo(lotNo);
		    ws.add(w);
		}
		return ws;
	}
	
	

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getIncomeList", method=RequestMethod.GET)
	public List<WSSelectObj> getIncomeList(@RequestParam("typeId") Long typeId) throws Exception {
		return mtfService.getIncomeList(typeId);
	}

	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/updateMtfStatus", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid updateMtfStatus(@RequestBody WSSMtf wsSMtf) {
		return mtfService.updateMtfStatus(wsSMtf);
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
					String status =(w.getStatus()==null)?"":""+w.getStatus();
					String[] d = {w.getMtNo(),""+w.getCodePo(),w.getCodeCo(),w.getDeliveryDate().toString(),w.getCreationTime().toString(),w.getMaterialPno(),w.getMaterialRev(),w.getMaterialDes(),""+w.getQty(),status,""+w.getIdMt()};
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
	@RequestMapping(value="/s/getSmtfMaterialListByTypeAndMaterial", method=RequestMethod.GET)
	public WSTableData  getSmtfMaterialListByTypeAndQ(@RequestParam(required=false,value="typeId") Long typeId,
			@RequestParam(required=false,value="materialId") Long materialId,
			@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
	
		List<WSSMtfMaterial> wsSMtfMaterials = mtfMaterialService.findWSSMtfMaterial(companyId, typeId,materialId);
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

			
			String[] d = {mtNo,w.getType(),material,lotNo,fromBin,toBin,""+w.getQty(),w.getEmpMtUser(),w.getRecMtUser(),w.getCreationTime().toString(),""+w.getIdMt()};
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