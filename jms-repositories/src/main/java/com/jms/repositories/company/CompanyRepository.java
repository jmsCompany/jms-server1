package com.jms.repositories.company;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.Company;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
	
	public Company findByCompanyName(String companyName);
	

	
}
