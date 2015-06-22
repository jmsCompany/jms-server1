package com.jms.service.system;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.domain.db.Module;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.system.ModuleRepository;


@Service
@Transactional
public class ModuleService {
	
	private static final Logger logger = LogManager.getLogger(ModuleService.class.getCanonicalName());
	@Autowired
	private ModuleRepository moduleRepository;
	@Autowired
	private CompanyRepository companyRepository ;
	
	public void loadModulesFromCSV(InputStream inputStream) throws IOException{
		CsvReader reader = new CsvReader(inputStream,',', Charset.forName("UTF-8"));
        reader.readHeaders();  //Parent,  Name,     Description
		while(reader.readRecord())
		{
			Module m = new Module();
			String parentModule = reader.get("Parent");
			if(!parentModule.isEmpty())
			{
				Module parent = moduleRepository.findByName(parentModule);
				m.setModule(parent);
			}
			m.setDescription(reader.get("Description"));
			m.setName(reader.get("Name"));
			logger.debug("Modules: " + m.getName() +", description: " + m.getDescription());
			save(m); 
	
		}
	}
	
	public void save(Module module)
	{
		moduleRepository.save(module);  //create new 
		
	}
}