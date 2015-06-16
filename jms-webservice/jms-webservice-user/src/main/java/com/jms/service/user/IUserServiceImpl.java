package com.jms.service.user;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.MessageTypeEnum;
import com.jms.messages.MessagesUitl;
import com.jms.repositories.user.UsersRepository;
import com.jms.user.IUserService;

@Service
@Qualifier("iUserServiceImpl")
@Transactional
public class IUserServiceImpl implements IUserService {

	private static final Logger logger = LogManager
			.getLogger(IUserServiceImpl.class.getCanonicalName());
	@Autowired
	protected UsersRepository usersRepository;
	@Autowired
	private MessagesUitl messagesUitl;
	@Autowired
	private ResourceBundleMessageSource source;



	public Message register(Users users) {
		Message msg = checkLogin(users.getUsername(), users.getEmail(),
				users.getMobile());
        String anyLogin ="";
        if(users.getUsername()!=null&&!users.getUsername().isEmpty())
        	anyLogin =users.getUsername();
        if(anyLogin.isEmpty())
        {
        	 if(users.getEmail()!=null&&!users.getEmail().isEmpty())
             	anyLogin =users.getEmail();
        }
        if(anyLogin.isEmpty())
        {
        	 if(users.getMobile()!=null&&!users.getMobile().isEmpty())
             	anyLogin =users.getMobile();
        }
		if (msg.getMessageTypeEnum().equals(MessageTypeEnum.ERROR))
			return msg;
		else {
			users.setPassword(new BCryptPasswordEncoder().encode(users
					.getPassword()));

			String token = anyLogin+"__"+new BCryptPasswordEncoder().encode(new Date().toString());
			users.setToken(token);
			users.setLastLogin(new Date());
			usersRepository.save(users);
			Message msgToClient = messagesUitl.getMessage(
					"user.register.success", null, MessageTypeEnum.INFOMATION);
			logger.debug(msgToClient.getMessageTypeEnum().toString() + ", "
					+ msgToClient.getMessage());
			return msgToClient;
		}

	}

	public void updatePassword(int idUser, String newpassword) {
		Users users = usersRepository.findOne(idUser);
		users.setPassword(new BCryptPasswordEncoder().encode(newpassword));
		usersRepository.save(users);
	}

	public Message checkLogin(String username, String email, String mobile) {
		logger.debug(" username: " + username + ", email: " + email + ", "
				+ mobile);
		Message msg1 = checkLogin(username);
		if (msg1 != null
				&& msg1.getMessageTypeEnum().equals(MessageTypeEnum.ERROR)) {
			logger.debug("message1: " + msg1.getMessage() + ", type: "
					+ msg1.getMessageTypeEnum());
			return msg1;
		}
		Message msg2 = checkLogin(email);
		if (msg2 != null
				&& msg2.getMessageTypeEnum().equals(MessageTypeEnum.ERROR)) {
			logger.debug("message2: " + msg2.getMessage() + ", type: "
					+ msg2.getMessageTypeEnum());
			return msg2;
		}

		Message msg3 = checkLogin(mobile);
		if (msg3 != null
				&& msg3.getMessageTypeEnum().equals(MessageTypeEnum.ERROR)) {
			logger.debug("message3: " + msg3.getMessage() + ", type: "
					+ msg3.getMessageTypeEnum());
			return msg3;
		}
		if (msg1 == null && msg2 == null && msg3 == null)
			return messagesUitl.getMessage("user.register.error.atleastone",
					null, MessageTypeEnum.ERROR);
		return messagesUitl.getMessage("user.register.ok", null,
				MessageTypeEnum.INFOMATION);

	}

	public Message checkLogin(String login) {
		if (login != null && !login.isEmpty()) {
			Users u = usersRepository.findByUsernameOrEmailOrMobile(login);
			if (u != null)
				return messagesUitl.getMessage("user.register.error",
						new Object[] { login }, MessageTypeEnum.ERROR);
			else
				return messagesUitl.getMessage("user.name.available", null,
						MessageTypeEnum.INFOMATION);
		} else
			return null;
	}
}
