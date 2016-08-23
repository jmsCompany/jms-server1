package com.jms.domain.db;
// Generated 2016-8-23 14:57:20 by Hibernate Tools 3.2.2.GA


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
 * QNcr2 generated by hbm2java
 */
@Entity
@Table(name="q_ncr2"
    ,catalog="jms5"
)
public class QNcr2  implements java.io.Serializable {


     private Long idNcr;
     private QG8d QG8d;
     private QCar QCar;
     private String ncrNo;
     private String who;
     private Date when1;
     private String where1;
     private String howMuch;
     private String how;
     private String what;
     private String pic1OrgFilename;
     private String pic1Filename;
     private String pic2OrgFilename;
     private String picFilename;
     private String emergencyAction;
     private Date actionDate;
     private String car;
     private String q8d;
     private Date deadline;
     private String respnose;
     private Date date;
     private Set<QNoProcess> QNoProcesses = new HashSet<QNoProcess>(0);

    public QNcr2() {
    }

    public QNcr2(QG8d QG8d, QCar QCar, String ncrNo, String who, Date when1, String where1, String howMuch, String how, String what, String pic1OrgFilename, String pic1Filename, String pic2OrgFilename, String picFilename, String emergencyAction, Date actionDate, String car, String q8d, Date deadline, String respnose, Date date, Set<QNoProcess> QNoProcesses) {
       this.QG8d = QG8d;
       this.QCar = QCar;
       this.ncrNo = ncrNo;
       this.who = who;
       this.when1 = when1;
       this.where1 = where1;
       this.howMuch = howMuch;
       this.how = how;
       this.what = what;
       this.pic1OrgFilename = pic1OrgFilename;
       this.pic1Filename = pic1Filename;
       this.pic2OrgFilename = pic2OrgFilename;
       this.picFilename = picFilename;
       this.emergencyAction = emergencyAction;
       this.actionDate = actionDate;
       this.car = car;
       this.q8d = q8d;
       this.deadline = deadline;
       this.respnose = respnose;
       this.date = date;
       this.QNoProcesses = QNoProcesses;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_ncr", unique=true, nullable=false)
    public Long getIdNcr() {
        return this.idNcr;
    }
    
    public void setIdNcr(Long idNcr) {
        this.idNcr = idNcr;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_g8d")
    public QG8d getQG8d() {
        return this.QG8d;
    }
    
    public void setQG8d(QG8d QG8d) {
        this.QG8d = QG8d;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_car")
    public QCar getQCar() {
        return this.QCar;
    }
    
    public void setQCar(QCar QCar) {
        this.QCar = QCar;
    }
    
    @Column(name="ncr_no", length=20)
    public String getNcrNo() {
        return this.ncrNo;
    }
    
    public void setNcrNo(String ncrNo) {
        this.ncrNo = ncrNo;
    }
    
    @Column(name="who", length=20)
    public String getWho() {
        return this.who;
    }
    
    public void setWho(String who) {
        this.who = who;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="when1", length=10)
    public Date getWhen1() {
        return this.when1;
    }
    
    public void setWhen1(Date when1) {
        this.when1 = when1;
    }
    
    @Column(name="where1", length=64)
    public String getWhere1() {
        return this.where1;
    }
    
    public void setWhere1(String where1) {
        this.where1 = where1;
    }
    
    @Column(name="how_much", length=20)
    public String getHowMuch() {
        return this.howMuch;
    }
    
    public void setHowMuch(String howMuch) {
        this.howMuch = howMuch;
    }
    
    @Column(name="how", length=512)
    public String getHow() {
        return this.how;
    }
    
    public void setHow(String how) {
        this.how = how;
    }
    
    @Column(name="what", length=1024)
    public String getWhat() {
        return this.what;
    }
    
    public void setWhat(String what) {
        this.what = what;
    }
    
    @Column(name="pic1_org_filename", length=256)
    public String getPic1OrgFilename() {
        return this.pic1OrgFilename;
    }
    
    public void setPic1OrgFilename(String pic1OrgFilename) {
        this.pic1OrgFilename = pic1OrgFilename;
    }
    
    @Column(name="pic1_filename", length=256)
    public String getPic1Filename() {
        return this.pic1Filename;
    }
    
    public void setPic1Filename(String pic1Filename) {
        this.pic1Filename = pic1Filename;
    }
    
    @Column(name="pic2_org_filename", length=256)
    public String getPic2OrgFilename() {
        return this.pic2OrgFilename;
    }
    
    public void setPic2OrgFilename(String pic2OrgFilename) {
        this.pic2OrgFilename = pic2OrgFilename;
    }
    
    @Column(name="pic_filename", length=256)
    public String getPicFilename() {
        return this.picFilename;
    }
    
    public void setPicFilename(String picFilename) {
        this.picFilename = picFilename;
    }
    
    @Column(name="emergency_action", length=1024)
    public String getEmergencyAction() {
        return this.emergencyAction;
    }
    
    public void setEmergencyAction(String emergencyAction) {
        this.emergencyAction = emergencyAction;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="action_date", length=10)
    public Date getActionDate() {
        return this.actionDate;
    }
    
    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }
    
    @Column(name="car", length=20)
    public String getCar() {
        return this.car;
    }
    
    public void setCar(String car) {
        this.car = car;
    }
    
    @Column(name="q8d", length=20)
    public String getQ8d() {
        return this.q8d;
    }
    
    public void setQ8d(String q8d) {
        this.q8d = q8d;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="deadline", length=10)
    public Date getDeadline() {
        return this.deadline;
    }
    
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
    
    @Column(name="respnose", length=20)
    public String getRespnose() {
        return this.respnose;
    }
    
    public void setRespnose(String respnose) {
        this.respnose = respnose;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="date", length=10)
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="QNcr2")
    public Set<QNoProcess> getQNoProcesses() {
        return this.QNoProcesses;
    }
    
    public void setQNoProcesses(Set<QNoProcess> QNoProcesses) {
        this.QNoProcesses = QNoProcesses;
    }




}

