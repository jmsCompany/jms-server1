package com.jms.domain.db;
// Generated 2015-5-31 12:27:01 by Hibernate Tools 3.2.2.GA


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * RolePriv generated by hbm2java
 */
@Entity
@Table(name="role_priv"
    ,catalog="jms"
)
public class RolePriv  implements java.io.Serializable {


     private RolePrivId id;
     private Roles roles;
     private Modules modules;
     private Integer priv;

    public RolePriv() {
    }

	
    public RolePriv(RolePrivId id, Roles roles, Modules modules) {
        this.id = id;
        this.roles = roles;
        this.modules = modules;
    }
    public RolePriv(RolePrivId id, Roles roles, Modules modules, Integer priv) {
       this.id = id;
       this.roles = roles;
       this.modules = modules;
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
    public Modules getModules() {
        return this.modules;
    }
    
    public void setModules(Modules modules) {
        this.modules = modules;
    }
    
    @Column(name="PRIV")
    public Integer getPriv() {
        return this.priv;
    }
    
    public void setPriv(Integer priv) {
        this.priv = priv;
    }




}


