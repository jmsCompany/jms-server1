package com.jms.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.SectorMember;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.MessageTypeEnum;
import com.jms.domain.ws.WSRoles;
import com.jms.domain.ws.WSUser;
import com.jms.domainadapter.UserAdapter;
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

	@Autowired protected UserAdapter userAdapter;

	public Message register(Users users) {
		Message msg = checkLogin(users.getUsername(), users.getEmail(),
				users.getMobile(),null);
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

		//	String token = anyLogin+"__"+new BCryptPasswordEncoder().encode(new Date().toString());
		//	users.setToken(token);
		//	users.setLastLogin(new Date());
			users.setCreationTime(new Date());
			users.setEnabled(1);
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

	public Message checkLogin(String username, String email, String mobile,Integer idUser) {
		logger.debug(" username: " + username + ", email: " + email + ", "
				+ mobile);
		Message msg1 = checkLogin(username,idUser);
		if (msg1 != null
				&& msg1.getMessageTypeEnum().equals(MessageTypeEnum.ERROR)) {
			logger.debug("message1: " + msg1.getMessage() + ", type: "
					+ msg1.getMessageTypeEnum());
			return msg1;
		}
		Message msg2 = checkLogin(email,idUser);
		if (msg2 != null
				&& msg2.getMessageTypeEnum().equals(MessageTypeEnum.ERROR)) {
			logger.debug("message2: " + msg2.getMessage() + ", type: "
					+ msg2.getMessageTypeEnum());
			return msg2;
		}

		Message msg3 = checkLogin(mobile,idUser);
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

	public Message checkLogin(String login, Integer idUser) {
		if (login != null && !login.isEmpty()) {
			Users u = usersRepository.findByUsernameOrEmailOrMobile(login);
			if(idUser==null)
			{
				if (u != null)
					return messagesUitl.getMessage("user.register.error",
							new Object[] { login }, MessageTypeEnum.ERROR);
				else
					return messagesUitl.getMessage("user.name.available", null,
							MessageTypeEnum.INFOMATION);
			}
			else
			{
				Users u1 = usersRepository.findOne(idUser);
				if(u != null)
				{
					if(u.getIdUser().equals(u1.getIdUser()))
						return messagesUitl.getMessage("user.name.available", null,
								MessageTypeEnum.INFOMATION);
					else
						return messagesUitl.getMessage("user.register.error",
								new Object[] { login }, MessageTypeEnum.ERROR);
				}
			
				else
					return messagesUitl.getMessage("user.name.available", null,
							MessageTypeEnum.INFOMATION);
			}
		
		} else
			return null;
	}
	
	@Transactional(readOnly=true)
	public List<WSUser> getUsersByIdSector(int idSector) throws Exception
	{
		List<WSUser> wsUsers = new ArrayList<WSUser>(0);
		for(Users u:usersRepository.findUsersByIdSector(idSector))
		{
			WSUser wsuser =userAdapter.toWSUser(u);
			for(SectorMember sm:u.getSectorMembers())
			{
				WSRoles wsRoles = new WSRoles();
				wsRoles.setIdRole(sm.getRoles().getIdRole());
				wsRoles.setRole(sm.getRoles().getRole());
				wsRoles.setEnabled(sm.getRoles().getEnabled());
                wsRoles.setIdSector(sm.getId().getIdSector());
                wsRoles.setSector(sm.getSector().getSector());
                wsRoles.setLevel(sm.getRoles().getLevel());
                wsRoles.setIsprimary(sm.getIsprimary());
                wsuser.getRoleList().add(wsRoles);
			}
			wsUsers.add(wsuser);
		}
		return wsUsers;
	}
	
}
