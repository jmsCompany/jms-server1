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

/**
 * TaskParticipant generated by hbm2java
 */
@Entity
@Table(name="task_participant"
    ,catalog="jms"
)
public class TaskParticipant  implements java.io.Serializable {


     private TaskParticipantId id;
     private Users users;
     private Task task;
     private Long isCharge;

    public TaskParticipant() {
    }

	
    public TaskParticipant(TaskParticipantId id, Users users, Task task) {
        this.id = id;
        this.users = users;
        this.task = task;
    }
    public TaskParticipant(TaskParticipantId id, Users users, Task task, Long isCharge) {
       this.id = id;
       this.users = users;
       this.task = task;
       this.isCharge = isCharge;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idTask", column=@Column(name="ID_TASK", nullable=false) ), 
        @AttributeOverride(name="idUser", column=@Column(name="ID_USER", nullable=false) ) } )
    public TaskParticipantId getId() {
        return this.id;
    }
    
    public void setId(TaskParticipantId id) {
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
    @JoinColumn(name="ID_TASK", nullable=false, insertable=false, updatable=false)
    public Task getTask() {
        return this.task;
    }
    
    public void setTask(Task task) {
        this.task = task;
    }
    
    @Column(name="IS_CHARGE")
    public Long getIsCharge() {
        return this.isCharge;
    }
    
    public void setIsCharge(Long isCharge) {
        this.isCharge = isCharge;
    }




}


