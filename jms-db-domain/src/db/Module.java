package com.jms.domain.db;
// Generated 2015-7-22 10:30:44 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * Module generated by hbm2java
 */
@Audited
@AuditTable(catalog="jms_aud", value = "module")
@Entity
@Table(name="module"
    ,catalog="jms"
)
public class Module  implements java.io.Serializable {


     private Long idModule;
     private Apps apps;
     private String name;

    public Module() {
    }

    public Module(Apps apps, String name) {
       this.apps = apps;
       this.name = name;
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
    @JoinColumn(name="ID")
    public Apps getApps() {
        return this.apps;
    }
    
    public void setApps(Apps apps) {
        this.apps = apps;
    }
    
    @Column(name="NAME", length=64)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }




}


