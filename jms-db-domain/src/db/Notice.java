package com.jms.domain.db;
// Generated 2015-7-22 10:30:44 by Hibernate Tools 3.2.2.GA


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * Notice generated by hbm2java
 */
@Audited
@AuditTable(catalog="jms_aud", value = "notice")
@Entity
@Table(name="notice"
    ,catalog="jms"
)
public class Notice  implements java.io.Serializable {


     private Long idNotice;
     private Users users;
     private Sector sector;
     private String title;
     private String notice;
     private String remark;
     private Date publishTime;

    public Notice() {
    }

	
    public Notice(Long idNotice) {
        this.idNotice = idNotice;
    }
    public Notice(Long idNotice, Users users, Sector sector, String title, String notice, String remark, Date publishTime) {
       this.idNotice = idNotice;
       this.users = users;
       this.sector = sector;
       this.title = title;
       this.notice = notice;
       this.remark = remark;
       this.publishTime = publishTime;
    }
   
     @Id 
    
    @Column(name="ID_NOTICE", unique=true, nullable=false)
    public Long getIdNotice() {
        return this.idNotice;
    }
    
    public void setIdNotice(Long idNotice) {
        this.idNotice = idNotice;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_USER")
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_SECTOR")
    public Sector getSector() {
        return this.sector;
    }
    
    public void setSector(Sector sector) {
        this.sector = sector;
    }
    
    @Column(name="TITLE", length=128)
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    @Column(name="NOTICE", length=4096)
    public String getNotice() {
        return this.notice;
    }
    
    public void setNotice(String notice) {
        this.notice = notice;
    }
    
    @Column(name="REMARK", length=1024)
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="PUBLISH_TIME", length=10)
    public Date getPublishTime() {
        return this.publishTime;
    }
    
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }




}

