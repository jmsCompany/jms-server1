package com.jms.repositories.p;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PCheckTime;


@Repository
public interface PCheckTimeRepository extends JpaRepository<PCheckTime, Long>{

    @Query("select p from PCheckTime p where p.MMachine.company.idCompany=?1")
	public List<PCheckTime> getByCompanyId(Long companyId);
}
