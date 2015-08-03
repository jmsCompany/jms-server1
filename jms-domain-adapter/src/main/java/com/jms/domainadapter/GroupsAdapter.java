package com.jms.domainadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.Company;
import com.jms.domain.db.Groups;
import com.jms.domain.ws.WSGroup;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.user.GroupTypeRepository;


@Service @Transactional(readOnly=true)
public class GroupsAdapter {
	private @Autowired  CompanyRepository companyRepository;
	private @Autowired  GroupTypeRepository groupTypeRepository;
	public Groups toDBGroup(WSGroup wsGroup,Groups group) throws Exception
	{
		Groups g = (Groups)BeanUtil.shallowCopy(wsGroup,Groups.class,group);
		Company company =companyRepository.findByCompanyName(wsGroup.getCompanyName());
		g.setCompany(company);
		g.setGroupType(groupTypeRepository.findByGroupType(wsGroup.getType()));
		return g;
	}


	public WSGroup toWSGroup(Groups group) throws Exception
	{
		WSGroup wsGroup= (WSGroup)BeanUtil.shallowCopy(group,WSGroup.class,null);
		wsGroup.setCompanyName(group.getCompany().getCompanyName());
		wsGroup.setType(group.getGroupType().getGroupType());
		return wsGroup;
	}

}