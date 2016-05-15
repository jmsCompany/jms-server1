package com.jms.repositories.p;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PStopsPlan;


@Repository
public interface PStopsPlanRepository extends JpaRepository<PStopsPlan, Long>{
	
	@Query("select p from PStopsPlan p where p.company.idCompany=?1 and p.PStatusDic.idPstatus=16")
	public List<PStopsPlan> getPStopsPlansByCompanyId(Long companyId);

}
