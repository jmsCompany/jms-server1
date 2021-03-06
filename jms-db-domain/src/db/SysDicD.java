package com.jms.domain.db;
// Generated 2015-7-22 10:30:44 by Hibernate Tools 3.2.2.GA


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

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * SysDicD generated by hbm2java
 */
@Audited
@AuditTable(catalog="jms_aud", value = "sys_dic_d")
@Entity
@Table(name="sys_dic_d"
    ,catalog="jms"
)
public class SysDicD  implements java.io.Serializable {


     private Long idDic;
     private SysDic sysDic;
     private String name;
     private Long enabled;
     private String description;
     private Long isDefault;
     private Set<Company> companiesForLocale = new HashSet<Company>(0);
     private Set<Task> tasksForPriority = new HashSet<Task>(0);
     private Set<Vote> votes = new HashSet<Vote>(0);
     private Set<Schedule> schedules = new HashSet<Schedule>(0);
     private Set<Task> tasksForStatus = new HashSet<Task>(0);
     private Set<ApplicationPhase> applicationPhases = new HashSet<ApplicationPhase>(0);
     private Set<Issue> issuesForPriority = new HashSet<Issue>(0);
     private Set<Issue> issuesForStatus = new HashSet<Issue>(0);
     private Set<PollParticipant> pollParticipants = new HashSet<PollParticipant>(0);
     private Set<Users> usersesForLocale = new HashSet<Users>(0);
     private Set<Users> usersesForGender = new HashSet<Users>(0);
     private Set<Users> usersesForScheme = new HashSet<Users>(0);
     private Set<Users> usersesForStatus = new HashSet<Users>(0);
     private Set<Issue> issuesForSourceType = new HashSet<Issue>(0);
     private Set<Repeats> repeatses = new HashSet<Repeats>(0);
     private Set<Poll> polls = new HashSet<Poll>(0);
     private Set<Application> applications = new HashSet<Application>(0);
     private Set<Company> companiesForCompanyCatorgory = new HashSet<Company>(0);
     private Set<Company> companiesForScheme = new HashSet<Company>(0);
     private Set<Company> companiesForCompanySize = new HashSet<Company>(0);
     private Set<Company> companiesForCompanyType = new HashSet<Company>(0);
     private Set<Company> companiesForCompanyNature = new HashSet<Company>(0);
     private Set<Project> projectsForPriority = new HashSet<Project>(0);
     private Set<Company> companiesForTaskType = new HashSet<Company>(0);
     private Set<Project> projectsForStatus = new HashSet<Project>(0);

    public SysDicD() {
    }

	
    public SysDicD(String name, Long enabled, String description) {
        this.name = name;
        this.enabled = enabled;
        this.description = description;
    }
    public SysDicD(SysDic sysDic, String name, Long enabled, String description, Long isDefault, Set<Company> companiesForLocale, Set<Task> tasksForPriority, Set<Vote> votes, Set<Schedule> schedules, Set<Task> tasksForStatus, Set<ApplicationPhase> applicationPhases, Set<Issue> issuesForPriority, Set<Issue> issuesForStatus, Set<PollParticipant> pollParticipants, Set<Users> usersesForLocale, Set<Users> usersesForGender, Set<Users> usersesForScheme, Set<Users> usersesForStatus, Set<Issue> issuesForSourceType, Set<Repeats> repeatses, Set<Poll> polls, Set<Application> applications, Set<Company> companiesForCompanyCatorgory, Set<Company> companiesForScheme, Set<Company> companiesForCompanySize, Set<Company> companiesForCompanyType, Set<Company> companiesForCompanyNature, Set<Project> projectsForPriority, Set<Company> companiesForTaskType, Set<Project> projectsForStatus) {
       this.sysDic = sysDic;
       this.name = name;
       this.enabled = enabled;
       this.description = description;
       this.isDefault = isDefault;
       this.companiesForLocale = companiesForLocale;
       this.tasksForPriority = tasksForPriority;
       this.votes = votes;
       this.schedules = schedules;
       this.tasksForStatus = tasksForStatus;
       this.applicationPhases = applicationPhases;
       this.issuesForPriority = issuesForPriority;
       this.issuesForStatus = issuesForStatus;
       this.pollParticipants = pollParticipants;
       this.usersesForLocale = usersesForLocale;
       this.usersesForGender = usersesForGender;
       this.usersesForScheme = usersesForScheme;
       this.usersesForStatus = usersesForStatus;
       this.issuesForSourceType = issuesForSourceType;
       this.repeatses = repeatses;
       this.polls = polls;
       this.applications = applications;
       this.companiesForCompanyCatorgory = companiesForCompanyCatorgory;
       this.companiesForScheme = companiesForScheme;
       this.companiesForCompanySize = companiesForCompanySize;
       this.companiesForCompanyType = companiesForCompanyType;
       this.companiesForCompanyNature = companiesForCompanyNature;
       this.projectsForPriority = projectsForPriority;
       this.companiesForTaskType = companiesForTaskType;
       this.projectsForStatus = projectsForStatus;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ID_DIC", unique=true, nullable=false)
    public Long getIdDic() {
        return this.idDic;
    }
    
    public void setIdDic(Long idDic) {
        this.idDic = idDic;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="TYPE")
    public SysDic getSysDic() {
        return this.sysDic;
    }
    
    public void setSysDic(SysDic sysDic) {
        this.sysDic = sysDic;
    }
    
    @Column(name="NAME", nullable=false, length=64)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="ENABLED", nullable=false)
    public Long getEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(Long enabled) {
        this.enabled = enabled;
    }
    
    @Column(name="DESCRIPTION", nullable=false, length=128)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="IS_DEFAULT")
    public Long getIsDefault() {
        return this.isDefault;
    }
    
    public void setIsDefault(Long isDefault) {
        this.isDefault = isDefault;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByLocale")
    public Set<Company> getCompaniesForLocale() {
        return this.companiesForLocale;
    }
    
    public void setCompaniesForLocale(Set<Company> companiesForLocale) {
        this.companiesForLocale = companiesForLocale;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByPriority")
    public Set<Task> getTasksForPriority() {
        return this.tasksForPriority;
    }
    
    public void setTasksForPriority(Set<Task> tasksForPriority) {
        this.tasksForPriority = tasksForPriority;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicD")
    public Set<Vote> getVotes() {
        return this.votes;
    }
    
    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicD")
    public Set<Schedule> getSchedules() {
        return this.schedules;
    }
    
    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByStatus")
    public Set<Task> getTasksForStatus() {
        return this.tasksForStatus;
    }
    
    public void setTasksForStatus(Set<Task> tasksForStatus) {
        this.tasksForStatus = tasksForStatus;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicD")
    public Set<ApplicationPhase> getApplicationPhases() {
        return this.applicationPhases;
    }
    
    public void setApplicationPhases(Set<ApplicationPhase> applicationPhases) {
        this.applicationPhases = applicationPhases;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByPriority")
    public Set<Issue> getIssuesForPriority() {
        return this.issuesForPriority;
    }
    
    public void setIssuesForPriority(Set<Issue> issuesForPriority) {
        this.issuesForPriority = issuesForPriority;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByStatus")
    public Set<Issue> getIssuesForStatus() {
        return this.issuesForStatus;
    }
    
    public void setIssuesForStatus(Set<Issue> issuesForStatus) {
        this.issuesForStatus = issuesForStatus;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicD")
    public Set<PollParticipant> getPollParticipants() {
        return this.pollParticipants;
    }
    
    public void setPollParticipants(Set<PollParticipant> pollParticipants) {
        this.pollParticipants = pollParticipants;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByLocale")
    public Set<Users> getUsersesForLocale() {
        return this.usersesForLocale;
    }
    
    public void setUsersesForLocale(Set<Users> usersesForLocale) {
        this.usersesForLocale = usersesForLocale;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByGender")
    public Set<Users> getUsersesForGender() {
        return this.usersesForGender;
    }
    
    public void setUsersesForGender(Set<Users> usersesForGender) {
        this.usersesForGender = usersesForGender;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByScheme")
    public Set<Users> getUsersesForScheme() {
        return this.usersesForScheme;
    }
    
    public void setUsersesForScheme(Set<Users> usersesForScheme) {
        this.usersesForScheme = usersesForScheme;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByStatus")
    public Set<Users> getUsersesForStatus() {
        return this.usersesForStatus;
    }
    
    public void setUsersesForStatus(Set<Users> usersesForStatus) {
        this.usersesForStatus = usersesForStatus;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDBySourceType")
    public Set<Issue> getIssuesForSourceType() {
        return this.issuesForSourceType;
    }
    
    public void setIssuesForSourceType(Set<Issue> issuesForSourceType) {
        this.issuesForSourceType = issuesForSourceType;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicD")
    public Set<Repeats> getRepeatses() {
        return this.repeatses;
    }
    
    public void setRepeatses(Set<Repeats> repeatses) {
        this.repeatses = repeatses;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicD")
    public Set<Poll> getPolls() {
        return this.polls;
    }
    
    public void setPolls(Set<Poll> polls) {
        this.polls = polls;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicD")
    public Set<Application> getApplications() {
        return this.applications;
    }
    
    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByCompanyCatorgory")
    public Set<Company> getCompaniesForCompanyCatorgory() {
        return this.companiesForCompanyCatorgory;
    }
    
    public void setCompaniesForCompanyCatorgory(Set<Company> companiesForCompanyCatorgory) {
        this.companiesForCompanyCatorgory = companiesForCompanyCatorgory;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByScheme")
    public Set<Company> getCompaniesForScheme() {
        return this.companiesForScheme;
    }
    
    public void setCompaniesForScheme(Set<Company> companiesForScheme) {
        this.companiesForScheme = companiesForScheme;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByCompanySize")
    public Set<Company> getCompaniesForCompanySize() {
        return this.companiesForCompanySize;
    }
    
    public void setCompaniesForCompanySize(Set<Company> companiesForCompanySize) {
        this.companiesForCompanySize = companiesForCompanySize;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByCompanyType")
    public Set<Company> getCompaniesForCompanyType() {
        return this.companiesForCompanyType;
    }
    
    public void setCompaniesForCompanyType(Set<Company> companiesForCompanyType) {
        this.companiesForCompanyType = companiesForCompanyType;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByCompanyNature")
    public Set<Company> getCompaniesForCompanyNature() {
        return this.companiesForCompanyNature;
    }
    
    public void setCompaniesForCompanyNature(Set<Company> companiesForCompanyNature) {
        this.companiesForCompanyNature = companiesForCompanyNature;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByPriority")
    public Set<Project> getProjectsForPriority() {
        return this.projectsForPriority;
    }
    
    public void setProjectsForPriority(Set<Project> projectsForPriority) {
        this.projectsForPriority = projectsForPriority;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByTaskType")
    public Set<Company> getCompaniesForTaskType() {
        return this.companiesForTaskType;
    }
    
    public void setCompaniesForTaskType(Set<Company> companiesForTaskType) {
        this.companiesForTaskType = companiesForTaskType;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByStatus")
    public Set<Project> getProjectsForStatus() {
        return this.projectsForStatus;
    }
    
    public void setProjectsForStatus(Set<Project> projectsForStatus) {
        this.projectsForStatus = projectsForStatus;
    }




}


