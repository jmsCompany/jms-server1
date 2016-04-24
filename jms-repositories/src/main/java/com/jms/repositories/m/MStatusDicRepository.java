package com.jms.repositories.m;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.MStatusDic;



@Repository
public interface MStatusDicRepository extends JpaRepository<MStatusDic, Long>{

	public List<MStatusDic> getBySource(String source);
	public MStatusDic getBySourceAndName(String source,String name);
}
