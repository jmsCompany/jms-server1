package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


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
 * QDisposalType generated by hbm2java
 */
@Entity
@Table(name="q_disposal_type"
)
public class QDisposalType  implements java.io.Serializable {


     private Long idDisposalType;
     private Company company;
     private QStatusDic QStatusDic;
     private String des;
     private Set<QNcpDisposal> QNcpDisposals = new HashSet<QNcpDisposal>(0);

    public QDisposalType() {
    }

    public QDisposalType(Company company, QStatusDic QStatusDic, String des, Set<QNcpDisposal> QNcpDisposals) {
       this.company = company;
       this.QStatusDic = QStatusDic;
       this.des = des;
       this.QNcpDisposals = QNcpDisposals;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_disposal_type", unique=true, nullable=false)
    public Long getIdDisposalType() {
        return this.idDisposalType;
    }
    
    public void setIdDisposalType(Long idDisposalType) {
        this.idDisposalType = idDisposalType;
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
    @JoinColumn(name="id_qstatus")
    public QStatusDic getQStatusDic() {
        return this.QStatusDic;
    }
    
    public void setQStatusDic(QStatusDic QStatusDic) {
        this.QStatusDic = QStatusDic;
    }
    
    @Column(name="des", length=20)
    public String getDes() {
        return this.des;
    }
    
    public void setDes(String des) {
        this.des = des;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="QDisposalType")
    public Set<QNcpDisposal> getQNcpDisposals() {
        return this.QNcpDisposals;
    }
    
    public void setQNcpDisposals(Set<QNcpDisposal> QNcpDisposals) {
        this.QNcpDisposals = QNcpDisposals;
    }




}


