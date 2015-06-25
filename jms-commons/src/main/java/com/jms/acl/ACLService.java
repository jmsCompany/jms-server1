package com.jms.acl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import com.jms.domain.db.AbstractSecuredObject;

public interface ACLService {

    public void addPermission(AbstractSecuredObject abstractSecureObject,Class domainClass,Permission permission);
	
	//@PreAuthorize("hasPermission(#abstractSecureObject, admin)")
    public void addPermission(AbstractSecuredObject abstractSecureObject,Class domainClass, Sid recipient, Permission permission);

    @PreAuthorize("hasPermission(#abstractSecureObject, admin)")
    public void deletePermission(AbstractSecuredObject abstractSecureObject, Class domainClass,Sid recipient, Permission permission);

    @PreAuthorize("hasPermission(#abstractSecureObject, admin)")
    public void deletePermission(AbstractSecuredObject abstractSecureObject, Class domainClass);
    

}
