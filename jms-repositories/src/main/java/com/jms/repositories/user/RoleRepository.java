package com.jms.repositories.user;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.Roles;

@Repository
public interface RoleRepository  extends CrudRepository<Roles, Integer>{
	
	@Query("select r from Roles r where r.role=?1 and r.company.companyName=?2")	
	public Roles findByRoleAndCompanyName(String role,String companyName);
	@Query("select r from Roles r where r.role=?1 and r.company is null")	
	public Roles findByRole(String role);

}
