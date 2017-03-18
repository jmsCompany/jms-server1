package com.jms.domain.db;
// Generated 2016-8-23 16:01:55 by Hibernate Tools 3.2.2.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * QFileType generated by hbm2java
 */
@Entity
@Table(name="q_file_type"
)
public class QFileType  implements java.io.Serializable {


     private Long idFileType;
     private String type;
     private String des;
     private Long idCompany;
     private Set<QFileManagent> QFileManagents = new HashSet<QFileManagent>(0);

    public QFileType() {
    }

    public QFileType(String type, String des, Long idCompany, Set<QFileManagent> QFileManagents) {
       this.type = type;
       this.des = des;
       this.idCompany = idCompany;
       this.QFileManagents = QFileManagents;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_file_type", unique=true, nullable=false)
    public Long getIdFileType() {
        return this.idFileType;
    }
    
    public void setIdFileType(Long idFileType) {
        this.idFileType = idFileType;
    }
    
    @Column(name="type", length=256)
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Column(name="des", length=256)
    public String getDes() {
        return this.des;
    }
    
    public void setDes(String des) {
        this.des = des;
    }
    
    @Column(name="id_company")
    public Long getIdCompany() {
        return this.idCompany;
    }
    
    public void setIdCompany(Long idCompany) {
        this.idCompany = idCompany;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="QFileType")
    public Set<QFileManagent> getQFileManagents() {
        return this.QFileManagents;
    }
    
    public void setQFileManagents(Set<QFileManagent> QFileManagents) {
        this.QFileManagents = QFileManagents;
    }




}


