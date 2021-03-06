package com.jms.web.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.jms.domain.ws.WSUser;



@Component
public class JMSUserDetails extends WSUser implements UserDetails {
	private Collection<GrantedAuthority> authorities;
	
	public JMSUserDetails(){}

	public JMSUserDetails(WSUser user) {
		super(user);
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
	
		return this.authorities;
	}

	
	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities=authorities;
	}
	public String getUsername() {
		return ""+getIdUser();
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		if(getEnabled().longValue()==0l)
		   return false;
		else
			return true;
	}

	private static final long serialVersionUID = 5639683223516504866L;

}