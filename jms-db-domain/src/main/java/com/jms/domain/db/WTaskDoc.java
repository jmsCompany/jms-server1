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
 * WTaskDoc generated by hbm2java
 */
@Entity
@Table(name="w_task_doc"
)
public class WTaskDoc  implements java.io.Serializable {


     private WTaskDocId id;
     private Document document;
     private WTask WTask;
     private Long publish;

    public WTaskDoc() {
    }

	
    public WTaskDoc(WTaskDocId id, Document document, WTask WTask) {
        this.id = id;
        this.document = document;
        this.WTask = WTask;
    }
    public WTaskDoc(WTaskDocId id, Document document, WTask WTask, Long publish) {
       this.id = id;
       this.document = document;
       this.WTask = WTask;
       this.publish = publish;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idTask", column=@Column(name="id_task", nullable=false) ), 
        @AttributeOverride(name="idDocument", column=@Column(name="id_document", nullable=false) ) } )
    public WTaskDocId getId() {
        return this.id;
    }
    
    public void setId(WTaskDocId id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_document", nullable=false, insertable=false, updatable=false)
    public Document getDocument() {
        return this.document;
    }
    
    public void setDocument(Document document) {
        this.document = document;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_task", nullable=false, insertable=false, updatable=false)
    public WTask getWTask() {
        return this.WTask;
    }
    
    public void setWTask(WTask WTask) {
        this.WTask = WTask;
    }
    
    @Column(name="publish")
    public Long getPublish() {
        return this.publish;
    }
    
    public void setPublish(Long publish) {
        this.publish = publish;
    }




}


