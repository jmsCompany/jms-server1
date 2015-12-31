package com.jms.domain.db;
// Generated 2015-12-31 19:48:00 by Hibernate Tools 3.2.2.GA


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
 * PPollItems generated by hbm2java
 */
@Entity
@Table(name="p_poll_items"
    ,catalog="jms3"
)
public class PPollItems  implements java.io.Serializable {


     private Long idItem;
     private PPoll PPoll;
     private Document document;
     private Long seq;
     private String item;
     private Set<PVote> PVotes = new HashSet<PVote>(0);

    public PPollItems() {
    }

    public PPollItems(PPoll PPoll, Document document, Long seq, String item, Set<PVote> PVotes) {
       this.PPoll = PPoll;
       this.document = document;
       this.seq = seq;
       this.item = item;
       this.PVotes = PVotes;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_item", unique=true, nullable=false)
    public Long getIdItem() {
        return this.idItem;
    }
    
    public void setIdItem(Long idItem) {
        this.idItem = idItem;
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
    @JoinColumn(name="pic")
    public Document getDocument() {
        return this.document;
    }
    
    public void setDocument(Document document) {
        this.document = document;
    }
    
    @Column(name="seq")
    public Long getSeq() {
        return this.seq;
    }
    
    public void setSeq(Long seq) {
        this.seq = seq;
    }
    
    @Column(name="item", length=512)
    public String getItem() {
        return this.item;
    }
    
    public void setItem(String item) {
        this.item = item;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="PPollItems")
    public Set<PVote> getPVotes() {
        return this.PVotes;
    }
    
    public void setPVotes(Set<PVote> PVotes) {
        this.PVotes = PVotes;
    }




}


