package com.jms.controller.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.FCostCenter;
import com.jms.domain.db.SCountryDic;
import com.jms.domain.db.SMaterialCategory;
import com.jms.domain.db.SMaterialTypeDic;
import com.jms.domain.db.SUnitDic;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.f.WSFCostCenter;
import com.jms.domain.ws.store.WSMaterial;
import com.jms.domain.ws.store.WSSStatus;
import com.jms.domain.ws.store.WSSType;
import com.jms.domain.ws.store.WSTst;
import com.jms.repositories.s.SMaterialCategoryRepository;
import com.jms.repositories.s.SMaterialTypeDicRepository;
import com.jms.repositories.s.SUnitDicRepository;
import com.jms.service.CostCenterService;
import com.jms.service.MaterialService;
import com.jms.web.security.SecurityUtils;


@RestController
@Transactional(readOnly=true)
public class MtfController {
	

	
	@Autowired private MaterialService materialService;
	@Autowired private SMaterialTypeDicRepository sMaterialTypeDicRepository;
	@Autowired private SMaterialCategoryRepository sMaterialCategoryRepository;
	@Autowired private SUnitDicRepository sUnitDicRepository;
	@Autowired private SecurityUtils securityUtils;
	@Autowired private  CostCenterService costCenterService;
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/testing", method=RequestMethod.GET)
	public List<WSSType> justForTesting()  {
		List<WSSType> ls = new ArrayList<WSSType>();
		
		for(long i=0;i<5;i++)
		{
			WSSType w = new WSSType();
			w.setId(i);
			w.setName("name"+i);
			ls.add(w);
		}
		
		return ls;
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/testing1", method=RequestMethod.GET)
	public WSTst justForTesting1()  {
		WSTst t = new WSTst();
		Map<String,WSSType> lm = new HashMap<String,WSSType>();
		for(long i=0;i<5;i++)
		{
			WSSType w = new WSSType();
			w.setId(i);
			w.setName("name"+i);
			lm.put(""+i, w);
		}
		t.setId(1001l);
		t.setName("大智慧");
		t.setTests(lm);
		return t;
	}
	
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/testing1", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSTst justForTesting2(@RequestBody WSTst wsTst)  {
		
		for(String k : wsTst.getTests().keySet())
		{
			WSSType w =wsTst.getTests().get(k);
			System.out.println("id: " + w.getId() +", name: " + w.getName());
		}
		return wsTst;
		
	}
	
	
}