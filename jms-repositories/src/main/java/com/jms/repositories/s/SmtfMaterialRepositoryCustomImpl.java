package com.jms.repositories.s;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SMtfMaterial;


@Repository("sMtfMaterialRepositoryCustom")
public class SmtfMaterialRepositoryCustomImpl implements SMtfMaterialRepositoryCustom {
	
	 @PersistenceContext
	 private EntityManager em;

	@Override
	public List<SMtfMaterial> getCustomSMtf(Long companyId,Long typeId, String q, Long fromStkId, Long toStkId, String fromDay, String toDay) {

	   String query ="select s from SMtfMaterial s where s.SMtf.company.idCompany=" +companyId;
	   if(typeId!=null)
	   {
		   query = query +" and s.SMtf.SMtfTypeDic.idMtfType=" +typeId;
	   }
	   if(fromStkId!=null)
	   {
		   query = query +" and s.SMtf.SStkByFromStk.id=" +fromStkId;
	   }
	   if(toStkId!=null)
	   {
		   query = query +" and s.SMtf.SStkByToStk.id=" +toStkId;
	   }
	   if(fromDay!=null)
	   {
		 query = query +" and DATE(s.SMtf.creationTime)>='" +fromDay +"'";
	   }
	   if(toDay!=null)
	   {
			 query = query +" and DATE(s.SMtf.creationTime)<='" +toDay +"'";
	   }
	   if(q!=null)
	   {
		   q="'%"+q+"%'";
		   query = query +" and (s.SMaterial.pno like "+q +"  or s.SMaterial.rev like " +q +" or s.SMaterial.des like "+q +" or s.SMtf.mtNo like "+q +")";
	   }
	   System.out.println(query);
	   return em.createQuery(query, SMtfMaterial.class).getResultList();
	}

}
