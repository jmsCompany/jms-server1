package com.jms.domain.ws.q;



public class WSQCheckList  implements java.io.Serializable{

    private Long idCheckList;
    private String smaterial;
    private Long materialId;
    private String rev;
    private String des;
    private String company;
    private Long companyId;
    private String pRoutineD;
    private Long pRoutineDId;
    private Long qTesterId;
    private String qTester;
    
    private Long qItemTypeId;
    private String qItemType;
    private String item;
    private String testMethod;
    private String specification;
    private Long frequency;
	public Long getIdCheckList() {
		return idCheckList;
	}
	public void setIdCheckList(Long idCheckList) {
		this.idCheckList = idCheckList;
	}
	public String getSmaterial() {
		return smaterial;
	}
	public void setSmaterial(String smaterial) {
		this.smaterial = smaterial;
	}
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
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
	public String getpRoutineD() {
		return pRoutineD;
	}
	public void setpRoutineD(String pRoutineD) {
		this.pRoutineD = pRoutineD;
	}
	public Long getpRoutineDId() {
		return pRoutineDId;
	}
	public void setpRoutineDId(Long pRoutineDId) {
		this.pRoutineDId = pRoutineDId;
	}
	public Long getqTesterId() {
		return qTesterId;
	}
	public void setqTesterId(Long qTesterId) {
		this.qTesterId = qTesterId;
	}
	public String getqTester() {
		return qTester;
	}
	public void setqTester(String qTester) {
		this.qTester = qTester;
	}
	public Long getqItemTypeId() {
		return qItemTypeId;
	}
	public void setqItemTypeId(Long qItemTypeId) {
		this.qItemTypeId = qItemTypeId;
	}
	public String getqItemType() {
		return qItemType;
	}
	public void setqItemType(String qItemType) {
		this.qItemType = qItemType;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getTestMethod() {
		return testMethod;
	}
	public void setTestMethod(String testMethod) {
		this.testMethod = testMethod;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public Long getFrequency() {
		return frequency;
	}
	public void setFrequency(Long frequency) {
		this.frequency = frequency;
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
}
