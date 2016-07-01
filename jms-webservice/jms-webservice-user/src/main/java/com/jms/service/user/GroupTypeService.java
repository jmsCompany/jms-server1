package com.jms.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.GroupTypeEnum;
import com.jms.domain.db.GroupType;
import com.jms.repositories.user.GroupTypeRepository;

@Service
@Transactional
public class GroupTypeService {
	
	@Autowired private GroupTypeRepository groupTypeRepository;
	public void loadGroupTypes()
	{
		GroupType company = new GroupType();
		company.setIdGroupType(GroupTypeEnum.Company.getStatusCode());
		company.setGroupType(GroupTypeEnum.Company.name());
		groupTypeRepository.save(company);
		
		GroupType user = new GroupType();
		user.setIdGroupType(GroupTypeEnum.User.getStatusCode());
		user.setGroupType(GroupTypeEnum.User.name());
		groupTypeRepository.save(user);
		
		GroupType sector = new GroupType();
		sector.setIdGroupType(GroupTypeEnum.Sector.getStatusCode());
		sector.setGroupType(GroupTypeEnum.Sector.name());
		groupTypeRepository.save(sector);
		
		GroupType group = new GroupType();
		group.setIdGroupType(GroupTypeEnum.Group.getStatusCode());
		group.setGroupType(GroupTypeEnum.Group.name());
		groupTypeRepository.save(group);
		

		
		
		GroupType role = new GroupType();
		role.setIdGroupType(GroupTypeEnum.Role.getStatusCode());
		role.setGroupType(GroupTypeEnum.Role.name());
		groupTypeRepository.save(role);
	}

}
