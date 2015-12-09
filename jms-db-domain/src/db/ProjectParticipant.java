package com.jms.domain.db;
// Generated 2015-7-22 10:30:44 by Hibernate Tools 3.2.2.GA


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
 * ProjectParticipant generated by hbm2java
 */
@Audited
@AuditTable(catalog="jms_aud", value = "project_participant")
@Entity
@Table(name="project_participant"
    ,catalog="jms"
)
public class ProjectParticipant  implements java.io.Serializable {


     private ProjectParticipantId id;
     private Users users;
     private Project project;
     private Long isCharge;

    public ProjectParticipant() {
    }

	
    public ProjectParticipant(ProjectParticipantId id, Users users, Project project) {
        this.id = id;
        this.users = users;
        this.project = project;
    }
    public ProjectParticipant(ProjectParticipantId id, Users users, Project project, Long isCharge) {
       this.id = id;
       this.users = users;
       this.project = project;
       this.isCharge = isCharge;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idProject", column=@Column(name="ID_PROJECT", nullable=false) ), 
        @AttributeOverride(name="idUser", column=@Column(name="ID_USER", nullable=false) ) } )
    public ProjectParticipantId getId() {
        return this.id;
    }
    
    public void setId(ProjectParticipantId id) {
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
    @JoinColumn(name="ID_PROJECT", nullable=false, insertable=false, updatable=false)
    public Project getProject() {
        return this.project;
    }
    
    public void setProject(Project project) {
        this.project = project;
    }
    
    @Column(name="IS_CHARGE")
    public Long getIsCharge() {
        return this.isCharge;
    }
    
    public void setIsCharge(Long isCharge) {
        this.isCharge = isCharge;
    }




}

