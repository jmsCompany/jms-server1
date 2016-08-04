package com.jms.repositories.p;


import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PCPp;


@Repository
public interface PCPpRepository extends JpaRepository<PCPp, Long>{

    @Query("select p from PCPp p where p.company.idCompany=?1 order by p.planSt desc")
	public List<PCPp> getByCompanyId(Long companyId);
    

    @Query("select p from PCPp p where p.company.idCompany=?1 and p.PWo.idWo in ?2 order by p.PWo.idWo desc,p.PRoutineD.idRoutineD asc,p.planSt desc")
	public List<PCPp> getByCompanyIdAndWoIds(Long companyId,List<Long> woIds);
    
//    DATE(p.planSt)=CURDATE()
//    and now()+86400000>p.plantSt 
//   DATE_ADD(now(), INTERVAL 1 DAY)>  明天
    @Query("select p from PCPp p where p.company.idCompany=?1 and p.users.idUser=?2 and p.actFt is null order by p.planSt")
	public List<PCPp> getByCompanyIdAndUserId(Long companyId,Long userId);
    
    
    @Query("select p from PCPp p where p.PWo.idWo=?1 and p.planSt<now()")
	public List<PCPp> getShouldStartedByWoId(Long woId);
    
    @Query("select p from PCPp p where p.PRoutineD.idRoutineD=?1 and p.actFt is not null")
  	public List<PCPp> getFinishedCppByRoutineDId(Long routineDId);
    
    @Query("select p from PCPp p where  p.company.idCompany=?1 and p.planSt>?2 and p.planFt<?3 order by p.PWo.idWo")
  	public List<PCPp> getByFromDateToDate(Long companyId,Date st,Date ft);
    
}
