package com.jms.repositories.s;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SPic;

@Repository
public interface SPicRepository  extends JpaRepository<SPic, Long>{
	


}