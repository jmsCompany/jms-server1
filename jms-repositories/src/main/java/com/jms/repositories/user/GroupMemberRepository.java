package com.jms.repositories.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.GroupMembers;
import com.jms.domain.db.GroupMembersId;
import com.jms.domain.db.Users;


@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMembers, GroupMembersId>{
	
	@Query("select gm from GroupMembers gm where gm.id.idGroup=?1")
	public List<GroupMembers> findGroupMembersByGroupId(Long idGroup);
	@Query("select gm from GroupMembers gm where gm.id.idUser=?1")
	public List<GroupMembers> findGroupMembersByUserId(Long idUser);
	
	
	@Query("select distinct g.users from GroupMembers g where g.groups.company.idCompany=?1 and g.groups.groupName='OP'")	
	public List<Users> findOPByCompanyId(Long companyId);
	

}
