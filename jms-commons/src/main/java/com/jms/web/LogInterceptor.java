package com.jms.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

public class LogInterceptor implements WebRequestInterceptor {  
    
	private static final Logger logger = LogManager.getLogger(LogInterceptor.class.getCanonicalName());
   
    @Override  
    public void preHandle(WebRequest request) throws Exception {  
       
    	//logger.debug("principal: " + request.getUserPrincipal());
    	
    }  
  

    @Override  
    public void postHandle(WebRequest request, ModelMap map) throws Exception {  

    }  
  
 
    @Override  
    public void afterCompletion(WebRequest request, Exception exception)  
    throws Exception {  
   
    }


      
}  