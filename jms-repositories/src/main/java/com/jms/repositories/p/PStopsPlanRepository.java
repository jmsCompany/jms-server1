package com.jms.repositories.p;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PStopsPlan;


@Repository
public interface PStopsPlanRepository extends JpaRepository<PStopsPlan, Long>{

}
