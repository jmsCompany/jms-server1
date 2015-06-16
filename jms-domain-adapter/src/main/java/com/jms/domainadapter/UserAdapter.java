package com.jms.domainadapter;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.Users;
import com.jms.domain.ws.WSUser;
import com.jms.repositories.system.SysDicDRepository;

@Service @Transactional(readOnly=true)
public class UserAdapter {
	
	@Autowired
	private SysDicDRepository sysDicDRepository;
	
	@Autowired
	private SysDicDAdapter sysDicDAdapter;
	
	public Users toDBUser(WSUser wsUser) throws Exception
	{
		Users u = (Users)BeanUtil.shallowCopy(wsUser,Users.class);
		if(wsUser.getLocale()==null)
			u.setSysDicDByLocale(sysDicDRepository.findDicsByType("").get(0));
		return u;
			
	}


	public WSUser toWSUser(Users users) throws Exception
	{
		WSUser wsUser = (WSUser)BeanUtil.shallowCopy(users,WSUser.class);
		wsUser.setPassword("*******");
		wsUser.setToken("*******");
		return wsUser;
	}

}
