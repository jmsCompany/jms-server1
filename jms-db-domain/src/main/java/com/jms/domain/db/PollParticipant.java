package com.jms.domain.db;
// Generated 2015-7-5 10:54:07 by Hibernate Tools 3.2.2.GA


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

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * PollParticipant generated by hbm2java
 */
@Audited
@AuditTable(catalog="jms_aud", value = "poll_participant")
@Entity
@Table(name="poll_participant"
    ,catalog="jms"
)
public class PollParticipant  implements java.io.Serializable {


     private Long idParticipant;
     private Poll poll;
     private SysDicD sysDicD;
     private Users users;
     private String email;
     private String tel;
     private String comment;
     private Set<Vote> votes = new HashSet<Vote>(0);

    public PollParticipant() {
    }

	
    public PollParticipant(Long idParticipant) {
        this.idParticipant = idParticipant;
    }
    public PollParticipant(Long idParticipant, Poll poll, SysDicD sysDicD, Users users, String email, String tel, String comment, Set<Vote> votes) {
       this.idParticipant = idParticipant;
       this.poll = poll;
       this.sysDicD = sysDicD;
       this.users = users;
       this.email = email;
       this.tel = tel;
       this.comment = comment;
       this.votes = votes;
    }
   
     @Id 
    
    @Column(name="ID_PARTICIPANT", unique=true, nullable=false)
    public Long getIdParticipant() {
        return this.idParticipant;
    }
    
    public void setIdParticipant(Long idParticipant) {
        this.idParticipant = idParticipant;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_POLL")
    public Poll getPoll() {
        return this.poll;
    }
    
    public void setPoll(Poll poll) {
        this.poll = poll;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="STATUS")
    public SysDicD getSysDicD() {
        return this.sysDicD;
    }
    
    public void setSysDicD(SysDicD sysDicD) {
        this.sysDicD = sysDicD;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PARTICIPANT")
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    
    @Column(name="EMAIL", length=128)
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Column(name="TEL", length=128)
    public String getTel() {
        return this.tel;
    }
    
    public void setTel(String tel) {
        this.tel = tel;
    }
    
    @Column(name="COMMENT", length=1024)
    public String getComment() {
        return this.comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="pollParticipant")
    public Set<Vote> getVotes() {
        return this.votes;
    }
    
    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }




}


