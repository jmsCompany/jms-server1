package com.jms.repositories.s;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SCompanyCo;

@Repository
public interface SCompanyCoRepository  extends JpaRepository<SCompanyCo, Long>{
	
	@Query("select s from SCompanyCo s where s.company.idCompany=?1 and s.STypeDic.id=?2")
	public List<SCompanyCo> findByCompanyIdandType(Long idCompany,Long idType);
	
	@Query("select s from SCompanyCo s where s.company.idCompany=?1")
	public List<SCompanyCo> findByCompanyID(Long idCompany);
	
	
	@Query("select s from SCompanyCo s where s.company.idCompany=?1")
	public List<SCompanyCo> findByCompanyCos(Long idCompany);
	
	
	@Query("select s from SCompanyCo s where s.company.idCompany=?1 and s.name=?2 and s.STypeDic.id=2")
	public SCompanyCo findByCompanyIdAndName(Long idCompany,String name);
		
}