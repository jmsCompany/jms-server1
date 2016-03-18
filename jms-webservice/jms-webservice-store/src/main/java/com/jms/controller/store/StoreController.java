package com.jms.controller.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.google.common.io.ByteStreams;
import com.jms.acl.SecuredObjectService;
import com.jms.domain.db.Apps;
import com.jms.domain.db.SStatusDic;
import com.jms.domain.db.SStk;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSCompany;
import com.jms.domain.ws.WSMenu;
import com.jms.domain.ws.WSPCpp;
import com.jms.domain.ws.WSSmr;
import com.jms.domain.ws.WSTest;
import com.jms.domain.ws.WSUser;
import com.jms.domain.ws.WSUserProfile;
import com.jms.domain.ws.store.WSMaterialCategory;
import com.jms.domainadapter.UserAdapter;
import com.jms.repositories.system.AppsRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.service.MaterialCategoryService;


@RestController
@Transactional(readOnly=true)
public class StoreController {
	
	@Autowired private MaterialCategoryService materialCategoryService;
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/s/saveMaterialCategory", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Long saveMaterialCategory(@RequestBody WSMaterialCategory wSMaterialCategory) throws Exception {
		return materialCategoryService.saveMaterialCategories(wSMaterialCategory);
	}
	
	
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/s/materialList", method=RequestMethod.GET)
	public WSTest  getMaterialList(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {
	   
		List<String[]> lst = new ArrayList<String[]>();
	
	
		for (int i = start; i < start + length; i++) {
			String[] d = { "" + i, "名称" + i ,"大类" + i,"小类" + i,"状态" + i};
			lst.add(d);

		}
		WSTest t = new WSTest();
		t.setDraw(draw);
		t.setRecordsTotal(1000000);
		t.setRecordsFiltered(1000000);
	    t.setData(lst);
	    return t;
	}
	
	
	//@Transactional(readOnly = true)
	@RequestMapping(value="/p/getPCpp", method=RequestMethod.GET)
	public WSPCpp getPcpp(@RequestParam String shift) {
	   
		//还需要系统时间和 用户 ID
		WSPCpp wsPCpp = new WSPCpp();
		wsPCpp.setWoNo("W001");
		wsPCpp.setpNo("P001");
		wsPCpp.setQty(100);
		wsPCpp.setOp("Tony");
		wsPCpp.setShift("A");
		wsPCpp.setLine(1);
		wsPCpp.setDrawNo("A01");
		wsPCpp.setDrawVer(1);
		wsPCpp.setRoute("cutting");
		wsPCpp.setmNo("A20");
		wsPCpp.setStdWtLabor("10m");
		wsPCpp.setStdWtMachine("20m");
		wsPCpp.setStdWtSetup("20m");
		wsPCpp.setCategory("boring");
		wsPCpp.setChecklistId("workmanship1.png");
		wsPCpp.setWiId("usingthymeleaf.pdf");
		wsPCpp.setSt(new Date().getTime()-1000*60*60);
		wsPCpp.setFt(new Date().getTime()+1000*60*60*10);
		wsPCpp.setDrawId("workmanship1.png");
		wsPCpp.setRouteId("workmanship1.png");
	    return wsPCpp;
	}
	
	
	
	@RequestMapping(value = "/getFile", method = RequestMethod.GET)
	public void getImage(@RequestParam("idFile") String idFile,HttpServletResponse response ) throws IOException
	{
		FileInputStream fs = new FileInputStream(new File("/Users/renhongtao/eme_files/"+idFile));
	    ByteStreams.copy(fs,response.getOutputStream() );
	}
	
	//获取需料信息	
	@RequestMapping(value="/s/getSMr", method=RequestMethod.GET)
	public List<WSSmr> getSmr() {

		List<WSSmr> smrs = new ArrayList<WSSmr>();
		WSSmr mr1 = new WSSmr();
		mr1.setOp("王青");
		mr1.setmNo("A20");
		mr1.setpNo("RW001");
		mr1.setVer(1);
		mr1.setDes("Round Bar");
		mr1.setQty(600);
		mr1.setLotNo("L001");
		mr1.setBin("B001");
		mr1.setBinQty(200);
		mr1.setActQty(200);
		smrs.add(mr1);
		
		WSSmr mr2 = new WSSmr();
		mr2.setOp("Tony");
		mr2.setmNo("A20");
		mr2.setpNo("RW001");
		mr2.setVer(1);
		mr2.setDes("Round Bar");
		mr2.setQty(600);
		mr2.setLotNo("L001");
		mr2.setBin("B001");
		mr2.setBinQty(200);
		mr2.setActQty(400);
		smrs.add(mr2);
		
		return smrs;
	}
	
	
}