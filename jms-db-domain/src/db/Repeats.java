package com.jms.domain.db;
// Generated 2015-7-22 10:30:44 by Hibernate Tools 3.2.2.GA


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

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * Repeats generated by hbm2java
 */
@Audited
@AuditTable(catalog="jms_aud", value = "repeats")
@Entity
@Table(name="repeats"
    ,catalog="jms"
)
public class Repeats  implements java.io.Serializable {


     private Long idRepeat;
     private SysDicD sysDicD;
     private Date endTime;
     private String frequency;
     private Set<Schedule> schedules = new HashSet<Schedule>(0);

    public Repeats() {
    }

    public Repeats(SysDicD sysDicD, Date endTime, String frequency, Set<Schedule> schedules) {
       this.sysDicD = sysDicD;
       this.endTime = endTime;
       this.frequency = frequency;
       this.schedules = schedules;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ID_REPEAT", unique=true, nullable=false)
    public Long getIdRepeat() {
        return this.idRepeat;
    }
    
    public void setIdRepeat(Long idRepeat) {
        this.idRepeat = idRepeat;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REPEAT_TYPE")
    public SysDicD getSysDicD() {
        return this.sysDicD;
    }
    
    public void setSysDicD(SysDicD sysDicD) {
        this.sysDicD = sysDicD;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="END_TIME", length=10)
    public Date getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    @Column(name="FREQUENCY", length=20)
    public String getFrequency() {
        return this.frequency;
    }
    
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="repeats")
    public Set<Schedule> getSchedules() {
        return this.schedules;
    }
    
    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }




}


