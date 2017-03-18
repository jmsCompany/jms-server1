package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


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
 * AApprovalProcess generated by hbm2java
 */
@Entity
@Table(name="a_approval_process")
public class AApprovalProcess  implements java.io.Serializable {


     private Long idApprovalProcess;
     private Document document;
     private AApprovalType AApprovalType;
     private String name;
     private String description;
     private Set<AApprovalPhase> AApprovalPhases = new HashSet<AApprovalPhase>(0);
     private Set<AApplication> AApplications = new HashSet<AApplication>(0);

    public AApprovalProcess() {
    }

    public AApprovalProcess(Document document, AApprovalType AApprovalType, String name, String description, Set<AApprovalPhase> AApprovalPhases, Set<AApplication> AApplications) {
       this.document = document;
       this.AApprovalType = AApprovalType;
       this.name = name;
       this.description = description;
       this.AApprovalPhases = AApprovalPhases;
       this.AApplications = AApplications;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_approval_process", unique=true, nullable=false)
    public Long getIdApprovalProcess() {
        return this.idApprovalProcess;
    }
    
    public void setIdApprovalProcess(Long idApprovalProcess) {
        this.idApprovalProcess = idApprovalProcess;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_document")
    public Document getDocument() {
        return this.document;
    }
    
    public void setDocument(Document document) {
        this.document = document;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_approval_type")
    public AApprovalType getAApprovalType() {
        return this.AApprovalType;
    }
    
    public void setAApprovalType(AApprovalType AApprovalType) {
        this.AApprovalType = AApprovalType;
    }
    
    @Column(name="name", length=64)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="description", length=1024)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="AApprovalProcess")
    public Set<AApprovalPhase> getAApprovalPhases() {
        return this.AApprovalPhases;
    }
    
    public void setAApprovalPhases(Set<AApprovalPhase> AApprovalPhases) {
        this.AApprovalPhases = AApprovalPhases;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="AApprovalProcess")
    public Set<AApplication> getAApplications() {
        return this.AApplications;
    }
    
    public void setAApplications(Set<AApplication> AApplications) {
        this.AApplications = AApplications;
    }




}


