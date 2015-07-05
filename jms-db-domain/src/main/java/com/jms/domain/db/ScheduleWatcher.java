package com.jms.domain.db;
// Generated 2015-7-5 10:54:07 by Hibernate Tools 3.2.2.GA


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * ScheduleWatcher generated by hbm2java
 */
@Audited
@AuditTable(catalog="jms_aud", value = "schedule_watcher")
@Entity
@Table(name="schedule_watcher"
    ,catalog="jms"
)
public class ScheduleWatcher  implements java.io.Serializable {


     private ScheduleWatcherId id;
     private Users users;
     private Schedule schedule;
     private Long enabled;

    public ScheduleWatcher() {
    }

	
    public ScheduleWatcher(ScheduleWatcherId id, Users users, Schedule schedule) {
        this.id = id;
        this.users = users;
        this.schedule = schedule;
    }
    public ScheduleWatcher(ScheduleWatcherId id, Users users, Schedule schedule, Long enabled) {
       this.id = id;
       this.users = users;
       this.schedule = schedule;
       this.enabled = enabled;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idUser", column=@Column(name="ID_USER", nullable=false) ), 
        @AttributeOverride(name="idSchedule", column=@Column(name="ID_SCHEDULE", nullable=false) ) } )
    public ScheduleWatcherId getId() {
        return this.id;
    }
    
    public void setId(ScheduleWatcherId id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_USER", nullable=false, insertable=false, updatable=false)
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_SCHEDULE", nullable=false, insertable=false, updatable=false)
    public Schedule getSchedule() {
        return this.schedule;
    }
    
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
    
    @Column(name="ENABLED")
    public Long getEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(Long enabled) {
        this.enabled = enabled;
    }




}


