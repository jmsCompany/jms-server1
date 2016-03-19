package com.jms.repositories.s;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.SStk;
import com.jms.domain.db.SStkTypeDic;

@Repository
public interface SStkTypeDicRepository  extends JpaRepository<SStkTypeDic, Long>{
		
}