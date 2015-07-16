package com.jms.service.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.GroupTypeEnum;
import com.jms.domain.db.Company;
import com.jms.domain.db.GroupMembers;
import com.jms.domain.db.GroupMembersId;
import com.jms.domain.db.Groups;
import com.jms.domain.db.Sector;
import com.jms.domain.db.Users;
import com.jms.repositories.user.GroupMemberRepository;
import com.jms.repositories.user.GroupRepository;
import com.jms.repositories.user.GroupTypeRepository;
import com.jms.repositories.user.RoleRepository;
import com.jms.web.security.SecurityUtils;


@Service
@Transactional
public class GroupService {
	
	@Autowired private GroupRepository groupRepository;
	@Autowired private GroupTypeRepository groupTypeRepository;
	@Autowired private GroupMemberRepository groupMemberRepository;
	@Autowired private RoleRepository roleRepository;
	@Autowired private SecurityUtils securityUtils;

	public void createDefaultGroups(Company company)
	{
	    Users dbUser=securityUtils.getCurrentDBUser();
		Groups g1 = new Groups();
		g1.setCompany(company);
		g1.setUsers(dbUser);
		g1.setCreationTime(new Date());
		g1.setGroupName(""+dbUser.getIdUser());
		g1.setDescription(dbUser.getName());
	    g1.setGroupType(groupTypeRepository.findByGroupType(GroupTypeEnum.User.name()));
	    groupRepository.save(g1);
	    
	    GroupMembers gm1 = new GroupMembers(); 
	    GroupMembersId id1 = new GroupMembersId();
	    id1.setIdGroup(g1.getIdGroup());
	    id1.setIdUser(dbUser.getIdUser());
	    gm1.setId(id1);
	    gm1.setRoles(roleRepository.findByRoleAndCompanyName("user", company.getCompanyName()));
	    groupMemberRepository.save(gm1);
	}

	public void addUserToDefaultGroup()
	{
		
	}
}
