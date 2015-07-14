package com.jms.service.system;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.Apps;
import com.jms.repositories.system.AppsRepository;

@Service
@Transactional(readOnly=false)
public class AppsService {

	@Autowired
	private AppsRepository appsRepository;

	
	public void createInitalApps() {
	
		String[] appArray = new String[] { "项目管理", "公告",
				"日程管理", "消息系统", "流程审批", "企业通讯录" };
		for(String app: appArray)
		{
			Apps apps = new Apps();
			apps.setScope(1l);
			apps.setAppName(app);
			apps.setDescription(app);
			appsRepository.save(apps);
		}
	}

}
