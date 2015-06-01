package com.jms.repositories.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.Sector;
import com.jms.domain.db.Users;

@Repository
public interface UsersRepository  extends CrudRepository<Users, Integer> {
	
	public Users findByEmail(String email);
	public Users findByMobile(String mobile);
	public Users findByUsername(String username);
	@Query("select u from Users u where u.username=?1 or u.email=?1 or u.mobile=?1")	
	public Users findByUsernameOrEmailOrMobile(String login);
}

