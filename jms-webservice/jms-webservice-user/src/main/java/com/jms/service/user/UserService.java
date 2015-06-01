package com.jms.service.user;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.jms.messages.MessagesUitl;
import com.jms.repositories.user.UsersRepository;

@Service
@Transactional
public class UserService {
	
	private static final Logger logger = LogManager.getLogger(UserService.class.getCanonicalName());
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private MessagesUitl messagesUitl;
	@Autowired
	private ResourceBundleMessageSource source;
	
	private static final String salt ="jms,rocks!";
	
	
//公司名,邮箱,电话号码是否为空的判断
	//公司名是否填写
	public static boolean ifCompanyNameEmpty( Users users ){
		boolean bEmptyFlag = false;
		if( users.getUsername() != null ){
			bEmptyFlag = true;
		}
		return bEmptyFlag;
	}
	//邮件是否填写
	public static boolean ifEmailEmpty( Users users ){
		boolean bEmptyFlag = false;
		if( users.getEmail() != null ){
			bEmptyFlag = true;
		}
		return bEmptyFlag;
	}
	//电话号码是否填写
	public static boolean ifMobileEmpty( Users users ){
		boolean bEmptyFlag = false;
		if( users.getMobile() != null ){
			bEmptyFlag = true;
		}
		return bEmptyFlag;
	}
	
//公司名,邮箱,电话号码是否符合规定
	//公司名是否符合规则
	public static boolean ifCompanyNameAbeyRules( Users users ){
		boolean bAbeyRulesFlag = false;
		String strTmpCompanyName = users.getUsername();
		if(strTmpCompanyName.contains("")){
			bAbeyRulesFlag = true;
		}
		return bAbeyRulesFlag;
	}
	//邮箱是否符合规则
	public static boolean ifEmailAbeyRules(Users users) {
		boolean bAbeyRulesFlag = false;
		String strTmpEmail = users.getEmail();
		String strRule = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(strRule);
		Matcher m = p.matcher(strTmpEmail);
		if(m.matches()){
			bAbeyRulesFlag = true;
		}
		return bAbeyRulesFlag;
	}
	//手机号码是否符合规则
	public static boolean ifMobileAbeyRules(Users users) {
		boolean bAbeyRulesFlag = false;
		String strTmpMobile = users.getMobile();
		String strRule = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		Pattern p = Pattern.compile(strRule);
		Matcher m = p.matcher(strTmpMobile);
		if( m.matches()){
			bAbeyRulesFlag = true;
		}
		return bAbeyRulesFlag;
	}

	
	public Message register(Users users)
	{
		Message msg;
		//公司名，邮件，电话都没填写
		if( ifCompanyNameEmpty(users)==false && ifEmailEmpty(users)==false && ifMobileEmpty(users)== false)
		{
			return messagesUitl.getMessage("user.register.error.atleastone",null,MessageTypeEnum.ERROR);
		}
		//公司名，邮件，电话至少有一个填写了
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
	
	public void loadUsersFromCSV(String fileName) throws IOException
	{
		CsvReader reader = new CsvReader(fileName,',', Charset.forName("UTF-8"));
        reader.readHeaders();  //username	password	name	email	mobile	address	id_card	enabled
		while(reader.readRecord())
		{
			Users u = new Users();
			u.setUsername(reader.get("username"));
			u.setPassword(encode(reader.get("password")));
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
