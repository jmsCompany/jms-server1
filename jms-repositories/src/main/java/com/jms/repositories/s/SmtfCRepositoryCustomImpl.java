package com.jms.repositories.s;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.SMtfC;
import com.jms.domain.db.SMtfMaterial;


@Repository("sMtfCRepositoryCustom")
public class SmtfCRepositoryCustomImpl implements SMtfCRepositoryCustom {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<SMtfC> getCustomSMtfC(Long companyId, String q, String companyName,String fromDay, String toDay) {

	   String query ="select s from SMtfC s where (s.idCompany1=" +companyId + " or s.idCompany2="+companyId +")";
	   
	   if(fromDay!=null)
	   {
		 query = query +" and DATE(s.auditDate)>='" +fromDay +"'";
	   }
	   if(toDay!=null)
	   {
			 query = query +" and DATE(s.auditDate)<='" +toDay +"'";
	   }
	   if(q!=null)
	   {
		   q="'%"+q+"%'";
		   query = query +" and (s.pno like "+q +"  or s.materialName like " +q +")";
	   }
	   if(companyName!=null)
	   {
		   companyName="'%"+companyName+"%'";
		   query = query +" and (s.company1 like "+companyName +"  or s.company1 like " +companyName +")";
	   }
	   
	   return em.createQuery(query, SMtfC.class).getResultList();
	}

	

}
