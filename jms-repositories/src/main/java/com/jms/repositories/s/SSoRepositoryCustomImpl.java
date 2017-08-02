package com.jms.repositories.s;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SSo;


@Repository("sSoRepositoryCustom")
public class SSoRepositoryCustomImpl implements SSoRepositoryCustom {
	
	@PersistenceContext
    private EntityManager em;


	@Override
	public List<SSo> getCustomSsos(Long companyId, Long status, String q, String fromDay, String toDay) {
		// String query ="select s from SSo s where s.company.idCompany=" +companyId +" and s.SCompanyCo is not null";
		 String query ="select s from SSo s where s.company.idCompany=" +companyId;
	
		 if(fromDay!=null)
		 {
			 query=query + " and DATE(s.dateOrder)>='" + fromDay +"'";
		 }
		 if(toDay!=null)
		 {
			 query=query + " and DATE(s.dateOrder)<='" + toDay +"'";
		 }
		 
		 if(status!=null&&!status.equals(1000l))
		 {
			 query=query + " and s.SStatusDic.id = " + status;
		 }
		 if(status!=null&&status.equals(1000l))
		 {
			 query=query + " and s.SStatusDic.id <> 16"; //16 作废
		 }
		 if(q!=null)
		 {
			 q ="'%" + q + "%'";
			// query =query + " and (s.SCompanyCo.shortName like " +q + " or s.codeSo like " +q +" or s.SMaterial.pno like " + q + " or s.SMaterial.rev like " +q +" or s.SMaterial.des like " +q +")"; 
			// query =query + " and (s.codeSo like " +q +" or s.SMaterial.pno like " + q + " or s.SMaterial.rev like " +q +" or s.SMaterial.des like " +q +")";  
			 // query =query + " and (s.SCompanyCo.shortName like " +q+")"; 
			// query =query + " or s.SCompanyCo is null";
			 query =query + " and (s.SCompanyCo.shortName like " +q + " or s.SMaterial.pno like " + q + " or s.SMaterial.rev like " +q +" or s.SMaterial.des like " +q +" or s.codeSo like " +q +")";  
		 }

		//  System.out.println("query: " + query);
		  return em.createQuery(query, SSo.class).getResultList();
	}

}
