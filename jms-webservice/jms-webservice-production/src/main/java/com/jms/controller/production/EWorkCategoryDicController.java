package com.jms.controller.production;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.PWorkCenter;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.production.WSPWorkCenter;
import com.jms.repositories.e.EWorkCategoryDicRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.service.production.EWorkCategoryDicService;
import com.jms.service.production.WorkCenterService;
import com.jms.web.security.SecurityUtils;




@RestController
@Transactional(readOnly=true)
public class EWorkCategoryDicController {
	
	@Autowired private EWorkCategoryDicService eWorkCategoryDicService;
	@Autowired private EWorkCategoryDicRepository eWorkCategoryDicRepository;
	@Autowired private SecurityUtils securityUtils;


	
	@Transactional(readOnly = true)
	@RequestMapping(value="/p/findWorkCategoriesSelectObjs", method=RequestMethod.GET)
	public List<WSSelectObj>  findWorkCategoriesSelectObjs() throws Exception {	   
	    return eWorkCategoryDicService.findWorkCategoriesSelectObjs();
	}


}