package com.jms.repositories.s;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.SMaterial;


@Repository
public interface SMaterialRepository  extends JpaRepository<SMaterial, Long>{
		
}