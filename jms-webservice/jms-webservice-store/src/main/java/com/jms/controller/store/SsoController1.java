package com.jms.controller.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.SComCom;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SSo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.s.WSMaterial;
import com.jms.domain.ws.s.WSMaterialDelivered;
import com.jms.domain.ws.s.WSSSoRemark;
import com.jms.domain.ws.s.WSSso;
import com.jms.repositories.s.SComComRepository;
import com.jms.repositories.s.SSoRepository;
import com.jms.repositories.s.SSoRepositoryCustom;
import com.jms.service.store.SsoService1;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class SsoController1 {
	

	@Autowired private SecurityUtils securityUtils;
	@Autowired private SsoService1 ssoService;
	@Autowired private SSoRepository sSoRepository;
	@Autowired private SSoRepositoryCustom sSoRepositoryCustom;
	@Autowired private SComComRepository sComComRepository;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/saveSo1", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid saveSo(@RequestBody WSSso wsSso) throws Exception {
		return ssoService.saveSSo(wsSso);
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findSo", method=RequestMethod.GET)
	public WSSso findWSSso(@RequestParam("soId") Long soId) throws Exception {
		return ssoService.findSso(soId);
	}
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findSo1", method=RequestMethod.GET)
	public WSSso findWSSso1(@RequestParam("soId") Long soId) throws Exception {
		return ssoService.findSso(soId);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findSoBySoNum", method=RequestMethod.GET)
	public WSSso findWSSsoBySoNum(@RequestParam("soNum") Long soNum) throws Exception {
		
		List<SSo> soList = sSoRepository.findBySoNum(soNum);
		System.out.println("soNum: " + soNum);
		WSSso wsSo = new WSSso();
		if(!soList.isEmpty())
		{
			System.out.println("list is not empty: ");
			SSo so = soList.get(0);
			wsSo.setIdSo(so.getSoNum());
			wsSo.setCodeSo(so.getCodeSo());
			wsSo.setsStatusId(so.getSStatusDic()==null?null:so.getSStatusDic().getId());
			return wsSo;
			//return ssoService.findSso(soId);
			
		}
		else
			return wsSo;
		//return ssoService.findSso(soId);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findCoOrderNosByCompanyCoId", method=RequestMethod.GET)
	public List<WSSelectObj> findCoOrderNosByCompanyCoId(@RequestParam("coId") Long coId) throws Exception {
		List<WSSelectObj> ws =new ArrayList<WSSelectObj>();
		for(String s: sSoRepository.findCoOrderNosByCompanyCoId(coId))
		{
			WSSelectObj w = new WSSelectObj();
			w.setSid(s);
			w.setName(s);
			ws.add(w);
		}
		return ws;
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findCoOrderNosByCompany2Id", method=RequestMethod.GET)
	public List<WSSelectObj> findCoOrderNosByCompany2Id(@RequestParam("idCompany2") Long idCompany2) throws Exception {
		List<WSSelectObj> ws =new ArrayList<WSSelectObj>();
	    //System.out.println("idCompany2: " + idCompany2); //32
	    //here is idComCom!!!!!!!
	    SComCom c = sComComRepository.findOne(idCompany2);
		Long idCompany =securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		//System.out.println("mycompany: " + idCompany); //116
		Long idCompanytwo;
		if(idCompany.equals(c.getIdCompany1()))
		{
			idCompanytwo = c.getIdCompany2();
			//System.out.println("idCompanytwo1: " + idCompanytwo); //119
		}
		else
		{
			idCompanytwo=c.getIdCompany1();
			//System.out.println("idCompanytwo2: " + idCompanytwo); //119
		}
		//116
		 //System.out.println("idCompanytwo: " + idCompanytwo);
		for(String s: sSoRepository.findCoOrderNosByCompany2Id(idCompanytwo))
		{
			WSSelectObj w = new WSSelectObj();
			w.setSid(s);
			w.setName(s);
			ws.add(w);
		}
		return ws;
	}
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getSoList1", method=RequestMethod.POST)
	public WSTableData  getSoList( 
			@RequestParam(required=false,value="q") String q,
			@RequestParam(required=false,value="status") Long status,
			@RequestParam(required=false,value="fromDay") String fromDay,
			@RequestParam(required=false,value="toDay") String toDay,
			@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
	//	System.out.println("get so list : " + status);
		List<SSo> ssos = sSoRepositoryCustom.getCustomSsos(companyId, status, q, fromDay, toDay);
		
		//System.out.println("size: " + ssos.size());
		
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(ssos.size()<start + length)
			end =ssos.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			SSo w = ssos.get(i);
			String status1=(w.getSStatusDic()==null)?"":w.getSStatusDic().getName();
			String unit =(w.getSMaterial().getSUnitDicByUnitPur()==null)?"":w.getSMaterial().getSUnitDicByUnitPur().getName();
			String companyCoShortName =(w.getSCompanyCo()==null)?"":w.getSCompanyCo().getShortName();
			String qtyDel = (w.getQtyDelivered()==null)?"":""+w.getQtyDelivered();
			String un="";
			if(w.getUsers()!=null)
			{
				un = w.getUsers().getName();
			}
			String ma ="";
			if(w.getSMaterial()!=null)
			{
				ma=w.getSMaterial().getPno()+"_"+w.getSMaterial().getRev()+"_"+w.getSMaterial().getDes();
			}
			String dd ="";
			if(w.getDeliveryDate()!=null)
			{
				dd=w.getDeliveryDate().toString();
			}
			String[] d = {w.getCodeSo(),""+w.getDateOrder(),un,companyCoShortName,status1,ma,unit,""+w.getQtySo(),""+w.getTotalAmount(),dd,qtyDel,""+w.getSoNum()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(ssos.size());
		t.setRecordsFiltered(ssos.size());
	    t.setData(lst);
	    return t;
	}
	
	
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getSoListByCompanyCoIdAndOrderNo", method=RequestMethod.GET)
	public List<WSSelectObj> getSoListByCompanyCoIdAndOrderNo(@RequestParam("companyCoId") Long companyCoId,@RequestParam("orderNo") String orderNo) throws Exception {
//		System.out.println("coId: " + companyCoId +", orderNo: " +orderNo);
		List<WSSelectObj>  ws = new ArrayList<WSSelectObj>();
		for(SSo s :sSoRepository.findByCompanyIdAndOrderNo(companyCoId,orderNo))
		{
		
				WSSelectObj w = new WSSelectObj(s.getIdSo(),s.getCodeSo()+"_"+s.getSMaterial().getPno());
				ws.add(w);
		}
		return ws;
	}
	
	
	
	//原来s/getSoListByCompany2IdAndOrderNo
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getSoListByCompany2IdAndOrderNo", method=RequestMethod.GET)
	public List<WSSelectObj> getSoListByCompany2IdAndOrderNo(@RequestParam("company2Id") Long company2Id,@RequestParam("orderNo") String orderNo) throws Exception {

		List<WSSelectObj>  ws = new ArrayList<WSSelectObj>();
		
		for(SSo s :sSoRepository.findByCompany2IdAndOrderNo(company2Id,orderNo))
		{
		
			WSSelectObj w = new WSSelectObj(s.getIdSo(),s.getCodeSo()+"_"+s.getSMaterial().getPno());
			ws.add(w);
			
		}
		return ws;
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getSoListByCompany2IdAndOrderNo1", method=RequestMethod.GET)
	public List<WSSelectObj> getSoListByCompany2IdAndOrderNo1(@RequestParam("company2Id") Long company2Id,@RequestParam("orderNo") String orderNo) throws Exception {
    	System.out.println("coId: " + company2Id +", orderNo: " +orderNo);
    	SComCom c = sComComRepository.findOne(company2Id); 
    	List<WSSelectObj>  ws = new ArrayList<WSSelectObj>();
    	if(c==null)
    	{
    		return ws;
    	}
    	Long idCompany =securityUtils.getCurrentDBUser().getCompany().getIdCompany();
 		Long idCompanytwo;
 		if(idCompany.equals(c.getIdCompany1()))
 		{
			idCompanytwo = c.getIdCompany2(); 
			} 	
 		else		{
			idCompanytwo=c.getIdCompany1();
		}
 		
 		//System.out.println("company2:" + idCompanytwo);
	
		//Map<String,String> map = new HashMap<String,String>();
		for(SSo s :sSoRepository.findByCompany2IdAndOrderNo(idCompanytwo,orderNo))
		{
		
			WSSelectObj w = new WSSelectObj(s.getIdSo(),s.getCodeSo()+"_"+s.getSMaterial().getPno());
			ws.add(w);
			
		}
		return ws;
	}
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getSoListObjs", method=RequestMethod.GET)
	public List<WSSelectObj> getSoList() throws Exception {
		List<WSSelectObj>  ws = new ArrayList<WSSelectObj>();
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		
		for(SSo s :sSoRepository.findByCompanyId(companyId))
		{
			WSSelectObj w = new WSSelectObj(s.getIdSo(),s.getCodeSo()+"_"+s.getSMaterial().getPno());
			ws.add(w);
			
		}
		return ws;
	}

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findOpenedSoList", method=RequestMethod.GET)
	public List<WSSelectObj> findOpenedSoList() throws Exception {
		List<WSSelectObj>  ws = new ArrayList<WSSelectObj>();
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		for(SSo s :sSoRepository.findByCompanyIdAndStatusId(companyId,18l)) //激活状态
		{
		
			WSSelectObj w = new WSSelectObj(s.getIdSo(),s.getCodeSo()+"_"+s.getSMaterial().getPno());
			ws.add(w);
		
		}
		return ws;
	}

	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findOpenedSoListByQ", method=RequestMethod.GET)
	public List<WSSelectObj> findOpenedSoListByQ(@RequestParam(value="q",required=false) String q) throws Exception {
		List<WSSelectObj>  ws = new ArrayList<WSSelectObj>();
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		if(q==null)
		{
			
			for(SSo s :sSoRepository.findByCompanyIdAndStatusId(companyId,18l)) //激活状态
			{
			
				WSSelectObj w = new WSSelectObj(s.getIdSo(),s.getCodeSo()+"_"+s.getSMaterial().getPno());
				ws.add(w);
				
			}
		}
		else
		{
			q = "%"+q+"%";
			for(SSo s :sSoRepository.findByCompanyIdAndStatusIdByQ(companyId,18l,q)) //激活状态
			{
				WSSelectObj w = new WSSelectObj(s.getIdSo(),s.getCodeSo()+"_"+s.getSMaterial().getPno());
				ws.add(w);
				
			}
		}
	
		return ws;
	}

	
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getMaterialDeliveredBySoId", method=RequestMethod.GET)
	public WSMaterialDelivered getMaterialDeliveredBySoId(@RequestParam("soId") Long soId) throws Exception {
		
	//	System.out.println("soId: " + soId);
		SSo sSo = sSoRepository.findOne(soId);
		WSMaterialDelivered wd = new WSMaterialDelivered();
		SMaterial s = sSo.getSMaterial();
	    wd.setIdMaterial(s.getIdMaterial());
	    wd.setPno(s.getPno());
	    wd.setRev(s.getRev());
	    wd.setDes(s.getDes());
	    if(s.getSUnitDicByUnitInf()!=null)
	    {
	    	 wd.setUnitInv(s.getSUnitDicByUnitInf().getName());
	    }
	  
	    wd.setQtySo(sSo.getQtySo());
	   if(sSo.getQtyDelivered()==null)
	   {
		   wd.setQtyDelivered(0l);
	   }
	   else
	   {
		   wd.setQtyDelivered(sSo.getQtyDelivered());
	   }
	
	   wd.setDeliveredDate(sSo.getDeliveryDate());
		return wd;
	}

	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/getMaterialBySoId", method = RequestMethod.GET)
	public WSMaterial findWSMaterial(@RequestParam("soId") Long soId) throws Exception {
		return ssoService.getMaterialBySoId(soId);

	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/saveSSoAutoRemark1", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid saveSSoRemark(@RequestBody WSSSoRemark wsSSoRemark) throws Exception {
		return ssoService.saveSoAutoRemark(wsSSoRemark);
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/check/ssoCode", method=RequestMethod.GET)
	public Valid checkCodeSo(@RequestParam("codeSo") String codeSo) {
		Valid valid = new Valid();
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<SSo> ssos = sSoRepository.findByCompanyIdAndCodeSo(companyId, codeSo);
		if(ssos.isEmpty())
		{
			valid.setValid(true);
			return valid;
		}
		else
		{
			valid.setValid(false);
			valid.setMsg("该销售订单编码已经存在！");
			return valid;
		}
	
	}
}