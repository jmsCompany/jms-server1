package com.jms.domain.db;
// Generated 2016-1-6 12:39:14 by Hibernate Tools 3.2.2.GA


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * AApprovalCc generated by hbm2java
 */
@Entity
@Table(name="a_approval_cc"
    ,catalog="jms3"
)
public class AApprovalCc  implements java.io.Serializable {


     private AApprovalCcId id;
     private Users users;
     private AApprovalPhase AApprovalPhase;
     private NotiMethod notiMethod;

    public AApprovalCc() {
    }

	
    public AApprovalCc(AApprovalCcId id, Users users, AApprovalPhase AApprovalPhase) {
        this.id = id;
        this.users = users;
        this.AApprovalPhase = AApprovalPhase;
    }
    public AApprovalCc(AApprovalCcId id, Users users, AApprovalPhase AApprovalPhase, NotiMethod notiMethod) {
       this.id = id;
       this.users = users;
       this.AApprovalPhase = AApprovalPhase;
       this.notiMethod = notiMethod;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idApprovalPhase", column=@Column(name="id_approval_phase", nullable=false) ), 
        @AttributeOverride(name="idUser", column=@Column(name="id_user", nullable=false) ) } )
    public AApprovalCcId getId() {
        return this.id;
    }
    
    public void setId(AApprovalCcId id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_user", nullable=false, insertable=false, updatable=false)
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_approval_phase", nullable=false, insertable=false, updatable=false)
    public AApprovalPhase getAApprovalPhase() {
        return this.AApprovalPhase;
    }
    
    public void setAApprovalPhase(AApprovalPhase AApprovalPhase) {
        this.AApprovalPhase = AApprovalPhase;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_noti_method")
    public NotiMethod getNotiMethod() {
        return this.notiMethod;
    }
    
    public void setNotiMethod(NotiMethod notiMethod) {
        this.notiMethod = notiMethod;
    }




}

