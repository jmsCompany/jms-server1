package com.jms.repositories.q;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.QG8d;





@Repository
public interface QG8dRepository extends JpaRepository<QG8d, Long>{

	@Query("select q from QG8d q where q.dateOpened>=?1 and q.dateOpened<=?2 and q.idMaterial=?3")
	public List<QG8d> getByFromToAndMaterialId(Date fromDate,Date toDate,Long materialId);
	
	
	@Query("select q from QG8d q where q.dateOpened>=?1 and q.dateOpened<=?2 and q.idMaterial=?3 and q.problemState like ?4")
	public List<QG8d> getByFromToAndMaterialIdAndReason(Date fromDate,Date toDate,Long materialId,String problemState);
	
}
