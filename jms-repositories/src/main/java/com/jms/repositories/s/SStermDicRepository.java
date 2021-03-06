package com.jms.repositories.s;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.STermDic;


@Repository
public interface SStermDicRepository  extends JpaRepository<STermDic, Long>{
	
	public List<STermDic> getBySource(String source);
		
}