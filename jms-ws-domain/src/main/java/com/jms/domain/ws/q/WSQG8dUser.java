package com.jms.domain.ws.q;

import java.util.Date;

public class WSQG8dUser  implements java.io.Serializable{

	private Long idG8dUsers;
    private Long idG8d;
    private Long idUser;
    private String name;
    
	public Long getIdG8dUsers() {
		return idG8dUsers;
	}
	public void setIdG8dUsers(Long idG8dUsers) {
		this.idG8dUsers = idG8dUsers;
	}
	public Long getIdG8d() {
		return idG8d;
	}
	public void setIdG8d(Long idG8d) {
		this.idG8d = idG8d;
	}

	public Long getIdUser() {
		return idUser;
	}
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
  
}
