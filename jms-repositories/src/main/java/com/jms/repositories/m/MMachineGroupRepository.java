package com.jms.repositories.m;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.MMachineGroup;



@Repository
public interface MMachineGroupRepository extends JpaRepository<MMachineGroup, Long>{


	public MMachineGroup findByGroupName(String groupName);
}
