package com.jms.repositories.user;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.Company;


@Repository
public interface CompanyReposity extends CrudRepository<Company, Integer>{
	
	public Company findByCompanyName(String companyName);

}
