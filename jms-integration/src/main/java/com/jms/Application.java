package com.jms;

import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.hateoas.hal.DefaultCurieProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableEntityLinks
@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
// @Import(CustomizedRestMvcConfiguration.class)
@EnableTransactionManagement(proxyTargetClass = true)
public class Application extends SpringBootServletInitializer {

	public static String CURIE_NAMESPACE = "jms";

	public @Bean CurieProvider curieProvider() {
		return new DefaultCurieProvider(CURIE_NAMESPACE, new UriTemplate(
				"http://localhost:8080/alps/{rel}"));
	}

	public static void main(String[] args) throws Exception {
		// SpringApplication.run(Application.class, args);

		ConfigurableApplicationContext ctx = SpringApplication.run(
				Application.class, args);

		// /DatabaseInit initDB = ctx.getBean(DatabaseInit.class);
		// /initDB.init(ctx);
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
