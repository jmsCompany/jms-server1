package com.jms.repositories.p;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PRoutineD;



@Repository
public interface PRoutineDRepository extends JpaRepository<PRoutineD, Long>{

	@Query("select p from PRoutineD p where p.PRoutine.SMaterial.idMaterial=?1 order by p.routeNo")
    public List<PRoutineD> findByMaterialId(Long materialId);

	@Query("select p from PRoutineD p where p.PRoutine.SMaterial.idMaterial=?1 and  p.routeNo=?2")
    public PRoutineD findByMaterialIdAndRouteNo(Long materialId, String routeNo);
	
	@Query("select p from PRoutineD p where p.PRoutine.idRoutine=?1 order by p.routeNo")
    public List<PRoutineD> findByRoutineId(Long routineId);
}
