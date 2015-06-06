package com.jms.domain.db;
// Generated 2015-6-6 20:38:20 by Hibernate Tools 3.2.2.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Groups generated by hbm2java
 */
@Entity
@Table(name="groups"
    ,catalog="jms"
)
public class Groups  implements java.io.Serializable {


     private Integer idGroup;
     private Users users;
     private String groupName;
     private String description;
     private Date creationTime;
     private Set<GroupMembers> groupMemberses = new HashSet<GroupMembers>(0);
     private Set<Roles> roleses = new HashSet<Roles>(0);

    public Groups() {
    }

	
    public Groups(String groupName) {
        this.groupName = groupName;
    }
    public Groups(Users users, String groupName, String description, Date creationTime, Set<GroupMembers> groupMemberses, Set<Roles> roleses) {
       this.users = users;
       this.groupName = groupName;
       this.description = description;
       this.creationTime = creationTime;
       this.groupMemberses = groupMemberses;
       this.roleses = roleses;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ID_GROUP", unique=true, nullable=false)
    public Integer getIdGroup() {
        return this.idGroup;
    }
    
    public void setIdGroup(Integer idGroup) {
        this.idGroup = idGroup;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CREATOR")
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    
    @Column(name="GROUP_NAME", nullable=false, length=64)
    public String getGroupName() {
        return this.groupName;
    }
    
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    @Column(name="DESCRIPTION", length=256)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="CREATION_TIME", length=10)
    public Date getCreationTime() {
        return this.creationTime;
    }
    
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="groups")
    public Set<GroupMembers> getGroupMemberses() {
        return this.groupMemberses;
    }
    
    public void setGroupMemberses(Set<GroupMembers> groupMemberses) {
        this.groupMemberses = groupMemberses;
    }
@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name="group_authorities", catalog="jms", joinColumns = { 
        @JoinColumn(name="ID_GROUP", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="ID_ROLE", nullable=false, updatable=false) })
    public Set<Roles> getRoleses() {
        return this.roleses;
    }
    
    public void setRoleses(Set<Roles> roleses) {
        this.roleses = roleses;
    }




}


