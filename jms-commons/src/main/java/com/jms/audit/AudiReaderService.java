package com.jms.audit;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AudiReaderService {
	@PersistenceContext
	private EntityManager em;

		@Transactional(readOnly = true)
	  public <T> T getObjectAtVersion(Class<T> clazz, Long idObject, Number idRevision) {
	    AuditReader reader = AuditReaderFactory.get(em);
	    return reader.find(clazz, idObject, idRevision);
	   
	  }
		
		@Transactional(readOnly = true)
		  public <T> List<T> getRevisions(Class<T> clazz, Long id) {
			List<T> objectList = new ArrayList<T>();
		    AuditReader reader = AuditReaderFactory.get(em);
		    for(Number idRev:reader.getRevisions(clazz, id))
		    {
		    	objectList.add(reader.find(clazz, id, idRev));
		    }
		    return objectList;
		  }
}
