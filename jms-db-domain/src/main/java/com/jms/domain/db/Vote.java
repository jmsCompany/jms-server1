package com.jms.domain.db;
// Generated 2015-7-5 10:54:07 by Hibernate Tools 3.2.2.GA


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
 * Vote generated by hbm2java
 */
@Entity
@Table(name="vote"
    ,catalog="jms"
)
public class Vote  implements java.io.Serializable {


     private VoteId id;
     private PollItems pollItems;
     private SysDicD sysDicD;
     private PollParticipant pollParticipant;

    public Vote() {
    }

	
    public Vote(VoteId id, PollItems pollItems, PollParticipant pollParticipant) {
        this.id = id;
        this.pollItems = pollItems;
        this.pollParticipant = pollParticipant;
    }
    public Vote(VoteId id, PollItems pollItems, SysDicD sysDicD, PollParticipant pollParticipant) {
       this.id = id;
       this.pollItems = pollItems;
       this.sysDicD = sysDicD;
       this.pollParticipant = pollParticipant;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idParticipant", column=@Column(name="ID_PARTICIPANT", nullable=false) ), 
        @AttributeOverride(name="idItem", column=@Column(name="ID_ITEM", nullable=false) ) } )
    public VoteId getId() {
        return this.id;
    }
    
    public void setId(VoteId id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_ITEM", nullable=false, insertable=false, updatable=false)
    public PollItems getPollItems() {
        return this.pollItems;
    }
    
    public void setPollItems(PollItems pollItems) {
        this.pollItems = pollItems;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DEGREE")
    public SysDicD getSysDicD() {
        return this.sysDicD;
    }
    
    public void setSysDicD(SysDicD sysDicD) {
        this.sysDicD = sysDicD;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_PARTICIPANT", nullable=false, insertable=false, updatable=false)
    public PollParticipant getPollParticipant() {
        return this.pollParticipant;
    }
    
    public void setPollParticipant(PollParticipant pollParticipant) {
        this.pollParticipant = pollParticipant;
    }




}


