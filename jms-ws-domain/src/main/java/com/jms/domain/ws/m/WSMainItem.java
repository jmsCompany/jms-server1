package com.jms.domain.ws.m;


public class WSMainItem {
	
	private Long idMainItem;
    private Long idMachine;
    private String machine;
    private Long idMainCycle;
    private String mainCycle;
    private String item;
	
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
   
}
