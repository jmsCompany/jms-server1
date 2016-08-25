package com.jms.domain.ws.q;

import java.util.Date;


public class WSQDeviation  implements java.io.Serializable{
	     private Long idDeviation;
	     private Long idNoProcess;
	     private String deviationNo;
	     private Long idMaterial;
	     private Long idRoutineD;
	     private Long idCompanyCo;
	     private Long issuer;
	     private Date date1;
	     private String scopePeriodMaxqty;
	     private String reason;
	     private String mfgRemark;
	     private Long mfgSign;
	     private String aeRemark;
	     private Long aeSign;
	     private String qeRemark;
	     private Long qeSign;
	     private String otherFuction;
	     private Long ofSign;
	     private Long idDept;
		public Long getIdDeviation() {
			return idDeviation;
		}
		public void setIdDeviation(Long idDeviation) {
			this.idDeviation = idDeviation;
		}
		public Long getIdNoProcess() {
			return idNoProcess;
		}
		public void setIdNoProcess(Long idNoProcess) {
			this.idNoProcess = idNoProcess;
		}
		public String getDeviationNo() {
			return deviationNo;
		}
		public void setDeviationNo(String deviationNo) {
			this.deviationNo = deviationNo;
		}
		public Long getIdMaterial() {
			return idMaterial;
		}
		public void setIdMaterial(Long idMaterial) {
			this.idMaterial = idMaterial;
		}
		public Long getIdRoutineD() {
			return idRoutineD;
		}
		public void setIdRoutineD(Long idRoutineD) {
			this.idRoutineD = idRoutineD;
		}
		public Long getIdCompanyCo() {
			return idCompanyCo;
		}
		public void setIdCompanyCo(Long idCompanyCo) {
			this.idCompanyCo = idCompanyCo;
		}
		public Long getIssuer() {
			return issuer;
		}
		public void setIssuer(Long issuer) {
			this.issuer = issuer;
		}
		public Date getDate1() {
			return date1;
		}
		public void setDate1(Date date1) {
			this.date1 = date1;
		}
		public String getScopePeriodMaxqty() {
			return scopePeriodMaxqty;
		}
		public void setScopePeriodMaxqty(String scopePeriodMaxqty) {
			this.scopePeriodMaxqty = scopePeriodMaxqty;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		public String getMfgRemark() {
			return mfgRemark;
		}
		public void setMfgRemark(String mfgRemark) {
			this.mfgRemark = mfgRemark;
		}
		public Long getMfgSign() {
			return mfgSign;
		}
		public void setMfgSign(Long mfgSign) {
			this.mfgSign = mfgSign;
		}
		public String getAeRemark() {
			return aeRemark;
		}
		public void setAeRemark(String aeRemark) {
			this.aeRemark = aeRemark;
		}
		public Long getAeSign() {
			return aeSign;
		}
		public void setAeSign(Long aeSign) {
			this.aeSign = aeSign;
		}
		public String getQeRemark() {
			return qeRemark;
		}
		public void setQeRemark(String qeRemark) {
			this.qeRemark = qeRemark;
		}
		public Long getQeSign() {
			return qeSign;
		}
		public void setQeSign(Long qeSign) {
			this.qeSign = qeSign;
		}
		public String getOtherFuction() {
			return otherFuction;
		}
		public void setOtherFuction(String otherFuction) {
			this.otherFuction = otherFuction;
		}
		public Long getOfSign() {
			return ofSign;
		}
		public void setOfSign(Long ofSign) {
			this.ofSign = ofSign;
		}
		public Long getIdDept() {
			return idDept;
		}
		public void setIdDept(Long idDept) {
			this.idDept = idDept;
		}
}
