package com.jms.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import com.jms.domain.db.Roles;
import com.jms.domain.db.Users;
import com.jms.repositories.user.UsersRepository;



public class SecurityUtils {
	@Autowired
	private transient static UsersRepository usersRepository;
	private static final Logger logger = LogManager.getLogger(SecurityUtils.class.getCanonicalName());
	
	public static void runAs(String username, String password, String... roles) {

		Assert.notNull(username, "Username must not be null!");
		Assert.notNull(password, "Password must not be null!");

		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(username, password, AuthorityUtils.createAuthorityList(roles)));
		//Locale locale = new Locale("en", "US");
		//LocaleContextHolder.setLocale(locale);
		//System.out.println("locale: " + LocaleContextHolder.getLocale());
		
	}
	
	
	public static String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) auth.getPrincipal()).getUsername();
        } else {
            return auth.getPrincipal().toString();
        }
    }

	public static Collection<GrantedAuthority> getAuthorities(String userName) {
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
	
	public static ArrayList<String> getUserRoles() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Collection<? extends GrantedAuthority> authorities = auth
				.getAuthorities();
		ArrayList<String> currentUserRoles = new ArrayList<String>();
		for (GrantedAuthority authority : authorities) {
			currentUserRoles.add(authority.getAuthority());
		}
		if (logger.isDebugEnabled()) {
			logger.debug("currentUserRoles:" + currentUserRoles);
		}
		return currentUserRoles;
	}
    
    public static boolean doesUserHasRole(String role) {
    	ArrayList<String> currentUserRoles = getUserRoles();
    	for(String s : currentUserRoles) {
    		if(s.equals(role)) {
    			return true;
    		}
    	}
    	return false;
    } 
}