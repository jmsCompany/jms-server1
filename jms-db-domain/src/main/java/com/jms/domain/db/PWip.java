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
 * PWip generated by hbm2java
 */
@Entity
@Table(name="p_wip"
    ,catalog="jms4"
)
public class PWip  implements java.io.Serializable {


     private Long idWip;
     private Company company;
     private PStatusDic PStatusDic;
     private String wip;
     private Set<MMachine> MMachines = new HashSet<MMachine>(0);

    public PWip() {
    }

	
    public PWip(Long idWip) {
        this.idWip = idWip;
    }
    public PWip(Long idWip, Company company, PStatusDic PStatusDic, String wip, Set<MMachine> MMachines) {
       this.idWip = idWip;
       this.company = company;
       this.PStatusDic = PStatusDic;
       this.wip = wip;
       this.MMachines = MMachines;
    }
   
     @Id 
    
    @Column(name="id_wip", unique=true, nullable=false)
    public Long getIdWip() {
        return this.idWip;
    }
    
    public void setIdWip(Long idWip) {
        this.idWip = idWip;
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
    
    @Column(name="wip", length=20)
    public String getWip() {
        return this.wip;
    }
    
    public void setWip(String wip) {
        this.wip = wip;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="PWip")
    public Set<MMachine> getMMachines() {
        return this.MMachines;
    }
    
    public void setMMachines(Set<MMachine> MMachines) {
        this.MMachines = MMachines;
    }




}


