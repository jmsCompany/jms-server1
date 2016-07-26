package com.jms.controller.store;


import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.jms.domain.db.SBin;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMaterialBins;
import com.jms.domain.db.SMaterialCategory;
import com.jms.domain.db.SMaterialPic;
import com.jms.domain.db.SMaterialTypeDic;
import com.jms.domain.db.SPic;
import com.jms.domain.db.SPoMaterial;
import com.jms.domain.db.SUnitDic;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.f.WSFCostCenter;
import com.jms.domain.ws.s.WSLotNo;
import com.jms.domain.ws.s.WSMaterial;
import com.jms.domain.ws.s.WSMaterialBin;
import com.jms.domain.ws.s.WSSpoMaterial;
import com.jms.file.FileMeta;
import com.jms.file.FileUploadService;
import com.jms.repositories.s.SBinRepository;
import com.jms.repositories.s.SMaterialBinsRepository;
import com.jms.repositories.s.SMaterialCategoryRepository;
import com.jms.repositories.s.SMaterialPicRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SMaterialTypeDicRepository;
import com.jms.repositories.s.SMtfMaterialRepository;
import com.jms.repositories.s.SPicRepository;
import com.jms.repositories.s.SSpoMaterialRepository;
import com.jms.repositories.s.SStkRepository;
import com.jms.repositories.s.SUnitDicRepository;
import com.jms.service.store.CostCenterService;
import com.jms.service.store.MaterialService;
import com.jms.service.store.SMaterialBinsService;
import com.jms.service.store.SpoMaterialService;
import com.jms.web.security.SecurityUtils;

@RestController
@Transactional(readOnly = true)
public class MaterialBinsController {

	@Autowired
	private SMaterialBinsService sMaterialBinsService;
	@Autowired private SecurityUtils securityUtils;
	@Autowired private SMaterialRepository sMaterialRepository;
	@Autowired
	private SMaterialBinsRepository sMaterialBinsRepository;
	
	@Autowired
	private SBinRepository sBinRepository;
	@Autowired
	private SStkRepository sStkRepository;



	
	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/saveMaterialBin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Valid saveMaterialBin(@RequestBody WSMaterialBin wsMaterialBin) throws Exception {
		return sMaterialBinsService.saveWSMaterialBin(wsMaterialBin);
	}

	
	
	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/deleteMaterialBin", method = RequestMethod.GET)
	public Valid deleteMaterialBin(@RequestParam("materialId") Long materialId,@RequestParam("binId") Long binId) {
		return sMaterialBinsService.deleteMaterialBins(binId, materialId);

	}
	


	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/materialBinList", method = RequestMethod.POST)
	public WSTableData getMaterialList( @RequestParam Integer draw,
			@RequestParam Integer start, @RequestParam Integer length) throws Exception {

		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
	
		List<WSMaterialBin> wsmbs = new ArrayList<WSMaterialBin>();
		
		for(SMaterialBins mb: sMaterialBinsRepository.getByIdCompany(companyId))
		{
			WSMaterialBin w = new WSMaterialBin();
			w.setBinName(sBinRepository.getOne(mb.getId().getIdBin()).getBin());
			w.setIdBin(mb.getId().getIdBin());
			w.setPno(sMaterialRepository.findOne(mb.getId().getIdMaterial()).getPno());
			w.setIdMaterial(mb.getId().getIdMaterial());
			w.setStkName(sStkRepository.findOne(mb.getIdStk()).getStkName());
			w.setIdStk(mb.getIdStk());
			wsmbs.add(w);
		}
		
		
		
		List<String[]> lst = new ArrayList<String[]>();
		int end = 0;
		if (wsmbs.size() < start + length)
			end = wsmbs.size();
		else
			end = start + length;
		for (int i = start; i < end; i++) {
			WSMaterialBin w = wsmbs.get(i);
			String[] d = { w.getPno(),w.getStkName(),w.getBinName(),w.getIdMaterial()+"_"+w.getIdBin() };
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(wsmbs.size());
		t.setRecordsFiltered(wsmbs.size());
		t.setData(lst);
		return t;
	}
	



	


}