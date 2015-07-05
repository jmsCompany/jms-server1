package com.jms.domain.db;
// Generated 2015-7-5 7:21:02 by Hibernate Tools 3.2.2.GA


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
 * GroupMembers generated by hbm2java
 */
@Audited
@AuditTable(catalog="jms_aud", value = "group_members_AUD")
@Entity
@Table(name="group_members"
    ,catalog="jms"
)
public class GroupMembers  implements java.io.Serializable {


     private GroupMembersId id;
     private Roles roles;
     private Users users;
     private Groups groups;

    public GroupMembers() {
    }

	
    public GroupMembers(GroupMembersId id, Users users, Groups groups) {
        this.id = id;
        this.users = users;
        this.groups = groups;
    }
    public GroupMembers(GroupMembersId id, Roles roles, Users users, Groups groups) {
       this.id = id;
       this.roles = roles;
       this.users = users;
       this.groups = groups;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idGroup", column=@Column(name="ID_GROUP", nullable=false) ), 
        @AttributeOverride(name="idUser", column=@Column(name="ID_USER", nullable=false) ) } )
    public GroupMembersId getId() {
        return this.id;
    }
    
    public void setId(GroupMembersId id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_ROLE")
    public Roles getRoles() {
        return this.roles;
    }
    
    public void setRoles(Roles roles) {
        this.roles = roles;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_USER", nullable=false, insertable=false, updatable=false)
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_GROUP", nullable=false, insertable=false, updatable=false)
    public Groups getGroups() {
        return this.groups;
    }
    
    public void setGroups(Groups groups) {
        this.groups = groups;
    }




}


