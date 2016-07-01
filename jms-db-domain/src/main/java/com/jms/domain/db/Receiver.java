package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Receiver generated by hbm2java
 */
@Entity
@Table(name="receiver"
    ,catalog="jms5"
)
public class Receiver  implements java.io.Serializable {


     private Long idReceiver;
     private Notification notification;
     private Groups groups;
     private Long unsubscribe;
     
     private Date receiveTime;
     private Long checked;
     private String view;
     private String msg;

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="receive_time", length=19)
	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

    @Column(name="checked")
	public Long getChecked() {
		return checked;
	}

	public void setChecked(Long checked) {
		this.checked = checked;
	}

	   @Column(name="view", length=64)
	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	   @Column(name="msg", length=512)
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}




}


