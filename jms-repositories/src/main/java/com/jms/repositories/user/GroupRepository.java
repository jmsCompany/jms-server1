package com.jms.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.Groups;

@Repository
public interface GroupRepository extends JpaRepository<Groups, Long>{
	
	@Query("select g from Groups g where g.groupName=?1 and g.company.idCompany=?2 and g.groupType.groupType=?3")
	public Groups findGroupByGroupNameAndCompany(String groupName,Long idCompany,String groupType);
	
}
