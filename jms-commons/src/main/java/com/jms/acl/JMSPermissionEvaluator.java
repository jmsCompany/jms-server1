package com.jms.acl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.acls.domain.DefaultPermissionFactory;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityRetrievalStrategyImpl;
import org.springframework.security.acls.domain.PermissionFactory;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.ObjectIdentityGenerator;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.jms.web.security.JMSUserDetails;


public class JMSPermissionEvaluator implements PermissionEvaluator {



    private final AclService aclService;
    private ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy = new ObjectIdentityRetrievalStrategyImpl();
    private ObjectIdentityGenerator objectIdentityGenerator = new ObjectIdentityRetrievalStrategyImpl();
    private PermissionFactory permissionFactory = new DefaultPermissionFactory();
    public JMSPermissionEvaluator(AclService aclService) {
        this.aclService = aclService;
    }

    /**
     * Determines whether the user has the given permission(s) on the domain object using the ACL
     * configuration. If the domain object is null, returns false (this can always be overridden using a null
     * check in the expression itself).
     */
    public boolean hasPermission(Authentication authentication, Object domainObject, Object permission) {
        if (domainObject == null) {
            return false;
        }

        ObjectIdentity objectIdentity = objectIdentityRetrievalStrategy.getObjectIdentity(domainObject);

        return checkPermission(authentication, objectIdentity, permission);
    }

    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        ObjectIdentity objectIdentity = objectIdentityGenerator.createObjectIdentity(targetId, targetType);

        return checkPermission(authentication, objectIdentity, permission);
    }

    private boolean checkPermission(Authentication authentication, ObjectIdentity oid, Object permission) {
        System.out.println("authentication.getPrincipal()" + authentication.getPrincipal());
        Collection<? extends GrantedAuthority> authorities = ((JMSUserDetails)authentication.getPrincipal()).getAuthorities();
        List<Sid> sids = new ArrayList<Sid>();

        sids.add(new PrincipalSid(authentication));
        for (GrantedAuthority authority : authorities) {
            sids.add(new GrantedAuthoritySid(authority));
        }
        
        List<Permission> requiredPermission = resolvePermission(permission);

  
        try {
            // Lookup only ACLs for SIDs we're interested in
            Acl acl = aclService.readAclById(oid, sids);

            if (acl.isGranted(requiredPermission, sids, false)) {
      
                return true;
            }

      

        } catch (NotFoundException nfe) {
            
        }

        return false;

    }

    List<Permission> resolvePermission(Object permission) {
        if (permission instanceof Integer) {
            return Arrays.asList(permissionFactory.buildFromMask(((Integer)permission).intValue()));
        }

        if (permission instanceof Permission) {
            return Arrays.asList((Permission)permission);
        }

        if (permission instanceof Permission[]) {
            return Arrays.asList((Permission[])permission);
        }

        if (permission instanceof String) {
            String permString = (String)permission;
            Permission p;

            try {
                p = permissionFactory.buildFromName(permString);
            } catch(IllegalArgumentException notfound) {
                p = permissionFactory.buildFromName(permString.toUpperCase());
            }

            if (p != null) {
                return Arrays.asList(p);
            }

        }
        throw new IllegalArgumentException("Unsupported permission: " + permission);
    }

    public void setObjectIdentityRetrievalStrategy(ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy) {
        this.objectIdentityRetrievalStrategy = objectIdentityRetrievalStrategy;
    }

    public void setObjectIdentityGenerator(ObjectIdentityGenerator objectIdentityGenerator) {
        this.objectIdentityGenerator = objectIdentityGenerator;
    }


    public void setPermissionFactory(PermissionFactory permissionFactory) {
        this.permissionFactory = permissionFactory;
    }


}
