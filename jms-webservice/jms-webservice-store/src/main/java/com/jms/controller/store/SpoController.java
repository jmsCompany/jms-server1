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
import com.jms.domain.db.SPo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.store.WSSpo;
import com.jms.domain.ws.store.WSSpoMaterial;
import com.jms.domain.ws.store.WSSpoRemark;
import com.jms.file.FileMeta;
import com.jms.file.FileUploadService;
import com.jms.repositories.s.SAttachmentRepository;
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
	
		logger.debug("upload spo attachment: spo id: " +  spoId);
		FileMeta fileMeta = new FileMeta();
		if (request.getFileNames().hasNext()) {
			fileMeta = fileUploadService.upload(request, response);
			SAttachment spic = new SAttachment();
			spic.setOrgFilename(fileMeta.getOrgName());
			spic.setFilename(fileMeta.getFileName());
			logger.debug("orgin file name: " +  fileMeta.getOrgName() +", file name in server: " + fileMeta.getFileName());
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
	@RequestMapping(value="/s/spoMaterialList", method=RequestMethod.GET)
	public WSTableData  getSpoList( @RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<WSSpoMaterial> wsSpoMaterials = spoMaterialService.findWSSpos(companyId);
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(wsSpoMaterials.size()<start + length)
			end =wsSpoMaterials.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			WSSpoMaterial w = wsSpoMaterials.get(i);
			String del = (w.getDeliveryDate()==null)?"":w.getDeliveryDate().toString();
			String[] d = {w.getCodePo(),""+w.getDateOrder(),w.getUsername(),w.getCodeCo(),w.getsStatus(),w.getsMaterial(),w.getRev(),w.getDes(),w.getUnit(),""+w.getQtyPo(),""+w.getTotalPrice(),del,""+w.getQtyReceived(),""+w.getsPoId()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(wsSpoMaterials.size());
		t.setRecordsFiltered(wsSpoMaterials.size());
	    t.setData(lst);
	    return t;
	}

	@Transactional(readOnly = true)
	@RequestMapping(value="/s/spoList", method=RequestMethod.GET)
	public List<WSSelectObj> findSpoList(@RequestParam("codeCo") Long codeCo) throws Exception {
		return spoService.findSpoListByCodeCo(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), codeCo);
	}
	

	
}