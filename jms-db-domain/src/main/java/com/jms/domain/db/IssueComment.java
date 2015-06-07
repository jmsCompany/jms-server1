package com.jms.domain.db;
// Generated 2015-6-7 13:49:29 by Hibernate Tools 3.2.2.GA


import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * IssueComment generated by hbm2java
 */
@Entity
@Table(name="issue_comment"
    ,catalog="jms"
)
public class IssueComment  implements java.io.Serializable {


     private Integer idIssueComment;
     private Issue issue;
     private IssueComment issueComment;
     private String comment;
     private Date creationTime;
     private Date modificationTime;
     private Set<IssueComment> issueComments = new HashSet<IssueComment>(0);

    public IssueComment() {
    }

	
    public IssueComment(Integer idIssueComment) {
        this.idIssueComment = idIssueComment;
    }
    public IssueComment(Integer idIssueComment, Issue issue, IssueComment issueComment, String comment, Date creationTime, Date modificationTime, Set<IssueComment> issueComments) {
       this.idIssueComment = idIssueComment;
       this.issue = issue;
       this.issueComment = issueComment;
       this.comment = comment;
       this.creationTime = creationTime;
       this.modificationTime = modificationTime;
       this.issueComments = issueComments;
    }
   

    @Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="ID_ISSUE_COMMENT", unique=true, nullable=false)
    public Integer getIdIssueComment() {
        return this.idIssueComment;
    }
    
    public void setIdIssueComment(Integer idIssueComment) {
        this.idIssueComment = idIssueComment;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_ISSUE")
    public Issue getIssue() {
        return this.issue;
    }
    
    public void setIssue(Issue issue) {
        this.issue = issue;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PARENT")
    public IssueComment getIssueComment() {
        return this.issueComment;
    }
    
    public void setIssueComment(IssueComment issueComment) {
        this.issueComment = issueComment;
    }
    
    @Column(name="COMMENT", length=1024)
    public String getComment() {
        return this.comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="CREATION_TIME", length=10)
    public Date getCreationTime() {
        return this.creationTime;
    }
    
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="MODIFICATION_TIME", length=10)
    public Date getModificationTime() {
        return this.modificationTime;
    }
    
    public void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="issueComment")
    public Set<IssueComment> getIssueComments() {
        return this.issueComments;
    }
    
    public void setIssueComments(Set<IssueComment> issueComments) {
        this.issueComments = issueComments;
    }




}


