package com.jms.repositories.s;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SMaterial;


@Repository
public interface SMaterialRepository  extends JpaRepository<SMaterial, Long>{
	
  @Query("select s from SMaterial s where s.company.idCompany=?1 order by s.pno")
  public List<SMaterial> getByCompanyId(Long companyId);
  
  @Query("select s from SMaterial s where s.company.idCompany=?1 and s.SMaterialTypeDic.id=?2")
  public List<SMaterial> getByCompanyIdAndMaterialType(Long companyId,Long materialTypeId);
  
  @Query("select s from SMaterial s where s.company.idCompany=?1 and s.SMaterialTypeDic.id in ?2")
  public List<SMaterial> getByCompanyIdAndMaterialTypes(Long companyId,List<Long> types);

  @Query("select s from SMaterial s where s.company.idCompany=?1 and s.SMaterialTypeDic.id=?2 and (s.pno like ?3 or s.rev like ?3 or s.des like ?3)")
  public List<SMaterial> getByCompanyIdAndMaterialTypeAndQuery(Long companyId,Long materialTypeId,String q);
  
  @Query("select s from SMaterial s where s.company.idCompany=?1 and s.SMaterialTypeDic.id in ?2 and (s.pno like ?3 or s.rev like ?3 or s.des like ?3)")
  public List<SMaterial> getByCompanyIdAndMaterialTypesAndQuery(Long companyId,List<Long> types,String q);
  
  @Query("select s from SMaterial s where s.company.idCompany=?1 and (s.pno like ?2 or s.rev like ?2 or s.des like ?2)")
  public List<SMaterial> getByCompanyIdAndQuery(Long companyId,String q);
  
  @Query("select s from SMaterial s where s.company.idCompany=?1 and s.pno=?2")
  public SMaterial getByCompanyIdAndPno(Long companyId,String pno);
  
  
  @Query("select s from SMaterial s where s.safetyInv is not null and s.SMaterialTypeDic.id=4 and s.idMaterial not in (select i.SMaterial.idMaterial from SInventory i where i.SBin.idBin in (select b.idBin from SBin b where b.SStk.stkName='IDI') group by i.SMaterial.idMaterial having sum(i.qty)>=s.safetyInv) order by s.company.idCompany") 
  public List<SMaterial> getUnsaftyMaterials();
		
}