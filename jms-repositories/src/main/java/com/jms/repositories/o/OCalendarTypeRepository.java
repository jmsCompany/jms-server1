package com.jms.repositories.o;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.OCalendarType;



@Repository
public interface OCalendarTypeRepository extends JpaRepository<OCalendarType, Long>{
	

}
