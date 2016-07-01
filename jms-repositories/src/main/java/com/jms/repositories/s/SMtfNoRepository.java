package com.jms.repositories.s;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.SMtfNo;


@Repository
public interface SMtfNoRepository  extends JpaRepository<SMtfNo, Long>{
	
    @Query("select s from SMtfNo s where s.companyId=?1 and type=?2")
	public SMtfNo getByCompanyIdAndType(Long companyId, Long type);
	

}