package com.jms.domain.db;
// Generated 2015-8-22 18:08:00 by Hibernate Tools 3.2.2.GA


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
 * IssueDoc generated by hbm2java
 */
@Entity
@Table(name="issue_doc"
    ,catalog="jms"
)
public class IssueDoc  implements java.io.Serializable {


     private IssueDocId id;
     private Issue issue;
     private IssueComment issueComment;
     private Document document;
     private Long publish;

    public IssueDoc() {
    }

	
    public IssueDoc(IssueDocId id, Issue issue, Document document) {
        this.id = id;
        this.issue = issue;
        this.document = document;
    }
    public IssueDoc(IssueDocId id, Issue issue, IssueComment issueComment, Document document, Long publish) {
       this.id = id;
       this.issue = issue;
       this.issueComment = issueComment;
       this.document = document;
       this.publish = publish;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idIssue", column=@Column(name="ID_ISSUE", nullable=false) ), 
        @AttributeOverride(name="idDocument", column=@Column(name="ID_DOCUMENT", nullable=false) ) } )
    public IssueDocId getId() {
        return this.id;
    }
    
    public void setId(IssueDocId id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_ISSUE", nullable=false, insertable=false, updatable=false)
    public Issue getIssue() {
        return this.issue;
    }
    
    public void setIssue(Issue issue) {
        this.issue = issue;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_ISSUE_COMMENT")
    public IssueComment getIssueComment() {
        return this.issueComment;
    }
    
    public void setIssueComment(IssueComment issueComment) {
        this.issueComment = issueComment;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_DOCUMENT", nullable=false, insertable=false, updatable=false)
    public Document getDocument() {
        return this.document;
    }
    
    public void setDocument(Document document) {
        this.document = document;
    }
    
    @Column(name="PUBLISH")
    public Long getPublish() {
        return this.publish;
    }
    
    public void setPublish(Long publish) {
        this.publish = publish;
    }




}


