package com.jms.controller.store;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.jms.domain.ws.s.WSMaterialChecked;
import com.jms.domain.ws.s.WSSMtf;
import com.jms.domain.ws.s.WSSMtfMaterial;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.s.SMtfMaterialRepository;
import com.jms.repositories.s.SMtfMaterialRepositoryCustom;
import com.jms.repositories.s.SMtfRepository;
import com.jms.repositories.s.SStatusDicRepository;
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
	@Autowired private SMtfMaterialRepository sMtfMaterialRepository;
	@Autowired private SMtfMaterialRepositoryCustom sMtfMaterialRepositoryCustom;
	@Autowired private CompanyRepository companyRepository;
	@Autowired private SStatusDicRepository sStatusDicRepository;
	
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
	@RequestMapping(value="/s/findSmtfMaterialObjs", method=RequestMethod.GET)
	public List<WSSelectObj> findSmtfMaterialObjs(@RequestParam("smtfId") Long smtfId) throws Exception {
		List<WSSelectObj> ws =new ArrayList<WSSelectObj>();
		SMtf sMtf = sMtfRepository.findOne(smtfId);
		for(SMtfMaterial sm:sMtfMaterialRepository.getNotReceivedBySmtfId(sMtf.getIdMt()))
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
		for(SMtfMaterial sm: sMtfMaterialRepository.getNotReceivedBySmtfId(sMtf.getIdMt()))
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
	@RequestMapping(value="/s/smtfMaterialList", method=RequestMethod.POST)
	public WSTableData  getSmtfMaterialList(
			@RequestParam Long typeId, 
			@RequestParam(required=false) String from,
			@RequestParam(required=false) String to,
			@RequestParam(required=false) String q,
			@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		//Pageable pageable = new PageRequest(start,length);
		//System.out.println("get smtf materials:  type = " + typeId);
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<SMtfMaterial> wsSMtfMaterials = sMtfMaterialRepositoryCustom.getSmtfMaterialList(companyId, typeId, q, from, to);
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
					WSSMtfMaterial w =mtfMaterialService.toWSSMtfMaterial(wsSMtfMaterials.get(i));
					String status =(w.getStatus()==null)?"":""+w.getStatus();
					String[] d = {w.getMtNo(),""+w.getCodePo(),w.getCodeCo(),formatDate(w.getDeliveryDate()),formatDate(w.getCreationTime()),w.getMaterialPno(),w.getMaterialRev(),w.getMaterialDes(),""+w.getQty(),status,""+w.getIdMt()};
					lst.add(d);
					//System.out.println(d[10]);

				}
		    	break;
		    }
		   case 2: //采购退货
		    {
		    	for (int i = start; i < end; i++) {
		    		WSSMtfMaterial w =mtfMaterialService.toWSSMtfMaterial(wsSMtfMaterials.get(i));
					String[] d = {w.getEmpMtUser(),formatDate(w.getCreationTime()),w.getMtNo(),w.getCodeCo(),w.getCodePo(),w.getMaterialPno(),w.getMaterialRev(),w.getMaterialDes(),w.getLotNo(),""+w.getQty(),""+w.getIdMt()};
					lst.add(d);

				}
		    	break;
		    }
		   case 5: //出货
		    {
		    	for (int i = start; i < end; i++) {
		    		WSSMtfMaterial w =mtfMaterialService.toWSSMtfMaterial(wsSMtfMaterials.get(i));
					String[] d = {w.getMtNo(),w.getSoCode(),w.getCodeCo(),formatDate(w.getDeliveryDate()),formatDate(w.getCreationTime()),w.getMaterialPno(),w.getMaterialRev(),w.getMaterialDes(),""+w.getQty(),""+w.getIdMt()};
			    	//System.out.println("mtNo ...... "+ w.getMtNo());
					lst.add(d);
				}
		    	break;
		    }
		   case 6: //销售退货
		    {
		    	for (int i = start; i < end; i++) {
		    		WSSMtfMaterial w =mtfMaterialService.toWSSMtfMaterial(wsSMtfMaterials.get(i));
					String[] d = {w.getMtNo(),w.getSoCode(),w.getCodeCo(),formatDate(w.getDeliveryDate()),w.getMaterialPno(),w.getMaterialRev(),w.getMaterialDes(),""+w.getQty(),w.getRecMtUser(),formatDate(w.getCreationTime()),""+w.getIdMt()};
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
	
	private String formatDate(Date d)
	{
		if(d==null)
			return "";
		SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		return myFmt1.format(d);
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getSmtfMaterialListByTypeAndMaterial", method=RequestMethod.POST)
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
		for(int i=start;i<end;i++)
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

			
			String[] d = {mtNo,w.getType(),material,lotNo,fromBin,toBin,""+w.getQty(),w.getEmpMtUser(),w.getRecMtUser(),formatDate(w.getCreationTime()),""+w.getIdMt()};
			lst.add(d);
		}

	
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(wsSMtfMaterials.size());
		t.setRecordsFiltered(wsSMtfMaterials.size());
	    t.setData(lst);
	    return t;
	}
	
	
	//6作废 友好公司 拒绝接收物料
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/refuseSmtf", method=RequestMethod.GET)
	public Valid refuseSmtf(@RequestParam("mtId") Long mtId){
		Valid v = new Valid();
		SMtf smtf = sMtfRepository.findOne(mtId);
		smtf.setSStatusDic(sStatusDicRepository.findOne(6l)); //作废
		sMtfRepository.save(smtf);
		v.setValid(true);
		return v;
      }
	
	//4待接收，5接收，6作废 友好公司
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getSmtfMaterialListFromCom", method=RequestMethod.POST)
	public WSTableData  getSmtfMaterialListFromCom(
			@RequestParam(value="statusId") Long statusId,  //4待接收，5接收，6作废
			
			@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length)  {	   
		
		//our companyId
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		String toCompanyName = securityUtils.getCurrentDBUser().getCompany().getCompanyName();
	
		
	
		List<SMtfMaterial> sMtfMaterials = sMtfMaterialRepositoryCustom.getCustomCSMtfMaterials(companyId, statusId);
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(sMtfMaterials.size()<start + length)
			end =sMtfMaterials.size();
		else
			end =start + length;
		for(int i=start;i<end;i++)
		{
			SMtfMaterial w = sMtfMaterials.get(i);
			SMtf smtf = w.getSMtf();
			
			String mtNo = (smtf.getMtNo()==null)?"":smtf.getMtNo();
			String material;
			if(w.getSMaterial()!=null)
			{
				 material = (w.getSMaterial().getPno()+"_"+w.getSMaterial().getRev()+"_"+w.getSMaterial().getDes());
			}
			else
			{
				material="";
			}
			String lotNo = w.getLotNo();
			String fromBin="",toBin="";
			if(statusId==4l||statusId==6l)
			{
				if(w.getSBinByFromBin()!=null)
				{
					fromBin=w.getSBinByFromBin().getSStk().getStkName() +"_"+w.getSBinByFromBin().getBin();
				}
			}
			else
			{
				if(w.getSBinByToBin()!=null)
				{
					toBin=w.getSBinByToBin().getSStk().getStkName() +"_"+w.getSBinByToBin().getBin();
				}
			}

			String coOrderNo="";
			if(w.getSSo()!=null)
			{
				coOrderNo = w.getSSo().getCoOrderNo();
				//wsSMtf.set
			}
			String empUser =(w.getSMtf().getUsersByEmpMt()==null)?"":w.getSMtf().getUsersByEmpMt().getName();
			if(empUser==null)
				empUser="";
			String rectUser =(w.getSMtf().getUsersByRecMt()==null)?"":w.getSMtf().getUsersByRecMt().getUsername();
			if(rectUser==null)
				rectUser="";
			Date date = w.getSMtf().getCreationTime();
			String sDate = "";
			if(date!=null)
			{
				SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd"); 
				sDate = myFmt1.format(date);;
			}
			
			Date dateq = w.getSMtf().getDateMt();
			String sDateq = "";
			if(dateq!=null)
			{
				SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd"); 
				sDateq = myFmt1.format(dateq);;
			}
			
			String remark="";
			if(w.getRemark()!=null)
			{
				remark=w.getRemark();
			}
		
			String fromCompany = "";
			if(statusId==4l||statusId==6l)
			{
				fromCompany=smtf.getCompany().getCompanyName();
			}
			else
			{
				if(smtf.getIdSmtfC()!=null)
				{
					SMtf psmtf = sMtfRepository.findOne(smtf.getIdSmtfC());
					if(psmtf!=null)
					{
						fromCompany = psmtf.getCompany().getCompanyName();
					}
				}
			
			}
			String[] d = {
					sDate,
					fromCompany,
					toCompanyName,
					lotNo,
					fromBin,
					toBin,
					material,
					""+w.getQty(),
					coOrderNo,
					remark,
					sDateq,
					mtNo,
					""+w.getSMtf().getIdMt()
					};
			lst.add(d);
		}

	
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(sMtfMaterials.size());
		t.setRecordsFiltered(sMtfMaterials.size());
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
	
	
	
	
	
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getSmtfMaterialList", method=RequestMethod.POST)
	public WSTableData  getSmtfMaterialList(
			@RequestParam(required=false,value="typeId") Long typeId,
			@RequestParam(required=false,value="q") String q,
			@RequestParam(required=false,value="fromStkId") Long fromStkId,
			@RequestParam(required=false,value="toStkId") Long toStkId,
			@RequestParam(required=false,value="fromDay") String fromDay,
			@RequestParam(required=false,value="toDay") String toDay,
			@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length)  {	   
		
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		
		System.out.println("companyId: " + companyId);
	
		List<SMtfMaterial> sMtfMaterials = sMtfMaterialRepositoryCustom.getCustomSMtf(companyId, typeId,q,fromStkId,toStkId, fromDay, toDay);
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(sMtfMaterials.size()<start + length)
			end =sMtfMaterials.size();
		else
			end =start + length;
		for(int i=start;i<end;i++)
		{
			SMtfMaterial w = sMtfMaterials.get(i);
			
			
			String mtNo = (w.getSMtf().getMtNo()==null)?"":w.getSMtf().getMtNo();
			String material;
			if(w.getSMaterial()!=null)
			{
				 material = (w.getSMaterial().getPno()+"_"+w.getSMaterial().getRev()+"_"+w.getSMaterial().getDes());
			}
			else
			{
				material="";
			}
			String lotNo = w.getLotNo();
			
			String fromBin;
			if(w.getSBinByFromBin()!=null)
			{
				fromBin=w.getSBinByFromBin().getSStk().getStkName() +"_"+w.getSBinByFromBin().getBin();
			}
			else
			{
				fromBin="";
			}
			
			String toBin;
			if(w.getSBinByToBin()!=null)
			{
				toBin=w.getSBinByToBin().getSStk().getStkName() +"_"+w.getSBinByToBin().getBin();
			}
			else
			{
				toBin="";
			}

		
			String empUser =(w.getSMtf().getUsersByEmpMt()==null)?"":w.getSMtf().getUsersByEmpMt().getName();
			if(empUser==null)
				empUser="";
			String rectUser =(w.getSMtf().getUsersByRecMt()==null)?"":w.getSMtf().getUsersByRecMt().getUsername();
			if(rectUser==null)
				rectUser="";
			Date date = w.getSMtf().getCreationTime();
			String sDate = "";
			
			String status="";
			if(w.getSMtf().getSStatusDic()!=null)
			{
				status = w.getSMtf().getSStatusDic().getName();
			}

			if(date!=null)
			{
				SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd"); 
				sDate = myFmt1.format(date);;
			}
			String[] d = {
					mtNo,
					w.getSMtf().getSMtfTypeDic().getName(),
					material,
					lotNo,
					fromBin,
					toBin,
					""+w.getQty(),
					empUser,
					sDate,
					status,
					""+w.getSMtf().getIdMt()
					};
			lst.add(d);
		}

	
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(sMtfMaterials.size());
		t.setRecordsFiltered(sMtfMaterials.size());
	    t.setData(lst);
	    return t;
	}
	
}