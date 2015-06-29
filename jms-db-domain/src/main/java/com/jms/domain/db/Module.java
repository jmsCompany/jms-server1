package com.jms.domain.db;
// Generated 2015-6-29 13:44:42 by Hibernate Tools 3.2.2.GA


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

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * Module generated by hbm2java
 */
@Audited
@AuditTable(catalog="jms_aud", value = "module_AUD")
@Entity
@Table(name="module"
    ,catalog="jms"
)
public class Module  implements java.io.Serializable {


     private Long idModule;
     private Module module;
     private String name;
     private String description;
     private Set<RolePriv> rolePrivs = new HashSet<RolePriv>(0);
     private Set<Module> modules = new HashSet<Module>(0);

    public Module() {
    }

	
    public Module(Module module) {
        this.module = module;
    }
    public Module(Module module, String name, String description, Set<RolePriv> rolePrivs, Set<Module> modules) {
       this.module = module;
       this.name = name;
       this.description = description;
       this.rolePrivs = rolePrivs;
       this.modules = modules;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ID_MODULE", unique=true, nullable=false)
    public Long getIdModule() {
        return this.idModule;
    }
    
    public void setIdModule(Long idModule) {
        this.idModule = idModule;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_MODULE", unique=true, nullable=false, insertable=false, updatable=false)
    public Module getModule() {
        return this.module;
    }
    
    public void setModule(Module module) {
        this.module = module;
    }
    
    @Column(name="NAME", length=64)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="DESCRIPTION", length=128)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="module")
    public Set<RolePriv> getRolePrivs() {
        return this.rolePrivs;
    }
    
    public void setRolePrivs(Set<RolePriv> rolePrivs) {
        this.rolePrivs = rolePrivs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="module")
    public Set<Module> getModules() {
        return this.modules;
    }
    
    public void setModules(Set<Module> modules) {
        this.modules = modules;
    }




}


