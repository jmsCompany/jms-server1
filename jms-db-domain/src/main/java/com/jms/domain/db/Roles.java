package com.jms.domain.db;
// Generated 2015-6-25 9:56:24 by Hibernate Tools 3.2.2.GA


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
     private Set<Users> userses = new HashSet<Users>(0);
     private Set<GroupMembers> groupMemberses = new HashSet<GroupMembers>(0);
     private Set<SectorMember> sectorMembers = new HashSet<SectorMember>(0);
     private Set<Roles> roleses = new HashSet<Roles>(0);
     private Set<Groups> groupses = new HashSet<Groups>(0);
     private Set<RolePriv> rolePrivs = new HashSet<RolePriv>(0);

    public Roles() {
    }

	
    public Roles(Company company, String role) {
        this.company = company;
        this.role = role;
    }
    public Roles(Company company, Sector sector, Roles roles, String role, String description, Long level, Long enabled, Set<Users> userses, Set<GroupMembers> groupMemberses, Set<SectorMember> sectorMembers, Set<Roles> roleses, Set<Groups> groupses, Set<RolePriv> rolePrivs) {
       this.company = company;
       this.sector = sector;
       this.roles = roles;
       this.role = role;
       this.description = description;
       this.level = level;
       this.enabled = enabled;
       this.userses = userses;
       this.groupMemberses = groupMemberses;
       this.sectorMembers = sectorMembers;
       this.roleses = roleses;
       this.groupses = groupses;
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
@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name="authorities", catalog="jms", joinColumns = { 
        @JoinColumn(name="ID_ROLE", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="ID_USER", nullable=false, updatable=false) })
    public Set<Users> getUserses() {
        return this.userses;
    }
    
    public void setUserses(Set<Users> userses) {
        this.userses = userses;
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
@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="roleses")
    public Set<Groups> getGroupses() {
        return this.groupses;
    }
    
    public void setGroupses(Set<Groups> groupses) {
        this.groupses = groupses;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="roles")
    public Set<RolePriv> getRolePrivs() {
        return this.rolePrivs;
    }
    
    public void setRolePrivs(Set<RolePriv> rolePrivs) {
        this.rolePrivs = rolePrivs;
    }




}


