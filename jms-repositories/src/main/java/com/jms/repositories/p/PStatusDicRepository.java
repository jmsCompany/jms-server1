package com.jms.repositories.p;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.PStatusDic;


@Repository
public interface PStatusDicRepository extends JpaRepository<PStatusDic, Long>{

	public List<PStatusDic> getBySource(String source);
	public PStatusDic getBySourceAndName(String source,String name);
}
