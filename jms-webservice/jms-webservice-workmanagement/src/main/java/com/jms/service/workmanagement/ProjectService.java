package com.jms.service.workmanagement;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.EnabledEnum;
import com.jms.domain.db.Company;
import com.jms.domain.db.Project;
import com.jms.domain.ws.MessageTypeEnum;
import com.jms.messages.MessagesUitl;
import com.jms.repositories.company.CompanyRepository;
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

	public void createGenerialProject() {

		for (Company c : companyRepository.findAll()) {
			Project p = new Project();
			p.setProjectName(messagesUitl.getMessage(
					"workmanagement.project.generialprojectname", null,
					MessageTypeEnum.INFOMATION).getMessage());
			p.setDescription(messagesUitl.getMessage(
					"workmanagement.project.generialprojectdescription", null,
					MessageTypeEnum.INFOMATION).getMessage());
			p.setEnabled(EnabledEnum.ENABLED.getStatusCode());
			p.setCompany(c);

			logger.debug("projectName: " + p.getProjectName()
					+ ", projectNumber: " + p.getProjectNumber()
					+ ", description: " + p.getDescription()
					+ ", CompanyName: " + p.getCompany().getCompanyName());
			projectRepository.save(p);
		}
	}
}