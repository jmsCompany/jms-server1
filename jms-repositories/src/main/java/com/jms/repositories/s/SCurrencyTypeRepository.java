package com.jms.repositories.s;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SBin;
import com.jms.domain.db.SCurrencyType;

@Repository
public interface SCurrencyTypeRepository  extends JpaRepository<SCurrencyType, Long>{
	
	
	public SCurrencyType findByCurrency(String currency);
	
		
}