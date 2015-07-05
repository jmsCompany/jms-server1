package com.jms.domain.db;
// Generated 2015-7-5 7:21:02 by Hibernate Tools 3.2.2.GA


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

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * ApprovalPhase generated by hbm2java
 */
@Audited
@AuditTable(catalog="jms_aud", value = "approval_phase_AUD")
@Entity
@Table(name="approval_phase"
    ,catalog="jms"
)
public class ApprovalPhase  implements java.io.Serializable {


     private Long idApprovalPhase;
     private ApprovalProcess approvalProcess;
     private Users users;
     private String name;
     private Long seq;
     private Set<ApplicationPhase> applicationPhases = new HashSet<ApplicationPhase>(0);
     private Set<ApprovalCc> approvalCcs = new HashSet<ApprovalCc>(0);

    public ApprovalPhase() {
    }

    public ApprovalPhase(ApprovalProcess approvalProcess, Users users, String name, Long seq, Set<ApplicationPhase> applicationPhases, Set<ApprovalCc> approvalCcs) {
       this.approvalProcess = approvalProcess;
       this.users = users;
       this.name = name;
       this.seq = seq;
       this.applicationPhases = applicationPhases;
       this.approvalCcs = approvalCcs;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ID_APPROVAL_PHASE", unique=true, nullable=false)
    public Long getIdApprovalPhase() {
        return this.idApprovalPhase;
    }
    
    public void setIdApprovalPhase(Long idApprovalPhase) {
        this.idApprovalPhase = idApprovalPhase;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_APPROVAL_PROCESS")
    public ApprovalProcess getApprovalProcess() {
        return this.approvalProcess;
    }
    
    public void setApprovalProcess(ApprovalProcess approvalProcess) {
        this.approvalProcess = approvalProcess;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="APPROVER")
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    
    @Column(name="NAME", length=64)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="SEQ")
    public Long getSeq() {
        return this.seq;
    }
    
    public void setSeq(Long seq) {
        this.seq = seq;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="approvalPhase")
    public Set<ApplicationPhase> getApplicationPhases() {
        return this.applicationPhases;
    }
    
    public void setApplicationPhases(Set<ApplicationPhase> applicationPhases) {
        this.applicationPhases = applicationPhases;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="approvalPhase")
    public Set<ApprovalCc> getApprovalCcs() {
        return this.approvalCcs;
    }
    
    public void setApprovalCcs(Set<ApprovalCc> approvalCcs) {
        this.approvalCcs = approvalCcs;
    }




}


