package com.jms.repositories.s;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SMtfMaterial;
import com.jms.domain.db.SPoMaterial;
import com.jms.domain.db.SSo;


@Repository("sSoRepositoryCustom")
public class SSoRepositoryCustomImpl implements SSoRepositoryCustom {
	
	 @PersistenceContext
	 private EntityManager em;


	@Override
	public List<SSo> getCustomSsos(Long companyId, String q, String fromDay, String toDay) {
		 String query ="select s from SSo s where s.company.idCompany=" +companyId;
		 if(q!=null)
		 {
			 q ="'%" + q + "%'";
			 query =query + " and (s.SCompanyCo.shortName like " +q + " or s.codeSo like " +q +" or s.SMaterial.pno like " + q + " or s.SMaterial.rev like " +q +" or s.SMaterial.des like " +q +")";  
		 }
		 if(fromDay!=null)
		 {
			 query=query + " and DATE(s.dateOrder)>='" + fromDay +"'";
		 }
		 if(toDay!=null)
		 {
			 query=query + " and DATE(s.dateOrder)<='" + toDay +"'";
		 }
//		 System.out.println(query);
		  return em.createQuery(query, SSo.class).getResultList();
	}

}
