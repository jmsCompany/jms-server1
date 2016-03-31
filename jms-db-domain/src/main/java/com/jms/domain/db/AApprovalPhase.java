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
 * AApprovalPhase generated by hbm2java
 */
@Entity
@Table(name="a_approval_phase"
    ,catalog="jms5"
)
public class AApprovalPhase  implements java.io.Serializable {


     private Long idApprovalPhase;
     private AApprovalProcess AApprovalProcess;
     private Users users;
     private String name;
     private Long seq;
     private Set<AApprovalCc> AApprovalCcs = new HashSet<AApprovalCc>(0);
     private Set<AApplicationPhase> AApplicationPhases = new HashSet<AApplicationPhase>(0);

    public AApprovalPhase() {
    }

    public AApprovalPhase(AApprovalProcess AApprovalProcess, Users users, String name, Long seq, Set<AApprovalCc> AApprovalCcs, Set<AApplicationPhase> AApplicationPhases) {
       this.AApprovalProcess = AApprovalProcess;
       this.users = users;
       this.name = name;
       this.seq = seq;
       this.AApprovalCcs = AApprovalCcs;
       this.AApplicationPhases = AApplicationPhases;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_approval_phase", unique=true, nullable=false)
    public Long getIdApprovalPhase() {
        return this.idApprovalPhase;
    }
    
    public void setIdApprovalPhase(Long idApprovalPhase) {
        this.idApprovalPhase = idApprovalPhase;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_approval_process")
    public AApprovalProcess getAApprovalProcess() {
        return this.AApprovalProcess;
    }
    
    public void setAApprovalProcess(AApprovalProcess AApprovalProcess) {
        this.AApprovalProcess = AApprovalProcess;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="approver")
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    
    @Column(name="name", length=64)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="seq")
    public Long getSeq() {
        return this.seq;
    }
    
    public void setSeq(Long seq) {
        this.seq = seq;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="AApprovalPhase")
    public Set<AApprovalCc> getAApprovalCcs() {
        return this.AApprovalCcs;
    }
    
    public void setAApprovalCcs(Set<AApprovalCc> AApprovalCcs) {
        this.AApprovalCcs = AApprovalCcs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="AApprovalPhase")
    public Set<AApplicationPhase> getAApplicationPhases() {
        return this.AApplicationPhases;
    }
    
    public void setAApplicationPhases(Set<AApplicationPhase> AApplicationPhases) {
        this.AApplicationPhases = AApplicationPhases;
    }




}


