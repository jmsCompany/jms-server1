package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * PUTime generated by hbm2java
 */
@Entity
@Table(name="p_u_time"
)
public class PUTime  implements java.io.Serializable {


     private Long idUTime;
     private String UTime;
     private Set<PCheckTime> PCheckTimes = new HashSet<PCheckTime>(0);

    public PUTime() {
    }

	
    public PUTime(Long idUTime) {
        this.idUTime = idUTime;
    }
    public PUTime(Long idUTime, String UTime, Set<PCheckTime> PCheckTimes) {
       this.idUTime = idUTime;
       this.UTime = UTime;
       this.PCheckTimes = PCheckTimes;
    }
   
     @Id 
    
    @Column(name="id_u_time", unique=true, nullable=false)
    public Long getIdUTime() {
        return this.idUTime;
    }
    
    public void setIdUTime(Long idUTime) {
        this.idUTime = idUTime;
    }
    
    @Column(name="u_time", length=20)
    public String getUTime() {
        return this.UTime;
    }
    
    public void setUTime(String UTime) {
        this.UTime = UTime;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="PUTime")
    public Set<PCheckTime> getPCheckTimes() {
        return this.PCheckTimes;
    }
    
    public void setPCheckTimes(Set<PCheckTime> PCheckTimes) {
        this.PCheckTimes = PCheckTimes;
    }




}


