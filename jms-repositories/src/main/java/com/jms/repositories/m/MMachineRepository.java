package com.jms.repositories.m;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.MMachine;



@Repository
public interface MMachineRepository extends JpaRepository<MMachine, Long>{


}
