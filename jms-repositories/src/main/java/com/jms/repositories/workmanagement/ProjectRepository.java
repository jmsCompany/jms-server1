package com.jms.repositories.workmanagement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;





import com.jms.domain.db.Company;
import com.jms.domain.db.WProject;


@Repository
public interface ProjectRepository  extends JpaRepository<WProject, Long>{
		
	public List<WProject> findByCompany(Company company);
}