package com.jms.acl;

import java.util.List;
import java.util.Map;

import org.springframework.security.acls.model.AccessControlEntry;

import com.jms.domain.db.AbstractSecuredObject;



public interface SecuredObjService {
	public <T extends AbstractSecuredObject> T find(Class<T> clazz, long id);
	public Map<String,String> findSidPermissionMap(Class clazz, long id, String sidType);
	public List<AccessControlEntry> getAccessEntries(AbstractSecuredObject abstractSecureObject, Class domainClass);
	public SidPerm getPerm(String sid,String perm,String type);
	public void changePerm(Class clazz, long id, SidPerm sidPerm,String perm);
	public void savePerm(Class clazz, long id,String type, String sid,String perm);
	public void removePerm(Class clazz, long id,String type, String sid,String perm);
	//public List<String> getSids(String type);
	public Map<String, Object> getSecuredObjectsWithPermissions(
			List<AbstractSecuredObject> secureObjList);

}
