package com.jms.user;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSUser;
import com.jms.domain.ws.WSUserRoles;


public interface IUserService {
	@Transactional(readOnly=false)
	public Boolean register(Users users);
	@Transactional(readOnly=true)
	public Boolean checkLogin(String login,Long idUser);
	@Transactional(readOnly=true)
	public Boolean checkLogin(String username,String email,String mobile,Long idUser);
	@Transactional(readOnly=true)
	public List<WSUser> getUsersByIdGroup(Long idGroup) throws Exception;
//	@Transactional(readOnly=false)
//	public Valid addRolesforUser(WSUserRoles wsUserRoles) throws Exception;
}

