package com.jms.repositories.q;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.QCar;
import com.jms.domain.db.QNcr2;





@Repository
public interface QCarRepository extends JpaRepository<QCar, Long>{

	@Query("select q from QCar q where q.commitDate1>=?1 and q.commitDate1<=toDate and q.QNcr2.QNoProcess.idMaterial=?3 order by q.idCar")
	public List<QCar> getByFromToAndMaterialId(Date fromDate, Date toDate,Long materialId);

}
