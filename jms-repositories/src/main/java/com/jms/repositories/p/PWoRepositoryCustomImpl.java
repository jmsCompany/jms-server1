package com.jms.repositories.p;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.PWo;



@Repository("pWoRepositoryCustomImpl")
public class PWoRepositoryCustomImpl implements PWoRepositoryCustom {
	
	 @PersistenceContext
	 private EntityManager em;


	@Override
	public List<PWo> getPwos(Long companyId, String q, Long materialId) {
		 String query ="select p from PWo p where p.SSo.company.idCompany=" +companyId;
		 if(q!=null)
		 {
			  q="'%"+q+"%'";
			  query =  query + " and p.woNo like" +q;
		 }
			
		 if(materialId!=null)
		 {
			 query =  query + " and p.SSo.SMaterial.idMaterial=" +materialId;
		 }
		 query =  query +  " order by p.woNo asc";
		 return em.createQuery(query, PWo.class).getResultList();
	}

}
