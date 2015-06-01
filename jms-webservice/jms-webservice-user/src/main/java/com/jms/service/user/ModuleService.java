package com.jms.service.user;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.domain.db.Modules;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.system.ModulesRepository;


@Service
@Transactional
public class ModuleService {
	
	private static final Logger logger = LogManager.getLogger(ModuleService.class.getCanonicalName());
	@Autowired
	private ModulesRepository modulesRepository;
	@Autowired
	private CompanyRepository companyRepository ;
	
	public void loadModulesFromCSV(String fileName) throws IOException{
		CsvReader reader = new CsvReader(fileName,',', Charset.forName("UTF-8"));
        reader.readHeaders();  //Parent,  Name,     Description
		while(reader.readRecord())
		{
			Modules m = new Modules();
			String parentModule = reader.get("Parent");
			if(!parentModule.isEmpty())
			{
				Modules parent = modulesRepository.findByName(parentModule);
				m.setModules(parent);
			}
			m.setDescription(reader.get("Description"));
			m.setName(reader.get("Name"));
			logger.debug("Modules: " + m.getName() +", description: " + m.getDescription());
			save(m); 
	
		}
	}
	
	public void save(Modules module)
	{
		modulesRepository.save(module);  //create new 
		
	}
}