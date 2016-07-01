package com.jms.repositories.s;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SBin;
import com.jms.domain.db.SUnitDic;

@Repository
public interface SUnitDicRepository  extends JpaRepository<SUnitDic, Long>{
	
	
	public SUnitDic findByName(String name);
	

}