package com.jms.domain.db;

import java.io.Serializable;

import javax.persistence.Transient;

public abstract class AbstractSecuredEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Transient
    private Long id;
	@Transient
    private Users user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractSecuredEntity other = (AbstractSecuredEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
}
