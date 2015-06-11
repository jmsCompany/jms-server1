package com.jms.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.boot.autoconfigure.security.SecurityProperties;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired private UserDetailsService userDetailsService;
	//@Autowired private IMEAuthProvider imeAuthProvider;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http
		.authorizeRequests()
			.antMatchers("/company/create").permitAll();
			
		http.csrf().disable();
	       http
           .authorizeRequests()
               .anyRequest().authenticated()
               .and().httpBasic();
		
		
	}
/*	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(imeAuthProvider);
		
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(new BCryptPasswordEncoder());
			
	}

	*/
	/*
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.inMemoryAuthentication()
			.withUser("user").password("password").roles("USER").and()
			.withUser("admin").password("password").roles("USER","ADMIN");
	}
	*/
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth, UserDetailsService userDetailsService) throws Exception {
		auth
			.userDetailsService(userDetailsService)
				.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Bean
	public SecurityEvaluationContextExtension securityEvaluationContextExtension(){
	    return new SecurityEvaluationContextExtension();
	}

}