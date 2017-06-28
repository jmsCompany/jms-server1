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


@Repository("sSpoMaterialRepositoryCustom")
public class SSpoMaterialRepositoryCustomImpl implements SSpoMaterialRepositoryCustom {
	
	 @PersistenceContext
	 private EntityManager em;


	@Override
	public List<SPoMaterial> getCustomSpoMaterials(Long companyId,Long type, String q, String fromDay, String toDay) {
		 String query ="select s from SPoMaterial s where s.SPo.company.idCompany=" +companyId;
		 
		 if(type!=null)
		 {
		
			 if(type.equals(1000l))
			 {
				 query =query + " and s.SPo.SStatusDic.id <> 9 ";  
			 }
			 else
			 {
				 query =query + " and s.SPo.SStatusDic.id = " + type +" ";  
			 }
			 
		 }
		 
		 if(q!=null)
		 {
			 q ="'%" + q + "%'";
			 query =query + " and (s.SPo.SCompanyCo.shortName like " +q + " or s.SPo.codePo like " +q +" or s.SMaterial.pno like " + q + " or s.SMaterial.rev like " +q +" or s.SMaterial.des like " +q +")";  
		 }
		 if(fromDay!=null)
		 {
			 query=query + " and DATE(s.SPo.dateOrder)>='" + fromDay +"'";
		 }
		 if(toDay!=null)
		 {
			 query=query + " and DATE(s.SPo.dateOrder)<='" + toDay +"'";
		 }
		 query=query + " order by s.SPo.codePo desc";
		 System.out.println(query);
		  return em.createQuery(query, SPoMaterial.class).getResultList();
		
	}


	@Override
	public List<SPoMaterial> getComComSpoMaterials(Long companyId, Long type, String q, String fromDay, String toDay) {
        
		 String query ="select s from SPoMaterial s where s.SPo.company.idCompany=" +companyId +" and s.SPo.idCompany1 is not null";
		 
		 if(type!=null)
		 {
		
			 if(type.equals(1000l))
			 {
				 query =query + " and s.SPo.SStatusDic.id <> 9 ";  
			 }
			 else
			 {
				 query =query + " and s.SPo.SStatusDic.id = " + type +" ";  
			 }
			 
		 }
		 if(q!=null)
		 {
			 q ="'%" + q + "%'";
			 query =query + " and (s.SPo.SCompanyCo.shortName like " +q + " or s.SPo.codePo like " +q +" or s.SMaterial.pno like " + q + " or s.SMaterial.rev like " +q +" or s.SMaterial.des like " +q +")";  
		 }
		 if(fromDay!=null)
		 {
			 query=query + " and DATE(s.SPo.dateOrder)>='" + fromDay +"'";
		 }
		 if(toDay!=null)
		 {
			 query=query + " and DATE(s.SPo.dateOrder)<='" + toDay +"'";
		 }
		 query=query + " order by s.SPo.codePo desc";
//		 System.out.println(query);
		  return em.createQuery(query, SPoMaterial.class).getResultList();
	}

}
