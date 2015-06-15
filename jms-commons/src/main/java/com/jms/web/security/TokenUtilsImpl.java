package com.jms.web.security;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.PersistentLogin;
import com.jms.domain.db.Users;
import com.jms.domain.ws.WSUser;
import com.jms.repositories.user.PersistentLoginRepository;


@Service("tokenUtils")
@Transactional(readOnly=true)
public class TokenUtilsImpl implements TokenUtils {

	@Autowired private PersistentLoginRepository  persistentLoginRepository;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired private SecurityUtils securityUtils;
	
	public static final Long TWO_WEEKS_S = 1209600000l;
	
	@Override 
	public String getToken(JMSUserDetails userDetails) {
	    	return getToken(userDetails,TWO_WEEKS_S);
	}

	@Override
	public String getToken(JMSUserDetails userDetails, Long expiration) {
		PersistentLogin persistentLogin = persistentLoginRepository.findByUserId(userDetails.getIdUser());
	    if(persistentLogin!=null)
	    {
	    	if(persistentLogin.getLastUesed().getTime()<new Date().getTime()-expiration)
	    		return null;
	    	return persistentLogin.getToken();
	    }
		
	    else
	    	return null;
	}

	@Override
	public boolean validate(String token) {
		PersistentLogin persistentLogin =persistentLoginRepository.findByToken(token);
	    if(persistentLogin!=null)
	    {

	    	if(persistentLogin.getLastUesed().getTime()<new Date().getTime()-TWO_WEEKS_S)
	    	{
	    		
	    		return false;
	    	}
	    		
	    	else
	    	{
	    		return true;
	    	}
	    		
	    }
		  
	    else
	      return false;
	}

	@Override @Transactional(readOnly=false)
	public JMSUserDetails getUserFromToken(String token) {
		PersistentLogin persistentLogin =persistentLoginRepository.findByToken(token);
		persistentLogin.setLastUesed(new Date());
		persistentLoginRepository.save(persistentLogin);
		Users user =  persistentLogin.getUsers();
		WSUser wsUser = new WSUser();
		wsUser.setIdUser(user.getIdUser());
		wsUser.setLocale(user.getSysDicDByLocale().getName());
		wsUser.setEmail(user.getEmail());
		String username =token.substring(0, token.indexOf("__"));
		wsUser.setLogin(username);
		wsUser.setUsername(user.getUsername());
		wsUser.setMobile(user.getMobile());
		wsUser.setPassword(user.getPassword());
		JMSUserDetails userDetails = new JMSUserDetails(wsUser);
		userDetails.setAuthorities(securityUtils.getAuthorities(username));
		return userDetails;
	}

}
