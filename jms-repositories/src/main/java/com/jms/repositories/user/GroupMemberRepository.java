package com.jms.repositories.user;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.GroupMembers;
import com.jms.domain.db.GroupMembersId;


@Repository
public interface GroupMemberRepository extends CrudRepository<GroupMembers, GroupMembersId>{
	
	@Query("select gm from GroupMembers gm where gm.id.idGroup=?1")
	public List<GroupMembers> findGroupMembersByGroupId(Long idGroup);

}
