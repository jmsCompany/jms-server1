package com.jms.repositories.s;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SCompanyCo;

@Repository
public interface SCompanyCoRepository  extends JpaRepository<SCompanyCo, Long>{

		
}