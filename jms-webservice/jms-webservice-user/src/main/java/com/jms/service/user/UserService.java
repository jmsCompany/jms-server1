package com.jms.service.user;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.audit.AudiReaderService;
import com.jms.domain.Config;
import com.jms.domain.db.Groups;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.MessageTypeEnum;
import com.jms.domain.ws.WSUser;
import com.jms.repositories.system.SysDicDRepository;

@Service
@Qualifier("userService")
public class UserService extends IUserServiceImpl{
	@Autowired
	private SysDicDRepository sysDicDRepository;
	@Autowired
	private AudiReaderService audiReaderService;
	private static final Logger logger = LogManager.getLogger(UserService.class.getCanonicalName());
	
	@Transactional(readOnly=false)
	public WSUser save(WSUser wsUser) throws Exception
	{
			Boolean userValid = checkLogin(wsUser.getUsername(), wsUser.getEmail(),
					wsUser.getMobile(),wsUser.getIdUser());
	        String anyLogin ="";
	        if(wsUser.getUsername()!=null&&!wsUser.getUsername().isEmpty())
	        	anyLogin =wsUser.getUsername();
	        if(anyLogin.isEmpty())
	        {
	        	 if(wsUser.getEmail()!=null&&!wsUser.getEmail().isEmpty())
	             	anyLogin =wsUser.getEmail();
	        }
	        if(anyLogin.isEmpty())
	        {
	        	 if(wsUser.getMobile()!=null&&!wsUser.getMobile().isEmpty())
	             	anyLogin =wsUser.getMobile();
	        }
			if (!userValid)
				throw new java.lang.Exception("用户已经被注册了");
			Users dbUser = usersRepository.findOne(wsUser.getIdUser());
			dbUser = userAdapter.toDBUser(wsUser, dbUser);
			usersRepository.save(dbUser);
			wsUser.setIdUser(dbUser.getIdUser());
			return wsUser;
	}
	
	/*
	@Transactional(readOnly=false)
	public void loadUsersFromCSV(InputStream inputStream) throws IOException
	{
		
		CsvReader reader = new CsvReader(inputStream,Charset.forName("UTF-8"));
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
			u.setEnabled(Long.parseLong(reader.get("enabled")));
			u.setCreationTime(new Date());
			u.setSysDicDByLocale(sysDicDRepository.findDicsByType(Config.lang).get(0));
			Message msg = register(u);
			logger.debug(msg.getMessageTypeEnum().toString()  +", "+ msg.getMessage());
		}
	}
	*/
	@Transactional(readOnly=false)
	public void createDefaultUsers()
	{
		Users system = new Users();
		system.setUsername("system");
		system.setPassword("system");
		
		register(system);
		Users admin = new Users();
		admin.setUsername("admin");
		admin.setPassword("admin");
		register(admin);
		
		
		Users user = new Users();
		user.setUsername("user");
		user.setPassword("user");
		register(user);

	}
	@Transactional(readOnly=false)
	public String login(String login, String password)
	{
		String defaultMsg=null;
	    Users user =  usersRepository.findByUsernameOrEmailOrMobile(login);
	    if(user!=null&&user.getEnabled().longValue()==1l)
	    {
	    	if(new BCryptPasswordEncoder().matches(password, user.getPassword()))
	    	{
	    		user.setLastLogin(new Date());
	    		String token = user.getIdUser()+"__"+new BCryptPasswordEncoder().encode(new Date().toString());
				user.setToken(token);
				usersRepository.save(user);
	    		defaultMsg = user.getToken();
	    	}
	    		
	    }
		return defaultMsg;
		
	}

	@Transactional(readOnly=true)
     public List<Users> findRevisions(Long idUser)
     {
    	return audiReaderService.getRevisions(Users.class, idUser);

     }
	
}
