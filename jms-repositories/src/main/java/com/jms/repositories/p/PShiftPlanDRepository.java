package com.jms.repositories.p;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PShiftPlanD;



@Repository
public interface PShiftPlanDRepository extends JpaRepository<PShiftPlanD, Long>{
	
	@Query("select p from PShiftPlanD p where p.PShiftPlan.company.idCompany=?1")
	public List<PShiftPlanD> getByCompanyId(Long companyId);
	
	
	@Query("select p from PShiftPlanD p where p.PShiftPlan.idShiftPlan=?1")
	public List<PShiftPlanD> getByShiftId(Long shiftId);
}
