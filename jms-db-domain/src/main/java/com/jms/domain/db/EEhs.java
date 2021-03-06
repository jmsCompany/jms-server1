package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


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
 * EEhs generated by hbm2java
 */
@Entity
@Table(name="e_ehs"
)
public class EEhs  implements java.io.Serializable {


     private Long idEhs;
     private EWorkCategoryDic EWorkCategoryDic;
     private Company company;
     private Users usersByOp;
     private ELevelDic ELevelDic;
     private EStatus EStatus;
     private Users usersBySupervisor;
     private Date creationTime;
     private String comments;
     private Set<EEhsD> EEhsDs = new HashSet<EEhsD>(0);

    public EEhs() {
    }

    public EEhs(EWorkCategoryDic EWorkCategoryDic, Company company, Users usersByOp, ELevelDic ELevelDic, EStatus EStatus, Users usersBySupervisor, Date creationTime, String comments, Set<EEhsD> EEhsDs) {
       this.EWorkCategoryDic = EWorkCategoryDic;
       this.company = company;
       this.usersByOp = usersByOp;
       this.ELevelDic = ELevelDic;
       this.EStatus = EStatus;
       this.usersBySupervisor = usersBySupervisor;
       this.creationTime = creationTime;
       this.comments = comments;
       this.EEhsDs = EEhsDs;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_ehs", unique=true, nullable=false)
    public Long getIdEhs() {
        return this.idEhs;
    }
    
    public void setIdEhs(Long idEhs) {
        this.idEhs = idEhs;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_work_category")
    public EWorkCategoryDic getEWorkCategoryDic() {
        return this.EWorkCategoryDic;
    }
    
    public void setEWorkCategoryDic(EWorkCategoryDic EWorkCategoryDic) {
        this.EWorkCategoryDic = EWorkCategoryDic;
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
    @JoinColumn(name="op")
    public Users getUsersByOp() {
        return this.usersByOp;
    }
    
    public void setUsersByOp(Users usersByOp) {
        this.usersByOp = usersByOp;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_level")
    public ELevelDic getELevelDic() {
        return this.ELevelDic;
    }
    
    public void setELevelDic(ELevelDic ELevelDic) {
        this.ELevelDic = ELevelDic;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_estatus")
    public EStatus getEStatus() {
        return this.EStatus;
    }
    
    public void setEStatus(EStatus EStatus) {
        this.EStatus = EStatus;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="supervisor")
    public Users getUsersBySupervisor() {
        return this.usersBySupervisor;
    }
    
    public void setUsersBySupervisor(Users usersBySupervisor) {
        this.usersBySupervisor = usersBySupervisor;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="creation_time", length=19)
    public Date getCreationTime() {
        return this.creationTime;
    }
    
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
    
    @Column(name="comments", length=1024)
    public String getComments() {
        return this.comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="EEhs")
    public Set<EEhsD> getEEhsDs() {
        return this.EEhsDs;
    }
    
    public void setEEhsDs(Set<EEhsD> EEhsDs) {
        this.EEhsDs = EEhsDs;
    }




}


