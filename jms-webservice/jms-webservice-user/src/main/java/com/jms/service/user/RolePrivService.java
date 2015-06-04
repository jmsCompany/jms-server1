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
import com.jms.domain.db.RolePriv;
import com.jms.domain.db.RolePrivId;
import com.jms.domain.db.Roles;
import com.jms.repositories.system.ModulesRepository;
import com.jms.repositories.user.RolePrivRepository;
import com.jms.repositories.user.RoleRepository;



@Service
@Transactional
public class RolePrivService {
	
	private static final Logger logger = LogManager.getLogger(RolePrivService.class.getCanonicalName());
	@Autowired
	private ModulesRepository modulesRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RolePrivRepository rolePrivReposity;
	
	
	public void loadRolesPrivFromCSV(String fileName) throws IOException{
		CsvReader reader = new CsvReader(fileName,',', Charset.forName("UTF-8"));
        reader.readHeaders();  //CompanyName,Module,Role,Priv
		while(reader.readRecord())
		{
			
			String module = reader.get("Module");
			String role = reader.get("Role");
			int priv = Integer.parseInt(reader.get("Priv"));
			
			Modules m = modulesRepository.findByName(module);
			Roles r =roleRepository.findByRoleAndCompanyName(role, reader.get("CompanyName"));
			RolePriv rP = new RolePriv();
			RolePrivId id = new RolePrivId(m.getIdModule(),r.getIdRole());
			rP.setId(id);
			rP.setModules(m);
			rP.setRoles(r);
			rP.setPriv(priv);
			
			logger.debug("Modules: " + m.getName() +", description: " + m.getDescription());
			save(rP); 
	
		}
	}
	
	public void save(RolePriv rolePriv)
	{
		rolePrivReposity.save(rolePriv);  
		
	}
}