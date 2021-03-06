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
 * QNcr generated by hbm2java
 */
@Entity
@Table(name="q_ncr"
)
public class QNcr  implements java.io.Serializable {


     private Long idNcr;
     private QNcrPic QNcrPicByPic2;
     private QNcrPic QNcrPicByPic1;
     private SMaterial SMaterial;
     private String ncrNo;
     private String des;
     private String who;
     private Date when1;
     private String where1;
     private String howMuch;
     private String how;
     private String what;
     private String emerAction;
     private Date actionDate;
     private String replyFormat;
     private Date deadline;
     private String response;
     private Date dare;

    public QNcr() {
    }

    public QNcr(QNcrPic QNcrPicByPic2, QNcrPic QNcrPicByPic1, SMaterial SMaterial, String ncrNo, String des, String who, Date when1, String where1, String howMuch, String how, String what, String emerAction, Date actionDate, String replyFormat, Date deadline, String response, Date dare) {
       this.QNcrPicByPic2 = QNcrPicByPic2;
       this.QNcrPicByPic1 = QNcrPicByPic1;
       this.SMaterial = SMaterial;
       this.ncrNo = ncrNo;
       this.des = des;
       this.who = who;
       this.when1 = when1;
       this.where1 = where1;
       this.howMuch = howMuch;
       this.how = how;
       this.what = what;
       this.emerAction = emerAction;
       this.actionDate = actionDate;
       this.replyFormat = replyFormat;
       this.deadline = deadline;
       this.response = response;
       this.dare = dare;
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
    @JoinColumn(name="pic2")
    public QNcrPic getQNcrPicByPic2() {
        return this.QNcrPicByPic2;
    }
    
    public void setQNcrPicByPic2(QNcrPic QNcrPicByPic2) {
        this.QNcrPicByPic2 = QNcrPicByPic2;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="pic1")
    public QNcrPic getQNcrPicByPic1() {
        return this.QNcrPicByPic1;
    }
    
    public void setQNcrPicByPic1(QNcrPic QNcrPicByPic1) {
        this.QNcrPicByPic1 = QNcrPicByPic1;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_material")
    public SMaterial getSMaterial() {
        return this.SMaterial;
    }
    
    public void setSMaterial(SMaterial SMaterial) {
        this.SMaterial = SMaterial;
    }
    
    @Column(name="ncr_no", length=20)
    public String getNcrNo() {
        return this.ncrNo;
    }
    
    public void setNcrNo(String ncrNo) {
        this.ncrNo = ncrNo;
    }
    
    @Column(name="des", length=1024)
    public String getDes() {
        return this.des;
    }
    
    public void setDes(String des) {
        this.des = des;
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
    
    @Column(name="emer_action", length=1024)
    public String getEmerAction() {
        return this.emerAction;
    }
    
    public void setEmerAction(String emerAction) {
        this.emerAction = emerAction;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="action_date", length=10)
    public Date getActionDate() {
        return this.actionDate;
    }
    
    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }
    
    @Column(name="reply_format", length=20)
    public String getReplyFormat() {
        return this.replyFormat;
    }
    
    public void setReplyFormat(String replyFormat) {
        this.replyFormat = replyFormat;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="deadline", length=10)
    public Date getDeadline() {
        return this.deadline;
    }
    
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
    
    @Column(name="response", length=20)
    public String getResponse() {
        return this.response;
    }
    
    public void setResponse(String response) {
        this.response = response;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="dare", length=10)
    public Date getDare() {
        return this.dare;
    }
    
    public void setDare(Date dare) {
        this.dare = dare;
    }




}


