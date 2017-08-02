package com.jms.repositories.s;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.BuyMehod;

@Repository
public interface BuyMehodRepository  extends JpaRepository<BuyMehod, Long>{
	

	public List<BuyMehod> findByIdCompany(Long idCompany);
	
		
}