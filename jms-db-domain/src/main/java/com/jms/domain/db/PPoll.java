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
 * PPoll generated by hbm2java
 */
@Entity
@Table(name="p_poll"
    ,catalog="jms5"
)
public class PPoll  implements java.io.Serializable {


     private Long idPoll;
     private SysDicD sysDicD;
     private Users users;
     private String title;
     private String description;
     private Long maxItems;
     private Date creationTime;
     private Date endTime;
     private Long isAnonymous;
     private Long showResults;
     private String conclusion;
     private String url;
     private Set<PPollItems> PPollItemses = new HashSet<PPollItems>(0);
     private Set<PPollParticipant> PPollParticipants = new HashSet<PPollParticipant>(0);

    public PPoll() {
    }

    public PPoll(SysDicD sysDicD, Users users, String title, String description, Long maxItems, Date creationTime, Date endTime, Long isAnonymous, Long showResults, String conclusion, String url, Set<PPollItems> PPollItemses, Set<PPollParticipant> PPollParticipants) {
       this.sysDicD = sysDicD;
       this.users = users;
       this.title = title;
       this.description = description;
       this.maxItems = maxItems;
       this.creationTime = creationTime;
       this.endTime = endTime;
       this.isAnonymous = isAnonymous;
       this.showResults = showResults;
       this.conclusion = conclusion;
       this.url = url;
       this.PPollItemses = PPollItemses;
       this.PPollParticipants = PPollParticipants;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_poll", unique=true, nullable=false)
    public Long getIdPoll() {
        return this.idPoll;
    }
    
    public void setIdPoll(Long idPoll) {
        this.idPoll = idPoll;
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
    @JoinColumn(name="creator")
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    
    @Column(name="title", length=64)
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    @Column(name="description", length=1024)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="max_items")
    public Long getMaxItems() {
        return this.maxItems;
    }
    
    public void setMaxItems(Long maxItems) {
        this.maxItems = maxItems;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="creation_time", length=19)
    public Date getCreationTime() {
        return this.creationTime;
    }
    
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="end_time", length=19)
    public Date getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    @Column(name="is_anonymous")
    public Long getIsAnonymous() {
        return this.isAnonymous;
    }
    
    public void setIsAnonymous(Long isAnonymous) {
        this.isAnonymous = isAnonymous;
    }
    
    @Column(name="show_results")
    public Long getShowResults() {
        return this.showResults;
    }
    
    public void setShowResults(Long showResults) {
        this.showResults = showResults;
    }
    
    @Column(name="conclusion", length=512)
    public String getConclusion() {
        return this.conclusion;
    }
    
    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
    
    @Column(name="url", length=512)
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="PPoll")
    public Set<PPollItems> getPPollItemses() {
        return this.PPollItemses;
    }
    
    public void setPPollItemses(Set<PPollItems> PPollItemses) {
        this.PPollItemses = PPollItemses;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="PPoll")
    public Set<PPollParticipant> getPPollParticipants() {
        return this.PPollParticipants;
    }
    
    public void setPPollParticipants(Set<PPollParticipant> PPollParticipants) {
        this.PPollParticipants = PPollParticipants;
    }




}


