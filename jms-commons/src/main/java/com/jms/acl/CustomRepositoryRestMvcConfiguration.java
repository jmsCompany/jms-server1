package com.jms.acl;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;

/**
 * To change any default config, we need to subclass
 * RepositoryRestMvcConfiguration
 * 
 */
@Configuration
@ImportResource(value={"classpath*:META-INF/app-acl.xml","classpath*:META-INF/app-secu.xml"})
public class CustomRepositoryRestMvcConfiguration extends
		RepositoryRestMvcConfiguration {

	@Override
	protected void configureRepositoryRestConfiguration(
			RepositoryRestConfiguration config) {
		config.setDefaultPageSize(1000);
	}
}
