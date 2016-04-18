package com.jms.repositories.p;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PRoutine;
import com.jms.domain.db.PRoutineD;
import com.jms.domain.db.PRoutineDAtt;
import com.jms.domain.db.PRoutineDCategory;
import com.jms.domain.db.PShiftPlan;



@Repository
public interface PShiftPlanRepository extends JpaRepository<PShiftPlan, Long>{


}
