
package com.jms.web.security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.Roles;
import com.jms.domain.db.Users;
import com.jms.repositories.user.UsersRepository;

//@Service
public class IMEAuthProvider implements AuthenticationProvider {
	
	@Autowired private UsersRepository usersRepository;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		System.out.println("username: " + token.getName()+", pass: " + token.getCredentials());
		Users user = usersRepository.findByUsernameOrEmailOrMobile(token.getName());
		return authenticatedToken(user,authentication);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		 return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}

	
	private Authentication authenticatedToken(Users user,
			Authentication original) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		Map<String,String> rolesMap = new HashMap<String,String>();
		if(user.getRoleses().isEmpty())
		{
		     authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		     rolesMap.put("ROLE_USER", "ROLE_USER");
		}
		else
		{
			for(Roles r: user.getRoleses())
			{
				String role = r.getRole();
				if(!rolesMap.containsKey(role))
				{
					rolesMap.put(role, role);
					authorities.add(new SimpleGrantedAuthority(role));
				}
				
			}
		}

		UsernamePasswordAuthenticationToken authenticated = new UsernamePasswordAuthenticationToken(
				user.getUsername(), user.getPassword(), authorities);
		return authenticated;
	}
}
