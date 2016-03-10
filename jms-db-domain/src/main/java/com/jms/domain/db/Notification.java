package com.jms.domain.db;
// Generated 2016-3-9 10:11:30 by Hibernate Tools 3.2.2.GA


import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Notification generated by hbm2java
 */
@Entity
@Table(name="notification"
    ,catalog="jms4"
)
public class Notification  implements java.io.Serializable {


     private Long idNoti;
     private JmsEvent jmsEvent;
     private Company company;
     private Users users;
     private NotiMethod notiMethod;
     private Long idSource;
     private Date creationTime;
     private Set<Receiver> receivers = new HashSet<Receiver>(0);

    public Notification() {
    }

    public Notification(JmsEvent jmsEvent, Company company, Users users, NotiMethod notiMethod, Long idSource, Date creationTime, Set<Receiver> receivers) {
       this.jmsEvent = jmsEvent;
       this.company = company;
       this.users = users;
       this.notiMethod = notiMethod;
       this.idSource = idSource;
       this.creationTime = creationTime;
       this.receivers = receivers;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="id_noti", unique=true, nullable=false)
    public Long getIdNoti() {
        return this.idNoti;
    }
    
    public void setIdNoti(Long idNoti) {
        this.idNoti = idNoti;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_event")
    public JmsEvent getJmsEvent() {
        return this.jmsEvent;
    }
    
    public void setJmsEvent(JmsEvent jmsEvent) {
        this.jmsEvent = jmsEvent;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_company")
    public Company getCompany() {
        return this.company;
    }
    
    public void setCompany(Company company) {
        this.company = company;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="creator")
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_noti_method")
    public NotiMethod getNotiMethod() {
        return this.notiMethod;
    }
    
    public void setNotiMethod(NotiMethod notiMethod) {
        this.notiMethod = notiMethod;
    }
    
    @Column(name="id_source")
    public Long getIdSource() {
        return this.idSource;
    }
    
    public void setIdSource(Long idSource) {
        this.idSource = idSource;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="creation_time", length=19)
    public Date getCreationTime() {
        return this.creationTime;
    }
    
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="notification")
    public Set<Receiver> getReceivers() {
        return this.receivers;
    }
    
    public void setReceivers(Set<Receiver> receivers) {
        this.receivers = receivers;
    }




}


