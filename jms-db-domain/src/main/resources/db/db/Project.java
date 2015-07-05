package com.jms.domain.db;
// Generated 2015-7-5 7:21:02 by Hibernate Tools 3.2.2.GA


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
 * Project generated by hbm2java
 */
@Audited
@AuditTable(catalog="jms_aud", value = "project_AUD")
@Entity
@Table(name="project"
    ,catalog="jms"
)
public class Project  implements java.io.Serializable {


     private Long idProject;
     private SysDicD sysDicDByStatus;
     private Company company;
     private Project project;
     private Users users;
     private SysDicD sysDicDByPriority;
     private String projectName;
     private String description;
     private String projectNumber;
     private Date planStartTime;
     private Date planEndTime;
     private Date startTime;
     private Date endTime;
     private Long process;
     private Set<ProjectDoc> projectDocs = new HashSet<ProjectDoc>(0);
     private Set<ProjectParticipant> projectParticipants = new HashSet<ProjectParticipant>(0);
     private Set<Task> tasks = new HashSet<Task>(0);
     private Set<Project> projects = new HashSet<Project>(0);

    public Project() {
    }

	
    public Project(Company company, String projectName) {
        this.company = company;
        this.projectName = projectName;
    }
    public Project(SysDicD sysDicDByStatus, Company company, Project project, Users users, SysDicD sysDicDByPriority, String projectName, String description, String projectNumber, Date planStartTime, Date planEndTime, Date startTime, Date endTime, Long process, Set<ProjectDoc> projectDocs, Set<ProjectParticipant> projectParticipants, Set<Task> tasks, Set<Project> projects) {
       this.sysDicDByStatus = sysDicDByStatus;
       this.company = company;
       this.project = project;
       this.users = users;
       this.sysDicDByPriority = sysDicDByPriority;
       this.projectName = projectName;
       this.description = description;
       this.projectNumber = projectNumber;
       this.planStartTime = planStartTime;
       this.planEndTime = planEndTime;
       this.startTime = startTime;
       this.endTime = endTime;
       this.process = process;
       this.projectDocs = projectDocs;
       this.projectParticipants = projectParticipants;
       this.tasks = tasks;
       this.projects = projects;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ID_PROJECT", unique=true, nullable=false)
    public Long getIdProject() {
        return this.idProject;
    }
    
    public void setIdProject(Long idProject) {
        this.idProject = idProject;
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
    @JoinColumn(name="ID_COMPANY", nullable=false)
    public Company getCompany() {
        return this.company;
    }
    
    public void setCompany(Company company) {
        this.company = company;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PARENT")
    public Project getProject() {
        return this.project;
    }
    
    public void setProject(Project project) {
        this.project = project;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="LEADER")
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
    
    @Column(name="PROJECT_NAME", nullable=false, length=64)
    public String getProjectName() {
        return this.projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    @Column(name="DESCRIPTION", length=256)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="PROJECT_NUMBER", length=64)
    public String getProjectNumber() {
        return this.projectNumber;
    }
    
    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="PLAN_START_TIME", length=10)
    public Date getPlanStartTime() {
        return this.planStartTime;
    }
    
    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="PLAN_END_TIME", length=10)
    public Date getPlanEndTime() {
        return this.planEndTime;
    }
    
    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="START_TIME", length=10)
    public Date getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="END_TIME", length=10)
    public Date getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    @Column(name="PROCESS")
    public Long getProcess() {
        return this.process;
    }
    
    public void setProcess(Long process) {
        this.process = process;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="project")
    public Set<ProjectDoc> getProjectDocs() {
        return this.projectDocs;
    }
    
    public void setProjectDocs(Set<ProjectDoc> projectDocs) {
        this.projectDocs = projectDocs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="project")
    public Set<ProjectParticipant> getProjectParticipants() {
        return this.projectParticipants;
    }
    
    public void setProjectParticipants(Set<ProjectParticipant> projectParticipants) {
        this.projectParticipants = projectParticipants;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="project")
    public Set<Task> getTasks() {
        return this.tasks;
    }
    
    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="project")
    public Set<Project> getProjects() {
        return this.projects;
    }
    
    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }




}


