package com.jms.repositories.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.PersistentLogin;


@Repository
public interface PersistentLoginRepository  extends CrudRepository<PersistentLogin, String> {
	
	@Query("select p from PersistentLogin p where p.users.idUser=?1")	
	public PersistentLogin findByUserId(Integer idUser);
		
	public PersistentLogin findByToken(String token);
}

