package com.jms.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.Groups;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.MessageTypeEnum;
import com.jms.domain.ws.WSRoles;
import com.jms.domain.ws.WSUser;
import com.jms.domainadapter.UserAdapter;
import com.jms.messages.MessagesUitl;
import com.jms.repositories.user.GroupRepository;
import com.jms.repositories.user.GroupTypeRepository;
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

	public Boolean register(Users users) {
		Boolean valid = checkLogin(users.getUsername(), users.getEmail(),
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
		if (!valid)
			return false;
		else {
			users.setPassword(new BCryptPasswordEncoder().encode(users
					.getPassword()));
			users.setCreationTime(new Date());
			users.setEnabled(1l);
			usersRepository.save(users);
			return true;
		}

	}

	public void updatePassword(Long idUser, String newpassword) {
		Users users = usersRepository.findOne(idUser);
		users.setPassword(new BCryptPasswordEncoder().encode(newpassword));
		usersRepository.save(users);
	}

	public Boolean checkLogin(String username, String email, String mobile,Long idUser) {

		Boolean valid = checkLogin(username,idUser);
		if (!valid)
			return false;
	    Boolean valid1 = checkLogin(email,idUser);
		if (!valid1)
			return false;
       Boolean valid2 = checkLogin(mobile,idUser);
		if (!valid2)
			return false;
		if (valid&&valid1&&valid2)
			return true;
		return false;

	}
	public Boolean checkLogin(String login, Long idUser) {
		if (login != null && !login.isEmpty()) {
			Users u = usersRepository.findByUsernameOrEmailOrMobile(login);
			if(idUser==null)
			{
				if (u != null)
					return false;
				else
					return true;
			}
			else
			{
				Users u1 = usersRepository.findOne(idUser);
				if(u != null)
				{
					if(u.getIdUser().equals(u1.getIdUser()))
						return true;
					else
						return false;
				}
			
				else
					return true;
			}
		
		} else
			return true;
	}
	
	public Boolean checkToken(String jmstoken) {

		Users u = usersRepository.findByToken(jmstoken);
		if(u!=null)
			return true;
		else
			return false;
		
	}
	
	@Transactional(readOnly=true)
	public List<WSUser> getUsersByIdGroup(Long idGroup) throws Exception
	{
		//Pageable p = new PageRequest(0,1);
		// Page<Users> requestedPage = usersRepository.findAll(p);
		// for(Users u:requestedPage.getContent())
		// {
		//	 logger.debug("uid:   "+u.getIdUser());
		// }
		//return requestedPage.getContent();
	

		List<WSUser> wsUsers = new ArrayList<WSUser>(0);
		for(Users u:usersRepository.findUsersByIdGroup(idGroup))
		{
			WSUser wsuser =userAdapter.toWSUser(u);
			
			wsUsers.add(wsuser);
		}
		return wsUsers;
	}
	
}
