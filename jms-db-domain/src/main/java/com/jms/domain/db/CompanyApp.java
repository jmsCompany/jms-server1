package com.jms.domain.db;
// Generated 2015-6-29 13:44:42 by Hibernate Tools 3.2.2.GA


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
@AuditTable(catalog="jms_aud", value = "Company_APP_AUD") 
@Entity
@Table(name="company_app"
    ,catalog="jms"
)
public class CompanyApp  implements java.io.Serializable {


     private CompanyAppId id;
     private Company company;
     private App app;
     private Long enabled;

    public CompanyApp() {
    }

	
    public CompanyApp(CompanyAppId id, Company company, App app) {
        this.id = id;
        this.company = company;
        this.app = app;
    }
    public CompanyApp(CompanyAppId id, Company company, App app, Long enabled) {
       this.id = id;
       this.company = company;
       this.app = app;
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
    public App getApp() {
        return this.app;
    }
    
    public void setApp(App app) {
        this.app = app;
    }
    
    @Column(name="ENABLED")
    public Long getEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(Long enabled) {
        this.enabled = enabled;
    }




}


