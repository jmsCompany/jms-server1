package com.jms.repositories.s;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SPo;
import com.jms.domain.db.SSo;

@Repository
public interface SSoRepository  extends JpaRepository<SSo, Long>{
	
	@Query("select s from SSo s where s.company.idCompany=?1")
	public List<SSo> findByCompanyId(Long companyId);
	
	
	@Query("select s from SSo s where s.SCompanyCo.id=?1")
	public List<SSo> findByCompanyCoId(Long companyCoId);
	
	@Query("select s.SMaterial from SSo s where s.idSo=?1")
	public SMaterial findBySoId(Long soId);
		
}