package com.jms.repositories.m;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.MMachineGroup;


@Repository
public interface MMachineGroupRepository extends JpaRepository<MMachineGroup, Long>{
	
	@Query("select m from MMachineGroup m where m.groupName=?1 and m.idCompany=?2")
	public MMachineGroup findByGroupNameAndIdCompany(String groupName,Long companyId);
	
	@Query("select m from MMachineGroup m where m.idCompany=?1")
	public List<MMachineGroup> findByIdCompany(Long companyId);
}
