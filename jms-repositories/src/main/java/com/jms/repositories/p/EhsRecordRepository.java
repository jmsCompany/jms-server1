package com.jms.repositories.p;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.EhsItem;
import com.jms.domain.db.EhsRecord;




@Repository
public interface EhsRecordRepository extends JpaRepository<EhsRecord, Long>{

	public List<EhsRecord> findByIdCompany(Long idCompany);
	
//  DATE(p.planSt)=CURDATE()
//  and now()+86400000>p.plantSt 
// DATE_ADD(now(), INTERVAL 1 DAY)>  明天
  @Query("select e from EhsRecord e where e.idOp=?1 and e.idShiftD=?2 and e.ehsItem.idEhs=?3 and DATE(e.date)=CURDATE()")
	public EhsRecord findByUserAndShiftDAndDate(Long idUser,Long idShiftD,Long itemId);
  
  
  @Query("select e from EhsRecord e where e.idOp=?1 and e.ehsItem.idEhs=?2 and DATE(e.date)=CURDATE()")
	public EhsRecord findByUserAndShiftDAndDate(Long idUser,Long itemId);

}
