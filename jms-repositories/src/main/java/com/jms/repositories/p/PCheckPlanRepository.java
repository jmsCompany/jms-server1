package com.jms.repositories.p;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PCheckPlan;


@Repository
public interface PCheckPlanRepository extends JpaRepository<PCheckPlan, Long>{

    @Query("select p from PCheckPlan p where p.usersByCreator.idUser=?1 and p.PCPp.idCPp=?2 order by p.planCheckTime desc")
	public List<PCheckPlan> getByUserIdAndCppId(Long userId,Long pcppId);
    
    
    @Query("select p from PCheckPlan p where p.PCPp.idCPp=?1 order by p.planCheckTime desc")
	public List<PCheckPlan> getByCppId(Long pcppId);
//    and DATE(p.checkTime)=CURDATE()
    @Query("select p from PCheckPlan p where p.usersByCreator.company.idCompany=?1  order by p.PCPp.idCPp asc,p.planCheckTime desc")
	public List<PCheckPlan> getCheckPlans(Long companyId);
    
    
    @Query("select p from PCheckPlan p where p.PCPp.idCPp=?1  and p.PCPp.PRoutineD.isFinished=1 order by p.idCheck DESC")
	public List<PCheckPlan> getMaxCheckPlanByCppId(Long pcppId);
    
}
