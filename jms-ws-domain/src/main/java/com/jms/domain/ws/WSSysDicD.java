package com.jms.domain.ws;

public class WSSysDicD implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private Integer idDic;
	private String type;
	private String name;
	private String description;
	private int enabled;
	public WSSysDicD()
	{
		
	}
	public WSSysDicD(Integer idDic,String type,String name,String description, int enabled)
	{
		this.idDic=idDic;
		this.type=type;
		this.name=name;
		this.description=description;
		this.enabled=enabled;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getIdDic() {
		return idDic;
	}

	public void setIdDic(Integer idDic) {
		this.idDic = idDic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	
}
