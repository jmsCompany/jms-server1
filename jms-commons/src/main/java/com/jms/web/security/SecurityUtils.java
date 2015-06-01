package com.jms.web.security;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;



public class SecurityUtils {

	/**
	 * Configures the Spring Security {@link SecurityContext} to be authenticated as the user with the given username and
	 * password as well as the given granted authorities.
	 * 
	 * @param username must not be {@literal null} or empty.
	 * @param password must not be {@literal null} or empty.
	 * @param roles
	 */
	public static void runAs(String username, String password, String... roles) {

		Assert.notNull(username, "Username must not be null!");
		Assert.notNull(password, "Password must not be null!");

		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(username, password, AuthorityUtils.createAuthorityList(roles)));
		//Locale locale = new Locale("en", "US");
		//LocaleContextHolder.setLocale(locale);
		//System.out.println("locale: " + LocaleContextHolder.getLocale());
		
	}
}