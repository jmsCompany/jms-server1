package com.jms.domain.db;
// Generated 2015-7-5 7:21:02 by Hibernate Tools 3.2.2.GA


import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * ApplicationPhase generated by hbm2java
 */
@Audited
@AuditTable(catalog="jms_aud", value = "application_phase_AUD")
@Entity
@Table(name="application_phase"
    ,catalog="jms"
)
public class ApplicationPhase  implements java.io.Serializable {


     private ApplicationPhaseId id;
     private SysDicD sysDicD;
     private Application application;
     private ApprovalPhase approvalPhase;
     private Date actionTime;
     private String comment;

    public ApplicationPhase() {
    }

	
    public ApplicationPhase(ApplicationPhaseId id, Application application, ApprovalPhase approvalPhase) {
        this.id = id;
        this.application = application;
        this.approvalPhase = approvalPhase;
    }
    public ApplicationPhase(ApplicationPhaseId id, SysDicD sysDicD, Application application, ApprovalPhase approvalPhase, Date actionTime, String comment) {
       this.id = id;
       this.sysDicD = sysDicD;
       this.application = application;
       this.approvalPhase = approvalPhase;
       this.actionTime = actionTime;
       this.comment = comment;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idApplication", column=@Column(name="ID_APPLICATION", nullable=false) ), 
        @AttributeOverride(name="idApprovalPhase", column=@Column(name="ID_APPROVAL_PHASE", nullable=false) ) } )
    public ApplicationPhaseId getId() {
        return this.id;
    }
    
    public void setId(ApplicationPhaseId id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="STATUS")
    public SysDicD getSysDicD() {
        return this.sysDicD;
    }
    
    public void setSysDicD(SysDicD sysDicD) {
        this.sysDicD = sysDicD;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_APPLICATION", nullable=false, insertable=false, updatable=false)
    public Application getApplication() {
        return this.application;
    }
    
    public void setApplication(Application application) {
        this.application = application;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_APPROVAL_PHASE", nullable=false, insertable=false, updatable=false)
    public ApprovalPhase getApprovalPhase() {
        return this.approvalPhase;
    }
    
    public void setApprovalPhase(ApprovalPhase approvalPhase) {
        this.approvalPhase = approvalPhase;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ACTION_TIME", length=19)
    public Date getActionTime() {
        return this.actionTime;
    }
    
    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }
    
    @Column(name="COMMENT", length=512)
    public String getComment() {
        return this.comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }




}


