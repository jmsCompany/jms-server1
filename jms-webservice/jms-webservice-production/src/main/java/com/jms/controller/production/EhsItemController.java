package com.jms.controller.production;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jms.domain.db.EhsItem;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.ehs.WSEHSItem;
import com.jms.repositories.p.EhsItemRepository;
import com.jms.service.production.EhsItemService;
import com.jms.web.security.SecurityUtils;



@RestController
@Transactional(readOnly=true)
public class EhsItemController {
	
	@Autowired private EhsItemService ehsItemService;
	@Autowired private EhsItemRepository ehsItemRepository;
	@Autowired private SecurityUtils securityUtils;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/e/saveWSEHSItem", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSEHSItem saveWSEHSItem(@RequestBody WSEHSItem wsEHSItem) throws Exception {
		return ehsItemService.saveWSEHSItem(wsEHSItem);
	}


	@Transactional(readOnly = true)
	@RequestMapping(value="/e/findWSEHSItem", method=RequestMethod.GET)
	public WSEHSItem findWSEHSItem(@RequestParam("idEhs") Long idEhs) throws Exception{
		return ehsItemService.findWSEHSItem(idEhs);
		
	}
	@Transactional(readOnly = false)
	@RequestMapping(value="/e/deleteWSEHSItem", method=RequestMethod.GET)
	public Valid deleteWSEHSItem(@RequestParam("idEhs") Long idEhs) {
		return ehsItemService.deleteWSEHSItem(idEhs);
		
	}
	@Transactional(readOnly = true)
	@RequestMapping(value="/e/getWSEHSItemList", method=RequestMethod.POST)
	public WSTableData  getWSEHSItemList(
			@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<EhsItem> ehsItemList =ehsItemRepository.findByIdCompany(companyId);
		
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(ehsItemList.size()<start + length)
			end =ehsItemList.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			EhsItem w = ehsItemList.get(i);
			String sType ="";
			if(w.getType()!=null&&w.getType().equals(1l))
			{
				sType ="不重要";
			}
			else
			{
				sType ="重要";
			}
			String[] d = {w.getDes(),sType,""+w.getIdEhs()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(ehsItemList.size());
		t.setRecordsFiltered(ehsItemList.size());
	    t.setData(lst);
	    return t;
	}
	
}