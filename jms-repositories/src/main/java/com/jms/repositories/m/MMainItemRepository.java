package com.jms.repositories.m;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.MMainCycle;
import com.jms.domain.db.MMainItem;




@Repository
public interface MMainItemRepository extends JpaRepository<MMainItem, Long>{
	
	
	@Query("select m from MMainItem m where m.MMachine.idMachine=?1 and m.MDept.idDept=?2 order by m.MMainCycle.idMainCycle")
	public List<MMainItem> getByIdMachineAndIdDept(Long idMachine,Long idDept);
	
	@Query("select m from MMainItem m where m.MMachine.idMachine=?1 and m.MMainCycle.idMainCycle=?2")
	public List<MMainItem> getByIdMachineAndIdMainCycle(Long idMachine,Long idMainCycle);

}
