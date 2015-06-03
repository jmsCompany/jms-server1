package com.jms.service.user;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Locale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.csvreader.CsvReader;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Message;

@Service("userService")
@Transactional
public class UserService extends IUserServiceImpl{
	
	private static final Logger logger = LogManager.getLogger(UserService.class.getCanonicalName());
	
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
	
	

}
