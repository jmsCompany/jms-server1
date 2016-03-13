package com.jms.repositories.s;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.SMaterialCategory;
import com.jms.domain.db.SMaterialTypeDic;
import com.jms.domain.db.SStk;

@Repository
public interface SMaterialCategoryRepository  extends JpaRepository<SMaterialCategory, Long>{
	
	@Query("select s from SMaterialCategory s where s.company.idCompany=?1")
	public List<SMaterialCategory> findByIdCompany(Long idCompany);
		
}