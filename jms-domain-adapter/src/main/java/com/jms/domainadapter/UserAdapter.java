package com.jms.domainadapter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.Users;
import com.jms.domain.ws.WSUser;

@Service @Transactional(readOnly=true)
public class UserAdapter {
	
	public Users toDBUser(WSUser wsUser) throws Exception
	{
		return (Users)BeanUtil.shallowCopy(wsUser,Users.class);
			
	}


	public WSUser toWSUser(Users users) throws Exception
	{
		WSUser wsUser = (WSUser)BeanUtil.shallowCopy(users,WSUser.class);
		wsUser.setPassword("********");
		return wsUser;
	}

}
