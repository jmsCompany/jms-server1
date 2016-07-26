package com.jms.repositories.p;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PMr;



@Repository
public interface PMrRepository extends JpaRepository<PMr, Long>{
 
   @Query("select p from PMr p where p.type=?1 and p.PStatusDic.idPstatus=9")
   public List<PMr> getByType(Long type);
   
   
   @Query("select p from PMr p where p.type=?1 and p.PCPp.company.idCompany=?2 and p.PStatusDic.idPstatus=9")
   public List<PMr> getByTypeAndCompanyId(Long type,Long companyId);
   
   
   @Query("select p from PMr p where p.type=?1 and p.PCPp.idCPp=?2")
   public List<PMr> getByTypeAndCppId(Long type,Long cppId);
   
   public List<PMr> getByIdUnplannedStop(Long idUnplannedStop);
   

}
