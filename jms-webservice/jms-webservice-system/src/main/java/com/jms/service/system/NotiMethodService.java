package com.jms.service.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.NotiMethod;
import com.jms.repositories.system.NotiMethodRepository;


@Service
@Transactional(readOnly=false)
public class NotiMethodService {

	@Autowired
	private NotiMethodRepository notiMethodRepository;
	

	public void createNotiMethods(){
		NotiMethod emailMethod = new NotiMethod();
		emailMethod.setMethod("email");
		notiMethodRepository.save(emailMethod);
		
		NotiMethod smsMethod = new NotiMethod();
		smsMethod.setMethod("sms");
		notiMethodRepository.save(smsMethod);
		
		NotiMethod sysMethod = new NotiMethod();
		sysMethod.setMethod("sys");
		notiMethodRepository.save(sysMethod);
		
	}

	
}
