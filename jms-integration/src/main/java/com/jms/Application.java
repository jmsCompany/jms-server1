package com.jms;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jms.domain.db.Users;
import com.jms.service.user.GroupTypeService;
import com.jms.service.user.UserService;

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
		/*
		UserService userService = ctx.getBean(UserService.class);
		for(Users u: userService.findRevisions(4l))
		{
			System.out.println("uid:" + u.getIdUser() +" ,token: " + u.getToken());
		}
		GroupTypeService groupTypeService = ctx.getBean(GroupTypeService.class);
		groupTypeService.loadGroupTypes();
		*/
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
