package com.jms.repositories.s;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SNomaterialReportSum;


@Repository("sNomaterialReportSumRepositoryCustom")
public class SNomaterialReportSumRepositoryCustomImpl implements SNomaterialReportSumRepositoryCustom {
	
	@PersistenceContext
	private EntityManager em;


	@Override
	public List<SNomaterialReportSum> findByIdCompany(Long idCompany, String fromDay, String toDay, Long materialId) {
		
		 String query ="select s from SNomaterialReportSum s where s.idCompany=" +idCompany;
		  if(fromDay!=null&&!fromDay.isEmpty())
		   {
			 query = query +" and DATE(s.RDate)>='" +fromDay +"'";
		   }
		   if(toDay!=null&&!toDay.isEmpty())
		   {
				 query = query +" and DATE(s.RDate)<='" +toDay +"'";
		   }
		   if(materialId!=null)
		   {
			   query = query +" and s.idMat = " +materialId;
		   }
		   return em.createQuery(query, SNomaterialReportSum.class).getResultList();
	}

	

}
