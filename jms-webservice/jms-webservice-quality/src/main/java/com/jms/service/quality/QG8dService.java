package com.jms.service.quality;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.QG8d;
import com.jms.domain.db.QG8dUsers;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.q.WSQG8d;
import com.jms.domain.ws.q.WSQG8dUser;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.q.QCarRepository;
import com.jms.repositories.q.QDeviationRepository;
import com.jms.repositories.q.QG8dRepository;
import com.jms.repositories.q.QG8dUsersRepository;
import com.jms.repositories.q.QNcr2Repository;
import com.jms.repositories.q.QNoProcessRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.web.security.SecurityUtils;


@Service
@Transactional
public class QG8dService {
	private static final Logger logger = LogManager.getLogger(QG8dService.class
			.getCanonicalName());
	@Autowired
	private QNcr2Repository qNcr2Repository;
	@Autowired
	private QCarRepository qCarRepository;
	
	@Autowired
	private QG8dRepository qG8dRepository;
	
	@Autowired
	private QG8dUsersRepository qG8dUsersRepository;
	
	@Autowired
	private  SecurityUtils securityUtils;
	@Autowired
	private  QNoProcessRepository qNoProcessRepository;
	
	
	@Autowired
	private QDeviationRepository qDeviationRepository;

	@Autowired
	private SMaterialRepository sMaterialRepository;
	@Autowired
	private UsersRepository usersRepository;

	public WSQG8d saveWSQG8d( WSQG8d wsQG8d) throws Exception {
		QG8d qG8d;
		if(wsQG8d.getIdG8d()!=null&&!wsQG8d.getIdG8d().equals(0l))
		{
			qG8d = qG8dRepository.findOne(wsQG8d.getIdG8d());
			for(QG8dUsers qg: qG8d.getQG8dUserses())
			{
				qG8dUsersRepository.delete(qg);
			}
			qG8d.getQG8dUserses().clear();
		}
		else
		{
			qG8d = new QG8d();
		
		}
		
		QG8d dbQG8d = toDBQG8d(wsQG8d,qG8d);
		
		dbQG8d = qG8dRepository.save(dbQG8d);
		   if(wsQG8d.getUsers()!=null)
		   {
			   for(WSQG8dUser u:wsQG8d.getUsers())
				{
					QG8dUsers qu = new QG8dUsers();
					qu.setIdUser(u.getIdUser());
					qu.setQG8d(dbQG8d);
					qG8dUsersRepository.save(qu);
				}
				
		   }
		
		
		
		return wsQG8d;
	}
	
	
	public Valid deleteQG8d(Long idQG8d) {
		Valid v = new Valid();
		v.setValid(true);
		qG8dRepository.delete(idQG8d);
		return v;
		
	}
	
	@Transactional(readOnly=true)
	public WSQG8d findWSQG8d(Long idQG8d) throws Exception {
		return toWSQG8d(qG8dRepository.findOne(idQG8d));
	}
	

	
	private QG8d toDBQG8d(WSQG8d wsQG8d,QG8d qG8d) throws Exception
	{
	
		QG8d dbQG8d = (QG8d)BeanUtil.shallowCopy(wsQG8d, QG8d.class, qG8d);
		if(wsQG8d.getIdNcr()!=null)
		{
			dbQG8d.setQNcr2(qNcr2Repository.findOne(wsQG8d.getIdNcr()));
		}
		return dbQG8d;
	}
	
	private WSQG8d toWSQG8d(QG8d qG8d) throws Exception
	{
		WSQG8d pc = (WSQG8d)BeanUtil.shallowCopy(qG8d, WSQG8d.class, null);
		
		if(qG8d.getQNcr2()!=null)
		{
			pc.setIdNcr(qG8d.getQNcr2().getIdNcr());
		}
		List<WSQG8dUser> users = new ArrayList<WSQG8dUser>();
	
		for(QG8dUsers qu: qG8d.getQG8dUserses())
		{
			WSQG8dUser u = new WSQG8dUser();
			u.setIdG8d(qG8d.getIdG8d());
			u.setIdG8dUsers(qu.getIdG8dUsers());
			u.setIdUser(qu.getIdUser());
			Users usr = usersRepository.findOne(qu.getIdUser());
			String name = (usr.getName()==null)?""+usr.getIdUser():usr.getName();
			u.setName(name);
			users.add(u);
		}
		pc.setUsers(users);
		
		
		if(qG8d.getVerification1()!=null)
		{
			Users u = usersRepository.findOne(qG8d.getVerification1());
			String name = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setVerification1Name(name);
		}
		
		if(qG8d.getIdLeader()!=null)
		{
			Users u = usersRepository.findOne(qG8d.getIdLeader());
			String name = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setIdLeaderName(name);
		}
		if(qG8d.getVerification2()!=null)
		{
			Users u = usersRepository.findOne(qG8d.getVerification2());
			String name = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setVerification2Name(name);
		}
		
		if(qG8d.getVerification3()!=null)
		{
			Users u = usersRepository.findOne(qG8d.getVerification3());
			String name = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setVerification3Name(name);
		}
		
		if(qG8d.getReportedBy()!=null)
		{
			Users u = usersRepository.findOne(qG8d.getReportedBy());
			String name = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setReportedByName(name);
		}
		
		if(qG8d.getResponsibility()!=null)
		{
			Users u = usersRepository.findOne(qG8d.getResponsibility());
			String name = (u.getName()==null)?""+u.getIdUser():u.getName();
			pc.setResponsibilityName(name);
		}
		if(qG8d.getIdMaterial()!=null)
		{
			SMaterial material = sMaterialRepository.findOne(qG8d.getIdMaterial());
			pc.setMaterial(material.getPno()+"_"+material.getRev()+"_"+material.getDes());
		}
		return pc;
	}
	
}
