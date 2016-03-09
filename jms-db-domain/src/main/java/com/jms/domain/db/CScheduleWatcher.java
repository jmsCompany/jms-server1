package com.jms.domain.db;
// Generated 2016-3-9 10:11:30 by Hibernate Tools 3.2.2.GA


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * CScheduleWatcher generated by hbm2java
 */
@Entity
@Table(name="c_schedule_watcher"
    ,catalog="jms4"
)
public class CScheduleWatcher  implements java.io.Serializable {


     private CScheduleWatcherId id;
     private Users users;
     private CSchedule CSchedule;
     private Long enabled;

    public CScheduleWatcher() {
    }

	
    public CScheduleWatcher(CScheduleWatcherId id, Users users, CSchedule CSchedule) {
        this.id = id;
        this.users = users;
        this.CSchedule = CSchedule;
    }
    public CScheduleWatcher(CScheduleWatcherId id, Users users, CSchedule CSchedule, Long enabled) {
       this.id = id;
       this.users = users;
       this.CSchedule = CSchedule;
       this.enabled = enabled;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idUser", column=@Column(name="id_user", nullable=false) ), 
        @AttributeOverride(name="idSchedule", column=@Column(name="id_schedule", nullable=false) ) } )
    public CScheduleWatcherId getId() {
        return this.id;
    }
    
    public void setId(CScheduleWatcherId id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_user", nullable=false, insertable=false, updatable=false)
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_schedule", nullable=false, insertable=false, updatable=false)
    public CSchedule getCSchedule() {
        return this.CSchedule;
    }
    
    public void setCSchedule(CSchedule CSchedule) {
        this.CSchedule = CSchedule;
    }
    
    @Column(name="enabled")
    public Long getEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(Long enabled) {
        this.enabled = enabled;
    }




}


