package com.jms.acl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.jms.domain.db.AbstractSecuredEntity;
import com.jms.web.security.JMSUserDetails;

@Repository
public class SecurityACLDAO {
	private static Logger logger = LoggerFactory.getLogger(SecurityACLDAO.class);

   @Autowired private MutableAclService mutableAclService;

	public SecurityACLDAO() {
	}

	@Transactional(readOnly = false)
	public void addPermission(AbstractSecuredEntity element,
			Class domainClass,Permission permission) {
        MutableAcl acl;
        ObjectIdentity oid = new ObjectIdentityImpl(domainClass, element.getId());
        System.out.println("class:" + domainClass.getName() +", id: " + element.getId() +", user: " + element.getUser().getIdUser());
        try {
            acl = (MutableAcl) mutableAclService.readAclById(oid);
        } catch (NotFoundException nfe) {
            acl = mutableAclService.createAcl(oid);
        }

        System.out.println("size: " + acl.getEntries().size());
        acl.insertAce(acl.getEntries().size(), permission, new PrincipalSid(""+element.getUser().getIdUser()), true);
     
        mutableAclService.updateAcl(acl);

	}
	@Transactional(readOnly = false)
    public void addPermission(AbstractSecuredEntity element, Sid recipient, Permission permission) {
        MutableAcl acl;
        //ObjectIdentity oid = new ObjectIdentityImpl(AbstractSecuredEntity.class, element.getId());
        ObjectIdentity oid = new ObjectIdentityImpl(element);

        try {
            acl = (MutableAcl) mutableAclService.readAclById(oid);
        } catch (NotFoundException nfe) {
            acl = mutableAclService.createAcl(oid);
        }
        acl.insertAce(acl.getEntries().size(), permission, recipient, true);
        mutableAclService.updateAcl(acl);

     //   logger.debug("Added permission " + permission + " for Sid " + recipient + " contact " + element);
    }

	@Transactional(readOnly = false)
    public boolean addAccessControlEntry(AbstractSecuredEntity element, Sid recipient, Permission permission) {
		Assert.notNull(element, "AbstractSecuredEntity required");
		Assert.notNull(recipient, "recipient required");
		Assert.notNull(permission, "permission required");

        MutableAcl acl;        
        //ObjectIdentity oid = new ObjectIdentityImpl(AbstractSecuredEntity.class, element.getId());
        ObjectIdentity oid = new ObjectIdentityImpl(element);

        try {
            acl = (MutableAcl) mutableAclService.readAclById(oid);
        } catch (NotFoundException nfe) {
            acl = mutableAclService.createAcl(oid);
        }

        /*
         * handle duplicate ACL entries
         * http://forum.springsource.org/showthread.php?73022-Should-AclImpl-allow-duplicate-permissions
         */
        if(doesACEExists(element, recipient, permission)) {
            logger.debug("ACE already exists, element:" + element.getId() + ", Sid:" + recipient + ", permission:" + permission);        	
        } else {
            acl.insertAce(acl.getEntries().size(), permission, recipient, true);
            mutableAclService.updateAcl(acl);
            logger.debug("Added permission " + permission + " for Sid " + recipient + " contact " + element);        	
        }
		return true;
    }

	@Transactional
	public void deletePermission(AbstractSecuredEntity element,
			Class domainClass, Sid recipient, Permission permission) {

        ObjectIdentity oid = new ObjectIdentityImpl(domainClass, element.getId());
        MutableAcl acl = (MutableAcl) mutableAclService.readAclById(oid);

        // Remove all permissions associated with this particular recipient (string equality to KISS)
        List<AccessControlEntry> entries = acl.getEntries();

        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getSid().equals(recipient) && entries.get(i).getPermission().equals(permission)) {
                acl.deleteAce(i);
                
            }
        }

        mutableAclService.updateAcl(acl);

		
	}

	
	@Transactional
	public void deletePermission(ObjectIdentity oid, Sid recipient, Permission permission) {

      
        MutableAcl acl = (MutableAcl) mutableAclService.readAclById(oid);

        // Remove all permissions associated with this particular recipient (string equality to KISS)
        List<AccessControlEntry> entries = acl.getEntries();

        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getSid().equals(recipient) && entries.get(i).getPermission().equals(permission)) {
                acl.deleteAce(i);
                
            }
        }

        mutableAclService.updateAcl(acl);

		
	}

	
	
	
	/*
	 * Check if ACE - Access Control Entry exists already
	 */
	public boolean doesACEExists(AbstractSecuredEntity element, Sid recipient, Permission permission) {
		boolean result = false;
        ObjectIdentity oid = new ObjectIdentityImpl(element);
        MutableAcl acl;
        try {
            acl = (MutableAcl) mutableAclService.readAclById(oid);
        } catch (NotFoundException nfe) {
            acl = mutableAclService.createAcl(oid);
        }
		
        List<AccessControlEntry> entries = acl.getEntries();
        for(AccessControlEntry ace : entries) {
            if (ace.getSid().equals(recipient) && ace.getPermission().equals(permission)) {
                //result = true;
                return true;
            }
        }
		return result;
	}
	
	@Transactional(readOnly = false)
    public void deletePermission(AbstractSecuredEntity element) {
        // Delete the ACL information as well
        //ObjectIdentity oid = new ObjectIdentityImpl(AbstractSecuredEntity.class, element.getId());
        ObjectIdentity oid = new ObjectIdentityImpl(element);
        mutableAclService.deleteAcl(oid, false);
        
       
    }

	@Transactional(readOnly = false)
    public boolean deleteAccessControlEntry(AbstractSecuredEntity element, Sid recipient, Permission permission) {
        ObjectIdentity oid = new ObjectIdentityImpl(element);
        MutableAcl acl;
        try {
            acl = (MutableAcl) mutableAclService.readAclById(oid);
        } catch (NotFoundException nfe) {
            acl = mutableAclService.createAcl(oid);
        }
		
        List<AccessControlEntry> entries = acl.getEntries();
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getSid().equals(recipient) && entries.get(i).getPermission().equals(permission)) {
                acl.deleteAce(i);
            }
        }

        mutableAclService.updateAcl(acl);

        if (logger.isDebugEnabled()) {
            logger.debug("Deleted Permission:" + permission + " for recipient: "+ recipient + ", for object: " + element);
        }
        
		return true;
    }

	public MutableAclService getMutableAclService() {
		return mutableAclService;
	}

	public void setMutableAclService(MutableAclService mutableAclService) {
		this.mutableAclService = mutableAclService;
	}	
}
