package com.jms.user;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.Users;
import com.jms.domain.ws.WSUser;


public interface IUserService {
	@Transactional(readOnly=false)
	public Boolean register(Users users);
	@Transactional(readOnly=true)
	public Boolean checkLogin(String login,Long idUser);
	@Transactional(readOnly=true)
	public Boolean checkLogin(String username,String email,String mobile,Long idUser);
	@Transactional(readOnly=true)
	public List<WSUser> getUsersByIdGroup(Long idGroup) throws Exception;
}

