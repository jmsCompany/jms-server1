package com.jms.domain.ws.m;

import java.util.ArrayList;
import java.util.List;

public class WSMainCycle {
	
    private Long idMainCycle;
    private String mainCycle;
    private Long numbers;
    
    private List<WSMainItem> items = new ArrayList<WSMainItem>();
	
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
	public List<WSMainItem> getItems() {
		return items;
	}
	public void setItems(List<WSMainItem> items) {
		this.items = items;
	}
	public Long getNumbers() {
		return numbers;
	}
	public void setNumbers(Long numbers) {
		this.numbers = numbers;
	}
    

}
