package com.jms.service.workmanagement;


import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.acl.SecuredObjectService;
import com.jms.domain.Config;
import com.jms.domain.db.Company;
import com.jms.domain.db.Project;
import com.jms.domain.ws.MessageTypeEnum;
import com.jms.messages.MessagesUitl;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.system.SysDicDRepository;
import com.jms.repositories.workmanagement.ProjectRepository;


@Service
@Transactional
public class ProjectService {

	private static final Logger logger = LogManager
			.getLogger(ProjectService.class.getCanonicalName());

	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private MessagesUitl messagesUitl;
	@Autowired
	private SysDicDRepository sysDicDRepository;
	
	@Autowired
	private SecuredObjectService securedObjectService;

	public void createGenerialProject(Company company) {

			Project p = new Project();
			p.setProjectName(messagesUitl.getMessage(
					"workmanagement.project.generialprojectname", null,
					MessageTypeEnum.INFOMATION).getMessage());
			p.setDescription(messagesUitl.getMessage(
					"workmanagement.project.generialprojectdescription", null,
					MessageTypeEnum.INFOMATION).getMessage());
			p.setSysDicDByStatus(sysDicDRepository.findDicsByTypeAndName(Config.projectStatus, "0").get());
			p.setCompany(company);
            p.setUsers(company.getUsersByCreator());
			logger.debug("projectName: " + p.getProjectName()
					+ ", projectNumber: " + p.getProjectNumber()
					+ ", description: " + p.getDescription()
					+ ", CompanyName: " + p.getCompany().getCompanyName());
			projectRepository.save(p);
		
	}
	
	public void SecuredObjectServiceTest()
	{
		 Map<String, String> map = securedObjectService.findSidPermissionMap(Project.class, 4l, "group");
	       for(String m:map.keySet())
	       {
	    	   
	    	   System.out.println("group id: " + m +", permission: " + map.get(m));
	       }
	       
	  	 Map<String, String> map1 = securedObjectService.findSidPermissionMap(Project.class, 4l, "user");
	       for(String m:map1.keySet())
	       {
	    	   
	    	   System.out.println("user id: " + m +", permission: " + map1.get(m));
	       }
	       
	}
}