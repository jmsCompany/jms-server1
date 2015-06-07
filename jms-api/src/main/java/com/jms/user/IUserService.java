package com.jms.user;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.Users;
import com.jms.domain.ws.Message;


public interface IUserService {
	@Transactional(readOnly=false)
	public Message register(Users users);
	@Transactional(readOnly=true)
	public Message checkLogin(String login);
	@Transactional(readOnly=true)
	public Message checkLogin(String username,String email,String mobile);
}
