package com.jms;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CommonsRequestLoggingFilter;




import com.jms.domain.db.Users;
import com.jms.service.system.AppsService;
import com.jms.service.user.GroupTypeService;
import com.jms.service.user.UserService;
import com.jms.service.workmanagement.ProjectService;
import com.jms.web.AccessControlAllowFilter;
import com.jms.web.JsonpCallbackFilter;
import com.jms.web.security.AuthenticationTokenProcessingFilter;

@EnableScheduling
@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableTransactionManagement(proxyTargetClass = true)
public class Application extends SpringBootServletInitializer {

	// JsonpCallbackFilter jsonpFilter = new JsonpCallbackFilter();
	AccessControlAllowFilter acaFilter = new AccessControlAllowFilter();
	//@Autowired AuthenticationTokenProcessingFilter authTokenFilter;
	public static void main(String[] args) throws Exception {
		// SpringApplication.run(Application.class, args);
		ConfigurableApplicationContext ctx = SpringApplication.run(
				Application.class, args);

		DatabaseInit initDB = ctx.getBean(DatabaseInit.class);
		initDB.init(ctx);

		/*
		 * UserService userService = ctx.getBean(UserService.class); for(Users
		 * u: userService.findRevisions(4l)) { System.out.println("uid:" +
		 * u.getIdUser() +" ,token: " + u.getToken()); } GroupTypeService
		 * groupTypeService = ctx.getBean(GroupTypeService.class);
		 * groupTypeService.loadGroupTypes();
		 */

		// ProjectService projectService = ctx.getBean(ProjectService.class);
		// projectService.SecuredObjectServiceTest();
	}

	@PostConstruct
	public void init() {
		SecurityContextHolder.clearContext();

	}

	// @Bean
	// public FilterRegistrationBean jsonpFilter() {
	// System.out.println("Setting up jsonpFilter with " +
	// jsonpFilter.toString());

	// FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
	// filterRegBean.setFilter(jsonpFilter);
	// return filterRegBean;
	// }
	@Bean
	public FilterRegistrationBean acaFilter() {
		FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
		filterRegBean.setFilter(acaFilter);
		return filterRegBean;
	}

	
	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
	    CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
	    loggingFilter.setIncludeClientInfo(true);
	    loggingFilter.setIncludeQueryString(true);
	    loggingFilter.setIncludePayload(true);
	    return loggingFilter;
	}
	
	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
}
