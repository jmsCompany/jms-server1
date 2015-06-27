package com.jms.repositories.system;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.Module;



@Repository
public interface ModuleRepository  extends JpaRepository<Module, Long>{
		
	public Module findByName(String name);

}