package com.jms.web.security;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.Users;
import com.jms.domain.ws.WSUser;
import com.jms.repositories.user.UsersRepository;
import com.jms.web.AccessControlAllowFilter;



@Service("tokenUtils")
@Transactional(readOnly=true)
public class TokenUtilsImpl implements TokenUtils {
	private static Log LOGGER = LogFactory.getLog(TokenUtilsImpl.class);
	@Autowired private UsersRepository  usersRepository;
	
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
		Users user = usersRepository.findOne(userDetails.getIdUser());
	    if(user!=null)
	    {
	    	if(user.getLastLogin().getTime()<new Date().getTime()-expiration)
	    		return null;
	    	return user.getToken();
	    }
		
	    else
	    	return null;
	}

	@Override
	public boolean validate(String token) {
		Users user = usersRepository.findByToken(token);
		boolean flag =false;
	    if(user!=null)
	    {
	    	if(user.getLastLogin().getTime()<new Date().getTime()-TWO_WEEKS_S)
	    		flag = false;
	    	else
	    		flag = true;	
	    }
	    	return flag;
     
	}

	@Override @Transactional(readOnly=false)
	public JMSUserDetails getUserFromToken(String token) {
		Users user = usersRepository.findByToken(token);
	
		user.setLastLogin(new Date());
		usersRepository.save(user);

		WSUser wsUser = new WSUser();
		wsUser.setIdUser(user.getIdUser());
		if(user.getSysDicDByLocale()!=null)
		{
			wsUser.setLocale(user.getSysDicDByLocale().getName());
		}
		
		wsUser.setEmail(user.getEmail());
		//String username =token.substring(0, token.indexOf("__"));
		wsUser.setLogin(""+user.getIdUser());
		wsUser.setUsername(user.getUsername());
		wsUser.setMobile(user.getMobile());
		wsUser.setPassword(user.getPassword());
		JMSUserDetails userDetails = new JMSUserDetails(wsUser);
		userDetails.setAuthorities(securityUtils.getAuthorities(user.getIdUser()));
		return userDetails;
	}

}
