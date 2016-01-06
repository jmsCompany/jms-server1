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
 * SDo generated by hbm2java
 */
@Entity
@Table(name="s_do"
    ,catalog="jms3"
)
public class SDo  implements java.io.Serializable {


     private Long idDo;
     private Company company;
     private SSo SSo;
     private SCompanyCo SCompanyCo;
     private Users users;
     private String doNo;
     private Date dateDelivery;
     private Long expressNo;
     private Set<SDoMaterial> SDoMaterials = new HashSet<SDoMaterial>(0);

    public SDo() {
    }

    public SDo(Company company, SSo SSo, SCompanyCo SCompanyCo, Users users, String doNo, Date dateDelivery, Long expressNo, Set<SDoMaterial> SDoMaterials) {
       this.company = company;
       this.SSo = SSo;
       this.SCompanyCo = SCompanyCo;
       this.users = users;
       this.doNo = doNo;
       this.dateDelivery = dateDelivery;
       this.expressNo = expressNo;
       this.SDoMaterials = SDoMaterials;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_do", unique=true, nullable=false)
    public Long getIdDo() {
        return this.idDo;
    }
    
    public void setIdDo(Long idDo) {
        this.idDo = idDo;
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
    @JoinColumn(name="id_so")
    public SSo getSSo() {
        return this.SSo;
    }
    
    public void setSSo(SSo SSo) {
        this.SSo = SSo;
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
    @JoinColumn(name="emp_delivery")
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    
    @Column(name="do_no", length=64)
    public String getDoNo() {
        return this.doNo;
    }
    
    public void setDoNo(String doNo) {
        this.doNo = doNo;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="date_delivery", length=10)
    public Date getDateDelivery() {
        return this.dateDelivery;
    }
    
    public void setDateDelivery(Date dateDelivery) {
        this.dateDelivery = dateDelivery;
    }
    
    @Column(name="express_no")
    public Long getExpressNo() {
        return this.expressNo;
    }
    
    public void setExpressNo(Long expressNo) {
        this.expressNo = expressNo;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SDo")
    public Set<SDoMaterial> getSDoMaterials() {
        return this.SDoMaterials;
    }
    
    public void setSDoMaterials(Set<SDoMaterial> SDoMaterials) {
        this.SDoMaterials = SDoMaterials;
    }




}


