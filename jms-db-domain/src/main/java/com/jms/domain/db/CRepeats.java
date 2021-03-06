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
@Table(name="c_repeats")
public class CRepeats  implements java.io.Serializable {


     private Long idRepeat;
     private SysDicD sysDicD;
     private Date endTime;
     private String frequency;
     private Set<CSchedule> CSchedules = new HashSet<CSchedule>(0);

    public CRepeats() {
    }

    public CRepeats(SysDicD sysDicD, Date endTime, String frequency, Set<CSchedule> CSchedules) {
       this.sysDicD = sysDicD;
       this.endTime = endTime;
       this.frequency = frequency;
       this.CSchedules = CSchedules;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_repeat", unique=true, nullable=false)
    public Long getIdRepeat() {
        return this.idRepeat;
    }
    
    public void setIdRepeat(Long idRepeat) {
        this.idRepeat = idRepeat;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="repeat_type")
    public SysDicD getSysDicD() {
        return this.sysDicD;
    }
    
    public void setSysDicD(SysDicD sysDicD) {
        this.sysDicD = sysDicD;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="end_time", length=10)
    public Date getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    @Column(name="frequency", length=20)
    public String getFrequency() {
        return this.frequency;
    }
    
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="CRepeats")
    public Set<CSchedule> getCSchedules() {
        return this.CSchedules;
    }
    
    public void setCSchedules(Set<CSchedule> CSchedules) {
        this.CSchedules = CSchedules;
    }




}


