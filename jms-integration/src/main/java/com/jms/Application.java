package com.jms;


import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;






import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jms.web.CustomizedRestMvcConfiguration;
import com.jms.web.security.SecurityUtils;



@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Import(CustomizedRestMvcConfiguration.class)
@EnableTransactionManagement(proxyTargetClass=true)
public class Application {

    public static void main(String[] args) throws Exception  {
    	//SpringApplication.run(Application.class, args);
    	
    	ConfigurableApplicationContext ctx= SpringApplication.run(Application.class, args);

    	//DatabaseInit initDB = ctx.getBean(DatabaseInit.class);
    	//initDB.init(ctx);

    	
    	//EmailSenderTest et = ctx.getBean(EmailSenderTest.class);
    	//et.testSendEmail();
    	
    }
    
    @PostConstruct
	public void init() {

		/**
		 * Due to method-level protections, the security context must be loaded
		 * with an authentication token containing the necessary privileges.
		 */
		SecurityUtils.runAs("admin", "admin", "ROLE_ADMIN");

		SecurityContextHolder.clearContext();
		
	}
}
