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
	
		String[] appArray = new String[] { "系统管理", "工作管理",
				"办公助理", "人事", "日程助理", "通讯录" , "公司公告" , "消息", "进销存" };
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
