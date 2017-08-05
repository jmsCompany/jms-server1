package com.jms.domain.ws.s;

import java.util.Date;

public class WSSsoItem {

	
    private Long idSo;
    private Long sCompanyCoId;
    private Date dateOrder;
    private Float totalAmount;
    private Long materialId;
    private String remark;
    private String sStatus;
    private Long sStatusId;
    private String userName;
    private Long userId;
    private Long qtySo;
    private Float uprice;
    private Date deliveryDate;
    private Long qtyDelivered;
    private String autoRemark;
    private Long idCompany1;
    private Long idCompany2;
    
    
    
    
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
	public Float getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Float totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public Float getUprice() {
		return uprice;
	}
	public void setUprice(Float uprice) {
		this.uprice = uprice;
	}
	public Long getIdCompany2() {
		return idCompany2;
	}
	public void setIdCompany2(Long idCompany2) {
		this.idCompany2 = idCompany2;
	}
	public Long getIdCompany1() {
		return idCompany1;
	}
	public void setIdCompany1(Long idCompany1) {
		this.idCompany1 = idCompany1;
	}


   
}
