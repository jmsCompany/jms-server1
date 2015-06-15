package com.jms.domain.ws;

import java.util.Date;
import java.util.Locale.Category;

import com.jms.domain.CompanyCategoryEnum;
import com.jms.domain.EnabledEnum;
import com.jms.domain.FineTaskEnum;


public class WSCompany implements java.io.Serializable{
	
    private Integer idCompany;
    private WSUser wsUsers;
    private String companyName;
    private String description;
    private Date creationTime;
    private FineTaskEnum fineTaskEnum;
    private EnabledEnum enabledEnum;
    private CompanyCategoryEnum companyCategoryEnum;
   
    
    private WSSysDicD companySize;
    private WSSysDicD companyType;
    private WSSysDicD companyNature;
 
    
    private  Float  usedSpace;
    
    
    private WSSysDicD wsTaskType;
    private int enabled;
    private WSSysDicD locale;
    private Integer verified;
    private String telephone;
    private String fax;
    private String email;
    private Date establishmentTime;
    private String address;
    private String postcode;
    private String url;
    
    
    private WSProvince wsProvince;
    private WSCity wsCity; 
    private WSDistrict wsDistrict;

   public WSCompany() {
   }

	
   public WSCompany(WSUser users, String companyName, Date creationTime, FineTaskEnum fineTaskEnum, EnabledEnum enabledEnum) {
	   this.wsUsers = users;
       this.companyName = companyName;
       this.creationTime = creationTime;
       this.setFineTaskEnum(fineTaskEnum);
       this.setEnabledEnum(enabledEnum);
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
	
	
	public EnabledEnum getEnabledEnum() {
		return enabledEnum;
	}
	
	
	public void setEnabledEnum(EnabledEnum enabledEnum) {
		this.enabledEnum = enabledEnum;
	}
	
	
	public FineTaskEnum getFineTaskEnum() {
		return fineTaskEnum;
	}
	
	
	public void setFineTaskEnum(FineTaskEnum fineTaskEnum) {
		this.fineTaskEnum = fineTaskEnum;
	}


	public CompanyCategoryEnum getCompanyCategoryEnum() {
		return companyCategoryEnum;
	}


	public void setCompanyCategoryEnum(CompanyCategoryEnum companyCategoryEnum) {
		this.companyCategoryEnum = companyCategoryEnum;
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


	public WSSysDicD getWsTaskType() {
		return wsTaskType;
	}


	public void setWsTaskType(WSSysDicD wsTaskType) {
		this.wsTaskType = wsTaskType;
	}


	public WSSysDicD getLocale() {
		return locale;
	}


	public void setLocale(WSSysDicD locale) {
		this.locale = locale;
	}





}
