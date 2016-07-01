package com.jms.repositories.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
	
	@Modifying
	@Query(value="delete from group_members where id_user=?1 and id_group=?2",nativeQuery=true)
	public void deleteByIdUserAndIdGroup(Long idUser,Long idGroup);
	

}
