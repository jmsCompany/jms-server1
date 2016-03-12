package com.jms.domain.db;
// Generated 2016-3-9 10:11:30 by Hibernate Tools 3.2.2.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * QAttType generated by hbm2java
 */
@Entity
@Table(name="q_att_type"
    ,catalog="jms4"
)
public class QAttType  implements java.io.Serializable {


     private Long idAttType;
     private Company company;
     private String des;
     private Set<QAtt> QAtts = new HashSet<QAtt>(0);

    public QAttType() {
    }

	
    public QAttType(Long idAttType) {
        this.idAttType = idAttType;
    }
    public QAttType(Long idAttType, Company company, String des, Set<QAtt> QAtts) {
       this.idAttType = idAttType;
       this.company = company;
       this.des = des;
       this.QAtts = QAtts;
    }
   
     @Id 
    
    @Column(name="id_att_type", unique=true, nullable=false)
    public Long getIdAttType() {
        return this.idAttType;
    }
    
    public void setIdAttType(Long idAttType) {
        this.idAttType = idAttType;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_company")
    public Company getCompany() {
        return this.company;
    }
    
    public void setCompany(Company company) {
        this.company = company;
    }
    
    @Column(name="des", length=64)
    public String getDes() {
        return this.des;
    }
    
    public void setDes(String des) {
        this.des = des;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="QAttType")
    public Set<QAtt> getQAtts() {
        return this.QAtts;
    }
    
    public void setQAtts(Set<QAtt> QAtts) {
        this.QAtts = QAtts;
    }




}

