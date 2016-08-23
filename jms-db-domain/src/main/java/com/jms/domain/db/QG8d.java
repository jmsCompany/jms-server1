package com.jms.domain.db;
// Generated 2016-8-23 14:57:20 by Hibernate Tools 3.2.2.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * QG8d generated by hbm2java
 */
@Entity
@Table(name="q_g8d"
    ,catalog="jms5"
)
public class QG8d  implements java.io.Serializable {


     private Long idG8d;
     private String g8dNo;
     private String title;
     private Date dateOpened;
     private Date lastUpdated;
     private Long idMaterial;
     private Long idRoutineD;
     private String organisationInfo;
     private Character emergencyResponseAction;
     private String effective1;
     private Date dateImplemented1;
     private String problemState;
     private String interimContainmentAction;
     private String effective2;
     private Date dateImplemented2;
     private String rootCause;
     private String default_;
     private String contribution;
     private String chosenPermanentAction;
     private String effective3;
     private String implementedPermanentAction;
     private Date dateImplemented3;
     private String prevenAction;
     private Date dateImplemented4;
     private String systemicPrevent;
     private String responsibility;
     private String tiRecognition;
     private Date dateClosed;
     private String reportedBy;
     private Long idLeader;
     private Set<QG8dUsers> QG8dUserses = new HashSet<QG8dUsers>(0);
     private Set<QG8dUsers> QG8dUserses_1 = new HashSet<QG8dUsers>(0);
     private Set<QNcr2> QNcr2s = new HashSet<QNcr2>(0);

    public QG8d() {
    }

	
    public QG8d(Long idG8d) {
        this.idG8d = idG8d;
    }
    public QG8d(Long idG8d, String g8dNo, String title, Date dateOpened, Date lastUpdated, Long idMaterial, Long idRoutineD, String organisationInfo, Character emergencyResponseAction, String effective1, Date dateImplemented1, String problemState, String interimContainmentAction, String effective2, Date dateImplemented2, String rootCause, String default_, String contribution, String chosenPermanentAction, String effective3, String implementedPermanentAction, Date dateImplemented3, String prevenAction, Date dateImplemented4, String systemicPrevent, String responsibility, String tiRecognition, Date dateClosed, String reportedBy, Long idLeader, Set<QG8dUsers> QG8dUserses, Set<QG8dUsers> QG8dUserses_1, Set<QNcr2> QNcr2s) {
       this.idG8d = idG8d;
       this.g8dNo = g8dNo;
       this.title = title;
       this.dateOpened = dateOpened;
       this.lastUpdated = lastUpdated;
       this.idMaterial = idMaterial;
       this.idRoutineD = idRoutineD;
       this.organisationInfo = organisationInfo;
       this.emergencyResponseAction = emergencyResponseAction;
       this.effective1 = effective1;
       this.dateImplemented1 = dateImplemented1;
       this.problemState = problemState;
       this.interimContainmentAction = interimContainmentAction;
       this.effective2 = effective2;
       this.dateImplemented2 = dateImplemented2;
       this.rootCause = rootCause;
       this.default_ = default_;
       this.contribution = contribution;
       this.chosenPermanentAction = chosenPermanentAction;
       this.effective3 = effective3;
       this.implementedPermanentAction = implementedPermanentAction;
       this.dateImplemented3 = dateImplemented3;
       this.prevenAction = prevenAction;
       this.dateImplemented4 = dateImplemented4;
       this.systemicPrevent = systemicPrevent;
       this.responsibility = responsibility;
       this.tiRecognition = tiRecognition;
       this.dateClosed = dateClosed;
       this.reportedBy = reportedBy;
       this.idLeader = idLeader;
       this.QG8dUserses = QG8dUserses;
       this.QG8dUserses_1 = QG8dUserses_1;
       this.QNcr2s = QNcr2s;
    }
   
     @Id 
    
    @Column(name="id_g8d", unique=true, nullable=false)
    public Long getIdG8d() {
        return this.idG8d;
    }
    
    public void setIdG8d(Long idG8d) {
        this.idG8d = idG8d;
    }
    
    @Column(name="g8d_no", length=20)
    public String getG8dNo() {
        return this.g8dNo;
    }
    
    public void setG8dNo(String g8dNo) {
        this.g8dNo = g8dNo;
    }
    
    @Column(name="title", length=128)
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="date_opened", length=10)
    public Date getDateOpened() {
        return this.dateOpened;
    }
    
    public void setDateOpened(Date dateOpened) {
        this.dateOpened = dateOpened;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="last_updated", length=10)
    public Date getLastUpdated() {
        return this.lastUpdated;
    }
    
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    @Column(name="id_material")
    public Long getIdMaterial() {
        return this.idMaterial;
    }
    
    public void setIdMaterial(Long idMaterial) {
        this.idMaterial = idMaterial;
    }
    
    @Column(name="id_routine_d")
    public Long getIdRoutineD() {
        return this.idRoutineD;
    }
    
    public void setIdRoutineD(Long idRoutineD) {
        this.idRoutineD = idRoutineD;
    }
    
    @Column(name="organisation_info", length=128)
    public String getOrganisationInfo() {
        return this.organisationInfo;
    }
    
    public void setOrganisationInfo(String organisationInfo) {
        this.organisationInfo = organisationInfo;
    }
    
    @Column(name="emergency_response_action", length=1)
    public Character getEmergencyResponseAction() {
        return this.emergencyResponseAction;
    }
    
    public void setEmergencyResponseAction(Character emergencyResponseAction) {
        this.emergencyResponseAction = emergencyResponseAction;
    }
    
    @Column(name="effective1", length=20)
    public String getEffective1() {
        return this.effective1;
    }
    
    public void setEffective1(String effective1) {
        this.effective1 = effective1;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="date_implemented1", length=10)
    public Date getDateImplemented1() {
        return this.dateImplemented1;
    }
    
    public void setDateImplemented1(Date dateImplemented1) {
        this.dateImplemented1 = dateImplemented1;
    }
    
    @Column(name="problem_state", length=512)
    public String getProblemState() {
        return this.problemState;
    }
    
    public void setProblemState(String problemState) {
        this.problemState = problemState;
    }
    
    @Column(name="interim_containment_action", length=1024)
    public String getInterimContainmentAction() {
        return this.interimContainmentAction;
    }
    
    public void setInterimContainmentAction(String interimContainmentAction) {
        this.interimContainmentAction = interimContainmentAction;
    }
    
    @Column(name="effective2", length=20)
    public String getEffective2() {
        return this.effective2;
    }
    
    public void setEffective2(String effective2) {
        this.effective2 = effective2;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="date_implemented2", length=10)
    public Date getDateImplemented2() {
        return this.dateImplemented2;
    }
    
    public void setDateImplemented2(Date dateImplemented2) {
        this.dateImplemented2 = dateImplemented2;
    }
    
    @Column(name="root_cause", length=1024)
    public String getRootCause() {
        return this.rootCause;
    }
    
    public void setRootCause(String rootCause) {
        this.rootCause = rootCause;
    }
    
    @Column(name="_default_", length=18)
    public String getDefault_() {
        return this.default_;
    }
    
    public void setDefault_(String default_) {
        this.default_ = default_;
    }
    
    @Column(name="contribution", length=20)
    public String getContribution() {
        return this.contribution;
    }
    
    public void setContribution(String contribution) {
        this.contribution = contribution;
    }
    
    @Column(name="chosen_permanent_action", length=1024)
    public String getChosenPermanentAction() {
        return this.chosenPermanentAction;
    }
    
    public void setChosenPermanentAction(String chosenPermanentAction) {
        this.chosenPermanentAction = chosenPermanentAction;
    }
    
    @Column(name="effective3", length=20)
    public String getEffective3() {
        return this.effective3;
    }
    
    public void setEffective3(String effective3) {
        this.effective3 = effective3;
    }
    
    @Column(name="implemented_permanent_action", length=1024)
    public String getImplementedPermanentAction() {
        return this.implementedPermanentAction;
    }
    
    public void setImplementedPermanentAction(String implementedPermanentAction) {
        this.implementedPermanentAction = implementedPermanentAction;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="date_implemented3", length=10)
    public Date getDateImplemented3() {
        return this.dateImplemented3;
    }
    
    public void setDateImplemented3(Date dateImplemented3) {
        this.dateImplemented3 = dateImplemented3;
    }
    
    @Column(name="preven_action", length=512)
    public String getPrevenAction() {
        return this.prevenAction;
    }
    
    public void setPrevenAction(String prevenAction) {
        this.prevenAction = prevenAction;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="date_implemented4", length=10)
    public Date getDateImplemented4() {
        return this.dateImplemented4;
    }
    
    public void setDateImplemented4(Date dateImplemented4) {
        this.dateImplemented4 = dateImplemented4;
    }
    
    @Column(name="systemic_prevent", length=512)
    public String getSystemicPrevent() {
        return this.systemicPrevent;
    }
    
    public void setSystemicPrevent(String systemicPrevent) {
        this.systemicPrevent = systemicPrevent;
    }
    
    @Column(name="responsibility", length=20)
    public String getResponsibility() {
        return this.responsibility;
    }
    
    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }
    
    @Column(name="ti_recognition", length=512)
    public String getTiRecognition() {
        return this.tiRecognition;
    }
    
    public void setTiRecognition(String tiRecognition) {
        this.tiRecognition = tiRecognition;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="date_closed", length=10)
    public Date getDateClosed() {
        return this.dateClosed;
    }
    
    public void setDateClosed(Date dateClosed) {
        this.dateClosed = dateClosed;
    }
    
    @Column(name="reported_by", length=20)
    public String getReportedBy() {
        return this.reportedBy;
    }
    
    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }
    
    @Column(name="id_leader")
    public Long getIdLeader() {
        return this.idLeader;
    }
    
    public void setIdLeader(Long idLeader) {
        this.idLeader = idLeader;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="QG8d")
    public Set<QG8dUsers> getQG8dUserses() {
        return this.QG8dUserses;
    }
    
    public void setQG8dUserses(Set<QG8dUsers> QG8dUserses) {
        this.QG8dUserses = QG8dUserses;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="QG8d")
    public Set<QG8dUsers> getQG8dUserses_1() {
        return this.QG8dUserses_1;
    }
    
    public void setQG8dUserses_1(Set<QG8dUsers> QG8dUserses_1) {
        this.QG8dUserses_1 = QG8dUserses_1;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="QG8d")
    public Set<QNcr2> getQNcr2s() {
        return this.QNcr2s;
    }
    
    public void setQNcr2s(Set<QNcr2> QNcr2s) {
        this.QNcr2s = QNcr2s;
    }




}

