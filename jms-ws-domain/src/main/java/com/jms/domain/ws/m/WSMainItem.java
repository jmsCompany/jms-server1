package com.jms.domain.ws.m;

import java.util.ArrayList;
import java.util.List;

public class WSMainItem {
	
	private Long idMainItem;
    private Long idMachine;
    private String machine;
    private Long idMainCycle;
    private String mainCycle;
    
    private Long idMDept;
    private String mDept;
    private String item;
    
    
    private Long days;
    private List<WSMainItemResult> itemValues = new ArrayList<WSMainItemResult>();
	
    public Long getIdMainItem() {
		return idMainItem;
	}
	public void setIdMainItem(Long idMainItem) {
		this.idMainItem = idMainItem;
	}
	public Long getIdMachine() {
		return idMachine;
	}
	public void setIdMachine(Long idMachine) {
		this.idMachine = idMachine;
	}
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
	public Long getIdMainCycle() {
		return idMainCycle;
	}
	public void setIdMainCycle(Long idMainCycle) {
		this.idMainCycle = idMainCycle;
	}
	public String getMainCycle() {
		return mainCycle;
	}
	public void setMainCycle(String mainCycle) {
		this.mainCycle = mainCycle;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public Long getIdMDept() {
		return idMDept;
	}
	public void setIdMDept(Long idMDept) {
		this.idMDept = idMDept;
	}
	public String getmDept() {
		return mDept;
	}
	public void setmDept(String mDept) {
		this.mDept = mDept;
	}
	public List<WSMainItemResult> getItemValues() {
		return itemValues;
	}
	public void setItemValues(List<WSMainItemResult> itemValues) {
		this.itemValues = itemValues;
	}
	public Long getDays() {
		return days;
	}
	public void setDays(Long days) {
		this.days = days;
	}
   
}
