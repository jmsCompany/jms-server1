package com.jms.domain.ws.store;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;



public class WSSpo {

    private Long idPo;
    private String codePo;
    private String codeCo;
    private Long sCompanyCoId;
    private Date dateOrder;
    private Long totalAmount;
    private Long taxRate;
    private Long exchange;
    private String remark;
    private String freightTerm;
    private String paymentTerm;
    private Long freightTermId;
    private Long paymentTermId;
    private String sStatus;
    private Long sStatusId;
    private String userName;
    private Long userId;
    private String sCurrencyType;
    private Long sCurrencyTypeId;
    private String sPoType;
    private Long sPoTypeId;
    private Long fileId;
    private String fileName;
    
    private Map<String,WSSpoMaterial> poItems = new LinkedHashMap<String,WSSpoMaterial>();
	
    public Long getIdPo() {
		return idPo;
	}
	public void setIdPo(Long idPo) {
		this.idPo = idPo;
	}
	public String getCodePo() {
		return codePo;
	}
	public void setCodePo(String codePo) {
		this.codePo = codePo;
	}
	public String getCodeCo() {
		return codeCo;
	}
	public void setCodeCo(String codeCo) {
		this.codeCo = codeCo;
	}
	public Long getsCompanyCoId() {
		return sCompanyCoId;
	}
	public void setsCompanyCoId(Long sCompanyCoId) {
		this.sCompanyCoId = sCompanyCoId;
	}
	public Date getDateOrder() {
		return dateOrder;
	}
	public void setDateOrder(Date dateOrder) {
		this.dateOrder = dateOrder;
	}
	public Long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Long getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(Long taxRate) {
		this.taxRate = taxRate;
	}
	public Long getExchange() {
		return exchange;
	}
	public void setExchange(Long exchange) {
		this.exchange = exchange;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFreightTerm() {
		return freightTerm;
	}
	public void setFreightTerm(String freightTerm) {
		this.freightTerm = freightTerm;
	}
	public String getPaymentTerm() {
		return paymentTerm;
	}
	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}
	public Long getFreightTermId() {
		return freightTermId;
	}
	public void setFreightTermId(Long freightTermId) {
		this.freightTermId = freightTermId;
	}
	public Long getPaymentTermId() {
		return paymentTermId;
	}
	public void setPaymentTermId(Long paymentTermId) {
		this.paymentTermId = paymentTermId;
	}
	public String getsStatus() {
		return sStatus;
	}
	public void setsStatus(String sStatus) {
		this.sStatus = sStatus;
	}
	public Long getsStatusId() {
		return sStatusId;
	}
	public void setsStatusId(Long sStatusId) {
		this.sStatusId = sStatusId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getsCurrencyType() {
		return sCurrencyType;
	}
	public void setsCurrencyType(String sCurrencyType) {
		this.sCurrencyType = sCurrencyType;
	}
	public Long getsCurrencyTypeId() {
		return sCurrencyTypeId;
	}
	public void setsCurrencyTypeId(Long sCurrencyTypeId) {
		this.sCurrencyTypeId = sCurrencyTypeId;
	}
	public String getsPoType() {
		return sPoType;
	}
	public void setsPoType(String sPoType) {
		this.sPoType = sPoType;
	}
	public Long getsPoTypeId() {
		return sPoTypeId;
	}
	public void setsPoTypeId(Long sPoTypeId) {
		this.sPoTypeId = sPoTypeId;
	}
	public Map<String,WSSpoMaterial> getPoItems() {
		return poItems;
	}
	public void setPoItems(Map<String,WSSpoMaterial> poItems) {
		this.poItems = poItems;
	}
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


   
}
