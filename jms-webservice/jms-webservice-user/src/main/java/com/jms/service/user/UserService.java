package com.jms.service.user;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.domain.Config;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Message;
import com.jms.repositories.system.SysDicDRepository;

@Service
@Qualifier("userService")
public class UserService extends IUserServiceImpl{
	@Autowired
	private SysDicDRepository sysDicDRepository;
	private static final Logger logger = LogManager.getLogger(UserService.class.getCanonicalName());
	@Transactional(readOnly=false)
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
			u.setSysDicDByLocale(sysDicDRepository.findDicsByType(Config.lang).get(0));
			Message msg = register(u);
			logger.debug(msg.getMessageTypeEnum().toString()  +", "+ msg.getMessage());
		}
	}
	@Transactional(readOnly=true)
	public String login(String login, String password)
	{
		String defaultMsg = "BADPASSWORD";
	    Users user =	usersRepository.findByUsernameOrEmailOrMobile(login);
	    if(user!=null)
	    {
	    	if(new BCryptPasswordEncoder().matches(password, user.getPassword()))
	    		defaultMsg = user.getToken();
	    }
		return defaultMsg;
		
	}

}
