package com.jms.web.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.jms.domain.ws.WSUser;



public class JMSUserDetails extends WSUser implements UserDetails {

	public JMSUserDetails(WSUser user) {
		super(user);
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		//AuthorityUtils.createAuthorityList(SecurityUtils.getUserRoles())
		return SecurityUtils.getAuthorities(getUsername());
	}

	public String getUsername() {
		return getUsername();
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
		return true;
	}

	private static final long serialVersionUID = 5639683223516504866L;

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

}