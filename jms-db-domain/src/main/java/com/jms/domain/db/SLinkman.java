package com.jms.domain.db;
// Generated 2016-1-6 12:39:14 by Hibernate Tools 3.2.2.GA


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SLinkman generated by hbm2java
 */
@Entity
@Table(name="s_linkman"
    ,catalog="jms3"
)
public class SLinkman  implements java.io.Serializable {


     private SLinkmanId id;
     private SCompanyCo SCompanyCo;
     private SStatusDic SStatusDic;
     private SGenderDic SGenderDic;
     private String position;
     private Long phoneNo;
     private String EMail;
     private Long qq;
     private String weChat;
     private String wangwang;
     private String remark;

    public SLinkman() {
    }

	
    public SLinkman(SLinkmanId id, SCompanyCo SCompanyCo) {
        this.id = id;
        this.SCompanyCo = SCompanyCo;
    }
    public SLinkman(SLinkmanId id, SCompanyCo SCompanyCo, SStatusDic SStatusDic, SGenderDic SGenderDic, String position, Long phoneNo, String EMail, Long qq, String weChat, String wangwang, String remark) {
       this.id = id;
       this.SCompanyCo = SCompanyCo;
       this.SStatusDic = SStatusDic;
       this.SGenderDic = SGenderDic;
       this.position = position;
       this.phoneNo = phoneNo;
       this.EMail = EMail;
       this.qq = qq;
       this.weChat = weChat;
       this.wangwang = wangwang;
       this.remark = remark;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="id", column=@Column(name="id", nullable=false) ), 
        @AttributeOverride(name="name", column=@Column(name="name", nullable=false, length=20) ) } )
    public SLinkmanId getId() {
        return this.id;
    }
    
    public void setId(SLinkmanId id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id", nullable=false, insertable=false, updatable=false)
    public SCompanyCo getSCompanyCo() {
        return this.SCompanyCo;
    }
    
    public void setSCompanyCo(SCompanyCo SCompanyCo) {
        this.SCompanyCo = SCompanyCo;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="status")
    public SStatusDic getSStatusDic() {
        return this.SStatusDic;
    }
    
    public void setSStatusDic(SStatusDic SStatusDic) {
        this.SStatusDic = SStatusDic;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="gender")
    public SGenderDic getSGenderDic() {
        return this.SGenderDic;
    }
    
    public void setSGenderDic(SGenderDic SGenderDic) {
        this.SGenderDic = SGenderDic;
    }
    
    @Column(name="position", length=64)
    public String getPosition() {
        return this.position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    @Column(name="phone_no")
    public Long getPhoneNo() {
        return this.phoneNo;
    }
    
    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }
    
    @Column(name="e_mail", length=64)
    public String getEMail() {
        return this.EMail;
    }
    
    public void setEMail(String EMail) {
        this.EMail = EMail;
    }
    
    @Column(name="qq")
    public Long getQq() {
        return this.qq;
    }
    
    public void setQq(Long qq) {
        this.qq = qq;
    }
    
    @Column(name="we_chat", length=32)
    public String getWeChat() {
        return this.weChat;
    }
    
    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }
    
    @Column(name="wangwang", length=32)
    public String getWangwang() {
        return this.wangwang;
    }
    
    public void setWangwang(String wangwang) {
        this.wangwang = wangwang;
    }
    
    @Column(name="remark", length=1024)
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }




}


