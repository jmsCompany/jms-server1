package com.jms.controller.quality;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.QCheckList;
import com.jms.domain.db.QTester;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.production.WSPRoutine;
import com.jms.domain.ws.q.WSQCheckList;
import com.jms.repositories.q.QCheckListRepository;
import com.jms.repositories.q.QTesterRepository;
import com.jms.service.quality.CheckListService;
import com.jms.service.quality.TesterService;

@RestController
@Transactional(readOnly = true)
public class CheckListController {

	@Autowired
	private CheckListService checkListService;
	@Autowired
	private QCheckListRepository qCheckListRepository;

	@Transactional(readOnly = false)
	@RequestMapping(value="/q/saveCheckList", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSQCheckList saveCheckList(@RequestBody WSQCheckList wsQCheckList) throws Exception {
		return checkListService.saveCheckList(wsQCheckList);
	}
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/q/deleteCheckList", method=RequestMethod.GET)
	public Valid deleteCheckList(@RequestParam("checkListId") Long checkListId) {
		return checkListService.deleteCheckList(checkListId);
		
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/q/findCheckList", method=RequestMethod.GET)
	public WSQCheckList findCheckList(@RequestParam("checkListId") Long checkListId) throws Exception {
		return checkListService.findCheckList(checkListId);
		
	}
	
	
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/q/getCheckList", method=RequestMethod.GET)
	public WSTableData  getCheckList(@RequestParam("routineDId") Long routineDId, @RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		List<QCheckList> checkList =qCheckListRepository.getByRoutineDId(routineDId);
		
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(checkList.size()<start + length)
			end =checkList.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			QCheckList w = checkList.get(i);
			String[] d = {w.getItem(),w.getTestMethod(),w.getSpecification(),w.getSMaterial().getPno()+"-"+w.getSMaterial().getRev()+"-"+w.getSMaterial().getDes(),""+w.getFrequency(),""+w.getQTester().getDes(),""+w.getIdCheckList()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(checkList.size());
		t.setRecordsFiltered(checkList.size());
	    t.setData(lst);
	    return t;
	}
	


}