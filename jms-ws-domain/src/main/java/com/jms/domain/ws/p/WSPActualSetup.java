package com.jms.domain.ws.p;

import java.util.Date;


public class WSPActualSetup implements java.io.Serializable{

	private static final long serialVersionUID = -1050323000750419624L;
		
	     private Long idActualSetup;
	     private Long cppId;

	     private String woNo;
	     private String shift;
	     private String machine;
	     
	     private String pno;
	     private String rev;
	     private String des;
	     
	     private Date actSt;
	     private Date actFt;
		public Long getIdActualSetup() {
			return idActualSetup;
		}
		public void setIdActualSetup(Long idActualSetup) {
			this.idActualSetup = idActualSetup;
		}
		public Long getCppId() {
			return cppId;
		}
		public void setCppId(Long cppId) {
			this.cppId = cppId;
		}
		public String getWoNo() {
			return woNo;
		}
		public void setWoNo(String woNo) {
			this.woNo = woNo;
		}
		public String getShift() {
			return shift;
		}
		public void setShift(String shift) {
			this.shift = shift;
		}
		public String getPno() {
			return pno;
		}
		public void setPno(String pno) {
			this.pno = pno;
		}
		public String getRev() {
			return rev;
		}
		public void setRev(String rev) {
			this.rev = rev;
		}
		public String getDes() {
			return des;
		}
		public void setDes(String des) {
			this.des = des;
		}
		public Date getActSt() {
			return actSt;
		}
		public void setActSt(Date actSt) {
			this.actSt = actSt;
		}
		public Date getActFt() {
			return actFt;
		}
		public void setActFt(Date actFt) {
			this.actFt = actFt;
		}
		public String getMachine() {
			return machine;
		}
		public void setMachine(String machine) {
			this.machine = machine;
		}
   
}
