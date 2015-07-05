package com.jms.domain.db;
// Generated 2015-7-5 10:54:07 by Hibernate Tools 3.2.2.GA


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
 * UserApp generated by hbm2java
 */
@Audited
@AuditTable(catalog="jms_aud", value = "user_app")
@Entity
@Table(name="user_app"
    ,catalog="jms"
)
public class UserApp  implements java.io.Serializable {


     private UserAppId id;
     private Roles roles;
     private Users users;
     private Apps apps;

    public UserApp() {
    }

	
    public UserApp(UserAppId id, Users users, Apps apps) {
        this.id = id;
        this.users = users;
        this.apps = apps;
    }
    public UserApp(UserAppId id, Roles roles, Users users, Apps apps) {
       this.id = id;
       this.roles = roles;
       this.users = users;
       this.apps = apps;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idUser", column=@Column(name="ID_USER", nullable=false) ), 
        @AttributeOverride(name="idApp", column=@Column(name="ID_APP", nullable=false) ) } )
    public UserAppId getId() {
        return this.id;
    }
    
    public void setId(UserAppId id) {
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
    @JoinColumn(name="ID_APP", nullable=false, insertable=false, updatable=false)
    public Apps getApps() {
        return this.apps;
    }
    
    public void setApps(Apps apps) {
        this.apps = apps;
    }




}


