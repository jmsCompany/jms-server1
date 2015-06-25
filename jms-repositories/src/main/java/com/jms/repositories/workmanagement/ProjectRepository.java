package com.jms.repositories.workmanagement;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;




import com.jms.domain.db.Company;
import com.jms.domain.db.Project;


@Repository
public interface ProjectRepository  extends CrudRepository<Project, Long>{
		
	public List<Project> findByCompany(Company company);
}