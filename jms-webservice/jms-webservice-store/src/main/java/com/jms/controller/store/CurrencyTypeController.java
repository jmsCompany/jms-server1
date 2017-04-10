package com.jms.controller.store;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.ws.WSSelectObj;
import com.jms.service.store.CurrencyTypeService;


@RestController
@Transactional(readOnly=true)
public class CurrencyTypeController {

	@Autowired private CurrencyTypeService currencyTypeService;

	@Transactional(readOnly = true)
	@RequestMapping(value="/s/currencyTypes", method=RequestMethod.GET)
	public List<WSSelectObj> findCurrencyTypes() {
		return currencyTypeService.getSCurrencyTypes();
	}
	
	
}