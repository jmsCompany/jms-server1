package com.jms.repositories.m;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.MRepairHistory;


@Repository
public interface MRepairHistoryRepository extends JpaRepository<MRepairHistory, Long>{
	
	@Query("select m from MRepairHistory m where m.MMachine.company.idCompany= ?1")
	public List<MRepairHistory> findByCompanyId(Long companyId);
	
	@Query("select m from MRepairHistory m where m.MMachine.idMachine= ?1")
	public List<MRepairHistory> findByIdMachine(Long idMachine);
		
	@Query("select m from MRepairHistory m where m.idUnplannedStop= ?1 and m.MStatusDic.idMstatusDic<>9")
	public MRepairHistory findByIdUnplannedStop(Long idUnplannedStop);
	

}
