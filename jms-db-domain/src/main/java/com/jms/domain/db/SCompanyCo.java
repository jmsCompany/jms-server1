package com.jms.domain.db;
// Generated 2016-3-9 10:11:30 by Hibernate Tools 3.2.2.GA


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

/**
 * SCompanyCo generated by hbm2java
 */
@Entity
@Table(name="s_company_co"
    ,catalog="jms4"
)
public class SCompanyCo  implements java.io.Serializable {


     private Long id;
     private STypeDic STypeDic;
     private SStatusDic SStatusDic;
     private SCountryDic SCountryDic;
     private Users users;
     private SLevelDic SLevelDic;
     private STermDic STermDicByFreightTerm;
     private STermDic STermDicByPaymentTerm;
     private String code;
     private String shartName;
     private String name;
     private String tel;
     private String fax;
     private String addressAct;
     private String addressReg;
     private String artificialPerson;
     private Long taxNo;
     private String bank;
     private Long bankAccNo;
     private String url;
     private String reamrk;
     private String autoRemark;
     private Set<SMtfResidual> SMtfResiduals = new HashSet<SMtfResidual>(0);
     private Set<SPo> SPos = new HashSet<SPo>(0);
     private Set<SLinkman> SLinkmans = new HashSet<SLinkman>(0);
     private Set<SSo> SSos = new HashSet<SSo>(0);
     private Set<SCompanyCoAttachment> SCompanyCoAttachments = new HashSet<SCompanyCoAttachment>(0);

    public SCompanyCo() {
    }

    public SCompanyCo(STypeDic STypeDic, SStatusDic SStatusDic, SCountryDic SCountryDic, Users users, SLevelDic SLevelDic, STermDic STermDicByFreightTerm, STermDic STermDicByPaymentTerm, String code, String shartName, String name, String tel, String fax, String addressAct, String addressReg, String artificialPerson, Long taxNo, String bank, Long bankAccNo, String url, String reamrk, String autoRemark, Set<SMtfResidual> SMtfResiduals, Set<SPo> SPos, Set<SLinkman> SLinkmans, Set<SSo> SSos, Set<SCompanyCoAttachment> SCompanyCoAttachments) {
       this.STypeDic = STypeDic;
       this.SStatusDic = SStatusDic;
       this.SCountryDic = SCountryDic;
       this.users = users;
       this.SLevelDic = SLevelDic;
       this.STermDicByFreightTerm = STermDicByFreightTerm;
       this.STermDicByPaymentTerm = STermDicByPaymentTerm;
       this.code = code;
       this.shartName = shartName;
       this.name = name;
       this.tel = tel;
       this.fax = fax;
       this.addressAct = addressAct;
       this.addressReg = addressReg;
       this.artificialPerson = artificialPerson;
       this.taxNo = taxNo;
       this.bank = bank;
       this.bankAccNo = bankAccNo;
       this.url = url;
       this.reamrk = reamrk;
       this.autoRemark = autoRemark;
       this.SMtfResiduals = SMtfResiduals;
       this.SPos = SPos;
       this.SLinkmans = SLinkmans;
       this.SSos = SSos;
       this.SCompanyCoAttachments = SCompanyCoAttachments;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="type")
    public STypeDic getSTypeDic() {
        return this.STypeDic;
    }
    
    public void setSTypeDic(STypeDic STypeDic) {
        this.STypeDic = STypeDic;
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
    @JoinColumn(name="country")
    public SCountryDic getSCountryDic() {
        return this.SCountryDic;
    }
    
    public void setSCountryDic(SCountryDic SCountryDic) {
        this.SCountryDic = SCountryDic;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="audit_by")
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="level")
    public SLevelDic getSLevelDic() {
        return this.SLevelDic;
    }
    
    public void setSLevelDic(SLevelDic SLevelDic) {
        this.SLevelDic = SLevelDic;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="freight_term")
    public STermDic getSTermDicByFreightTerm() {
        return this.STermDicByFreightTerm;
    }
    
    public void setSTermDicByFreightTerm(STermDic STermDicByFreightTerm) {
        this.STermDicByFreightTerm = STermDicByFreightTerm;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="payment_term")
    public STermDic getSTermDicByPaymentTerm() {
        return this.STermDicByPaymentTerm;
    }
    
    public void setSTermDicByPaymentTerm(STermDic STermDicByPaymentTerm) {
        this.STermDicByPaymentTerm = STermDicByPaymentTerm;
    }
    
    @Column(name="code", length=20)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    @Column(name="shart_name", length=20)
    public String getShartName() {
        return this.shartName;
    }
    
    public void setShartName(String shartName) {
        this.shartName = shartName;
    }
    
    @Column(name="name", length=64)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="tel", length=20)
    public String getTel() {
        return this.tel;
    }
    
    public void setTel(String tel) {
        this.tel = tel;
    }
    
    @Column(name="fax", length=20)
    public String getFax() {
        return this.fax;
    }
    
    public void setFax(String fax) {
        this.fax = fax;
    }
    
    @Column(name="address_act", length=128)
    public String getAddressAct() {
        return this.addressAct;
    }
    
    public void setAddressAct(String addressAct) {
        this.addressAct = addressAct;
    }
    
    @Column(name="address_reg", length=128)
    public String getAddressReg() {
        return this.addressReg;
    }
    
    public void setAddressReg(String addressReg) {
        this.addressReg = addressReg;
    }
    
    @Column(name="artificial_person", length=20)
    public String getArtificialPerson() {
        return this.artificialPerson;
    }
    
    public void setArtificialPerson(String artificialPerson) {
        this.artificialPerson = artificialPerson;
    }
    
    @Column(name="tax_no")
    public Long getTaxNo() {
        return this.taxNo;
    }
    
    public void setTaxNo(Long taxNo) {
        this.taxNo = taxNo;
    }
    
    @Column(name="bank", length=64)
    public String getBank() {
        return this.bank;
    }
    
    public void setBank(String bank) {
        this.bank = bank;
    }
    
    @Column(name="bank_acc_no")
    public Long getBankAccNo() {
        return this.bankAccNo;
    }
    
    public void setBankAccNo(Long bankAccNo) {
        this.bankAccNo = bankAccNo;
    }
    
    @Column(name="url", length=512)
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Column(name="reamrk", length=1024)
    public String getReamrk() {
        return this.reamrk;
    }
    
    public void setReamrk(String reamrk) {
        this.reamrk = reamrk;
    }
    
    @Column(name="auto_remark", length=1024)
    public String getAutoRemark() {
        return this.autoRemark;
    }
    
    public void setAutoRemark(String autoRemark) {
        this.autoRemark = autoRemark;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SCompanyCo")
    public Set<SMtfResidual> getSMtfResiduals() {
        return this.SMtfResiduals;
    }
    
    public void setSMtfResiduals(Set<SMtfResidual> SMtfResiduals) {
        this.SMtfResiduals = SMtfResiduals;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SCompanyCo")
    public Set<SPo> getSPos() {
        return this.SPos;
    }
    
    public void setSPos(Set<SPo> SPos) {
        this.SPos = SPos;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SCompanyCo")
    public Set<SLinkman> getSLinkmans() {
        return this.SLinkmans;
    }
    
    public void setSLinkmans(Set<SLinkman> SLinkmans) {
        this.SLinkmans = SLinkmans;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SCompanyCo")
    public Set<SSo> getSSos() {
        return this.SSos;
    }
    
    public void setSSos(Set<SSo> SSos) {
        this.SSos = SSos;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SCompanyCo")
    public Set<SCompanyCoAttachment> getSCompanyCoAttachments() {
        return this.SCompanyCoAttachments;
    }
    
    public void setSCompanyCoAttachments(Set<SCompanyCoAttachment> SCompanyCoAttachments) {
        this.SCompanyCoAttachments = SCompanyCoAttachments;
    }




}


