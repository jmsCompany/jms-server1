package com.jms.web.security;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.Order;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.transaction.PlatformTransactionManager;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ImportResource(value={"classpath*:META-INF/app-datasource.xml","classpath*:META-INF/app-acl.xml","classpath*:META-INF/app-secu.xml"})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired private UserDetailsService userDetailsService;
	@Autowired private AuthenticationTokenProcessingFilter authenticationTokenProcessingFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http
		.authorizeRequests().antMatchers("/company/create").permitAll()
		.antMatchers("/error").permitAll()
		.antMatchers("/check/**").permitAll()
		.antMatchers("/dic/**").permitAll()
		.antMatchers("/download/**").permitAll()
		.antMatchers("/login").permitAll();
	    http.addFilter(authenticationTokenProcessingFilter);
	    http.authorizeRequests().anyRequest().authenticated();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth, UserDetailsService userDetailsService) throws Exception {
	//	auth
	//		.userDetailsService(userDetailsService)
	//			.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Bean
	public SecurityEvaluationContextExtension securityEvaluationContextExtension(){
	    return new SecurityEvaluationContextExtension();
	}
	

}