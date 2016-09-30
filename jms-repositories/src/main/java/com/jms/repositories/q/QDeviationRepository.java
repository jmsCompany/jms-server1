package com.jms.repositories.q;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.QCar;
import com.jms.domain.db.QDeviation;
import com.jms.domain.db.QNcr2;





@Repository
public interface QDeviationRepository extends JpaRepository<QDeviation, Long>{
	
	@Query("select q from QDeviation q where q.date1>=?1 and q.date1<=?2 and q.idMaterial=?3")
	public List<QDeviation> getByFromToAndMaterialId(Date fromDate,Date toDate,Long materialId);
	
	
	@Query("select q from QDeviation q where q.date1>=?1 and q.date1<=?2 and q.idMaterial=?3 and q.reason like ?4")
	public List<QDeviation> getByFromToAndMaterialIdAndReason(Date fromDate,Date toDate,Long materialId,String reason);


}
