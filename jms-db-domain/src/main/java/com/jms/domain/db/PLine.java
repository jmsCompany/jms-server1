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
 * PLine generated by hbm2java
 */
@Entity
@Table(name="p_line"
    ,catalog="jms5"
)
public class PLine  implements java.io.Serializable {


     private Long idPline;
     private Company company;
     private PStatusDic PStatusDic;
     private Long pline;
     private Set<MMachine> MMachines = new HashSet<MMachine>(0);
     private Set<PRoutine> PRoutines = new HashSet<PRoutine>(0);

    public PLine() {
    }

    public PLine(Company company, PStatusDic PStatusDic, Long pline, Set<MMachine> MMachines, Set<PRoutine> PRoutines) {
       this.company = company;
       this.PStatusDic = PStatusDic;
       this.pline = pline;
       this.MMachines = MMachines;
       this.PRoutines = PRoutines;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_pline", unique=true, nullable=false)
    public Long getIdPline() {
        return this.idPline;
    }
    
    public void setIdPline(Long idPline) {
        this.idPline = idPline;
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
    @JoinColumn(name="id_pstatus")
    public PStatusDic getPStatusDic() {
        return this.PStatusDic;
    }
    
    public void setPStatusDic(PStatusDic PStatusDic) {
        this.PStatusDic = PStatusDic;
    }
    
    @Column(name="pline")
    public Long getPline() {
        return this.pline;
    }
    
    public void setPline(Long pline) {
        this.pline = pline;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="PLine")
    public Set<MMachine> getMMachines() {
        return this.MMachines;
    }
    
    public void setMMachines(Set<MMachine> MMachines) {
        this.MMachines = MMachines;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="PLine")
    public Set<PRoutine> getPRoutines() {
        return this.PRoutines;
    }
    
    public void setPRoutines(Set<PRoutine> PRoutines) {
        this.PRoutines = PRoutines;
    }




}


