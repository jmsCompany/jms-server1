package com.jms.repositories.p;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PShiftPlanD;



@Repository
public interface PShiftPlanDRepository extends JpaRepository<PShiftPlanD, Long>{


}
