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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * NotiType generated by hbm2java
 */
@Audited
@AuditTable(catalog="jms_aud", value = "noti_type")
@Entity
@Table(name="noti_type"
    ,catalog="jms"
)
public class NotiType  implements java.io.Serializable {


     private Long idNotiType;
     private String notiType;
     private String description;
     private Set<Notification> notifications = new HashSet<Notification>(0);

    public NotiType() {
    }

    public NotiType(String notiType, String description, Set<Notification> notifications) {
       this.notiType = notiType;
       this.description = description;
       this.notifications = notifications;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ID_NOTI_TYPE", unique=true, nullable=false)
    public Long getIdNotiType() {
        return this.idNotiType;
    }
    
    public void setIdNotiType(Long idNotiType) {
        this.idNotiType = idNotiType;
    }
    
    @Column(name="NOTI_TYPE", length=64)
    public String getNotiType() {
        return this.notiType;
    }
    
    public void setNotiType(String notiType) {
        this.notiType = notiType;
    }
    
    @Column(name="DESCRIPTION", length=128)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="notiType")
    public Set<Notification> getNotifications() {
        return this.notifications;
    }
    
    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }




}


