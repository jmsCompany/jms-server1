package com.jms.acl;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="UserACLRequest")
public class UserACLRequest {
	String entityClassName;
	String entityId;
	Long idUser;
	Integer permission;

	public Integer getPermission() {
		return permission;
	}
	public void setPermission(Integer permission) {
		this.permission = permission;
	}
	public String getEntityClassName() {
		return entityClassName;
	}
	public void setEntityClassName(String entityClassName) {
		this.entityClassName = entityClassName;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	@Override
	public String toString() {
		return "UserACLRequest [entityClassName=" + entityClassName
				+ ", entityId=" + entityId + ", userId=" + idUser
				+ ", permission=" + permission + "]";
	}
	public Long getIdUser() {
		return idUser;
	}
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
}
