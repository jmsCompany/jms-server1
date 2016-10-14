package com.jms.repositories.o;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.OOvertime;



@Repository
public interface OOvertimeRepository extends JpaRepository<OOvertime, Long>{
	
	@Query("select o from OOvertime o where o.idCompany=?1 order by o.creationTime")
	public List<OOvertime> findByCompanyId(Long companyId);
}
