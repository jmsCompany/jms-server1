package com.jms.domain.db;
// Generated 2015-6-7 13:49:29 by Hibernate Tools 3.2.2.GA


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
 * Task generated by hbm2java
 */
@Entity
@Table(name="task"
    ,catalog="jms"
)
public class Task  implements java.io.Serializable {


     private Integer idTask;
     private Users usersByAssignee;
     private Users usersByCreator;
     private Project project;
     private String name;
     private String description;
     private Date creationTime;
     private Date modificationTime;
     private Date planStartTime;
     private Date planEndTime;
     private Integer planDuration;
     private Date startTime;
     private Date endTime;
     private Integer duration;
     private String priority;
     private String status;
     private Integer seq;
     private Set<TaskDoc> taskDocs = new HashSet<TaskDoc>(0);
     private Set<TaskComment> taskComments = new HashSet<TaskComment>(0);

    public Task() {
    }

	
    public Task(String name, Date planStartTime, Date planEndTime, String priority, String status) {
        this.name = name;
        this.planStartTime = planStartTime;
        this.planEndTime = planEndTime;
        this.priority = priority;
        this.status = status;
    }
    public Task(Users usersByAssignee, Users usersByCreator, Project project, String name, String description, Date creationTime, Date modificationTime, Date planStartTime, Date planEndTime, Integer planDuration, Date startTime, Date endTime, Integer duration, String priority, String status, Integer seq, Set<TaskDoc> taskDocs, Set<TaskComment> taskComments) {
       this.usersByAssignee = usersByAssignee;
       this.usersByCreator = usersByCreator;
       this.project = project;
       this.name = name;
       this.description = description;
       this.creationTime = creationTime;
       this.modificationTime = modificationTime;
       this.planStartTime = planStartTime;
       this.planEndTime = planEndTime;
       this.planDuration = planDuration;
       this.startTime = startTime;
       this.endTime = endTime;
       this.duration = duration;
       this.priority = priority;
       this.status = status;
       this.seq = seq;
       this.taskDocs = taskDocs;
       this.taskComments = taskComments;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ID_TASK", unique=true, nullable=false)
    public Integer getIdTask() {
        return this.idTask;
    }
    
    public void setIdTask(Integer idTask) {
        this.idTask = idTask;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ASSIGNEE")
    public Users getUsersByAssignee() {
        return this.usersByAssignee;
    }
    
    public void setUsersByAssignee(Users usersByAssignee) {
        this.usersByAssignee = usersByAssignee;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CREATOR")
    public Users getUsersByCreator() {
        return this.usersByCreator;
    }
    
    public void setUsersByCreator(Users usersByCreator) {
        this.usersByCreator = usersByCreator;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_PROJECT")
    public Project getProject() {
        return this.project;
    }
    
    public void setProject(Project project) {
        this.project = project;
    }
    
    @Column(name="NAME", nullable=false, length=64)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="DESCRIPTION", length=1024)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="CREATION_TIME", length=10)
    public Date getCreationTime() {
        return this.creationTime;
    }
    
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="MODIFICATION_TIME", length=10)
    public Date getModificationTime() {
        return this.modificationTime;
    }
    
    public void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="PLAN_START_TIME", nullable=false, length=10)
    public Date getPlanStartTime() {
        return this.planStartTime;
    }
    
    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="PLAN_END_TIME", nullable=false, length=10)
    public Date getPlanEndTime() {
        return this.planEndTime;
    }
    
    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }
    
    @Column(name="PLAN_DURATION")
    public Integer getPlanDuration() {
        return this.planDuration;
    }
    
    public void setPlanDuration(Integer planDuration) {
        this.planDuration = planDuration;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="START_TIME", length=10)
    public Date getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="END_TIME", length=10)
    public Date getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    @Column(name="DURATION")
    public Integer getDuration() {
        return this.duration;
    }
    
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    
    @Column(name="PRIORITY", nullable=false, length=20)
    public String getPriority() {
        return this.priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    @Column(name="STATUS", nullable=false, length=20)
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Column(name="SEQ")
    public Integer getSeq() {
        return this.seq;
    }
    
    public void setSeq(Integer seq) {
        this.seq = seq;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="task")
    public Set<TaskDoc> getTaskDocs() {
        return this.taskDocs;
    }
    
    public void setTaskDocs(Set<TaskDoc> taskDocs) {
        this.taskDocs = taskDocs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="task")
    public Set<TaskComment> getTaskComments() {
        return this.taskComments;
    }
    
    public void setTaskComments(Set<TaskComment> taskComments) {
        this.taskComments = taskComments;
    }




}


