package com.jms.repositories.s;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SMaterial;


@Repository
public interface SMaterialRepository  extends JpaRepository<SMaterial, Long>{
	
  @Query("select s from SMaterial s where s.company.idCompany=?1")
  public List<SMaterial> getByCompanyId(Long companyId);
  
  @Query("select s from SMaterial s where s.company.idCompany=?1 and s.SMaterialTypeDic.id=?2")
  public List<SMaterial> getByCompanyIdAndMaterialType(Long companyId,Long materialTypeId);
  
  @Query("select s from SMaterial s where s.company.idCompany=?1 and s.SMaterialTypeDic.id=?2 and (pno like ?3 or rev like ?3 or des like ?3)")
  public List<SMaterial> getByCompanyIdAndMaterialTypeAndQuery(Long companyId,Long materialTypeId,String q);
  
  @Query("select s from SMaterial s where s.company.idCompany=?1 and (pno like ?2 or rev like ?2 or des like ?2)")
  public List<SMaterial> getByCompanyIdAndQuery(Long companyId,String q);
		
}