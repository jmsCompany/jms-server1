package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


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
 * WIssueDoc generated by hbm2java
 */
@Entity
@Table(name="w_issue_doc"
)
public class WIssueDoc  implements java.io.Serializable {


     private WIssueDocId id;
     private WIssue WIssue;
     private WIssueComment WIssueComment;
     private Document document;
     private Long publish;

    public WIssueDoc() {
    }

	
    public WIssueDoc(WIssueDocId id, WIssue WIssue, Document document) {
        this.id = id;
        this.WIssue = WIssue;
        this.document = document;
    }
    public WIssueDoc(WIssueDocId id, WIssue WIssue, WIssueComment WIssueComment, Document document, Long publish) {
       this.id = id;
       this.WIssue = WIssue;
       this.WIssueComment = WIssueComment;
       this.document = document;
       this.publish = publish;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idIssue", column=@Column(name="id_issue", nullable=false) ), 
        @AttributeOverride(name="idDocument", column=@Column(name="id_document", nullable=false) ) } )
    public WIssueDocId getId() {
        return this.id;
    }
    
    public void setId(WIssueDocId id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_issue", nullable=false, insertable=false, updatable=false)
    public WIssue getWIssue() {
        return this.WIssue;
    }
    
    public void setWIssue(WIssue WIssue) {
        this.WIssue = WIssue;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_issue_comment")
    public WIssueComment getWIssueComment() {
        return this.WIssueComment;
    }
    
    public void setWIssueComment(WIssueComment WIssueComment) {
        this.WIssueComment = WIssueComment;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_document", nullable=false, insertable=false, updatable=false)
    public Document getDocument() {
        return this.document;
    }
    
    public void setDocument(Document document) {
        this.document = document;
    }
    
    @Column(name="publish")
    public Long getPublish() {
        return this.publish;
    }
    
    public void setPublish(Long publish) {
        this.publish = publish;
    }




}


