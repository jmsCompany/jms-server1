package com.jms.domain.db;
// Generated 2015-7-22 10:30:44 by Hibernate Tools 3.2.2.GA


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
 * Receipt generated by hbm2java
 */
@Audited
@AuditTable(catalog="jms_aud", value = "receipt")
@Entity
@Table(name="receipt"
    ,catalog="jms"
)
public class Receipt  implements java.io.Serializable {


     private Long idReceipt;
     private Company company;
     private String title;
     private String contactPerson;
     private String contactTel;
     private String address;
     private Long isPrimary;
     private String bank;
     private String bankNumber;
     private Set<BuyRecord> buyRecords = new HashSet<BuyRecord>(0);

    public Receipt() {
    }

	
    public Receipt(Company company) {
        this.company = company;
    }
    public Receipt(Company company, String title, String contactPerson, String contactTel, String address, Long isPrimary, String bank, String bankNumber, Set<BuyRecord> buyRecords) {
       this.company = company;
       this.title = title;
       this.contactPerson = contactPerson;
       this.contactTel = contactTel;
       this.address = address;
       this.isPrimary = isPrimary;
       this.bank = bank;
       this.bankNumber = bankNumber;
       this.buyRecords = buyRecords;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ID_RECEIPT", unique=true, nullable=false)
    public Long getIdReceipt() {
        return this.idReceipt;
    }
    
    public void setIdReceipt(Long idReceipt) {
        this.idReceipt = idReceipt;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_COMPANY", nullable=false)
    public Company getCompany() {
        return this.company;
    }
    
    public void setCompany(Company company) {
        this.company = company;
    }
    
    @Column(name="TITLE", length=256)
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    @Column(name="Contact_PERSON", length=20)
    public String getContactPerson() {
        return this.contactPerson;
    }
    
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
    
    @Column(name="CONTACT_TEL", length=20)
    public String getContactTel() {
        return this.contactTel;
    }
    
    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }
    
    @Column(name="ADDRESS", length=512)
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    @Column(name="IS_PRIMARY")
    public Long getIsPrimary() {
        return this.isPrimary;
    }
    
    public void setIsPrimary(Long isPrimary) {
        this.isPrimary = isPrimary;
    }
    
    @Column(name="BANK", length=128)
    public String getBank() {
        return this.bank;
    }
    
    public void setBank(String bank) {
        this.bank = bank;
    }
    
    @Column(name="BANK_NUMBER", length=64)
    public String getBankNumber() {
        return this.bankNumber;
    }
    
    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="receipt")
    public Set<BuyRecord> getBuyRecords() {
        return this.buyRecords;
    }
    
    public void setBuyRecords(Set<BuyRecord> buyRecords) {
        this.buyRecords = buyRecords;
    }




}


