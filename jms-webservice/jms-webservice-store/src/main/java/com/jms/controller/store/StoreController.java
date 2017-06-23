package com.jms.controller.store;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.SBin;
import com.jms.domain.db.SStk;
import com.jms.domain.db.SYesOrNoDic;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.s.WSBin;
import com.jms.domain.ws.s.WSMaterialCategory;
import com.jms.domain.ws.s.WSSStatus;
import com.jms.domain.ws.s.WSStk;
import com.jms.domain.ws.s.WSStkType;
import com.jms.domain.ws.s.WSYesAndNoType;
import com.jms.repositories.s.SStkRepository;
import com.jms.repositories.s.SYesOrNoDicRepository;
import com.jms.service.store.MaterialCategoryService;
import com.jms.service.store.SBinService;
import com.jms.service.store.SStatusDicService;
import com.jms.service.store.SStkService;
import com.jms.service.store.SStkTypeDicService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class StoreController {
	
	@Autowired private MaterialCategoryService materialCategoryService;
	@Autowired private SStkTypeDicService sStkTypeDicService;
	@Autowired private SStatusDicService sStatusDicService;
	@Autowired private SStkService sStkService;
	@Autowired private SStkRepository sStkRepository;
	@Autowired private SecurityUtils securityUtils;
	@Autowired private SBinService sBinService;
	

	@Autowired private SYesOrNoDicRepository sYesOrNoDicRepository;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/saveMaterialCategory", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSMaterialCategory saveMaterialCategory(@RequestBody WSMaterialCategory wSMaterialCategory) throws Exception {
		return materialCategoryService.saveMaterialCategory(wSMaterialCategory);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getStkTypeList", method=RequestMethod.GET)
	public List<WSStkType> getStkTypeList() {
		return sStkTypeDicService.getStkTypes();
	}

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getIsReturnShelfList", method=RequestMethod.GET)
	public List<WSYesAndNoType> getYesAndNoList() {
		List<SYesOrNoDic>  types = sYesOrNoDicRepository.findAll();
		List<WSYesAndNoType> wsTypes = new ArrayList<WSYesAndNoType>();
		for(SYesOrNoDic s:types)
		{
			WSYesAndNoType w = new WSYesAndNoType();
			w.setId(s.getId());
			w.setName(s.getName());
			wsTypes.add(w);
		}
		return wsTypes;
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getSStatusList", method=RequestMethod.GET)
	public List<WSSStatus> getSStatusList(@RequestParam("source") String source,@RequestParam(value="removeEdit",required=false) Boolean removeEdit) {
		if(removeEdit==null){
			removeEdit=false;
		}
		return sStatusDicService.getSStatus(source,removeEdit);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/saveStk", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid saveStk(@RequestBody WSStk wsStk) {
		return sStkService.saveStk(wsStk);
		
	}

	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/saveBin", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid saveBin(@RequestBody WSBin wsBin) {
		return sBinService.saveBin(wsBin);
		
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getStks", method=RequestMethod.GET)
	public List<WSSelectObj> getStks(@RequestParam("statusId") Long statusId) {
		return sStkService.findStksSelectObj(statusId);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getStksByTypes", method=RequestMethod.GET)
	public List<WSSelectObj> getStksByTypes(@RequestParam("statusId") Long statusId,@RequestParam("types") List<Long> types) {
	
		return sStkService.findStksSelectObjByTypes(statusId, types);
	}
	
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getBins", method=RequestMethod.GET)
	public List<WSSelectObj> geBins(@RequestParam Long idStk) {
		System.out.println("get bins by idStk: " + idStk);
		return sBinService.findBinsObjs(idStk);
	}
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getBinsByStkIdAndMaterialIdAMethod", method=RequestMethod.GET)
	public List<WSSelectObj> getBinsByStkIdAndMaterialIdAMethod(@RequestParam Long idStk,@RequestParam Long idMaterial) {
		System.out.println("A method: idStk: " + idStk +  ", idMaterial " + idMaterial);
		return sBinService.getBinsByStkIdAndMaterialIdAMethod(idStk, idMaterial);
	}
	
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getBinsByStkIdAndMaterialIdBMethod", method=RequestMethod.GET)
	public List<WSSelectObj> getBinsByStkIdAndMaterialIdBMethod(@RequestParam Long idStk,@RequestParam Long idMaterial) {
		System.out.println("B method: idStk: " + idStk +  ", idMaterial " + idMaterial);
		return sBinService.getBinsByStkIdAndMaterialIdBMethod(idStk, idMaterial);
	}
	
	
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getBinsByStkIdAndMaterialIdCMethod", method=RequestMethod.GET)
	public List<WSSelectObj> getBinsByStkIdAndMaterialIdCMethod(@RequestParam Long idStk,@RequestParam Long idMaterial) {
		System.out.println("C method: idStk: " + idStk +  ", idMaterial " + idMaterial);
		return sBinService.getBinsByStkIdAndMaterialIdCMethod(idStk, idMaterial);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getSystemBinByStkTypeIdAndBinName", method=RequestMethod.GET)
	public WSBin getSystemBinByStkTypeIdAndBinName(@RequestParam Long stkTypeId,@RequestParam String binName) {
		return sBinService.getSystemBinByStkTypeIdAndBinName(stkTypeId,binName);
	}
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/stkList", method=RequestMethod.POST)
	public WSTableData  getStklList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {
		List<SStk> stks = sStkRepository.findByIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		List<String[]> lst = new ArrayList<String[]>();
	
		int end=0;
		if(stks.size()<start + length)
			end =stks.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			String[] d = { stks.get(i).getStkName(), stks.get(i).getDes() ,stks.get(i).getAddress(),stks.get(i).getSStkTypeDic().getName(),stks.get(i).getSStatusDic().getName(),""+stks.get(i).getId()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(stks.size());
		t.setRecordsFiltered(stks.size());
	    t.setData(lst);
	    return t;
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/invalidateStk", method=RequestMethod.GET)
	public Valid invalidateStk(@RequestParam("stkId") Long stkId) {
		return sStkService.invalidateStk(stkId);
		
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/deleteStk", method=RequestMethod.GET)
	public Valid deleteStk(@RequestParam("stkId") Long stkId) {
		return sStkService.deleteStk(stkId);
		
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/deleteBin", method=RequestMethod.GET)
	public Valid deleteBin(@RequestParam("binId") Long binId) {
		return sBinService.deleteBin(binId);
		
	}
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/deleteMaterialCategory", method=RequestMethod.GET)
	public Valid deleteMaterialCategory(@RequestParam("materialCategoryId") Long materialCategoryId) {
		return materialCategoryService.deleteMaterialCategory(materialCategoryId);
		
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findStk", method=RequestMethod.GET)
	public WSStk findStk(@RequestParam("stkId") Long stkId) {
		return sStkService.findStk(stkId);
		
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findStkByStkName", method=RequestMethod.GET)
	public WSStk findStkByStkName(@RequestParam("stkName") String stkName) {
		return sStkService.findByStkName(stkName);
		
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findBin", method=RequestMethod.GET)
	public WSBin findBin(@RequestParam("binId") Long binId) {
		return sBinService.findBin(binId);
		
	}
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findMaterialCategory", method=RequestMethod.GET)
	public WSMaterialCategory findMaterialCategory(@RequestParam("materialCategoryId") Long materialCategoryId) {
		return materialCategoryService.findMaterialCategory(materialCategoryId);
		
	}
	
	

	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/getBinList", method=RequestMethod.POST)
	public WSTableData  getBinlList(@RequestParam Long idStk, @RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		List<WSBin> wsBins = sBinService.findBins(idStk);	
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(wsBins.size()<start + length)
			end =wsBins.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			WSBin w = wsBins.get(i);
			String des = (w.getDes()==null)?"":w.getDes();
			String[] d = { w.getBin(),des,w.getIsReturnShelfName(),w.getStatusName(),""+w.getIdBin()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(wsBins.size());
		t.setRecordsFiltered(wsBins.size());
	    t.setData(lst);
	    return t;
	}
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/materialCategoryList", method=RequestMethod.POST)
	public WSTableData  getMaterialCategoryList( @RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {
		List<WSMaterialCategory> mcs = materialCategoryService.getMaterialCategories(securityUtils.getCurrentDBUser().getCompany().getIdCompany());	
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(mcs.size()<start + length)
			end =mcs.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			String[] d = { mcs.get(i).getName(),mcs.get(i).getDes(),mcs.get(i).getStatusName(),""+mcs.get(i).getId()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(mcs.size());
		t.setRecordsFiltered(mcs.size());
	    t.setData(lst);
	    return t;
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="s/checkStkName", method=RequestMethod.GET)
	public Valid checkStkName(@RequestParam("stkName") String stkName,@RequestParam(required=false,value="idStk") Long idStk) throws Exception {
		Boolean returnVal= sStkService.checkStkName(stkName, idStk);
		Valid valid = new Valid();
		valid.setValid(returnVal);
		return valid;
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="s/checkBinName", method=RequestMethod.GET)
	public Valid checkBinName(@RequestParam("bin") String bin,@RequestParam(required=false,value="idBin") Long idBin) throws Exception {
		Boolean returnVal= sBinService.checkBinName(bin, idBin);
		Valid valid = new Valid();
		valid.setValid(returnVal);
		return valid;
	}


}