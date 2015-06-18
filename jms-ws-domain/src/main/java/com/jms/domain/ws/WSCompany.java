package com.jms.domain.ws;

import java.util.Date;

public class WSCompany implements java.io.Serializable{
	
	private static final long serialVersionUID = -677955905886087907L;
	private Integer idCompany;
    private WSUser wsUsers;
    private String companyName;
    private String description;
    private Date creationTime;
    private WSSysDicD companyCategory;
    private WSSysDicD companySize;
    private WSSysDicD companyType;
    private WSSysDicD companyNature;
    private WSSysDicD scheme;
    private WSSysDicD taskType;
    private WSSysDicD locale;
    private  Float  usedSpace;
    private int enabled;
    private Integer verified;
    private String telephone;
    private String fax;
    private String email;
    private String establishPerson;
    private Date establishmentTime;
    private String address;
    private String postcode;
    private String url;
    private Float space;
    private Integer msgAvailableNum;
    private Integer msgUsedNum;
    private WSProvince wsProvince;
    private WSCity wsCity; 
    private WSDistrict wsDistrict;

   public WSCompany() {
   }

	
   public WSCompany(WSUser users, String companyName, Date creationTime) {
	   this.wsUsers = users;
       this.companyName = companyName;
       this.creationTime = creationTime;
 
   }
  
   public Integer getIdCompany() {
       return this.idCompany;
   }
   
   public void setIdCompany(Integer idCompany) {
       this.idCompany = idCompany;
   }


	public String getDescription() {
		return description;
	}
	
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public WSUser getWsUsers() {
		return wsUsers;
	}
	
	
	public void setWsUsers(WSUser users) {
		this.wsUsers = users;
	}
	
	
	public String getCompanyName() {
		return companyName;
	}
	
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
	public Date getCreationTime() {
		return creationTime;
	}
	
	
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	
	public Float getUsedSpace() {
		return usedSpace;
	}


	public void setUsedSpace(Float usedSpace) {
		this.usedSpace = usedSpace;
	}

	public int getEnabled() {
		return enabled;
	}


	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public Integer getVerified() {
		return verified;
	}


	public void setVerified(Integer verified) {
		this.verified = verified;
	}


	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public String getFax() {
		return fax;
	}


	public void setFax(String fax) {
		this.fax = fax;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Date getEstablishmentTime() {
		return establishmentTime;
	}


	public void setEstablishmentTime(Date establishmentTime) {
		this.establishmentTime = establishmentTime;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getPostcode() {
		return postcode;
	}


	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}

	public WSSysDicD getCompanySize() {
		return companySize;
	}


	public void setCompanySize(WSSysDicD companySize) {
		this.companySize = companySize;
	}


	public WSSysDicD getCompanyType() {
		return companyType;
	}


	public void setCompanyType(WSSysDicD companyType) {
		this.companyType = companyType;
	}


	public WSSysDicD getCompanyNature() {
		return companyNature;
	}


	public void setCompanyNature(WSSysDicD companyNature) {
		this.companyNature = companyNature;
	}


	public WSDistrict getWsDistrict() {
		return wsDistrict;
	}


	public void setWsDistrict(WSDistrict wsDistrict) {
		this.wsDistrict = wsDistrict;
	}


	public WSCity getWsCity() {
		return wsCity;
	}


	public void setWsCity(WSCity wsCity) {
		this.wsCity = wsCity;
	}




	public WSProvince getWsProvince() {
		return wsProvince;
	}


	public void setWsProvince(WSProvince wsProvince) {
		this.wsProvince = wsProvince;
	}

	public WSSysDicD getLocale() {
		return locale;
	}


	public void setLocale(WSSysDicD locale) {
		this.locale = locale;
	}


	public String getEstablishPerson() {
		return establishPerson;
	}


	public void setEstablishPerson(String establishPerson) {
		this.establishPerson = establishPerson;
	}


	public WSSysDicD getScheme() {
		return scheme;
	}


	public void setScheme(WSSysDicD scheme) {
		this.scheme = scheme;
	}


	public WSSysDicD getTaskType() {
		return taskType;
	}


	public void setTaskType(WSSysDicD taskType) {
		this.taskType = taskType;
	}


	public WSSysDicD getCompanyCategory() {
		return companyCategory;
	}


	public void setCompanyCategory(WSSysDicD companyCategory) {
		this.companyCategory = companyCategory;
	}


	public Integer getMsgAvailableNum() {
		return msgAvailableNum;
	}


	public void setMsgAvailableNum(Integer msgAvailableNum) {
		this.msgAvailableNum = msgAvailableNum;
	}


	public Integer getMsgUsedNum() {
		return msgUsedNum;
	}


	public void setMsgUsedNum(Integer msgUsedNum) {
		this.msgUsedNum = msgUsedNum;
	}


	public Float getSpace() {
		return space;
	}


	public void setSpace(Float space) {
		this.space = space;
	}





}
