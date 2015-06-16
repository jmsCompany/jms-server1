package com.jms.web.security;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.Users;
import com.jms.domain.ws.WSUser;
import com.jms.repositories.user.UsersRepository;

@Service("userDetailService")
public class JMSUserDetailService implements Serializable,
		UserDetailsService {
	private static final long serialVersionUID = 1L;
	@Autowired private UsersRepository usersRepository;
	@Autowired private SecurityUtils securityUtils;
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		Users user = usersRepository.findByUsernameOrEmailOrMobile(username);
		if (user == null) {
			
			throw new UsernameNotFoundException("Could not find user " + username);
		}
		WSUser wsUser = new WSUser();
		wsUser.setIdUser(user.getIdUser());
		wsUser.setLocale(user.getSysDicDByLocale().getName());
		wsUser.setEmail(user.getEmail());
		wsUser.setLogin(username);
		wsUser.setUsername(user.getUsername());
		wsUser.setMobile(user.getMobile());
		wsUser.setPassword(user.getPassword());
		wsUser.setToken(user.getToken());
		JMSUserDetails userDetails = new JMSUserDetails(wsUser);
		userDetails.setAuthorities(securityUtils.getAuthorities(username));
		return userDetails;
	}
}