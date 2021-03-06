package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


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
 * CRepeats generated by hbm2java
 */
@Entity
@Table(name="event_receiver"
)

public class EventReceiver  implements java.io.Serializable {


     private Long idEventReceiver;
     private Long idEvent;
     private Long idCompany;
     private Long idGroup;
     private Long delay;
     private String email;
  

    public EventReceiver() {
    }


    @Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="id_event_receiver", unique=true, nullable=false)
	public Long getIdEventReceiver() {
		return idEventReceiver;
	}


	public void setIdEventReceiver(Long idEventReceiver) {
		this.idEventReceiver = idEventReceiver;
	}


    @Column(name="id_group")
	public Long getIdGroup() {
		return idGroup;
	}


	public void setIdGroup(Long idGroup) {
		this.idGroup = idGroup;
	}

	@Column(name="id_company")
	public Long getIdCompany() {
		return idCompany;
	}


	public void setIdCompany(Long idCompany) {
		this.idCompany = idCompany;
	}


	@Column(name="id_event")
	public Long getIdEvent() {
		return idEvent;
	}


	public void setIdEvent(Long idEvent) {
		this.idEvent = idEvent;
	}


	@Column(name="delay")
	public Long getDelay() {
		return delay;
	}


	public void setDelay(Long delay) {
		this.delay = delay;
	}


	@Column(name="email", length=256)
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

    
}


