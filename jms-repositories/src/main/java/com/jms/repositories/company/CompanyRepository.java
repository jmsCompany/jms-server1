package com.jms.repositories.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
	public Company findByCompanyName(String companyName);

}
