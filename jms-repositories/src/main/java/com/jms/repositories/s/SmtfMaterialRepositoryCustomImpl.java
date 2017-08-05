package com.jms.repositories.s;


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

	@Override
	public List<SMtfMaterial> getSmtfMaterialList(Long companyId, Long typeId, String q, String fromDay, String toDay) {
		  String query ="select s from SMtfMaterial s where s.SMtf.company.idCompany=" +companyId;
		   if(typeId!=null)
		   {
			   query = query +" and s.SMtf.SMtfTypeDic.idMtfType=" +typeId;
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
			   query = query +" and (s.SMaterial.pno like "+q +"  or s.SMaterial.rev like " +q +" or s.SMaterial.des like "+q +" or s.SPoMaterial.SPo.codePo like "+q + "or s.SPoMaterial.SPo.SCompanyCo.shortName like "+q+" or s.SPoMaterial.SPo.SCompanyCo.code like "+q+" or s.SPoMaterial.SPo.SCompanyCo.name like "+q+")";
		   }
		  // System.out.println(query);
		   return em.createQuery(query, SMtfMaterial.class).getResultList();
	}

	@Override
	public List<SMtfMaterial> getCustomCSMtf(Long companyId, Long companyId1, String q, String fromDay, String toDay) {
		
	   String query ="select s from SMtfMaterial s where (s.SMtf.company.idCompany=" +companyId +" or s.SMtf.idToCompany=" +companyId +")";
	   if(companyId1!=null)
	   {
		   query = query +" and (s.SMtf.company.idCompany=" +companyId1 +" or s.SMtf.idToCompany=" +companyId1+")";
	   }
	   if(q!=null)
	   {
		   q="'%"+q+"%'";
		   query = query +" and (s.SMaterial.pno like "+q +"  or s.SMaterial.rev like " +q +" or s.SMaterial.des like "+q +" or s.SMtf.mtNo like "+q +")";
	   }
	   if(fromDay!=null)
	   {
		 query = query +" and DATE(s.SMtf.creationTime)>='" +fromDay +"'";
	   }
	   if(toDay!=null)
	   {
			 query = query +" and DATE(s.SMtf.creationTime)<='" +toDay +"'";
	   }
	   query = query +" and s.SMtf.SMtfTypeDic.idMtfType=" +9 + " and s.SMtf.SStatusDic.id=5";
	   System.out.println(query);
	   return em.createQuery(query, SMtfMaterial.class).getResultList();
	}

	@Override
	public List<SMtfMaterial> getCustomCSMtfMaterials(Long companyId, Long statusId) {
		String query="";
		Long typeId = -1l;
		if(statusId.equals(4l))
		{
			typeId =9l;
		    query ="select s from SMtfMaterial s where s.SMtf.idToCompany=" +companyId + " and s.SMtf.SStatusDic.id="+statusId +" and s.SMtf.SMtfTypeDic.idMtfType="+typeId;
		}
		else if(statusId.equals(5l))
		{
			typeId =10l;
			 query ="select s from SMtfMaterial s where s.SMtf.company.idCompany=" +companyId + " and s.SMtf.SStatusDic.id="+statusId +" and s.SMtf.SMtfTypeDic.idMtfType="+typeId;
		}
		else
		{
			typeId =9l;
			query ="select s from SMtfMaterial s where s.SMtf.idToCompany=" +companyId + " and s.SMtf.SStatusDic.id="+statusId +" and s.SMtf.SMtfTypeDic.idMtfType="+typeId;
		}
	//    System.out.println(query);
		return em.createQuery(query, SMtfMaterial.class).getResultList();
	}

}
