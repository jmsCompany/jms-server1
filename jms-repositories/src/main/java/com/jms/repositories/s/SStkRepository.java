package com.jms.repositories.s;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.SStk;

@Repository
public interface SStkRepository  extends JpaRepository<SStk, Long>{

	@Query("select s from SStk s where s.company.idCompany=?1 and s.SStatusDic.id=?2")
	public List<SStk> findByIdCompanyAndStatus(Long companyId,Long statusId);
	
	

	@Query("select s from SStk s where s.company.idCompany=?1 and s.SStatusDic.id=?2 and s.SStkTypeDic.idStkType in ?3")
	public List<SStk> findByIdCompanyAndStatusAndTypes(Long companyId,Long statusId,List<Long> types);
	
	@Query("select s from SStk s where s.company.idCompany=?1")
	public List<SStk> findByIdCompany(Long companyId);
	
	@Query("select s from SStk s where s.company.idCompany=?1 and s.stkName=?2")
	public List<SStk> findByIdCompanyAndStkName(Long companyId,String stkName);
	
	
	@Query("select s from SStk s where s.company.idCompany=?1 and s.stkName=?2 and s.SStatusDic.id=27")
	public SStk findActiveStkByIdCompanyAndStkName(Long companyId,String stkName);
		
}