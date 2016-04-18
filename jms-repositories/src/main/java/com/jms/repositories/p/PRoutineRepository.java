package com.jms.repositories.p;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PRoutine;



@Repository
public interface PRoutineRepository extends JpaRepository<PRoutine, Long>{


}
