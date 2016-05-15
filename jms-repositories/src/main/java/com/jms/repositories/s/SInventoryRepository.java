package com.jms.repositories.s;

import java.util.List;

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


@Query("select s from SInventory s where s.SMaterial.idMaterial=?1 and s.lotNo=?2 and s.SBin.SStk.id=?3 and s.SBin.SStk.SStkTypeDic.idStkType<>4")
public List<SInventory> findBinsByMaterialIdAndLotNoAndStkId(Long materialId,String lotNo,Long stkId);


@Query("select s from SInventory s where s.SMaterial.idMaterial=?1 and s.SMaterial.company.idCompany=?2 and s.SBin.SStk.id=?3 and s.SBin.SStk.SStkTypeDic.idStkType<>4 order by s.SBin.SStk.id")
public List<SInventory> findInventorySummaryByMaterialAndStk(Long idMaterial,Long companyId,Long stkId);


@Query("select s from SInventory s where s.SMaterial.idMaterial=?1 and s.SMaterial.company.idCompany=?2 and s.SBin.SStk.SStkTypeDic.idStkType<>4  order by s.SBin.SStk.id")
public List<SInventory> findInventorySummaryByMaterial(Long idMaterial,Long companyId);

@Query("select s from SInventory s where s.SMaterial.company.idCompany=?1 and s.SBin.SStk.id=?2 and s.SBin.SStk.SStkTypeDic.idStkType<>4 order by s.SBin.SStk.id, s.SMaterial.idMaterial")
public List<SInventory> findInventorySummaryByStk(Long companyId,Long stkId);





}