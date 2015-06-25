package com.jms.acl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.AbstractSecuredObject;

@Repository("securedObjDAO")
public class SecuredObjDAOImpl implements SecuredObjDAO {
	
	protected EntityManager em = null;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public <T extends AbstractSecuredObject> T find(Class<T> clazz, long id) {
		return em.find(clazz, id);
	}

}
