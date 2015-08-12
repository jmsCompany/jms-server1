package com.jms.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSSysDicD;
import com.jms.system.IDicDService;

@RestController
@Transactional(readOnly = true)
public class DicController {
	
	@Autowired  @Qualifier("dicDService")
	private IDicDService dicDService;
	
	@RequestMapping(value="/dic/{type}", method=RequestMethod.GET)
	public List<WSSelectObj> getWSSysDicDs(@PathVariable("type") String type) throws Exception
	{
		return dicDService.getSelectObjByType(type);
	}
	
	
}