package com.jms.domain.ws.store;

import java.util.ArrayList;
import java.util.List;

public class WSCompanyCo {


    private Long id;
    private String code;
    private String shortName;
    private String name;
    private String tel;
    private String fax;
    private String addressAct;
    private String addressReg;
    private String artificialPerson;
    private String taxNo;
    private String bank;
    private String bankAccNo;
    private String url;
    private Long typeId;
    private String type;
    private Long countryId;
    private String country;
    private Long lvlId;
    private String lvl;
    private Long freightTermId;
    private String freightTerm;
    private Long paymentTermId;
    private String paymentTerm;
    private String auditBy;
    private String remark;
    private String autoRemark;
    private Long statusId;
    private String status;
    
    
    private List<WSLinkman> linkmen = new ArrayList<WSLinkman>();


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getShortName() {
		return shortName;
	}


	public void setShortName(String shortName) {
		this.shortName = shortName;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getTel() {
		return tel;
	}


	public void setTel(String tel) {
		this.tel = tel;
	}


	public String getFax() {
		return fax;
	}


	public void setFax(String fax) {
		this.fax = fax;
	}


	public String getAddressAct() {
		return addressAct;
	}


	public void setAddressAct(String addressAct) {
		this.addressAct = addressAct;
	}


	public String getAddressReg() {
		return addressReg;
	}


	public void setAddressReg(String addressReg) {
		this.addressReg = addressReg;
	}


	public String getArtificialPerson() {
		return artificialPerson;
	}


	public void setArtificialPerson(String artificialPerson) {
		this.artificialPerson = artificialPerson;
	}


	public String getTaxNo() {
		return taxNo;
	}


	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}


	public String getBank() {
		return bank;
	}


	public void setBank(String bank) {
		this.bank = bank;
	}


	public String getBankAccNo() {
		return bankAccNo;
	}


	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public Long getTypeId() {
		return typeId;
	}


	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Long getCountryId() {
		return countryId;
	}


	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public Long getLvlId() {
		return lvlId;
	}


	public void setLvlId(Long lvlId) {
		this.lvlId = lvlId;
	}


	public String getLvl() {
		return lvl;
	}


	public void setLvl(String lvl) {
		this.lvl = lvl;
	}


	public Long getFreightTermId() {
		return freightTermId;
	}


	public void setFreightTermId(Long freightTermId) {
		this.freightTermId = freightTermId;
	}


	public String getFreightTerm() {
		return freightTerm;
	}


	public void setFreightTerm(String freightTerm) {
		this.freightTerm = freightTerm;
	}


	public Long getPaymentTermId() {
		return paymentTermId;
	}


	public void setPaymentTermId(Long paymentTermId) {
		this.paymentTermId = paymentTermId;
	}


	public String getPaymentTerm() {
		return paymentTerm;
	}


	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}




	public String getAuditBy() {
		return auditBy;
	}


	public void setAuditBy(String auditBy) {
		this.auditBy = auditBy;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getAutoRemark() {
		return autoRemark;
	}


	public void setAutoRemark(String autoRemark) {
		this.autoRemark = autoRemark;
	}


	public Long getStatusId() {
		return statusId;
	}


	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public List<WSLinkman> getLinkmen() {
		return linkmen;
	}


	public void setLinkmen(List<WSLinkman> linkmen) {
		this.linkmen = linkmen;
	}
}
