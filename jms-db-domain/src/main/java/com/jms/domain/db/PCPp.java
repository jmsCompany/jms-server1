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
 * PCPp generated by hbm2java
 */
@Entity
@Table(name="p_c_pp"
    ,catalog="jms5"
)
public class PCPp  implements java.io.Serializable {


     private Long idCPp;
     private Company company;
     private MMachine MMachine;
     private PRoutineD PRoutineD;
     private PShiftPlanD PShiftPlanD;
     private Users users;
     private PWo PWo;
     private Long CPpCode;
     private Long qty;
     private Date planSt;
     private Date planFt;
     private Set<PActualSetup> PActualSetups = new HashSet<PActualSetup>(0);
     private Set<PCheckPlan> PCheckPlans = new HashSet<PCheckPlan>(0);
     private Set<PMr> PMrs = new HashSet<PMr>(0);

    public PCPp() {
    }

    public PCPp(Company company, MMachine MMachine, PRoutineD PRoutineD, PShiftPlanD PShiftPlanD, Users users, PWo PWo, Long CPpCode, Long qty, Date planSt, Date planFt, Set<PActualSetup> PActualSetups, Set<PCheckPlan> PCheckPlans, Set<PMr> PMrs) {
       this.company = company;
       this.MMachine = MMachine;
       this.PRoutineD = PRoutineD;
       this.PShiftPlanD = PShiftPlanD;
       this.users = users;
       this.PWo = PWo;
       this.CPpCode = CPpCode;
       this.qty = qty;
       this.planSt = planSt;
       this.planFt = planFt;
       this.PActualSetups = PActualSetups;
       this.PCheckPlans = PCheckPlans;
       this.PMrs = PMrs;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_c_pp", unique=true, nullable=false)
    public Long getIdCPp() {
        return this.idCPp;
    }
    
    public void setIdCPp(Long idCPp) {
        this.idCPp = idCPp;
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
    @JoinColumn(name="id_machine")
    public MMachine getMMachine() {
        return this.MMachine;
    }
    
    public void setMMachine(MMachine MMachine) {
        this.MMachine = MMachine;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_routine_d")
    public PRoutineD getPRoutineD() {
        return this.PRoutineD;
    }
    
    public void setPRoutineD(PRoutineD PRoutineD) {
        this.PRoutineD = PRoutineD;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_shift_d")
    public PShiftPlanD getPShiftPlanD() {
        return this.PShiftPlanD;
    }
    
    public void setPShiftPlanD(PShiftPlanD PShiftPlanD) {
        this.PShiftPlanD = PShiftPlanD;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="op")
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_wo")
    public PWo getPWo() {
        return this.PWo;
    }
    
    public void setPWo(PWo PWo) {
        this.PWo = PWo;
    }
    
    @Column(name="c_pp_code")
    public Long getCPpCode() {
        return this.CPpCode;
    }
    
    public void setCPpCode(Long CPpCode) {
        this.CPpCode = CPpCode;
    }
    
    @Column(name="qty")
    public Long getQty() {
        return this.qty;
    }
    
    public void setQty(Long qty) {
        this.qty = qty;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="plan_st", length=10)
    public Date getPlanSt() {
        return this.planSt;
    }
    
    public void setPlanSt(Date planSt) {
        this.planSt = planSt;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="plan_ft", length=10)
    public Date getPlanFt() {
        return this.planFt;
    }
    
    public void setPlanFt(Date planFt) {
        this.planFt = planFt;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="PCPp")
    public Set<PActualSetup> getPActualSetups() {
        return this.PActualSetups;
    }
    
    public void setPActualSetups(Set<PActualSetup> PActualSetups) {
        this.PActualSetups = PActualSetups;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="PCPp")
    public Set<PCheckPlan> getPCheckPlans() {
        return this.PCheckPlans;
    }
    
    public void setPCheckPlans(Set<PCheckPlan> PCheckPlans) {
        this.PCheckPlans = PCheckPlans;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="PCPp")
    public Set<PMr> getPMrs() {
        return this.PMrs;
    }
    
    public void setPMrs(Set<PMr> PMrs) {
        this.PMrs = PMrs;
    }




}


