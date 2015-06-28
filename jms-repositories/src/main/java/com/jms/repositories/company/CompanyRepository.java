package com.jms.repositories.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.Company;

@Repository
public interface CompanyRepository extends RevisionRepository<Company, Long, Integer>,JpaRepository<Company, Long> {
	public Company findByCompanyName(String companyName);

}
