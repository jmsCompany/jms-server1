package com.jms.domain.db;
// Generated 2016-3-9 10:11:30 by Hibernate Tools 3.2.2.GA


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
 * AApplication generated by hbm2java
 */
@Entity
@Table(name="a_application"
    ,catalog="jms4"
)
public class AApplication  implements java.io.Serializable {


     private Long idApplication;
     private SysDicD sysDicD;
     private AApprovalProcess AApprovalProcess;
     private AApplication AApplication;
     private Users users;
     private Date startTime;
     private Date endTime;
     private String content;
     private Set<AApplication> AApplications = new HashSet<AApplication>(0);
     private Set<AApplicationPhase> AApplicationPhases = new HashSet<AApplicationPhase>(0);

    public AApplication() {
    }

    public AApplication(SysDicD sysDicD, AApprovalProcess AApprovalProcess, AApplication AApplication, Users users, Date startTime, Date endTime, String content, Set<AApplication> AApplications, Set<AApplicationPhase> AApplicationPhases) {
       this.sysDicD = sysDicD;
       this.AApprovalProcess = AApprovalProcess;
       this.AApplication = AApplication;
       this.users = users;
       this.startTime = startTime;
       this.endTime = endTime;
       this.content = content;
       this.AApplications = AApplications;
       this.AApplicationPhases = AApplicationPhases;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_application", unique=true, nullable=false)
    public Long getIdApplication() {
        return this.idApplication;
    }
    
    public void setIdApplication(Long idApplication) {
        this.idApplication = idApplication;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="status")
    public SysDicD getSysDicD() {
        return this.sysDicD;
    }
    
    public void setSysDicD(SysDicD sysDicD) {
        this.sysDicD = sysDicD;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_approval_process")
    public AApprovalProcess getAApprovalProcess() {
        return this.AApprovalProcess;
    }
    
    public void setAApprovalProcess(AApprovalProcess AApprovalProcess) {
        this.AApprovalProcess = AApprovalProcess;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="previous_application")
    public AApplication getAApplication() {
        return this.AApplication;
    }
    
    public void setAApplication(AApplication AApplication) {
        this.AApplication = AApplication;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="applicant")
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="start_time", length=19)
    public Date getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="end_time", length=19)
    public Date getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    @Column(name="content", length=1024)
    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="AApplication")
    public Set<AApplication> getAApplications() {
        return this.AApplications;
    }
    
    public void setAApplications(Set<AApplication> AApplications) {
        this.AApplications = AApplications;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="AApplication")
    public Set<AApplicationPhase> getAApplicationPhases() {
        return this.AApplicationPhases;
    }
    
    public void setAApplicationPhases(Set<AApplicationPhase> AApplicationPhases) {
        this.AApplicationPhases = AApplicationPhases;
    }




}


