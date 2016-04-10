package com.jms.repositories.s;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.SMaterialTypeDic;
import com.jms.domain.db.SMtf;
import com.jms.domain.db.SMtfTypeDic;
import com.jms.domain.db.SStk;

@Repository
public interface SMtfRepository  extends JpaRepository<SMtf, Long>{
		
}