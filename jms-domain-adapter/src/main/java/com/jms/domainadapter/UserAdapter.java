package com.jms.domainadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.Users;
import com.jms.domain.ws.WSSysDicD;
import com.jms.domain.ws.WSUser;
import com.jms.repositories.system.SysDicDRepository;

@Service @Transactional(readOnly=true)
public class UserAdapter {
	
	@Autowired
	private SysDicDRepository sysDicDRepository;
	
	@Autowired
	private SysDicDAdapter sysDicDAdapter;
	
	public Users toDBUser(WSUser wsUser,Users users) throws Exception
	{
		if(wsUser==null)
			return null;
		Users u = (Users)BeanUtil.shallowCopy(wsUser,Users.class,users);
		if(wsUser.getLocaleD()!=null)
		{
			u.setSysDicDByLocale(sysDicDRepository.findOne(wsUser.getLocaleD().getIdDic()));
		}
		if(wsUser.getStatus()!=null)
		{
			u.setSysDicDByStatus(sysDicDRepository.findOne(wsUser.getStatus().getIdDic()));
		}
		if(wsUser.getScheme()!=null)
		{
			u.setSysDicDByScheme(sysDicDRepository.findOne(wsUser.getScheme().getIdDic()));
		}
		if(wsUser.getGender()!=null)
		{
			u.setSysDicDByGender(sysDicDRepository.findOne(wsUser.getGender().getIdDic()));
		}
		return u;
			
	}


	public WSUser toWSUser(Users users) throws Exception
	{
		WSUser wsUser = (WSUser)BeanUtil.shallowCopy(users,WSUser.class,null);
		wsUser.setPassword("*******");
		wsUser.setToken("*******");
		if (users.getSysDicDByLocale() != null) {
			WSSysDicD locale = sysDicDAdapter.toWSSysDicD(users
					.getSysDicDByLocale());
			wsUser.setLocaleD(locale);
			wsUser.setLocale(locale.getName());
		}
		if (users.getSysDicDByScheme() != null) {
			WSSysDicD scheme = sysDicDAdapter.toWSSysDicD(users
					.getSysDicDByScheme());
			wsUser.setScheme(scheme);
		}
		if (users.getSysDicDByGender() != null) {
			WSSysDicD gender = sysDicDAdapter.toWSSysDicD(users
					.getSysDicDByGender());
			wsUser.setGender(gender);
		}
		if (users.getSysDicDByStatus() != null) {
			WSSysDicD status = sysDicDAdapter.toWSSysDicD(users
					.getSysDicDByStatus());
			wsUser.setStatus(status);
		}
		return wsUser;
	}

}
