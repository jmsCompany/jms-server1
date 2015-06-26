package com.jms.acl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.AbstractSecuredEntity;

@Repository("securedObjDAO")
public class SecuredObjDAO {
	
	protected EntityManager em = null;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	public <T extends AbstractSecuredEntity> T find(Class<T> clazz, Long id) {
		return em.find(clazz, id);
	}

}
