package com.jms.repositories.s;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SInventory;


@Repository
public interface SInventoryRepository  extends JpaRepository<SInventory, Long>{
	
@Query("select s from SInventory s where s.SMaterial.idMaterial=?1 and s.SBin.idBin=?2 and s.lotNo=?3")
public SInventory findByMaterialIdAndBinIdAndLotNo(Long materialId,Long binId,String lotNo);


@Query("select s from SInventory s where s.SMaterial.idMaterial=?1 and s.SBin.idBin=?2")
public SInventory findByMaterialIdAndBinId(Long materialId,Long binId);

}