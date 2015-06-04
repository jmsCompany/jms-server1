package com.jms.repositories.company;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.Company;
import com.jms.domain.db.Sector;



@Repository
public interface SectorsRepository  extends CrudRepository<Sector, Integer>{
	
	@Query("select s from Sector s where s.sector=?1 and s.company.companyName=?2")	
	public Sector findBySectorAndCompanyName(String sector,String companyName);

	public List<Sector> findByCompany(Company company);

}