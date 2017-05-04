package com.jms.repositories.q;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.QFileManagent;
import com.jms.domain.db.SMtfMaterial;
import com.jms.domain.db.SPoMaterial;


@Repository("qFileManagementRepositoryCustomImpl")
public class QFileManagementRepositoryCustomImpl implements QFileManagementRepositoryCustom {
	
	 @PersistenceContext
	 private EntityManager em;


	@Override
	public List<QFileManagent> getQFiles(Long companyId, String fromDate, String toDate, Long materialId, Long woId,
			Long routineDId, Long filetypeId, Long creatorId) {

		 String query ="select q from QFileManagent q where q.idCompany=" +companyId;
	
		 if(fromDate!=null)
		 {
			 query=query + " and DATE(q.creationTime)>='" + fromDate +"'";
		 }
		 if(toDate!=null)
		 {
			 query=query + " and DATE(q.creationTime)<='" + toDate +"'";
		 }
		 if(materialId!=null)
		 {
			 query=query + " and q.idMaterial = " + materialId;
		 }
		 if(woId!=null)
		 {
			 query=query + " and q.idWo=" + woId;
		 }
		 if(routineDId!=null)
		 {
			 query=query + " and q.idRoutineD=" + routineDId;
		 }
		 if(creatorId!=null)
		 {
			 query=query + " and q.creator=" + creatorId;
		 }
		 if(filetypeId!=null)
		 {
			 query=query + " and q.QFileType.idFileType=" + filetypeId;
		 }
		 System.out.println(query);
		  return em.createQuery(query, QFileManagent.class).getResultList();
	
	}

}
