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
 * QDeviation generated by hbm2java
 */
@Entity
@Table(name="q_deviation"
)
public class QDeviation  implements java.io.Serializable {


     private Long idDeviation;
     private QNoProcess QNoProcess;
     private String deviationNo;
     private Long idMaterial;
     private Long idRoutineD;
     private Long idCompanyCo;
     private Long issuer;
     private Date date1;
     private String scopePeriodMaxqty;
     private String reason;
     private String mfgRemark;
     private Long mfgSign;
     private String aeRemark;
     private Long aeSign;
     private String qeRemark;
     private Long qeSign;
     private String otherFuction;
     private Long ofSign;
     private Long idDept;

    public QDeviation() {
    }

    public QDeviation(QNoProcess QNoProcess, String deviationNo, Long idMaterial, Long idRoutineD, Long idCompanyCo, Long issuer, Date date1, String scopePeriodMaxqty, String reason, String mfgRemark, Long mfgSign, String aeRemark, Long aeSign, String qeRemark, Long qeSign, String otherFuction, Long ofSign, Long idDept) {
       this.QNoProcess = QNoProcess;
       this.deviationNo = deviationNo;
       this.idMaterial = idMaterial;
       this.idRoutineD = idRoutineD;
       this.idCompanyCo = idCompanyCo;
       this.issuer = issuer;
       this.date1 = date1;
       this.scopePeriodMaxqty = scopePeriodMaxqty;
       this.reason = reason;
       this.mfgRemark = mfgRemark;
       this.mfgSign = mfgSign;
       this.aeRemark = aeRemark;
       this.aeSign = aeSign;
       this.qeRemark = qeRemark;
       this.qeSign = qeSign;
       this.otherFuction = otherFuction;
       this.ofSign = ofSign;
       this.idDept = idDept;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_deviation", unique=true, nullable=false)
    public Long getIdDeviation() {
        return this.idDeviation;
    }
    
    public void setIdDeviation(Long idDeviation) {
        this.idDeviation = idDeviation;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_no_process")
    public QNoProcess getQNoProcess() {
        return this.QNoProcess;
    }
    
    public void setQNoProcess(QNoProcess QNoProcess) {
        this.QNoProcess = QNoProcess;
    }
    
    @Column(name="deviation_no", length=20)
    public String getDeviationNo() {
        return this.deviationNo;
    }
    
    public void setDeviationNo(String deviationNo) {
        this.deviationNo = deviationNo;
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
    
    @Column(name="id_company_co")
    public Long getIdCompanyCo() {
        return this.idCompanyCo;
    }
    
    public void setIdCompanyCo(Long idCompanyCo) {
        this.idCompanyCo = idCompanyCo;
    }
    
    @Column(name="issuer")
    public Long getIssuer() {
        return this.issuer;
    }
    
    public void setIssuer(Long issuer) {
        this.issuer = issuer;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="date1", length=10)
    public Date getDate1() {
        return this.date1;
    }
    
    public void setDate1(Date date1) {
        this.date1 = date1;
    }
    
    @Column(name="scope_period_maxqty", length=128)
    public String getScopePeriodMaxqty() {
        return this.scopePeriodMaxqty;
    }
    
    public void setScopePeriodMaxqty(String scopePeriodMaxqty) {
        this.scopePeriodMaxqty = scopePeriodMaxqty;
    }
    
    @Column(name="reason", length=1024)
    public String getReason() {
        return this.reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    @Column(name="mfg_remark", length=20)
    public String getMfgRemark() {
        return this.mfgRemark;
    }
    
    public void setMfgRemark(String mfgRemark) {
        this.mfgRemark = mfgRemark;
    }
    
    @Column(name="mfg_sign")
    public Long getMfgSign() {
        return this.mfgSign;
    }
    
    public void setMfgSign(Long mfgSign) {
        this.mfgSign = mfgSign;
    }
    
    @Column(name="ae_remark", length=20)
    public String getAeRemark() {
        return this.aeRemark;
    }
    
    public void setAeRemark(String aeRemark) {
        this.aeRemark = aeRemark;
    }
    
    @Column(name="ae_sign")
    public Long getAeSign() {
        return this.aeSign;
    }
    
    public void setAeSign(Long aeSign) {
        this.aeSign = aeSign;
    }
    
    @Column(name="qe_remark", length=18)
    public String getQeRemark() {
        return this.qeRemark;
    }
    
    public void setQeRemark(String qeRemark) {
        this.qeRemark = qeRemark;
    }
    
    @Column(name="qe_sign")
    public Long getQeSign() {
        return this.qeSign;
    }
    
    public void setQeSign(Long qeSign) {
        this.qeSign = qeSign;
    }
    
    @Column(name="other_fuction", length=20)
    public String getOtherFuction() {
        return this.otherFuction;
    }
    
    public void setOtherFuction(String otherFuction) {
        this.otherFuction = otherFuction;
    }
    
    @Column(name="of_sign")
    public Long getOfSign() {
        return this.ofSign;
    }
    
    public void setOfSign(Long ofSign) {
        this.ofSign = ofSign;
    }
    
    @Column(name="id_dept")
    public Long getIdDept() {
        return this.idDept;
    }
    
    public void setIdDept(Long idDept) {
        this.idDept = idDept;
    }




}


