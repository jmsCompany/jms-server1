package com.jms.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired private UserDetailsService userDetailsService;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.httpBasic().and().authorizeRequests().
				antMatchers("/api").hasAuthority("SYSADMIN").
				antMatchers("/api/**").hasAuthority("SYSADMIN");
		http
		.authorizeRequests()
			.antMatchers("/company/create").permitAll();
	
		http.csrf().disable();
	     http
           .authorizeRequests()
               .anyRequest().authenticated()
               .and().httpBasic();
	    
	}

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