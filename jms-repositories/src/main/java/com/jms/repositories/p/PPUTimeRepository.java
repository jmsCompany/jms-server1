package com.jms.repositories.p;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PUTime;


@Repository
public interface PPUTimeRepository extends JpaRepository<PUTime, Long>{

	
}
