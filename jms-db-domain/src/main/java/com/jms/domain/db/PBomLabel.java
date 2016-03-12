package com.jms.domain.db;
// Generated 2016-3-9 10:11:30 by Hibernate Tools 3.2.2.GA


import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PBomLabel generated by hbm2java
 */
@Entity
@Table(name="p_bom_label"
    ,catalog="jms4"
)
public class PBomLabel  implements java.io.Serializable {


     private Long idBomLabel;
     private Company company;
     private Users users;
     private PStatusDic PStatusDic;
     private Date creationTime;
     private Set<PBom> PBoms = new HashSet<PBom>(0);

    public PBomLabel() {
    }

	
    public PBomLabel(Long idBomLabel) {
        this.idBomLabel = idBomLabel;
    }
    public PBomLabel(Long idBomLabel, Company company, Users users, PStatusDic PStatusDic, Date creationTime, Set<PBom> PBoms) {
       this.idBomLabel = idBomLabel;
       this.company = company;
       this.users = users;
       this.PStatusDic = PStatusDic;
       this.creationTime = creationTime;
       this.PBoms = PBoms;
    }
   
     @Id 
    
    @Column(name="id_bom_label", unique=true, nullable=false)
    public Long getIdBomLabel() {
        return this.idBomLabel;
    }
    
    public void setIdBomLabel(Long idBomLabel) {
        this.idBomLabel = idBomLabel;
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
    @JoinColumn(name="creator")
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_pstatus")
    public PStatusDic getPStatusDic() {
        return this.PStatusDic;
    }
    
    public void setPStatusDic(PStatusDic PStatusDic) {
        this.PStatusDic = PStatusDic;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="creation_time", length=19)
    public Date getCreationTime() {
        return this.creationTime;
    }
    
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="PBomLabel")
    public Set<PBom> getPBoms() {
        return this.PBoms;
    }
    
    public void setPBoms(Set<PBom> PBoms) {
        this.PBoms = PBoms;
    }




}

