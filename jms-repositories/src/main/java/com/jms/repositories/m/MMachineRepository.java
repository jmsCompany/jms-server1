package com.jms.repositories.m;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.MMachine;


@Repository
public interface MMachineRepository extends JpaRepository<MMachine, Long>{


	 @Query("select m from MMachine m where m.company.idCompany=?1 and m.MStatusDic.idMstatusDic=5")
	  public List<MMachine> getActiveMachinesByCompanyId(Long companyId);
	 
	 
	 @Query("select m from MMachine m where m.PLine.idPline=?1")
	  public List<MMachine> getByLineId(Long lineId);
	 
	 
	 @Query("select m from MMachine m where m.company.idCompany=?1 and (m.code like ?2 or m.name like ?2 or m.spec like ?2 or m.MMachineGroup.groupName like ?2)")
	  public List<MMachine> getMachinesByCompanyIdAndQ(Long companyId,String q);
	 @Query("select m from MMachine m where m.company.idCompany=?1")
	  public List<MMachine> getMachinesByCompanyId(Long companyId);
	 
}
