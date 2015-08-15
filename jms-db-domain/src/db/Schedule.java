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
 * Schedule generated by hbm2java
 */
@Audited
@AuditTable(catalog="jms_aud", value = "schedule")
@Entity
@Table(name="schedule"
    ,catalog="jms"
)
public class Schedule  implements java.io.Serializable {


     private Long idSchedule;
     private SysDicD sysDicD;
     private Repeats repeats;
     private Users users;
     private String name;
     private String description;
     private Date creationTime;
     private Date startTime;
     private Date endTime;
     private Long isWholeday;
     private Date remind;
     private Date lazySet;
     private Set<ScheduleWatcher> scheduleWatchers = new HashSet<ScheduleWatcher>(0);
     private Set<ScheduleDoc> scheduleDocs = new HashSet<ScheduleDoc>(0);

    public Schedule() {
    }

	
    public Schedule(Date creationTime) {
        this.creationTime = creationTime;
    }
    public Schedule(SysDicD sysDicD, Repeats repeats, Users users, String name, String description, Date creationTime, Date startTime, Date endTime, Long isWholeday, Date remind, Date lazySet, Set<ScheduleWatcher> scheduleWatchers, Set<ScheduleDoc> scheduleDocs) {
       this.sysDicD = sysDicD;
       this.repeats = repeats;
       this.users = users;
       this.name = name;
       this.description = description;
       this.creationTime = creationTime;
       this.startTime = startTime;
       this.endTime = endTime;
       this.isWholeday = isWholeday;
       this.remind = remind;
       this.lazySet = lazySet;
       this.scheduleWatchers = scheduleWatchers;
       this.scheduleDocs = scheduleDocs;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ID_SCHEDULE", unique=true, nullable=false)
    public Long getIdSchedule() {
        return this.idSchedule;
    }
    
    public void setIdSchedule(Long idSchedule) {
        this.idSchedule = idSchedule;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SCHEDULE_TYPE")
    public SysDicD getSysDicD() {
        return this.sysDicD;
    }
    
    public void setSysDicD(SysDicD sysDicD) {
        this.sysDicD = sysDicD;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_REPEAT")
    public Repeats getRepeats() {
        return this.repeats;
    }
    
    public void setRepeats(Repeats repeats) {
        this.repeats = repeats;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CREATOR")
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    
    @Column(name="NAME", length=256)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="DESCRIPTION", length=20)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATION_TIME", nullable=false, length=19)
    public Date getCreationTime() {
        return this.creationTime;
    }
    
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="START_TIME", length=19)
    public Date getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="END_TIME", length=19)
    public Date getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    @Column(name="IS_WHOLEDAY")
    public Long getIsWholeday() {
        return this.isWholeday;
    }
    
    public void setIsWholeday(Long isWholeday) {
        this.isWholeday = isWholeday;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="REMIND", length=19)
    public Date getRemind() {
        return this.remind;
    }
    
    public void setRemind(Date remind) {
        this.remind = remind;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LAZY_SET", length=19)
    public Date getLazySet() {
        return this.lazySet;
    }
    
    public void setLazySet(Date lazySet) {
        this.lazySet = lazySet;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="schedule")
    public Set<ScheduleWatcher> getScheduleWatchers() {
        return this.scheduleWatchers;
    }
    
    public void setScheduleWatchers(Set<ScheduleWatcher> scheduleWatchers) {
        this.scheduleWatchers = scheduleWatchers;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="schedule")
    public Set<ScheduleDoc> getScheduleDocs() {
        return this.scheduleDocs;
    }
    
    public void setScheduleDocs(Set<ScheduleDoc> scheduleDocs) {
        this.scheduleDocs = scheduleDocs;
    }




}


