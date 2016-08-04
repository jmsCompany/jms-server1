package com.jms.repositories.m;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.MMainCycle;




@Repository
public interface MMainCycleRepository extends JpaRepository<MMainCycle, Long>{

}
