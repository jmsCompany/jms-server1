package com.jms.repositories.user;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.RolePriv;
import com.jms.domain.db.RolePrivId;
import com.jms.domain.db.Roles;



@Repository
public interface RolePrivReposity  extends CrudRepository<RolePriv, RolePrivId>{
		
	//@Query("select r from RolePriv r where r.role=?1  and r.moudle=?2 ")	
	//public RolePriv findByRoleidAndMoudleid(String role);
	
	//public RolePriv findByName(String name);

}