package com.jms.repositories.m;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.MMainCycle;




@Repository
public interface MMainCycleRepository extends JpaRepository<MMainCycle, Long>{
	
	@Query("select m from MMainCycle m where m.idMainCycle<3")
	public List<MMainCycle> getDailyMainCycles();
	
	
	@Query("select m from MMainCycle m where m.idMainCycle>1")
	public List<MMainCycle> getPreventMainCycles();
	

}
