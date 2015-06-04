package com.jms.domain.ws;

import java.util.Date;
import java.util.Locale.Category;

import com.jms.domain.CompanyCategoryEnum;
import com.jms.domain.EnabledEnum;
import com.jms.domain.FineTaskEnum;


public class WSCompany {
	
    private Integer idCompany;
    private WSUser wsUsers;
    private String companyName;
    private String description;
    private Date creationTime;
    private FineTaskEnum fineTaskEnum;
    private EnabledEnum enabledEnum;
    private CompanyCategoryEnum companyCategoryEnum;

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

}
