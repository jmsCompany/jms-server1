package com.jms.repositories.q;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.QCar;
import com.jms.domain.db.QNcr2;





@Repository
public interface QNcr2Repository extends JpaRepository<QNcr2, Long>{
	
	
	@Query("select q from QNcr2 q where q.when1>=?1 and q.when1<=?2 and q.QNoProcess.idMaterial=?3 order by q.idNcr")
	public List<QNcr2> getByFromToAndMaterialId(Date fromDate, Date toDate,Long materialId);


}
