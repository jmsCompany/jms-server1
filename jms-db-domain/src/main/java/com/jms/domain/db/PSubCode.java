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
 * PSubCode generated by hbm2java
 */
@Entity
@Table(name="p_sub_code"
    ,catalog="jms4"
)
public class PSubCode  implements java.io.Serializable {


     private Long idSubCode;
     private MDept MDept;
     private PStopsCode PStopsCode;
     private String subCode;
     private String subDes;
     private Set<PUnplannedStops> PUnplannedStopses = new HashSet<PUnplannedStops>(0);
     private Set<PStopsPlan> PStopsPlans = new HashSet<PStopsPlan>(0);

    public PSubCode() {
    }

	
    public PSubCode(Long idSubCode) {
        this.idSubCode = idSubCode;
    }
    public PSubCode(Long idSubCode, MDept MDept, PStopsCode PStopsCode, String subCode, String subDes, Set<PUnplannedStops> PUnplannedStopses, Set<PStopsPlan> PStopsPlans) {
       this.idSubCode = idSubCode;
       this.MDept = MDept;
       this.PStopsCode = PStopsCode;
       this.subCode = subCode;
       this.subDes = subDes;
       this.PUnplannedStopses = PUnplannedStopses;
       this.PStopsPlans = PStopsPlans;
    }
   
     @Id 
    
    @Column(name="id_sub_code", unique=true, nullable=false)
    public Long getIdSubCode() {
        return this.idSubCode;
    }
    
    public void setIdSubCode(Long idSubCode) {
        this.idSubCode = idSubCode;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_dept")
    public MDept getMDept() {
        return this.MDept;
    }
    
    public void setMDept(MDept MDept) {
        this.MDept = MDept;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_stops_code")
    public PStopsCode getPStopsCode() {
        return this.PStopsCode;
    }
    
    public void setPStopsCode(PStopsCode PStopsCode) {
        this.PStopsCode = PStopsCode;
    }
    
    @Column(name="sub_code", length=20)
    public String getSubCode() {
        return this.subCode;
    }
    
    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }
    
    @Column(name="sub_des", length=1024)
    public String getSubDes() {
        return this.subDes;
    }
    
    public void setSubDes(String subDes) {
        this.subDes = subDes;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="PSubCode")
    public Set<PUnplannedStops> getPUnplannedStopses() {
        return this.PUnplannedStopses;
    }
    
    public void setPUnplannedStopses(Set<PUnplannedStops> PUnplannedStopses) {
        this.PUnplannedStopses = PUnplannedStopses;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="PSubCode")
    public Set<PStopsPlan> getPStopsPlans() {
        return this.PStopsPlans;
    }
    
    public void setPStopsPlans(Set<PStopsPlan> PStopsPlans) {
        this.PStopsPlans = PStopsPlans;
    }




}

