package com.jms.domain.db;
// Generated 2016-1-6 12:39:14 by Hibernate Tools 3.2.2.GA


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
 * SIncome generated by hbm2java
 */
@Entity
@Table(name="s_income"
    ,catalog="jms3"
)
public class SIncome  implements java.io.Serializable {


     private Long idR;
     private Company company;
     private SCompanyCo SCompanyCo;
     private SPo SPo;
     private Users users;
     private String RNo;
     private Date dataReceiving;
     private Long expressNo;
     private Set<SIncomeMaterial> SIncomeMaterials = new HashSet<SIncomeMaterial>(0);

    public SIncome() {
    }

    public SIncome(Company company, SCompanyCo SCompanyCo, SPo SPo, Users users, String RNo, Date dataReceiving, Long expressNo, Set<SIncomeMaterial> SIncomeMaterials) {
       this.company = company;
       this.SCompanyCo = SCompanyCo;
       this.SPo = SPo;
       this.users = users;
       this.RNo = RNo;
       this.dataReceiving = dataReceiving;
       this.expressNo = expressNo;
       this.SIncomeMaterials = SIncomeMaterials;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_r", unique=true, nullable=false)
    public Long getIdR() {
        return this.idR;
    }
    
    public void setIdR(Long idR) {
        this.idR = idR;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_company")
    public Company getCompany() {
        return this.company;
    }
    
    public void setCompany(Company company) {
        this.company = company;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="express_company")
    public SCompanyCo getSCompanyCo() {
        return this.SCompanyCo;
    }
    
    public void setSCompanyCo(SCompanyCo SCompanyCo) {
        this.SCompanyCo = SCompanyCo;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_po")
    public SPo getSPo() {
        return this.SPo;
    }
    
    public void setSPo(SPo SPo) {
        this.SPo = SPo;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="emp_receiving")
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    
    @Column(name="r_no", length=20)
    public String getRNo() {
        return this.RNo;
    }
    
    public void setRNo(String RNo) {
        this.RNo = RNo;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="data_receiving", length=10)
    public Date getDataReceiving() {
        return this.dataReceiving;
    }
    
    public void setDataReceiving(Date dataReceiving) {
        this.dataReceiving = dataReceiving;
    }
    
    @Column(name="express_no")
    public Long getExpressNo() {
        return this.expressNo;
    }
    
    public void setExpressNo(Long expressNo) {
        this.expressNo = expressNo;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SIncome")
    public Set<SIncomeMaterial> getSIncomeMaterials() {
        return this.SIncomeMaterials;
    }
    
    public void setSIncomeMaterials(Set<SIncomeMaterial> SIncomeMaterials) {
        this.SIncomeMaterials = SIncomeMaterials;
    }




}


