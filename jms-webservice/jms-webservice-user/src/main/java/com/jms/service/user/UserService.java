package com.jms.service.user;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.MessageTypeEnum;
import com.jms.email.LocalUtil;
import com.jms.repositories.user.UsersRepository;


@Service
@Transactional
public class UserService {
	
	private static final Logger logger = LogManager.getLogger(UserService.class.getCanonicalName());
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private ResourceBundleMessageSource source;
	private static final String salt ="jms,rocks!";
	
	public Message register(Users users)
	{
		Message msg = new Message();
		users.setPassword(encode(users.getPassword()));
		String username = users.getUsername();
		String email = users.getEmail(); //todo: check whether email is valid...
		String mobile = users.getMobile(); // todo check whether the number is correct
		if(username==null&&email==null&&mobile==null)
		{
			String message = source.getMessage("user.register.error.atleastone", null, LocalUtil.getLocal());
			msg.setMessage(message);
			msg.setMessageTypeEnum(MessageTypeEnum.ERROR);
			return msg;
		}	
		else
		{
			msg =checkLogin(username);
			if(msg!=null)
				return msg;
			msg =checkLogin(email);
			if(msg!=null)
				return msg;
			msg =checkLogin(mobile);
			if(msg!=null)
				return msg;
		}
		String password =  encode(users.getPassword());
		users.setPassword(password);
        usersRepository.save(users);
        String message = source.getMessage("user.register.success", null, LocalUtil.getLocal());
        msg = new Message();
        msg.setMessage(message);
	    msg.setMessageTypeEnum(MessageTypeEnum.INFOMATION);
		logger.debug(msg.getMessageTypeEnum().toString()  +", "+ msg.getMessage());
		return msg;
	}


	public void loadUsersFromCSV(String fileName) throws IOException
	{
		CsvReader reader = new CsvReader(fileName,',', Charset.forName("UTF-8"));
		
        reader.readHeaders();  //username	password	name	email	mobile	address	id_card	enabled
		while(reader.readRecord())
		{

			Users u = new Users();
			u.setUsername(reader.get("username"));
			u.setPassword(reader.get("password"));
			u.setName(reader.get("name"));
			u.setEmail(reader.get("email"));
			u.setMobile(reader.get("mobile"));
			u.setAddress(reader.get("address"));
			u.setIdcard(reader.get("id_card"));
			u.setEnabled(Integer.parseInt(reader.get("enabled")));
			u.setCreationTime(new Date());
            u.setLocale(Locale.CHINA.toString());
			Message msg = register(u);
			logger.debug(msg.getMessageTypeEnum().toString()  +", "+ msg.getMessage());
		}
	}
	

	private String encode(String password)
	{
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		return md5.encodePassword(password, salt);
	}
	
	private Message checkLogin(String login)
	{
		if(login!=null)
		{
			Message msg = new Message();
			Users u =usersRepository.findByUsernameOrEmailOrMobile(login);
			if(u!=null)
			{
				String message = source.getMessage("user.register.error", new Object[] {login}, LocalUtil.getLocal());
				msg.setMessage(message);
				msg.setMessageTypeEnum(MessageTypeEnum.ERROR);
				return msg;
			}
			else
				return null;
		}
		else
			return null;
		

	}
}
