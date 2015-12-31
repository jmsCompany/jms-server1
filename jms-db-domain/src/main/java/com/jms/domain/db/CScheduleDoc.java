package com.jms.domain.db;
// Generated 2015-12-31 19:48:00 by Hibernate Tools 3.2.2.GA


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
 * CScheduleDoc generated by hbm2java
 */
@Entity
@Table(name="c_schedule_doc"
    ,catalog="jms3"
)
public class CScheduleDoc  implements java.io.Serializable {


     private CScheduleDocId id;
     private Document document;
     private CSchedule CSchedule;
     private Long publish;

    public CScheduleDoc() {
    }

	
    public CScheduleDoc(CScheduleDocId id, Document document, CSchedule CSchedule) {
        this.id = id;
        this.document = document;
        this.CSchedule = CSchedule;
    }
    public CScheduleDoc(CScheduleDocId id, Document document, CSchedule CSchedule, Long publish) {
       this.id = id;
       this.document = document;
       this.CSchedule = CSchedule;
       this.publish = publish;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idSchedule", column=@Column(name="id_schedule", nullable=false) ), 
        @AttributeOverride(name="idDocument", column=@Column(name="id_document", nullable=false) ) } )
    public CScheduleDocId getId() {
        return this.id;
    }
    
    public void setId(CScheduleDocId id) {
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
    @JoinColumn(name="id_schedule", nullable=false, insertable=false, updatable=false)
    public CSchedule getCSchedule() {
        return this.CSchedule;
    }
    
    public void setCSchedule(CSchedule CSchedule) {
        this.CSchedule = CSchedule;
    }
    
    @Column(name="publish")
    public Long getPublish() {
        return this.publish;
    }
    
    public void setPublish(Long publish) {
        this.publish = publish;
    }




}


