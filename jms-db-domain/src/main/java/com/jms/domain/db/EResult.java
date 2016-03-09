package com.jms.domain.db;
// Generated 2016-3-9 10:11:30 by Hibernate Tools 3.2.2.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * EResult generated by hbm2java
 */
@Entity
@Table(name="e_result"
    ,catalog="jms4"
)
public class EResult  implements java.io.Serializable {


     private Long idResult;
     private String des;
     private Set<EEhsD> EEhsDs = new HashSet<EEhsD>(0);

    public EResult() {
    }

	
    public EResult(Long idResult) {
        this.idResult = idResult;
    }
    public EResult(Long idResult, String des, Set<EEhsD> EEhsDs) {
       this.idResult = idResult;
       this.des = des;
       this.EEhsDs = EEhsDs;
    }
   
     @Id 
    
    @Column(name="id_result", unique=true, nullable=false)
    public Long getIdResult() {
        return this.idResult;
    }
    
    public void setIdResult(Long idResult) {
        this.idResult = idResult;
    }
    
    @Column(name="des", length=20)
    public String getDes() {
        return this.des;
    }
    
    public void setDes(String des) {
        this.des = des;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="EResult")
    public Set<EEhsD> getEEhsDs() {
        return this.EEhsDs;
    }
    
    public void setEEhsDs(Set<EEhsD> EEhsDs) {
        this.EEhsDs = EEhsDs;
    }




}


