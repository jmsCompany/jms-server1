package com.jms.domain.db;
// Generated 2015-6-29 13:44:42 by Hibernate Tools 3.2.2.GA


import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * JmsEvent generated by hbm2java
 */
@Audited
@AuditTable(catalog="jms_aud", value = "jms_event_AUD")
@Entity
@Table(name="jms_event"
    ,catalog="jms"
)
public class JmsEvent  implements java.io.Serializable {


     private Long idEvent;
     private App app;
     private String name;
     private String description;
     private Set<Notification> notifications = new HashSet<Notification>(0);

    public JmsEvent() {
    }

	
    public JmsEvent(Long idEvent) {
        this.idEvent = idEvent;
    }
    public JmsEvent(Long idEvent, App app, String name, String description, Set<Notification> notifications) {
       this.idEvent = idEvent;
       this.app = app;
       this.name = name;
       this.description = description;
       this.notifications = notifications;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ID_EVENT", unique=true, nullable=false)
    public Long getIdEvent() {
        return this.idEvent;
    }
    
    public void setIdEvent(Long idEvent) {
        this.idEvent = idEvent;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_APP")
    public App getApp() {
        return this.app;
    }
    
    public void setApp(App app) {
        this.app = app;
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
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="jmsEvent")
    public Set<Notification> getNotifications() {
        return this.notifications;
    }
    
    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }




}


