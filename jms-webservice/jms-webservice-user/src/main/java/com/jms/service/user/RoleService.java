package com.jms.service.user;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.domain.db.Company;
import com.jms.domain.db.Module;
import com.jms.domain.db.RolePriv;
import com.jms.domain.db.RolePrivId;
import com.jms.domain.db.Roles;
import com.jms.domain.db.Sector;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.MessageTypeEnum;
import com.jms.domain.ws.WSModulePriv;
import com.jms.domain.ws.WSRolePrivs;
import com.jms.domain.ws.WSRoles;
import com.jms.domain.ws.WSSector;
import com.jms.domainadapter.RoleAdapter;
import com.jms.messages.MessagesUitl;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.system.ModuleRepository;
import com.jms.repositories.user.RolePrivRepository;
import com.jms.repositories.user.RoleRepository;

@Service
@Transactional
public class RoleService {

	private static final Logger logger = LogManager.getLogger(RoleService.class
			.getCanonicalName());
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private RolePrivRepository rolePrivRepository;
	@Autowired
	private MessagesUitl messagesUitl;
	@Autowired
	private RoleAdapter roleAdapter;
	private @Autowired ModuleRepository moduleRepository;

	public void loadRolesFromCSV(InputStream inputStream) throws IOException {
		CsvReader reader = new CsvReader(inputStream, ',',
				Charset.forName("UTF-8"));

		reader.readHeaders(); // CompanyName, Role Description
		while (reader.readRecord()) {
			Roles r = new Roles();
			r.setRole(reader.get("Role"));
			r.setDescription(reader.get("Description"));
			r.setCompany(companyRepository.findByCompanyName(reader
					.get("CompanyName")));
			logger.debug("role: " + r.getRole() + ", description: "
					+ r.getDescription());
			save(r);
		}
	}
 
	public void createDefaultRoles(Company company)
	{
		save("user","员工",company);
		save("admin","管理员",company);
		save("group_admin","组管理员",company);
		save("sector_supervisor","部门经理",company);
		save("manager","总经理",company);
	}
	
	public Roles save(String role,String description,Company company)
	{
		Roles r = new Roles();
		r.setCompany(company);
		r.setRole(role);
		r.setDescription(description);
        return save(r);
	}
	
	public Roles save(Roles role) {
		Roles r;
		if (role.getCompany() == null) {
			r = roleRepository.findByRole(role.getRole());
		} else {
			r = roleRepository.findByRoleAndCompanyName(role.getRole(), role
					.getCompany().getCompanyName());
		}

		if (r == null) {
			roleRepository.save(role); // create new
		} else {
			role.setIdRole(r.getIdRole()); // update by Id
			roleRepository.save(role);
		}
		return r;
	}

	public Message addRole(WSRoles wsRoles) throws Exception {
		Message msg = checkRole(wsRoles);
		if (msg.getMessageTypeEnum().equals(MessageTypeEnum.ERROR))
			return msg;

		Roles roles = roleAdapter.toDBRole(wsRoles);
		roleRepository.save(roles);
		return messagesUitl.getMessage("company.role.success", null,
				MessageTypeEnum.INFOMATION);
	}

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

	public void addRolePriv(Long idRole, Long idModule) {
		RolePrivId id = new RolePrivId(idModule, idRole);
		RolePriv rp = new RolePriv();
		rp.setId(id);
		rp.setPriv(1l);
		rolePrivRepository.save(rp);
	}

	public Message addRolePrivs(WSRolePrivs wsRolePrivs) {
		String companyName = wsRolePrivs.getCompanyName();
		if (companyName == null || companyName.isEmpty())
			return messagesUitl.getMessage("company.doesnotexist", null,
					MessageTypeEnum.ERROR);
		String role = wsRolePrivs.getRole();
		Roles roles = roleRepository
				.findByRoleAndCompanyName(role, companyName);
		if (roles == null)
			return messagesUitl.getMessage("company.role.doesnotexist", null,
					MessageTypeEnum.ERROR);

		for (Module mod : moduleRepository.findAll()) {
			RolePrivId id = new RolePrivId(mod.getIdModule(), roles.getIdRole());
			RolePriv rolePriv = rolePrivRepository.findOne(id);
			if (rolePriv != null) {
				int idx = checkModuleInTheList(mod.getName(),
						wsRolePrivs.getModulePrivList());
				if (idx != -1) {
					rolePriv.setPriv(wsRolePrivs.getModulePrivList().get(idx)
							.getPriv());
				} else {
					rolePriv.setPriv(0l);

				}
				rolePrivRepository.save(rolePriv);
			} else {
				int idx = checkModuleInTheList(mod.getName(),
						wsRolePrivs.getModulePrivList());
				rolePriv = new RolePriv();
				rolePriv.setId(id);
				if (idx != -1) {
					rolePriv.setPriv(wsRolePrivs.getModulePrivList().get(idx)
							.getPriv());
				} else {
					rolePriv.setPriv(0l);

				}
				rolePrivRepository.save(rolePriv);
			}

		}

		return messagesUitl.getMessage("company.role.priv.success", null,
				MessageTypeEnum.INFOMATION);
	}

	public WSRolePrivs getRolePrivs(String companyName, String role) {
		WSRolePrivs wsRolePrivs = new WSRolePrivs();
		wsRolePrivs.setCompanyName(companyName);
		wsRolePrivs.setRole(role);
		Roles roles = roleRepository
				.findByRoleAndCompanyName(role, companyName);
		for (RolePriv rp : roles.getRolePrivs()) {
			WSModulePriv wsModulePriv = new WSModulePriv();
			wsModulePriv.setName(rp.getModule().getName());
			wsModulePriv.setDescription(rp.getModule().getDescription());
			wsModulePriv.setPriv(rp.getPriv());
			wsRolePrivs.getModulePrivList().add(wsModulePriv);
		}
		return wsRolePrivs;
	}

	private int checkModuleInTheList(String module,
			List<WSModulePriv> wsModulePrivList) {
		int idx = 0;
		for (WSModulePriv m : wsModulePrivList) {
			if (m.getName().equals(module))
				return idx;
			idx++;
		}
		return -1;
	}
}
