package com.jms.domain.ws;

public class WSSysDicD implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private Long idDic;
	private String type;
	private String name;
	private String description;
	private Long enabled;
	public WSSysDicD()
	{
		
	}
	public WSSysDicD(Long idDic,String type,String name,String description, Long enabled)
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

	public Long getIdDic() {
		return idDic;
	}

	public void setIdDic(Long idDic) {
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
	public Long getEnabled() {
		return enabled;
	}
	public void setEnabled(Long enabled) {
		this.enabled = enabled;
	}
	
}
