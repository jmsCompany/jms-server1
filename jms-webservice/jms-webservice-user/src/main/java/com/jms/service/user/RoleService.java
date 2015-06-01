package com.jms.service.user;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.domain.db.Roles;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.user.RoleRepository;

@Service
@Transactional
public class RoleService {
	
	private static final Logger logger = LogManager.getLogger(RoleService.class.getCanonicalName());
	@Autowired
	private CompanyRepository companyRepository ;
	@Autowired
	private RoleRepository roleRepository;
	public void loadRolesFromCSV(String fileName) throws IOException{
		CsvReader reader = new CsvReader(fileName,',', Charset.forName("UTF-8"));
		
        reader.readHeaders();  //CompanyName, Role	Description
		while(reader.readRecord())
		{
			Roles r = new Roles();
			r.setRole(reader.get("Role"));
			r.setDescription(reader.get("Description"));
			r.setCompany(companyRepository.findByCompanyName(reader.get("CompanyName")));
			logger.debug("role: " + r.getRole() +", description: " + r.getDescription());
			save(r);
		}
	}
	
	public Roles save(Roles role)
	{
		Roles r;
		if( role.getCompany() ==null ){
			r = roleRepository.findByRole(role.getRole());
		}
		else{
			r=roleRepository.findByRoleAndCompanyName(role.getRole(), role.getCompany().getCompanyName());
		}
		
		if(r == null){
			roleRepository.save(role);  //create new 
		}
		else
		{
			role.setIdRole(r.getIdRole());  //update by Id
			roleRepository.save(role);
		}
		return r;
	}
}
