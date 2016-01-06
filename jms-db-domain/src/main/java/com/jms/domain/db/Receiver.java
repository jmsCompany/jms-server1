package com.jms.domain.db;
// Generated 2016-1-6 12:39:14 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Receiver generated by hbm2java
 */
@Entity
@Table(name="receiver"
    ,catalog="jms3"
)
public class Receiver  implements java.io.Serializable {


     private Long idReceiver;
     private Notification notification;
     private Groups groups;
     private Long unsubscribe;

    public Receiver() {
    }

    public Receiver(Notification notification, Groups groups, Long unsubscribe) {
       this.notification = notification;
       this.groups = groups;
       this.unsubscribe = unsubscribe;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_receiver", unique=true, nullable=false)
    public Long getIdReceiver() {
        return this.idReceiver;
    }
    
    public void setIdReceiver(Long idReceiver) {
        this.idReceiver = idReceiver;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_noti")
    public Notification getNotification() {
        return this.notification;
    }
    
    public void setNotification(Notification notification) {
        this.notification = notification;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="receive_group")
    public Groups getGroups() {
        return this.groups;
    }
    
    public void setGroups(Groups groups) {
        this.groups = groups;
    }
    
    @Column(name="unsubscribe")
    public Long getUnsubscribe() {
        return this.unsubscribe;
    }
    
    public void setUnsubscribe(Long unsubscribe) {
        this.unsubscribe = unsubscribe;
    }




}


