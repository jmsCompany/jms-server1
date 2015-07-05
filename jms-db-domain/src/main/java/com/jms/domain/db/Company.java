package com.jms.domain.db;
// Generated 2015-7-5 10:54:07 by Hibernate Tools 3.2.2.GA


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
import javax.persistence.Transient;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.jms.domain.db.Users;

/**
 * Company generated by hbm2java
 */
@Audited
@AuditTable(catalog="jms_aud", value = "company")
@Entity
@Table(name="company"
    ,catalog="jms"
)
public class Company extends AbstractSecuredEntity implements java.io.Serializable {


     private Long idCompany;
     private SysDicD sysDicDByCompanyCatorgory;
     private SysDicD sysDicDByLocale;
     private District district;
     private Users usersByModifyBy;
     private SysDicD sysDicDByScheme;
     private Document documentByLogo;
     private Users usersByCreator;
     private SysDicD sysDicDByCompanySize;
     private SysDicD sysDicDByCompanyType;
     private SysDicD sysDicDByCompanyNature;
     private Document documentByLicence;
     private SysDicD sysDicDByTaskType;
     private String companyName;
     private String description;
     private Date creationTime;
     private Date modifyTime;
     private String establishPerson;
     private Date establishmentTime;
     private String email;
     private String postcode;
     private String address;
     private Long verified;
     private String telephone;
     private String fax;
     private String url;
     private Long enabled;
     private Float usedSpace;
     private Long msgUsedNum;
     private Long msgAvailableNum;
     private Float space;
     private Set<Users> userses = new HashSet<Users>(0);
     private Set<CompanyApp> companyApps = new HashSet<CompanyApp>(0);
     private Set<Project> projects = new HashSet<Project>(0);
     private Set<Notification> notifications = new HashSet<Notification>(0);
     private Set<Sector> sectors = new HashSet<Sector>(0);
     private Set<BuyRecord> buyRecords = new HashSet<BuyRecord>(0);
     private Set<Groups> groupses = new HashSet<Groups>(0);
     private Set<Receipt> receipts = new HashSet<Receipt>(0);
     private Set<Roles> roleses = new HashSet<Roles>(0);
     private Set<ApprovalType> approvalTypes = new HashSet<ApprovalType>(0);

    public Company() {
    }

	
    public Company(Users usersByCreator, String companyName, Long enabled) {
        this.usersByCreator = usersByCreator;
        this.companyName = companyName;
        this.enabled = enabled;
    }
    public Company(SysDicD sysDicDByCompanyCatorgory, SysDicD sysDicDByLocale, District district, Users usersByModifyBy, SysDicD sysDicDByScheme, Document documentByLogo, Users usersByCreator, SysDicD sysDicDByCompanySize, SysDicD sysDicDByCompanyType, SysDicD sysDicDByCompanyNature, Document documentByLicence, SysDicD sysDicDByTaskType, String companyName, String description, Date creationTime, Date modifyTime, String establishPerson, Date establishmentTime, String email, String postcode, String address, Long verified, String telephone, String fax, String url, Long enabled, Float usedSpace, Long msgUsedNum, Long msgAvailableNum, Float space, Set<Users> userses, Set<CompanyApp> companyApps, Set<Project> projects, Set<Notification> notifications, Set<Sector> sectors, Set<BuyRecord> buyRecords, Set<Groups> groupses, Set<Receipt> receipts, Set<Roles> roleses, Set<ApprovalType> approvalTypes) {
       this.sysDicDByCompanyCatorgory = sysDicDByCompanyCatorgory;
       this.sysDicDByLocale = sysDicDByLocale;
       this.district = district;
       this.usersByModifyBy = usersByModifyBy;
       this.sysDicDByScheme = sysDicDByScheme;
       this.documentByLogo = documentByLogo;
       this.usersByCreator = usersByCreator;
       this.sysDicDByCompanySize = sysDicDByCompanySize;
       this.sysDicDByCompanyType = sysDicDByCompanyType;
       this.sysDicDByCompanyNature = sysDicDByCompanyNature;
       this.documentByLicence = documentByLicence;
       this.sysDicDByTaskType = sysDicDByTaskType;
       this.companyName = companyName;
       this.description = description;
       this.creationTime = creationTime;
       this.modifyTime = modifyTime;
       this.establishPerson = establishPerson;
       this.establishmentTime = establishmentTime;
       this.email = email;
       this.postcode = postcode;
       this.address = address;
       this.verified = verified;
       this.telephone = telephone;
       this.fax = fax;
       this.url = url;
       this.enabled = enabled;
       this.usedSpace = usedSpace;
       this.msgUsedNum = msgUsedNum;
       this.msgAvailableNum = msgAvailableNum;
       this.space = space;
       this.userses = userses;
       this.companyApps = companyApps;
       this.projects = projects;
       this.notifications = notifications;
       this.sectors = sectors;
       this.buyRecords = buyRecords;
       this.groupses = groupses;
       this.receipts = receipts;
       this.roleses = roleses;
       this.approvalTypes = approvalTypes;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ID_COMPANY", unique=true, nullable=false)
    public Long getIdCompany() {
        return this.idCompany;
    }
    
    public void setIdCompany(Long idCompany) {
        this.idCompany = idCompany;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="COMPANY_CATORGORY")
    public SysDicD getSysDicDByCompanyCatorgory() {
        return this.sysDicDByCompanyCatorgory;
    }
    
    public void setSysDicDByCompanyCatorgory(SysDicD sysDicDByCompanyCatorgory) {
        this.sysDicDByCompanyCatorgory = sysDicDByCompanyCatorgory;
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
    @JoinColumn(name="ID_DISTRICT")
    public District getDistrict() {
        return this.district;
    }
    
    public void setDistrict(District district) {
        this.district = district;
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
    @JoinColumn(name="SCHEME")
    public SysDicD getSysDicDByScheme() {
        return this.sysDicDByScheme;
    }
    
    public void setSysDicDByScheme(SysDicD sysDicDByScheme) {
        this.sysDicDByScheme = sysDicDByScheme;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="LOGO")
    public Document getDocumentByLogo() {
        return this.documentByLogo;
    }
    
    public void setDocumentByLogo(Document documentByLogo) {
        this.documentByLogo = documentByLogo;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CREATOR", nullable=false)
    public Users getUsersByCreator() {
        return this.usersByCreator;
    }
    
    public void setUsersByCreator(Users usersByCreator) {
        this.usersByCreator = usersByCreator;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="COMPANY_SIZE")
    public SysDicD getSysDicDByCompanySize() {
        return this.sysDicDByCompanySize;
    }
    
    public void setSysDicDByCompanySize(SysDicD sysDicDByCompanySize) {
        this.sysDicDByCompanySize = sysDicDByCompanySize;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="COMPANY_TYPE")
    public SysDicD getSysDicDByCompanyType() {
        return this.sysDicDByCompanyType;
    }
    
    public void setSysDicDByCompanyType(SysDicD sysDicDByCompanyType) {
        this.sysDicDByCompanyType = sysDicDByCompanyType;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Company_Nature")
    public SysDicD getSysDicDByCompanyNature() {
        return this.sysDicDByCompanyNature;
    }
    
    public void setSysDicDByCompanyNature(SysDicD sysDicDByCompanyNature) {
        this.sysDicDByCompanyNature = sysDicDByCompanyNature;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="LICENCE")
    public Document getDocumentByLicence() {
        return this.documentByLicence;
    }
    
    public void setDocumentByLicence(Document documentByLicence) {
        this.documentByLicence = documentByLicence;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="TASK_TYPE")
    public SysDicD getSysDicDByTaskType() {
        return this.sysDicDByTaskType;
    }
    
    public void setSysDicDByTaskType(SysDicD sysDicDByTaskType) {
        this.sysDicDByTaskType = sysDicDByTaskType;
    }
    
    @Column(name="COMPANY_NAME", nullable=false, length=64)
    public String getCompanyName() {
        return this.companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    @Column(name="DESCRIPTION", length=1024)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
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
    
    @Column(name="ESTABLISH_PERSON", length=20)
    public String getEstablishPerson() {
        return this.establishPerson;
    }
    
    public void setEstablishPerson(String establishPerson) {
        this.establishPerson = establishPerson;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="ESTABLISHMENT_TIME", length=10)
    public Date getEstablishmentTime() {
        return this.establishmentTime;
    }
    
    public void setEstablishmentTime(Date establishmentTime) {
        this.establishmentTime = establishmentTime;
    }
    
    @Column(name="EMAIL", length=64)
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Column(name="POSTCODE", length=20)
    public String getPostcode() {
        return this.postcode;
    }
    
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    
    @Column(name="ADDRESS", length=128)
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    @Column(name="VERIFIED")
    public Long getVerified() {
        return this.verified;
    }
    
    public void setVerified(Long verified) {
        this.verified = verified;
    }
    
    @Column(name="TELEPHONE", length=20)
    public String getTelephone() {
        return this.telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    @Column(name="FAX", length=20)
    public String getFax() {
        return this.fax;
    }
    
    public void setFax(String fax) {
        this.fax = fax;
    }
    
    @Column(name="URL", length=128)
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Column(name="ENABLED", nullable=false)
    public Long getEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(Long enabled) {
        this.enabled = enabled;
    }
    
    @Column(name="USED_SPACE", precision=12, scale=0)
    public Float getUsedSpace() {
        return this.usedSpace;
    }
    
    public void setUsedSpace(Float usedSpace) {
        this.usedSpace = usedSpace;
    }
    
    @Column(name="MSG_USED_NUM")
    public Long getMsgUsedNum() {
        return this.msgUsedNum;
    }
    
    public void setMsgUsedNum(Long msgUsedNum) {
        this.msgUsedNum = msgUsedNum;
    }
    
    @Column(name="MSG_AVAILABLE_NUM")
    public Long getMsgAvailableNum() {
        return this.msgAvailableNum;
    }
    
    public void setMsgAvailableNum(Long msgAvailableNum) {
        this.msgAvailableNum = msgAvailableNum;
    }
    
    @Column(name="SPACE", precision=12, scale=0)
    public Float getSpace() {
        return this.space;
    }
    
    public void setSpace(Float space) {
        this.space = space;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="company")
    public Set<Users> getUserses() {
        return this.userses;
    }
    
    public void setUserses(Set<Users> userses) {
        this.userses = userses;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="company")
    public Set<CompanyApp> getCompanyApps() {
        return this.companyApps;
    }
    
    public void setCompanyApps(Set<CompanyApp> companyApps) {
        this.companyApps = companyApps;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="company")
    public Set<Project> getProjects() {
        return this.projects;
    }
    
    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="company")
    public Set<Notification> getNotifications() {
        return this.notifications;
    }
    
    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="company")
    public Set<Sector> getSectors() {
        return this.sectors;
    }
    
    public void setSectors(Set<Sector> sectors) {
        this.sectors = sectors;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="company")
    public Set<BuyRecord> getBuyRecords() {
        return this.buyRecords;
    }
    
    public void setBuyRecords(Set<BuyRecord> buyRecords) {
        this.buyRecords = buyRecords;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="company")
    public Set<Groups> getGroupses() {
        return this.groupses;
    }
    
    public void setGroupses(Set<Groups> groupses) {
        this.groupses = groupses;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="company")
    public Set<Receipt> getReceipts() {
        return this.receipts;
    }
    
    public void setReceipts(Set<Receipt> receipts) {
        this.receipts = receipts;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="company")
    public Set<Roles> getRoleses() {
        return this.roleses;
    }
    
    public void setRoleses(Set<Roles> roleses) {
        this.roleses = roleses;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="company")
    public Set<ApprovalType> getApprovalTypes() {
        return this.approvalTypes;
    }
    
    public void setApprovalTypes(Set<ApprovalType> approvalTypes) {
        this.approvalTypes = approvalTypes;
    }


    @Override @Transient
    public Long getId() {
        return this.getIdCompany();
    }
   @Override  @Transient
  	public Users getUser() {
  		return this.getUsersByCreator();
  	}

}


