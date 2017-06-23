package com.jms.controller.maintenance;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.MDept;
import com.jms.domain.db.MMachineGroup;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.m.WSMDept;
import com.jms.domain.ws.m.WSMachineGroup;
import com.jms.repositories.m.MDeptRepository;
import com.jms.service.maintenance.MDeptService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class MDeptController {
	

	@Autowired private MDeptService mDeptService;
	@Autowired private MDeptRepository mDeptRepository;
	@Autowired private SecurityUtils securityUtils;

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/m/getDept", method=RequestMethod.GET)
	public List<WSSelectObj> getDepts() {
		return mDeptService.getDepts();
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/m/saveDept", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSMDept saveDept(@RequestBody WSMDept wsMDept) throws Exception  {
		return mDeptService.saveWSMDept(wsMDept);
	}


	@Transactional(readOnly = false)
	@RequestMapping(value="/m/deleteDept", method=RequestMethod.GET)
	public Valid deleteDept(@RequestParam("idDept") Long idDept) {
		return mDeptService.deleteDept(idDept);
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/m/findDept", method=RequestMethod.GET)
	public WSMDept findDept(@RequestParam("idDept") Long idDept) throws Exception {
		
		
		MDept mDept =mDeptRepository.findOne(idDept);
		WSMDept dept = new WSMDept();
		dept.setId(mDept.getIdDept());
		dept.setDes(mDept.getDes());
		return dept;
		
	}
	

	@Transactional(readOnly = true)
	@RequestMapping(value="/m/deptTable", method=RequestMethod.POST)
	public WSTableData  deptTable(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		List<MDept> mgs =mDeptRepository.findByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(mgs.size()<start + length)
			end =mgs.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			MDept w = mgs.get(i);
			String[] d = {w.getDes(),""+w.getIdDept()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(mgs.size());
		t.setRecordsFiltered(mgs.size());
	    t.setData(lst);
	    return t;
	}
	
}