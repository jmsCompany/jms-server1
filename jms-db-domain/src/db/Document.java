package com.jms.domain.db;
// Generated 2015-7-22 10:30:44 by Hibernate Tools 3.2.2.GA


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

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * Document generated by hbm2java
 */
@Audited
@AuditTable(catalog="jms_aud", value = "document")
@Entity
@Table(name="document"
    ,catalog="jms"
)
public class Document  implements java.io.Serializable {


     private Long idDocument;
     private Users usersByModifyBy;
     private Users usersByCreator;
     private Document document;
     private Date creationTime;
     private Date modifyTime;
     private String relativePath;
     private String name;
     private String fileName;
     private String description;
     private Long size;
     private Set<TaskDoc> taskDocs = new HashSet<TaskDoc>(0);
     private Set<IssueDoc> issueDocs = new HashSet<IssueDoc>(0);
     private Set<ProjectDoc> projectDocs = new HashSet<ProjectDoc>(0);
     private Set<Company> companiesForLicence = new HashSet<Company>(0);
     private Set<Document> documents = new HashSet<Document>(0);
     private Set<ScheduleDoc> scheduleDocs = new HashSet<ScheduleDoc>(0);
     private Set<PollItems> pollItemses = new HashSet<PollItems>(0);
     private Set<Users> usersesForPic = new HashSet<Users>(0);
     private Set<ApprovalProcess> approvalProcesses = new HashSet<ApprovalProcess>(0);
     private Set<Company> companiesForLogo = new HashSet<Company>(0);
     private Set<Users> usersesForCv = new HashSet<Users>(0);

    public Document() {
    }

    public Document(Users usersByModifyBy, Users usersByCreator, Document document, Date creationTime, Date modifyTime, String relativePath, String name, String fileName, String description, Long size, Set<TaskDoc> taskDocs, Set<IssueDoc> issueDocs, Set<ProjectDoc> projectDocs, Set<Company> companiesForLicence, Set<Document> documents, Set<ScheduleDoc> scheduleDocs, Set<PollItems> pollItemses, Set<Users> usersesForPic, Set<ApprovalProcess> approvalProcesses, Set<Company> companiesForLogo, Set<Users> usersesForCv) {
       this.usersByModifyBy = usersByModifyBy;
       this.usersByCreator = usersByCreator;
       this.document = document;
       this.creationTime = creationTime;
       this.modifyTime = modifyTime;
       this.relativePath = relativePath;
       this.name = name;
       this.fileName = fileName;
       this.description = description;
       this.size = size;
       this.taskDocs = taskDocs;
       this.issueDocs = issueDocs;
       this.projectDocs = projectDocs;
       this.companiesForLicence = companiesForLicence;
       this.documents = documents;
       this.scheduleDocs = scheduleDocs;
       this.pollItemses = pollItemses;
       this.usersesForPic = usersesForPic;
       this.approvalProcesses = approvalProcesses;
       this.companiesForLogo = companiesForLogo;
       this.usersesForCv = usersesForCv;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ID_DOCUMENT", unique=true, nullable=false)
    public Long getIdDocument() {
        return this.idDocument;
    }
    
    public void setIdDocument(Long idDocument) {
        this.idDocument = idDocument;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="MODIFY_BY")
    public Users getUsersByModifyBy() {
        return this.usersByModifyBy;
    }
    
    public void setUsersByModifyBy(Users usersByModifyBy) {
        this.usersByModifyBy = usersByModifyBy;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CREATOR")
    public Users getUsersByCreator() {
        return this.usersByCreator;
    }
    
    public void setUsersByCreator(Users usersByCreator) {
        this.usersByCreator = usersByCreator;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PARENT")
    public Document getDocument() {
        return this.document;
    }
    
    public void setDocument(Document document) {
        this.document = document;
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
    @Column(name="MODIFY_TIME", length=10)
    public Date getModifyTime() {
        return this.modifyTime;
    }
    
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
    
    @Column(name="RELATIVE_PATH", length=256)
    public String getRelativePath() {
        return this.relativePath;
    }
    
    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }
    
    @Column(name="NAME", length=64)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="FILE_NAME", length=256)
    public String getFileName() {
        return this.fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    @Column(name="DESCRIPTION", length=256)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="SIZE")
    public Long getSize() {
        return this.size;
    }
    
    public void setSize(Long size) {
        this.size = size;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="document")
    public Set<TaskDoc> getTaskDocs() {
        return this.taskDocs;
    }
    
    public void setTaskDocs(Set<TaskDoc> taskDocs) {
        this.taskDocs = taskDocs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="document")
    public Set<IssueDoc> getIssueDocs() {
        return this.issueDocs;
    }
    
    public void setIssueDocs(Set<IssueDoc> issueDocs) {
        this.issueDocs = issueDocs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="document")
    public Set<ProjectDoc> getProjectDocs() {
        return this.projectDocs;
    }
    
    public void setProjectDocs(Set<ProjectDoc> projectDocs) {
        this.projectDocs = projectDocs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="documentByLicence")
    public Set<Company> getCompaniesForLicence() {
        return this.companiesForLicence;
    }
    
    public void setCompaniesForLicence(Set<Company> companiesForLicence) {
        this.companiesForLicence = companiesForLicence;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="document")
    public Set<Document> getDocuments() {
        return this.documents;
    }
    
    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="document")
    public Set<ScheduleDoc> getScheduleDocs() {
        return this.scheduleDocs;
    }
    
    public void setScheduleDocs(Set<ScheduleDoc> scheduleDocs) {
        this.scheduleDocs = scheduleDocs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="document")
    public Set<PollItems> getPollItemses() {
        return this.pollItemses;
    }
    
    public void setPollItemses(Set<PollItems> pollItemses) {
        this.pollItemses = pollItemses;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="documentByPic")
    public Set<Users> getUsersesForPic() {
        return this.usersesForPic;
    }
    
    public void setUsersesForPic(Set<Users> usersesForPic) {
        this.usersesForPic = usersesForPic;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="document")
    public Set<ApprovalProcess> getApprovalProcesses() {
        return this.approvalProcesses;
    }
    
    public void setApprovalProcesses(Set<ApprovalProcess> approvalProcesses) {
        this.approvalProcesses = approvalProcesses;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="documentByLogo")
    public Set<Company> getCompaniesForLogo() {
        return this.companiesForLogo;
    }
    
    public void setCompaniesForLogo(Set<Company> companiesForLogo) {
        this.companiesForLogo = companiesForLogo;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="documentByCv")
    public Set<Users> getUsersesForCv() {
        return this.usersesForCv;
    }
    
    public void setUsersesForCv(Set<Users> usersesForCv) {
        this.usersesForCv = usersesForCv;
    }




}

