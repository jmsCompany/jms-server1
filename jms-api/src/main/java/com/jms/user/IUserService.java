package com.jms.user;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.Users;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.WSUser;


public interface IUserService {
	@Transactional(readOnly=false)
	public Message register(Users users);
	@Transactional(readOnly=true)
	public Message checkLogin(String login,Long idUser);
	@Transactional(readOnly=true)
	public Message checkLogin(String username,String email,String mobile,Long idUser);
	@Transactional(readOnly=true)
	public List<WSUser> getUsersByIdSector(Long idSector) throws Exception;
}

