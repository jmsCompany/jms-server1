package com.jms.repositories.s;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SMtf;


@Repository
public interface SMtfRepository  extends JpaRepository<SMtf, Long>{
	
	@Query("select s from SMtf s where s.SMtfTypeDic.idMtfType=?1 and s.company.idCompany=?2 ")
	public List<SMtf> getSMtfByType(Long smtfId,Long companyId);
		
}