package com.jms.repositories.s;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.SStk;
import com.jms.domain.db.SStkTypeDic;
import com.jms.domain.db.STypeDic;

@Repository
public interface STypeDicRepository  extends JpaRepository<STypeDic, Long>{
		
}