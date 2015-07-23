package com.jms.domain.db;
// Generated 2015-7-22 10:30:44 by Hibernate Tools 3.2.2.GA


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
 * CompanyApp generated by hbm2java
 */
@Audited
@AuditTable(catalog="jms_aud", value = "company_app")
@Entity
@Table(name="company_app"
    ,catalog="jms"
)
public class CompanyApp  implements java.io.Serializable {


     private CompanyAppId id;
     private Company company;
     private Apps apps;
     private Long enabled;

    public CompanyApp() {
    }

	
    public CompanyApp(CompanyAppId id, Company company, Apps apps) {
        this.id = id;
        this.company = company;
        this.apps = apps;
    }
    public CompanyApp(CompanyAppId id, Company company, Apps apps, Long enabled) {
       this.id = id;
       this.company = company;
       this.apps = apps;
       this.enabled = enabled;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idCompany", column=@Column(name="ID_COMPANY", nullable=false) ), 
        @AttributeOverride(name="idApp", column=@Column(name="ID_APP", nullable=false) ) } )
    public CompanyAppId getId() {
        return this.id;
    }
    
    public void setId(CompanyAppId id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_COMPANY", nullable=false, insertable=false, updatable=false)
    public Company getCompany() {
        return this.company;
    }
    
    public void setCompany(Company company) {
        this.company = company;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_APP", nullable=false, insertable=false, updatable=false)
    public Apps getApps() {
        return this.apps;
    }
    
    public void setApps(Apps apps) {
        this.apps = apps;
    }
    
    @Column(name="ENABLED")
    public Long getEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(Long enabled) {
        this.enabled = enabled;
    }




}


