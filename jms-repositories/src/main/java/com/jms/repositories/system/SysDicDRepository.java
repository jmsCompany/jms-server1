package com.jms.repositories.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.SysDicD;

@Repository
public interface SysDicDRepository extends JpaRepository<SysDicD, Long>{

	@Query("select s from SysDicD s where s.sysDic.type=?1 ")	
	public List<SysDicD> findDicsByType(String type);
	
	@Query("select s from SysDicD s where s.sysDic.type=?1 and s.name=?2 ")	
	public List<SysDicD> findDicsByTypeAndName(String type,String name);
}
