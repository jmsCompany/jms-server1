package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PCheckPlan generated by hbm2java
 */
@Entity
@Table(name="p_check_plan"
)
public class PCheckPlan  implements java.io.Serializable {


     private Long idCheck;
     private Users usersByChecker;
     private PCPp PCPp;
     private Users usersByCreator;
     private PStatusDic PStatusDic;
     private Long checkPlanNo;
     private Date planCheckTime;
     private Long planQty;
     private Date checkTime;
     private Long toBeQty;
     private Long finQty;
     private Date creationTime;

    public PCheckPlan() {
    }

    public PCheckPlan(Users usersByChecker, PCPp PCPp, Users usersByCreator, PStatusDic PStatusDic, Long checkPlanNo, Date planCheckTime, Long planQty, Date checkTime, Long toBeQty, Long finQty, Date creationTime) {
       this.usersByChecker = usersByChecker;
       this.PCPp = PCPp;
       this.usersByCreator = usersByCreator;
       this.PStatusDic = PStatusDic;
       this.checkPlanNo = checkPlanNo;
       this.planCheckTime = planCheckTime;
       this.planQty = planQty;
       this.checkTime = checkTime;
       this.toBeQty = toBeQty;
       this.finQty = finQty;
       this.creationTime = creationTime;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_check", unique=true, nullable=false)
    public Long getIdCheck() {
        return this.idCheck;
    }
    
    public void setIdCheck(Long idCheck) {
        this.idCheck = idCheck;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="checker")
    public Users getUsersByChecker() {
        return this.usersByChecker;
    }
    
    public void setUsersByChecker(Users usersByChecker) {
        this.usersByChecker = usersByChecker;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_c_pp")
    public PCPp getPCPp() {
        return this.PCPp;
    }
    
    public void setPCPp(PCPp PCPp) {
        this.PCPp = PCPp;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="creator")
    public Users getUsersByCreator() {
        return this.usersByCreator;
    }
    
    public void setUsersByCreator(Users usersByCreator) {
        this.usersByCreator = usersByCreator;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_pstatus")
    public PStatusDic getPStatusDic() {
        return this.PStatusDic;
    }
    
    public void setPStatusDic(PStatusDic PStatusDic) {
        this.PStatusDic = PStatusDic;
    }
    
    @Column(name="check_plan_no")
    public Long getCheckPlanNo() {
        return this.checkPlanNo;
    }
    
    public void setCheckPlanNo(Long checkPlanNo) {
        this.checkPlanNo = checkPlanNo;
    }
    @Temporal(TemporalType.TIME)
    @Column(name="plan_check_time", length=8)
    public Date getPlanCheckTime() {
        return this.planCheckTime;
    }
    
    public void setPlanCheckTime(Date planCheckTime) {
        this.planCheckTime = planCheckTime;
    }
    
    @Column(name="plan_qty")
    public Long getPlanQty() {
        return this.planQty;
    }
    
    public void setPlanQty(Long planQty) {
        this.planQty = planQty;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="check_time", length=19)
    public Date getCheckTime() {
        return this.checkTime;
    }
    
    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }
    
    @Column(name="to_be_qty")
    public Long getToBeQty() {
        return this.toBeQty;
    }
    
    public void setToBeQty(Long toBeQty) {
        this.toBeQty = toBeQty;
    }
    
    @Column(name="fin_qty")
    public Long getFinQty() {
        return this.finQty;
    }
    
    public void setFinQty(Long finQty) {
        this.finQty = finQty;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="creation_time", length=19)
    public Date getCreationTime() {
        return this.creationTime;
    }
    
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }




}


