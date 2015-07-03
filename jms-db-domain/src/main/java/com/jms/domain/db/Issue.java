package com.jms.domain.db;
// Generated 2015-7-3 12:07:40 by Hibernate Tools 3.2.2.GA


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

import org.hibernate.envers.Audited;

/**
 * Issue generated by hbm2java
 */
@Audited
@Entity
@Table(name="issue"
    ,catalog="jms"
)
public class Issue  implements java.io.Serializable {


     private Long idIssue;
     private SysDicD sysDicDByStatus;
     private Users users;
     private SysDicD sysDicDByPriority;
     private String title;
     private String description;
     private Date creationTime;
     private Long sourceType;
     private Long source;
     private Set<IssueDoc> issueDocs = new HashSet<IssueDoc>(0);
     private Set<IssueComment> issueComments = new HashSet<IssueComment>(0);

    public Issue() {
    }

    public Issue(SysDicD sysDicDByStatus, Users users, SysDicD sysDicDByPriority, String title, String description, Date creationTime, Long sourceType, Long source, Set<IssueDoc> issueDocs, Set<IssueComment> issueComments) {
       this.sysDicDByStatus = sysDicDByStatus;
       this.users = users;
       this.sysDicDByPriority = sysDicDByPriority;
       this.title = title;
       this.description = description;
       this.creationTime = creationTime;
       this.sourceType = sourceType;
       this.source = source;
       this.issueDocs = issueDocs;
       this.issueComments = issueComments;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ID_ISSUE", unique=true, nullable=false)
    public Long getIdIssue() {
        return this.idIssue;
    }
    
    public void setIdIssue(Long idIssue) {
        this.idIssue = idIssue;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="STATUS")
    public SysDicD getSysDicDByStatus() {
        return this.sysDicDByStatus;
    }
    
    public void setSysDicDByStatus(SysDicD sysDicDByStatus) {
        this.sysDicDByStatus = sysDicDByStatus;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CREATOR")
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PRIORITY")
    public SysDicD getSysDicDByPriority() {
        return this.sysDicDByPriority;
    }
    
    public void setSysDicDByPriority(SysDicD sysDicDByPriority) {
        this.sysDicDByPriority = sysDicDByPriority;
    }
    
    @Column(name="TITLE", length=64)
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    @Column(name="DESCRIPTION", length=256)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="CREATION_TIME", length=10)
    public Date getCreationTime() {
        return this.creationTime;
    }
    
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
    
    @Column(name="SOURCE_TYPE")
    public Long getSourceType() {
        return this.sourceType;
    }
    
    public void setSourceType(Long sourceType) {
        this.sourceType = sourceType;
    }
    
    @Column(name="SOURCE")
    public Long getSource() {
        return this.source;
    }
    
    public void setSource(Long source) {
        this.source = source;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="issue")
    public Set<IssueDoc> getIssueDocs() {
        return this.issueDocs;
    }
    
    public void setIssueDocs(Set<IssueDoc> issueDocs) {
        this.issueDocs = issueDocs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="issue")
    public Set<IssueComment> getIssueComments() {
        return this.issueComments;
    }
    
    public void setIssueComments(Set<IssueComment> issueComments) {
        this.issueComments = issueComments;
    }




}


