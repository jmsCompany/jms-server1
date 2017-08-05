package com.jms.repositories.s;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SSoNum;

@Repository
public interface SSoNumRepository  extends JpaRepository<SSoNum, Long>{
	
		
}