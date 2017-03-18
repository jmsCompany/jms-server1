package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


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

/**
 * NotiMethod generated by hbm2java
 */
@Entity
@Table(name="noti_method"
)
public class NotiMethod  implements java.io.Serializable {


     private Long idNotiMethod;
     private String method;
     private Set<AApprovalCc> AApprovalCcs = new HashSet<AApprovalCc>(0);
     private Set<Notification> notifications = new HashSet<Notification>(0);

    public NotiMethod() {
    }

    public NotiMethod(String method, Set<AApprovalCc> AApprovalCcs, Set<Notification> notifications) {
       this.method = method;
       this.AApprovalCcs = AApprovalCcs;
       this.notifications = notifications;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_noti_method", unique=true, nullable=false)
    public Long getIdNotiMethod() {
        return this.idNotiMethod;
    }
    
    public void setIdNotiMethod(Long idNotiMethod) {
        this.idNotiMethod = idNotiMethod;
    }
    
    @Column(name="method", length=20)
    public String getMethod() {
        return this.method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="notiMethod")
    public Set<AApprovalCc> getAApprovalCcs() {
        return this.AApprovalCcs;
    }
    
    public void setAApprovalCcs(Set<AApprovalCc> AApprovalCcs) {
        this.AApprovalCcs = AApprovalCcs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="notiMethod")
    public Set<Notification> getNotifications() {
        return this.notifications;
    }
    
    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }




}


