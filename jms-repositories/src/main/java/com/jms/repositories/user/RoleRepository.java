package com.jms.repositories.user;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.Roles;

@Repository
public interface RoleRepository  extends JpaRepository<Roles, Long>{
	
	@Query("select r from Roles r where r.role=?1 and r.company.companyName=?2")	
	public Roles findByRoleAndCompanyName(String role,String companyName);
	@Query("select r from Roles r where r.role=?1 and r.company is null")	
	public Roles findByRole(String role);
	
	
	@Query("select r from Roles r where r.company.idCompany=?1")	
	public List<Roles> findByCompanyId(Long companyId);

}
