package com.jms.repositories.p;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PRoutineD;



@Repository
public interface PRoutineDRepository extends JpaRepository<PRoutineD, Long>{

	@Query("select p from PRoutineD p where p.PRoutine.SMaterial.idMaterial=?1")
    public List<PRoutineD> findByMaterialId(Long materialId);

}
