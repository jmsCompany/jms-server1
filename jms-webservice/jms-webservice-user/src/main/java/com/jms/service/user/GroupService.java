package com.jms.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.GroupTypeEnum;
import com.jms.domain.SystemRoleEnum;
import com.jms.domain.db.Company;
import com.jms.domain.db.GroupMembers;
import com.jms.domain.db.GroupMembersId;
import com.jms.domain.db.Groups;
import com.jms.domain.db.Roles;
import com.jms.domain.db.Users;
import com.jms.domain.ws.WSGroup;
import com.jms.domainadapter.GroupsAdapter;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.user.GroupMemberRepository;
import com.jms.repositories.user.GroupRepository;
import com.jms.repositories.user.GroupTypeRepository;
import com.jms.repositories.user.RoleRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class GroupService {

	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private GroupTypeRepository groupTypeRepository;
	@Autowired
	private GroupMemberRepository groupMemberRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private SecurityUtils securityUtils;
	@Autowired
	private GroupsAdapter groupsAdapter;
	@Autowired
	private CompanyRepository companyRepository;

	public void createDefaultGroups(Company company) {
		Users dbUser = securityUtils.getCurrentDBUser();
		Groups companyGroup = createGroup(company, GroupTypeEnum.Company,
				"全公司", "全公司", null);
		createGroup(company, GroupTypeEnum.Sector, "财务部", "财务部", 1l);
		createGroup(company, GroupTypeEnum.Sector, "总裁办", "总裁办", 2l);
		createGroup(company, GroupTypeEnum.Sector, "项目办", "项目办", 3l);
		Groups selfGroup = createGroup(company, GroupTypeEnum.User,
				"" + dbUser.getIdUser(), "" + dbUser.getIdUser(), null);

		Roles adminRole = roleRepository.findByRoleAndCompanyName(
				SystemRoleEnum.admin.name(), company.getCompanyName());
		addUserToDefaultGroup(dbUser, companyGroup, adminRole);
		Roles userRole = roleRepository.findByRoleAndCompanyName(
				SystemRoleEnum.user.name(), company.getCompanyName());
		addUserToDefaultGroup(dbUser, selfGroup, userRole);
	}

	public Groups createGroup(Company company, GroupTypeEnum groupTyppe,
			String groupName, String description, Long seq) {
		Users dbUser = securityUtils.getCurrentDBUser();
		Groups g = new Groups();
		g.setCompany(company);
		g.setUsers(dbUser);
		g.setCreationTime(new Date());
		g.setGroupName(groupName);
		g.setDescription(description);
		if (seq != null) {
			g.setSeq(seq);
		}
		g.setGroupType(groupTypeRepository.findByGroupType(groupTyppe.name()));
		groupRepository.save(g);
		return g;
	}

	public void addUserToDefaultGroup(Users user, Groups group, Roles role) {
		GroupMembers gm = new GroupMembers();
		GroupMembersId id = new GroupMembersId();
		id.setIdGroup(group.getIdGroup());
		id.setIdUser(user.getIdUser());
		gm.setId(id);
		gm.setRoles(role);
		groupMemberRepository.save(gm);
	}

	public WSGroup save(WSGroup wsGroup) throws Exception {
		Company company = companyRepository.findByCompanyName(wsGroup
				.getCompanyName());
		Groups group = groupRepository.findGroupByGroupNameAndCompany(
				wsGroup.getGroupName(), company.getIdCompany(), wsGroup.getType());
		if (wsGroup.getIdGroup() == null) {

			if (group != null)
				throw new Exception("该部门或则群组已经存在！");
			else {
				Groups g = groupsAdapter.toDBGroup(wsGroup, null);
				groupRepository.save(g);
				wsGroup.setIdGroup(g.getIdGroup());
			}
		} else {

			if (group != null
					&& !group.getIdGroup().equals(wsGroup.getIdGroup())) {
				throw new Exception("该部门或则群组已经存在！");
			} else {
				Groups g = groupsAdapter.toDBGroup(wsGroup, group);
				groupRepository.save(g);
			}

		}
		return wsGroup;
	}
	
	public List<WSGroup> getGroupsByIdCompany(Long idCompany) throws Exception
	{
		List<WSGroup> groups = new ArrayList<WSGroup>(0);
		for(Groups g: groupRepository.findByIdCompany(idCompany))
		{
			groups.add(groupsAdapter.toWSGroup(g));
		}
		return groups;
	}
	public List<WSGroup> getSectorsByIdCompany(Long idCompany) throws Exception
	{
		List<WSGroup> groups = new ArrayList<WSGroup>(0);
		for(Groups g: groupRepository.findByIdCompany(idCompany))
		{
			if(g.getGroupType().getGroupType().equals(GroupTypeEnum.Sector.name()))
			{
				groups.add(groupsAdapter.toWSGroup(g));
			}
			
		}
		return groups;
	}
	public WSGroup getWSGroup(Long idGroup) throws Exception
	{
		Groups g= groupRepository.findOne(idGroup);
		return groupsAdapter.toWSGroup(g);

	}
}
