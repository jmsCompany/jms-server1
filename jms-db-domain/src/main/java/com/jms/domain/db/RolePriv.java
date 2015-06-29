package com.jms.domain.db;
// Generated 2015-6-25 9:56:24 by Hibernate Tools 3.2.2.GA


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * RolePriv generated by hbm2java
 */
@Audited
@AuditTable(catalog="jms_aud", value = "role_priv_AUD") 
@Entity
@Table(name="role_priv"
    ,catalog="jms"
)
public class RolePriv  implements java.io.Serializable {


     private RolePrivId id;
     private Roles roles;
     private Module module;
     private Long priv;

    public RolePriv() {
    }

	
    public RolePriv(RolePrivId id, Roles roles, Module module) {
        this.id = id;
        this.roles = roles;
        this.module = module;
    }
    public RolePriv(RolePrivId id, Roles roles, Module module, Long priv) {
       this.id = id;
       this.roles = roles;
       this.module = module;
       this.priv = priv;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idModule", column=@Column(name="ID_MODULE", nullable=false) ), 
        @AttributeOverride(name="idRole", column=@Column(name="ID_ROLE", nullable=false) ) } )
    public RolePrivId getId() {
        return this.id;
    }
    
    public void setId(RolePrivId id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_ROLE", nullable=false, insertable=false, updatable=false)
    public Roles getRoles() {
        return this.roles;
    }
    
    public void setRoles(Roles roles) {
        this.roles = roles;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_MODULE", nullable=false, insertable=false, updatable=false)
    public Module getModule() {
        return this.module;
    }
    
    public void setModule(Module module) {
        this.module = module;
    }
    
    @Column(name="PRIV")
    public Long getPriv() {
        return this.priv;
    }
    
    public void setPriv(Long priv) {
        this.priv = priv;
    }




}


