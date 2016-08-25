package com.jms.repositories.q;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.MStatusDic;
import com.jms.domain.db.QCheckList;
import com.jms.domain.db.QStatusDic;


@Repository
public interface QStatusDicRepository extends JpaRepository<QStatusDic, Long>{
  
	public List<QStatusDic> getBySource(String source);
	public QStatusDic getBySourceAndDes(String source,String des);
}
