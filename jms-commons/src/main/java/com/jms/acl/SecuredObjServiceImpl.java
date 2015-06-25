package com.jms.acl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.AbstractSecuredObject;
import com.jms.domain.db.Users;
import com.jms.repositories.user.UsersRepository;


@Service("securedObjService")
@Transactional(readOnly = true)
public class SecuredObjServiceImpl implements SecuredObjService {

	@Autowired
	private SecuredObjDAO securedObjDAO;
	@Autowired
	private JdbcMutableAclService aclService;
	@Autowired
	private ACLService ACLService;
	@Autowired private UsersRepository usersRepository;
	
	@Autowired
	private PermissionEvaluator permissionEvaluator;
	private final static Permission[] HAS_DELETE = new Permission[] {
		BasePermission.DELETE, BasePermission.ADMINISTRATION };
private final static Permission[] HAS_ADMIN = new Permission[] { BasePermission.ADMINISTRATION };
private final static Permission[] HAS_READ = new Permission[] {
		BasePermission.ADMINISTRATION, BasePermission.READ };

	@Override
	public <T extends AbstractSecuredObject> T find(Class<T> clazz, long id) {
		return securedObjDAO.find(clazz, id);
	}

	@Override
	public Map<String, String> findSidPermissionMap(Class clazz, long id,
			String sidType) {
		ObjectIdentity oid = new ObjectIdentityImpl(clazz, id);
		MutableAcl acl = (MutableAcl) aclService.readAclById(oid);
		List<AccessControlEntry> entries = acl.getEntries();
		Map<String,String> sidPermMap = new HashMap<String,String>();
		//System.out.println("sidType: " + sidType);
		if(sidType.equalsIgnoreCase("group"))
		{
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
					String group =r.getGrantedAuthority();
					group = group.substring(5);
					sidPermMap.put(group, perm);
			
				}

			}
		}
		else if(sidType.equalsIgnoreCase("user"))
		{

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

	@Override
	public List<AccessControlEntry> getAccessEntries(
			AbstractSecuredObject abstractSecureObject, Class domainClass) {
		ObjectIdentity oid = new ObjectIdentityImpl(domainClass,
				abstractSecureObject.getId());
		MutableAcl acl = (MutableAcl) aclService.readAclById(oid);
		List<AccessControlEntry> entries = acl.getEntries();
		return entries;
	}

	@Override
	public SidPerm getPerm(String sid, String perm, String type) {
		SidPerm sidPerm = new SidPerm();
		sidPerm.setSid(sid);
		sidPerm.setPermission(perm);
		sidPerm.setType(type);
		return sidPerm;
	}

	@Override @Transactional(readOnly = false)
	public void changePerm(Class clazz, long id, SidPerm sidPerm, String perm) {
		if(sidPerm.getPermission().equalsIgnoreCase(perm))
			return;
		else
		{
			AbstractSecuredObject abstractSecureObject = securedObjDAO.find(clazz, id);
			Sid recipient;
			if(sidPerm.getType().equalsIgnoreCase("user"))
			{
				recipient = new PrincipalSid(sidPerm.getSid());
			}
			else if(sidPerm.getType().equalsIgnoreCase("group"))
			{
				recipient = new GrantedAuthoritySid("ROLE_"+sidPerm.getSid());
			}
			else
			{
				recipient = new PrincipalSid(sidPerm.getSid());
			}
			Permission permission;
			if(sidPerm.getPermission().equalsIgnoreCase("admin"))
			{
				permission = BasePermission.ADMINISTRATION;
			}
			else  //read
			{
				permission = BasePermission.READ;
			}
			Permission newPermission;
			if(perm.equalsIgnoreCase("admin"))
			{
				newPermission = BasePermission.ADMINISTRATION;
			}
			else  //read
			{
				newPermission = BasePermission.READ;
			}
			ACLService.deletePermission(abstractSecureObject, clazz, recipient, permission);
			ACLService.addPermission(abstractSecureObject, clazz, recipient, newPermission);
			// log.debug("class: " + clazz.getCanonicalName() +", id: " +id + ", sid: " + sidPerm.getSid() +",type: " + sidPerm.getType()+ ", from: " + sidPerm.getPermission() +" change to: " +perm);
		}
		
		
	}
/*
	@Override
	public List<String> getSids(String type) {
		List<String> sids = new ArrayList<String>(0);
		if(type.equalsIgnoreCase("user"))
		{
			for(Users u : usersRepository.findAll())
			{
				sids.add(""+ u.getIdUser());
			}
		}
		else if(type.equalsIgnoreCase("group"))
		{
			List<String> groups = authorityService.findGroups();
			for(String g: groups)
			{
				sids.add(g.substring(5));
			}
		}
		else
		{
			try {
				throw new Exception("no such Sid type");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sids;
	}
*/
	@Override @Transactional(readOnly = false)
	public void savePerm(Class clazz, long id, String type, String sid,
			String perm) {
		AbstractSecuredObject abstractSecureObject = securedObjDAO.find(clazz, id);
		Sid recipient;
		if(type.equalsIgnoreCase("user"))
		{
			recipient = new PrincipalSid(sid);
		}
		else if(type.equalsIgnoreCase("group"))
		{
			recipient = new GrantedAuthoritySid("ROLE_"+sid);
		}
		else
		{
			recipient = new PrincipalSid(sid);
		}
		Permission permission;
		if(perm.equalsIgnoreCase("admin"))
		{
			permission = BasePermission.ADMINISTRATION;
		}
		else  //read
		{
			permission = BasePermission.READ;
		}
		ACLService.addPermission(abstractSecureObject, clazz, recipient, permission);
	}

	@Override  @Transactional(readOnly = false)
	public void removePerm(Class clazz, long id, String type, String sid,
			String perm) {
		AbstractSecuredObject abstractSecureObject = securedObjDAO.find(clazz, id);
		Sid recipient;
		if(type.equalsIgnoreCase("user"))
		{
			recipient = new PrincipalSid(sid);
		}
		else if(type.equalsIgnoreCase("group"))
		{
			recipient = new GrantedAuthoritySid("ROLE_"+sid);
		}
		else
		{
			recipient = new PrincipalSid(sid);
		}
		Permission permission;
		if(perm.equalsIgnoreCase("admin"))
		{
			permission = BasePermission.ADMINISTRATION;
		}
		else  //read
		{
			permission = BasePermission.READ;
		}
		ACLService.deletePermission(abstractSecureObject, clazz, recipient, permission);
	
		
	}



	@Override
	public Map<String, Object> getSecuredObjectsWithPermissions(
			List<AbstractSecuredObject> secureObjList) {
		Map<AbstractSecuredObject, Boolean> hasDelete = new HashMap<AbstractSecuredObject, Boolean>(
				secureObjList.size());
		Map<AbstractSecuredObject, Boolean> hasAdmin = new HashMap<AbstractSecuredObject, Boolean>(
				secureObjList.size());
		Map<AbstractSecuredObject, Boolean> hasRead = new HashMap<AbstractSecuredObject, Boolean>(
				secureObjList.size());

		Authentication user = SecurityContextHolder.getContext()
				.getAuthentication();
		
		
		for (AbstractSecuredObject obj : secureObjList) {
			hasDelete.put(obj, Boolean.valueOf(permissionEvaluator
					.hasPermission(user, obj, HAS_DELETE)));
			hasAdmin.put(obj, Boolean.valueOf(permissionEvaluator
					.hasPermission(user, obj, HAS_ADMIN)));
			hasRead.put(obj, Boolean.valueOf(permissionEvaluator.hasPermission(
					user, obj, HAS_READ)));
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("objList", secureObjList);
		model.put("hasDeletePermission", hasDelete);
		model.put("hasAdminPermission", hasAdmin);
		model.put("hasReadPermission", hasRead);
		return model;

	}
}
