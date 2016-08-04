package com.jms.repositories.m;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.MHistoryPart;



@Repository
public interface MHistoryPartRepository extends JpaRepository<MHistoryPart, Long>{
	
}
