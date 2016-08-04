package com.jms.repositories.m;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.MSparePart;


@Repository
public interface MSparePartRepository extends JpaRepository<MSparePart, Long>{
	

	@Query("select m from MSparePart m where m.SMaterial.idMaterial=?1 and m.MMachine.idMachine=?2")
	public MSparePart findByMaterialIdAndMachineId(Long materialId,Long machineId);
	
	@Query("select m from MSparePart m where m.MMachine.idMachine=?1")
	public List<MSparePart> findMachineId(Long machineId);
}
