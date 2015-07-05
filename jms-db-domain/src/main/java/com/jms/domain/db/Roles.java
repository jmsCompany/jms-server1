package com.jms.domain.db;
// Generated 2015-7-5 10:54:07 by Hibernate Tools 3.2.2.GA


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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Roles generated by hbm2java
 */
@Entity
@Table(name="roles"
    ,catalog="jms"
)
public class Roles  implements java.io.Serializable {


     private Long idRole;
     private Company company;
     private Sector sector;
     private Roles roles;
     private String role;
     private String description;
     private Long level;
     private Long enabled;
     private Set<GroupMembers> groupMemberses = new HashSet<GroupMembers>(0);
     private Set<SectorMember> sectorMembers = new HashSet<SectorMember>(0);
     private Set<Roles> roleses = new HashSet<Roles>(0);
     private Set<UserApp> userApps = new HashSet<UserApp>(0);
     private Set<GroupAuthorities> groupAuthoritieses = new HashSet<GroupAuthorities>(0);
     private Set<RolePriv> rolePrivs = new HashSet<RolePriv>(0);

    public Roles() {
    }

	
    public Roles(Company company, String role) {
        this.company = company;
        this.role = role;
    }
    public Roles(Company company, Sector sector, Roles roles, String role, String description, Long level, Long enabled, Set<GroupMembers> groupMemberses, Set<SectorMember> sectorMembers, Set<Roles> roleses, Set<UserApp> userApps, Set<GroupAuthorities> groupAuthoritieses, Set<RolePriv> rolePrivs) {
       this.company = company;
       this.sector = sector;
       this.roles = roles;
       this.role = role;
       this.description = description;
       this.level = level;
       this.enabled = enabled;
       this.groupMemberses = groupMemberses;
       this.sectorMembers = sectorMembers;
       this.roleses = roleses;
       this.userApps = userApps;
       this.groupAuthoritieses = groupAuthoritieses;
       this.rolePrivs = rolePrivs;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ID_ROLE", unique=true, nullable=false)
    public Long getIdRole() {
        return this.idRole;
    }
    
    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_COMPANY", nullable=false)
    public Company getCompany() {
        return this.company;
    }
    
    public void setCompany(Company company) {
        this.company = company;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_SECTOR")
    public Sector getSector() {
        return this.sector;
    }
    
    public void setSector(Sector sector) {
        this.sector = sector;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PARENT")
    public Roles getRoles() {
        return this.roles;
    }
    
    public void setRoles(Roles roles) {
        this.roles = roles;
    }
    
    @Column(name="ROLE", nullable=false, length=64)
    public String getRole() {
        return this.role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    @Column(name="DESCRIPTION", length=256)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="LEVEL")
    public Long getLevel() {
        return this.level;
    }
    
    public void setLevel(Long level) {
        this.level = level;
    }
    
    @Column(name="ENABLED")
    public Long getEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(Long enabled) {
        this.enabled = enabled;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="roles")
    public Set<GroupMembers> getGroupMemberses() {
        return this.groupMemberses;
    }
    
    public void setGroupMemberses(Set<GroupMembers> groupMemberses) {
        this.groupMemberses = groupMemberses;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="roles")
    public Set<SectorMember> getSectorMembers() {
        return this.sectorMembers;
    }
    
    public void setSectorMembers(Set<SectorMember> sectorMembers) {
        this.sectorMembers = sectorMembers;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="roles")
    public Set<Roles> getRoleses() {
        return this.roleses;
    }
    
    public void setRoleses(Set<Roles> roleses) {
        this.roleses = roleses;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="roles")
    public Set<UserApp> getUserApps() {
        return this.userApps;
    }
    
    public void setUserApps(Set<UserApp> userApps) {
        this.userApps = userApps;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="roles")
    public Set<GroupAuthorities> getGroupAuthoritieses() {
        return this.groupAuthoritieses;
    }
    
    public void setGroupAuthoritieses(Set<GroupAuthorities> groupAuthoritieses) {
        this.groupAuthoritieses = groupAuthoritieses;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="roles")
    public Set<RolePriv> getRolePrivs() {
        return this.rolePrivs;
    }
    
    public void setRolePrivs(Set<RolePriv> rolePrivs) {
        this.rolePrivs = rolePrivs;
    }




}


