package com.jms.domain.ws.store;

import java.util.Date;

public class WSSso {

    private Long idSo;
    private String codeCo;
    private String codeSo;
    private String coOrderNo;
    private Long sCompanyCoId;
    private Date dateOrder;
    private Long totalAmount;
    private Long taxRate;
    private Long exchange;
    private Long materialId;
    private String remark;
    private String freightTerm;
    private String paymentTerm;
    private Long freightTermId;
    private Long paymentTermId;
    private String sStatus;
    private Long sStatusId;
    private String userName;
    private Long userId;
    private Long qtySo;
    private Long uprice;
    private Date deliveryDate;
    private Long qtyDelivered;
    private String autoRemark;
    

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

	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	public String getCoOrderNo() {
		return coOrderNo;
	}
	public void setCoOrderNo(String coOrderNo) {
		this.coOrderNo = coOrderNo;
	}
	public String getCodeSo() {
		return codeSo;
	}
	public void setCodeSo(String codeSo) {
		this.codeSo = codeSo;
	}
	public String getCodeCo() {
		return codeCo;
	}
	public void setCodeCo(String codeCo) {
		this.codeCo = codeCo;
	}
	public Long getIdSo() {
		return idSo;
	}
	public void setIdSo(Long idSo) {
		this.idSo = idSo;
	}
	public String getAutoRemark() {
		return autoRemark;
	}
	public void setAutoRemark(String autoRemark) {
		this.autoRemark = autoRemark;
	}
	public Long getQtyDelivered() {
		return qtyDelivered;
	}
	public void setQtyDelivered(Long qtyDelivered) {
		this.qtyDelivered = qtyDelivered;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Long getQtySo() {
		return qtySo;
	}
	public void setQtySo(Long qtySo) {
		this.qtySo = qtySo;
	}
	public Long getUprice() {
		return uprice;
	}
	public void setUprice(Long uprice) {
		this.uprice = uprice;
	}


   
}
