package com.jms.domain.db;
// Generated 2015-6-6 20:38:20 by Hibernate Tools 3.2.2.GA


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
 * Modules generated by hbm2java
 */
@Entity
@Table(name="modules"
    ,catalog="jms"
)
public class Modules  implements java.io.Serializable {


     private Integer idModule;
     private Modules modules;
     private String name;
     private String description;
     private Set<RolePriv> rolePrivs = new HashSet<RolePriv>(0);
     private Set<Modules> moduleses = new HashSet<Modules>(0);

    public Modules() {
    }

    public Modules(Modules modules, String name, String description, Set<RolePriv> rolePrivs, Set<Modules> moduleses) {
       this.modules = modules;
       this.name = name;
       this.description = description;
       this.rolePrivs = rolePrivs;
       this.moduleses = moduleses;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ID_MODULE", unique=true, nullable=false)
    public Integer getIdModule() {
        return this.idModule;
    }
    
    public void setIdModule(Integer idModule) {
        this.idModule = idModule;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PARENT")
    public Modules getModules() {
        return this.modules;
    }
    
    public void setModules(Modules modules) {
        this.modules = modules;
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
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="modules")
    public Set<RolePriv> getRolePrivs() {
        return this.rolePrivs;
    }
    
    public void setRolePrivs(Set<RolePriv> rolePrivs) {
        this.rolePrivs = rolePrivs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="modules")
    public Set<Modules> getModuleses() {
        return this.moduleses;
    }
    
    public void setModuleses(Set<Modules> moduleses) {
        this.moduleses = moduleses;
    }




}


