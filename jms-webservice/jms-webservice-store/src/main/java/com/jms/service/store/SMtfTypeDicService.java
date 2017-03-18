package com.jms.service.store;


import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.Config;
import com.jms.domain.db.SMtfTypeDic;
import com.jms.domain.db.Users;
import com.jms.domain.ws.WSSelectObj;
import com.jms.repositories.s.SMtfTypeDicRepository;
import com.jms.web.security.SecurityUtils;


@Service
@Transactional
public class SMtfTypeDicService {

	private static final Logger logger = LogManager.getLogger(SMtfTypeDicService.class
			.getCanonicalName());
	@Autowired
	private SMtfTypeDicRepository sMtfTypeDicRepository;
	@Autowired private SecurityUtils securityUtils;

	//导入物料流转类型
	public void loadMtfTypes() {
		
		for(String lvl: Config.sMtfTypes)
		{
			SMtfTypeDic s = new SMtfTypeDic();
			s.setName(lvl);
			sMtfTypeDicRepository.save(s);
		}
	}
	
	@Transactional(readOnly=true)
	public List<WSSelectObj> getMtfTypes()
	{
		List<WSSelectObj> ls = new ArrayList<WSSelectObj>();
		for(SMtfTypeDic s :sMtfTypeDicRepository.findAll())
		{
			WSSelectObj ws = new WSSelectObj();
			ws.setId(s.getIdMtfType());
			
			Users u =securityUtils.getCurrentDBUser();
			if(u.getLang()!=null&&u.getLang().equals("en"))
			{
				ws.setName(s.getNameEn());
			}
			else
			{
				ws.setName(s.getName());
			}
	
			ls.add(ws);
		}
		return ls;
	}


}
