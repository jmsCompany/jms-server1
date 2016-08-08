package com.jms.repositories.p;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PStopsPlan;


@Repository
public interface PStopsPlanRepository extends JpaRepository<PStopsPlan, Long>{
	
//	and DATE(p.planSt)=CURDATE()
	@Query("select p from PStopsPlan p where p.company.idCompany=?1  order by p.planSt desc")
	public List<PStopsPlan> getPStopsPlansByCompanyId(Long companyId);
	
//	and DATE(p.planSt)=CURDATE() 
	@Query("select p from PStopsPlan p where p.company.idCompany=?1  and p.MMachine.idMachine=?2 and now()>p.planSt and now()<p.planFt order by p.planSt desc")
	public List<PStopsPlan> getPStopsPlansByCompanyIdAndMachineId(Long companyId,Long machineId);
	
	@Query("select p from PStopsPlan p where p.MMachine.idMachine=?1 and p.actSt>=?2 and p.actSt<=?3")
	public List<PStopsPlan> getPStopsPlansByMachineIdAndDuration(Long machineId,Date start,Date end);

}
