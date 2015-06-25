package com.jms.acl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.AbstractSecuredObject;
import com.jms.repositories.user.UsersRepository;


@Service("ACLService")
@Transactional(readOnly=true)
public class ACLServiceImpl implements ACLService {

    private static Log log = LogFactory.getLog(ACLServiceImpl.class);

   
    private JdbcMutableAclService aclService;
    
    @Autowired
    private UsersRepository usersRepository;
    
	@Override @Transactional
	public void addPermission(AbstractSecuredObject abstractSecureObject,
			Class domainClass, Sid recipient, Permission permission) {
		   
        MutableAcl acl;
        ObjectIdentity oid = new ObjectIdentityImpl(domainClass, abstractSecureObject.getId());
     
        try {
            acl = (MutableAcl) aclService.readAclById(oid);
        } catch (NotFoundException nfe) {
            acl = aclService.createAcl(oid);
        }

        acl.insertAce(acl.getEntries().size(), permission, recipient, true);
        aclService.updateAcl(acl);

       // logger.debug("Added permission " + permission + " for Sid " + recipient + " contact " + contact);
    
	}
 
	@Override @Transactional
	public void deletePermission(AbstractSecuredObject abstractSecureObject,
			Class domainClass, Sid recipient, Permission permission) {

        ObjectIdentity oid = new ObjectIdentityImpl(domainClass, abstractSecureObject.getId());
        MutableAcl acl = (MutableAcl) aclService.readAclById(oid);

        // Remove all permissions associated with this particular recipient (string equality to KISS)
        List<AccessControlEntry> entries = acl.getEntries();

        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getSid().equals(recipient) && entries.get(i).getPermission().equals(permission)) {
                acl.deleteAce(i);
            }
        }

        aclService.updateAcl(acl);

		
	}



	@Override @Transactional
	public void deletePermission(AbstractSecuredObject abstractSecureObject,
			Class domainClass) {
        ObjectIdentity oid = new ObjectIdentityImpl(domainClass, abstractSecureObject.getId());
        aclService.deleteAcl(oid, false);
       
     
	}



	@Override @Transactional
	public void addPermission(AbstractSecuredObject abstractSecureObject,
			Class domainClass,Permission permission) {
        MutableAcl acl;
        ObjectIdentity oid = new ObjectIdentityImpl(domainClass, abstractSecureObject.getId());
        

        try {
            acl = (MutableAcl) aclService.readAclById(oid);
        } catch (NotFoundException nfe) {
            acl = aclService.createAcl(oid);
        }

        acl.insertAce(acl.getEntries().size(), permission, new PrincipalSid(""+abstractSecureObject.getId()), true);
        aclService.updateAcl(acl);

      
    
	
		
	}



   
}
