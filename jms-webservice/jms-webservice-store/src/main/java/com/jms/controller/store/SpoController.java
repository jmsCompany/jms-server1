package com.jms.controller.store;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.jms.domain.db.SAttachment;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SPo;
import com.jms.domain.db.SPoMaterial;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.s.WSSpo;
import com.jms.domain.ws.s.WSSpoMaterial;
import com.jms.domain.ws.s.WSSpoRemark;
import com.jms.file.FileMeta;
import com.jms.file.FileUploadService;
import com.jms.repositories.s.SAttachmentRepository;
import com.jms.repositories.s.SSpoMaterialRepositoryCustom;
import com.jms.repositories.s.SSpoRepository;
import com.jms.service.store.SpoMaterialService;
import com.jms.service.store.SpoService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class SpoController {
	
	@Autowired private SecurityUtils securityUtils;
	@Autowired private SpoService spoService;
	@Autowired private SSpoRepository sSpoRepository;
	@Autowired private SpoMaterialService spoMaterialService;
	@Autowired private FileUploadService fileUploadService;
	@Autowired private SAttachmentRepository sAttachmentRepository;
	@Autowired private SSpoMaterialRepositoryCustom sSpoMaterialRepositoryCustom;
	private static final Logger logger = LogManager.getLogger(SpoController.class
			.getCanonicalName());
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/saveSpo", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSSpo saveSpo(@RequestBody WSSpo wsSpo) throws Exception {
		//System.out.println("save Spo!");
		return spoService.saveSpo(wsSpo);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/uploadSpoAttachment", method = RequestMethod.POST)
	public FileMeta uploadSpoAttachment(@RequestParam(required=false, value="spoId") Long  spoId, MultipartHttpServletRequest request, HttpServletResponse response) {
	
		//logger.debug("upload spo attachment: spo id: " +  spoId);
		FileMeta fileMeta = new FileMeta();
		if (request.getFileNames().hasNext()) {
			fileMeta = fileUploadService.upload(request, response,false);
			SAttachment spic = new SAttachment();
			spic.setOrgFilename(fileMeta.getOrgName());
			spic.setFilename(fileMeta.getFileName());
			//logger.debug("orgin file name: " +  fileMeta.getOrgName() +", file name in server: " + fileMeta.getFileName());
			spic.setUsers(securityUtils.getCurrentDBUser());
			spic = sAttachmentRepository.save(spic);
			fileMeta.setFileId(spic.getId());
			fileMeta.setBytes(null);
			if(spoId!=null&&!spoId.equals(0l))
			{
				SPo spo = sSpoRepository.findOne(spoId);
			    spo.setSAttachment(spic);
			    sSpoRepository.save(spo);
			}

			
		}
		else
		{
			logger.debug("no file was uploaded");
		}
		return fileMeta;
	}
	
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/saveSpoRemark", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid saveSpoRemark(@RequestBody WSSpoRemark wsSpoRemark) throws Exception {
		return spoService.saveSpoRemark(wsSpoRemark);
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/deleteSpo", method=RequestMethod.GET) 
	public Valid deleteSpo(@RequestParam("spoId") Long spoId) {
		return spoService.deleteSpo(spoId);
	}


	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findSpo", method=RequestMethod.GET)
	public WSSpo findWSSpo(@RequestParam("spoId") Long spoId) throws Exception {
		return spoService.findSpo(spoId);
	}
	

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/spoMaterialList", method=RequestMethod.POST)
	public WSTableData  getSpoList( 
			@RequestParam(required=false,value="q") String q,
			@RequestParam(required=false,value="fromDay") String fromDay,
			@RequestParam(required=false,value="toDay") String toDay,
			@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<SPoMaterial> spoMaterials = sSpoMaterialRepositoryCustom.getCustomSpoMaterials(companyId, q, fromDay, toDay);
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(spoMaterials.size()<start + length)
			end =spoMaterials.size();
		else
			end =start + length;
//		6、8、9、11
		for (int i = start; i < end; i++) {
			SPoMaterial w = spoMaterials.get(i);
			String del = (w.getDeliveryDate()==null)?"":w.getDeliveryDate().toString();
			String pno="";
			String rev="";
			String des="";
			SMaterial s = w.getSMaterial();
			if(s!=null)
			{
				 pno=(s.getPno()==null)?"":s.getPno();
				 rev=(s.getRev()==null)?"":s.getRev();
				 des=(s.getDes()==null)?"":s.getDes();
			}
	
			String qtyPo=(w.getQtyPo()==null)?"":""+w.getQtyPo();
			String totalPrice =(w.getTotalPrice()==null)?"":""+w.getTotalPrice();
			String qtyRev =(w.getQtyReceived()==null)?"":""+w.getQtyReceived();
			String userName ="";
			if(w.getSPo().getUsers()!=null)
			{
				if(w.getSPo().getUsers().getUsername()!=null)
				userName =w.getSPo().getUsers().getUsername();
			}
			String coShortName= "";
			if(w.getSPo().getSCompanyCo()!=null)
			{
				coShortName	=w.getSPo().getSCompanyCo().getShortName();
			}
			String status="";
			if(w.getSStatusDic()!=null)
			{
				status= w.getSStatusDic().getName();
			}
			String unit="";
			if(w.getSMaterial().getSUnitDicByUnitPur()!=null)
			{
				unit = w.getSMaterial().getSUnitDicByUnitPur().getName();
			}
			
			String[] d = {w.getSPo().getCodePo(),""+w.getSPo().getDateOrder(),userName,coShortName,status,pno+"_"+rev+"_"+des,unit,qtyPo,totalPrice,del,qtyRev,""+w.getSPo().getIdPo()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(spoMaterials.size());
		t.setRecordsFiltered(spoMaterials.size());
	    t.setData(lst);
	    return t;
	}

	@Transactional(readOnly = true)
	@RequestMapping(value="/s/spoList", method=RequestMethod.GET)
	public List<WSSelectObj> findSpoList(@RequestParam("codeCo") Long codeCo) throws Exception {
		return spoService.findSpoListByCodeCo(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), codeCo);
	}
	

	
}