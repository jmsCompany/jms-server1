package com.jms.repositories.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.RolePriv;
import com.jms.domain.db.RolePrivId;



@Repository
public interface RolePrivRepository  extends JpaRepository<RolePriv, RolePrivId>{
		
	//@Query("select r from RolePriv r where r.role=?1  and r.moudle=?2 ")	
	//public RolePriv findByRoleidAndMoudleid(String role);
	
	//public RolePriv findByName(String name);

}