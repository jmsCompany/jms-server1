package com.jms.web.security;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jms.domain.db.Roles;
import com.jms.domain.db.Users;
import com.jms.repositories.user.UsersRepository;


public class JMSUserDetailService implements Serializable,
		UserDetailsService {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory
			.getLogger(JMSUserDetailService.class);

	@Autowired
	private transient UsersRepository usersRepository;

	/**
	 * Returns a populated {@link UserDetails} object. 
	 * The username is first retrieved from the database and then mapped to 
	 * a {@link UserDetails} object.
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Users user = usersRepository.findByUsernameOrEmailOrMobile(username);
			
			boolean enabled = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
			
			return new User(
					user.getUsername(), 
					user.getPassword(),
					enabled,
					accountNonExpired,
					credentialsNonExpired,
					accountNonLocked,
					getAuthorities(user.getUsername()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private Collection<GrantedAuthority> getAuthorities(String userName) {
		logger.debug("in getAuthoritiesFromDb");
		List<GrantedAuthority> l = new ArrayList<GrantedAuthority>();

		Users user = usersRepository.findByUsernameOrEmailOrMobile(userName);
		if(user == null) {
			return l;
		}
		for(final Roles role : user.getRoleses()) {
			l.add( new GrantedAuthority() {
				private static final long serialVersionUID = 1L;
				
				@Override
				public String getAuthority() {
					return role.getRole();
				}
				
				@Override
				public String toString() {
					return getAuthority();
				}
			});
		}
		
		return l;		
	}	
}