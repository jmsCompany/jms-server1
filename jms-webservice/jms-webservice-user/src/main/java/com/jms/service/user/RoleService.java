package com.jms.service.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.SystemRoleEnum;
import com.jms.domain.db.Company;
import com.jms.domain.db.Groups;
import com.jms.domain.db.Roles;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.MessageTypeEnum;
import com.jms.domain.ws.WSRoles;
import com.jms.domainadapter.RoleAdapter;
import com.jms.messages.MessagesUitl;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.user.GroupRepository;
import com.jms.repositories.user.GroupTypeRepository;
import com.jms.repositories.user.RoleRepository;

@Service
public class RoleService {

	private static final Logger logger = LogManager.getLogger(RoleService.class
			.getCanonicalName());
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private MessagesUitl messagesUitl;
	@Autowired
	private RoleAdapter roleAdapter;
	@Autowired
	private GroupTypeRepository groupTypeRepository;
	
	@Transactional
	public void createDefaultRoles(Company company)
	{
		save(SystemRoleEnum.user.name(),"员工",company);
		save(SystemRoleEnum.admin.name(),"管理员",company);
//		save(SystemRoleEnum.group_admin.name(),"组管理员",company);
//		save(SystemRoleEnum.sector_supervisor.name(),"部门经理",company);
//		save(SystemRoleEnum.manager.name(),"总经理",company);
		
		save(SystemRoleEnum.OP.name(),"操作员",company);
		save(SystemRoleEnum.quality.name(),"质检员",company);
		save(SystemRoleEnum.warehouse.name(),"库管员",company);
		save(SystemRoleEnum.supervisor.name(),"主管",company);
		save(SystemRoleEnum.equipment.name(),"仪器维护员",company);
	}
	@Transactional
	public Roles save(String role,String description,Company company)
	{
		Roles r = new Roles();
		r.setCompany(company);
		r.setRole(role);
		r.setDescription(description);
		r=roleRepository.save(r);
		Groups g = new Groups();
		g.setCompany(company);
		g.setGroupName(""+r.getRole());
		g.setDescription(r.getDescription());
		g.setGroupType(groupTypeRepository.findOne(5l));
		groupRepository.save(g);
		
        return r;
	}
	
	@Transactional
	public Message addRole(WSRoles wsRoles) throws Exception {
		Message msg = checkRole(wsRoles);
		if (msg.getMessageTypeEnum().equals(MessageTypeEnum.ERROR))
			return msg;

		Roles roles = roleAdapter.toDBRole(wsRoles);
		roles = roleRepository.save(roles);
		Groups g = new Groups();
		g.setCompany(companyRepository.findByCompanyName(wsRoles.getCompanyName()));
		g.setGroupName(""+roles.getRole());
		g.setDescription(wsRoles.getDescription());
		g.setGroupType(groupTypeRepository.findOne(5l));
		groupRepository.save(g);
		return messagesUitl.getMessage("company.role.success", null,
				MessageTypeEnum.INFOMATION);
	}
	@Transactional(readOnly=true)
	public Message checkRole(WSRoles wsRoles) {

		if (companyRepository.findByCompanyName(wsRoles.getCompanyName()) == null)
			return messagesUitl.getMessage("company.doesnotexist", null,
					MessageTypeEnum.ERROR);

		if (roleRepository.findByRoleAndCompanyName(wsRoles.getRole(),
				wsRoles.getCompanyName()) == null)
			return messagesUitl.getMessage("company.role.avaible", null,
					MessageTypeEnum.INFOMATION);
		return messagesUitl.getMessage("company.role.alreadyexist", null,
				MessageTypeEnum.ERROR);
	}



}
