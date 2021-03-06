package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


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
 * MRepairHistory generated by hbm2java
 */
@Entity
@Table(name="m_repair_history"
)
public class MRepairHistory  implements java.io.Serializable {


     private Long idRepairHistory;
     private MMachine MMachine;
     private Users usersByConfirmor;
     private Users usersByOp;
     private Users usersByRepair;
     private MStatusDic MStatusDic;
     private Users usersByMaintainer;
     private Long repairHistoryNo;
     private Date repairTime;
     private String issueDes;
     private String solution;
     private String suggestion;
     private Date responseTime;
     private Date repairingTime;
     private Date recoverTime;
     
     private Long idUnplannedStop;
     private Set<MHistoryPart> MHistoryParts = new HashSet<MHistoryPart>(0);

    public MRepairHistory() {
    }

    public MRepairHistory(MMachine MMachine, Users usersByConfirmor, Users usersByOp, Users usersByRepair, MStatusDic MStatusDic, Users usersByMaintainer, Long repairHistoryNo, Date repairTime, String issueDes, String solution, String suggestion, Date responseTime, Date repairingTime, Date recoverTime, Set<MHistoryPart> MHistoryParts) {
       this.MMachine = MMachine;
       this.usersByConfirmor = usersByConfirmor;
       this.usersByOp = usersByOp;
       this.usersByRepair = usersByRepair;
       this.MStatusDic = MStatusDic;
       this.usersByMaintainer = usersByMaintainer;
       this.repairHistoryNo = repairHistoryNo;
       this.repairTime = repairTime;
       this.issueDes = issueDes;
       this.solution = solution;
       this.suggestion = suggestion;
       this.responseTime = responseTime;
       this.repairingTime = repairingTime;
       this.recoverTime = recoverTime;
       this.MHistoryParts = MHistoryParts;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_repair_history", unique=true, nullable=false)
    public Long getIdRepairHistory() {
        return this.idRepairHistory;
    }
    
    public void setIdRepairHistory(Long idRepairHistory) {
        this.idRepairHistory = idRepairHistory;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_machine")
    public MMachine getMMachine() {
        return this.MMachine;
    }
    
    public void setMMachine(MMachine MMachine) {
        this.MMachine = MMachine;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="confirmor")
    public Users getUsersByConfirmor() {
        return this.usersByConfirmor;
    }
    
    public void setUsersByConfirmor(Users usersByConfirmor) {
        this.usersByConfirmor = usersByConfirmor;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="op")
    public Users getUsersByOp() {
        return this.usersByOp;
    }
    
    public void setUsersByOp(Users usersByOp) {
        this.usersByOp = usersByOp;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="repair")
    public Users getUsersByRepair() {
        return this.usersByRepair;
    }
    
    public void setUsersByRepair(Users usersByRepair) {
        this.usersByRepair = usersByRepair;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_mstatus_dic")
    public MStatusDic getMStatusDic() {
        return this.MStatusDic;
    }
    
    public void setMStatusDic(MStatusDic MStatusDic) {
        this.MStatusDic = MStatusDic;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="maintainer")
    public Users getUsersByMaintainer() {
        return this.usersByMaintainer;
    }
    
    public void setUsersByMaintainer(Users usersByMaintainer) {
        this.usersByMaintainer = usersByMaintainer;
    }
    
    @Column(name="repair_history_no")
    public Long getRepairHistoryNo() {
        return this.repairHistoryNo;
    }
    
    public void setRepairHistoryNo(Long repairHistoryNo) {
        this.repairHistoryNo = repairHistoryNo;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="repair_time", length=19)
    public Date getRepairTime() {
        return this.repairTime;
    }
    
    public void setRepairTime(Date repairTime) {
        this.repairTime = repairTime;
    }
    
    @Column(name="issue_des", length=1024)
    public String getIssueDes() {
        return this.issueDes;
    }
    
    public void setIssueDes(String issueDes) {
        this.issueDes = issueDes;
    }
    
    @Column(name="solution", length=1024)
    public String getSolution() {
        return this.solution;
    }
    
    public void setSolution(String solution) {
        this.solution = solution;
    }
    
    @Column(name="suggestion", length=1024)
    public String getSuggestion() {
        return this.suggestion;
    }
    
    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="response_time", length=19)
    public Date getResponseTime() {
        return this.responseTime;
    }
    
    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="repairing_time", length=19)
    public Date getRepairingTime() {
        return this.repairingTime;
    }
    
    public void setRepairingTime(Date repairingTime) {
        this.repairingTime = repairingTime;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="recover_time", length=19)
    public Date getRecoverTime() {
        return this.recoverTime;
    }
    
    public void setRecoverTime(Date recoverTime) {
        this.recoverTime = recoverTime;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="MRepairHistory")
    public Set<MHistoryPart> getMHistoryParts() {
        return this.MHistoryParts;
    }
    
    public void setMHistoryParts(Set<MHistoryPart> MHistoryParts) {
        this.MHistoryParts = MHistoryParts;
    }
    @Column(name="id_unplanned_stop")
	public Long getIdUnplannedStop() {
		return idUnplannedStop;
	}

	public void setIdUnplannedStop(Long idUnplannedStop) {
		this.idUnplannedStop = idUnplannedStop;
	}




}


