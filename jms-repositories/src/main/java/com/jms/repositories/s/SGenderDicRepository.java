package com.jms.repositories.s;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SGenderDic;

@Repository
public interface SGenderDicRepository  extends JpaRepository<SGenderDic, Long>{
	
		
}