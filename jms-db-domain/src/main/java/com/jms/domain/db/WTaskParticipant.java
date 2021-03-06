package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


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
 * WTaskParticipant generated by hbm2java
 */
@Entity
@Table(name="w_task_participant"
)
public class WTaskParticipant  implements java.io.Serializable {


     private WTaskParticipantId id;
     private Users users;
     private WTask WTask;
     private Long isCharge;

    public WTaskParticipant() {
    }

	
    public WTaskParticipant(WTaskParticipantId id, Users users, WTask WTask) {
        this.id = id;
        this.users = users;
        this.WTask = WTask;
    }
    public WTaskParticipant(WTaskParticipantId id, Users users, WTask WTask, Long isCharge) {
       this.id = id;
       this.users = users;
       this.WTask = WTask;
       this.isCharge = isCharge;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idTask", column=@Column(name="id_task", nullable=false) ), 
        @AttributeOverride(name="idUser", column=@Column(name="id_user", nullable=false) ) } )
    public WTaskParticipantId getId() {
        return this.id;
    }
    
    public void setId(WTaskParticipantId id) {
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
    @JoinColumn(name="id_task", nullable=false, insertable=false, updatable=false)
    public WTask getWTask() {
        return this.WTask;
    }
    
    public void setWTask(WTask WTask) {
        this.WTask = WTask;
    }
    
    @Column(name="is_charge")
    public Long getIsCharge() {
        return this.isCharge;
    }
    
    public void setIsCharge(Long isCharge) {
        this.isCharge = isCharge;
    }




}


