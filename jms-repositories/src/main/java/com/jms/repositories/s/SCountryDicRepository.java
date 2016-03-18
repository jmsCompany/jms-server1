package com.jms.repositories.s;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.SCountryDic;
import com.jms.domain.db.SStk;

@Repository
public interface SCountryDicRepository  extends JpaRepository<SCountryDic, Long>{
		
}