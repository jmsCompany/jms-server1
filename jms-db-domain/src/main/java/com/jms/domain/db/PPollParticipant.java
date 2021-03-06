package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


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
 * PPollParticipant generated by hbm2java
 */
@Entity
@Table(name="p_poll_participant"
)
public class PPollParticipant  implements java.io.Serializable {


     private Long id;
     private PPoll PPoll;
     private SysDicD sysDicD;
     private Users users;
     private String email;
     private String tel;
     private String comment;
     private Set<PVote> PVotes = new HashSet<PVote>(0);

    public PPollParticipant() {
    }

	
    public PPollParticipant(Long id) {
        this.id = id;
    }
    public PPollParticipant(Long id, PPoll PPoll, SysDicD sysDicD, Users users, String email, String tel, String comment, Set<PVote> PVotes) {
       this.id = id;
       this.PPoll = PPoll;
       this.sysDicD = sysDicD;
       this.users = users;
       this.email = email;
       this.tel = tel;
       this.comment = comment;
       this.PVotes = PVotes;
    }
   
     @Id 
    
    @Column(name="id", unique=true, nullable=false)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_poll")
    public PPoll getPPoll() {
        return this.PPoll;
    }
    
    public void setPPoll(PPoll PPoll) {
        this.PPoll = PPoll;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="status")
    public SysDicD getSysDicD() {
        return this.sysDicD;
    }
    
    public void setSysDicD(SysDicD sysDicD) {
        this.sysDicD = sysDicD;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="participant")
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    
    @Column(name="email", length=128)
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Column(name="tel", length=128)
    public String getTel() {
        return this.tel;
    }
    
    public void setTel(String tel) {
        this.tel = tel;
    }
    
    @Column(name="comment", length=1024)
    public String getComment() {
        return this.comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="PPollParticipant")
    public Set<PVote> getPVotes() {
        return this.PVotes;
    }
    
    public void setPVotes(Set<PVote> PVotes) {
        this.PVotes = PVotes;
    }




}


