package com.jms.domainadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.Company;
import com.jms.domain.db.Roles;
import com.jms.domain.ws.WSRoles;
import com.jms.repositories.company.CompanyRepository;

@Service @Transactional(readOnly=true)
public class RoleAdapter {
	private @Autowired  CompanyRepository companyRepository;
	public Roles toDBRole(WSRoles wsRoles) throws Exception
	{
		Roles roles = (Roles)BeanUtil.shallowCopy(wsRoles,Roles.class);
		Company company =companyRepository.findByCompanyName(wsRoles.getCompanyName());
		roles.setCompany(company);
		return roles;
	}


	public WSRoles toWSRole(Roles roles) throws Exception
	{
		WSRoles wsRoles = (WSRoles)BeanUtil.shallowCopy(roles,WSRoles.class);
		wsRoles.setCompanyName(roles.getCompany().getCompanyName());
		return wsRoles;
	}

}
