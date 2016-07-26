package com.jms.domain.ws.s;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class WSSpoMaterial {
	
	
    private String codePo;
    private Date dateOrder;
    private String username;
  //  private String codeCo;
    private String coShortName;
    private String sStatus;
	
	

    private Long idPoMaterial;
    private Long sStatusId;
    private Long sPoId;
    private Long sMaterialId;
    private String sMaterial;
    private Long line;
    private Long qtyPo;
    private BigDecimal uprice;
    private Long totalPrice;
    private String remark;
    private Date deliveryDate;
    private Long qtyReceived;
    private String autoRemark;
    
    
    private String rev;
    private String des;
    private String unit;
	
    public Long getIdPoMaterial() {
		return idPoMaterial;
	}
	public void setIdPoMaterial(Long idPoMaterial) {
		this.idPoMaterial = idPoMaterial;
	}
	public Long getsStatusId() {
		return sStatusId;
	}
	public void setsStatusId(Long sStatusId) {
		this.sStatusId = sStatusId;
	}
	public Long getsPoId() {
		return sPoId;
	}
	public void setsPoId(Long sPoId) {
		this.sPoId = sPoId;
	}
	public Long getsMaterialId() {
		return sMaterialId;
	}
	public void setsMaterialId(Long sMaterialId) {
		this.sMaterialId = sMaterialId;
	}
	public String getsMaterial() {
		return sMaterial;
	}
	public void setsMaterial(String sMaterial) {
		this.sMaterial = sMaterial;
	}
	public Long getLine() {
		return line;
	}
	public void setLine(Long line) {
		this.line = line;
	}
	public Long getQtyPo() {
		return qtyPo;
	}
	public void setQtyPo(Long qtyPo) {
		this.qtyPo = qtyPo;
	}

	public Long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Long getQtyReceived() {
		return qtyReceived;
	}
	public void setQtyReceived(Long qtyReceived) {
		this.qtyReceived = qtyReceived;
	}
	public String getAutoRemark() {
		return autoRemark;
	}
	public void setAutoRemark(String autoRemark) {
		this.autoRemark = autoRemark;
	}
	public String getCodePo() {
		return codePo;
	}
	public void setCodePo(String codePo) {
		this.codePo = codePo;
	}
	public Date getDateOrder() {
		return dateOrder;
	}
	public void setDateOrder(Date dateOrder) {
		this.dateOrder = dateOrder;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getsStatus() {
		return sStatus;
	}
	public void setsStatus(String sStatus) {
		this.sStatus = sStatus;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getRev() {
		return rev;
	}
	public void setRev(String rev) {
		this.rev = rev;
	}
	public BigDecimal getUprice() {
		return uprice;
	}
	public void setUprice(BigDecimal uprice) {
		this.uprice = uprice;
	}
	public String getCoShortName() {
		return coShortName;
	}
	public void setCoShortName(String coShortName) {
		this.coShortName = coShortName;
	}
   
}
