package com.jms.repositories.m;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.MMainRecord;




@Repository
public interface MMainRecordRepository extends JpaRepository<MMainRecord, Long>{
	
	
	@Query("select m from MMainRecord m where m.MMainItem.idMainItem=?1 and m.timeValue=?2 and m.year=?3")
	public MMainRecord getByIdMainItemAndTimeValueAndYear(Long idItem, Long timeValue,Long year);
	


}
