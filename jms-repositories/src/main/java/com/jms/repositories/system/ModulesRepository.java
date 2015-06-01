package com.jms.repositories.system;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.Modules;



@Repository
public interface ModulesRepository  extends CrudRepository<Modules, Integer>{
		
	public Modules findByName(String name);

}