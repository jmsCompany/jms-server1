package com.jms.domain.db;
// Generated 2016-8-23 16:01:55 by Hibernate Tools 3.2.2.GA


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
 * QCar generated by hbm2java
 */
@Entity
@Table(name="q_car"
    ,catalog="jms5"
)
public class QCar  implements java.io.Serializable {


     private Long idCar;
     private QNcr2 QNcr2;
     private String carNo;
     private String to1;
     private String problemDes;
     private String ia;
     private String ea;
     private String quality;
     private String ehs;
     private String other1;
     private String other1Des;
     private String man;
     private String machine;
     private String material;
     private String method;
     private String other2;
     private String other2Des;
     private String interimCorrectiveAction;
     private Date commitDate1;
     private String permanentCorrectiveAction;
     private Date commitDate2;
     private Long response;
     private Date date1;
     private String result;
     private String actionPlan;
     private Long confirmor;
     private Date date2;

    public QCar() {
    }

    public QCar(QNcr2 QNcr2, String carNo, String to1, String problemDes, String ia, String ea, String quality, String ehs, String other1, String other1Des, String man, String machine, String material, String method, String other2, String other2Des, String interimCorrectiveAction, Date commitDate1, String permanentCorrectiveAction, Date commitDate2, Long response, Date date1, String result, String actionPlan, Long confirmor, Date date2) {
       this.QNcr2 = QNcr2;
       this.carNo = carNo;
       this.to1 = to1;
       this.problemDes = problemDes;
       this.ia = ia;
       this.ea = ea;
       this.quality = quality;
       this.ehs = ehs;
       this.other1 = other1;
       this.other1Des = other1Des;
       this.man = man;
       this.machine = machine;
       this.material = material;
       this.method = method;
       this.other2 = other2;
       this.other2Des = other2Des;
       this.interimCorrectiveAction = interimCorrectiveAction;
       this.commitDate1 = commitDate1;
       this.permanentCorrectiveAction = permanentCorrectiveAction;
       this.commitDate2 = commitDate2;
       this.response = response;
       this.date1 = date1;
       this.result = result;
       this.actionPlan = actionPlan;
       this.confirmor = confirmor;
       this.date2 = date2;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_car", unique=true, nullable=false)
    public Long getIdCar() {
        return this.idCar;
    }
    
    public void setIdCar(Long idCar) {
        this.idCar = idCar;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_ncr")
    public QNcr2 getQNcr2() {
        return this.QNcr2;
    }
    
    public void setQNcr2(QNcr2 QNcr2) {
        this.QNcr2 = QNcr2;
    }
    
    @Column(name="car_no", length=20)
    public String getCarNo() {
        return this.carNo;
    }
    
    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }
    
    @Column(name="to1", length=256)
    public String getTo1() {
        return this.to1;
    }
    
    public void setTo1(String to1) {
        this.to1 = to1;
    }
    
    @Column(name="problem_des", length=512)
    public String getProblemDes() {
        return this.problemDes;
    }
    
    public void setProblemDes(String problemDes) {
        this.problemDes = problemDes;
    }
    
    @Column(name="ia", length=20)
    public String getIa() {
        return this.ia;
    }
    
    public void setIa(String ia) {
        this.ia = ia;
    }
    
    @Column(name="ea", length=20)
    public String getEa() {
        return this.ea;
    }
    
    public void setEa(String ea) {
        this.ea = ea;
    }
    
    @Column(name="quality", length=20)
    public String getQuality() {
        return this.quality;
    }
    
    public void setQuality(String quality) {
        this.quality = quality;
    }
    
    @Column(name="ehs", length=20)
    public String getEhs() {
        return this.ehs;
    }
    
    public void setEhs(String ehs) {
        this.ehs = ehs;
    }
    
    @Column(name="other1", length=20)
    public String getOther1() {
        return this.other1;
    }
    
    public void setOther1(String other1) {
        this.other1 = other1;
    }
    
    @Column(name="other1_des", length=32)
    public String getOther1Des() {
        return this.other1Des;
    }
    
    public void setOther1Des(String other1Des) {
        this.other1Des = other1Des;
    }
    
    @Column(name="man", length=20)
    public String getMan() {
        return this.man;
    }
    
    public void setMan(String man) {
        this.man = man;
    }
    
    @Column(name="machine", length=20)
    public String getMachine() {
        return this.machine;
    }
    
    public void setMachine(String machine) {
        this.machine = machine;
    }
    
    @Column(name="material", length=20)
    public String getMaterial() {
        return this.material;
    }
    
    public void setMaterial(String material) {
        this.material = material;
    }
    
    @Column(name="method", length=20)
    public String getMethod() {
        return this.method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
    @Column(name="other2", length=20)
    public String getOther2() {
        return this.other2;
    }
    
    public void setOther2(String other2) {
        this.other2 = other2;
    }
    
    @Column(name="other2_des", length=32)
    public String getOther2Des() {
        return this.other2Des;
    }
    
    public void setOther2Des(String other2Des) {
        this.other2Des = other2Des;
    }
    
    @Column(name="interim_corrective_action", length=1024)
    public String getInterimCorrectiveAction() {
        return this.interimCorrectiveAction;
    }
    
    public void setInterimCorrectiveAction(String interimCorrectiveAction) {
        this.interimCorrectiveAction = interimCorrectiveAction;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="commit_date1", length=10)
    public Date getCommitDate1() {
        return this.commitDate1;
    }
    
    public void setCommitDate1(Date commitDate1) {
        this.commitDate1 = commitDate1;
    }
    
    @Column(name="permanent_corrective_action", length=1024)
    public String getPermanentCorrectiveAction() {
        return this.permanentCorrectiveAction;
    }
    
    public void setPermanentCorrectiveAction(String permanentCorrectiveAction) {
        this.permanentCorrectiveAction = permanentCorrectiveAction;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="commit_date2", length=10)
    public Date getCommitDate2() {
        return this.commitDate2;
    }
    
    public void setCommitDate2(Date commitDate2) {
        this.commitDate2 = commitDate2;
    }
    
    @Column(name="response")
    public Long getResponse() {
        return this.response;
    }
    
    public void setResponse(Long response) {
        this.response = response;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="date1", length=10)
    public Date getDate1() {
        return this.date1;
    }
    
    public void setDate1(Date date1) {
        this.date1 = date1;
    }
    
    @Column(name="result", length=256)
    public String getResult() {
        return this.result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
    
    @Column(name="action_plan", length=256)
    public String getActionPlan() {
        return this.actionPlan;
    }
    
    public void setActionPlan(String actionPlan) {
        this.actionPlan = actionPlan;
    }
    
    @Column(name="confirmor")
    public Long getConfirmor() {
        return this.confirmor;
    }
    
    public void setConfirmor(Long confirmor) {
        this.confirmor = confirmor;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="date2", length=10)
    public Date getDate2() {
        return this.date2;
    }
    
    public void setDate2(Date date2) {
        this.date2 = date2;
    }




}


