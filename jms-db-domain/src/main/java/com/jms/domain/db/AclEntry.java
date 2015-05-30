package com.jms.domain.db;
// Generated 2015-5-30 22:39:13 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * AclEntry generated by hbm2java
 */
@Entity
@Table(name="acl_entry"
    ,catalog="jms"
    , uniqueConstraints = @UniqueConstraint(columnNames={"acl_object_identity", "ace_order"}) 
)
public class AclEntry  implements java.io.Serializable {


     private Long id;
     private AclSid aclSid;
     private AclObjectIdentity aclObjectIdentity;
     private int aceOrder;
     private int mask;
     private boolean granting;
     private boolean auditSuccess;
     private boolean auditFailure;

    public AclEntry() {
    }

    public AclEntry(AclSid aclSid, AclObjectIdentity aclObjectIdentity, int aceOrder, int mask, boolean granting, boolean auditSuccess, boolean auditFailure) {
       this.aclSid = aclSid;
       this.aclObjectIdentity = aclObjectIdentity;
       this.aceOrder = aceOrder;
       this.mask = mask;
       this.granting = granting;
       this.auditSuccess = auditSuccess;
       this.auditFailure = auditFailure;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="sid", nullable=false)
    public AclSid getAclSid() {
        return this.aclSid;
    }
    
    public void setAclSid(AclSid aclSid) {
        this.aclSid = aclSid;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="acl_object_identity", nullable=false)
    public AclObjectIdentity getAclObjectIdentity() {
        return this.aclObjectIdentity;
    }
    
    public void setAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
        this.aclObjectIdentity = aclObjectIdentity;
    }
    
    @Column(name="ace_order", nullable=false)
    public int getAceOrder() {
        return this.aceOrder;
    }
    
    public void setAceOrder(int aceOrder) {
        this.aceOrder = aceOrder;
    }
    
    @Column(name="mask", nullable=false)
    public int getMask() {
        return this.mask;
    }
    
    public void setMask(int mask) {
        this.mask = mask;
    }
    
    @Column(name="granting", nullable=false)
    public boolean isGranting() {
        return this.granting;
    }
    
    public void setGranting(boolean granting) {
        this.granting = granting;
    }
    
    @Column(name="audit_success", nullable=false)
    public boolean isAuditSuccess() {
        return this.auditSuccess;
    }
    
    public void setAuditSuccess(boolean auditSuccess) {
        this.auditSuccess = auditSuccess;
    }
    
    @Column(name="audit_failure", nullable=false)
    public boolean isAuditFailure() {
        return this.auditFailure;
    }
    
    public void setAuditFailure(boolean auditFailure) {
        this.auditFailure = auditFailure;
    }




}


