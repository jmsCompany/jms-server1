package com.jms.domain.db;
// Generated 2015-8-15 16:28:29 by Hibernate Tools 3.2.2.GA


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

/**
 * Users generated by hbm2java
 */
@Entity
@Table(name="users"
    ,catalog="jms"
)
public class Users  implements java.io.Serializable {


     private Long idUser;
     private SysDicD sysDicDByLocale;
     private SysDicD sysDicDByStatus;
     private Company company;
     private District district;
     private Document documentByPic;
     private SysDicD sysDicDByScheme;
     private SysDicD sysDicDByGender;
     private Users users;
     private Document documentByCv;
     private Date creationTime;
     private String username;
     private String mobile;
     private String email;
     private String password;
     private String idcard;
     private String name;
     private String address;
     private String ext;
     private String ENo;
     private String school;
     private Date gradTime;
     private String major;
     private String degree;
     private String emergencyHp;
     private Long enabled;
     private String description;
     private Date birthday;
     private Date lastLogin;
     private String token;
     private Long userType;
     private Set<Document> documentsForModifyBy = new HashSet<Document>(0);
     private Set<ApprovalCc> approvalCcs = new HashSet<ApprovalCc>(0);
     private Set<Document> documentsForCreator = new HashSet<Document>(0);
     private Set<Feedback> feedbacks = new HashSet<Feedback>(0);
     private Set<Notice> notices = new HashSet<Notice>(0);
     private Set<Poll> polls = new HashSet<Poll>(0);
     private Set<ApprovalPhase> approvalPhases = new HashSet<ApprovalPhase>(0);
     private Set<TaskComment> taskComments = new HashSet<TaskComment>(0);
     private Set<Application> applications = new HashSet<Application>(0);
     private Set<GroupMembers> groupMemberses = new HashSet<GroupMembers>(0);
     private Set<Groups> groupses = new HashSet<Groups>(0);
     private Set<Schedule> schedules = new HashSet<Schedule>(0);
     private Set<ScheduleWatcher> scheduleWatchers = new HashSet<ScheduleWatcher>(0);
     private Set<Task> tasks = new HashSet<Task>(0);
     private Set<PollParticipant> pollParticipants = new HashSet<PollParticipant>(0);
     private Set<Users> userses = new HashSet<Users>(0);
     private Set<Project> projects = new HashSet<Project>(0);
     private Set<Company> companiesForCreator = new HashSet<Company>(0);
     private Set<Company> companiesForModifyBy = new HashSet<Company>(0);
     private Set<TaskParticipant> taskParticipants = new HashSet<TaskParticipant>(0);
     private Set<ProjectParticipant> projectParticipants = new HashSet<ProjectParticipant>(0);
     private Set<IssueComment> issueComments = new HashSet<IssueComment>(0);
     private Set<ApprovalType> approvalTypes = new HashSet<ApprovalType>(0);
     private Set<Issue> issues = new HashSet<Issue>(0);

    public Users() {
    }

	
    public Users(String password, Long enabled) {
        this.password = password;
        this.enabled = enabled;
    }
    public Users(SysDicD sysDicDByLocale, SysDicD sysDicDByStatus, Company company, District district, Document documentByPic, SysDicD sysDicDByScheme, SysDicD sysDicDByGender, Users users, Document documentByCv, Date creationTime, String username, String mobile, String email, String password, String idcard, String name, String address, String ext, String ENo, String school, Date gradTime, String major, String degree, String emergencyHp, Long enabled, String description, Date birthday, Date lastLogin, String token, Long userType, Set<Document> documentsForModifyBy, Set<ApprovalCc> approvalCcs, Set<Document> documentsForCreator, Set<Feedback> feedbacks, Set<Notice> notices, Set<Poll> polls, Set<ApprovalPhase> approvalPhases, Set<TaskComment> taskComments, Set<Application> applications, Set<GroupMembers> groupMemberses, Set<Groups> groupses, Set<Schedule> schedules, Set<ScheduleWatcher> scheduleWatchers, Set<Task> tasks, Set<PollParticipant> pollParticipants, Set<Users> userses, Set<Project> projects, Set<Company> companiesForCreator, Set<Company> companiesForModifyBy, Set<TaskParticipant> taskParticipants, Set<ProjectParticipant> projectParticipants, Set<IssueComment> issueComments, Set<ApprovalType> approvalTypes, Set<Issue> issues) {
       this.sysDicDByLocale = sysDicDByLocale;
       this.sysDicDByStatus = sysDicDByStatus;
       this.company = company;
       this.district = district;
       this.documentByPic = documentByPic;
       this.sysDicDByScheme = sysDicDByScheme;
       this.sysDicDByGender = sysDicDByGender;
       this.users = users;
       this.documentByCv = documentByCv;
       this.creationTime = creationTime;
       this.username = username;
       this.mobile = mobile;
       this.email = email;
       this.password = password;
       this.idcard = idcard;
       this.name = name;
       this.address = address;
       this.ext = ext;
       this.ENo = ENo;
       this.school = school;
       this.gradTime = gradTime;
       this.major = major;
       this.degree = degree;
       this.emergencyHp = emergencyHp;
       this.enabled = enabled;
       this.description = description;
       this.birthday = birthday;
       this.lastLogin = lastLogin;
       this.token = token;
       this.userType = userType;
       this.documentsForModifyBy = documentsForModifyBy;
       this.approvalCcs = approvalCcs;
       this.documentsForCreator = documentsForCreator;
       this.feedbacks = feedbacks;
       this.notices = notices;
       this.polls = polls;
       this.approvalPhases = approvalPhases;
       this.taskComments = taskComments;
       this.applications = applications;
       this.groupMemberses = groupMemberses;
       this.groupses = groupses;
       this.schedules = schedules;
       this.scheduleWatchers = scheduleWatchers;
       this.tasks = tasks;
       this.pollParticipants = pollParticipants;
       this.userses = userses;
       this.projects = projects;
       this.companiesForCreator = companiesForCreator;
       this.companiesForModifyBy = companiesForModifyBy;
       this.taskParticipants = taskParticipants;
       this.projectParticipants = projectParticipants;
       this.issueComments = issueComments;
       this.approvalTypes = approvalTypes;
       this.issues = issues;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ID_USER", unique=true, nullable=false)
    public Long getIdUser() {
        return this.idUser;
    }
    
    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="LOCALE")
    public SysDicD getSysDicDByLocale() {
        return this.sysDicDByLocale;
    }
    
    public void setSysDicDByLocale(SysDicD sysDicDByLocale) {
        this.sysDicDByLocale = sysDicDByLocale;
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
    @JoinColumn(name="ID_COMPANY")
    public Company getCompany() {
        return this.company;
    }
    
    public void setCompany(Company company) {
        this.company = company;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_DISTRICT")
    public District getDistrict() {
        return this.district;
    }
    
    public void setDistrict(District district) {
        this.district = district;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PIC")
    public Document getDocumentByPic() {
        return this.documentByPic;
    }
    
    public void setDocumentByPic(Document documentByPic) {
        this.documentByPic = documentByPic;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SCHEME")
    public SysDicD getSysDicDByScheme() {
        return this.sysDicDByScheme;
    }
    
    public void setSysDicDByScheme(SysDicD sysDicDByScheme) {
        this.sysDicDByScheme = sysDicDByScheme;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="GENDER")
    public SysDicD getSysDicDByGender() {
        return this.sysDicDByGender;
    }
    
    public void setSysDicDByGender(SysDicD sysDicDByGender) {
        this.sysDicDByGender = sysDicDByGender;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CREATOR")
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CV")
    public Document getDocumentByCv() {
        return this.documentByCv;
    }
    
    public void setDocumentByCv(Document documentByCv) {
        this.documentByCv = documentByCv;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="CREATION_TIME", length=10)
    public Date getCreationTime() {
        return this.creationTime;
    }
    
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
    
    @Column(name="USERNAME", length=64)
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    @Column(name="MOBILE", length=64)
    public String getMobile() {
        return this.mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    @Column(name="EMAIL", length=64)
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Column(name="PASSWORD", nullable=false, length=64)
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Column(name="IDCARD", length=18)
    public String getIdcard() {
        return this.idcard;
    }
    
    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
    
    @Column(name="NAME", length=64)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="ADDRESS", length=128)
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    @Column(name="EXT", length=20)
    public String getExt() {
        return this.ext;
    }
    
    public void setExt(String ext) {
        this.ext = ext;
    }
    
    @Column(name="E_NO", length=20)
    public String getENo() {
        return this.ENo;
    }
    
    public void setENo(String ENo) {
        this.ENo = ENo;
    }
    
    @Column(name="SCHOOL", length=128)
    public String getSchool() {
        return this.school;
    }
    
    public void setSchool(String school) {
        this.school = school;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="GRAD_TIME", length=10)
    public Date getGradTime() {
        return this.gradTime;
    }
    
    public void setGradTime(Date gradTime) {
        this.gradTime = gradTime;
    }
    
    @Column(name="MAJOR", length=64)
    public String getMajor() {
        return this.major;
    }
    
    public void setMajor(String major) {
        this.major = major;
    }
    
    @Column(name="DEGREE", length=64)
    public String getDegree() {
        return this.degree;
    }
    
    public void setDegree(String degree) {
        this.degree = degree;
    }
    
    @Column(name="EMERGENCY_HP", length=64)
    public String getEmergencyHp() {
        return this.emergencyHp;
    }
    
    public void setEmergencyHp(String emergencyHp) {
        this.emergencyHp = emergencyHp;
    }
    
    @Column(name="ENABLED", nullable=false)
    public Long getEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(Long enabled) {
        this.enabled = enabled;
    }
    
    @Column(name="DESCRIPTION", length=256)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="BIRTHDAY", length=10)
    public Date getBirthday() {
        return this.birthday;
    }
    
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LAST_LOGIN", length=19)
    public Date getLastLogin() {
        return this.lastLogin;
    }
    
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
    
    @Column(name="TOKEN", length=128)
    public String getToken() {
        return this.token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    @Column(name="USER_TYPE")
    public Long getUserType() {
        return this.userType;
    }
    
    public void setUserType(Long userType) {
        this.userType = userType;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="usersByModifyBy")
    public Set<Document> getDocumentsForModifyBy() {
        return this.documentsForModifyBy;
    }
    
    public void setDocumentsForModifyBy(Set<Document> documentsForModifyBy) {
        this.documentsForModifyBy = documentsForModifyBy;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")
    public Set<ApprovalCc> getApprovalCcs() {
        return this.approvalCcs;
    }
    
    public void setApprovalCcs(Set<ApprovalCc> approvalCcs) {
        this.approvalCcs = approvalCcs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="usersByCreator")
    public Set<Document> getDocumentsForCreator() {
        return this.documentsForCreator;
    }
    
    public void setDocumentsForCreator(Set<Document> documentsForCreator) {
        this.documentsForCreator = documentsForCreator;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")
    public Set<Feedback> getFeedbacks() {
        return this.feedbacks;
    }
    
    public void setFeedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")
    public Set<Notice> getNotices() {
        return this.notices;
    }
    
    public void setNotices(Set<Notice> notices) {
        this.notices = notices;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")
    public Set<Poll> getPolls() {
        return this.polls;
    }
    
    public void setPolls(Set<Poll> polls) {
        this.polls = polls;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")
    public Set<ApprovalPhase> getApprovalPhases() {
        return this.approvalPhases;
    }
    
    public void setApprovalPhases(Set<ApprovalPhase> approvalPhases) {
        this.approvalPhases = approvalPhases;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")
    public Set<TaskComment> getTaskComments() {
        return this.taskComments;
    }
    
    public void setTaskComments(Set<TaskComment> taskComments) {
        this.taskComments = taskComments;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")
    public Set<Application> getApplications() {
        return this.applications;
    }
    
    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")
    public Set<GroupMembers> getGroupMemberses() {
        return this.groupMemberses;
    }
    
    public void setGroupMemberses(Set<GroupMembers> groupMemberses) {
        this.groupMemberses = groupMemberses;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")
    public Set<Groups> getGroupses() {
        return this.groupses;
    }
    
    public void setGroupses(Set<Groups> groupses) {
        this.groupses = groupses;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")
    public Set<Schedule> getSchedules() {
        return this.schedules;
    }
    
    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")
    public Set<ScheduleWatcher> getScheduleWatchers() {
        return this.scheduleWatchers;
    }
    
    public void setScheduleWatchers(Set<ScheduleWatcher> scheduleWatchers) {
        this.scheduleWatchers = scheduleWatchers;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")
    public Set<Task> getTasks() {
        return this.tasks;
    }
    
    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")
    public Set<PollParticipant> getPollParticipants() {
        return this.pollParticipants;
    }
    
    public void setPollParticipants(Set<PollParticipant> pollParticipants) {
        this.pollParticipants = pollParticipants;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")
    public Set<Users> getUserses() {
        return this.userses;
    }
    
    public void setUserses(Set<Users> userses) {
        this.userses = userses;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")
    public Set<Project> getProjects() {
        return this.projects;
    }
    
    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="usersByCreator")
    public Set<Company> getCompaniesForCreator() {
        return this.companiesForCreator;
    }
    
    public void setCompaniesForCreator(Set<Company> companiesForCreator) {
        this.companiesForCreator = companiesForCreator;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="usersByModifyBy")
    public Set<Company> getCompaniesForModifyBy() {
        return this.companiesForModifyBy;
    }
    
    public void setCompaniesForModifyBy(Set<Company> companiesForModifyBy) {
        this.companiesForModifyBy = companiesForModifyBy;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")
    public Set<TaskParticipant> getTaskParticipants() {
        return this.taskParticipants;
    }
    
    public void setTaskParticipants(Set<TaskParticipant> taskParticipants) {
        this.taskParticipants = taskParticipants;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")
    public Set<ProjectParticipant> getProjectParticipants() {
        return this.projectParticipants;
    }
    
    public void setProjectParticipants(Set<ProjectParticipant> projectParticipants) {
        this.projectParticipants = projectParticipants;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")
    public Set<IssueComment> getIssueComments() {
        return this.issueComments;
    }
    
    public void setIssueComments(Set<IssueComment> issueComments) {
        this.issueComments = issueComments;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")
    public Set<ApprovalType> getApprovalTypes() {
        return this.approvalTypes;
    }
    
    public void setApprovalTypes(Set<ApprovalType> approvalTypes) {
        this.approvalTypes = approvalTypes;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")
    public Set<Issue> getIssues() {
        return this.issues;
    }
    
    public void setIssues(Set<Issue> issues) {
        this.issues = issues;
    }




}


