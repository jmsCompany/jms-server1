package com.jms.repositories.s;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.SBin;
import com.jms.domain.db.SMtfMaterial;



@Repository
public interface SMtfMaterialRepository  extends JpaRepository<SMtfMaterial, Long>{
	
	@Query("select s from SMtfMaterial s where s.SMtf.company.idCompany=?1 and s.SMtf.SMtfTypeDic.idMtfType=?2")
	public List<SMtfMaterial> getByCompanyIdAndTypeId(Long companyId,Long typeId);
	
	
	@Query("delete from SMtfMaterial s where s.SMtf.idMt=?1")
	public void deleteByMtId(Long idMt);
	
	
	@Query("select distinct s.lotNo from SMtfMaterial s where s.SPoMaterial.SPo.idPo=?1 and s.SPoMaterial.SMaterial.idMaterial=?2")
	public List<String> getLotNosBySpoIdAndMaterialId(Long spoId,Long materialId);
	
	
	@Query("select distinct s.SBinByToBin from SMtfMaterial s where s.SPoMaterial.SPo.idPo=?1 and s.SPoMaterial.SMaterial.idMaterial=?2")
	public List<SBin> getToBinsBySpoIdAndMaterialId(Long spoId,Long materialId);
	
	

		
}