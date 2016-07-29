package com.jms.domain.ws;


public class WSUserPassword implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long idUser;
    private String password;
    private String newPassword;

    public Long getIdUser() {
		return idUser;
	}
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
  


}
