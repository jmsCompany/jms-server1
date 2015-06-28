package com.jms;

import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableTransactionManagement(proxyTargetClass = true)
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) throws Exception {
		// SpringApplication.run(Application.class, args);
		ConfigurableApplicationContext ctx = SpringApplication.run(
				Application.class, args);

		DatabaseInit initDB = ctx.getBean(DatabaseInit.class);
		initDB.init(ctx);
	}

	@PostConstruct
	public void init() {
		SecurityContextHolder.clearContext();
	
		
	}

	/**
	 * Allows the application to be started when being deployed into a Servlet 3
	 * container.
	 * 
	 * @see org.springframework.boot.web.SpringBootServletInitializer#configure(org.springframework.boot.builder.SpringApplicationBuilder)
	 */
	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
}
