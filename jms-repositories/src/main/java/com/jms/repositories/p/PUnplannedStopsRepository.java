package com.jms.repositories.p;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PUnplannedStops;



@Repository
public interface PUnplannedStopsRepository extends JpaRepository<PUnplannedStops, Long>{
	
	@Query("select p from PUnplannedStops p where p.idMachine=?1")
	public List<PUnplannedStops> getByMachineId(Long machineId);
	
	@Query("select p from PUnplannedStops p, MMachine m where p.idMachine=m.idMachine and m.company.idCompany=?1")
	public List<PUnplannedStops> getByCompanyId(Long companyId);
	
	
	
	@Query("select p from PUnplannedStops p where p.idMachine=?1 and p.PStatusDic.idPstatus=?2")
	public List<PUnplannedStops> getByMachineIdAndStatusId(Long machineId,Long statusId);
	
	
	@Query("select p from PUnplannedStops p where p.PSubCode.idSubCode=?1 and p.idCompany=?2 and p.PStatusDic.idPstatus=18")
	public List<PUnplannedStops> getByPSubCodeId(Long pSubCodeId, Long companyId);
	
}
