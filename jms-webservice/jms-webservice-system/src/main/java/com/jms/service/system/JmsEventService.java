package com.jms.service.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.EventTypeEnum;
import com.jms.domain.db.Company;
import com.jms.domain.db.Groups;
import com.jms.domain.db.JmsEvent;
import com.jms.domain.db.WProject;
import com.jms.domain.db.WTask;
import com.jms.repositories.system.JmsEventRepository;

@Service
@Transactional(readOnly=false)
public class JmsEventService {
	@Autowired
	private JmsEventRepository jmsEventRepository;
	public void createJmsEvents(){
		JmsEvent jmsNewCompany = new JmsEvent();
		jmsNewCompany.setClass_(Company.class.getCanonicalName());
		jmsNewCompany.setName(EventTypeEnum.create.name());
		jmsNewCompany.setDescription("创建公司");
		jmsEventRepository.save(jmsNewCompany);
		
		JmsEvent jmsNewProject = new JmsEvent();
		jmsNewProject.setClass_(WProject.class.getCanonicalName());
		jmsNewProject.setName(EventTypeEnum.create.name());
		jmsNewProject.setDescription("创建项目");
		jmsEventRepository.save(jmsNewProject);
		
		JmsEvent jmsUpdateProject = new JmsEvent();
		jmsUpdateProject.setClass_(WProject.class.getCanonicalName());
		jmsUpdateProject.setName(EventTypeEnum.update.name());
		jmsUpdateProject.setDescription("修改项目");
		jmsEventRepository.save(jmsUpdateProject);
		
		
		JmsEvent jmsNewTask = new JmsEvent();
		jmsNewTask.setClass_(WTask.class.getCanonicalName());
		jmsNewTask.setName(EventTypeEnum.create.name());
		jmsNewTask.setDescription("创建任务");
		jmsEventRepository.save(jmsNewTask);
		
		JmsEvent jmsUpdateTask = new JmsEvent();
		jmsUpdateTask.setClass_(WTask.class.getCanonicalName());
		jmsUpdateTask.setName(EventTypeEnum.update.name());
		jmsUpdateTask.setDescription("修改任务");
		jmsEventRepository.save(jmsUpdateTask);
		
		
		JmsEvent jmsNewGroup = new JmsEvent();
		jmsNewGroup.setClass_(Groups.class.getCanonicalName());
		jmsNewGroup.setName(EventTypeEnum.create.name());
		jmsNewGroup.setDescription("创建群组");
		jmsEventRepository.save(jmsNewGroup);
		
	}

	
}
