package com.jms.acl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.jms.domain.db.AbstractSecuredEntity;
import com.jms.domain.db.Users;
import com.jms.web.security.JMSUserDetailService;
import com.jms.web.security.JMSUserDetails;
import com.jms.web.security.SecurityUtils;

@Service
public class SecuredObjectService {

	private final static Permission[] HAS_ADMIN = new Permission[] { BasePermission.ADMINISTRATION };
	private final static Permission[] HAS_READ = new Permission[] {
			BasePermission.ADMINISTRATION, BasePermission.READ };

	@Autowired
	private SecurityACLDAO securityACLDAO;
	@Autowired
	private SecurityUtils securityUtils;
	@Autowired
	private SecuredObjDAO securedObjDAO;
	@Autowired
	private MutableAclService mutableAclService;
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private PermissionEvaluator permissionEvaluator;
	@Autowired
	private JMSUserDetailService userDetailService;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Transactional(readOnly = true)
	public <T extends AbstractSecuredEntity> Map<T,String> getSecuredObjectsWithPermissions(
			List<T> secureObjList) {
	
		Map<T, String> pMap = new LinkedHashMap<T, String>(
				secureObjList.size());
		
		Authentication user = SecurityContextHolder.getContext()
				.getAuthentication();
		
		for (T obj : secureObjList) {
			if(permissionEvaluator
					.hasPermission(user, obj, HAS_ADMIN))
					pMap.put(obj, "ADMIN");
			else if(permissionEvaluator
					.hasPermission(user, obj, HAS_READ))
				    pMap.put(obj, "READ");
		}
		
		return pMap;
			
	}
	
	
	@Transactional(readOnly = true)
	public <T extends AbstractSecuredEntity> Map<T,String> getSecuredObjectsWithPermissions(
			Users user, List<T> secureObjList) {
	
		Map<T, String> pMap = new LinkedHashMap<T, String>(
				secureObjList.size());
		
		JMSUserDetails userDetails = (JMSUserDetails)userDetailService.loadUserByUsername(""+user.getIdUser());
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				userDetails.getUsername(), userDetails.getPassword());
		Authentication userAuth = authenticationManager
				.authenticate(authentication);
		
		for (T obj : secureObjList) {
			if(permissionEvaluator
					.hasPermission(userAuth, obj, HAS_ADMIN))
					pMap.put(obj, "ADMIN");
			else if(permissionEvaluator
					.hasPermission(userAuth, obj, HAS_READ))
				    pMap.put(obj, "READ");
		}
		
		return pMap;
			
	}

	@Transactional(readOnly = true)
	public Map<String, String> findSidPermissionMap(Class clazz, Long id,
			String sidType) {
		ObjectIdentity oid = new ObjectIdentityImpl(clazz, id);
		MutableAcl acl = (MutableAcl) mutableAclService.readAclById(oid);
		List<AccessControlEntry> entries = acl.getEntries();
		Map<String, String> sidPermMap = new HashMap<String, String>();
		// System.out.println("sidType: " + sidType);
		if (sidType.equalsIgnoreCase("group")) {
			for (AccessControlEntry entry : entries) {
				Sid sid = entry.getSid();
				String perm = "";
				if (entry.getPermission().equals(BasePermission.READ)) {
					perm = "READ";
				} else if (entry.getPermission().equals(
						BasePermission.ADMINISTRATION)) {
					perm = "ADMIN";
				} else {
					perm = "" + entry.getPermission().getMask();
				}
				if (sid instanceof PrincipalSid) {

					PrincipalSid p = (PrincipalSid) sid;

				} else {
					GrantedAuthoritySid r = (GrantedAuthoritySid) sid;
				
					String group = r.getGrantedAuthority();
					sidPermMap.put(group, perm);

				}

			}
		} else if (sidType.equalsIgnoreCase("user")) {

			for (AccessControlEntry entry : entries) {
				Sid sid = entry.getSid();
				String perm = "";
				if (entry.getPermission().equals(BasePermission.READ)) {
					perm = "READ";
				} else if (entry.getPermission().equals(
						BasePermission.ADMINISTRATION)) {
					perm = "ADMIN";
				} else {
					perm = "" + entry.getPermission().getMask();
				}
				if (sid instanceof PrincipalSid) {

					PrincipalSid p = (PrincipalSid) sid;
					sidPermMap.put(p.getPrincipal(), perm);

				} else {
					GrantedAuthoritySid r = (GrantedAuthoritySid) sid;

				}

			}

		}

		return sidPermMap;
	}

	@Transactional(readOnly = true)
	public List<AccessControlEntry> getAccessEntries(
			AbstractSecuredEntity element) {
		ObjectIdentity oid = new ObjectIdentityImpl(element);
		MutableAcl acl = (MutableAcl) mutableAclService.readAclById(oid);
		List<AccessControlEntry> entries = acl.getEntries();
		return entries;
	}

	@Transactional(readOnly = false)
	public void changePerm(Class clazz, long id, SidPerm sidPerm, String perm) {
		if (sidPerm.getPermission().equalsIgnoreCase(perm))
			return;
		else {
			AbstractSecuredEntity element = securedObjDAO.find(clazz, id);
			Sid recipient;
			if (sidPerm.getType().equalsIgnoreCase("user")) {
				recipient = new PrincipalSid(sidPerm.getSid());
			} else if (sidPerm.getType().equalsIgnoreCase("group")) {
				recipient = new GrantedAuthoritySid(sidPerm.getSid());
			} else {
				recipient = new PrincipalSid(sidPerm.getSid());
			}
			Permission permission;
			if (sidPerm.getPermission().equalsIgnoreCase("admin")) {
				permission = BasePermission.ADMINISTRATION;
			} else // read
			{
				permission = BasePermission.READ;
			}
			Permission newPermission;
			if (perm.equalsIgnoreCase("admin")) {
				newPermission = BasePermission.ADMINISTRATION;
			} else // read
			{
				newPermission = BasePermission.READ;
			}
			securityACLDAO.deletePermission(element, clazz, recipient,
					permission);
			securityACLDAO.addPermission(element, recipient, newPermission);

		}

	}

	@Transactional(readOnly = false)
	public void savePerm(Class clazz, Long id, String type, String sid,
			String perm) {
		AbstractSecuredEntity element = securedObjDAO.find(clazz, id);
		Sid recipient;
		if (type.equalsIgnoreCase("user")) {
			recipient = new PrincipalSid(sid);
		} else if (type.equalsIgnoreCase("group")) {
			recipient = new GrantedAuthoritySid(sid);
		} else {
			recipient = new PrincipalSid(sid);
		}
		Permission permission;
		if (perm.equalsIgnoreCase("admin")) {
			permission = BasePermission.ADMINISTRATION;
		} else // read
		{
			permission = BasePermission.READ;
		}
		securityACLDAO.addPermission(element, recipient, permission);
	}

	@Transactional(readOnly = false)
	public void removePerm(Class clazz, Long id, String type, String sid,
			String perm) {
		AbstractSecuredEntity element = securedObjDAO.find(clazz, id);
		Sid recipient;
		if (type.equalsIgnoreCase("user")) {
			recipient = new PrincipalSid(sid);
		} else if (type.equalsIgnoreCase("group")) {
			recipient = new GrantedAuthoritySid(sid);
		} else {
			recipient = new PrincipalSid(sid);
		}
		Permission permission;
		if (perm.equalsIgnoreCase("admin")) {
			permission = BasePermission.ADMINISTRATION;
		} else // read
		{
			permission = BasePermission.READ;
		}
		securityACLDAO.deletePermission(element, clazz, recipient, permission);

	}

	public SidPerm getPerm(String sid, String perm, String type) {
		SidPerm sidPerm = new SidPerm();
		sidPerm.setSid(sid);
		sidPerm.setPermission(perm);
		sidPerm.setType(type);
		return sidPerm;
	}


}
