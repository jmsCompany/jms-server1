package com.jms.domain.ws.store;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


public class WSSMtf {

	     private Long idMt;
	     private String type;
	     private Long typeId;
	     private String company;
	     private Long companyId;
	     private Long toStkId;
	     private String ToStk;
	     private Long fromStkId;
	     private String fromStk;
	     private Long statusId;
	     private String status;
	     private String empMtUser;
	     private Long empMtUserId;
	     private String recMtUser;
	     private Long recMtUserId;
	     private String mtNo;
	     private Date dateMt;
	     private Date creationTime;
	     
	     private Long coCompanyId;
	     private Long poId;
	     
	     private Long idWo;
	     
	     private Map<String,WSSMtfMaterial> smtfItems = new LinkedHashMap<String,WSSMtfMaterial>(0);
		public Long getIdMt() {
			return idMt;
		}
		public void setIdMt(Long idMt) {
			this.idMt = idMt;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public Long getTypeId() {
			return typeId;
		}
		public void setTypeId(Long typeId) {
			this.typeId = typeId;
		}
		public String getCompany() {
			return company;
		}
		public void setCompany(String company) {
			this.company = company;
		}
		public Long getCompanyId() {
			return companyId;
		}
		public void setCompanyId(Long companyId) {
			this.companyId = companyId;
		}

		public String getToStk() {
			return ToStk;
		}
		public void setToStk(String toStk) {
			ToStk = toStk;
		}
		public Long getFromStkId() {
			return fromStkId;
		}
		public void setFromStkId(Long fromStkId) {
			this.fromStkId = fromStkId;
		}
		public String getFromStk() {
			return fromStk;
		}
		public void setFromStk(String fromStk) {
			this.fromStk = fromStk;
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
		public String getEmpMtUser() {
			return empMtUser;
		}
		public void setEmpMtUser(String empMtUser) {
			this.empMtUser = empMtUser;
		}
		public Long getEmpMtUserId() {
			return empMtUserId;
		}
		public void setEmpMtUserId(Long empMtUserId) {
			this.empMtUserId = empMtUserId;
		}
		public String getRecMtUser() {
			return recMtUser;
		}
		public void setRecMtUser(String recMtUser) {
			this.recMtUser = recMtUser;
		}
		public Long getRecMtUserId() {
			return recMtUserId;
		}
		public void setRecMtUserId(Long recMtUserId) {
			this.recMtUserId = recMtUserId;
		}
		public String getMtNo() {
			return mtNo;
		}
		public void setMtNo(String mtNo) {
			this.mtNo = mtNo;
		}
		public Date getDateMt() {
			return dateMt;
		}
		public void setDateMt(Date dateMt) {
			this.dateMt = dateMt;
		}
		public Date getCreationTime() {
			return creationTime;
		}
		public void setCreationTime(Date creationTime) {
			this.creationTime = creationTime;
		}
	
		public Map<String,WSSMtfMaterial> getSmtfItems() {
			return smtfItems;
		}
		public void setSmtfItems(Map<String,WSSMtfMaterial> smtfItems) {
			this.smtfItems = smtfItems;
		}
		public Long getToStkId() {
			return toStkId;
		}
		public void setToStkId(Long toStkId) {
			this.toStkId = toStkId;
		}
		public Long getPoId() {
			return poId;
		}
		public void setPoId(Long poId) {
			this.poId = poId;
		}
		public Long getCoCompanyId() {
			return coCompanyId;
		}
		public void setCoCompanyId(Long coCompanyId) {
			this.coCompanyId = coCompanyId;
		}
		public Long getIdWo() {
			return idWo;
		}
		public void setIdWo(Long idWo) {
			this.idWo = idWo;
		}
   
}
