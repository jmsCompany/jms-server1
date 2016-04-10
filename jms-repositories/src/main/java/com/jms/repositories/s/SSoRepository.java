package com.jms.repositories.s;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SSo;

@Repository
public interface SSoRepository  extends JpaRepository<SSo, Long>{
	
		
}