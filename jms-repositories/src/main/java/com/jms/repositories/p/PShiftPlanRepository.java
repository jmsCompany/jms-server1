package com.jms.repositories.p;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PShiftPlan;



@Repository
public interface PShiftPlanRepository extends JpaRepository<PShiftPlan, Long>{

	@Query("select p from PShiftPlan p where p.company.idCompany=?1")
    public List<PShiftPlan> getByCompanyId(Long companyId);
}
