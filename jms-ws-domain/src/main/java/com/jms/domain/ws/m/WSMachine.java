package com.jms.domain.ws.m;


public class WSMachine {
	
    private Long idMachine;
    private String code;
    private String name;
    private String spec;
    private Long totalKwa;
	public Long getIdMachine() {
		return idMachine;
	}
	public void setIdMachine(Long idMachine) {
		this.idMachine = idMachine;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public Long getTotalKwa() {
		return totalKwa;
	}
	public void setTotalKwa(Long totalKwa) {
		this.totalKwa = totalKwa;
	}

}
