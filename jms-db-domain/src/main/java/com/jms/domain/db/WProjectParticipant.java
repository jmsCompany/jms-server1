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
 * WProjectParticipant generated by hbm2java
 */
@Entity
@Table(name="w_project_participant"
    ,catalog="jms4"
)
public class WProjectParticipant  implements java.io.Serializable {


     private WProjectParticipantId id;
     private Users users;
     private WProject WProject;
     private Long isCharge;

    public WProjectParticipant() {
    }

	
    public WProjectParticipant(WProjectParticipantId id, Users users, WProject WProject) {
        this.id = id;
        this.users = users;
        this.WProject = WProject;
    }
    public WProjectParticipant(WProjectParticipantId id, Users users, WProject WProject, Long isCharge) {
       this.id = id;
       this.users = users;
       this.WProject = WProject;
       this.isCharge = isCharge;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idProject", column=@Column(name="id_project", nullable=false) ), 
        @AttributeOverride(name="idUser", column=@Column(name="id_user", nullable=false) ) } )
    public WProjectParticipantId getId() {
        return this.id;
    }
    
    public void setId(WProjectParticipantId id) {
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
    @JoinColumn(name="id_project", nullable=false, insertable=false, updatable=false)
    public WProject getWProject() {
        return this.WProject;
    }
    
    public void setWProject(WProject WProject) {
        this.WProject = WProject;
    }
    
    @Column(name="is_charge")
    public Long getIsCharge() {
        return this.isCharge;
    }
    
    public void setIsCharge(Long isCharge) {
        this.isCharge = isCharge;
    }




}


