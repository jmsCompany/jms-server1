package com.jms.domain.db;
// Generated 2015-6-7 13:49:29 by Hibernate Tools 3.2.2.GA


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
     private Documents documents;
     private Integer publish;

    public IssueDoc() {
    }

	
    public IssueDoc(IssueDocId id, Issue issue, Documents documents) {
        this.id = id;
        this.issue = issue;
        this.documents = documents;
    }
    public IssueDoc(IssueDocId id, Issue issue, Documents documents, Integer publish) {
       this.id = id;
       this.issue = issue;
       this.documents = documents;
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
    @JoinColumn(name="ID_DOCUMENT", nullable=false, insertable=false, updatable=false)
    public Documents getDocuments() {
        return this.documents;
    }
    
    public void setDocuments(Documents documents) {
        this.documents = documents;
    }
    
    @Column(name="PUBLISH")
    public Integer getPublish() {
        return this.publish;
    }
    
    public void setPublish(Integer publish) {
        this.publish = publish;
    }




}

