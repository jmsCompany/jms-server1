package com.jms.domain.ws;


public class WSSysDicD implements java.io.Serializable{
	
	private Integer idDic;
	private String type;
	private String name;
	private int  enabled;

    private String description;
    
	public Integer getIdDic() {
		return idDic;
	}
	public void setIdDic(Integer idDic) {
		this.idDic = idDic;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}
