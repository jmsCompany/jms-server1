package com.jms.repositories.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	
	public Users findByEmail(String email);
	
	public Users findByMobile(String mobile);
	
	public Users findByUsername(String username);
	
	@Query("select u from Users u where u.username=?1 or u.email=?1 or u.mobile=?1")	
	public Users findByUsernameOrEmailOrMobile(String login);
	
	public Users findByToken(String token);
	
	@Query("select u from Users u inner join fetch u.groupMemberses g where g.id.idGroup=?1")	
	public List<Users> findUsersByIdGroup(Long idGroup);
	
	@Query("select u from Users u where u.company.idCompany=?1")	
	public List<Users> findUsersByIdCompany(Long companyId);
	
	
	@Query("select u from Users u where u.company.idCompany=?1 and (u.username like ?2 or u.name like ?2 or u.email like ?2 or u.mobile like ?2)")	
	public List<Users> findUsersByIdCompanyAndQ(Long companyId,String q);
	
	
}

