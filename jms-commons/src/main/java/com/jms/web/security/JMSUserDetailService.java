package com.jms.web.security;


import java.io.Serializable;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jms.domain.db.Users;
import com.jms.domain.ws.WSUser;
import com.jms.repositories.user.UsersRepository;

@Service("userDetailService")
public class JMSUserDetailService implements Serializable,
		UserDetailsService {
	private final UsersRepository usersRepository;

	@Autowired
	public JMSUserDetailService(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Users user = usersRepository.findByUsernameOrEmailOrMobile(username);
		if (user == null) {
			throw new UsernameNotFoundException("Could not find user " + username);
		}
		return new CustomUserDetails(user);
	}

	private final static class CustomUserDetails extends Users implements UserDetails {

		private CustomUserDetails(Users user) {
			super(user);
		}

		public Collection<? extends GrantedAuthority> getAuthorities() {
			return AuthorityUtils.createAuthorityList("ROLE_USER");
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
	}
	

	
}