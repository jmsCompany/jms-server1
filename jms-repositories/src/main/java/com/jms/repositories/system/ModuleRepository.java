package com.jms.repositories.system;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.Module;



@Repository
public interface ModuleRepository  extends CrudRepository<Module, Integer>{
		
	public Module findByName(String name);

}