package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PActStops generated by hbm2java
 */
@Entity
@Table(name="p_act_stops"
)
public class PActStops  implements java.io.Serializable {


     private Long idActStops;
     private PStopsPlan PStopsPlan;
     private Date actSt;
     private Date actFt;

    public PActStops() {
    }

    public PActStops(PStopsPlan PStopsPlan, Date actSt, Date actFt) {
       this.PStopsPlan = PStopsPlan;
       this.actSt = actSt;
       this.actFt = actFt;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_act_stops", unique=true, nullable=false)
    public Long getIdActStops() {
        return this.idActStops;
    }
    
    public void setIdActStops(Long idActStops) {
        this.idActStops = idActStops;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_stops_plan")
    public PStopsPlan getPStopsPlan() {
        return this.PStopsPlan;
    }
    
    public void setPStopsPlan(PStopsPlan PStopsPlan) {
        this.PStopsPlan = PStopsPlan;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="act_st", length=19)
    public Date getActSt() {
        return this.actSt;
    }
    
    public void setActSt(Date actSt) {
        this.actSt = actSt;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="act_ft", length=19)
    public Date getActFt() {
        return this.actFt;
    }
    
    public void setActFt(Date actFt) {
        this.actFt = actFt;
    }




}


