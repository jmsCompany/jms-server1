package com.jms.service.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.MessageTypeEnum;
import com.jms.messages.MessagesUitl;
import com.jms.repositories.user.UsersRepository;
import com.jms.user.IUserService;

@Service("iUserServiceImpl")
@Transactional
public class IUserServiceImpl implements IUserService{
	
	private static final Logger logger = LogManager.getLogger(IUserServiceImpl.class.getCanonicalName());
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private MessagesUitl messagesUitl;
	@Autowired
	private ResourceBundleMessageSource source;
	private static final String salt ="jms,rocks!";
	
	public Message register(Users users)
	{
		Message msg;
	
		if(users.getEmail()==null&&users.getUsername()==null&&users.getMobile()==null)
		{
			return messagesUitl.getMessage("user.register.error.atleastone",null,MessageTypeEnum.ERROR);
		}
		else
		{
			msg =checkLogin(users.getUsername());
			if(msg!=null)
				return msg;
			msg =checkLogin(users.getEmail());
			if(msg!=null)
				return msg;
			msg =checkLogin(users.getMobile());
			if(msg!=null)
				return msg;
		}
		String password =  encode(users.getPassword());
		users.setPassword(password);
        usersRepository.save(users);
        Message msgToClient = messagesUitl.getMessage("user.register.success",null,MessageTypeEnum.INFOMATION);
		logger.debug(msgToClient.getMessageTypeEnum().toString()  +", "+ msgToClient.getMessage());
		return msgToClient;
	}

	private String encode(String password)
	{
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		return md5.encodePassword(password, salt);
	}
	
	public Message checkLogin(String login)
	{
		if(login!=null)
		{
			Users u =usersRepository.findByUsernameOrEmailOrMobile(login);
			if(u!=null)
			{
				return messagesUitl.getMessage("user.register.error",new Object[] {login}, MessageTypeEnum.ERROR);
			}
			else
				return null;
		}
		else
			return null;
	}
}
