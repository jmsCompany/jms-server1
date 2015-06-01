package com.jms.repositories.workmanagement;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.jms.domain.db.Project;


@Repository
public interface ProjectReposity  extends CrudRepository<Project, Integer>{
		
}