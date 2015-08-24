package com.jms.repositories.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.JmsEvent;


@Repository
public interface JmsEventRepository extends JpaRepository<JmsEvent, Long>{
	
	@Query("select j from JmsEvent j where j.class_=?1 and j.name=?2")
	public JmsEvent findByClassAndName(String class_,String name);


}
