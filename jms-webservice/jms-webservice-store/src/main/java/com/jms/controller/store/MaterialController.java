package com.jms.controller.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.io.ByteStreams;
import com.jms.domain.db.SBin;
import com.jms.domain.db.SMaterial;
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
import com.jms.domain.ws.store.WSLotNo;
import com.jms.domain.ws.store.WSMaterial;
import com.jms.domain.ws.store.WSSpoMaterial;
import com.jms.file.FileMeta;
import com.jms.file.FileUploadService;
import com.jms.repositories.s.SMaterialCategoryRepository;
import com.jms.repositories.s.SMaterialPicRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SMaterialTypeDicRepository;
import com.jms.repositories.s.SMtfMaterialRepository;
import com.jms.repositories.s.SPicRepository;
import com.jms.repositories.s.SSpoMaterialRepository;
import com.jms.repositories.s.SUnitDicRepository;
import com.jms.service.store.CostCenterService;
import com.jms.service.store.MaterialService;
import com.jms.service.store.SpoMaterialService;
import com.jms.web.security.SecurityUtils;

@RestController
@Transactional(readOnly = true)
public class MaterialController {

	@Autowired
	private MaterialService materialService;
	@Autowired
	private SMaterialTypeDicRepository sMaterialTypeDicRepository;
	@Autowired
	private SMaterialCategoryRepository sMaterialCategoryRepository;
	@Autowired
	private SUnitDicRepository sUnitDicRepository;
	@Autowired
	private SecurityUtils securityUtils;
	@Autowired
	private CostCenterService costCenterService;
	@Autowired
	private SMaterialRepository sMaterialRepository;
	@Autowired
	private SSpoMaterialRepository sSpoMaterialRepository;
	@Autowired
	private SpoMaterialService spoMaterialService;

	@Autowired
	private SMtfMaterialRepository sMtfMaterialRepository;

    @Value("${filePath}")
	private String filePath;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private SPicRepository sPicRepository;

	@Autowired
	private SMaterialPicRepository sMaterialPicRepository;

	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/saveMaterial", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public WSMaterial saveWSMaterial(@RequestBody WSMaterial wsMaterial) throws Exception {
		return materialService.saveMaterial(wsMaterial);
	}

	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/deleteMaterial", method = RequestMethod.GET)
	public Valid deleteMaterial(@RequestParam("materialId") Long materialId) {
		return materialService.deleteMaterial(materialId);

	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/findMaterial", method = RequestMethod.GET)
	public WSMaterial findWSMaterial(@RequestParam("materialId") Long materialId) throws Exception {
		return materialService.findMaterial(materialId);

	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/materialList", method = RequestMethod.GET)
	public WSTableData getMaterialList(@RequestParam(value = "materialTypeId", required = false) Long materialTypeId,
			@RequestParam(value = "q", required = false) String q, @RequestParam Integer draw,
			@RequestParam Integer start, @RequestParam Integer length) throws Exception {

		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<WSMaterial> wsMaterials = materialService.getMaterials(companyId, materialTypeId, q);
		List<String[]> lst = new ArrayList<String[]>();
		int end = 0;
		if (wsMaterials.size() < start + length)
			end = wsMaterials.size();
		else
			end = start + length;
		for (int i = start; i < end; i++) {
			WSMaterial w = wsMaterials.get(i);
			String[] d = { w.getPno(), w.getDes(), w.getRev(), w.getsMaterialTypeDic(), w.getMaterialCategory(),
					w.getsUnitDicByUnitInf(), w.getsStatusDic(), "" + w.getIdMaterial() };
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(wsMaterials.size());
		t.setRecordsFiltered(wsMaterials.size());
		t.setData(lst);
		return t;
	}
	
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/getMaterialListObjs", method = RequestMethod.GET)
	public List<WSSelectObj> getMaterialListObjs(
			@RequestParam(value = "q", required = false) String q) throws Exception {
		//System.out.println("find material by q: " + q);

		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<WSSelectObj> ws =new ArrayList<WSSelectObj>();
		List<WSMaterial> wsMaterials = materialService.getMaterials(companyId, null, q);
		
		for (WSMaterial m:wsMaterials) {
		
			WSSelectObj w = new WSSelectObj(m.getIdMaterial(),m.getPno()+"_"+m.getRev()+"_"+m.getDes());
			ws.add(w);
		}

		return ws;
	}

	

	// 物料大类
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/materialTypes", method = RequestMethod.GET)
	public List<WSSelectObj> findMaterialTypes() {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		for (SMaterialTypeDic s : sMaterialTypeDicRepository.findAll()) {
			wso.add(new WSSelectObj(s.getId(), s.getName()));
		}
		return wso;
	}

	// 物料小类
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/materialCategories", method = RequestMethod.GET)
	public List<WSSelectObj> findMateriaCategories() {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		for (SMaterialCategory s : sMaterialCategoryRepository
				.findByIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany())) {
			wso.add(new WSSelectObj(s.getId(), s.getName()));
		}
		return wso;
	}

	// 物料Unit
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/units", method = RequestMethod.GET)
	public List<WSSelectObj> findUnits() {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		for (SUnitDic s : sUnitDicRepository.findAll()) {
			wso.add(new WSSelectObj(s.getId(), s.getName()));
		}
		return wso;
	}

	// costCenter
	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/costCenters", method = RequestMethod.GET)
	public List<WSSelectObj> findCostCenters() throws Exception {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		for (WSFCostCenter s : costCenterService.getCostCenterList()) {
			wso.add(new WSSelectObj(s.getIdCostCenter(), s.getDes()));
		}
		return wso;
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/materials", method = RequestMethod.GET)
	public List<WSSelectObj> findMaterials(@RequestParam(required=false, value="materialId") Long materialId) {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		for (SMaterial s : sMaterialRepository
				.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany())) {
			wso.add(new WSSelectObj(s.getIdMaterial(), s.getPno() + "-" + s.getDes() + "-" + s.getRev()));
		}
		return wso;
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/findPoMaterialsBySpoId", method = RequestMethod.GET)
	public List<WSSelectObj> findPoMaterialsBySpoId(@RequestParam("spoId") Long spoId) {
		// System.out.println("find material by spo id: " + spoId);
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		for (SPoMaterial s : sSpoMaterialRepository.getBySpoId(spoId)) {
			// System.out.println("add s");
			wso.add(new WSSelectObj(s.getIdPoMaterial(),
					s.getSMaterial().getPno() + "-" + s.getSMaterial().getDes() + "-" + s.getSMaterial().getRev()));
		}
		return wso;
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/findMaterialsBySpoMaterialId", method = RequestMethod.GET)
	public WSSpoMaterial findMaterialsBySpoMaterialId(@RequestParam("spoMaterialId") Long spoMaterialId)
			throws Exception {

		SPoMaterial spoMaterial = sSpoMaterialRepository.findOne(spoMaterialId);
		return spoMaterialService.toWSSpoMaterial(spoMaterial);

	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/findLotNos", method = RequestMethod.GET)
	public List<WSLotNo> findLotNos(@RequestParam("spoId") Long spoId, @RequestParam("materialId") Long materialId) {
		List<WSLotNo> wso = new ArrayList<WSLotNo>();
		for (String s : sMtfMaterialRepository.getLotNosBySpoIdAndMaterialId(spoId, materialId)) {
			wso.add(new WSLotNo(s, s));
		}
		return wso;
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/s/findToBinsBySpoIdAndMaterialID", method = RequestMethod.GET)
	public List<WSSelectObj> findToBinsBySpoIdAndMaterialID(@RequestParam("spoId") Long spoId,
			@RequestParam("materialId") Long materialId) {
		List<WSSelectObj> wso = new ArrayList<WSSelectObj>();
		for (SBin s : sMtfMaterialRepository.getToBinsBySpoIdAndMaterialId(spoId, materialId)) {
			wso.add(new WSSelectObj(s.getIdBin(), s.getBin()));
		}
		return wso;
	}

	@Transactional(readOnly = false)
	@RequestMapping(value = "/s/uploadMaterialImage", method = RequestMethod.POST)
	public FileMeta uploadFile(@RequestParam("materialId") Long materialId, MultipartHttpServletRequest request,
			HttpServletResponse response) {
		FileMeta fileMeta = new FileMeta();
		if (request.getFileNames().hasNext()) {
			fileMeta = fileUploadService.upload(request, response,false);
			SPic spic = new SPic();
			spic.setOrgFilename(fileMeta.getOrgName());
			spic.setFilename(fileMeta.getFileName());
			spic.setUsers(securityUtils.getCurrentDBUser());
			spic = sPicRepository.save(spic);
			fileMeta.setFileId(spic.getId());
			fileMeta.setBytes(null);
			if (materialId != null && !materialId.equals(0l)) {
				SMaterial sMaterial = sMaterialRepository.findOne(materialId);
				SMaterialPic sp;
				List<SMaterialPic> mps = sMaterialPicRepository.findBySMaterialId(sMaterial.getIdMaterial());
				if (mps != null && !mps.isEmpty()) {
					sp = mps.get(0);
				} else {
					sp = new SMaterialPic();
					sp.setSMaterial(sMaterial);
				}
				sp.setSPic(spic);
				sMaterialPicRepository.save(sp);
			}

		}
		return fileMeta;
	}



}