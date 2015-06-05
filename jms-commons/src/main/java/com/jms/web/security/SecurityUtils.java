package com.jms.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;



public class SecurityUtils {

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