package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


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
 * GroupAuthorities generated by hbm2java
 */
@Entity
@Table(name="group_authorities"
)
public class GroupAuthorities  implements java.io.Serializable {


     private GroupAuthoritiesId id;
     private Roles roles;
     private Groups groups;
     private Long enabled;

    public GroupAuthorities() {
    }

	
    public GroupAuthorities(GroupAuthoritiesId id, Roles roles, Groups groups) {
        this.id = id;
        this.roles = roles;
        this.groups = groups;
    }
    public GroupAuthorities(GroupAuthoritiesId id, Roles roles, Groups groups, Long enabled) {
       this.id = id;
       this.roles = roles;
       this.groups = groups;
       this.enabled = enabled;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idGroup", column=@Column(name="id_group", nullable=false) ), 
        @AttributeOverride(name="idRole", column=@Column(name="id_role", nullable=false) ) } )
    public GroupAuthoritiesId getId() {
        return this.id;
    }
    
    public void setId(GroupAuthoritiesId id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_role", nullable=false, insertable=false, updatable=false)
    public Roles getRoles() {
        return this.roles;
    }
    
    public void setRoles(Roles roles) {
        this.roles = roles;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_group", nullable=false, insertable=false, updatable=false)
    public Groups getGroups() {
        return this.groups;
    }
    
    public void setGroups(Groups groups) {
        this.groups = groups;
    }
    
    @Column(name="enabled")
    public Long getEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(Long enabled) {
        this.enabled = enabled;
    }




}


