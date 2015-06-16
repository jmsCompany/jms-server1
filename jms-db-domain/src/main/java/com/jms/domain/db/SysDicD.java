package com.jms.domain.db;
// Generated 2015-6-16 15:01:20 by Hibernate Tools 3.2.2.GA


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
 * SysDicD generated by hbm2java
 */
@Entity
@Table(name="sys_dic_d"
    ,catalog="jms"
)
public class SysDicD  implements java.io.Serializable {


     private Integer idDic;
     private SysDic sysDic;
     private String name;
     private int enabled;
     private String description;
     private Integer isDefault;
     private Set<Company> companiesForTaskType = new HashSet<Company>(0);
     private Set<Company> companiesForLocale = new HashSet<Company>(0);
     private Set<Task> tasksForPriority = new HashSet<Task>(0);
     private Set<Task> tasksForStatus = new HashSet<Task>(0);
     private Set<Issue> issuesForPriority = new HashSet<Issue>(0);
     private Set<Company> companiesForCompanyCatorgory = new HashSet<Company>(0);
     private Set<Users> usersesForLocale = new HashSet<Users>(0);
     private Set<Company> companiesForScheme = new HashSet<Company>(0);
     private Set<Users> usersesForGender = new HashSet<Users>(0);
     private Set<Company> companiesForCompanyType = new HashSet<Company>(0);
     private Set<Users> usersesForScheme = new HashSet<Users>(0);
     private Set<Users> usersesForStatus = new HashSet<Users>(0);
     private Set<Company> companiesForCompanyNature = new HashSet<Company>(0);
     private Set<Company> companiesForCompanySize = new HashSet<Company>(0);
     private Set<Project> projects = new HashSet<Project>(0);
     private Set<Issue> issuesForStatus = new HashSet<Issue>(0);

    public SysDicD() {
    }

	
    public SysDicD(String name, int enabled, String description) {
        this.name = name;
        this.enabled = enabled;
        this.description = description;
    }
    public SysDicD(SysDic sysDic, String name, int enabled, String description, Integer isDefault, Set<Company> companiesForTaskType, Set<Company> companiesForLocale, Set<Task> tasksForPriority, Set<Task> tasksForStatus, Set<Issue> issuesForPriority, Set<Company> companiesForCompanyCatorgory, Set<Users> usersesForLocale, Set<Company> companiesForScheme, Set<Users> usersesForGender, Set<Company> companiesForCompanyType, Set<Users> usersesForScheme, Set<Users> usersesForStatus, Set<Company> companiesForCompanyNature, Set<Company> companiesForCompanySize, Set<Project> projects, Set<Issue> issuesForStatus) {
       this.sysDic = sysDic;
       this.name = name;
       this.enabled = enabled;
       this.description = description;
       this.isDefault = isDefault;
       this.companiesForTaskType = companiesForTaskType;
       this.companiesForLocale = companiesForLocale;
       this.tasksForPriority = tasksForPriority;
       this.tasksForStatus = tasksForStatus;
       this.issuesForPriority = issuesForPriority;
       this.companiesForCompanyCatorgory = companiesForCompanyCatorgory;
       this.usersesForLocale = usersesForLocale;
       this.companiesForScheme = companiesForScheme;
       this.usersesForGender = usersesForGender;
       this.companiesForCompanyType = companiesForCompanyType;
       this.usersesForScheme = usersesForScheme;
       this.usersesForStatus = usersesForStatus;
       this.companiesForCompanyNature = companiesForCompanyNature;
       this.companiesForCompanySize = companiesForCompanySize;
       this.projects = projects;
       this.issuesForStatus = issuesForStatus;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ID_DIC", unique=true, nullable=false)
    public Integer getIdDic() {
        return this.idDic;
    }
    
    public void setIdDic(Integer idDic) {
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
    public int getEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(int enabled) {
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
    public Integer getIsDefault() {
        return this.isDefault;
    }
    
    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByTaskType")
    public Set<Company> getCompaniesForTaskType() {
        return this.companiesForTaskType;
    }
    
    public void setCompaniesForTaskType(Set<Company> companiesForTaskType) {
        this.companiesForTaskType = companiesForTaskType;
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
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByStatus")
    public Set<Task> getTasksForStatus() {
        return this.tasksForStatus;
    }
    
    public void setTasksForStatus(Set<Task> tasksForStatus) {
        this.tasksForStatus = tasksForStatus;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByPriority")
    public Set<Issue> getIssuesForPriority() {
        return this.issuesForPriority;
    }
    
    public void setIssuesForPriority(Set<Issue> issuesForPriority) {
        this.issuesForPriority = issuesForPriority;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByCompanyCatorgory")
    public Set<Company> getCompaniesForCompanyCatorgory() {
        return this.companiesForCompanyCatorgory;
    }
    
    public void setCompaniesForCompanyCatorgory(Set<Company> companiesForCompanyCatorgory) {
        this.companiesForCompanyCatorgory = companiesForCompanyCatorgory;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByLocale")
    public Set<Users> getUsersesForLocale() {
        return this.usersesForLocale;
    }
    
    public void setUsersesForLocale(Set<Users> usersesForLocale) {
        this.usersesForLocale = usersesForLocale;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByScheme")
    public Set<Company> getCompaniesForScheme() {
        return this.companiesForScheme;
    }
    
    public void setCompaniesForScheme(Set<Company> companiesForScheme) {
        this.companiesForScheme = companiesForScheme;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByGender")
    public Set<Users> getUsersesForGender() {
        return this.usersesForGender;
    }
    
    public void setUsersesForGender(Set<Users> usersesForGender) {
        this.usersesForGender = usersesForGender;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByCompanyType")
    public Set<Company> getCompaniesForCompanyType() {
        return this.companiesForCompanyType;
    }
    
    public void setCompaniesForCompanyType(Set<Company> companiesForCompanyType) {
        this.companiesForCompanyType = companiesForCompanyType;
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
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByCompanyNature")
    public Set<Company> getCompaniesForCompanyNature() {
        return this.companiesForCompanyNature;
    }
    
    public void setCompaniesForCompanyNature(Set<Company> companiesForCompanyNature) {
        this.companiesForCompanyNature = companiesForCompanyNature;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByCompanySize")
    public Set<Company> getCompaniesForCompanySize() {
        return this.companiesForCompanySize;
    }
    
    public void setCompaniesForCompanySize(Set<Company> companiesForCompanySize) {
        this.companiesForCompanySize = companiesForCompanySize;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicD")
    public Set<Project> getProjects() {
        return this.projects;
    }
    
    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDicDByStatus")
    public Set<Issue> getIssuesForStatus() {
        return this.issuesForStatus;
    }
    
    public void setIssuesForStatus(Set<Issue> issuesForStatus) {
        this.issuesForStatus = issuesForStatus;
    }




}


