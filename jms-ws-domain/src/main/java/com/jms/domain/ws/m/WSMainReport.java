package com.jms.domain.ws.m;

import java.util.ArrayList;
import java.util.List;

public class WSMainReport {
	
	private Long year;
	private Long month;
    private Long idMachine;
    private String machine;
   
    private List<WSMainCycle> cycles = new ArrayList<WSMainCycle>();
    
	public Long getYear() {
		return year;
	}
	public void setYear(Long year) {
		this.year = year;
	}
	public Long getMonth() {
		return month;
	}
	public void setMonth(Long month) {
		this.month = month;
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
	public List<WSMainCycle> getCycles() {
		return cycles;
	}
	public void setCycles(List<WSMainCycle> cycles) {
		this.cycles = cycles;
	}
   
   
	
  
}
