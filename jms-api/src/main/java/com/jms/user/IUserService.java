package com.jms.user;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Message;

public interface IUserService {
	public Message register(Users users);
	public Message checkLogin(String login);
	public Message checkLogin(String username,String email,String mobile);
}
