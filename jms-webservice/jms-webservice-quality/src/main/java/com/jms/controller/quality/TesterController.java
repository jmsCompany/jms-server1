package com.jms.controller.quality;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jms.domain.db.QTester;
import com.jms.domain.ws.WSSelectObj;
import com.jms.repositories.q.QTesterRepository;
import com.jms.service.quality.TesterService;

@RestController
@Transactional(readOnly = true)
public class TesterController {

	@Autowired
	private TesterService testerService;
	@Autowired
	private QTesterRepository qTesterRepository;

	@Transactional(readOnly = true)
	@RequestMapping(value = "/q/getTestersObjsList", method = RequestMethod.GET)
	public List<WSSelectObj> getTestersObjsList() throws Exception {
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>(0);
		for (QTester q : qTesterRepository.findAll()) {
			WSSelectObj w = new WSSelectObj(q.getIdTester(), q.getDes());
			ws.add(w);
		}
		return ws;
	}

}