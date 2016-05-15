package com.jms.domain.ws.store;

import java.util.Date;

public class WSSMtfInfo {

	     private String mtNo;
	     private String type;
	     private String material;
	     private String lotNo;
	     private String fromBin;
	     private String toBin;
	     private Long qty;
	     private String empMtUser;
	     private String recMtUser;
	     private Date dateMt;
    
		public String getMtNo() {
			return mtNo;
		}

		public void setMtNo(String mtNo) {
			this.mtNo = mtNo;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getMaterial() {
			return material;
		}

		public void setMaterial(String material) {
			this.material = material;
		}

		public String getLotNo() {
			return lotNo;
		}

		public void setLotNo(String lotNo) {
			this.lotNo = lotNo;
		}

		public String getFromBin() {
			return fromBin;
		}

		public void setFromBin(String fromBin) {
			this.fromBin = fromBin;
		}

		public String getToBin() {
			return toBin;
		}

		public void setToBin(String toBin) {
			this.toBin = toBin;
		}

		public Long getQty() {
			return qty;
		}

		public void setQty(Long qty) {
			this.qty = qty;
		}

		public String getEmpMtUser() {
			return empMtUser;
		}

		public void setEmpMtUser(String empMtUser) {
			this.empMtUser = empMtUser;
		}

		public String getRecMtUser() {
			return recMtUser;
		}

		public void setRecMtUser(String recMtUser) {
			this.recMtUser = recMtUser;
		}

		public Date getDateMt() {
			return dateMt;
		}

		public void setDateMt(Date dateMt) {
			this.dateMt = dateMt;
		}
	 
   
}
