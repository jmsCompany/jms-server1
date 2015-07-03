package com.jms.domain.db;
// Generated 2015-7-3 12:07:40 by Hibernate Tools 3.2.2.GA


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

/**
 * ProjectDoc generated by hbm2java
 */
@Audited
@Entity
@Table(name="project_doc"
    ,catalog="jms"
)
public class ProjectDoc  implements java.io.Serializable {


     private ProjectDocId id;
     private Document document;
     private Project project;
     private Long publish;

    public ProjectDoc() {
    }

	
    public ProjectDoc(ProjectDocId id, Document document, Project project) {
        this.id = id;
        this.document = document;
        this.project = project;
    }
    public ProjectDoc(ProjectDocId id, Document document, Project project, Long publish) {
       this.id = id;
       this.document = document;
       this.project = project;
       this.publish = publish;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idProject", column=@Column(name="ID_PROJECT", nullable=false) ), 
        @AttributeOverride(name="idDocument", column=@Column(name="ID_DOCUMENT", nullable=false) ) } )
    public ProjectDocId getId() {
        return this.id;
    }
    
    public void setId(ProjectDocId id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_DOCUMENT", nullable=false, insertable=false, updatable=false)
    public Document getDocument() {
        return this.document;
    }
    
    public void setDocument(Document document) {
        this.document = document;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_PROJECT", nullable=false, insertable=false, updatable=false)
    public Project getProject() {
        return this.project;
    }
    
    public void setProject(Project project) {
        this.project = project;
    }
    
    @Column(name="PUBLISH")
    public Long getPublish() {
        return this.publish;
    }
    
    public void setPublish(Long publish) {
        this.publish = publish;
    }




}


