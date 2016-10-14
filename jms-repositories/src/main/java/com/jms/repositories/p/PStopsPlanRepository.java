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
	
	@Query("select p from PStopsPlan p where p.company.idCompany=?1 and p.PStatusDic.idPstatus=?2 order by p.planSt desc")
	public List<PStopsPlan> getPStopsPlansByCompanyIdAndStatusId(Long companyId,Long statusId);
	
//	and DATE(p.planSt)=CURDATE() 
	@Query("select p from PStopsPlan p where p.company.idCompany=?1  and p.MMachine.idMachine=?2 and p.planSt<?3 and p.actFt is null order by p.planSt desc")
	public List<PStopsPlan> getPStopsPlansByCompanyIdAndMachineId(Long companyId,Long machineId,Date future);
	
	@Query("select p from PStopsPlan p where p.MMachine.idMachine=?1 and p.actSt>=?2 and p.actSt<=?3")
	public List<PStopsPlan> getPStopsPlansByMachineIdAndDuration(Long machineId,Date start,Date end);
	
	//需要提醒的计划停机
	@Query("select p from PStopsPlan p where p.planSt<=?1 and p.remind is null")
	public List<PStopsPlan> getPStopsPlansBydRemindTime(Date remind);

}
