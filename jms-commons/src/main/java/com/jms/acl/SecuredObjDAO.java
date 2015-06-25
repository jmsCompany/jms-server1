package com.jms.acl;

import com.jms.domain.db.AbstractSecuredObject;



public interface SecuredObjDAO {
	
	public <T extends AbstractSecuredObject> T find(Class<T> clazz, long id);

}
