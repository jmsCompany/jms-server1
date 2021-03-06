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
 * JmsEvent generated by hbm2java
 */
@Audited
@AuditTable(catalog="jms_aud", value = "jms_event")
@Entity
@Table(name="jms_event"
    ,catalog="jms"
)
public class JmsEvent  implements java.io.Serializable {


     private Long idEvent;
     private JmsEvent jmsEvent;
     private Apps apps;
     private String name;
     private String description;
     private String template;
     private Set<Notification> notifications = new HashSet<Notification>(0);
     private Set<JmsEvent> jmsEvents = new HashSet<JmsEvent>(0);

    public JmsEvent() {
    }

    public JmsEvent(JmsEvent jmsEvent, Apps apps, String name, String description, String template, Set<Notification> notifications, Set<JmsEvent> jmsEvents) {
       this.jmsEvent = jmsEvent;
       this.apps = apps;
       this.name = name;
       this.description = description;
       this.template = template;
       this.notifications = notifications;
       this.jmsEvents = jmsEvents;
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
    @JoinColumn(name="PARENT")
    public JmsEvent getJmsEvent() {
        return this.jmsEvent;
    }
    
    public void setJmsEvent(JmsEvent jmsEvent) {
        this.jmsEvent = jmsEvent;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_APP")
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
    
    @Column(name="DESCRIPTION", length=128)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="TEMPLATE", length=64)
    public String getTemplate() {
        return this.template;
    }
    
    public void setTemplate(String template) {
        this.template = template;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="jmsEvent")
    public Set<Notification> getNotifications() {
        return this.notifications;
    }
    
    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="jmsEvent")
    public Set<JmsEvent> getJmsEvents() {
        return this.jmsEvents;
    }
    
    public void setJmsEvents(Set<JmsEvent> jmsEvents) {
        this.jmsEvents = jmsEvents;
    }




}


