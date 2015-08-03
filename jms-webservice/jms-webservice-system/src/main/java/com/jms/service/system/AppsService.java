package com.jms.service.system;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.Apps;
import com.jms.domain.db.Module;
import com.jms.repositories.system.AppsRepository;
import com.jms.repositories.system.ModuleRepository;

@Service
@Transactional(readOnly=false)
public class AppsService {

	@Autowired
	private AppsRepository appsRepository;
	@Autowired
	private ModuleRepository moduleRepository;
	
	public void createInitalApps() {
	
		String[] appArray = new String[] { "工作管理",
				"办公助理", "人事", "日程助理", "通讯录" , "公司公告" , "消息", "进销存" };
		for(String app: appArray)
		{
			Apps apps = new Apps();
			apps.setScope(1l);
			apps.setAppName(app);
			apps.setDescription(app);
			appsRepository.save(apps);
		}
		Apps apps = new Apps();
		apps.setScope(1l);
		apps.setAppName("系统管理");
		apps.setDescription("系统管理");
		appsRepository.save(apps);
		String[] sysModules = new String[]{"公司管理","缴费","组织结构"};
		for(String module: sysModules)
		{
			Module m = new Module();
			m.setApps(apps);
			m.setName(module);
			moduleRepository.save(m);
		}
		
		
	}

}
