package com.jms.repositories.o;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.OCalendar;
import com.jms.domain.db.OCalendarType;
import com.jms.domain.db.PActualSetup;



@Repository
public interface OCalendarRepository extends JpaRepository<OCalendar, Long>{
	
	@Query("select o from OCalendar o where o.idCompany=?1 order by o.date")
	public List<OCalendar> findByCompanyId(Long companyId);
	
	
	@Query("select o from OCalendar o where o.idCompany=?1 and o.year=?2 order by o.date")
	public List<OCalendar> findByCompanyIdAndYear(Long companyId,Long year);
}
