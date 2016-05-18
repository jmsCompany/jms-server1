package com.jms.repositories.p;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PCheckPlan;


@Repository
public interface PCheckPlanRepository extends JpaRepository<PCheckPlan, Long>{

    @Query("select p from PCheckPlan p where p.usersByCreator.idUser=?1 and p.PCPp.idCPp=?2 and DATE(p.checkTime)=CURDATE() order by p.planCheckTime desc")
	public List<PCheckPlan> getByUserIdAndCppId(Long userId,Long pcppId);
}
