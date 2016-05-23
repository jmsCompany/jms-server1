package com.jms.controller.quality;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.jms.domain.db.QItemType;
import com.jms.domain.db.QTester;
import com.jms.domain.ws.WSSelectObj;
import com.jms.repositories.q.QItemTypeRepository;
import com.jms.repositories.q.QTesterRepository;
import com.jms.service.quality.ItemTypeService;
import com.jms.service.quality.TesterService;

@RestController
@Transactional(readOnly = true)
public class ItemTypeController {

	@Autowired
	private ItemTypeService itemTypeService;
	@Autowired
	private QItemTypeRepository qItemTypeRepository;

	@Transactional(readOnly = true)
	@RequestMapping(value = "/q/getItemTypeObjsList", method = RequestMethod.GET)
	public List<WSSelectObj> getItemTypeObjsList() throws Exception {
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>(0);
		for (QItemType q : qItemTypeRepository.findAll()) {
			WSSelectObj w = new WSSelectObj(q.getIdItemType(), q.getDes());
			ws.add(w);
		}
		return ws;
	}

}