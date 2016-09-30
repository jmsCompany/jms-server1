package com.jms.domain.ws.q;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;




public class WSQNcr2  implements java.io.Serializable{
	   
	private Long idNcr;
	     private Long idQNoProcess;
	     private String ncrNo;
	     private Long who;
	     private Date when1;
	     private String where1;
	     private String howMuch;
	     private String how;
	     private String what;
	     private String pic1OrgFilename;
	     private String pic1Filename;
	     private String pic2OrgFilename;
	     private String picFilename;
	     private String emergencyAction;
	     private Date actionDate;
	     private String car;
	     private String q8d;
	     private Date deadline;
	     private Long respnose;
	     private Date date;
	     
	     private Long idMaterial;
	     
	     private String whoName;
	     private String respnoseName;
	     
	     private Long idCar;
	     private Long idQ8d;
	    
	     private String carNo;
	     private String q8dNo;
	     
	     private String material;
	     
	     private List<WSQCar> cars = new ArrayList<WSQCar>(0);
	     private List<WSQG8d> g8ds = new ArrayList<WSQG8d>(0);
	
	     public Long getIdNcr() {
			return idNcr;
		}
		public void setIdNcr(Long idNcr) {
			this.idNcr = idNcr;
		}
		public Long getIdQNoProcess() {
			return idQNoProcess;
		}
		public void setIdQNoProcess(Long idQNoProcess) {
			this.idQNoProcess = idQNoProcess;
		}
		public String getNcrNo() {
			return ncrNo;
		}
		public void setNcrNo(String ncrNo) {
			this.ncrNo = ncrNo;
		}
		public Long getWho() {
			return who;
		}
		public void setWho(Long who) {
			this.who = who;
		}
		public Date getWhen1() {
			return when1;
		}
		public void setWhen1(Date when1) {
			this.when1 = when1;
		}
		public String getWhere1() {
			return where1;
		}
		public void setWhere1(String where1) {
			this.where1 = where1;
		}
		public String getHowMuch() {
			return howMuch;
		}
		public void setHowMuch(String howMuch) {
			this.howMuch = howMuch;
		}
		public String getHow() {
			return how;
		}
		public void setHow(String how) {
			this.how = how;
		}
		public String getWhat() {
			return what;
		}
		public void setWhat(String what) {
			this.what = what;
		}
		public String getPic1OrgFilename() {
			return pic1OrgFilename;
		}
		public void setPic1OrgFilename(String pic1OrgFilename) {
			this.pic1OrgFilename = pic1OrgFilename;
		}
		public String getPic1Filename() {
			return pic1Filename;
		}
		public void setPic1Filename(String pic1Filename) {
			this.pic1Filename = pic1Filename;
		}
		public String getPic2OrgFilename() {
			return pic2OrgFilename;
		}
		public void setPic2OrgFilename(String pic2OrgFilename) {
			this.pic2OrgFilename = pic2OrgFilename;
		}
		public String getPicFilename() {
			return picFilename;
		}
		public void setPicFilename(String picFilename) {
			this.picFilename = picFilename;
		}
		public String getEmergencyAction() {
			return emergencyAction;
		}
		public void setEmergencyAction(String emergencyAction) {
			this.emergencyAction = emergencyAction;
		}
		public Date getActionDate() {
			return actionDate;
		}
		public void setActionDate(Date actionDate) {
			this.actionDate = actionDate;
		}
		public String getCar() {
			return car;
		}
		public void setCar(String car) {
			this.car = car;
		}
		public String getQ8d() {
			return q8d;
		}
		public void setQ8d(String q8d) {
			this.q8d = q8d;
		}
		public Date getDeadline() {
			return deadline;
		}
		public void setDeadline(Date deadline) {
			this.deadline = deadline;
		}
		public Long getRespnose() {
			return respnose;
		}
		public void setRespnose(Long respnose) {
			this.respnose = respnose;
		}
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		public List<WSQCar> getCars() {
			return cars;
		}
		public void setCars(List<WSQCar> cars) {
			this.cars = cars;
		}
		public List<WSQG8d> getG8ds() {
			return g8ds;
		}
		public void setG8ds(List<WSQG8d> g8ds) {
			this.g8ds = g8ds;
		}
		public Long getIdMaterial() {
			return idMaterial;
		}
		public void setIdMaterial(Long idMaterial) {
			this.idMaterial = idMaterial;
		}
		public String getWhoName() {
			return whoName;
		}
		public void setWhoName(String whoName) {
			this.whoName = whoName;
		}
		public String getRespnoseName() {
			return respnoseName;
		}
		public void setRespnoseName(String respnoseName) {
			this.respnoseName = respnoseName;
		}
		public Long getIdCar() {
			return idCar;
		}
		public void setIdCar(Long idCar) {
			this.idCar = idCar;
		}
		public Long getIdQ8d() {
			return idQ8d;
		}
		public void setIdQ8d(Long idQ8d) {
			this.idQ8d = idQ8d;
		}
		public String getCarNo() {
			return carNo;
		}
		public void setCarNo(String carNo) {
			this.carNo = carNo;
		}
		public String getQ8dNo() {
			return q8dNo;
		}
		public void setQ8dNo(String q8dNo) {
			this.q8dNo = q8dNo;
		}
		public String getMaterial() {
			return material;
		}
		public void setMaterial(String material) {
			this.material = material;
		}
}
