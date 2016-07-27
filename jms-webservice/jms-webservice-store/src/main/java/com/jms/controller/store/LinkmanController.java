package com.jms.controller.store;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.s.WSLinkman;
import com.jms.service.store.LinkmanService;


@RestController
@Transactional(readOnly=true)
public class LinkmanController {
	

	
	@Autowired private LinkmanService linkmanService;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/saveLinkman", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSLinkman saveLinkman(@RequestBody WSLinkman wsLinkman) throws Exception {
	//	System.out.println("email from controller: " + wsLinkman.getEmail());
		return linkmanService.saveWSLinkman(wsLinkman);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/deleteLinkman", method=RequestMethod.GET)
	public Valid deleteLinkman(@RequestParam("linkmanId") Long linkmanId) {
		return linkmanService.deleteLinkman(linkmanId);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/s/findLinkman", method=RequestMethod.GET)
	public WSLinkman findLinkman(@RequestParam("linkmanId") Long linkmanId) throws Exception {
		return linkmanService.findLinkman(linkmanId);
		
	}
	

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/LinkmanList", method=RequestMethod.POST)
	public WSTableData  getLinkmanList(@RequestParam("companyCoId") Long companyCoId, @RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		List<WSLinkman> wsLinkmen = linkmanService.getLinkmans(companyCoId);
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(wsLinkmen.size()<start + length)
			end =wsLinkmen.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			WSLinkman w = wsLinkmen.get(i);
			String[] d = {w.getName(),w.getPosition(),""+w.getPhoneNo(),""+w.getStatus(),""+w.getIdLinkman()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(wsLinkmen.size());
		t.setRecordsFiltered(wsLinkmen.size());
	    t.setData(lst);
	    return t;
	}
	
	


}