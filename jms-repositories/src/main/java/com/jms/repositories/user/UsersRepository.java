package com.jms.repositories.user;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.Users;

@Repository
public interface UsersRepository  extends CrudRepository<Users, Long> {
	
	public Users findByEmail(String email);
	public Users findByMobile(String mobile);
	public Users findByUsername(String username);
	@Query("select u from Users u where u.username=?1 or u.email=?1 or u.mobile=?1")	
	public Users findByUsernameOrEmailOrMobile(String login);
	public Users findByToken(String token);
	
	@Query("select u from Users u inner join fetch u.sectorMembers s where s.id.idSector=?1")	
	public List<Users> findUsersByIdSector(Long idSector);
}

